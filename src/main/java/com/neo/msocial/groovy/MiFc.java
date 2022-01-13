package com.neo.msocial.groovy;

import com.neo.msocial.dto.Soap34;
import com.neo.msocial.dto.Soap8;
import com.neo.msocial.dto.SoapPckInfo;
import com.neo.msocial.service.Activation;
import com.neo.msocial.utils.GenericsRequest;
import com.neo.msocial.utils.RedisUtils;
import com.neo.msocial.utils.SystemParameterServices;
import com.neo.msocial.utils.UtilServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class MiFc {

    @Autowired
    private RedisUtils context;

    @Autowired
    private UtilServices utilServices;

    @Autowired
    private GenericsRequest<SoapPckInfo> genericsRequestSoapPckInfo;

    @Autowired
    private SystemParameterServices systemParameterServices;

    //String logSql(String msisdn, String $transactionId, String $stepName, String $stepIndex,String $stepKey,String $stepInput,String $stepOutput,String $stepAction,String $startTime, String $endTime,String $inputParameter,String $scriptShopId) {
    String logSql(String ...params){
        StringBuilder str_soap = new StringBuilder();
        str_soap.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:vms=\"http://vms.neo\">");
        str_soap.append("<soapenv:Header/><soapenv:Body>");
        str_soap.append("<vms:updateXml>");
        str_soap.append("<vms:Service>").append("sql_log_transaction_step").append("</vms:Service>");
        str_soap.append("<vms:Provider>").append("default").append("</vms:Provider>");
        str_soap.append("<vms:ParamSize>").append( "12").append("</vms:ParamSize>");
        //str_soap.append("<vms:P1>").append($transactionId).append("</vms:P1>");
        //str_soap.append("<vms:P2>").append($stepName).append("</vms:P2>");
        //str_soap.append("<vms:P3>").append($stepIndex).append("</vms:P3>");
       // str_soap.append("<vms:P4>").append($stepKey).append("</vms:P4>");
        //str_soap.append("<vms:P5>").append($stepInput).append("</vms:P5>");
        //str_soap.append("<vms:P6>").append($stepOutput).append("</vms:P6>");
        //str_soap.append("<vms:P7>").append($stepAction).append("</vms:P7>");
        //str_soap.append("<vms:P8>").append($startTime).append("</vms:P8>");
        //str_soap.append("<vms:P9>").append($endTime).append("</vms:P9>");
        //str_soap.append("<vms:P10>").append(msisdn).append("</vms:P10>");
        //str_soap.append("<vms:P11>").append($inputParameter).append("</vms:P11>");
        //str_soap.append("<vms:P12>").append($scriptShopId).append("</vms:P12>");
        str_soap.append("</vms:updateXml></soapenv:Body></soapenv:Envelope>");

		//String result = new Activation().soapCall(context.get("dataflow_param:sqlmodule"), str_soap);

        return  systemParameterServices.executeLogSql(params);

    }
    String sendSms  (String receiver, String contentmsg ){
        try{
//            receiver = String.valueOf(receiver);
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
            str_soap.append("<vms:args2>").append(contentmsg).append("</vms:args2>");
            str_soap.append("<vms:args3>").append(smsHost).append("</vms:args3>");
            str_soap.append("<vms:args4>").append(smsPort).append("</vms:args4>");
            str_soap.append("<vms:args5>").append(smsLookup).append("</vms:args5>");
            str_soap.append("</vms:sendSms></soapenv:Body></soapenv:Envelope>");

            String result = new Activation().soapCall(utilUrl,str_soap.toString());
            return result;
        }catch(Exception e){
            return "-1|"+e.getMessage();
        }
    }

    String getValueFromKeyMultiRecords(String lstPckInfo, String record, String tagName, String content, String value){
        return "0";
    }

    String getValueFromKey(String ret, String value){
        return "";
    }
    String [] checkMIBigger (String info,String serviceId,String packageCode ){
        String[] retval = new String[7];
        if(info == null || info.indexOf("DATA_WEB_SUCC")<0){
            if(info!=null){
                if(info.equals("KT_DATA_WEB_ERR01: NULL")){
                    retval[0]="0";
                    return retval;
                }
                else{
                    retval[0]="-1";
                    return retval;
                }
            }
            else{
                retval[0]="0";
                return retval;
            }
        }
        String pckNeedCheck = info.split("\\|")[0];
        pckNeedCheck = pckNeedCheck.substring(info.indexOf(" ")).trim();
        if(pckNeedCheck.equals(packageCode)){
            retval[0]="1";
            return retval;
        }
        StringBuilder request = new StringBuilder();
        request.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:vms=\"http://vms.neo\">");
        request.append("<soapenv:Header/><soapenv:Body>");
        request.append("<vms:queryXml>");
        request.append("<vms:Service>").append("get_service_package_detail").append("</vms:Service>");
        request.append("<vms:Provider>").append("default").append("</vms:Provider>");
        request.append("<vms:ParamSize>").append("3").append("</vms:ParamSize>");
        request.append("<vms:P1>").append(packageCode).append("</vms:P1>");
        request.append("<vms:P2>").append(pckNeedCheck).append("</vms:P2>");
        request.append("<vms:P3>").append(serviceId).append("</vms:P3>");
        request.append("</vms:queryXml></soapenv:Body></soapenv:Envelope>");

        String pckInfo = "";
        double usedPckPrice = -1;
        double registerPckPrice = -1;
        String capacity = "";
        String cycle = "";
        try{
            pckInfo = new Activation().parseXMLtext(new Activation().soapCall(context.get("dataflow_param:sqlmodule"),request.toString()),"//*[local-name() = 'return']");

            usedPckPrice = Double.parseDouble( getValueFromKeyMultiRecords(pckInfo,"record","PACKAGE_CODE",pckNeedCheck,"PACKAGE_PRICE"));
            registerPckPrice = Double.parseDouble(getValueFromKeyMultiRecords(pckInfo,"record","PACKAGE_CODE",packageCode,"PACKAGE_PRICE"));
            capacity = getValueFromKeyMultiRecords(pckInfo,"record","PACKAGE_CODE",packageCode,"CAPACITY");
            cycle = getValueFromKeyMultiRecords(pckInfo,"record","PACKAGE_CODE",packageCode,"PACKAGE_CYCLE");
            if(String.valueOf(usedPckPrice).equals("M120") && (packageCode.equals("M120")||packageCode.equals("KM120"))){
                retval[0] = "10"; retval[1] = pckNeedCheck; retval[2] = String.valueOf(usedPckPrice);retval[3]=pckNeedCheck;retval[4]=String.valueOf(usedPckPrice);retval[5]=capacity;retval[6]=cycle;
                return retval;
            }
            if(registerPckPrice == usedPckPrice){
                retval[0] = "10"; retval[1] = pckNeedCheck; retval[2] = String.valueOf(usedPckPrice);retval[3]=pckNeedCheck;retval[4]=String.valueOf(usedPckPrice);retval[5]=capacity;retval[6]=cycle;
                return retval;
            }
            //gia goi dang ky > gia goi thue bao dang su dung
            if(registerPckPrice < usedPckPrice){
                retval[0] = "2";retval[1]=packageCode;retval[2]=String.valueOf(registerPckPrice);retval[3]=pckNeedCheck;retval[4]=String.valueOf(usedPckPrice);retval[5]=capacity;retval[6]=cycle;
            }
            else if(registerPckPrice > usedPckPrice){
                retval[0] = "3";retval[1]=packageCode;retval[2]=String.valueOf(registerPckPrice);retval[3]=pckNeedCheck;retval[4]=String.valueOf(usedPckPrice);retval[5]=capacity;retval[6]=cycle;
            }
            else{
                retval[0] = "1";
            }
            return retval;
        }catch(Exception ex){
            ex.printStackTrace();
            retval[0] = "-1"; return retval;
        }
    }

    String createTransaction (String script_shop_id,String sharing_key_id, String messageSend,String messageReceive,
                             String msisdn, String transactionStatus,String transactionResponse, String channelIDGT,String channelIDCF,
                             String content, String  packageCodeId, String price, String charging, String chargingType, String chargingStatus,
                             String avenueOne,String avenueMonth,String avenueAgentOne,String avenueAgentMonth, String agent_name_id){
        String ret = "";
        StringBuilder str_soap = new StringBuilder();
        str_soap.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:vms=\"http://vms.neo\">");
        str_soap.append("<soapenv:Header/><soapenv:Body>");
        str_soap.append("<vms:updateXml>");
        str_soap.append("<vms:Service>").append("generate_transaction").append("</vms:Service>");
        str_soap.append("<vms:Provider>").append("default").append("</vms:Provider>");
        str_soap.append("<vms:ParamSize>").append("20").append("</vms:ParamSize>");
        str_soap.append("<vms:P1>").append(script_shop_id).append("</vms:P1>");
        str_soap.append("<vms:P2>").append(sharing_key_id).append("</vms:P2>");
        str_soap.append("<vms:P3>").append(messageSend).append("</vms:P3>");
        str_soap.append("<vms:P4>").append(messageReceive).append("</vms:P4>");
        str_soap.append("<vms:P5>").append(msisdn).append("</vms:P5>");
        str_soap.append("<vms:P6>").append(transactionStatus).append("</vms:P6>");
        str_soap.append("<vms:P7>").append(transactionResponse).append("</vms:P7>");
        str_soap.append("<vms:P8>").append(channelIDGT).append("</vms:P8>");
        str_soap.append("<vms:P9>").append(channelIDCF).append("</vms:P9>");
        str_soap.append("<vms:P10>").append(content).append("</vms:P10>");
        str_soap.append("<vms:P11>").append(packageCodeId).append("</vms:P11>");
        str_soap.append("<vms:P12>").append(price).append("</vms:P12>");
        str_soap.append("<vms:P13>").append(charging).append("</vms:P13>");
        str_soap.append("<vms:P14>").append(chargingType).append("</vms:P14>");
        str_soap.append("<vms:P15>").append(chargingStatus).append("</vms:P15>");
        str_soap.append("<vms:P16>").append(avenueOne).append("</vms:P16>");
        str_soap.append("<vms:P17>").append(avenueMonth).append("</vms:P17>");
        str_soap.append("<vms:P18>").append(avenueAgentOne).append("</vms:P18>");
        str_soap.append("<vms:P19>").append(avenueAgentMonth).append("</vms:P19>");
        str_soap.append("<vms:P20>").append(agent_name_id).append("</vms:P20>");
        str_soap.append("</vms:updateXml></soapenv:Body></soapenv:Envelope>");
        try{
            ret = new Activation().parseXMLtext(new Activation().soapCall(context.get("dataflow_param:sqlmodule"),str_soap.toString()),"//*[local-name() = 'return']");
            StringBuilder request = new StringBuilder();
            request.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:vms=\"http://vms.neo\">");
            request.append("<soapenv:Header/><soapenv:Body>");
            request.append("<vms:queryXml>");
            request.append("<vms:Service>").append("get_transaction_id").append("</vms:Service>");
            request.append("<vms:Provider>").append("default").append("</vms:Provider>");
            request.append("<vms:ParamSize>").append("0").append("</vms:ParamSize>");
            request.append("</vms:queryXml></soapenv:Body></soapenv:Envelope>");
            ret = new Activation().parseXMLtext(new Activation().soapCall(context.get("dataflow_param:sqlmodule"),request.toString()),"//*[local-name() = 'return']");
            ret = getValueFromKey(ret,"TRANSACTION_ID");
        }catch(Exception ex){
            ret = "-1";
        }
        return ret;
    }

    String createTempTrans  (String transactionId, String sharingKey, String msisdn ){
        String ret = "";
        StringBuilder request = new StringBuilder();
        request.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:vms=\"http://vms.neo\">");
        request.append("<soapenv:Header/><soapenv:Body>");
        request.append("<vms:queryXml>");
        request.append("<vms:Service>").append("select_temp_accept_id").append("</vms:Service>");
        request.append("<vms:Provider>").append("default").append("</vms:Provider>");
        request.append("<vms:ParamSize>").append("1").append("</vms:ParamSize>");
        request.append("<vms:P1>").append(msisdn).append("</vms:P1>");
        request.append("</vms:queryXml></soapenv:Body></soapenv:Envelope>");
        try{
            ret = new Activation().parseXMLtext(new Activation().soapCall(context.get("dataflow_param:sqlmodule"),request.toString()),"//*[local-name() = 'return']");
            int tempAcceptId = Integer.parseInt(getValueFromKey(ret,"ACCEPT_ID"));
            if(tempAcceptId ==0) tempAcceptId =1;
            StringBuilder str_soap = new StringBuilder();
            str_soap.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:vms=\"http://vms.neo\">");
            str_soap.append("<soapenv:Header/><soapenv:Body>");
            str_soap.append("<vms:updateXml>");
            str_soap.append("<vms:Service>").append("insert_temp_accept_id").append("</vms:Service>");
            str_soap.append("<vms:Provider>").append("default").append("</vms:Provider>");
            str_soap.append("<vms:ParamSize>").append("4").append("</vms:ParamSize>");
            str_soap.append("<vms:P1>").append(transactionId).append("</vms:P1>");
            str_soap.append("<vms:P2>").append(sharingKey).append("</vms:P2>");
            str_soap.append("<vms:P3>").append(msisdn).append("</vms:P3>");
            str_soap.append("<vms:P4>").append(tempAcceptId).append("</vms:P4>");
            str_soap.append("</vms:updateXml></soapenv:Body></soapenv:Envelope>");
            try{
                ret = new Activation().parseXMLtext(new Activation().soapCall(context.get("dataflow_param:sqlmodule"),str_soap.toString()),"//*[local-name() = 'return']");
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }catch(Exception ex){
            ex.printStackTrace();
            return "-1";
        }
        return ret;
    }
