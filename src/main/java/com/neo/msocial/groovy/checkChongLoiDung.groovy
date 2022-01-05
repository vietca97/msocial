package com.neo.msocial.groovy

import com.neo.msocial.dto.PolicyDTO
import com.neo.msocial.dto.Soap12
import com.neo.msocial.dto.Soap34
import com.neo.msocial.dto.Soap8
import com.neo.msocial.service.Activation
import com.neo.msocial.service.ParseXml
import com.neo.msocial.utils.RedisUtils
import com.neo.msocial.utils.SystemParameterServices
import com.neo.msocial.utils.UtilServices
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.text.*;

@Component
class CheckChongLoiDung {

    @Autowired
    private RedisUtils context;

    @Autowired
    private UtilServices utilServices;

    @Autowired
    private SystemParameterServices services;

    def logSql = { String msisdn, String transactionId, String stepName, String stepIndex, String stepKey, String stepInput, String stepOutput, String stepAction, String startTime, String endTime, String inputParameter, String scriptShopId ->
        String request = """
    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:vms="http://vms.neo">
       <soapenv:Header/>
       <soapenv:Body>
          <vms:updateXml>
             <!--Optional:-->
             <vms:Service>sql_log_transaction_step</vms:Service>
             <!--Optional:-->
             <vms:Provider>default</vms:Provider>
             <!--Optional:-->
            <vms:ParamSize>12</vms:ParamSize>
             <!--Optional:-->
             <vms:P1>$transactionId</vms:P1>
             <!--Optional:-->
             <vms:P2>$stepName</vms:P2>
             <!--Optional:-->
             <vms:P3>$stepIndex</vms:P3>
             <!--Optional:-->
             <vms:P4>$stepKey</vms:P4>
             <!--Optional:-->
             <vms:P5>$stepInput</vms:P5>
             <!--Optional:-->
             <vms:P6>$stepOutput</vms:P6>
             <!--Optional:-->
             <vms:P7>$stepAction</vms:P7>
             <!--Optional:-->
             <vms:P8>$startTime</vms:P8>
             <!--Optional:-->
             <vms:P9>$endTime</vms:P9>
             <!--Optional:-->
             <vms:P10>$msisdn</vms:P10>
             <!--Optional:-->
             <vms:P11>$inputParameter</vms:P11>      
             <vms:P12>$scriptShopId</vms:P12>   
          </vms:updateXml>
       </soapenv:Body>
    </soapenv:Envelope>
    """;
        String result = new Activation().soapCall(context.get("dataflow_param:sqlmodule"), request);

    }
    def sendSms = { String receiver, String content ->
        try {
            receiver = String.valueOf(receiver);
            String serviceNumber = context.get("SERVICE_NUMBER");
            String smsHost = context.get("SMS_HOST");
            String smsPort = context.get("SMS_PORT");
            String smsLookup = context.get("SMS_LOOKUP");
            String utilUrl = context.get("dataflow_param:utilmodule");
            String request = """
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:vms="http://vms.neo">
   <soapenv:Header/>
   <soapenv:Body>
      <vms:sendSms>
         <!--Optional:-->
         <vms:args0>$serviceNumber</vms:args0>
         <!--Optional:-->
         <vms:args1>$receiver</vms:args1>
         <!--Optional:-->
         <vms:args2>$content</vms:args2>
         <!--Optional:-->
         <vms:args3>$smsHost</vms:args3>
         <!--Optional:-->
         <vms:args4>$smsPort</vms:args4>
         <!--Optional:-->
         <vms:args5>$smsLookup</vms:args5>
      </vms:sendSms>
   </soapenv:Body>
</soapenv:Envelope>
"""
            String result = new Activation().soapCall(utilUrl, request);
            return result;
        } catch (Exception e) {
            return "-1|" + e.getMessage();
        }
    }
    def getValueFromKey = { String body, String key ->
        String ret = "";
        try {
            def rootNode = new XmlSlurper().parseText(body);
            for (def record : rootNode.record.children()) {
                if (record.name().equals(key)) {
                    ret = record.text();
                    break;
                }
            }

        } catch (Exception ex) {
            //ex.printStackTrace();
            ret = "";
        }
        return ret;
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


    def getValueFromKeyMultiRecords = { String body, String rootTag, String tagName, String key, String value ->
        String ret = new ParseXml().getValueFromKey(body, rootTag, tagName, key, value);

        return ret;
    }
//do bussiness
/*
soap_8: mt_info
soap_24: system_info
soap_9:service_package_info
soap_12:partner_info
*/

    boolean bussiness(
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

            ret = new Activation().parseXMLtext(new Activation().soapCall(context.get("dataflow_param:sqlmodule"), request), "//*[local-name() = 'return']");
            System.out.println(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "|AAAAAAAAAAA:" + msisdn);
            String price = "0";
            policyTypeName = "";
            try {
                try {
                   //getDataCheckPrice(String script_shop_id, String msisdn, String serviceid){
                    List<PolicyDTO> lstPolicy = services.getDataCheckPrice(script_shop_id, msisdn, serviceid);
                    price = getValueLoiDung(lstPolicy, "PRICE");
                    policyTypeName = getValueLoiDung(lstPolicy, "POLICY_TYPE_NAME");
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
                                mt = utilServices.getValueFromKeySOAP8($soap_8_extract1, "THONG_BAO_PRICE_HIGHER",).replaceAll("\\{MSISDN}", String.valueOf(msisdn)).replaceAll("\\{SERVICE_KEY}", context.get("SERVICE_KEY")).replaceAll("\\{PACKAGE_CODE}", context.get("PACKAGE_CODE")).replaceAll("\\{PACKAGE_PRICE}", context.get("PACKAGE_PRICE"));
                            ret = sendSms(context.get("sharingkey"), mt);

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
