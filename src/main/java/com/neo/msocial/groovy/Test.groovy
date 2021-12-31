package com.neo.msocial.groovy

import com.neo.msocial.utils.RedisUtils

def parseXML = { String soapResponse ->
    RedisUtils context = new RedisUtils();
    try{
        if(!soapResponse.equals("<ResponseResult/>") && !soapResponse.equals("&lt;ResponseResult/>")){
            rootNode = new XmlSlurper().parseText(soapResponse);
            for(def record : rootNode.record.children()){
                context.put(record.name(),record.text());
            }
        }
    }catch(Exception ex){
        System.out.prinln(ex.getMessage());
        context.set("SCRIPT_MT_EMPTY",true);
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
def value = '''<ns:queryResponse xmlns:ns="http://vms.neo">
    <ns:return>[{"CHECK_ACCOUNT_API":"0"}]</ns:return>
</ns:queryResponse>'''
println getValueFromKey(value, 0);