//do bussiness
/*
soap_8: mt_info
soap_24: system_info
soap_9:service_package_info
soap_12:partner_info
*/
    public boolean checkMifc(List<Soap8> $soap_8_extract1,
                      List<Soap34> $soap_34_extract1,
                      String msisdn, String sharingkey,
            String serviceid , String packagecode , String channel, String script_shop_id, String checkStartDate
    ) {

        System.out.println("PACKAGE_CYCLE:" + context.get("PACKAGE_CYCLE") + "|" + msisdn + "|" + context.get("SERVICE_KEY"));

//check thue bao huy
        boolean check = false;
        String startTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        boolean stepResult = false;
        context.put("ErrorCodeAPI", "0"); context.put("ErrorDescAPI", "init");
        // fix data
        //if (!context.get("SERVICE_KEY").equals("MOBILEINTERNET") && !context.get("SERVICE_KEY").equals("FASTCONNECT")) {
        //    return true;
        //}
        if ("1".equals("1")) {
            return true;
        }
        System.out.println("PACKAGE_CYCLE:" + context.get("PACKAGE_CYCLE") + "|" + msisdn);

        String mi_status_info = context.get("checkservice_cmdresponse_with_value");
        System.out.println("PACKAGE_CYCLE:" + context.get("PACKAGE_CYCLE") + "|" + msisdn);

        String mi_huy_info = context.get("checkhuy_cmdresponse_with_value");
        System.out.println("PACKAGE_CYCLE:" + context.get("PACKAGE_CYCLE") + "|" + msisdn);

        String packageCode = context.get("PACKAGE_CODE");
        String serviceId = context.get("serviceid");
        int pckCycle = -1;
        try {
            boolean iscontinue = false;

            //Đối với các gói không cần check giá cước, sử dụng dịch vụ thì cho đăng ký luôn DP100,200,300,MAX10,30
            if (context.get("HAVE_CHECK_HUY").equals("0")) {
                System.out.println(msisdn + "||||" + iscontinue + "|||" + context.get("PACKAGE_CODE") + "|||" + context.get("HAVE_CHECK_HUY"));
                return true;
            }
            pckCycle = Integer.parseInt(context.get("PACKAGE_CYCLE"));
            System.out.println(msisdn + "||||" + iscontinue + "|||" + packageCode + "|||" + context.get("HAVE_CHECK_HUY"));
            if (pckCycle == -1) return false;
            //check MI, FC chu ky 30 ngay
            if (pckCycle == 30) {
                if (!packageCode.startsWith("KM") && !packageCode.startsWith("MAX")) {
                    if (mi_status_info == "" || mi_status_info.indexOf("DATA_WEB_SUCC") < 0) {

                    } else {
                        String pckInUse = mi_status_info.split("\\|")[0];
                        pckInUse = pckInUse.substring(pckInUse.lastIndexOf(" ")).trim();

                        if (pckInUse.equals("MIU") || pckInUse.equals("MIU3") || pckInUse.equals("MIU25")) {
                            if (packageCode.equals("M70")) iscontinue = false;
                            else {
                                if (context.get("HAVE_CHECK_HUY_WITH_5000").equals("0"))
                                    iscontinue = false;
                                else
                                    iscontinue = true;
                            }
                        } else {
                            if (context.get("HAVE_CHECK_HUY_WITH_5000").equals("0")) {
                                if (packageCode.equals("DP100") || packageCode.equals("DP200") || packageCode.equals("DP300") || packageCode.equals("2T79")) {
                                    iscontinue = true;
                                }
                                iscontinue = false;
                            } else
                                iscontinue = true;
                        }
                        System.out.println(msisdn + "||||" + iscontinue + "|||" + packageCode + "|||" + context.get("HAVE_CHECK_HUY"));
                        if (!iscontinue) {
                            context.put("ErrorCodeAPI", "250");
                            context.put("ErrorDescAPI", utilServices.getValueFromKeySOAP8($soap_8_extract1,"SUBSCRIBER_HAVE_OTHER_PACKAGE_CODE").replaceAll("\\{MSISDN}", String.valueOf(msisdn)).replaceAll("\\{PACKAGE_CODE}", context.get("PACKAGE_CODE_SMS")).replaceAll("\\{PACKAGE_CODE_IN_USE}", pckInUse));
                            if (context.get("SEND_SMS").equals("1") && context.get("channel").equals("SMS")) {
                                //return tid+":INFO:Thong bao: Ban KHONG THE dang ky goi dich vu "+packCode+" cho thue bao "+receiver+", do hien tai thue bao dang co goi "+pckInUse+". Chi tiet lien he 9090.";
                                String mt = utilServices.getValueFromKeySOAP34($soap_34_extract1, "SUBSCRIBER_HAVE_OTHER_PACKAGE_CODE").replaceAll("\\{MSISDN}", String.valueOf(msisdn)).replaceAll("\\{PACKAGE_CODE}", context.get("PACKAGE_CODE_SMS")).replaceAll("\\{PACKAGE_CODE_IN_USE}", pckInUse);
                                if (mt == null || mt.equals(""))
                                    mt = utilServices.getValueFromKeySOAP8($soap_8_extract1, "SUBSCRIBER_HAVE_OTHER_PACKAGE_CODE").replaceAll("\\{MSISDN}", String.valueOf(msisdn)).replaceAll("\\{PACKAGE_CODE}", context.get("PACKAGE_CODE_SMS")).replaceAll("\\{PACKAGE_CODE_IN_USE}", pckInUse);
                                 String ret = sendSms(context.get("sharingkey"), mt);
                            }
                            return false;
                        }
                        //neu iscontinue = true thi chay tiep phia duoi de check gia goi cao hay thap
                    }
                    //goi ham lay thong tin huy
                    String vasgateHost = context.get("VASGATE_SERVER_IP");
                    String vasgatePort = context.get("VASGATE_SERVER_PORT");
                    StringBuilder request = new StringBuilder();
                    request.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:vms=\"http://vms.neo\">");
                    request.append("<soapenv:Header/><soapenv:Body>");
                    request.append("<vms:sendCommand>");
                    request.append("<vms:cmd>").append("VASPRO.SERVICEACTION -subscriber=").append(msisdn).append("-arg0=999 -arg1=GET_REGISTED_DATA -arg2= -arg3=VAS -arg4=vms_vas -arg5=vms_vas").append("</vms:cmd>");
                    request.append("<vms:channel>").append("channel_").append(channel).append("</vms:channel>");
                    request.append("<vms:vasgatehost>").append(vasgateHost).append("</vms:vasgatehost>");
                    request.append("<vms:vasgateport>").append(vasgatePort).append("</vms:vasgateport>");
                    request.append("</vms:sendCommand></soapenv:Body></soapenv:Envelope>");
			
                    String huy_info_2 = "";
                    try {
                        huy_info_2 = new Activation().parseXMLtext(new Activation().soapCall(context.get("dataflow_param:utilmodule"), request.toString()), "//*[local-name() = 'return']");
                        context.put("SUBSCRIBER_HUY_INFO_2_REQUEST", request.toString());
                        context.put("SUBSCRIBER_HUY_INFO_2_RESPONSE", huy_info_2);
                        logSql(msisdn, "-1", "CHECK_HUY_MIFC_REQUEST", "5", "CHECK_HUY_MIFC_REQUEST", "VASPRO.SERVICEACTION -subscriber=msisdn -arg0=999 -arg1=GET_REGISTED_DATA -arg2= -arg3=VAS -arg4=vms_vas -arg5=vms_vas", huy_info_2, context.get("ErrorCodeAPI") + "|" + context.get("ErrorDescAPI"), startTime, new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()), "sharingkey= " + sharingkey +",serviceid= " + serviceid + ",packagecode=" + packagecode + ",channel=" + channel + ",dataflow:registerService", script_shop_id);
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                        context.put("ErrorCodeAPI", "-1");
                        context.put("ErrorDescAPI", "Loi khi tra cuu Profile thue bao FC");
                        logSql(msisdn, "-1", "CHECK_HUY_MIFC_REQUEST", "5", "CHECK_HUY_MIFC_REQUEST", "VASPRO.SERVICEACTION -subscriber=msisdn -arg0=999 -arg1=GET_REGISTED_DATA -arg2= -arg3=VAS -arg4=vms_vas -arg5=vms_vas", ex.getMessage(), context.get("ErrorCodeAPI") + "|" + context.get("ErrorDescAPI"), startTime, new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()), "sharingkey= " + sharingkey +",serviceid= " + serviceid + ",packagecode=" + packagecode + ",channel=" + channel + ",dataflow:registerService", script_shop_id);
                        return false;
                    }
                    if (huy_info_2 == null || huy_info_2.equals("GET_REGISTED_DATA_ERR")) {
                        context.put("ErrorCodeAPI", "26");
                        context.put("ErrorDescAPI", "Loi tra cuu lich su huy dich vu");
                        return false;
                    }
                    if (huy_info_2.startsWith("GET_REGISTED_DATA_EXIST")) {
                        //getTime
                        if (context.get("HAVE_CHECK_HUY").equals("1")) {
                            try {
                                String[] p = huy_info_2.split("\\|");
                                if (p.length != 4 && p.length != 5) {
                                    if (p.length != 3) {
                                        context.put("ErrorCodeAPI", "26");
                                        context.put("ErrorDescAPI", "Loi tra cuu lich su huy dich vu " + mi_huy_info + ",p_length:" + p.length);
                                        return false;
                                    }
                                } else {
                                    String pckHuy = p[0].substring(p[0].lastIndexOf(":") + 1).trim();
                                    String time = p[3];
                                    java.util.Date timeHuy = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(time);
                                    Calendar c = Calendar.getInstance();
                                    c.setTime(timeHuy);
                                    c.add(Calendar.DATE, 30);
                                    Calendar now = Calendar.getInstance();
                                    if (c.getTime().after(now.getTime())) {
                                        if (context.get("HAVE_CHECK_HUY_WITH_5000").equals("0")) {
                                            iscontinue = false;
                                        } else
                                            iscontinue = false;

                                        if (!iscontinue) {
                                            //huy trong vong 30 ngay
                                            //return tid+":INFO:Thong bao: Ban KHONG THE dang ky goi dich vu "+packCode+" cho thue bao "+receiver+". Do thue bao da co giao dich HUY/bi HUY goi "+pckHuy+" trong vong 30 ngay truoc do. Chi tiet lien he 9090.";
                                            if (context.get("SEND_SMS").equals("1") && context.get("channel").equals("SMS")) {
                                                String mt = utilServices.getValueFromKeySOAP34($soap_34_extract1,  "SUBSCRIBER_CAN_NOT_HUY_30NGAY").replaceAll("\\{MSISDN}", String.valueOf(msisdn)).replaceAll("\\{PACKAGE_CODE}", context.get("PACKAGE_CODE_SMS")).replaceAll("\\{PACKAGE_CODE_HUY}", pckHuy);
                                                if (mt == null || mt.equals(""))
                                                    mt = utilServices.getValueFromKeySOAP8($soap_8_extract1, "SUBSCRIBER_CAN_NOT_HUY_30NGAY").replaceAll("\\{MSISDN}", String.valueOf(msisdn)).replaceAll("\\{PACKAGE_CODE}", context.get("PACKAGE_CODE_SMS")).replaceAll("\\{PACKAGE_CODE_HUY}", pckHuy);
                                                String ret = sendSms(context.get("sharingkey"), mt);
                                            }
                                            context.put("ErrorCodeAPI", "259");
                                            context.put("ErrorDescAPI", "Thue bao MI/FC co lich su huy goi trong 30 ngay");
                                            return false;
                                        }
                                    }

                                }

                            } catch (Exception ex) {
                                ex.printStackTrace();
                                context.put("ErrorCodeAPI", "26");
                                context.put("ErrorDescAPI", "Loi tra cuu lich su huy dich vu");
                                return false;
                            }
                        }
                    }
                }
            }
            //check profile fastconnect
            if (context.get("SERVICE_KEY").equals("FASTCONNECT")) {
                //check Profile thue bao
                String vasgateHost = context.get("VASGATE_SERVER_IP");
                String vasgatePort = context.get("VASGATE_SERVER_PORT");
                System.out.println("vasgatePort:" + vasgatePort);
                String msisdnTmp = msisdn; if (msisdn.startsWith("84")) msisdnTmp = msisdn.substring(2);
                StringBuilder request = new StringBuilder();
                request.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:vms=\"http://vms.neo\">");
                request.append("<soapenv:Header/><soapenv:Body>");
                request.append("<vms:sendCommand>");
                request.append("<vms:cmd>").append("VIEW.PROFILE -subscriber=").append(msisdnTmp).append("</vms:cmd>");
                request.append("<vms:channel>").append("MSOCIAL_").append(channel).append("</vms:channel>");
                request.append("<vms:vasgatehost>").append(vasgateHost).append("</vms:vasgatehost>");
                request.append("<vms:vasgateport>").append(vasgatePort).append("</vms:vasgateport>");
                request.append("</vms:sendCommand></soapenv:Body></soapenv:Envelope>");
       
                String profile = "";
                try {
                    profile = new Activation().parseXMLtext(new Activation().soapCall(context.get("dataflow_param:utilmodule"), request.toString()), "//*[local-name() = 'return']");
                    context.put("SUBSCRIBER_PROFILE_REQUEST", request.toString());
                    context.put("SUBSCRIBER_PROFILE_RESPONSE", profile);
                    logSql(msisdn, "-1", "CHECK_PROFILE_FC_REQUEST", "5", "CHECK_PROFILE_FC_REQUEST", "VIEW.PROFILE -subscriber=" + msisdnTmp, profile, context.get("ErrorCodeAPI") + "|" + context.get("ErrorDescAPI"), startTime, new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()), "sharingkey= " + sharingkey +",serviceid= " + serviceid + ",packagecode=" + packagecode + ",channel=" + channel + ",dataflow:registerService", script_shop_id);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    context.put("ErrorCodeAPI", "-1");
                    context.put("ErrorDescAPI", "Loi khi tra cuu Profile thue bao FC");
                    logSql(msisdn, "-1", "CHECK_PROFILE_FC_REQUEST", "5", "CHECK_PROFILE_FC_REQUEST", "VIEW.PROFILE -subscriber=" + msisdnTmp, ex.getMessage(), context.get("ErrorCodeAPI") + "|" + context.get("ErrorDescAPI"), startTime, new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()), "sharingkey= " + sharingkey +",serviceid= " + serviceid + ",packagecode=" + packagecode + ",channel=" + channel + ",dataflow:registerService", script_shop_id);
                    return false;
                }
                boolean isTT = false;
                boolean foundFC = false;
                String[] fcProfile = context.get("dataflow_param:FC_PROFILE").split(",");
