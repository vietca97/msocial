package com.neo.msocial.groovy;

import com.neo.msocial.dto.PolicyDTO;
import com.neo.msocial.dto.Soap12;
import com.neo.msocial.dto.Soap34;
import com.neo.msocial.dto.Soap8;
import com.neo.msocial.service.Activation;
import com.neo.msocial.service.ParseXml;
import com.neo.msocial.utils.RedisUtils;
import com.neo.msocial.utils.SystemParameterServices;
import com.neo.msocial.utils.UtilServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.*;
import java.util.Date;
import java.util.List;

@Component
public class ChongLoiDung {

    @Autowired
    private RedisUtils context;

    @Autowired
    private UtilServices utilServices;

    @Autowired
    private SystemParameterServices services;

    String logSql ( String msisdn, String transactionId, String stepName, String stepIndex, String stepKey, String stepInput, String stepOutput, String stepAction, String startTime, String endTime, String inputParameter, String scriptShopId ){
        StringBuilder str_soap = new StringBuilder();
        str_soap.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:vms=\"http://vms.neo\">");
        str_soap.append("<soapenv:Header/><soapenv:Body>");
        str_soap.append("<vms:updateXml>");

        str_soap.append("<vms:Service>").append("sql_log_transaction_step").append("</vms:Service>");
        str_soap.append("<vms:Provider>").append("default").append("</vms:Provider>");
        str_soap.append("<vms:ParamSize>").append("12").append("</vms:ParamSize>");

        str_soap.append("<vms:P1>").append(transactionId).append("</vms:P1>");
        str_soap.append("<vms:P2>").append(stepName).append("</vms:P2>");
        str_soap.append("<vms:P3>").append(stepIndex).append("</vms:P3>");
        str_soap.append("<vms:P4>").append(stepKey).append("</vms:P4>");
        str_soap.append("<vms:P5>").append(stepInput).append("</vms:P5>");
        str_soap.append("<vms:P6>").append(stepOutput).append("</vms:P6>");
        str_soap.append("<vms:P7>").append(stepAction).append("</vms:P7>");
        str_soap.append("<vms:P8>").append(startTime).append("</vms:P8>");
        str_soap.append("<vms:P9>").append(endTime).append("</vms:P9>");
        str_soap.append("<vms:P10>").append(msisdn).append("</vms:P10>");
        str_soap.append("<vms:P11>").append(inputParameter).append("</vms:P11>");
        str_soap.append("<vms:P12>").append(scriptShopId).append("</vms:P12>");

        str_soap.append("</vms:updateXml></soapenv:Body></soapenv:Envelope>");


        String result = new Activation().soapCall(context.get("dataflow_param:sqlmodule"), str_soap.toString());
        return result;
    }
    String sendSms ( String receiver, String messageSms ){
        try {
            System.out.println(receiver + "|||" + messageSms);
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
            str_soap.append("</vms:sendSms></soapenv:Body></soapenv:Envelope>");
            String result = new Activation().soapCall(utilUrl, str_soap.toString());
            return result;
        } catch (Exception e) {
            return "-1|" + e.getMessage();
        }
    }

    String getValueLoiDung(List<PolicyDTO> lst, String key) {
        String ret = "";
        for (PolicyDTO record : lst) {
            switch (key) {
                case "POLICY_TYPE_NAME":
                    if (record.getPOLICY_TYPE_NAME() != null) {
                        ret = record.getPOLICY_TYPE_NAME();
                        break;
                    }
                case "PRICE":
                    if (record.getPRICE() != null) {
                        ret = record.getPRICE();
                        break;
                    }
            }

        }
        return ret;
    }

