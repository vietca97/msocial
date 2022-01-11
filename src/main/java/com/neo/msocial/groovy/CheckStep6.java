package com.neo.msocial.groovy;

import com.neo.msocial.dto.Soap2;
import com.neo.msocial.dto.Soap35;
import com.neo.msocial.service.Activation;
import com.neo.msocial.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CheckStep6 {

    @Autowired
    private RedisUtils context;

    String sendSms (String receiver, String messageSms ){
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

            String result = new Activation().soapCall(utilUrl, str_soap.toString());
            return result;
        } catch (Exception e) {
            return "-1|" + e.getMessage();
        }
    }

    String getValue (List<Soap2> lstSoap2, String key ){
        String ret = "";
        try {
            for (Soap2 record : lstSoap2) {
                if (key.equals("time_validate") && record.getTimeValidate() != null) {
                    ret = record.getTimeValidate();
                    break;
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            ret = "";
        }
        return ret;
    }

    public boolean getRetValue(List<Soap35> $soap_35_extract1, List<Soap2> $soap_2_extract1) {
        boolean retval = false;
        boolean checkSms = false;
        System.out.println("aaaaa000000");
        context.put("debug_start_time", String.valueOf(System.currentTimeMillis()));
        System.out.println("IP_LIST-----------" + context.get("dataflow_param:IP_LIST_REGISTER_PARTNER"));
        System.out.println("IP_CLIENT-----------" + context.get("IP_CLIENT"));

        try {
            for (Soap35 record : $soap_35_extract1) {
                if(record.getJOB_EXP_DATA_DIR_EXP_LOCAL() != null){
                    context.put(Soap35.jobExpDataDirExpLocal, record.getJOB_EXP_DATA_DIR_EXP_LOCAL());
                }
                if(record.getJOB_EXP_DATA_DIR_FTP() != null){
                    context.put(Soap35.jobExpDataDirFtp, record.getJOB_EXP_DATA_DIR_FTP());
                }
                if(record.getLIST_EMAIL_REPORT_DUYTRI() != null){
                    context.put(Soap35.listEmailReportDuytri, record.getLIST_EMAIL_REPORT_DUYTRI());
                }

                if(record.getLIST_EMAIL_REPORT_JOB() != null){
                    context.put(Soap35.listEmailReportJob, record.getLIST_EMAIL_REPORT_JOB());
                }

                if(record.getLIST_EMAIL_REPORT_PHATSINH() != null){
                    context.put(Soap35.listEmailReportPhatsinh, record.getLIST_EMAIL_REPORT_PHATSINH());
                }

                if(record.getLOCK_TIME() != null){
                    context.put(Soap35.lockTime, record.getLOCK_TIME());
                }

                if(record.getLOGIN_MAX() != null){
                    context.put(Soap35.loginMax, record.getLOGIN_MAX());
                }

                if(record.getLOGSYSTEM() != null){
                    context.put(Soap35.logSystem, record.getLOGSYSTEM());
                }

                if(record.getON_OFF_LOCK() != null){
                    context.put(Soap35.onOffLock, record.getON_OFF_LOCK());
                }
                if(record.getSERVER_FROM() != null){
                    context.put(Soap35.serverFrom, record.getSERVER_FROM());
                }
                if(record.getSERVER_HOST() != null){
                    context.put(Soap35.serverHost, record.getSERVER_HOST());
                }
                if(record.getSERVER_PASS() != null){
                    context.put(Soap35.serverPass, record.getSERVER_PASS());
                }
                if(record.getSERVER_PORT() != null){
                    context.put(Soap35.serverPort, record.getSERVER_PORT());
                }
                if(record.getSERVER_USER() != null){
                    context.put(Soap35.serverUser, record.getSERVER_USER());
                }

            }

            if (context.get("content") == null || context.get("content").equals("")) context.put("content", " ");

            String errorCode = $soap_2_extract1.get(0).getErrorCode();

            if (!"0".equals(errorCode)) {
                String errorDesc = $soap_2_extract1.get(0).getErrorDesc();
                context.put("ErrorCodeAPI", errorCode);
                context.put("ErrorDescAPI", errorDesc);

            } else {

                String time = getValue($soap_2_extract1, "time_validate");
                System.out.println("VALIDATE_TIME:" + time);
                if ("0".equals(time)) {
                    context.put("ErrorCodeAPI", "55");
                    context.put("ErrorDescAPI", "Thoi gian kich ban khong phu hop");
                } else {
                    for(Soap2 record : $soap_2_extract1){
                        context.put(Soap2.agent_id , record.getAgentId());
                        context.put(Soap2.channel_id , record.getChannelId());
                        context.put(Soap2.ma_chi_nhanh , record.getMaChiNhanh());
                        context.put(Soap2.packagecode_id, record.getPackageCodeId());
                        context.put(Soap2.partner_id , record.getPartnerId());
                        context.put(Soap2.script_shop_id , record.getScriptShopId());
                        context.put(Soap2.service_id , record.getServiceId());
                        context.put(Soap2.sharing_key_id , record.getSharingKeyId());
                        context.put(Soap2.time_validate , record.getTimeValidate());
                    }
                    retval = true;
                }

            }
            if (!retval) {
                if (context.get("channel").equals("SMS")) {
                    String mt = "He thong khong tra cuu duoc thong tin, xin moi thu lai sau!";
                    if (context.get("ErrorCodeAPI").equals("3"))
                        mt = "Giao dich khong thanh cong do diem ban chua duoc kich hoat tren he thong!";
                    else if (context.get("ErrorCodeAPI").equals("4"))
                        mt = "Thue bao so " + context.get("sharingkey") + " vi pham quy che ban hang. Thue bao da bi khoa chuc nang ban dich vu trong 24 tieng. Xin moi thu lai sau.";
                    else if (context.get("ErrorCodeAPI").equals("55"))
                        mt = "Kich ban dich vu " + context.get("packagecode") + " chua duoc kich hoat cho diem ban " + context.get("sharingkey");
                    else if (context.get("ErrorCodeAPI").equals("2"))
                        mt = "Ma kenh ban hang " + context.get("channel") + " khong dung, giao dich khong thanh cong.";
                    else if (context.get("ErrorCodeAPI").equals("1"))
                        mt = "So thue bao gioi thieu khong dung.";
                    else if (context.get("ErrorCodeAPI").equals("5"))
                        mt = "Ma dich vu khong dung.";
                    else if (context.get("ErrorCodeAPI").equals("6"))
                        mt = "Ma goi dich vu khong dung.";
                    else if (context.get("ErrorCodeAPI").equals("7"))
                        mt = "Diem ban hoac doi tac chua duoc cap quyen phan phoi dich vu.";
                    //sendSms(context.get("sharingkey"), mt);
                }
            }
            return retval;
        }
        catch (
                Exception ex
                ) {
            ex.printStackTrace();
            return retval;
        }
    }
}