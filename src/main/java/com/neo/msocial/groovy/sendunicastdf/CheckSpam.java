package com.neo.msocial.groovy.sendunicastdf;

import com.neo.msocial.dto.*;
import com.neo.msocial.service.Activation;
import com.neo.msocial.service.ParseXml;
import com.neo.msocial.utils.GenericsRequest;
import com.neo.msocial.utils.RedisUtils;
import com.neo.msocial.utils.SystemParameterServices;
import com.neo.msocial.utils.UtilServices;
import groovy.lang.Closure;
import groovy.util.XmlSlurper;
import groovy.util.slurpersupport.GPathResult;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.codehaus.groovy.runtime.StringGroovyMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CheckSpam {
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
    @Autowired
    private ParseXml parseXml;
    public String logSql(String msisdn, String transactionId, String stepName, String stepIndex, String stepKey, String stepInput, String stepOutput, String stepAction, String startTime, String endTime, String inputParameter, String scriptShopId) {
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
        str_soap.append("<vms:P12>").append(scriptShopId).append("</vms:P12>");

        str_soap.append("</vms:updateXml></soapenv:Body></soapenv:Envelope>");
        String result = activation.soapCall(context.get("dataflow_param:sqlmodule"), str_soap.toString());
        return result;
    }

    public String sendSms(String receiver, String messageSms) {
        try {
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

    public boolean checkSendSms(List<Soap8> $soap_8_extract1, List<Soap12> $soap_12_extract1, List<Soap14> $soap_14_extract1, List<Soap15> $soap_15_extract1, List<Soap16> $soap_16_extract1, List<Soap17> $soap_17_extract1, List<Soap19> $soap_19_extract1, List<Soap34> $soap_34_extract1, String channel, String sharing_key_id, String msisdn) {
        System.out.println($soap_16_extract1);
        System.out.println($soap_15_extract1);
        System.out.println($soap_14_extract1);
        boolean sendSmsForSharing = false;
        String startTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        //int noConfirm = Integer.parseInt(getValueFromKey($soap_16_extract1,"waitregistertotal".toUpperCase()));
        int noConfirm = Integer.parseInt($soap_16_extract1.get(0).getWAITREGISTERTOTAL());
        //int no = Integer.parseInt(getValueFromKey($soap_14_extract1,"refusedtotal".toUpperCase()));
        int no = Integer.parseInt($soap_14_extract1.get(0).getREFUSEDTOTAL());
        int maxRefusedPerDay = 0;
        boolean stepResult = true;
        try {
            //check co gui sms cho diem ban hay khong
            try {
                //String retval = getValueFromKey($soap_12_extract1,"SEND_SMS");
                String retval = $soap_12_extract1.get(0).getSEND_SMS();
                if (retval.equals("1")) sendSmsForSharing = true;
            } catch (Exception e) {
            }


            //check thue bao dang cho dich vu da gioi thieu cua diem ban
            System.out.println($soap_17_extract1.get(0).getWAITPERSERVICETOTAL() + "|sharingkey:" + context.get("sharingkey"));
            if (Integer.parseInt($soap_17_extract1.get(0).getWAITPERSERVICETOTAL()) >= 1) {
                if (sendSmsForSharing) {
                    //String mt = getValueFromKeyMultiRecords($soap_31_extract1,"record","MT_TYPE_KEY","MT_NOTICE_SHARING_PARTNER_WAIT_PER_SERVICE","MT_TYPE_VALUE").replaceAll("\\{MSISDN}",String.valueOf($msisdn)).replaceAll("\\{SERVICE_KEY}",context.get("SERVICE_KEY"));
                    String mt = $soap_34_extract1.get(0).getMT_TYPE_VALUE().replaceAll("\\{MSISDN}",msisdn).replaceAll("\\{SERVICE_KEY}",context.get("SERVICE_KEY"));
                    if (mt == null || mt.equals(""))
                        mt = $soap_8_extract1.get(0).getMT_TYPE_VALUE().replaceAll("\\{MSISDN}",msisdn).replaceAll("\\{SERVICE_KEY}",context.get("SERVICE_KEY"));
                    String ret = sendSms(context.get("sharingkey"), mt);
                    System.out.println("ret:" + ret);
                }

                context.put("ErrorCodeAPI", "40");
                context.put("ErrorDescAPI", "MT_NOTICE_SHARING_PARTNER_WAIT_PER_SERVICE");
                stepResult = false;
            }

            //check thue bao da tu choi dich vu da gioi thieu cua diem ban
            System.out.println($soap_19_extract1.get(0).getREFUSEDPERSERVICETOTAL() + "|sharingkey:" + context.get("sharingkey"));
            if (Integer.parseInt($soap_19_extract1.get(0).getREFUSEDPERSERVICETOTAL()) >= 1) {
                if (sendSmsForSharing) {
                    String mt = $soap_34_extract1.get(0).getMT_TYPE_VALUE().replaceAll("\\{MSISDN}",msisdn).replaceAll("\\{SERVICE_KEY}",context.get("SERVICE_KEY"));
                    if (mt == null || mt.equals(""))
                        mt = ((String) getGetValueFromKeyMultiRecords()).replaceAll("\\{MSISDN}", String.valueOf(getProperty("$msisdn"))).replaceAll("\\{SERVICE_KEY}", context.get("SERVICE_KEY"));
                    String ret = sendSms(context.get("sharingkey"), mt);
                    System.out.println("ret:" + ret);
                }

                context.put("ErrorCodeAPI", "41");
                context.put("ErrorDescAPI", "MT_NOTICE_SHARING_PARTNER_REFUSED_PER_SERVICE");
                stepResult = false;
            }


            try {
                maxRefusedPerDay = Integer.parseInt((String) getGetValueFromKeyMultiRecords());
            } catch (Exception e) {
            }

            int maxFaildPerDay = 0;
            try {
                maxFaildPerDay = Integer.parseInt((String) getGetValueFromKeyMultiRecords());
            } catch (Exception e) {
                maxFaildPerDay = 1000000;
            }

            System.out.println("maxRefusedPerDay:" + maxRefusedPerDay + "|maxFaildPerDay:" + maxFaildPerDay + "|no:" + no + "|noconfirm:" + noConfirm + "|sharingkey:" + context.get("sharingkey"));
            if (no >= maxRefusedPerDay || noConfirm >= maxFaildPerDay || (no + noConfirm) >= maxFaildPerDay) {
                System.out.println("maxRefusedPerDay:" + maxRefusedPerDay + ",no=" + no + ",maxFaildPerDay=" + maxFaildPerDay + ",noConfirm:" + noConfirm);
                //do action
                System.out.println("soap_12_extract1:" + $soap_15_extract1);
                String actionType = (String) getGetValueFromKeyMultiRecords();
                if (actionType.equals("LOCK_DB")) {
                    String ret = parameterServices.getDataCheckLockDb(sharing_key_id);
                    if (ret.equals("1")) {
                        if (sendSmsForSharing) {
                            if (no >= maxRefusedPerDay) {
                                String mt = DefaultGroovyMethods.invokeMethod(getGetValueFromKeyMultiRecords(), "replaceAll", new Object[]{"\\{MSISDN}", DefaultGroovyMethods.invokeMethod(String.class, "valueOf", new Object[]{getProperty("$msisdn")})}).replaceAll("\\{SERVICE_KEY}", context.get("SERVICE_KEY"));
                                if (mt == null || mt.equals(""))
                                    mt = ((String) (DefaultGroovyMethods.invokeMethod(((String) getGetValueFromKeyMultiRecords()).replaceAll("\\{MSISDN}", String.valueOf(getProperty("$msisdn"))), "replaceAll", new Object[]{"\\{SERVICE_KEY}", DefaultGroovyMethods.invokeMethod(context.get("SERVICE_KEY"), "replaceAll", new Object[]{"\\{REFUSED_TOTAL}", no})})));
                                ret = sendSms(context.get("sharingkey"), mt);
                                System.out.println("ret:" + ret);
                            } else if (noConfirm >= maxFaildPerDay || (no + noConfirm) >= maxFaildPerDay) {
                                String mt = DefaultGroovyMethods.invokeMethod(getGetValueFromKeyMultiRecords(), "replaceAll", new Object[]{"\\{MSISDN}", DefaultGroovyMethods.invokeMethod(String.class, "valueOf", new Object[]{getProperty("$msisdn")})}).replaceAll("\\{SERVICE_KEY}", context.get("SERVICE_KEY"));
                                if (mt == null || mt.equals(""))
                                    mt = ((String) getGetValueFromKeyMultiRecords()).replaceAll("\\{MSISDN}", String.valueOf(getProperty("$msisdn"))).replaceAll("\\{SERVICE_KEY}", context.get("SERVICE_KEY"));
                                ret = sendSms(context.get("sharingkey"), mt);
                                System.out.println("ret:" + ret);
                            }

                        }

                    }

                }

                context.put("ErrorCodeAPI", "49");
                context.put("ErrorDescAPI", "SPAM_VIOLET");
                stepResult = false;
            } else {
                if ((no + noConfirm) == (maxFaildPerDay - 1) || (no + noConfirm) == (maxRefusedPerDay - 1)) {
                    if (sendSmsForSharing) {
                        if ((no + noConfirm) == (maxFaildPerDay - 1)) {
                            String mt = DefaultGroovyMethods.invokeMethod(getGetValueFromKeyMultiRecords(), "replaceAll", new Object[]{"\\{MSISDN}", DefaultGroovyMethods.invokeMethod(String.class, "valueOf", new Object[]{getProperty("$msisdn")})}).replaceAll("\\{SERVICE_KEY}", context.get("SERVICE_KEY"));
                            if (mt == null || mt.equals(""))
                                mt = ((String) getGetValueFromKeyMultiRecords()).replaceAll("\\{TRANSACTION_FAILED}", String.valueOf(no + noConfirm));
                            String ret = sendSms(context.get("sharingkey"), mt);
                            System.out.println("ret:" + ret);
                        } else {
                            String mt = DefaultGroovyMethods.invokeMethod(getGetValueFromKeyMultiRecords(), "replaceAll", new Object[]{"\\{MSISDN}", DefaultGroovyMethods.invokeMethod(String.class, "valueOf", new Object[]{getProperty("$msisdn")})}).replaceAll("\\{SERVICE_KEY}", context.get("SERVICE_KEY"));
                            if (mt == null || mt.equals(""))
                                mt = ((String) getGetValueFromKeyMultiRecords()).replaceAll("\\{TRANSACTION_FAILED}", String.valueOf((no + noConfirm)));
                            String ret = sendSms(context.get("sharingkey"), mt);
                            System.out.println("ret:" + ret);
                        }

                    }

                }

            }

            //check max sms per day
            System.out.println("checking sms per day....");
            boolean needCheck = false;
            String info = "-1";
            try {
                info = getGetValueFromKeyMultiRecords();

            } catch (Exception ex) {

            }

            try {
                if (!info.equals("-1") && !info.equals("")) {
                    int maxSms = Integer.parseInt(info);
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("typeQuery", "query");
                    params.put("Service", "total_sms");
                    params.put("Provider", "default");
                    params.put("ParamSize", "1");
                    params.put("P1", channel);
                    List<SmsPerDayDTO> lstSmsPerDay = smsPerDayDTOGenericsRequest.getData(params);

                    if (lstSmsPerDay.get(0) != null && lstSmsPerDay.get(0).getTOTAL_SMS() != null && Integer.parseInt(lstSmsPerDay.get(0).getTOTAL_SMS()) >= maxSms) {
                        context.put("ErrorCodeAPI", "21");
                        context.put("ErrorDescAPI", "Diem ban vuot qua gioi han " + maxSms + " gui tin qua kenh " + channel);
                        return false;
                    }

                }

                System.out.println("--cndcjdsncdncjdnc");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }


        } catch (Exception ex) {
            ex.printStackTrace();
            return stepResult = false;
        } finally {
            /*def logSql = {String msisdn, String transactionId, String stepName, int stepID,String stepKey,
            String stepInput,String stepOutput,String stepAction,String startTime, String endTime,String inputParameter ->*/
            logSql(msisdn, "-1", "CHECK_SPAM_POLICY", "1", "CHECK_SPAM_POLICY", StringGroovyMethods.plus("script_shop_id=" + DefaultGroovyMethods.invokeMethod(String.class, "valueOf", new Object[]{getProperty("script_shop_id")}) + "|no=", no) + ",noConfirm=" + noConfirm, String.valueOf(stepResult), context.get("ErrorCodeAPI") + "|" + context.get("ErrorDescAPI"), startTime, new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()), "sharingkey=" + DefaultGroovyMethods.invokeMethod(String.class, "valueOf", new Object[]{getProperty("sharingkey")}) + ",serviceid=" + DefaultGroovyMethods.invokeMethod(String.class, "valueOf", new Object[]{getProperty("serviceid")}) + ",packagecode=" + DefaultGroovyMethods.invokeMethod(String.class, "valueOf", new Object[]{getProperty("packagecode")}) + ",channel=" + channel, (String) getProperty("$script_shop_id"));
            System.out.println("stepResult:" + stepResult);
            return stepResult;
        }

    }






}
