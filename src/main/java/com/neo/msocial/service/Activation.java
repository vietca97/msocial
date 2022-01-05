package com.neo.msocial.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Configuration
@PropertySource("classpath:df.properties")
@Component
public class Activation {
    Logger logTask = Logger.getLogger("task"), logRequest = Logger.getLogger("request"), logOther = Logger.getLogger("others");
    public static JedisSentinelPool jedisPool = null;
    static int minute = 1*60*1000;
    static int time_out = 3*minute;

    @Value("${redis_server}")
    private  String redis_server;

    DB db = new DB();
    Gson gson = new Gson();
    //static SqlServices sqlServices = null;
    private static Map<String, BasicDataSource> oracleDS = new HashMap<String, BasicDataSource>();

    String translog_database, translog_log_request;
    String translog_update_request_status;
    String translog_log_request_task;
    private Map<String, String> sysParam = new HashMap<String, String>();
    Map<String, Map<String, String>> srcs = new HashMap<String, Map<String, String>>();

    @SuppressWarnings("unused")
    private int socketTimeout = 10000, soapTimeout = 100000;

    static PropertiesConfiguration props;
    static final String dataSource = "default";
    //static SMSServiceConnection smsservice = null;
    //static neo.vms.util.SmsSender smsnew = null;
    //System parameter
    static Map<String, String> sys_param = new HashMap<String, String>();
//    static {
//        try {
//            props = new PropertiesConfiguration();
//            props.setDelimiterParsingDisabled(true);
//            props.setEncoding("UTF8");
//            props.setPath("df.properties");
//            props.load();
//            props.setReloadingStrategy(new FileChangedReloadingStrategy());
//        } catch (Exception e) {
//        }
//    }

    public static String now(String f) {
        DateFormat dateFormat = new SimpleDateFormat(f);
        java.util.Date date = new java.util.Date();
        return dateFormat.format(date);
    }
    
    public Activation() {
    	try {
        } catch (Exception ex) {
        }
    }
    
    public void logMessage(String message) {
        //log.writeToLogWithDate(message);
    }
    
    public Activation(JedisSentinelPool pool) {
        if (jedisPool==null) {
        	jedisPool = pool;
        }
        init();
    }

    //Backup ham cu ko phu hop UTF8:
    public String soapCall(String url, String body) {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        String out = "";
        long start = System.currentTimeMillis();
        try {
            url = url.replace("APACHE_LOAD_BALANCING_VIP", sysParam.get("dataflow_param:APACHE_LOAD_BALANCING_VIP") + "");
            url = url.replace("APACHE_LOAD_BALANCING_1", sysParam.get("dataflow_param:APACHE_LOAD_BALANCING_1") + "");
            url = url.replace("APACHE_LOAD_BALANCING_2", sysParam.get("dataflow_param:APACHE_LOAD_BALANCING_2") + "");
            HttpPost post = new HttpPost(url);

            post.setEntity(new StringEntity(body,"UTF8"));
            post.setHeader("Content-Type", "text/xml; charset=UTF-8");
            CloseableHttpResponse res = httpclient.execute(post);
            out = IOUtils.toString(res.getEntity().getContent(), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            out = "ERR_WS_TIME_OUT";
            System.out.println("soapcall error: " + url + "...." + out + "|" + e.toString());
            return out + ":" + e.getMessage();
        } finally {
            httpclient.close();
            start = System.currentTimeMillis() - start;
            StringBuilder str = new StringBuilder("soapCall:").append(url).append(", body:[").append(body).append("]|res:").append(out.length()).append(", time_ms:").append(start);
            System.out.println(str.toString());
            //log.writeToLogWithDate(str.toString());
        }
        return out;
    }
    
    //Nen chuyen sang su dung ham nay.
    public String soapCall2(String url_call, String body) {
		String v = "";
		StringBuilder response = new StringBuilder();
		long start = System.currentTimeMillis();
		try{
			url_call = url_call.replace("APACHE_LOAD_BALANCING_VIP", sysParam.get("dataflow_param:APACHE_LOAD_BALANCING_VIP") + "");
			url_call = url_call.replace("APACHE_LOAD_BALANCING_1", sysParam.get("dataflow_param:APACHE_LOAD_BALANCING_1") + "");
			url_call = url_call.replace("APACHE_LOAD_BALANCING_2", sysParam.get("dataflow_param:APACHE_LOAD_BALANCING_2") + "");
			
			URL url = new URL(url_call);
			URLConnection connection = url.openConnection();
			HttpURLConnection httpConn = (HttpURLConnection)connection;
			httpConn.setConnectTimeout(time_out);
			
			httpConn.setRequestMethod("POST");
			httpConn.setRequestProperty( "Content-Type", "text/xml; charset=utf-8");
			
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);
			OutputStream out = httpConn.getOutputStream();
			out.write(body.getBytes("UTF8"));
			out.flush();
			out.close();
			if (httpConn.getResponseCode()==200) {
				InputStreamReader isr = new InputStreamReader(httpConn.getInputStream());
				BufferedReader in = new BufferedReader(isr);
				String value =null;
				while ((value = in.readLine()) != null) {
					response.append(value).append("\n");//Them xuong dong la ok...
				}
				in.close();
				v = response.toString();
			}else{
				v= "ERRWS_01:" + httpConn.getResponseCode();
			}
		}catch(Exception e) {
			e.printStackTrace();
			v = "ERRWS_02_time_out:" + e.toString();
			//start = System.currentTimeMillis() - start;
			StringBuilder str = new StringBuilder("soapCall_error: ").append(url_call).append(",[").append(body).append("]|res:").append(v);
            System.out.println(str.toString());
		}finally {
			start = System.currentTimeMillis() - start;
            StringBuilder str = new StringBuilder("soapCall: ").append(url_call).append(",[").append(body).append("]|res_length:").append(v.length()).append(", time_ms: ").append(start);
            System.out.println(str.toString());
            //log.writeToLogWithDate(str.toString());
		}
		return v;
	}

