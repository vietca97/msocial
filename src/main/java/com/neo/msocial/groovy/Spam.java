package com.neo.msocial.groovy;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.neo.msocial.constant.RequestUrl;
import com.neo.msocial.dto.PolicyDTO;
import com.neo.msocial.dto.SmsPerDayDTO;
import com.neo.msocial.dto.Soap14;
import com.neo.msocial.dto.Soap16;
import com.neo.msocial.dto.Soap17;
import com.neo.msocial.dto.Soap19;
import com.neo.msocial.dto.Soap34;
import com.neo.msocial.dto.Soap12;
import com.neo.msocial.dto.Soap15;
import com.neo.msocial.dto.Soap8;
import com.neo.msocial.service.Activation;
import com.neo.msocial.utils.GenericsRequest;
import com.neo.msocial.utils.RedisUtils;
import com.neo.msocial.utils.SystemParameterServices;
import com.neo.msocial.utils.UtilServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Spam {

    @Autowired
    private Activation activation;

    @Autowired
    private RedisUtils context;

    @Autowired
    private UtilServices utilServices;

    @Autowired
    private SystemParameterServices parameterServices;

    @Autowired
    private GenericsRequest<SmsPerDayDTO> smsPerDayDTOGenericsRequest;

    String logSql (String msisdn, String transactionId, String stepName, String stepIndex, String stepKey, String stepInput, String stepOutput, String stepAction, String startTime, String endTime, String inputParameter, String scriptShopId ){
        StringBuilder str_soap = new StringBuilder();
        str_soap.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:vms=\"http://vms.neo\">");
        str_soap.append("<soapenv:Header/><soapenv:Body>");
        str_soap.append("<vms:updateXml>");

        str_soap.append("<vms:Service>").append("sql_log_transaction_step").append("</vms:Service>");
        str_soap.append("<vms:Provider>").append("default").append("</vms:Provider>");
        str_soap.append("<vms:ParamSize>").append("11").append("</vms:ParamSize>");

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


        str_soap.append("</vms:updateXml></soapenv:Body></soapenv:Envelope>");


        String result = activation.soapCall(context.get("dataflow_param:sqlmodule"), str_soap.toString());
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
            String result = activation.soapCall(utilUrl, str_soap.toString());
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

    public boolean checksendSms(List<Soap8> $soap_8_extract1,
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
                    // fix data
                    //String ret = sendSms(context.get("sharingkey"), mt);
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
                if ("LOCK_DB".equals(actionType)) {

                    String ret = parameterServices.getDataCheckLockDb(sharing_key_id);
                    if (ret.equals("1")) {
                        if (sendSmsForSharing && context.get("channel").equals("SMS")) {
                            if (no >= maxRefusedPerDay) {
                                String mt = utilServices.getValueFromKeySOAP34($soap_34_extract1, "REFUSED_GIOITHIEU").replaceAll("\\{MSISDN}", String.valueOf(msisdn)).replaceAll("\\{SERVICE_KEY}", context.get("SERVICE_KEY").replaceAll("\\{REFUSED_TOTAL}", String.valueOf(no)));
                                if (mt == null || mt.equals("")){
                                    mt = utilServices.getValueFromKeySOAP8($soap_8_extract1, "REFUSED_GIOITHIEU").replaceAll("\\{MSISDN}", String.valueOf(msisdn)).replaceAll("\\{SERVICE_KEY}", context.get("SERVICE_KEY").replaceAll("\\{REFUSED_TOTAL}", String.valueOf(no)));
                                    ret = sendSms(context.get("sharingkey"), mt);
                                }

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
                            // fix data
                            // String ret = sendSms(context.get("sharingkey"), mt);
                        } else {
                            String mt = utilServices.getValueFromKeySOAP8($soap_8_extract1, "TRANSACTION_FAILED").replaceAll("\\{TRANSACTION_FAILED}", String.valueOf((no + noConfirm)));
                            // fix data
                            //String ret = sendSms(context.get("sharingkey"), mt);
                        }
                    }
                }
            }
            //check max sms per day

            System.out.println("checking sms per day....");
            boolean needCheck = false;
            // fix data
            //String info = utilServices.getValueFromKeySOAP15($soap_15_extract1, "MAX_SMS", "ACTION_TYPE");
            String info = "0";
            try {
                int maxSms = Integer.parseInt(info);

                Map<String,String> params = new HashMap<>();
                params.put("typeQuery","query");
                params.put("Service","total_sms");
                params.put("Provider","default");
                params.put("ParamSize","1");
                params.put("P1",channel);
                List<SmsPerDayDTO> lstSmsPerDay = smsPerDayDTOGenericsRequest.getData(params);
                // fix data
                SmsPerDayDTO sms = new SmsPerDayDTO();
                sms.setTOTAL_SMS("-10");
                lstSmsPerDay.add(sms);

                if(lstSmsPerDay.get(0) != null && lstSmsPerDay.get(0).getTOTAL_SMS() != null && Integer.parseInt(lstSmsPerDay.get(0).getTOTAL_SMS()) >= maxSms){
                    context.put("ErrorCodeAPI", "21");
                    context.put("ErrorDescAPI", "Diem ban vuot qua gioi han " + maxSms + " gui tin qua kenh " + channel);
                    return false;
                }
                //if (Integer.parseInt(getValueFromKey(activation.parseXMLtext(ret, "//*[local-name() = 'return']"), "TOTAL_SMS")) >= maxSms) {
                //    context.put("ErrorCodeAPI", "21");
               //     context.put("ErrorDescAPI", "Diem ban vuot qua gioi han " + maxSms + " gui tin qua kenh $channel");
                //    return false;
               // }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            //stepResult = true;
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
