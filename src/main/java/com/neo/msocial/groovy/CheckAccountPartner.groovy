package com.neo.msocial.groovy

import com.neo.msocial.service.Activation
import com.neo.msocial.service.ParseXml
import com.neo.msocial.utils.RedisUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


class CheckAccountPartner {

    @Autowired
    private RedisUtils context;

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

    boolean check(String soap_37_extract1, String key) {
        boolean ret = false;
        try {
            //String content = "$content";
            String content = "$key";
        } catch (Exception ex) {
            context.put("content", context);
        }
        try {
            //String check = getValueFromKey(soap_37_extract1,key);
            // System.out.println(""""CHECK_ACCOUNT_API:$username,check:"""+check);
            // System.out.println(""CHECK_ACCOUNT_API:$password,check:""+check);
            if (Integer.parseInt(soap_37_extract1) < 1) ret = false;
            else ret = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            ret = false;
        } finally {
            if (!ret) {
                context.put("ErrorCodeAPI", "-2");
                context.put("ErrorDescAPI", "Username/Password khong dung");
            }
            return ret;
        }
    }

}
