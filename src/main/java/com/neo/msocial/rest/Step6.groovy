package com.neo.msocial.rest

import com.neo.msocial.service.Activation
import com.neo.msocial.service.ParseXml
import com.neo.msocial.utils.RedisUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Step6 {

    @Autowired
    private RedisUtils context;

    def sendSms = { String receiver, String messageSms ->
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

    def getValueFromKeyMultiRecords = { String body, String rootTag, String tagName, String key, String value ->
        String ret = new ParseXml().getValueFromKey(body, rootTag, tagName, key, value);
        return ret;
    }

    public boolean check() {
        boolean retval = false;
        boolean checkSms = false;
        System.out.println("aaaaa000000");
        context.put("debug_start_time", System.currentTimeMillis());
        System.out.println("IP_LIST-----------" + context.get("dataflow_param:IP_LIST_REGISTER_PARTNER"));
        System.out.println("IP_CLIENT-----------" + context.get("IP_CLIENT"));
/*
if(context.get("dataflow_param:IP_LIST_REGISTER_PARTNER").indexOf(context.get("IP_CLIENT")<0)
{
 context.put("ErrorCodeAPI","222");context.put("ErrorDescAPI","IP khong cho phep");
 return false;
}
*/
        try {

            def rootNode = new XmlSlurper().parseText($soap_35_extract1);
            for (def record : rootNode.record.children()) {
                context.put(record.text().split(":")[0], record.text().split(":")[1]);
            }
            System.out.println("aaaaa000000 " + content);
            System.out.println("aaaaa000000 " + $soap_35_extract1);
            if (context.get("content") == null || context.get("content").equals("")) context.put("content", " ");
            //xu ly tin nhan lap
            /*
    System.out.println("aaaaa000000 aaaa");
    String request = """
    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:vms="http://vms.neo">
       <soapenv:Header/>
       <soapenv:Body>
          <vms:queryXml>
      <!--Optional:-->
      <vms:Service>check_sms_duplicate</vms:Service>
      <!--Optional:-->
      <vms:Provider>default</vms:Provider>
      <!--Optional:-->
      <vms:ParamSize>2</vms:ParamSize>
      <vms:P1>$msisdn</vms:P1>
      <vms:P2>$content</vms:P2>
          </vms:queryXml>
       </soapenv:Body>
    </soapenv:Envelope>
    """;
    System.out.println(request);
    String ret = "";
    try{
     ret = new neo.service.Activation().parseXMLtext(new neo.service.Activation().soapCall(context.get("dataflow_param:sqlmodule"),request),"//*[local-name() = 'return']");
     System.out.println("ret:a"+ret);
     String total = getValueFromKey(ret,"TOTAL");
     if(total == null || total.equals("")|| total.equals("null")) total = "0";
     if (Integer.parseInt(total)>1){
      String mt = "Giao dich dang duoc he thong xu ly.Ket qua se duoc thong bao sau.Xin cam on.";
      context.put("ErrorCodeAPI","222");context.put("ErrorDescAPI","Giao dich dang duoc he thong xu ly.Ket qua se duoc thong bao sau.Xin cam on.");
      ret = sendSms(context.get("sharingkey"),mt);
      return false;
     }
    }catch(Exception ex){System.out.println(ex.getMessage());
     context.put("ErrorCodeAPI","22");context.put("ErrorDescAPI","Diem ban khong duoc gioi thieu cho thue bao");
     return false;
    }
    */
            rootNode = new XmlSlurper().parseText($soap_2_extract1);
            String errorCode = rootNode.Parameter.ErrorCode.text();

            if (!errorCode.equals("0")) {
                String errorDesc = rootNode.Parameter.ErrorDesc.text();
                context.put("ErrorCodeAPI", errorCode);
                context.put("ErrorDescAPI", errorDesc);

            } else {

                String time = getValueFromKey($soap_2_extract1, "time_validate");
                System.out.println("VALIDATE_TIME:" + time);
                if (time.equals("0")) {
                    context.put("ErrorCodeAPI", "55");
                    context.put("ErrorDescAPI", "Thoi gian kich ban khong phu hop");
                } else {
                    rootNode.record.children().each {
                        context.put(it.name(), it.text());
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
                    ret = sendSms(context.get("sharingkey"), mt);
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