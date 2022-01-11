package com.neo.msocial.groovy

import com.neo.msocial.dto.Soap2
import com.neo.msocial.dto.Soap35
import com.neo.msocial.service.Activation
import com.neo.msocial.utils.RedisUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CheckStep6 {

    @Autowired
    private RedisUtils context;

    String sendSms (String receiver, String messageSms ){
        try {
            receiver = String.valueOf(receiver);
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
            ex.printStackTrace();
            ret = "";
        }
        return ret;
    }

    def getValue (List<Soap2> lstSoap2 ){
        String ret = "";
        try {
            for (Soap2 record : lstSoap2) {
                if (record.getTimeValidate() != null) {
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

    boolean getRetValue(List<Soap35> $soap_35_extract1, List<Soap2> $soap_2_extract1) {
        boolean retval = false;
        boolean checkSms = false;
        System.out.println("aaaaa000000");
        context.put("debug_start_time", System.currentTimeMillis());
        System.out.println("IP_LIST-----------" + context.get("dataflow_param:IP_LIST_REGISTER_PARTNER"));
        System.out.println("IP_CLIENT-----------" + context.get("IP_CLIENT"));

        try {
            for (Soap35 record : $soap_35_extract1) {
                context.put(Soap35.jobExpDataDirExpLocal, record.getJOB_EXP_DATA_DIR_EXP_LOCAL());
                context.put(Soap35.jobExpDataDirFtp, record.getJOB_EXP_DATA_DIR_FTP());
                context.put(Soap35.listEmailReportDuytri, record.getLIST_EMAIL_REPORT_DUYTRI());
                context.put(Soap35.listEmailReportJob, record.getLIST_EMAIL_REPORT_JOB());
                context.put(Soap35.listEmailReportPhatsinh, record.getLIST_EMAIL_REPORT_PHATSINH());
                context.put(Soap35.lockTime, record.getLOCK_TIME());
                context.put(Soap35.loginMax, record.getLOGIN_MAX());
                context.put(Soap35.logSystem, record.getLOGSYSTEM());
                context.put(Soap35.onOffLock, record.getON_OFF_LOCK());
                context.put(Soap35.serverFrom, record.getSERVER_FROM());
                context.put(Soap35.serverHost, record.getSERVER_HOST());
                context.put(Soap35.serverPass, record.getSERVER_PASS());
                context.put(Soap35.serverPort, record.getSERVER_PORT());
                context.put(Soap35.serverUser, record.getSERVER_USER());
            }
            System.out.println("aaaaa000000 " + content);
            System.out.println("aaaaa000000 " + $soap_35_extract1);
            if (context.get("content") == null || context.get("content").equals("")) context.put("content", " ");

            String errorCode = $soap_2_extract1.get(0).getErrorCode();

            if (!errorCode.equals("0")) {
                String errorDesc = $soap_2_extract1.get(0).getErrorDesc();
                context.put("ErrorCodeAPI", errorCode);
                context.put("ErrorDescAPI", errorDesc);

            } else {

                String time = getValue($soap_2_extract1, "time_validate");
                System.out.println("VALIDATE_TIME:" + time);
                if (time.equals("0")) {
                    context.put("ErrorCodeAPI", "55");
                    context.put("ErrorDescAPI", "Thoi gian kich ban khong phu hop");
                } else {
                    for(Soap2 record : $soap_2_extract1){
                        //context.put(it.name(), it.text());
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
                    sendSms(context.get("sharingkey"), mt);
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