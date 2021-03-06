package com.neo.msocial.groovy;

import com.neo.msocial.dto.Soap34;
import com.neo.msocial.dto.Soap8;
import com.neo.msocial.service.Activation;
import com.neo.msocial.service.ParseXml;
import com.neo.msocial.utils.RedisUtils;
import com.neo.msocial.utils.UtilServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class ThuebaoSudungDichvu {

    @Autowired
    private RedisUtils context;

    @Autowired
    private UtilServices utilServices;

    String logSql (String msisdn, String transactionId, String stepName, String stepIndex,String stepKey,String stepInput,String stepOutput,String stepAction,String startTime, String endTime,String inputParameter,String scriptShopId){
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
//do bussiness
/*
soap_8: mt_info
soap_24: system_info
soap_9:service_package_info
soap_12:partner_info
*/
//check thue bao da su dung dich vu hay chua
    public boolean checkThueBao(
            List<Soap34> $soap_34_extract1,
            List<Soap8> $soap_8_extract1,
            String script_shop_id,
            String sharingkey,
            String msisdn,
            String packagecode,
            String channel
    ) {
        boolean check = false;
        String startTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        boolean stepResult = false;

        try {
            StringBuilder str_soap = new StringBuilder();
            str_soap.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:vms=\"http://vms.neo\">");
            str_soap.append("<soapenv:Header/><soapenv:Body>");
            str_soap.append("<vms:checkUsedService>");
            str_soap.append("<vms:scriptShopId>").append(script_shop_id).append("</vms:scriptShopId>");
            str_soap.append("<vms:sharing_key>").append(sharingkey).append("</vms:sharing_key>");
            str_soap.append("<vms:msisdn>").append(msisdn).append("</vms:msisdn>");
            str_soap.append("<vms:packagecode>").append(packagecode).append("</vms:packagecode>");
            str_soap.append("<vms:channelkey>").append(channel).append("</vms:channelkey>");
            str_soap.append("</vms:checkUsedService></soapenv:Body></soapenv:Envelope>");

            String ret = "";
            try {
                //fix data
               // ret = new Activation().parseXMLtext(new Activation().soapCall(context.get("dataflow_param:utilmodule"), request), "//*[local-name() = 'return']");
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                //return true;
                stepResult = false;
            }
            int cmdResultId = -1;
            try {
                //def rootNode = new XmlSlurper().parseText(ret);
                //String errorCode = rootNode.Parameter.ErrorCode.text();
               // String errorDesc = rootNode.Parameter.ErrorDesc.text();
                String errorCode = "0";
                String errorDesc = "0";
                if (!errorCode.equals("0")) {
                    context.put("ErrorCodeAPI", errorCode);
                    context.put("ErrorDescAPI", errorDesc);
                    //return false;
                    stepResult = false;
                } else {
                    //success
                    try {
                        // fix data
                        //request = rootNode.Parameter.cmdRequest_with_value.text();
                        //String response = rootNode.Parameter.cmdresponse_with_value.text();
                        String response = "success";
                        //System.out.println("checkservice_response:" + response);
                        //context.put("checkservice_" + rootNode.Parameter.cmdRequest_with_value.name(), rootNode.Parameter.cmdRequest_with_value.text());
                        //context.put("checkservice_" + rootNode.Parameter.cmdresponse_with_value.name(), rootNode.Parameter.cmdresponse_with_value.text());

                        try {

                            // fix data
                            //cmdResultId = Integer.parseInt(rootNode.record.COMMANDS_RESULTS_ID.text());
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        if (cmdResultId >= 0) {
                            // fix data
                            //String cmdStatus = rootNode.record.COMMANDS_STATUS.text();
                           // System.out.println(cmdStatus);
                            //rootNode.record.children().each {
                            //    context.put(it.name(), it.text());
                           // }
                            if ("NHA_NONG_XANH".equals(context.get("SERVICE_KEY_SMS"))) {
                                //fix data
                               // if (rootNode.Parameter.cmdresponse_with_value.text().indexOf(context.get("PACKAGE_CODE")) >= 0)
                               //     cmdStatus = "INUSE";
                               // else cmdStatus = "UNUSE";
                            }
                            String cmdStatus = "SUCCESS";
                            if (cmdStatus.equals("INUSE")) {
                                context.put("ErrorCodeAPI", "23");
                                context.put("ErrorDescAPI", "Thue bao dang su dung dich vu");
                                if (context.get("SEND_SMS").equals("1") && context.get("channel").equals("SMS")) {
                                    String mt = utilServices.getValueFromKeySOAP34($soap_34_extract1,"SERVICE_IN_USE").replaceAll("\\{MSISDN}", String.valueOf(msisdn)).replaceAll("\\{SERVICE_KEY}", context.get("SERVICE_KEY_SMS"));
                                    if (mt == null || mt.equals(""))
                                        mt = utilServices.getValueFromKeySOAP8($soap_8_extract1, "SERVICE_IN_USE").replaceAll("\\{MSISDN}", String.valueOf(msisdn)).replaceAll("\\{SERVICE_KEY}", context.get("SERVICE_KEY_SMS"));
                                    // fix data
                                    //ret = sendSms(context.get("sharingkey"), mt);
                                    //System.out.println("ret:" + ret);
                                }
                                stepResult = false;
                                //return false;
                            } else if (cmdStatus.equals("ERROR")) {
                                context.put("ErrorCodeAPI", "23");
                                context.put("ErrorDescAPI", "Thue bao dang su dung dich vu");
                                if (context.get("SEND_SMS").equals("1") && context.get("channel").equals("SMS")) {
                                    String mt = utilServices.getValueFromKeySOAP34($soap_34_extract1, "SERVICE_IN_USE").replaceAll("\\{MSISDN}", String.valueOf(msisdn)).replaceAll("\\{SERVICE_KEY}", context.get("SERVICE_KEY_SMS"));
                                    if (mt == null || mt.equals(""))
                                        mt = utilServices.getValueFromKeySOAP8($soap_8_extract1,"SERVICE_IN_USE").replaceAll("\\{MSISDN}", String.valueOf(msisdn)).replaceAll("\\{SERVICE_KEY}", context.get("SERVICE_KEY_SMS"));
                                    // fix data
                                    //ret = sendSms(context.get("sharingkey"), mt);
                                    //System.out.println("ret:" + ret);
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

            //logSql(msisdn, "-1", "CHECK_THUE_BAO_SU_DUNG_DICH_VU", "3", "CHECK_THUE_BAO_SU_DUNG_DICH_VU", context.get("checkservice_cmdRequest_with_value"), context.get("checkservice_cmdresponse_with_value") + "|stepResult=" + stepResult, context.get("ErrorCodeAPI") + "|" + context.get("ErrorDescAPI"), startTime, new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()), "sharingkey=$sharingkey,serviceid=$serviceid,packagecode=$packagecode,channel=$channel,dataflow:registerService", script_shop_id);
            return stepResult;
        }
    }
}