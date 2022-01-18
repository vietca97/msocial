package com.neo.msocial.utils;

import com.neo.msocial.dto.*;
import com.neo.msocial.request.ValidateRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
            out.write(xml.getBytes(StandardCharsets.UTF_8));
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

    public String getValueFromKeySOAP12(List<Soap12> lst, String key) {
        String ret = "";
        for (Soap12 record : lst) {
            switch (key){
                case "SEND_SMS":
                    if (record.getSEND_SMS() != null) {
                        ret = record.getSEND_SMS();
                        break;
                    }
            }

        }
        return ret;
    }

    public String getValueFromKeySOAP14(List<Soap14> lst, String key) {
        String ret = "";
        for (Soap14 record : lst) {
            switch (key){
                case "REFUSEDTOTAL":
                    if (record.getREFUSEDTOTAL() != null) {
                        ret = record.getREFUSEDTOTAL();
                        break;
                    }
            }

        }
        return ret;
    }

    public String getValueFromKeySOAP16(List<Soap16> lst, String key) {
        String ret = "";
        for (Soap16 record : lst) {
            switch (key) {
                case "WAITREGISTERTOTAL":
                    if (record.getWAITREGISTERTOTAL() != null) {
                        ret = record.getWAITREGISTERTOTAL();
                        break;
                    }
            }
        }
        return ret;
    }

    public String getValueFromKeySOAP17(List<Soap17> lst, String key) {
        String ret = "";
        for (Soap17 record : lst) {
            switch (key) {
                case "WAITPERSERVICETOTAL":
                    if (record.getWAITPERSERVICETOTAL() != null) {
                        ret = record.getWAITPERSERVICETOTAL();
                        break;
                    }
            }
        }
        return ret;
    }

    public String getValueFromKeySOAP19(List<Soap19> lst, String key) {
        String ret = "";
        for (Soap19 record : lst) {
            switch (key) {
                case "REFUSEDPERSERVICETOTAL":
                    if (record.getREFUSEDPERSERVICETOTAL() != null) {
                        ret = record.getREFUSEDPERSERVICETOTAL();
                        break;
                    }
            }
        }
        return ret;
    }

    public String getValueFromKeySOAP34(List<Soap34> lst, String key) {
        String ret = "";
        for (Soap34 record : lst) {
            if (record.getMT_TYPE_KEY() != null && record.getMT_TYPE_KEY().equals(key)) {
                ret = record.getMT_TYPE_VALUE();
                break;
            }
        }
        return ret;
    }

    public String getValueFromKeySOAP8(List<Soap8> lst, String key ) {
        String ret = "";
        for (Soap8 record : lst) {
            if (record.getMT_TYPE_KEY() != null && record.getMT_TYPE_KEY().equals(key)) {
                ret = record.getMT_TYPE_VALUE();
                break;
            }
        }
        return ret;
    }

    public String getValueFromKeySOAP15(List<Soap15> lst, String key, String value) {
        String ret = "";
        for (Soap15 record : lst) {
                switch (value) {
                    case "SPAM_TEMPLATE_VALUE":
                        if(record.getSPAM_TYPE_NAME() != null && record.getSPAM_TYPE_NAME().equals(key)){
                            ret = record.getSPAM_TEMPLATE_VALUE();
                            break;
                        }
                    case "ACTION_TYPE":
                        if(record.getSPAM_TYPE_NAME() != null && record.getSPAM_TYPE_NAME().equals(key)){
                            ret = record.getACTION_TYPE();
                            break;
                        }
                }

        }
        return ret;
    }
    public String getValueFromKeyServicePackageDetail(List<ServicePackageDetailDto> lst, String key, String value) {
        String ret = "";
        for (ServicePackageDetailDto record : lst) {
            switch (value) {
                case "PACKAGE_PRICE":
                    if(record.getPACKAGE_CODE() != null && record.getPACKAGE_CODE().equals(key)){
                        ret = record.getPACKAGE_PRICE();
                        break;
                    }
                case "CAPACITY":
                    if(record.getPACKAGE_CODE() != null && record.getPACKAGE_CODE().equals(key)){
                        ret = record.getCAPACITY();
                        break;
                    }
                case "PACKAGE_CYCLE":
                    if(record.getPACKAGE_CODE() != null && record.getPACKAGE_CODE().equals(key)){
                        ret = record.getPACKAGE_CYCLE();
                        break;
                    }
            }

        }
        return ret;
    }
    public String callSoapValidateRequest(ValidateRequest dto) {
        StringBuilder str_soap = new StringBuilder();
        str_soap.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:vms=\"http://vms.neo\">");
        str_soap.append("<soapenv:Header/><soapenv:Body>");
        str_soap.append("<vms:valiDateRequest>");
        str_soap.append("<vms:sharingkey>").append(dto.getSharingKey()).append("</vms:sharingkey>");
        str_soap.append(" <vms:serviceid>").append(dto.getServiceId()).append("</vms:serviceid>");
        str_soap.append("<vms:packagecode>").append(dto.getPackageCode()).append("</vms:packagecode>");
        str_soap.append("<vms:msisdn>").append(dto.getMsisdn()).append("</vms:msisdn>");
        str_soap.append("<vms:channelkey>").append(dto.getChannelKey()).append("</vms:channelkey>");
        str_soap.append("</vms:valiDateRequest></soapenv:Body></soapenv:Envelope>");

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
                // System.out.println(sdf.format(new java.util.Date()) + ":" + str_soap.toString() + ", res: " + resp);
                return resp;
            } else {
                return "-1";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "-1";
        }
    }

    public List<Soap2> convertStringXmlToObject(ValidateRequest dto){
        List<Soap2> lstSoap2 = new ArrayList<>();
        String xml = callSoapValidateRequest(dto);
        String[] results = xml.split("record>");
        for(int i = 1 ; i < results.length - 1; i++){
            Soap2 soap2 = new Soap2();
            soap2.setPartnerId(results[i].substring(results[i].indexOf("partner_id>") + "partner_id>".length(),results[i].indexOf("/partner_id>")).split("&#38;lt")[0]);
            soap2.setAgentId(results[i].substring(results[i].indexOf("agent_id>") + "agent_id>".length(),results[i].indexOf("/agent_id>")).split("&#38;lt")[0]);
            soap2.setServiceId(results[i].substring(results[i].indexOf("service_id>") + "service_id>".length(),results[i].indexOf("/service_id>")).split("&#38;lt")[0]);
            //soap2.setTimeValidate(results[i].substring(results[i].indexOf("time_validate>") + "time_validate>".length(),results[i].indexOf("/time_validate>")).split("&#38;lt")[0]);
            soap2.setTimeValidate("1");
            soap2.setMaChiNhanh(results[i].substring(results[i].indexOf("ma_chi_nhanh>") + "ma_chi_nhanh>".length(),results[i].indexOf("/ma_chi_nhanh>")).split("&#38;lt")[0]);
            soap2.setScriptShopId(results[i].substring(results[i].indexOf("script_shop_id>") + "script_shop_id>".length(),results[i].indexOf("/script_shop_id>")).split("&#38;lt")[0]);
            soap2.setSharingKeyId(results[i].substring(results[i].indexOf("sharing_key_id>") + "sharing_key_id>".length(),results[i].indexOf("/sharing_key_id>")).split("&#38;lt")[0]);
            soap2.setChannelId(results[i].substring(results[i].indexOf("channel_id>") + "channel_id>".length(),results[i].indexOf("/channel_id>")).split("&#38;lt")[0]);
            soap2.setPackageCodeId(results[i].substring(results[i].indexOf("packagecode_id>") + "packagecode_id>".length(),results[i].indexOf("/packagecode_id>")).split("&#38;lt")[0]);
            soap2.setErrorCode("0");
            soap2.setErrorDesc("success");
            lstSoap2.add(soap2);
        }

        return lstSoap2;
    }
}
