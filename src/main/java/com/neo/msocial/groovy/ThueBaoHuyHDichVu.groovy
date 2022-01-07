package com.neo.msocial.groovy

import com.neo.msocial.service.Activation
import com.neo.msocial.service.ParseXml
import com.neo.msocial.utils.RedisUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.text.*;
import java.util.*;

@Component
class ThuebaoHuyDichVu {
    @Autowired
    private RedisUtils context;

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
        System.out.println(result);
    }
    def sendSms = {String receiver, String message ->
        try{
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
           <vms:args2>$message</vms:args2>
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
            System.out.println(request);
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
        System.out.println("RET:"+ret);
        return ret;
    }
//do bussiness
/*
soap_8: mt_info
soap_24: system_info
soap_9:service_package_info
soap_12:partner_info
*/
//check thue bao huy
    Boolean checkThueBaoHuy() {
        boolean check = false;
        String startTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        boolean stepResult = true;
        String serviceType = context.get("LIKE_MI_OR_VAS");
        String numberDayHuy = context.get("SO_NGAY_CHECKHUY");
        if (numberDayHuy == null || numberDayHuy.equals("")) numberDayHuy = "30";
//System.out.println("1222222222222222222222222222222222222-22222222222222222222 "+numberDayHuy+"-------:::"+context.get("SO_NGAY_CHECKHUY"));

        String HAVE_CHECK_HUY = context.get("HAVE_CHECK_HUY");
        try {
            if (HAVE_CHECK_HUY != null && HAVE_CHECK_HUY.equals("0")) {
                stepResult = true;
            } else {
                if (context.get("SERVICE_KEY").equals("NHAC_CHO_FUNRING") || context.get("SERVICE_KEY").equals("ZING_TANG_NHACCHO") || context.get("isservice").equals("true") || context.get("HAVE_CHECK_HUY").equals("0"))
                    stepResult = true;
                else {
                    String request = """
   <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:vms="http://vms.neo">
      <soapenv:Header/>
      <soapenv:Body>
      <vms:checkHuyNew>
      <!--Optional:-->
      <vms:scriptShopId>$script_shop_id</vms:scriptShopId>
      <!--Optional:-->
      <vms:msisdn>$msisdn</vms:msisdn>
      <!--Optional:-->
      <vms:sharingkey>$sharingkey</vms:sharingkey>
      <!--Optional:-->
      <vms:packagecode>$packagecode</vms:packagecode>
      <vms:channelkey>$channel</vms:channelkey>
     <vms:servicetype>$serviceType</vms:servicetype>
     <vms:numberdayhuy>$numberDayHuy</vms:numberdayhuy>
      </vms:checkHuyNew>
      </soapenv:Body>
   </soapenv:Envelope>
   """;
                    System.out.println("REQUEST---1:" + request);
                    String ret = "";
                    try {
                        ret = new Activation().parseXMLtext(new Activation().soapCall(context.get("dataflow_param:utilmodule"), request), "//*[local-name() = 'return']");
                        System.out.println("---ret:" + ret + "-----");
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                        stepResult = true;
                    }
                    try {
                        def rootNode = new XmlSlurper().parseText(ret);
                        String errorCode = rootNode.Parameter.ErrorCode.text();
                        String errorDesc = rootNode.Parameter.ErrorDesc.text();

                        System.out.println("ErrorCode:" + errorCode);
                        if (!errorCode.equals("0")) {
                            context.put("ErrorCodeAPI", errorCode);
                            context.put("ErrorDescAPI", errorDesc);
                            stepResult = false;
                        } else {
                            //success
                            try {
                                request = rootNode.Parameter.cmdRequest_with_value.text();
                                String response = rootNode.Parameter.cmdresponse_with_value.text();
                                context.put("checkhuy_" + rootNode.Parameter.cmdRequest_with_value.name(), rootNode.Parameter.cmdRequest_with_value.text());
                                context.put("checkhuy_" + rootNode.Parameter.cmdresponse_with_value.name(), rootNode.Parameter.cmdresponse_with_value.text());
                                context.put(rootNode.Parameter.check_huy_status.name(), rootNode.Parameter.check_huy_status.text());
                                String checkHuyStatus = rootNode.Parameter.check_huy_status.text();
                                System.out.println(checkHuyStatus);
                                context.put("check_huy_status", checkHuyStatus);
                                if (response.startsWith("-1000|")) {
                                    context.put("ErrorCodeAPI", "-1");
                                    context.put("ErrorCodeDesc", "Loi khong tra cuu duoc lich su huy cua thue bao");
                                    stepResult = false;
                                }
                                if (checkHuyStatus.equals("1")) {
                                    context.put("ErrorCodeAPI", "24");
                                    context.put("ErrorDescAPI", "Thue bao co lich su huy trong 30 ngay");
                                    if (context.get("SEND_SMS").equals("1")) {
                                        String mt = getValueFromKeyMultiRecords($soap_31_extract1, "record", "MT_TYPE_KEY", "HUY_DICH_VU_30NGAY", "MT_TYPE_VALUE").replaceAll("\\{MSISDN}", String.valueOf($msisdn)).replaceAll("\\{SERVICE_KEY}", context.get("SERVICE_KEY_SMS")).replaceAll("\\{SERVICE_NAME}", context.get("SERVICE_NAME_SMS"));
                                        if (mt == null || mt.equals(""))
                                            mt = getValueFromKeyMultiRecords($soap_8_extract1, "record", "MT_TYPE_KEY", "HUY_DICH_VU_30NGAY", "MT_TYPE_VALUE").replaceAll("\\{MSISDN}", String.valueOf($msisdn)).replaceAll("\\{SERVICE_KEY}", context.get("SERVICE_KEY_SMS")).replaceAll("\\{SERVICE_NAME}", context.get("SERVICE_NAME_SMS"));
                                        System.out.println("MT:--" + mt);
                                        ret = sendSms(context.get("sharingkey"), mt);
                                        System.out.println("ret:" + ret);
                                    }
                                    stepResult = false;
                                } else if (checkHuyStatus.equals("0")) {
                                    stepResult = true;
                                } else {
                                    context.put("ErrorCodeAPI", "33");
                                    context.put("ErrorDescAPI", "Khong tra cuu duoc thong tin huy dich vu cua thue bao.1" + checkHuyStatus);
                                    stepResult = false;
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                context.put("ErrorCodeAPI", "33");
                                context.put("ErrorDescAPI", "Khong tra cuu duoc thong tin huy dich vu cua thue bao.2");

                            }

                        }
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                        stepResult = false;
                    }
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            context.put("ErrorCodeAPI", "33");
            context.put("ErrorDescAPI", "Khong tra cuu duoc thong tin huy dich vu cua thue bao.3");
            stepResult = false;
        }
        finally {
            /*def logSql = {String msisdn, String transactionId, String stepName, int stepID,String stepKey,
        String stepInput,String stepOutput,String stepAction,String startTime, String endTime,String inputParameter ->*/
            logSql(msisdn, "-1", "CHECK_THUE_BAO_HUY_DICH_VU", "4", "CHECK_THUE_BAO_HUY_DICH_VU", context.get("checkhuy_cmdRequest_with_value"), context.get("checkhuy_cmdresponse_with_value") + "|stepResult=" + stepResult, context.get("ErrorCodeAPI") + "|" + context.get("ErrorDescAPI"), startTime, new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()), "sharingkey=$sharingkey,serviceid=$serviceid,packagecode=$packagecode,channel=$channel", (String) $script_shop_id);
            return stepResult;
        }
    }

}