//  ["FCZ:0","FCT2:0","TTN:0","FCPV:0","TSFC:1","CFC:0","TSFB:1","FCDN:0"];
                for (int i = 0; i < fcProfile.length; i++) {
                    String[] p = fcProfile[i].split(":");
                    if (p[0].equals(profile)) {
                        foundFC = true;
                        if (p[1].equals("0")) isTT = true;
                        else isTT = false;
                        break;
                    }
                }
                if (!foundFC) {
                    //return tid+":INFO:Thong bao: Ban KHONG THE dang ky dich vu "+obj.getServiceKey()+" cho thue bao "+receiver+". Do thue bao khong phai la thue bao FASTCONNECT. Chi tiet lien he 9090.";
                    if (context.get("SEND_SMS").equals("1") && context.get("channel").equals("SMS")) {
                        String mt = utilServices.getValueFromKeySOAP34($soap_34_extract1,"THUE_BAO_NOT_FASTCONNECT").replaceAll("\\{MSISDN}", String.valueOf(msisdn)).replaceAll("\\{SERVICE_KEY}", context.get("SERVICE_KEY_SMS")).replaceAll("\\{SERVICE_NAME}", context.get("SERVICE_KEY_SMS"));
                        if (mt == null || mt.equals(""))
                            mt = utilServices.getValueFromKeySOAP8($soap_8_extract1, "THUE_BAO_NOT_FASTCONNECT").replaceAll("\\{MSISDN}", String.valueOf(msisdn)).replaceAll("\\{SERVICE_KEY}", context.get("SERVICE_KEY_SMS")).replaceAll("\\{SERVICE_NAME}", context.get("SERVICE_KEY_SMS"));
                        System.out.println(msisdn + "||||" + mt);
                        String ret = sendSms(context.get("sharingkey"), mt);
                        System.out.println("ret:" + ret);
                    }
                    context.put("ErrorCodeAPI", "27"); context.put("ErrorDescAPI", "Thue bao khong phai Fastconnect");
                    return false;
                }
                if (isTT) {
                    System.out.println("continue ...");
                } else {
                    
                    boolean activeMoreThan365Days = false;
                    StringBuilder str_soap = new StringBuilder();
                    str_soap.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:vms=\"http://vms.neo\">");
                    str_soap.append("<soapenv:Header/><soapenv:Body>");
                    str_soap.append("<vms:sendCommand>");
                    str_soap.append("<vms:cmd>").append("VIEW.ACTIVEDATE -subscriber=\"+").append(msisdn.substring(2)).append("</vms:cmd>");
                    str_soap.append("<vms:channel>").append("MSOCIAL_").append(channel).append("</vms:channel>");
                    str_soap.append("<vms:vasgatehost>").append(vasgateHost).append("</vms:vasgatehost>");
                    str_soap.append("<vms:vasgateport>").append(vasgatePort).append("</vms:vasgateport>");
                    str_soap.append("</vms:sendCommand></soapenv:Body></soapenv:Envelope>");
                    String startDate = "";
                    try {
                        startDate = new Activation().parseXMLtext(new Activation().soapCall(context.get("dataflow_param:utilmodule"), str_soap.toString()), "//*[local-name() = 'return']");
                        context.put("SUBSCRIBER_ACTIVE_DATE_REQUEST", str_soap.toString());
                        context.put("SUBSCRIBER_ACTIVE_DATE_RESPONSE", startDate);
                        logSql(msisdn, "-1", "CHECK_STARTDATE_FC_REQUEST", "6", "CHECK_STARTDATE_FC_REQUEST", "VIEW.ACTIVEDATE -subscriber=" + msisdn.substring(2), startDate, context.get("ErrorCodeAPI") + "|" + context.get("ErrorDescAPI"), startTime, new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()), "sharingkey= " + sharingkey +",serviceid= " + serviceid + ",packagecode=" + packagecode + ",channel=" + channel + ",dataflow:registerService", script_shop_id);
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                        context.put("ErrorCodeAPI", "-1");
                        context.put("ErrorDescAPI", "Loi khi tra cuu ngay kich hoat FC");
                        logSql(msisdn, "-1", "CHECK_STARTDATE_FC_REQUEST", "6", "CHECK_STARTDATE_FC_REQUEST", "VIEW.ACTIVEDATE -subscriber=" + msisdn.substring(2), ex.getMessage(), context.get("ErrorCodeAPI") + "|" + context.get("ErrorDescAPI"), startTime, new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()), "sharingkey= " + sharingkey +",serviceid= " + serviceid + ",packagecode=" + packagecode + ",channel=" + channel + ",dataflow:registerService", script_shop_id);
                        return false;
                    }
                    System.out.println("dateActive:" + startDate + ",msisdn:" + msisdn);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    if (startDate == null || startDate.equals(""))
                        activeMoreThan365Days = false;
                    else {
                        try {
                            Date cDate = sdf.parse(startDate);
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(cDate);
                            cal.add(Calendar.DATE, 365);
                            Calendar now = Calendar.getInstance();
                            System.out.println("------------------DTAC_DATE:" + checkStartDate);
                            System.out.println("------------------DTAC_DATE+365:" + sdf.format(cal.getTime()));
                            System.out.println("------------------NOW_DATE:" + sdf.format(now.getTime()));
                            if (cal.getTime().after(now.getTime()) || sdf.format(cal.getTime()).equals(sdf.format(now.getTime()))) {
                                activeMoreThan365Days = false;
                            } else
                                activeMoreThan365Days = true;
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                            activeMoreThan365Days = true;
                        }

                    }
                    if (!activeMoreThan365Days) {
                        //return tid+":INFO:Thong bao: Ban KHONG THE dang ky dich vu "+obj.getServiceKey()+" cho thue bao "+receiver+". Do day la thue bao Fast Connect tra sau co thoi gian kich hoat trong vong 12 thang truoc do. Chi tiet lien he 9090";
                        if (context.get("SEND_SMS").equals("1") && context.get("channel").equals("SMS")) {
                            String mt = utilServices.getValueFromKeySOAP34($soap_34_extract1, "THUE_BAO_FASTCONNECT_TS").replaceAll("\\{MSISDN}", String.valueOf(msisdn)).replaceAll("\\{SERVICE_KEY}", context.get("SERVICE_KEY_SMS")).replaceAll("\\{SERVICE_NAME}", context.get("SERVICE_KEY_SMS"));
                            if (mt == null || mt.equals(""))
                                mt = utilServices.getValueFromKeySOAP8($soap_8_extract1,"THUE_BAO_FASTCONNECT_TS").replaceAll("\\{MSISDN}", String.valueOf(msisdn)).replaceAll("\\{SERVICE_KEY}", context.get("SERVICE_KEY_SMS")).replaceAll("\\{SERVICE_NAME}", context.get("SERVICE_KEY_SMS"));
                            String ret = sendSms(context.get("sharingkey"), mt);
                            System.out.println("ret:" + ret);
                        }
                        context.put("ErrorCodeAPI", "28");
                        context.put("ErrorDescAPI", "Thue bao Fastconnect co thoi gian kich hoat trong 12 thang");
                        logSql(msisdn, "-1", "CHECK_THUE_BAO_HUY_DICH_VU_MI_FC", "7", "CHECK_THUE_BAO_HUY_DICH_VU_MI_FC", context.get("checkhuy_cmdRequest_with_value") + "|stepResult=" + stepResult, context.get("checkhuy_cmdresponse_with_value") + "|stepResult=" + stepResult, context.get("ErrorCodeAPI") + "|" + context.get("ErrorDescAPI"), startTime, new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()), "sharingkey= " + sharingkey +",serviceid= " + serviceid + ",packagecode=" + packagecode + ",channel=" + channel + ",dataflow:registerService", script_shop_id);
                        return false;
                    } else {
                        System.out.println("FC TS continue...");
                    }
                }
            }
            System.out.println("AAAAAAAAAAAAAAAAAAA-------------BBBBBBBBBBBBBBBBBBBb " + msisdn + " - " + String.valueOf(iscontinue));
            //Thiet lap tham so iscontinue (de thiet lap hoa hong 5000 cho cac giao dich MI/FC thue bao co log huy trong 30 ngay va dang ky goi cuoc 30 ngay
            context.put("BILLING_ISCONTINUE", String.valueOf(iscontinue));
            if (packageCode.equals("KM1") || packageCode.equals("KM2") || packageCode.equals("KM3") || packageCode.equals("KM4") || packageCode.equals("KM6") || packageCode.equals("KM7") || packageCode.equals("KM8")) {
                boolean checkkm = true;//checkKM(receiver);
                if (checkkm) {
                    try {
                        String p = mi_status_info.split("\\|")[0];
                        p = p.substring(p.lastIndexOf(" ")).trim();
                        if (p != null && !p.equals("") && !p.equals("NULL")) {
                            //sendSms(this.sender, receiver, "Ban dang su dung goi Mobile Internet "+p+". Ban can phai huy goi truoc khi dang ky CTKM. Chi tiet lien he 9090. Xin cam on");
                            //return tid+":INFO:Khach hang "+receiver+" dang su dung goi Mobile Internet "+p+". Khach hang can phai huy goi truoc khi dang ky CTKM. Chi tiet lien he 9090. Xin cam on";
                            if (context.get("SEND_SMS").equals("1") && context.get("channel").equals("SMS")) {
                                String mt = utilServices.getValueFromKeySOAP34($soap_34_extract1,"MI_CTKM_NOTICE").replaceAll("\\{MSISDN}", String.valueOf(msisdn)).replaceAll("\\{SERVICE_KEY}", context.get("SERVICE_KEY_SMS")).replaceAll("\\{PACKAGE_CODE_IN_USE}", context.get("PACKAGE_CODE_IN_USE")).replaceAll("\\{SERVICE_NAME}", p);
                                if (mt == null || mt.equals(""))
                                    mt = utilServices.getValueFromKeySOAP8($soap_8_extract1, "MI_CTKM_NOTICE").replaceAll("\\{MSISDN}", String.valueOf(msisdn)).replaceAll("\\{SERVICE_KEY}", context.get("SERVICE_KEY_SMS")).replaceAll("\\{PACKAGE_CODE_IN_USE}", context.get("PACKAGE_CODE_IN_USE")).replaceAll("\\{SERVICE_NAME}", p);
                                String ret = sendSms(context.get("sharingkey"), mt);
                                System.out.println("ret:" + ret);
                            }
                            context.put("ErrorCodeAPI", "29");
                            context.put("ErrorDescAPI", "Thue bao can huy goi truoc khi dang ky CTKM");
                            logSql(msisdn, "-1", "CHECK_THUE_BAO_HUY_DICH_VU_MI_FC", "7", "CHECK_THUE_BAO_HUY_DICH_VU_MI_FC", context.get("checkhuy_cmdRequest_with_value") + "|stepResult=" + stepResult, context.get("checkhuy_cmdresponse_with_value") + "|stepResult=" + stepResult, context.get("ErrorCodeAPI") + "|" + context.get("ErrorDescAPI"), startTime, new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()),  "sharingkey= " + sharingkey +",serviceid= " + serviceid + ",packagecode=" + packagecode + ",channel=" + channel + ",dataflow:registerService", script_shop_id);
                            return false;
                        } else {
                            //process below
                        }
                    } catch (Exception ex) {
                        // fix data
                        //return tid + ":INFO:He thong dang ban xin moi thu lai sau.";
                        return false;
                    }
                } else {
                    //return tid+":INFO:Thue bao "+receiver+" khong nam trong danh sach duoc khuyen mai. Chi tiet lien he 9090. Xin cam on";
                }
            } else {
                String[] info = checkMIBigger(mi_status_info, serviceId, packageCode);
                String[] miinfo = null;
                if (info[0].equals("2") || info[0].equals("3"))
                    miinfo = info;
                System.out.println("miinfo:" + miinfo);
                System.out.println("info:" + info[0]);
                if (info[0].equals("2")) {

                    if (packageCode.equals("MAX10") || packageCode.equals("MAX30")) {

                    } else {
                        //retval[0] = "2";retval[1]=packageCode;retval[2]=registerPckPrice;retval[3]=pckNeedCheck;retval[4]=usedPckPrice;retval[5]=capacity;retval[6]=cycle;
                        //return tid+":INFO:Thong bao: Khach hang "+receiver+" dang su dung goi cuoc FC "+info[1]+" gia cuoc "+info[2]+"dong. Ban KHONG THE gioi thieu dang ky voi goi cuoc thap hon, hay gioi thieu goi cuoc CAO HON hoac goi 9090 de duoc ho tro.";
                        if (context.get("SEND_SMS").equals("1") && context.get("channel").equals("SMS")) {
                            String mt = utilServices.getValueFromKeySOAP34($soap_34_extract1,"THONG_BAO_PRICE_HIGHER").replaceAll("\\{MSISDN}", String.valueOf(msisdn)).replaceAll("\\{SERVICE_KEY}", context.get("SERVICE_KEY_SMS")).replaceAll("\\{SERVICE_NAME}", context.get("SERVICE_KEY_SMS")).replaceAll("\\{PACKAGE_CODE}", info[3]).replaceAll("\\{PACKAGE_PRICE}", info[4]);
                            if (mt == null || mt.equals(""))
                                mt = utilServices.getValueFromKeySOAP8($soap_8_extract1,"THONG_BAO_PRICE_HIGHER").replaceAll("\\{MSISDN}", String.valueOf(msisdn)).replaceAll("\\{SERVICE_KEY}", context.get("SERVICE_KEY_SMS")).replaceAll("\\{SERVICE_NAME}", context.get("SERVICE_KEY_SMS")).replaceAll("\\{PACKAGE_CODE}", info[3]).replaceAll("\\{PACKAGE_PRICE}", info[4]);

                            String ret = sendSms(context.get("sharingkey"), mt);
                            System.out.println("ret:" + ret);
                        }
                        context.put("ErrorCodeAPI", "30");
                        context.put("ErrorDescAPI", "Khach hang dang su dung goi cuoc co gia cao hon.");
                        logSql(msisdn, "-1", "CHECK_THUE_BAO_SU_DUNG_GOI_CUOC_CAOTHAP_DICH_VU_MI_FC", "7", "CHECK_THUE_BAO_SU_DUNG_GOI_CUOC_CAOTHAP_DICH_VU_MI_FC", context.get("checkservice_cmdRequest_with_value") + "|stepResult=" + stepResult, context.get("checkservice_cmdRequest_with_value") + "|stepResult=" + stepResult, context.get("ErrorCodeAPI") + "|" + context.get("ErrorDescAPI"), startTime, new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()),  "sharingkey= " + sharingkey +",serviceid= " + serviceid + ",packagecode=" + packagecode + ",channel=" + channel + ",dataflow:registerService", script_shop_id);
                        return false;
                    }
                } else if (info[0].equals("1")) {
                    //return tid+":INFO:So thue bao "+receiver+" da dang ky su dung dich vu "+obj.getServiceKey()+". De nghi dang ky khong thanh cong.";
                    if (context.get("SEND_SMS").equals("1") && context.get("channel").equals("SMS")) {
                        String mt = utilServices.getValueFromKeySOAP34($soap_34_extract1,"SERVICE_IN_USE").replaceAll("\\{MSISDN}", String.valueOf(msisdn)).replaceAll("\\{SERVICE_KEY}", context.get("SERVICE_KEY_SMS")).replaceAll("\\{SERVICE_NAME}", context.get("SERVICE_KEY_SMS"));
                        if (mt == null || mt.equals(""))
                            mt = utilServices.getValueFromKeySOAP8($soap_8_extract1,"SERVICE_IN_USE").replaceAll("\\{MSISDN}", String.valueOf(msisdn)).replaceAll("\\{SERVICE_KEY}", context.get("SERVICE_KEY_SMS")).replaceAll("\\{SERVICE_NAME}", context.get("SERVICE_KEY_SMS"));
                        String ret = sendSms(context.get("sharingkey"), mt);
                        System.out.println("ret:" + ret);
                    }
                    context.put("ErrorCodeAPI", "31");
                    context.put("ErrorDescAPI", "Khach hang dang su dung goi cuoc co gia bang goi dang dang ky.");
                    logSql(msisdn, "-1", "CHECK_THUE_BAO_HUY_DICH_VU_MI_FC", "7", "CHECK_THUE_BAO_HUY_DICH_VU_MI_FC", context.get("checkhuy_cmdRequest_with_value") + "|stepResult=" + stepResult, context.get("checkhuy_cmdresponse_with_value") + "|stepResult=" + stepResult, context.get("ErrorCodeAPI") + "|" + context.get("ErrorDescAPI"), startTime, new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()), "sharingkey= " + sharingkey +",serviceid= " + serviceid + ",packagecode=" + packagecode + ",channel=" + channel + ",dataflow:registerService", script_shop_id);
                    return false;
                } else if (info[0].equals("10")) {
                    //return tid+":INFO:Thong bao: Khach hang "+receiver+" dang su dung goi cuoc MI "+info[1]+" gia cuoc "+info[2]+"dong. Ban KHONG THE gioi thieu dang ky voi goi cuoc thap hon hoac bang, hay gioi thieu goi cuoc CAO HON hoac goi 9090 de duoc ho tro.";
                    if (context.get("SEND_SMS").equals("1") && context.get("channel").equals("SMS")) {
                        String mt = utilServices.getValueFromKeySOAP34($soap_34_extract1,"MI_KM120_NOTICE_CHAN").replaceAll("\\{MSISDN}", String.valueOf(msisdn)).replaceAll("\\{SERVICE_KEY}", context.get("SERVICE_KEY_SMS")).replaceAll("\\{SERVICE_NAME}", context.get("SERVICE_KEY_SMS")).replaceAll("\\{PACKAGE_CODE}", info[3]).replaceAll("\\{PACKAGE_PRICE}", info[4]);
                        if (mt == null || mt.equals(""))
                            mt = utilServices.getValueFromKeySOAP8($soap_8_extract1, "MI_KM120_NOTICE_CHAN").replaceAll("\\{MSISDN}", String.valueOf(msisdn)).replaceAll("\\{SERVICE_KEY}", context.get("SERVICE_KEY_SMS")).replaceAll("\\{SERVICE_NAME}", context.get("SERVICE_KEY_SMS")).replaceAll("\\{PACKAGE_CODE}", info[3]).replaceAll("\\{PACKAGE_PRICE}", info[4]);
                        String ret = sendSms(context.get("sharingkey"), mt);
                        System.out.println("ret:" + ret);
                    }
                    context.put("ErrorCodeAPI", "32");
                    context.put("ErrorDescAPI", "Khach hang dang su dung goi cuoc co gia bang goi dang dang ky.");
                    logSql(msisdn, "-1", "CHECK_THUE_BAO_HUY_DICH_VU_MI_FC", "7", "CHECK_THUE_BAO_HUY_DICH_VU_MI_FC", context.get("checkhuy_cmdRequest_with_value") + "|stepResult=" + stepResult, context.get("checkhuy_cmdresponse_with_value") + "|stepResult=" + stepResult, context.get("ErrorCodeAPI") + "|" + context.get("ErrorDescAPI"), startTime, new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()),  "sharingkey= " + sharingkey +",serviceid= " + serviceid + ",packagecode=" + packagecode + ",channel=" + channel + ",dataflow:registerService", script_shop_id);
                    return false;
                } else if (info[0].equals("-1")) {
                    context.put("ErrorCodeAPI", "-1"); context.put("ErrorDescAPI", "Loi khong tra cuu duoc thong tin.");
                    logSql(msisdn, "-1", "CHECK_THUE_BAO_HUY_DICH_VU_MI_FC", "7", "CHECK_THUE_BAO_HUY_DICH_VU_MI_FC", context.get("checkhuy_cmdRequest_with_value") + "|stepResult=" + stepResult, context.get("checkhuy_cmdresponse_with_value") + "|stepResult=" + stepResult, context.get("ErrorCodeAPI") + "|" + context.get("ErrorDescAPI"), startTime, new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()),  "sharingkey= " + sharingkey +",serviceid= " + serviceid + ",packagecode=" + packagecode + ",channel=" + channel + ",dataflow:registerService", script_shop_id);
                    String ret = sendSms(context.get("sharingkey"), "Giao dich khong thanh cong do ma goi cuoc thue bao dang su dung chua duoc cap nhat tren he thong.");
                    return false;
                }

            }
            stepResult = true;
            logSql(msisdn, "-1", "CHECK_THUE_BAO_HUY_DICH_VU_MI_FC", "7", "CHECK_THUE_BAO_HUY_DICH_VU_MI_FC", context.get("checkhuy_cmdRequest_with_value") + "|stepResult=" + stepResult, context.get("checkhuy_cmdresponse_with_value") + "|stepResult=" + stepResult, context.get("ErrorCodeAPI") + "|" + context.get("ErrorDescAPI"), startTime, new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()),  "sharingkey= " + sharingkey +",serviceid= " + serviceid + ",packagecode=" + packagecode + ",channel=" + channel + ",dataflow:registerService", script_shop_id);
            System.out.println("ACV--");
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            return false;
        }
    }

}

