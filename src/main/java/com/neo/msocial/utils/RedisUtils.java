package com.neo.msocial.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
@Component
@PropertySource("classpath:app.properties")
public class RedisUtils {

    final  HashSet<String> sentinels = new HashSet<String>();
    public  JedisSentinelPool jedisPool = null;

    String translog_database, translog_log_request;
    String translog_update_request_status;
    String translog_log_request_task;

    Gson gson = new Gson();
    public  JedisPoolConfig configPool = null;
    private  Map<String, String> sysParam = new HashMap<String, String>();
    private  Map<String, Map<String, String>> srcs = new HashMap<String, Map<String, String>>();

    @Value("${redis.server}")
    private String redisServer;

    @Value("${redis.port}")
    private String redisPort;

    @SuppressWarnings("unused")
    private static int socketTimeout = 10000, soapTimeout = 100000;

    public RedisUtils(){
        if(jedisPool == null ){
            init();
        }
    }
    public  String get(String serviceName){
        if(jedisPool == null ){
            init();
        }
        Jedis jedis = jedisPool.getResource();
        return jedis.get(serviceName);
    }

    public void put(String key, String value){
        if(jedisPool == null ){
            init();
        }
        Jedis jedis = jedisPool.getResource();
        jedis.append(key,value);
    }

    public void set(String key, String value){
        if(jedisPool == null ){
            init();
        }
        Jedis jedis = jedisPool.getResource();
        jedis.set(key,value);
    }

    public  void init(){
        redisServer = "10.252.12.237;";
        redisPort = "16379;";
        String[] host = redisServer.split(";");
        String[] port = redisPort.split(";");

        for (int i = 0; i < host.length; i++) {
            sentinels.add(host[i] + ":" + Integer.parseInt(port[i]));
        }
        configPool = new JedisPoolConfig();
        configPool.setMaxTotal(10000);
        configPool.setMaxIdle(10000);
        configPool.setMinIdle(200);
        //config.setTestOnBorrow(true);
        //config.setTestOnReturn(true);
        //configPool.setTestWhileIdle(true);
        configPool.setTimeBetweenEvictionRunsMillis(60000);
        configPool.setMaxWaitMillis(30000);
        //System.out.println("config:"+sentinels.size());
        jedisPool = new JedisSentinelPool("mymaster", sentinels, configPool, 30 * 1000);

    }

    public  void initPool() {
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
                String value = jedis.get(key)
                        ;
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
                String cfg = jedis.get(key)
                        ;
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
}