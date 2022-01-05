package com.neo.msocial.groovy


import com.neo.msocial.dto.Soap14
import com.neo.msocial.dto.Soap16
import com.neo.msocial.dto.Soap17
import com.neo.msocial.dto.Soap19
import com.neo.msocial.dto.Soap34
import com.neo.msocial.dto.Soap12
import com.neo.msocial.dto.Soap15
import com.neo.msocial.dto.Soap8
import com.neo.msocial.service.Activation
import com.neo.msocial.utils.RedisUtils
import com.neo.msocial.utils.SystemParameterServices
import com.neo.msocial.utils.UtilServices
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.text.*

@Component
class CheckSpam {

    @Autowired
    private Activation activation;

    @Autowired
    private RedisUtils context;

    @Autowired
    private UtilServices utilServices;

    @Autowired
    private SystemParameterServices parameterServices;

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
<vms:P12>$scriptShopId</vms:P12>             </vms:updateXml>
       </soapenv:Body>
    </soapenv:Envelope>
    """;
        String result = activation.soapCall(context.get("dataflow_param:sqlmodule"), request);
    }

    def sendSms = { String receiver, String messageSms ->
        try {
            System.out.println(receiver + "|||" + messageSms);
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
           <vms:args2>$messageSms</vms:args2>
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
            String result = activation.soapCall(utilUrl, request);
            return result;
        } catch (Exception e) {
            return "-1|" + e.getMessage();
        }
    }
//do bussiness
/*
soap_8: mt_info
soap_24: system_info
soap_15:spam_info
soap_9:service_package_info
soap_12:partner_info
soap_14:trans_refused
soap_16:trans_wait
soap_17:trans_wait_per_service
soap_19:trans_refused_per_service
*/

    boolean checksendSms(List<Soap8> $soap_8_extract1,
                         List<Soap12> $soap_12_extract1,
                         List<Soap14> $soap_14_extract1,
                         List<Soap15> $soap_15_extract1,
                         List<Soap16> $soap_16_extract1,
                         List<Soap17> $soap_17_extract1,
                         List<Soap19> $soap_19_extract1,
                         List<Soap34> $soap_34_extract1,

                         String channel,
                         String sharing_key_id,
                         String msisdn

    ) {
        boolean sendSmsForSharing = false;
        String startTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        int noConfirm = Integer.parseInt(utilServices.getValueFromKeySOAP16($soap_16_extract1, "waitregistertotal".toUpperCase()));
        int no = Integer.parseInt(utilServices.getValueFromKeySOAP14($soap_14_extract1, "refusedtotal".toUpperCase()));
        int maxRefusedPerDay = 0;
        boolean stepResult = false;
        try {
            System.out.println("ccdcd:sdcdcccccccccccccccccc " + msisdn + ",noConfirm:" + noConfirm + ",no:" + no);
            //check co gui sms cho diem ban hay khong
            try {
                String retval = utilServices.getValueFromKeySOAP12($soap_12_extract1, "SEND_SMS");
                if (retval.equals("1")) sendSmsForSharing = true;
            } catch (Exception e) {
            }

            //check thue bao dang cho dich vu da gioi thieu cua diem ban
            if (Integer.parseInt(utilServices.getValueFromKeySOAP17($soap_17_extract1, "WAITPERSERVICETOTAL")) >= 1) {
                if (sendSmsForSharing && context.get("channel").equals("SMS")) {
                    //String mt = getValueFromKeyMultiRecords($soap_34_extract1, "record", "MT_TYPE_KEY", "MT_NOTICE_SHARING_PARTNER_WAIT_PER_SERVICE", "MT_TYPE_VALUE").replaceAll("\\{MSISDN}", String.valueOf($msisdn)).replaceAll("\\{SERVICE_KEY}", context.get("SERVICE_KEY"));
                    String mt = utilServices.getValueFromKeySOAP34($soap_34_extract1, "MT_NOTICE_SHARING_PARTNER_WAIT_PER_SERVICE").replaceAll("\\{MSISDN}", String.valueOf(msisdn)).replaceAll("\\{SERVICE_KEY}", context.get("SERVICE_KEY"));
                    if (mt == null || mt.equals(""))
                        mt = utilServices.getValueFromKeySOAP8($soap_8_extract1,"MT_NOTICE_SHARING_PARTNER_WAIT_PER_SERVICE").replaceAll("\\{MSISDN}", String.valueOf(msisdn)).replaceAll("\\{SERVICE_KEY}", context.get("SERVICE_KEY"));
                    String ret = sendSms(context.get("sharingkey"), mt);
                }
                context.put("ErrorCodeAPI", "40");
                context.put("ErrorDescAPI", "MT_NOTICE_SHARING_PARTNER_WAIT_PER_SERVICE");
                return false;
            }

            //check thue bao da tu choi dich vu da gioi thieu cua diem ban
            if (Integer.parseInt(utilServices.getValueFromKeySOAP19($soap_19_extract1, "REFUSEDPERSERVICETOTAL")) >= 1) {
                if (sendSmsForSharing && context.get("channel").equals("SMS")) {
                    String mt = utilServices.getValueFromKeySOAP34($soap_34_extract1, "MT_NOTICE_SHARING_PARTNER_REFUSED_PER_SERVICE").replaceAll("\\{MSISDN}", String.valueOf(msisdn)).replaceAll("\\{SERVICE_KEY}", context.get("SERVICE_KEY"));
                    if (mt == null || mt.equals(""))
                        mt = utilServices.getValueFromKeySOAP8($soap_8_extract1,"MT_NOTICE_SHARING_PARTNER_REFUSED_PER_SERVICE").replaceAll("\\{MSISDN}", String.valueOf(msisdn)).replaceAll("\\{SERVICE_KEY}", context.get("SERVICE_KEY"));
                    String ret = sendSms(context.get("sharingkey"), mt);
                }
                context.put("ErrorCodeAPI", "41");
                context.put("ErrorDescAPI", "MT_NOTICE_SHARING_PARTNER_REFUSED_PER_SERVICE");
                return false;
            }


            try {
                maxRefusedPerDay = Integer.parseInt(utilServices.getValueFromKeySOAP15($soap_15_extract1,"TRANS_REFUSED_PER_DAY","SPAM_TEMPLATE_VALUE"));
                System.out.println("msisdn:" + msisdn + "," + maxRefusedPerDay);
            } catch (Exception e) {
            }
            int maxFaildPerDay = 0;
            try {
                maxFaildPerDay = Integer.parseInt(utilServices.getValueFromKeySOAP15($soap_15_extract1, "TRANS_FAILED_PER_DAY","SPAM_TEMPLATE_VALUE"));
                System.out.println("msisdn:" + msisdn + "," + maxFaildPerDay);
            } catch (Exception e) {
                maxFaildPerDay = 1000000;
            }
            if (no >= maxRefusedPerDay || noConfirm >= maxFaildPerDay || (no + noConfirm) >= maxFaildPerDay) {
                System.out.println("msisdn:" + msisdn + ",maxRefusedPerDay:" + maxRefusedPerDay + ",no=" + no + ",maxFaildPerDay=" + maxFaildPerDay + ",noConfirm:" + noConfirm);
                //do action
                String actionType = utilServices.getValueFromKeySOAP15($soap_15_extract1, "TRANS_FAILED_PER_DAY", "ACTION_TYPE");
                if (actionType.equals("LOCK_DB")) {

                    String ret = parameterServices.getDataCheckLockDb(sharing_key_id);
                    if (ret.equals("1")) {
                        if (sendSmsForSharing && context.get("channel").equals("SMS")) {
                            if (no >= maxRefusedPerDay) {
                                String mt = utilServices.getValueFromKeySOAP34($soap_34_extract1, "REFUSED_GIOITHIEU",).replaceAll("\\{MSISDN}", String.valueOf(msisdn)).replaceAll("\\{SERVICE_KEY}", context.get("SERVICE_KEY").replaceAll("\\{REFUSED_TOTAL}", no));
                                if (mt == null || mt.equals(""))
                                    mt = utilServices.getValueFromKeySOAP8($soap_8_extract1, "REFUSED_GIOITHIEU").replaceAll("\\{MSISDN}", String.valueOf(msisdn)).replaceAll("\\{SERVICE_KEY}", context.get("SERVICE_KEY").replaceAll("\\{REFUSED_TOTAL}", no));
                                ret = sendSms(context.get("sharingkey"), mt);
                            } else if (noConfirm >= maxFaildPerDay || (no + noConfirm) >= maxFaildPerDay) {
                                String mt = utilServices.getValueFromKeySOAP34($soap_34_extract1, "LOCK_DIEM_BAN").replaceAll("\\{MSISDN}", String.valueOf(msisdn)).replaceAll("\\{SERVICE_KEY}", context.get("SERVICE_KEY"));
                                if (mt == null || mt.equals(""))
                                    mt = utilServices.getValueFromKeySOAP8($soap_8_extract1, "LOCK_DIEM_BAN").replaceAll("\\{MSISDN}", String.valueOf(msisdn)).replaceAll("\\{SERVICE_KEY}", context.get("SERVICE_KEY"));

                                ret = sendSms(context.get("sharingkey"), mt);
                            }
                        }
                    }
                }
                context.put("ErrorCodeAPI", "49"); context.put("ErrorDescAPI", "SPAM_VIOLET");
                return false;
            } else {
                if ((no + noConfirm) == (maxFaildPerDay - 1) || (no + noConfirm) == (maxRefusedPerDay - 1)) {
                    if (sendSmsForSharing && context.get("channel").equals("SMS")) {
                        if ((no + noConfirm) == (maxFaildPerDay - 1)) {
                            String mt = utilServices.getValueFromKeySOAP34($soap_34_extract1,"TRANSACTION_FAILED").replaceAll("\\{TRANSACTION_FAILED}", String.valueOf(no + noConfirm));
                            if (mt == null || mt.equals(""))
                                mt = utilServices.getValueFromKeySOAP8($soap_8_extract1,"TRANSACTION_FAILED").replaceAll("\\{TRANSACTION_FAILED}", String.valueOf(no + noConfirm));
                            String ret = sendSms(context.get("sharingkey"), mt);
                        } else {
                            String mt = utilServices.getValueFromKeySOAP8($soap_8_extract1, "TRANSACTION_FAILED").replaceAll("\\{TRANSACTION_FAILED}", String.valueOf((no + noConfirm)));
                            String ret = sendSms(context.get("sharingkey"), mt);
                        }
                    }
                }
            }
            //check max sms per day

//            System.out.println("checking sms per day....");
//            boolean needCheck = false;
//            String info = utilServices.getValueFromKeySOAP15($soap_15_extract1, "MAX_SMS", "ACTION_TYPE");
//            try {
//                int maxSms = Integer.parseInt(info);
//                String request = """
//  <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:vms="http://vms.neo">
//     <soapenv:Header/>
//     <soapenv:Body>
//        <vms:updateXml>
//           <!--Optional:-->
//           <vms:Service>total_sms</vms:Service>
//           <!--Optional:-->
//           <vms:Provider>default</vms:Provider>
//           <!--Optional:-->
//           <vms:ParamSize>1</vms:ParamSize>
//           <!--Optional:-->
//           <vms:P1>$channel</vms:P1>
//        </vms:updateXml>
//     </soapenv:Body>
//  </soapenv:Envelope>
//  """;
//
//                String ret = activation.soapCall(context.get("dataflow_param:sqlmodule"), request);
//                if (Integer.parseInt(getValueFromKey(activation.parseXMLtext(ret, "//*[local-name() = 'return']"), "TOTAL_SMS")) >= maxSms) {
//                    context.put("ErrorCodeAPI", "21");
//                    context.put("ErrorDescAPI", "Diem ban vuot qua gioi han " + maxSms + " gui tin qua kenh $channel");
//                    return false;
//                }
//            } catch (Exception e) {
//                System.out.println(e.getMessage());
//            }
//            stepResult = true;
            //logSql(msisdn,"-1","CHECK_SPAM_POLICY","1","CHECK_SPAM_POLICY","script_shop_id=$script_shop_id|no="+no+",noConfirm="+noConfirm,String.valueOf(stepResult),context.get("ErrorCodeAPI")+"|"+context.get("ErrorDescAPI"),startTime,new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()),"sharingkey=$sharingkey,serviceid=$serviceid,packagecode=$packagecode,channel=$channel,dataflow:registerService",(String)$script_shop_id);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        } finally {
            /*def logSql = {String msisdn, String transactionId, String stepName, int stepID,String stepKey,
        String stepInput,String stepOutput,String stepAction,String startTime, String endTime,String inputParameter ->*/
        }

    }
}
