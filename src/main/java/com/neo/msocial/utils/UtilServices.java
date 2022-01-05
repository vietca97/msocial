package com.neo.msocial.utils;

import com.neo.msocial.dto.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Component
@PropertySource("classpath:app.properties")
public class UtilServices {

    static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    public static int time_out = 3 * 60 * 1000;

    @Value("${vasgate.soapapi}")
    private String urlSoap;

    public String callSoapHttp(String xml, String url_api) {
        String v = "";
        StringBuilder response = new StringBuilder();
        Date d = new Date();
        long time = System.currentTimeMillis();
        try {
            URL url = new URL(url_api);
            URLConnection connection = url.openConnection();
            HttpURLConnection httpConn = (HttpURLConnection) connection;
            httpConn.setConnectTimeout(time_out);

            httpConn.setRequestMethod("POST");
            //httpConn.setRequestProperty("Content-Length", String.valueOf(xml.length()));
            httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            OutputStream out = httpConn.getOutputStream();
            out.write(xml.getBytes("utf8"));
            out.flush();
            out.close();
            //Read the response:
            if (httpConn.getResponseCode() == 200) {
                InputStreamReader isr = new InputStreamReader(httpConn.getInputStream());
                BufferedReader in = new BufferedReader(isr);

                String value = null;
                while ((value = in.readLine()) != null) {
                    response.append(value);
                }
                in.close();
                v = response.toString();
            } else {
                v = "ERRWS_01:" + httpConn.getResponseCode();
            }
        } catch (Exception e) {
            e.printStackTrace();
            v = "ERRWS_02:" + e.toString();
        } finally {
            time = System.currentTimeMillis() - time;
            StringBuilder str = new StringBuilder();
            str.append(sdf.format(d)).append(", call cmd: ").append(xml).append(", response: ").append(v).append(", time_ms: ").append(time);
            //LogObj logObj = new LogObj(thread_id, str.toString());
            // if (mngService!=null) mngService.pushLog(logObj);
            //log.writeToLog(str.toString(), "logs/log");
        }
        return v;
    }


    public String callSoapVasGate(RegisterServicePartnerDTO dto) {
        StringBuilder str_soap = new StringBuilder();
        str_soap.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:vms=\"http://vms.neo\">");
        str_soap.append("<soapenv:Header/><soapenv:Body>");
        str_soap.append("<vms:queryXml>");
        str_soap.append("<vms:Service>").append(dto.getService()).append("</vms:Service>");
        str_soap.append("<vms:Provider>").append(dto.getProvider()).append("</vms:Provider>");
        str_soap.append("<vms:ParamSize>").append(dto.getParamSize()).append("</vms:ParamSize>");
        str_soap.append("</vms:queryXml></soapenv:Body></soapenv:Envelope>");
        try {
            String resp = callSoapHttp(str_soap.toString(), urlSoap);
            if (resp != null) {
				/*
				Document d = stringToDom(resp);
				NodeList l = d.getElementsByTagName("ns:return");
				String value = l.item(0).getTextContent();
				*/
                String value = getValueResult(resp);
                //Sau do parser loai bo ky tu xml dac biet?
                resp = parserXmlFormat(value);
                System.out.println(sdf.format(new java.util.Date()) + ":" + str_soap.toString() + ", res: " + resp);
                return resp;
            } else {
                return "-1";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "-1";
        }
    }

    public String getValueResult(String xml) {
        String v = "";
        if (xml == null) return "";
        try {
            int id = xml.indexOf("<ns:return>");
            if (id > 0) {
                v = xml.substring(id + 11, xml.indexOf("</ns:return>"));
            } else {
                v = xml;
            }
        } catch (Exception e) {
            e.printStackTrace();
            v = xml;
        }
        return v;
    }

    public static String parserXmlFormat(String v) {
        StringBuilder str = new StringBuilder();
        try {
            if (v == null) return "";
            if (v.length() >= 100) {
                int size = v.length();
                for (int i = 0; i < size; i++) {
                    char c = v.charAt(i);
                    if (c == '<') {
                        str.append("&lt;");
                    } else if (c == '&') {
                        str.append("&#38;");
                    } else {
                        str.append(c);
                    }
                }//End for.
            } else {
                str.append(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR:parserXmlFormat: " + v);
        }
        return str.toString();
    }

    String getValueFromKeySOAP12(List<Step11DTO> lst) {
        String ret = "";
        for (Step11DTO record : lst) {
            if (record.getSEND_SMS() != null) {
                ret = record.getSEND_SMS();
                break;
            }
        }
        return ret;
    }

    String getValueFromKeySOAP14(List<Soap14DTO> lst) {
        String ret = "";
        for (Soap14DTO record : lst) {
            if (record.getREFUSEDTOTAL() != null) {
                ret = record.getREFUSEDTOTAL();
                break;
            }
        }
        return ret;
    }

    String getValueFromKeySOAP16(List<Soap16DTO> lst) {
        String ret = "";
        for (Soap16DTO record : lst) {
            if (record.getWAITREGISTERTOTAL() != null) {
                ret = record.getWAITREGISTERTOTAL();
                break;
            }
        }
        return ret;
    }

    String getValueFromKeySOAP17(List<Soap17DTO> lst) {
        String ret = "";
        for (Soap17DTO record : lst) {
            if (record.getWAITPERSERVICETOTAL() != null) {
                ret = record.getWAITPERSERVICETOTAL();
                break;
            }
        }
        return ret;
    }

    String getValueFromKeySOAP19(List<Soap19DTO> lst) {
        String ret = "";
        for (Soap19DTO record : lst) {
            if (record.getREFUSEDPERSERVICETOTAL() != null) {
                ret = record.getREFUSEDPERSERVICETOTAL();
                break;
            }
        }
        return ret;
    }

    String getValueFromKeySOAP34(List<Soap34DTO> lst, String key) {
        String ret = "";
        for (Soap34DTO record : lst) {
            if (record.getMT_TYPE_KEY().equals(key) ) {
                ret = record.getMT_TYPE_VALUE();
                break;
            }
        }
        return ret;
    }

    String getValueFromKeySOAP8(List<Step9DTO> lst, String key ) {
        String ret = "";
        for (Step9DTO record : lst) {
            if (key.equals(record.getMT_TYPE_KEY())) {
                ret = record.getMT_TYPE_VALUE();
                break;
            }
        }
        return ret;
    }

    String getValueFromKeySOAP15(List<Step8DTO> lst, String key, String value) {
        String ret = "";
        for (Step8DTO record : lst) {
            if (key.equals(record.getSPAM_TYPE_NAME())) {
                switch (value) {
                    case "SPAM_TEMPLATE_VALUE":
                        ret = record.getSPAM_TEMPLATE_VALUE();
                        break;
                    case "ACTION_TYPE":
                        ret = record.getACTION_TYPE();
                        break;
                }

            }
        }
        return ret;
    }
}