    public void init() {
    	Jedis jedis = null;
    	try {
    		System.out.println("-----> Start get all params init: *****************************************");
    		jedis = jedisPool.getResource();
	        translog_database = jedis.get("translog:database").toString();
	        translog_log_request = jedis.get("translog:log_request").toString();
	        translog_log_request_task = jedis.get("translog:log_request_task").toString();
	        translog_update_request_status = jedis.get("translog:update_request_status").toString();
	
	        String socketTimeout_ = jedis.get("properties:socket_timeout").toString();
	        if (socketTimeout_ != null && !socketTimeout_.equals("")) {
	            socketTimeout = Integer.parseInt(socketTimeout_);
	        }
	        String soapTimeout_ = jedis.get("properties:soap_timeout").toString();
	        if (soapTimeout_ != null && !soapTimeout_.equals("")) {
	            soapTimeout = Integer.parseInt(soapTimeout_);
	        }
	        
	        Set<String> param = jedis.keys("dataflow_param:*");
	        Iterator<String> it1 = param.iterator();
	        
	        while (it1.hasNext()) {
	            String key = it1.next();
	            String value = jedis.get(key);
	            /* Su dung cho Site HCM? Check lai co che cho nay, nen de o file cau hinh:
	            if (value!=null) {
		            value = value.replace("10.54.4.129", "10.54.73.47");
		            value = value.replace("10.54.4.130", "10.54.73.47");
		            value = value.replace("10.54.4.176", "10.54.73.47");
	            }
	            System.out.println("----> "+ key +" = " + value);
	            */
	            sysParam.put(key, value);
	        }
	
	        Set<String> srcset = jedis.keys("dataflow_src:*");
	        Iterator<String> it2 = srcset.iterator();
	        while (it2.hasNext()) {
	            String key = it2.next();
	            String cfg = jedis.get(key);
	            System.out.println(key + "="+ cfg);
	            Map<String, String> props = gson.fromJson(cfg, new TypeToken<Map<String, String>>() {
	            }.getType());
	            srcs.put(key, props);
	        }
	        System.out.println("-----> END init get all params init: *****************************************");
        }catch(Exception e) {
        	e.printStackTrace();
        }finally{
        	try{
        		if (jedis!=null) {
        			jedis.close();
        		}
        	}catch(Exception e) {
        		e.printStackTrace();
        	}
        }
    }

