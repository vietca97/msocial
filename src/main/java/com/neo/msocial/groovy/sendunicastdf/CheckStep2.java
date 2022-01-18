package com.neo.msocial.groovy.sendunicastdf;

import com.neo.msocial.dto.Soap2;
import com.neo.msocial.service.Activation;
import com.neo.msocial.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CheckStep2 {

    @Autowired
    private RedisUtils context;

    @Autowired
    private Activation activation;

    String sendSms(String receiver, String messageSms) {
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

    //Ngay 31/08/2018: Bo sung check cac goi FTPPlay:
    int getInt(String value) {
        try {
            //Ham convert Integer:
            return Integer.parseInt(value);
        } catch (
                Exception e) {
            return 0;
        }

    }

    String getProfile(String channel, String msisdn) {
        try {
            String utilUrl = context.get("dataflow_param:utilmodule");

            StringBuilder str_soap = new StringBuilder();
            str_soap.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:vms=\"http://vms.neo\">");
            str_soap.append("<soapenv:Header/><soapenv:Body>");
            str_soap.append("<vms:getProFileInfo>");
            str_soap.append("<vms:args0>").append(channel).append("</vms:args0>");
            str_soap.append("<vms:args1>").append(msisdn).append("</vms:args1>");
            str_soap.append("</vms:getProFileInfo></soapenv:Body></soapenv:Envelope>");
            String result = activation.parseXMLtext(activation.soapCall(utilUrl, str_soap.toString()), "//*[local-name() = 'return']");
            return result;
        } catch (Exception e) {
            return "ERR|" + e.getMessage();
        }
    }

    boolean check(String packagecode, String channel, String msisdn, String sharingkey) {
        String pkg = packagecode;
        if (pkg.equals("TT30") || pkg.equals("TH30") || pkg.equals("TT90") || pkg.equals("TH90")) {
            String resPro = getProfile(channel, msisdn);
            System.out.println("check_profile_sendu: " + msisdn + "," + resPro);
            //context.put("msisdn_profile",resPro);
            boolean is_pass = true;//Mac dinh la pass.
            if (resPro == null || resPro.startsWith("ERR")) {
                //Gui sms bao he thong ban:
                sendSms(msisdn, "Khong the tra cuu duoc thong tin thue bao, vui long thu lai sau!");
                is_pass = false;
            } else {
                //Xu ly kich ban goi theo profile: msisdn_profile:TT,QT2,25,06/08/2018
                String[] arr = resPro.split(",");
                String type_tb = arr[0];
                int ngay_active = getInt(arr[2]);
                if (msisdn.equals("84904862420") || msisdn.equals("84904508766")) ngay_active = 100;
                String sms_temp1 = "Thong bao: Ban KHONG THE dang ky goi PACKAGE_CODE dich vu SERVICE_KEY cho thue bao MSISDN. Do thue bao kich hoat chua du 120 ngay. Chi tiet lien he 9090";
                String sms_temp2 = "Thong bao: Quy khach KHONG THE dang ky goi PACKAGE_CODE dich vu SERVICE_KEY do so thue bao kich hoat chua du 120 ngay. Chi tiet lien he 9090";
                //Kiem tra ma goi:
                if (pkg.equals("TT30") || pkg.equals("TH30")) {
                    //Check ngay kich hoat full TT,TS: Toi thieu 120 ngay:
                    //System.out.println("-----> Check ngay kich hoat full: " + pkg + ", " + ngay_active);
                    if (ngay_active < 120) {
                        //Thue bao ko du dieu kien:
                        String sms_msg = sms_temp1;
                        sms_msg = sms_msg.replace("PACKAGE_CODE", pkg);
                        sms_msg = sms_msg.replace("SERVICE_KEY", "FTP_PLAY");
                        sms_msg = sms_msg.replace("MSISDN", msisdn);

                        sendSms(sharingkey, sms_msg);
                        is_pass = false;
                    }
                } else {
                    if (pkg.equals("TT90") || pkg.equals("TH90")) {
                        //Chi check tra sau:
                        if (type_tb.equals("TS")) {
                            //System.out.println("-----> Check ngay kich hoat TS: " + pkg + ", " + ngay_active);
                            if (ngay_active < 120) {
                                //Thue bao ko du dieu kien:
                                String sms_msg = sms_temp1;
                                sms_msg = sms_msg.replace("PACKAGE_CODE", pkg);
                                sms_msg = sms_msg.replace("SERVICE_KEY", "FTP_PLAY");
                                sms_msg = sms_msg.replace("MSISDN", msisdn);

                                sendSms(sharingkey, sms_msg);
                                is_pass = false;
                            }
                        }
                    }
                }
            }
            if (!is_pass) {
                context.put("ErrorCodeAPI", "100");
                context.put("ErrorDescAPI", "FTPPLAY_KHONG_DU_KICH_HOAT");
                return is_pass;
            }
        }
        return true;//End check goi ftpplay.
    }

    public boolean getRetVal(List<Soap2> $soap_2_extract1) {
        boolean retval = false;
        try {
            System.out.println($soap_2_extract1);
            if (context.get("content") == null || context.get("content").equals("")) context.put("content", " ");
            //def rootNode = new XmlSlurper().parseText($soap_2_extract1);
            String errorCode = $soap_2_extract1.get(0).getErrorCode();
            if (!errorCode.equals("0")) {
                String errorDesc = $soap_2_extract1.get(0).getErrorDesc();
                context.put("ErrorCodeAPI", errorCode);
                context.put("ErrorDescAPI", errorDesc);
            } else {
                //String time = getValueFromKey($soap_2_extract1, "time_validate");
                String time = $soap_2_extract1.get(0).getTimeValidate();
                //System.out.println("VALIDATE_TIME:"+time);
                if (time.equals("0")) {
                    System.out.println("VALIDATE_TIME:" + $soap_2_extract1);
                    context.put("ErrorCodeAPI", "55");
                    context.put("ErrorDescAPI", "Thoi gian kich ban khong phu hop");
                } else {
                    for (Soap2 record : $soap_2_extract1) {
                        //context.put(it.name(), it.text());
                    }
                    retval = true;
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (!retval) {
                try {
                    String mt = "";
                    //System.out.println("CHANNEL:"+context.get("channel"));
                    if (context.get("channel").equals("SMS")) {
                        if (context.get("ErrorCodeAPI").equals("55")) {
                            mt = "Thoi gian kich hoat kich ban phan phoi dich vu/goi dich vu khong dung.";
                        } else if (context.get("ErrorCodeAPI").equals("2")) {
                            mt = "Ma kenh phan phoi cho diem ban chua duoc kich hoat tren kich ban phan phoi goi dich vu nay";
                        } else if (context.get("ErrorCodeAPI").equals("3")) {
                            mt = "Ma diem ban chua duoc kich hoat tren he thong";
                        } else if (context.get("ErrorCodeAPI").equals("4")) {
                            mt = "Ma diem ban dang bi khoa.";
                        } else if (context.get("ErrorCodeAPI").equals("5")) {
                            mt = "Ma dich vu khong dung.";
                        } else if (context.get("ErrorCodeAPI").equals("6")) {
                            mt = "Ma goi dich vu khong dung.";
                        } else if (context.get("ErrorCodeAPI").equals("7")) {
                            mt = "Diem ban hoac doi tac chua duoc gan kich ban phan phoi dich vu";
                        } else if (context.get("ErrorCodeAPI").equals("8")) {
                            mt = "Bi lap kich ban phan phoi cho doi tac hoac diem ban theo dich vu,kenh phan phoi";
                        } else if (context.get("ErrorCodeAPI").equals("-1")) {
                            mt = "Loi dich vu, xin moi thu lai sau.";
                        } else if (context.get("ErrorCodeAPI").equals("1")) {
                            mt = "So thue bao gioi thieu khong dung";
                        } else {
                            mt = "Tin nhan sai cu phap, xin moi thu lai.";
                        }
                        sendSms(context.get("sharingkey"), mt);
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
            return retval;
        }
    }

}


