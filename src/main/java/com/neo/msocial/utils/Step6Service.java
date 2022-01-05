package com.neo.msocial.utils;

import com.neo.msocial.service.Activation;
import org.springframework.beans.factory.annotation.Autowired;

public class Step6Service {
    @Autowired
    private RedisUtils context;

    public String sendSms(String receiver, String messageSms){
        try{
            System.out.println(receiver+"|||"+messageSms);
            String serviceNumber = context.get("SERVICE_NUMBER");
            String smsHost = context.get("SMS_HOST");
            String smsPort = context.get("SMS_PORT");
            String smsLookup = context.get("SMS_LOOKUP");
            String utilUrl = context.get("dataflow_param:utilmodule");

            StringBuilder str_soap = new StringBuilder();

            str_soap.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:vms=\"http://vms.neo\">");
            str_soap.append("<soapenv:Header/><soapenv:Body>");
            str_soap.append("<vms:sendSms>");
            str_soap.append("<vms:args0>").append(serviceNumber).append("</vms:args0>");
            str_soap.append("<vms:args1>").append(receiver).append("</vms:args1>");
            str_soap.append("<vms:args2>").append(messageSms).append("</vms:args2>");
            str_soap.append("<vms:args3>").append(smsHost).append("</vms:args3>");
            str_soap.append("<vms:args4>").append(smsPort).append("</vms:args4>");
            str_soap.append("<vms:args5>").append(smsLookup).append("</vms:args5>");
            str_soap.append("</vms:queryXml></soapenv:Body></soapenv:Envelope>");

            String result = new Activation().soapCall(utilUrl,str_soap.toString());
            return result;
        }catch(Exception e){
            return "-1|"+e.getMessage();
        }
    }
}