    public Connection getOracleConn(Map<String, String> props) throws SQLException {
        String src = props.get("src_name");
        if (!oracleDS.containsKey(src)) {
            BasicDataSource oracleDS_ = new BasicDataSource();
            oracleDS_.setDriverClassName(props.get("oracle.driver"));
            oracleDS_.setUsername(props.get("oracle.username"));
            oracleDS_.setPassword(props.get("oracle.password"));
            oracleDS_.setUrl(props.get("oracle.url"));
            oracleDS_.setMaxIdle(Integer.parseInt(props.get("oracle.max_idle")));
            oracleDS_.setMaxTotal(Integer.parseInt(props.get("oracle.max_total")));
            oracleDS_.setInitialSize(Integer.parseInt(props.get("oracle.initial_size")));
            oracleDS.put(src, oracleDS_);
        }
        return oracleDS.get(src).getConnection();
    }

    public synchronized Connection getConn(String src) throws SQLException {
        Map<String, String> props = srcs.get("dataflow_src:" + src);
        if (props.get("type").equals("Oracle")) {
            return getOracleConn(props);
        }
        return null;
    }

    public Map Call(String serviceName, Map Param) throws SQLException, IOException {
        String traceId = serviceName + "_" + (System.nanoTime() + (new Random()).nextInt(10000));
        Map<String, Object> out = new LinkedHashMap<String, Object>();
        Jedis jedis = null;
        try {
            //System.out.println("Jedis pool: " + jedisPool.getNumActive() + ",idle:" + jedisPool.getNumIdle() + ",idle:" + jedisPool.getNumWaiters());
            System.out.println("----> Start call Dataflow: " + serviceName + ", param: " + Param);
        	jedis = jedisPool.getResource();
        	
            final Map context = new HashMap();
            context.putAll(sysParam);
            context.putAll(Param);
            List flow = new LinkedList();//LinkedHashMap
            Map<String, Map<String, String>> stepDetail = new HashMap<String, Map<String, String>>();//LinkedHashMap
            out.put("traceId", traceId);
            out.put("startDate", now("yyyy/MM/dd hh:mm:ss"));
            out.put("serviceName", serviceName);
            out.put("startParameters", Param);
            out.put("nanoStart", String.valueOf(System.nanoTime()));
            logRequest.debug(gson.toJson(out));

            Map reqTrans = new LinkedHashMap();
            reqTrans.put("id", traceId);
            reqTrans.put("start_Date", now("yyyy/MM/dd hh:mm:ss"));
            reqTrans.put("service_Name", serviceName);
            reqTrans.put("input_Params", Param.toString());
            reqTrans.put("nanoStart", String.valueOf(System.nanoTime()));

            String conf = "";
            //System.out.println("----> Start call Dataflow2: " + serviceName + ", param:" + Param);
            conf = jedis.get(serviceName);
            jedis.close();
            //System.out.println("----> Start call Dataflow3: " + serviceName + ", param:" + Param);

            Map<String, List<Map<String, String>>> rows = gson.fromJson(conf, new TypeToken<Map<String, List<Map<String, String>>>>() {
            }.getType());
            String nextStep = "";
            boolean nextStepIsTrueStep = true;
            Map<String, String> taskType = new LinkedHashMap<String, String>();
            for (Map<String, String> node : rows.get("nodes")) {
                taskType.put(node.get("id"), node.get("type"));
                for (Map.Entry<String, String> e : node.entrySet()) {
                    if (e.getKey().equals("type") && e.getValue().equals("start")) {
                        nextStep = node.get("id");
                    }
                }
            }
            int order = 0;
            while (!taskType.get(nextStep).equals("end")) {
                order++;
                flow.add(nextStep);
                final Map<String, String> props = new LinkedHashMap<String, String>();
                for (Map<String, String> node : rows.get("properties")) {
                    if (node.get("key").startsWith(taskType.get(nextStep) + "|" + nextStep)) {
                        props.put(node.get("key").replace(taskType.get(nextStep) + "|" + nextStep + "|", ""), eval(node.get("value"), context));
                    }
                }
                Map task = new LinkedHashMap<String, String>();
                task.put("taskType", taskType.get(nextStep));
                task.put("taskId", nextStep);
                task.put("taskStartDate", now("yyyy/MM/dd hh:mm:ss"));
                task.put("nanoTaskStart", String.valueOf(System.nanoTime()));
                logTask.debug(gson.toJson(task));

                Map taskTrans = new LinkedHashMap();
                taskTrans.put("id", nextStep);
                taskTrans.put("request_Id", traceId);
                taskTrans.put("task_Start_Date", now("yyyy/MM/dd hh:mm:ss"));
                taskTrans.put("task_Type", taskType.get(nextStep));
                taskTrans.put("task_order", order);
                taskTrans.put("nanoTaskStart", String.valueOf(System.nanoTime()));

                //SQL
                if (taskType.get(nextStep).equals("sql")) {
                    //Update
                    if (props.get("type").equals("Update")) {
                        int r_ = db.update(props.get("sql"), getConn(props.get("source")));
                        task.put(props.get("out_param"), r_);
                        context.put(props.get("out_param"), r_);
                    } else if (props.get("type").equals("Select")) {
                        Connection conn = getConn(props.get("source"));
                        List<Map<String, String>> list = db.toList(db.select(props.get("sql"), conn));
                        task.put(props.get("out_param"), list);
                        context.put(props.get("out_param"), list);
                        conn.close();
                    } else if (props.get("type").equals("Function")) {
                        String r_ = db.callFunc(props.get("sql"), getConn(props.get("source")));
                        task.put(props.get("out_param"), r_);
                        context.put(props.get("out_param"), r_);
                    }
                    //SOAP
                } else if (taskType.get(nextStep).equals("soap")) {

                    String body = soapCall(props.get("url"), props.get("request"));
                    task.put(props.get("response"), body);
                    context.put(props.get("response"), body);
                    //Extract value
                    for (int i = 1; i < 20; i++) {
                        if (props.get("extract" + i) != null && !props.get("extract" + i).equals("")) {
                            try {
                                String extract = parseXMLtext(body, props.get("extract" + i));
                                if (extract != null && extract.indexOf("\n") >= 0) {
                                    extract = "\"\"\"" + extract + "\"\"\"";
                                }
                                context.put(nextStep + "_extract" + i, extract);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    //extract attribute
                    for (int i = 1; i < 20; i++) {
                        if (props.get("attr" + i) != null && !props.get("attr" + i).equals("")) {
                            try {
                                String extract = parseXMLattr(body, props.get("attr" + i));
                                if (extract != null && extract.indexOf("\n") >= 0) {
                                    extract = "\"\"\"" + extract + "\"\"\"";
                                }
                                context.put(nextStep + "_attr" + i, extract);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    //Groovy
                } else if (taskType.get(nextStep).equals("groovy")) {
                    try {
                        Binding binding = new Binding();
                        for (Object e1 : context.entrySet()) {
                            Map.Entry e = (Map.Entry) e1;
                            if (e.getKey() != null) {
                                binding.setVariable(e.getKey().toString(), e.getValue());
                            } else {
                                //System.out.println(e);
                            }
                        }
                        binding.setVariable("context", context);
                        GroovyShell shell = new GroovyShell(binding);
                        Object value = shell.evaluate(props.get("script").intern());
                        shell = null;
                        task.put(props.get("out_param"), value);
                        context.put(props.get("out_param"), value);
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                    //socket
                } else if (taskType.get(nextStep).equals("socket")) {
                    Socket echoSocket = new Socket();
                    echoSocket.connect(new InetSocketAddress(props.get("server"), Integer.parseInt(props.get("port"))), socketTimeout / 1000);
                    echoSocket.setSoTimeout(socketTimeout);
                    OutputStream os = echoSocket.getOutputStream();
                    DataInputStream is = new DataInputStream(echoSocket.getInputStream());
                    os.write(props.get("request").getBytes());
                    String sout = is.readLine();
                    os.close();
                    is.close();
                    echoSocket.close();
                    task.put(props.get("response"), sout);
                    context.put(props.get("response"), sout);

                    //gateway
                } else if (taskType.get(nextStep).equals("gateway")) {
                    Binding binding = new Binding();
                    for (Object e1 : Param.entrySet()) {
                        Map.Entry e = (Map.Entry) e1;
                        binding.setVariable(e.getKey().toString(), e.getValue());
                    }
                    binding.setVariable("context", context);
                    GroovyShell shell = new GroovyShell(binding);
                    //System.out.println("condition:"+props.get("condition"));
                    Object value = shell.evaluate(props.get("condition").intern());
                    shell = null;
                    //System.out.println("gateway return: "+value.toString());
                    if (!Boolean.valueOf(value.toString())) {
                        nextStepIsTrueStep = false;
                    }

                    System.out.println("nextStepIsTrueStep:" + nextStepIsTrueStep);
                    task.put(props.get("out_param"), value);
                    context.put(props.get("out_param"), value);
                }
                task.put("nanoTaskEnd", String.valueOf(System.nanoTime()));
                logTask.debug(gson.toJson(task));
                if (task.toString().length() > 4000) {
                    taskTrans.put("task_Context", task.toString().substring(0, 4000).replaceAll("'", "''"));
                } else {
                    taskTrans.put("task_Context", task.toString().replaceAll("'", "''"));
                }
                taskTrans.put("nanoEnd", String.valueOf(System.nanoTime()));
                stepDetail.put(nextStep, task);
                for (Map<String, String> conn : rows.get("connections")) {
					//System map
                    if (conn.get("sourceId").equals(nextStep)) {
                        boolean isHadNextTask = false;
                        if (taskType.get(nextStep).equals("gateway")) {
                            if (nextStepIsTrueStep) {
                                System.out.println("check:" + conn.get("isTrue") + "," + conn.get("targetId"));
                                if (conn.get("isTrue").equals("true")) {
                                    nextStep = conn.get("targetId");
                                    isHadNextTask = true;
                                }
                            } else {
                                if (!conn.containsKey("isTrue")) {
                                    nextStep = conn.get("targetId");
                                    isHadNextTask = true;
                                }
                            }
                            if (isHadNextTask) {
                                break;
                            }
                        } else {
                            nextStep = conn.get("targetId");
                            break;
                        }
                    }
                }
            }
            flow.add(nextStep);

            Map endTrans = new LinkedHashMap();
            endTrans.put("id", traceId);
            endTrans.put("end_date", now("yyyy/MM/dd hh:mm:ss"));
            endTrans.put("ended_Steps", flow.toString());
            endTrans.put("nanoEnd", String.valueOf(System.nanoTime()));

            out.put("nanoEnd", String.valueOf(System.nanoTime()));
            out.put("context", context);
            out.put("dataflow", flow);
            out.put("steps", stepDetail);
            logRequest.debug(gson.toJson(out));

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        } finally {

        }
        return out;
    }

    public static String eval(String input, Map parameters) {
        VelocityContext vcontext = new VelocityContext(parameters);
        StringWriter sw = new StringWriter();
        Velocity.evaluate(vcontext, sw, "velocity", input);
        return sw.toString();
    }

    public void shutdown() throws SQLException {
        //System.out.println("start destroy");
        for (Map.Entry<String, BasicDataSource> e : oracleDS.entrySet()) {
            BasicDataSource ds = e.getValue();
            try {
            	if (ds!=null){
            		ds.close();
            	}
            } catch (Exception e1) {
                System.out.println("shutdown Activation but can't close BasicDataSource for reason:" + e1.getMessage());
            } finally {

            }
        }
    }

    public String parseXMLtext(String xml, String path) {
    	try{
	    	SAXReader reader = new SAXReader();
	        Document document = reader.read(new StringReader(xml));
	        String ret = document.selectSingleNode(path).getText();
	        return ret;
    	}catch(Exception e) {
    		e.printStackTrace();
    		return "";
    	}
    }

    public String parseXMLattr(String xml, String path) {
    	try{
	        SAXReader reader = new SAXReader();
	        Document document = reader.read(new StringReader(xml));
	        return ((Attribute) document.selectSingleNode(path)).getText();
    	}catch(Exception e){
    		e.printStackTrace();
    		return "";
    	}
    }

    public String readUrl(String url) {
        String ret = "";
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
            String strTemp = "";
            StringBuffer buffer = new StringBuffer();
            while (null != (strTemp = br.readLine())) {

                buffer.append(strTemp);
            }
            br.close();

            ret = buffer.toString();
			//System.out.println(ret);

            Map<String, Object> rootDefObject = (Map<String, Object>) new groovy.json.JsonSlurper().parseText(ret);
            //System.out.println(rootDefObject);
            Map<String, String> child = (Map<String, String>) rootDefObject.get("context");
            System.out.println(child.get("ErrorCodeAPI"));
            System.out.println(child.get("ErrorDescAPI"));
            ret = child.get("ErrorCodeAPI") + "|" + child.get("ErrorDescAPI");
        } catch (Exception e) {
            e.printStackTrace();
            ret = "-1|" + e.getMessage();
        }
        return ret;
    }
    public static void main(String[] args) {
            Activation act = new Activation();
  		  System.out.println(act.redis_server);
    }
}