//do bussiness
/*
soap_8: mt_info
soap_24: system_info
soap_9:service_package_info
soap_12:partner_info
*/

   public boolean bussiness(
            List<Soap8> $soap_8_extract1,
            List<Soap12> $soap_12_extract1,
            List<Soap34> $soap_34_extract1,
            String script_shop_id,
            String msisdn,
            String serviceid) {
        boolean sendSmsForSharing = false;
        String startTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        boolean stepResult = false;
        System.out.println(new

                SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "|AAAAAAAAAAA:" + msisdn + ",startTime:" + startTime);
        try {
            String retval = utilServices.getValueFromKeySOAP12($soap_12_extract1, "SEND_SMS");
            if (retval.equals("1")) sendSmsForSharing = true;
        } catch (
                Exception e
                ) {
        }

//kiem tra chinh sach chong loi dung
        boolean needCheck = false;

        try {
            String ret = "";
            try {
                List<PolicyDTO> lstPolicy = services.getDataCheckPolicy(script_shop_id, msisdn);
                //POLICY_TYPE_NAME
                String policyTypeName = getValueLoiDung(lstPolicy, "POLICY_TYPE_NAME");
                if (policyTypeName != null && !policyTypeName.equals("")) {
                    context.put("ErrorCodeAPI", "22");
                    context.put("ErrorDescAPI", "Diem ban khong duoc gioi thieu cho thue bao");
                    return false;
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                context.put("ErrorCodeAPI", "22");
                context.put("ErrorDescAPI", "Diem ban khong duoc gioi thieu cho thue bao");
                return false;
            }
            System.out.println(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "|AAAAAAAAAAA:" + msisdn);
            //check muc gia chung cho cac dich vu

                StringBuilder str_soap = new StringBuilder();
                str_soap.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:vms=\"http://vms.neo\">");
                str_soap.append("<soapenv:Header/><soapenv:Body>");
                str_soap.append("<vms:queryXml>");

                str_soap.append("<vms:Service>").append("checkLoiDungNotSendSms").append("</vms:Service>");
                str_soap.append("<vms:Provider>").append("default").append("</vms:Provider>");
                str_soap.append("<vms:ParamSize>").append("2").append("</vms:ParamSize>");

                str_soap.append("<vms:P8>").append(script_shop_id).append("</vms:P8>");
                str_soap.append("<vms:P10>").append(msisdn).append("</vms:P10>");

                str_soap.append("</vms:queryXml></soapenv:Body></soapenv:Envelope>");
                // fix data
                //ret = new Activation().parseXMLtext(new Activation().soapCall(context.get("dataflow_param:sqlmodule"), str_soap.toString()), "//*[local-name() = 'return']");
            System.out.println(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "|AAAAAAAAAAA:" + msisdn);
            String price = "0";
            String policyTypeName = "";
            try {
                try {
                   //getDataCheckPrice(String script_shop_id, String msisdn, String serviceid){
                    List<PolicyDTO> lstPolicy = services.getDataCheckPrice(script_shop_id, msisdn, serviceid);
                    // fix data
                    //price = getValueLoiDung(lstPolicy, "PRICE");
                    //policyTypeName = getValueLoiDung(lstPolicy, "POLICY_TYPE_NAME");
                    price = "price";
                    policyTypeName = "policyTypeName";

                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
                System.out.println("DAILV_DEBUG_PRICE:-----------------" + price);
                System.out.println("DAILV_DEBUG_POLICY_TYPE_NAME:-----------------" + policyTypeName);
                //co cau hinh chinh sach chong loi dung
                if (!policyTypeName.equals("")) {
                    if (Double.parseDouble(context.get("PACKAGE_PRICE")) <= Double.parseDouble(price)) {
                        context.put("ErrorCodeAPI", "22");
                        context.put("ErrorDescAPI", "Diem ban gioi thieu goi cuoc co gia thap hon gia thue bao da su dung");
                        if (sendSmsForSharing && context.get("channel").equals("SMS")) {
                            String mt = utilServices.getValueFromKeySOAP34($soap_34_extract1, "THONG_BAO_PRICE_HIGHER").replaceAll("\\{MSISDN}", String.valueOf(msisdn)).replaceAll("\\{SERVICE_KEY}", context.get("SERVICE_KEY")).replaceAll("\\{PACKAGE_CODE}", context.get("PACKAGE_CODE")).replaceAll("\\{PACKAGE_PRICE}", context.get("PACKAGE_PRICE"));
                            if (mt == null || mt.equals(""))
                                mt = utilServices.getValueFromKeySOAP8($soap_8_extract1, "THONG_BAO_PRICE_HIGHER").replaceAll("\\{MSISDN}", String.valueOf(msisdn)).replaceAll("\\{SERVICE_KEY}", context.get("SERVICE_KEY")).replaceAll("\\{PACKAGE_CODE}", context.get("PACKAGE_CODE")).replaceAll("\\{PACKAGE_PRICE}", context.get("PACKAGE_PRICE"));

                            // fix data
                            //ret = sendSms(context.get("sharingkey"), mt);

                        }
                        return false;
                    }
                }
                System.out.println(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "|AAAAAAAAAAA:" + msisdn);

            } catch (Exception ex) {
                System.out.println(ex.getMessage()); return true;
            }
            stepResult = true;
            System.out.println(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "|AAAAAAAAAAA:" + msisdn + ",startTime:" + startTime);
//logSql(msisdn,""-1"",""CHECK_CHONG_LOIDUNG"",""2"",""CHECK_CHONG_LOIDUNG"",""script_shop_id=$script_shop_id"",String.valueOf(stepResult),context.get(""ErrorCodeAPI"")+""|""+context.get(""ErrorDescAPI""),startTime,new SimpleDateFormat(""yyyyMMddHHmmss"").format(new Date()),""sharingkey=$sharingkey,serviceid=$serviceid,packagecode=$packagecode,channel=$channel,dataflow:registerService"",(String)$script_shop_id);
            return true;
        } catch (
                Exception ex
                ) {
            System.out.println(ex.getMessage()); return true;
        }
        finally {
            /*def logSql = {String msisdn, String transactionId, String stepName, int stepID,String stepKey,
    String stepInput,String stepOutput,String stepAction,String startTime, String endTime,String inputParameter ->*/
        }
    }
}
