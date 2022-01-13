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
public class ThueBaoHuyDichVu {

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


        //String result = new Activation().soapCall(context.get("dataflow_param:sqlmodule"), str_soap.toString());
        return "";
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
            //String result = new Activation().soapCall(utilUrl, str_soap.toString());
            return "";
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
//check thue bao huy
    public boolean checkThueBaoHuy(List<Soap34> $soap_34_extract1, List<Soap8> $soap_8_extract1, String script_shop_id, String msisdn,
                                   String sharingkey,
                                   String packagecode,
                                   String channel
    ) {
        boolean check = false;
        String startTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        boolean stepResult = true;
        String serviceType = context.get("LIKE_MI_OR_VAS");
        String numberDayHuy = context.get("SO_NGAY_CHECKHUY");
        if (numberDayHuy == null || numberDayHuy.equals("")) numberDayHuy = "30";
//System.out.println("1222222222222222222222222222222222222-22222222222222222222 "+numberDayHuy+"-------:::"+context.get("SO_NGAY_CHECKHUY"));

        String HAVE_CHECK_HUY = context.get("HAVE_CHECK_HUY");
        // fix data
        HAVE_CHECK_HUY = "1";
        try {
            if (HAVE_CHECK_HUY != null && HAVE_CHECK_HUY.equals("0")) {
                stepResult = true;
            } else {
                // fix data
                //if (context.get("SERVICE_KEY").equals("NHAC_CHO_FUNRING") || context.get("SERVICE_KEY").equals("ZING_TANG_NHACCHO") || context.get("isservice").equals("true") || context.get("HAVE_CHECK_HUY").equals("0"))
                   // stepResult = true;
                if("2".equals("1")){}
                else {

                    StringBuilder str_soap = new StringBuilder();
                    str_soap.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:vms=\"http://vms.neo\">");
                    str_soap.append("<soapenv:Header/><soapenv:Body>");
                    str_soap.append("<vms:checkHuyNew>");
                    str_soap.append("<vms:scriptShopId>").append(script_shop_id).append("</vms:scriptShopId>");
                    str_soap.append("<vms:sharing_key>").append(sharingkey).append("</vms:sharing_key>");
                    str_soap.append("<vms:msisdn>").append(msisdn).append("</vms:msisdn>");
                    str_soap.append("<vms:packagecode>").append(packagecode).append("</vms:packagecode>");
                    str_soap.append("<vms:channelkey>").append(channel).append("</vms:channelkey>");
                    str_soap.append("<vms:serviceType>").append(serviceType).append("</vms:serviceType>");
                    str_soap.append("<vms:numberdayhuy>").append(numberDayHuy).append("</vms:numberdayhuy>");
                    str_soap.append("</vms:checkHuyNew></soapenv:Body></soapenv:Envelope>");


                    String ret = "";
                    try {
                        //ret = new Activation().parseXMLtext(new Activation().soapCall(context.get("dataflow_param:utilmodule"), request), "//*[local-name() = 'return']");
                        System.out.println("---ret:" + ret + "-----");
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                        stepResult = true;
                    }
                    try {
                        // fix data
                        //def rootNode = new XmlSlurper().parseText(ret);
                        //String errorCode = rootNode.Parameter.ErrorCode.text();
                        //String errorDesc = rootNode.Parameter.ErrorDesc.text();
                        String errorCode = "0";
                        String errorDesc = "0";

                        System.out.println("ErrorCode:" + errorCode);
                        if (!errorCode.equals("0")) {
                            context.put("ErrorCodeAPI", errorCode);
                            context.put("ErrorDescAPI", errorDesc);
                            stepResult = false;
                        } else {
                            //success
                            try {
                                //request = rootNode.Parameter.cmdRequest_with_value.text();
                                // fix data
                                //String response = rootNode.Parameter.cmdresponse_with_value.text();
                                String response = "success";
                                //context.put("checkhuy_" + rootNode.Parameter.cmdRequest_with_value.name(), rootNode.Parameter.cmdRequest_with_value.text());
                                //context.put("checkhuy_" + rootNode.Parameter.cmdresponse_with_value.name(), rootNode.Parameter.cmdresponse_with_value.text());
                                //context.put(rootNode.Parameter.check_huy_status.name(), rootNode.Parameter.check_huy_status.text());
                                //String checkHuyStatus = rootNode.Parameter.check_huy_status.text();
                                String checkHuyStatus = "1";
                               // System.out.println(checkHuyStatus);
                                //context.put("check_huy_status", checkHuyStatus);
                                if (response.startsWith("-1000|")) {
                                    context.put("ErrorCodeAPI", "-1");
                                    context.put("ErrorCodeDesc", "Loi khong tra cuu duoc lich su huy cua thue bao");
                                    stepResult = false;
                                }
                                if (checkHuyStatus.equals("1")) {
                                    context.put("ErrorCodeAPI", "24");
                                    context.put("ErrorDescAPI", "Thue bao co lich su huy trong 30 ngay");
                                    if (context.get("SEND_SMS").equals("1")) {
                                        String mt = utilServices.getValueFromKeySOAP34($soap_34_extract1, "HUY_DICH_VU_30NGAY").replaceAll("\\{MSISDN}", String.valueOf(msisdn)).replaceAll("\\{SERVICE_KEY}", context.get("SERVICE_KEY_SMS")).replaceAll("\\{SERVICE_NAME}", context.get("SERVICE_NAME_SMS"));
                                        if (mt == null || mt.equals(""))
                                            mt = utilServices.getValueFromKeySOAP8($soap_8_extract1, "HUY_DICH_VU_30NGAY").replaceAll("\\{MSISDN}", String.valueOf(msisdn)).replaceAll("\\{SERVICE_KEY}", context.get("SERVICE_KEY_SMS")).replaceAll("\\{SERVICE_NAME}", context.get("SERVICE_NAME_SMS"));
                                        System.out.println("MT:--" + mt);
                                        // fix data
                                        //ret = sendSms(context.get("sharingkey"), mt);
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
            //logSql(msisdn, "-1", "CHECK_THUE_BAO_HUY_DICH_VU", "4", "CHECK_THUE_BAO_HUY_DICH_VU", context.get("checkhuy_cmdRequest_with_value"), context.get("checkhuy_cmdresponse_with_value") + "|stepResult=" + stepResult, context.get("ErrorCodeAPI") + "|" + context.get("ErrorDescAPI"), startTime, new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()), "sharingkey=$sharingkey,serviceid=$serviceid,packagecode=$packagecode,channel=$channel", (String) $script_shop_id);
            return stepResult;
        }
    }

}