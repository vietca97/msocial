package com.neo.msocial.groovy

import com.neo.msocial.dto.Soap34
import com.neo.msocial.dto.Soap8
import com.neo.msocial.service.Activation
import com.neo.msocial.service.ParseXml
import com.neo.msocial.utils.RedisUtils
import com.neo.msocial.utils.UtilServices
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.text.SimpleDateFormat

@Component
class CheckThueBaoSuDungDichVu {

    @Autowired
    private RedisUtils context;

    @Autowired
    private UtilServices utilServices;

    def logSql = {String msisdn, String transactionId, String stepName, String stepIndex,String stepKey,String stepInput,String stepOutput,String stepAction,String startTime, String endTime,String inputParameter,String scriptShopId ->
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
        String result = new Activation().soapCall(context.get("dataflow_param:sqlmodule"),request);

    }
    def sendSms = {String receiver, String message_sms ->
        try{
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
         <vms:args2>$message_sms</vms:args2>
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
            String result = new Activation().soapCall(utilUrl,request);
            return result;
        }catch(Exception e){
            return "-1|"+e.getMessage();
        }
    }
    def getValueFromKey = {String body, String key ->
        String ret = "";
        try{
            def rootNode = new XmlSlurper().parseText(body);
            for(def record : rootNode.record.children()){
                if(record.name().equals(key)){
                    ret = record.text();
                    break;
                }
            }

        }catch(Exception ex){
            ex.printStackTrace();
            ret = "";
        }
        return ret;
    }
    def getValueFromKeyMultiRecords = {String body, String rootTag,String tagName,String key,String value ->
        String ret = new ParseXml().getValueFromKey(body,rootTag,tagName,key,value);

        return ret;
    }
//do bussiness
/*
soap_8: mt_info
soap_24: system_info
soap_9:service_package_info
soap_12:partner_info
*/
//check thue bao da su dung dich vu hay chua
    boolean checkThueBao(
            List<Soap34> $soap_34_extract1,
            List<Soap8> $soap_8_extract1,
            String $script_shop_id,
            String sharingkey,
            String $msisdn,
            String packagecode,
            String channel
    ) {
        boolean check = false;
        String startTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        boolean stepResult = false;

        try {
            String request = """
 <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:vms="http://vms.neo">
    <soapenv:Header/>
    <soapenv:Body>
       <vms:checkUsedService>
          <!--Optional:-->
          <vms:scriptShopId>$script_shop_id</vms:scriptShopId>
          <!--Optional:-->
          <vms:sharing_key>$sharingkey</vms:sharing_key>
          <!--Optional:-->
          <vms:msisdn>$msisdn</vms:msisdn>
          <vms:packagecode>$packagecode</vms:packagecode>
          <vms:channelkey>$channel</vms:channelkey>
       </vms:checkUsedService>
    </soapenv:Body>
 </soapenv:Envelope>
 """;
            String ret = "";
            try {
                ret = new Activation().parseXMLtext(new Activation().soapCall(context.get("dataflow_param:utilmodule"), request), "//*[local-name() = 'return']");
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                //return true;
                stepResult = false;
            }
            int cmdResultId = -1;
            try {
                def rootNode = new XmlSlurper().parseText(ret);
                String errorCode = rootNode.Parameter.ErrorCode.text();
                String errorDesc = rootNode.Parameter.ErrorDesc.text();

                if (!errorCode.equals("0")) {
                    context.put("ErrorCodeAPI", errorCode);
                    context.put("ErrorDescAPI", errorDesc);
                    //return false;
                    stepResult = false;
                } else {
                    //success
                    try {
                        request = rootNode.Parameter.cmdRequest_with_value.text();
                        String response = rootNode.Parameter.cmdresponse_with_value.text();
                        System.out.println("checkservice_response:" + response);
                        context.put("checkservice_" + rootNode.Parameter.cmdRequest_with_value.name(), rootNode.Parameter.cmdRequest_with_value.text());
                        context.put("checkservice_" + rootNode.Parameter.cmdresponse_with_value.name(), rootNode.Parameter.cmdresponse_with_value.text());
                        try {
                            cmdResultId = Integer.parseInt(rootNode.record.COMMANDS_RESULTS_ID.text());
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        if (cmdResultId >= 0) {
                            String cmdStatus = rootNode.record.COMMANDS_STATUS.text();
                            System.out.println(cmdStatus);
                            rootNode.record.children().each {
                                context.put(it.name(), it.text());
                            }
                            if (context.get("SERVICE_KEY_SMS").equals("NHA_NONG_XANH")) {
                                if (rootNode.Parameter.cmdresponse_with_value.text().indexOf(context.get("PACKAGE_CODE")) >= 0)
                                    cmdStatus = "INUSE";
                                else cmdStatus = "UNUSE";
                            }
                            if (cmdStatus.equals("INUSE")) {
                                context.put("ErrorCodeAPI", "23");
                                context.put("ErrorDescAPI", "Thue bao dang su dung dich vu");
                                if (context.get("SEND_SMS").equals("1") && context.get("channel").equals("SMS")) {
                                    String mt = utilServices.getValueFromKeySOAP34($soap_34_extract1,"SERVICE_IN_USE").replaceAll("\\{MSISDN}", String.valueOf($msisdn)).replaceAll("\\{SERVICE_KEY}", context.get("SERVICE_KEY_SMS"));
                                    if (mt == null || mt.equals(""))
                                        mt = utilServices.getValueFromKeySOAP8($soap_8_extract1, "SERVICE_IN_USE").replaceAll("\\{MSISDN}", String.valueOf($msisdn)).replaceAll("\\{SERVICE_KEY}", context.get("SERVICE_KEY_SMS"));
                                    ret = sendSms(context.get("sharingkey"), mt);
                                    System.out.println("ret:" + ret);
                                }
                                stepResult = false;
                                //return false;
                            } else if (cmdStatus.equals("ERROR")) {
                                context.put("ErrorCodeAPI", "23");
                                context.put("ErrorDescAPI", "Thue bao dang su dung dich vu");
                                if (context.get("SEND_SMS").equals("1") && context.get("channel").equals("SMS")) {
                                    String mt = utilServices.getValueFromKeySOAP34($soap_34_extract1, "SERVICE_IN_USE").replaceAll("\\{MSISDN}", String.valueOf($msisdn)).replaceAll("\\{SERVICE_KEY}", context.get("SERVICE_KEY_SMS"));
                                    if (mt == null || mt.equals(""))
                                        mt = utilServices.getValueFromKeySOAP8($soap_8_extract1,"SERVICE_IN_USE").replaceAll("\\{MSISDN}", String.valueOf($msisdn)).replaceAll("\\{SERVICE_KEY}", context.get("SERVICE_KEY_SMS"));
                                    ret = sendSms(context.get("sharingkey"), mt);
                                    System.out.println("ret:" + ret);
                                }
                                stepResult = false;
                                //return false;
                            } else {
                                //return true;
                                stepResult = true;
                            }

                        } else {
                            System.out.println("Process manually...");
                            if (response.startsWith("-1|")) {
                                context.put("ErrorCodeAPI", "-1");
                                context.put("ErrorDescAPI", "He thong khong tra cuu duoc thong tin thue bao");

                                //return false;
                                stepResult = false;
                            } else
                            //return false;
                                stepResult = false;
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        stepResult = false;
                    }

                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
                //return false;
                stepResult = false;
            }
            //stepResult = true;

            //return true;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        finally {

            logSql(msisdn, "-1", "CHECK_THUE_BAO_SU_DUNG_DICH_VU", "3", "CHECK_THUE_BAO_SU_DUNG_DICH_VU", context.get("checkservice_cmdRequest_with_value"), context.get("checkservice_cmdresponse_with_value") + "|stepResult=" + stepResult, context.get("ErrorCodeAPI") + "|" + context.get("ErrorDescAPI"), startTime, new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()), "sharingkey=$sharingkey,serviceid=$serviceid,packagecode=$packagecode,channel=$channel,dataflow:registerService", (String) $script_shop_id);
            return stepResult;
        }
    }
}