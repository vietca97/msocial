//package com.neo.msocial.groovy
//
//import com.neo.msocial.service.Activation
//import com.neo.msocial.service.ParseXml
//
//import java.text.*;
//import java.util.*;
//def logSql = {String msisdn, String transactionId, String stepName, String stepIndex,String stepKey,String stepInput,String stepOutput,String stepAction,String startTime, String endTime,String inputParameter,String scriptShopId ->
//    String request = """
//				<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:vms="http://vms.neo">
//				   <soapenv:Header/>
//				   <soapenv:Body>
//				      <vms:updateXml>
//				         <!--Optional:-->
//				         <vms:Service>sql_log_transaction_step</vms:Service>
//				         <!--Optional:-->
//				         <vms:Provider>default</vms:Provider>
//				         <!--Optional:-->
//				        <vms:ParamSize>12</vms:ParamSize>
//				         <!--Optional:-->
//				         <vms:P1>$transactionId</vms:P1>
//				         <!--Optional:-->
//				         <vms:P2>$stepName</vms:P2>
//				         <!--Optional:-->
//				         <vms:P3>$stepIndex</vms:P3>
//				         <!--Optional:-->
//				         <vms:P4>$stepKey</vms:P4>
//				         <!--Optional:-->
//				         <vms:P5>$stepInput</vms:P5>
//				         <!--Optional:-->
//				         <vms:P6>$stepOutput</vms:P6>
//				         <!--Optional:-->
//				         <vms:P7>$stepAction</vms:P7>
//				         <!--Optional:-->
//				         <vms:P8>$startTime</vms:P8>
//				         <!--Optional:-->
//				         <vms:P9>$endTime</vms:P9>
//				         <!--Optional:-->
//				         <vms:P10>$msisdn</vms:P10>
//				         <!--Optional:-->
//				         <vms:P11>$inputParameter</vms:P11>
//				         <vms:P12>$scriptShopId</vms:P12>
//				      </vms:updateXml>
//				   </soapenv:Body>
//				</soapenv:Envelope>
//				""";
//    String result = new Activation().soapCall(context.get("dataflow_param:sqlmodule"),request);
//
//}
//def initDuyTriTransaction = {String transactionId ->
//    //dang ky dich vu thanh cong, cap nhat hoa hong cai dat, khoi tao giao dich duy tri
//    if(context.get("HAVE_MAINTAIN").equals("1")){
//        //insert giao dich duy tri
//        try{
//            request = """
//			<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:vms="http://vms.neo">
//			   <soapenv:Header/>
//			   <soapenv:Body>
//			      <vms:value>
//			         <!--Optional:-->
//			         <vms:Service>init_duytri_transaction</vms:Service>
//			         <!--Optional:-->
//			         <vms:Provider>default</vms:Provider>
//			         <!--Optional:-->
//			         <vms:ParamSize>2</vms:ParamSize>
//			         <!--Optional:-->
//			         <vms:P1>$transactionId</vms:P1>
//			         <vms:P2>$MAINTAIN_CYCLE</vms:P2>
//			      </vms:value>
//			   </soapenv:Body>
//			</soapenv:Envelope>
//			""";
//            result = new Activation().parseXMLtext(new Activation().soapCall(context.get("dataflow_param:sqlmodule"),request),"//*[local-name() = 'return']");
//
//        }catch(Exception ex){
//            System.out.println("init duy tri transaction error "+ex.getMessage());
//        }
//
//    }
//}
////update transaction
//def updateTransaction = {String transactionId,String transactionStatus,String transactionResponse,
//                         int channel_id_confirm,String content_extra,
//                         double price,int charging ,int charging_type,String charging_status,String charging_time,
//                         double avenue_one,double avenue_month,double avenue_agent_one,double avenue_agent_month,String mobType,String startActiveDate,String monitorCdr, int hhType,int serviceId ->
//    String result = "";
//    try{
//        request = """
//		<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:vms="http://vms.neo">
//		   <soapenv:Header/>
//		   <soapenv:Body>
//		      <vms:update>
//		         <!--Optional:-->
//		         <vms:Service>update_transaction</vms:Service>
//		         <!--Optional:-->
//		         <vms:Provider>default</vms:Provider>
//		         <!--Optional:-->
//		         <vms:ParamSize>19</vms:ParamSize>
//		         <!--Optional:-->
//		         <vms:P1>$transactionStatus</vms:P1>
//		         <vms:P2>$transactionResponse</vms:P2>
//		         <vms:P3>$channel_id_confirm</vms:P3>
//		         <vms:P4>$content_extra</vms:P4>
//		         <vms:P5>$price</vms:P5>
//		         <vms:P6>$charging</vms:P6>
//		         <vms:P7>$charging_type</vms:P7>
//		         <vms:P8>$charging_status</vms:P8>
//		         <vms:P9>$charging_time</vms:P9>
//		         <vms:P10>$avenue_one</vms:P10>
//		         <vms:P11>$avenue_month</vms:P11>
//		         <vms:P12>$avenue_agent_one</vms:P12>
//		         <vms:P13>$avenue_agent_month</vms:P13>
//		         <vms:P14>$mobType</vms:P14>
//		         <vms:P15>$startActiveDate</vms:P15>
//		         <vms:P16>$monitorCdr</vms:P16>
//			 <vms:P17>$hhType</vms:P17>
//			 <vms:P18>$serviceId</vms:P18>
// 			 <vms:P19>$transactionId</vms:P19>
//		      </vms:update>
//		   </soapenv:Body>
//		</soapenv:Envelope>
//		""";
//        System.out.println(request);
//        result = new Activation().parseXMLtext(new Activation().soapCall(context.get("dataflow_param:sqlmodule"),request),"//*[local-name() = 'return']");
//
//    }catch(Exception ex){
//        ex.printStackTrace();
//        System.out.println("update transaction error "+ex.getMessage());
//        result = ex.getMessage();
//    }
//    return result;
//}
////update transaction
//def getHoahong = {String transactionId,double price,int mobType,String startActiveDate, int cs ->
//    String result = "";
//    try{
//        request = """
//		<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:vms="http://vms.neo">
//		   <soapenv:Header/>
//		   <soapenv:Body>
//		      <vms:value>
//		         <vms:Service>get_hh</vms:Service>
//		         <vms:Provider>default</vms:Provider>
//		         <vms:ParamSize>5</vms:ParamSize>
//		         <vms:P1>$transactionId</vms:P1>
//		         <vms:P2>$price</vms:P2>
//		         <vms:P3>$mobType</vms:P3>
//		         <vms:P4>$startActiveDate</vms:P4>
//		         <vms:P5>$cs</vms:P5>
//		      </vms:value>
//		   </soapenv:Body>
//		</soapenv:Envelope>
//		""";
//        result = new Activation().parseXMLtext(new Activation().soapCall(context.get("dataflow_param:sqlmodule"),request),"//*[local-name() = 'return']");
//
//    }catch(Exception ex){
//        System.out.println("update transaction error "+ex.getMessage());
//        result = ex.getMessage();
//    }
//    return result;
//}
//return status|charging|price|error_code
//
//def getPriceFromResult = {String input,String serviceKey ->
//    String status = "";
//    String charging = "";
//    String price = "";
//    String errordesc="";
//    String errorcode = "";
//    try{
//        if(serviceKey.equals("MCA")){
//            try{
//                String[] info = input.split("\\|");
//                for(int i=0;i<info.length;i++) System.out.println(info[i]+"--------");
//                if(info[0].equals("true")) status= "1";
//                else status = "0";
//                charging = info[1];
//                price = info[2];
//                return status+"|"+charging+"|"+price+"|OK|"+input;
//            }catch(Exception ex){
//                ex.printStackTrace();
//                return "0|0|0|-1|"+input;
//            }
//        }
//        else if (serviceKey.equals("FUNRING")){
//            try{
//                //0|9000#2015-02-10 08:42:25#2015-03-11 23:59:59
//                String[] info = input.split("\\|");
//                try{
//                    double priceCheck = Double.parseDouble(info[1].substring(0,info[1].indexOf("#")));
//                    price = String.valueOf(priceCheck);
//                    return info[0]+"|1|"+price+"|OK|"+input;
//                }catch(Exception ex){
//                    return "0|0|0|-1|"+input;
//                }
//            }catch(Exception ex){
//                ex.printStackTrace();
//                return "0|0|0|-1|"+input;
//            }
//        }
//        else if (serviceKey.equals("FUNRINGME")){
//            try{
//                //000000|5000#2015-02-12 11:21:23#2015-03-13 23:59:59
//                String[] info = input.split("\\|");
//                try{
//                    double priceCheck = Double.parseDouble(info[1].substring(0,info[1].indexOf("#")));
//                    price = String.valueOf(priceCheck);
//                    return info[0]+"|1|"+price+"|OK|"+input;
//                }catch(Exception ex){
//                    return "0|0|0|-1|"+input;
//                }
//            }catch(Exception ex){
//                ex.printStackTrace();
//                return "0|0|0|-1|"+input;
//            }
//        }
//        else if (serviceKey.equals("NHAC_CHO_FUNRING")){
//            try{
//                //000000|5000#2015-02-12 11:21:23#2015-03-13 23:59:59
//                String[] info = input.split("\\|");
//                try{
//
//                    double priceCheck = Double.parseDouble(info[1].substring(0,info[1].indexOf("#")));
//                    price = String.valueOf(priceCheck);
//                    return info[0]+"|1|"+price+"|OK|"+input;
//                }catch(Exception ex){
//                    ex.printStackTrace();
//                    return "0|0|0|-1|"+input;
//                }
//            }catch(Exception ex){
//                ex.printStackTrace();
//                return "0|0|0|-1|"+input;
//            }
//        }
//        else if (serviceKey.equals("FUNRING_MULTI_CHUKY")){
//            try{
//                //000000|5000#2015-02-12 11:21:23#2015-03-13 23:59:59
//                String[] info = input.split("\\|");
//                try{
//                    double priceCheck = Double.parseDouble(info[1].substring(0,info[1].indexOf("#")));
//                    price = String.valueOf(priceCheck);
//                    return info[0]+"|1|"+price+"|OK|"+input;
//                }catch(Exception ex){
//                    return "0|0|0|-1|"+input;
//                }
//            }catch(Exception ex){
//                ex.printStackTrace();
//                return "0|0|0|-1|"+input;
//            }
//        }
//        else if (serviceKey.equals("MCA_MULTI_CHUKY")){
//            try{
//                //1|16000
//                String[] info = input.split("\\|");
//                try{
//                    double priceCheck = Double.parseDouble(info[1]);
//                    price = String.valueOf(priceCheck);
//                    return info[0]+"|1|"+info[1]+"|OK|"+input;
//                }catch(Exception ex){
//                    return "0|0|0|-1|"+input;
//                }
//            }catch(Exception ex){
//                ex.printStackTrace();
//                return "0|0|0|-1|"+input;
//            }
//        }
//        else if (serviceKey.equals("BCVQT")){
//            try{
//                status = "1";
//                try{
//                    errorcode = input.substring(0,input.indexOf(":"));
//                    errordesc = input.substring(input.indexOf("|")+1);
//                    double priceCheck = Double.parseDouble( input.substring(input.indexOf(":")+1,input.indexOf("|")).trim());
//                    price = String.valueOf(priceCheck);
//                    charging = "1";
//                    return status +"|"+charging+"|"+price+"|OK_"+errorcode+"|"+errordesc;
//                }catch(Exception ex){
//                    ex.printStackTrace();
//                    return "0|0|0|-1|"+input;
//                }
//            }catch(Exception ex){
//                return "0|0|0|-1|"+input;
//            }
//
//        }
//        else  if (serviceKey.equals("Kenh1") || serviceKey.equals("HOC_LIEN") ){
//            try{
//                //1#Success#K3,90,1000
//                String[] inf = input.split(",");
//                price = inf[2];
//                if(inf[0].startsWith("1#")){
//                    charging = "1";
//                    status = "1";
//                }
//                else{charging="0";status="0";}
//                errorcode=inf[0];
//                errordesc=inf[0];
//                return status +"|"+charging+"|"+price+"|OK_"+errorcode+"|"+errordesc;
//            }catch(Exception ex){
//                ex.printStackTrace();
//                return "0|0|0|-1|"+input;
//            }
//        }
//        else  if (serviceKey.equals("MOBILEINTERNET") || serviceKey.equals("CALLBARRING") || serviceKey.equals("COMBO") || serviceKey.equals("COMBO_TT3")  || serviceKey.equals("COMBO_TT5")|| serviceKey.equals("COMBO_TT4")|| serviceKey.equals("BONGLUA")|| serviceKey.equals("KHUYEN_MAI_SIM")|| serviceKey.equals("KHUYEN_MAI_4C100")|| serviceKey.equals("COMBO_DATA_VAS")|| serviceKey.equals("COMBO_CTY1")){
//            try{
//                status = "1";
//                try{
//                    errorcode = input.substring(0,input.indexOf(":"));
//                    errordesc = input.substring(input.indexOf("|")+1);
//                    double priceCheck = Double.parseDouble(input.substring(input.indexOf(":")+1,input.indexOf("|")).trim());
//                    price = String.valueOf(priceCheck);
//                    charging = "1";
//                    return status +"|"+charging+"|"+price+"|OK_"+errorcode+"|"+errordesc;
//                }catch(Exception ex){
//                    ex.printStackTrace();
//                    return "0|0|0|-1|"+input;
//                }
//
//            }catch(Exception ex){
//                return "0|0|0|-1|"+input;
//            }
//        }
//        else  if (serviceKey.equals("FASTCONNECT") ||serviceKey.equals("DICH_VU_4G")||serviceKey.equals("GLOBAL_SAVING")||serviceKey.equals("THANTAI") ||serviceKey.equals("GAME_DATA") ||serviceKey.equals("DICH_VU_3_KHIA")){
//            try{
//                status = "1";
//                try{
//                    errorcode = input.substring(0,input.indexOf(":"));
//                    errordesc = input.substring(input.indexOf("|")+1);
//                    double priceCheck = Double.parseDouble(input.substring(input.indexOf(":")+1,input.indexOf("|")).trim());
//                    price = String.valueOf(priceCheck);
//                    charging = "1";
//                    return status +"|"+charging+"|"+price+"|OK_"+errorcode+"|"+errordesc;
//                }catch(Exception ex){
//                    ex.printStackTrace();
//                    return "0|0|0|-1|"+input;
//                }
//
//            }catch(Exception ex){
//                return "0|0|0|-1|"+input;
//            }
//        }
//        else {
//            try{
//                status = "1";
//                if(context.get("LIKE_MI_OR_VAS").equals("1")){
//                    try{
//                        errorcode = input.substring(0,input.indexOf(":"));
//                        errordesc = input.substring(input.indexOf("|")+1);
//                        double priceCheck = Double.parseDouble(input.substring(input.indexOf(":")+1,input.indexOf("|")).trim());
//                        price = String.valueOf(priceCheck);
//                        charging = "1";
//                        return status +"|"+charging+"|"+price+"|OK_"+errorcode+"|"+errordesc;
//                    }catch(Exception ex){
//                        ex.printStackTrace();
//                        return "0|0|0|-1|"+input;
//                    }
//                }
//                else{
//                    try{
//                        //|1#Success#LUA,30,10000,LUA
//                        if(input.endsWith(",")) input = input +" ";
//                        String[] inf = input.split(",");
//                        price = inf[2];
//                        if(inf[0].startsWith("1#")){
//                            charging = "1";
//                            status = "1";
//                        }
//                        else{charging="0";status="0";}
//                        errorcode=inf[0];
//                        errordesc=input;
//                        return status +"|"+charging+"|"+price+"|OK_"+errorcode+"|"+errordesc;
//                    }catch(Exception ex){
//                        ex.printStackTrace();
//                        return "0|0|0|-1|"+input;
//                    }
//                }
//            }catch(Exception ex){
//                return "0|0|0|-1|"+input;
//            }
//        }
//    }catch(Exception ex){
//        ex.printStackTrace();
//        return "0|0|0|-1|"+input;
//    }
//
//}
//def sendSms = {String receiver, String messageSms ->
//    try{
//        //System.out.println("----------------------SMS:MT:"+messageSms+"|||"+receiver);
//        receiver = String.valueOf(receiver);
//        String serviceNumber = context.get("SERVICE_NUMBER");
//        String smsHost = context.get("SMS_HOST");
//        String smsPort = context.get("SMS_PORT");
//        String smsLookup = context.get("SMS_LOOKUP");
//        String utilUrl = context.get("dataflow_param:utilmodule");
//        String request = """
//		<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:vms="http://vms.neo">
//		   <soapenv:Header/>
//		   <soapenv:Body>
//		      <vms:sendSms>
//		         <!--Optional:-->
//		         <vms:args0>$serviceNumber</vms:args0>
//		         <!--Optional:-->
//		         <vms:args1>$receiver</vms:args1>
//		         <!--Optional:-->
//		         <vms:args2>$messageSms</vms:args2>
//		         <!--Optional:-->
//		         <vms:args3>$smsHost</vms:args3>
//		         <!--Optional:-->
//		         <vms:args4>$smsPort</vms:args4>
//		         <!--Optional:-->
//		         <vms:args5>$smsLookup</vms:args5>
//		      </vms:sendSms>
//		   </soapenv:Body>
//		</soapenv:Envelope>
//		"""
//        String result = new Activation().soapCall(utilUrl,request);
//        return result;
//    }catch(Exception e){
//        return "-1|"+e.getMessage();
//    }
//}
//def doCommand = {String commandKey, String sharingKey,String msisdn, String packageCode, String channelName, String p1,String v1,String p2,String v2,String p3,String v3 ->
//    try{
//        String utilUrl = context.get("dataflow_param:utilmodule");
//        String request = """
//			<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:vms="http://vms.neo">
//			   <soapenv:Header/>
//			   <soapenv:Body>
//			      <vms:doCommand>
//			         <!--Optional:-->
//			         <vms:commandkey>$commandKey</vms:commandkey>
//			         <!--Optional:-->
//			         <vms:sharingkey>$sharingKey</vms:sharingkey>
//			         <!--Optional:-->
//			         <vms:msisdn>$msisdn</vms:msisdn>
//			         <!--Optional:-->
//			         <vms:packagecode>$packageCode</vms:packagecode>
//			         <!--Optional:-->
//			         <vms:channelname>$channelName</vms:channelname>
//			         <vms:P1>$p1</vms:P1>
//			         <vms:V1>$v1</vms:V1>
//			         <vms:P2>$p2</vms:P2>
//			         <vms:V2>$v2</vms:V2>
//			         <vms:P3>$p3</vms:P3>
//			         <vms:V3>$v3</vms:V3>
//			      </vms:doCommand>
//			   </soapenv:Body>
//			</soapenv:Envelope>
//			""";
//        String result = new Activation().parseXMLtext(new Activation().soapCall(utilUrl,request),"//*[local-name() = 'return']");
//        return result;
//    }catch(Exception e){
//        return "-1|"+e.getMessage();
//    }
//}
//def getValueFromKey = {String body, String key ->
//    String ret = "";
//    try{
//        def rootNode = new XmlSlurper().parseText(body);
//        for(def record : rootNode.record.children()){
//            if(record.name().equals(key)){
//                ret = record.text();
//                break;
//            }
//        }
//    }catch(Exception ex){
//        //ex.printStackTrace();
//        ret = "";
//    }
//    return ret;
//}
//def getValueFromKeyMultiRecords = {String body, String rootTag,String tagName,String key,String value ->
//    String ret = new ParseXml().getValueFromKey(body,rootTag,tagName,key,value);
//
//    return ret;
//}
//
//def createTransaction = {String script_shop_id,String sharing_key_id, String messageSend,String messageReceive,
//                         String msisdn, String transactionStatus,String transactionResponse, String channelIDGT,String channelIDCF,
//                         String content_extra, String  packageCodeId, String price, String charging, String chargingType, String chargingStatus,
//                         String avenueOne,String avenueMonth,String avenueAgentOne,String avenueAgentMonth, String agent_name_id,String partnerId,String serviceId,int flag ->
//    String ret = "";
//    String request = """
//	   <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:vms="http://vms.neo">
//		   <soapenv:Header/>
//		   <soapenv:Body>
//		      <vms:queryXml>
//		         <vms:Service>get_transaction_id</vms:Service>
//		         <vms:Provider>default</vms:Provider>
//		         <vms:ParamSize>0</vms:ParamSize>
//		      </vms:queryXml>
//		   </soapenv:Body>
//		</soapenv:Envelope>
//	""";
//    try{
//        String transId = new Activation().parseXMLtext(new Activation().soapCall(context.get("dataflow_param:sqlmodule"),request),"//*[local-name() = 'return']");
//
//        transId = getValueFromKey(transId,"TRANSACTION_ID");//new XmlSlurper().parseText(transId).record.TRANSACTION_ID.text();
//
//        request = """
//		<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:vms="http://vms.neo">
//			   <soapenv:Header/>
//			   <soapenv:Body>
//			      <vms:updateXml>
//			  		 <!--Optional:-->
//			         <vms:Service>generate_transaction</vms:Service>
//			         <!--Optional:-->
//			         <vms:Provider>default</vms:Provider>
//			         <!--Optional:-->
//			         <vms:ParamSize>24</vms:ParamSize>
//			         <vms:P1>$script_shop_id</vms:P1>
//		 		    <vms:P2>$sharing_key_id</vms:P2>
//		 		    <vms:P3>$messageSend</vms:P3>
//		 		    <vms:P4>$messageReceive</vms:P4>
//		 		    <vms:P5>$msisdn</vms:P5>
//		 		    <vms:P6>$transactionStatus</vms:P6>
//		 		    <vms:P7>$transactionResponse</vms:P7>
//		 		    <vms:P8>$channelIDGT</vms:P8>
//		 		    <vms:P9>$channelIDCF</vms:P9>
//		 		    <vms:P10>$content_extra</vms:P10>
//				    <vms:P11>$packageCodeId</vms:P11>
//		 		    <vms:P12>$price</vms:P12>
//		 		    <vms:P13>$charging</vms:P13>
//		 		    <vms:P14>$chargingType</vms:P14>
//		 		    <vms:P15>$chargingStatus</vms:P15>
//		 		    <vms:P16>$avenueOne</vms:P16>
//		 		    <vms:P17>$avenueMonth</vms:P17>
//		 		    <vms:P18>$avenueAgentOne</vms:P18>
//		 		    <vms:P19>$avenueAgentMonth</vms:P19>
//		 		    <vms:P20>$agent_name_id</vms:P20>
//		 		    <vms:P21>$transId</vms:P21>
//		 		    <vms:P22>$partnerId</vms:P22>
//		 		    <vms:P23>$serviceId</vms:P23>
//				    <vms:P24>$flag</vms:P24>
//			      </vms:updateXml>
//			   </soapenv:Body>
//			</soapenv:Envelope>
//			""";
//
//        ret = new Activation().parseXMLtext(new Activation().soapCall(context.get("dataflow_param:sqlmodule"),request),"//*[local-name() = 'return']");
//
//        ret = transId;
//    }catch(Exception ex){
//        ex.printStackTrace();
//        ret = "-1";
//    }
//    return ret;
//}
//def createTempTrans = {String transactionId, String sharingKey, String msisdn ->
//    String ret = "";
//    String request = """
//	<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:vms="http://vms.neo">
//	   <soapenv:Header/>
//	   <soapenv:Body>
//	      <vms:queryXml>
//	  		 <!--Optional:-->
//	         <vms:Service>select_temp_accept_id</vms:Service>
//	         <!--Optional:-->
//	         <vms:Provider>default</vms:Provider>
//	         <!--Optional:-->
//	         <vms:ParamSize>1</vms:ParamSize>
//	         <vms:P1>$msisdn</vms:P1>
//	      </vms:queryXml>
//	   </soapenv:Body>
//	</soapenv:Envelope>
//	""";
//    try{
//        ret = new Activation().parseXMLtext(new Activation().soapCall(context.get("dataflow_param:sqlmodule"),request),"//*[local-name() = 'return']");
//
//        int tempAcceptId = Integer.parseInt(getValueFromKey(ret,"ACCEPT_ID"));
//
//        if(tempAcceptId ==0) tempAcceptId =1;
//        else tempAcceptId +=1;
//        ret = String.valueOf(tempAcceptId);
//
//        request = """
//		<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:vms="http://vms.neo">
//		   <soapenv:Header/>
//		   <soapenv:Body>
//		      <vms:updateXml>
//		  		 <!--Optional:-->
//		         <vms:Service>insert_temp_accept_id</vms:Service>
//		         <!--Optional:-->
//		         <vms:Provider>default</vms:Provider>
//		         <!--Optional:-->
//		         <vms:ParamSize>4</vms:ParamSize>
//		         <vms:P1>$transactionId</vms:P1>
//		         <vms:P2>$sharingKey</vms:P2>
//		         <vms:P3>$msisdn</vms:P3>
//		         <vms:P4>$tempAcceptId</vms:P4>
//		      </vms:updateXml>
//		   </soapenv:Body>
//		</soapenv:Envelope>
//		""";
//        try{
//
//            String retval = new Activation().parseXMLtext(new Activation().soapCall(context.get("dataflow_param:sqlmodule"),request),"//*[local-name() = 'return']");
//
//        }catch(Exception ex){
//            ex.printStackTrace();
//        }
//    }catch(Exception ex){
//        //ex.printStackTrace();
//        return "-1";
//    }
//
//    return ret;
//}
//def kiemtraMI = {String info,String serviceId,String packageCode ->
//    String[] retval = new String[9];
//    if(info == null || info.indexOf("DATA_WEB_SUCC")<0){
//        if(info!=null){
//            if(info.equals("KT_DATA_WEB_ERR01: NULL")){
//                retval[0]="0";
//                return retval;
//            }
//            else{
//                retval[0]="-1";
//                return retval;
//            }
//        }
//        else{
//            retval[0]="0";
//            return retval;
//        }
//    }
//    String pckNeedCheck = info.split("\\|")[0];
//    pckNeedCheck = pckNeedCheck.substring(info.indexOf(" ")).trim();
//    if(pckNeedCheck.equals(packageCode)){
//        retval[0]="1";
//        return retval;
//    }
//    String request = """
//	<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:vms="http://vms.neo">
//	   <soapenv:Header/>
//	   <soapenv:Body>
//	      <vms:queryXml>
//	  		 <!--Optional:-->
//	         <vms:Service>get_service_package_detail</vms:Service>
//	         <!--Optional:-->
//	         <vms:Provider>default</vms:Provider>
//	         <!--Optional:-->
//	         <vms:ParamSize>3</vms:ParamSize>
//	         <vms:P1>$packageCode</vms:P1>
// 		    <vms:P2>$pckNeedCheck</vms:P2>
// 		    <vms:P3>$serviceId</vms:P3>
//	      </vms:queryXml>
//	   </soapenv:Body>
//	</soapenv:Envelope>
//	""";
//    String pckInfo = "";
//    double usedPckPrice = -1;
//    double registerPckPrice = -1;
//    String capacity = "";
//    String cycle = "";
//    try{
//        pckInfo = new Activation().parseXMLtext(new Activation().soapCall(context.get("dataflow_param:sqlmodule"),request),"//*[local-name() = 'return']");
//        usedPckPrice = Double.parseDouble( getValueFromKeyMultiRecords(pckInfo,"record","PACKAGE_CODE",pckNeedCheck,"PACKAGE_PRICE"));
//        registerPckPrice = Double.parseDouble(getValueFromKeyMultiRecords(pckInfo,"record","PACKAGE_CODE",packageCode,"PACKAGE_PRICE"));
//        capacity = getValueFromKeyMultiRecords(pckInfo,"record","PACKAGE_CODE",packageCode,"CAPACITY");
//        cycle = getValueFromKeyMultiRecords(pckInfo,"record","PACKAGE_CODE",packageCode,"PACKAGE_CYCLE");
//        capacityPckNeedCheck = getValueFromKeyMultiRecords(pckInfo,"record","PACKAGE_CODE",pckNeedCheck,"CAPACITY");
//        cyclePckNeedCheck = getValueFromKeyMultiRecords(pckInfo,"record","PACKAGE_CODE",pckNeedCheck,"PACKAGE_CYCLE");
//
//        if(usedPckPrice.equals("M120") && (packageCode.equals("M120")||packageCode.equals("KM120"))){
//            retval[0] = "10"; retval[1] = pckNeedCheck; retval[2] = usedPckPrice;retval[3]=pckNeedCheck;retval[4]=usedPckPrice;retval[5]=capacity;retval[6]=cycle;
//            return retval;
//        }
//        if(registerPckPrice == usedPckPrice){
//            retval[0] = "10"; retval[1] = pckNeedCheck; retval[2] = usedPckPrice;retval[3]=pckNeedCheck;retval[4]=usedPckPrice;retval[5]=capacity;retval[6]=cycle;
//            return retval;
//        }
//        //gia goi dang ky < gia goi thue bao dang su dung
//        if(registerPckPrice < usedPckPrice){
//            retval[0] = "2";retval[1]=packageCode;retval[2]=registerPckPrice;retval[3]=pckNeedCheck;retval[4]=usedPckPrice;retval[5]=capacity;retval[6]=cycle;retval[7] = capacityPckNeedCheck; retval[8] = cyclePckNeedCheck;
//        }
//        else if(registerPckPrice > usedPckPrice){
//            retval[0] = "3";retval[1]=packageCode;retval[2]=registerPckPrice;retval[3]=pckNeedCheck;retval[4]=usedPckPrice;retval[5]=capacity;retval[6]=cycle;retval[7] = capacityPckNeedCheck; retval[8] = cyclePckNeedCheck;
//        }
//        else{
//            retval[0] = "1";
//        }
//        return retval;
//    }catch(Exception ex){
//        retval[0] = "-1"; return retval;
//    }
//}
//
//def getCheckDG = {String msisdn, String pkg1, String pkg2, String channelName ->
//    try{
//        String utilUrl = context.get("dataflow_param:utilmodule");
//        String request = """
//			<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:vms="http://vms.neo">
//			   <soapenv:Header/>
//			   <soapenv:Body>
//					<vms:getCheckDoiGoi>
//					<vms:msisdn>$msisdn</vms:msisdn>
//					<vms:pkg1>$pkg1</vms:pkg1>
//					<vms:pkg2>$pkg2</vms:pkg2>
//					<vms:channel>$channelName</vms:channel>
//					</vms:getCheckDoiGoi>
//			   </soapenv:Body>
//			</soapenv:Envelope>
//			""";
//        String result = new Activation().parseXMLtext(new Activation().soapCall(utilUrl,request),"//*[local-name() = 'return']");
//        return result;
//    }catch(Exception e){
//        return "-1,0," +e.getMessage();
//    }
//}
//
////System.out.println("DAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA1111");
//
//String mi_status_info = context.get("checkservice_cmdresponse_with_value");
////System.out.println("DAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA1111");
//
//String billing_flag = context.get("BILLING_ISCONTINUE");
//
//String mi_huy_info = context.get("checkhuy_cmdresponse_with_value");
////System.out.println("DAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA1111");
//
//String packageCode = context.get("PACKAGE_CODE");
//String serviceId = context.get("serviceid");
//int pckCycle = -1;
//String charging = "";
//String chargingStatus = "";
//String startActiveDate = "";
//String mobType = "";
//String profile = "";
//String chargingType = context.get("CHARGING_TYPE");
//String transactionStatus = "";
//String transactionResponse = "";
//String registerInfo = "";
//boolean isBill = false;
//boolean isChangePackage = false;
//double price = 0;
//double price_old = 0;
//String is_doi_goi = context.get("DOIGOI");
//
//if (is_doi_goi==null) {
//    is_doi_goi = "0";
//}
//System.out.println("DOI GOI: " + is_doi_goi + ", msisdn:" + context.get("msisdn") + ", pkg: " + context.get("packagecode") + ", date:" + new Date().toString());
//if (is_doi_goi.equals("1")) {
//    String pkg_old = getCheckDG(context.get("msisdn"), "", context.get("packagecode"), context.get("CHANNEL"));
//    try{
//        if (pkg_old!=null && pkg_old.length()>0) {
//            String[] arr = pkg_old.split(",");
//            price_old = Double.parseDouble(arr[1]);
//        }
//    }catch(Exception ex){}
//}
//
//String startTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
//boolean stepResult = false;
////System.out.println("DAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
//long transactionId = -1;
//try{
//    String msisdnTmp = "";
//    if(msisdn.startsWith("0")) msisdn = msisdn.substring(1);
//    if(!msisdn.startsWith("84")) msisdn = "84"+msisdn;
//    msisdnTmp = msisdn.substring(2);
//    String[] info = kiemtraMI(mi_status_info, serviceId, packageCode);
//    int flag = 0;
//    if(billing_flag.equals("true")) flag=1;
//    System.out.println("FLAG:"+billing_flag.equals("true")+"||HAVE_CHECK_HUY:"+context.get("HAVE_CHECK_HUY")+"||msisdn:"+msisdn+"check_huy_status:"+ context.get("check_huy_status")+":::"+context.get("CHANGE_PACKAGE").equals("1"));
//    if(context.get("SERVICE_KEY").equals("MOBILEINTERNET")||context.get("SERVICE_KEY").equals("FAST_CONNECT")){
//        if(flag ==0 &&context.get("HAVE_CHECK_HUY").equals("1") && context.get("check_huy_status").equals("1")) {
//            //get package huy DATA_DEL_SUCC: 906044701|M10|05/11/2015 16:22:58|05/12/2015 16:22:57|05/11/2015 16:25:53|stepResult=false
//            //return tid+":INFO:Thong bao: Ban KHONG THE dang ky goi dich vu "+packCode+" cho thue bao "+receiver+". Do thue bao da co giao dich HUY/bi HUY goi "+pckHuy+" trong vong 30 ngay truoc do. Chi tiet lien he 9090.";
//            if(context.get("SEND_SMS").equals("1") && context.get("channel").equals("SMS")){
//                String mt = getValueFromKeyMultiRecords($soap_34_extract1,"record","MT_TYPE_KEY","SUBSCRIBER_CAN_NOT_HUY_30NGAY","MT_TYPE_VALUE").replaceAll("\\{MSISDN}",String.valueOf($msisdn)).replaceAll("\\{PACKAGE_CODE}",context.get("PACKAGE_CODE")).replaceAll("\\{PACKAGE_CODE_HUY}",context.get("checkhuy_cmdresponse_with_value").split("\\|")[1]);
//                if(mt == null || mt.equals(""))
//                    mt = getValueFromKeyMultiRecords($soap_8_extract1,"record","MT_TYPE_KEY","SUBSCRIBER_CAN_NOT_HUY_30NGAY","MT_TYPE_VALUE").replaceAll("\\{MSISDN}",String.valueOf($msisdn)).replaceAll("\\{PACKAGE_CODE}",context.get("PACKAGE_CODE")).replaceAll("\\{PACKAGE_CODE_HUY}",context.get("checkhuy_cmdresponse_with_value").split("\\|")[1]);
//                ret = sendSms(context.get("sharingkey"),mt);
//                System.out.println("ret:"+ret);
//            }
//            context.put("ErrorCodeAPI","25"); context.put("ErrorDescAPI","Thue bao MI/FC co lich su huy goi trong 30 ngay");
//            return false;
//        }
//    }
//    String maChiNhanh = String.valueOf($ma_chi_nhanh);
//    String partnerId = "";
//    if(maChiNhanh.equals("0"))
//        partnerId = String.valueOf($partner_id);
//    else
//        partnerId = maChiNhanh;
//
//    transactionId = Long.parseLong( createTransaction(String.valueOf($script_shop_id),String.valueOf($sharing_key_id),"","",String.valueOf($msisdn),"","",String.valueOf($channel_id),"",context.get("packagecode"),context.get("PACKAGE_CODE_ID"),context.get("PACKAGE_PRICE"),charging,context.get("CHARGING_TYPE"),chargingStatus,"","","","",String.valueOf($agent_id),String.valueOf(partnerId),String.valueOf($serviceid),flag));
//    System.out.println("TRANSACTION_ID:"+transactionId);
//
//    if(transactionId <0){
//        context.put("ErrorCodeAPI","34"); context.put("ErrorDescAPI","Loi he thong khong khoi tao duoc giao dich.");
//        return false;
//    }
//    context.put("TRANSACTION_ID",transactionId);
//
//    if(!is_doi_goi.equals("1") && (context.get("SERVICE_KEY").equals("MOBILEINTERNET")||context.get("SERVICE_KEY").equals("FAST_CONNECT")) &&(info!=null &&info[0].equals("3")) && (context.get("CHANGE_PACKAGE").equals("1"))){
//
//        //Vao case gioi thieu neu dang co goi? or check la doi goi?
//        int msgId = Integer.parseInt(createTempTrans(String.valueOf(transactionId),"$sharingkey",String.valueOf($msisdn)));
//
//        if(msgId <0){
//            context.put("ErrorCodeAPI","34"); context.put("ErrorDescAPI","Loi he thong khong khoi tao duoc giao dich.");
//            return false;
//        }
//        //Ban dang su dung goi {SERVICE_KEY} {PACKAGE_IN_USE} voi dung luong {PACKAGE_IN_USE_CAPACITY}, muc cuoc {PACKAGE_IN_USE_PRICE}dong. Ban co muon chuyen sang goi cuoc {PACKAGECODE}, gia cuoc {PACKAGE_PRICE}/{PACKAGE_CYCLE}ngay do dai ly cua MobiFone gioi thieu khong? Soan Y{CONFIRMID} gui {SERVICE_NUMBER} de xac nhan trong 24 gio hoac Soan N{CONFIRMID} gui {SERVICE_NUMBER} de tu choi.
//        //retval[0] = "2";retval[1]=packageCode;retval[2]=registerPckPrice;retval[3]=pckNeedCheck;retval[4]=usedPckPrice;retval[5]=capacity;retval[6]=cycle;retval[7] = capacityPckNeedCheck; retval[8] = cyclePckNeedCheck;
//
//        mt = getValueFromKeyMultiRecords($soap_34_extract1,"record","MT_TYPE_KEY","GIOI_THIEU_DOI_GOI_THANH_CONG","MT_TYPE_VALUE");
//        if(mt == null || mt.equals(""))
//            mt = getValueFromKeyMultiRecords($soap_8_extract1,"record","MT_TYPE_KEY","GIOI_THIEU_DOI_GOI_THANH_CONG","MT_TYPE_VALUE");
//
//        mt = mt.replaceAll("\\{SERVICE_KEY}",context.get("SERVICE_KEY_SMS")).replaceAll("\\{PACKAGE_CODE}",context.get("PACKAGE_CODE_SMS")).replaceAll("\\{PACKAGE_PRICE}",context.get("PACKAGE_PRICE"));
//        mt = mt.replaceAll("\\{SERVICE_NUMBER}",context.get("SERVICE_NUMBER")).replaceAll("\\{MSG_ID}",String.valueOf(msgId));
//        mt = mt.replaceAll("\\{PACKAGE_IN_USE}",info[3]).replaceAll("\\{PACKAGE_IN_USE_CAPACITY}",info[7]);
//        mt = mt.replaceAll("\\{PACKAGE_IN_USE_PRICE}",info[4]);
//        mt = mt.replaceAll("\\{PACKAGE_PRICE}",info[2]);
//        if(context.get("CAPACITY").equals("")){
//            mt = mt.replaceAll("\\{PACKAGE_CYCLE}",context.get("PACKAGE_CYCLE"));
//        }
//        else{
//            mt = mt.replaceAll("\\{PACKAGE_CYCLE}",context.get("CAPACITY"));
//        }
//        ret = sendSms(context.get("msisdn"),mt);
//
//        context.put("TRANSACTION_ID",String.valueOf(transactionId));
//        context.put("ErrorCodeAPI","0"); context.put("ErrorDescAPI",String.valueOf(transactionId)+"|Giao dịch đã được giới thiệu cho thuê bao");
//        return false;
//    }
//    else{
//        //cai dat dich vu cho thue bao
//        //set ngay kich hoat , thue bao tra truoc, tra sau de tinh hoa hong cho doi tac TGDD
//        String msisdnTemp = msisdn.substring(2);
//        //check dich vu funring me
//        if(context.get("SERVICE_KEY").equals("FUNRINGME")){
//            //check xem thue bao da su dung dich vu funring chua
//            String result = doCommand("Kiem tra dich vu Funring",context.get("sharingkey"),context.get("msisdn"),context.get("package_code"),context.get("CHANNEL"),"","","","","","");
//
//            int cmdResultId = -1;
//            try{
//                def rootNode = new XmlSlurper().parseText(result);
//                String errorCode = rootNode.Parameter.ErrorCode.text();
//                String errorDesc = rootNode.Parameter.ErrorDesc.text();
//
//                if (!errorCode.equals("0")){
//                    context.put("ErrorCodeAPI",errorCode);
//                    context.put("ErrorDescAPI",errorDesc);
//                    return false;
//                }
//                else{
//                    //success
//                    try{
//                        request = rootNode.Parameter.cmdRequest_with_value.text();
//                        String response = rootNode.Parameter.cmdresponse_with_value.text();
//                        context.put(rootNode.Parameter.cmdRequest_with_value.name(),rootNode.Parameter.cmdRequest_with_value.text());
//                        context.put(rootNode.Parameter.cmdresponse_with_value.name(),rootNode.Parameter.cmdresponse_with_value.text());
//                        try{
//                            cmdResultId = Integer.parseInt(rootNode.record.COMMANDS_RESULTS_ID.text());
//                        }catch(Exception ex){}
//                        if(cmdResultId>=0){
//                            String cmdStatus = rootNode.record.COMMANDS_STATUS.text();
//
//                            if(!cmdStatus.equals("INUSE")){
//                                context.put("ErrorCodeAPI","50"); context.put("ErrorDescAPI","Thue bao chua cai dat dich vu Funring nen khong the cai dat dich vu Funring Me. Lien he 9090 de biet them thong tin.");
//                                if(context.get("SEND_SMS").equals("1") && context.get("channel").equals("SMS")){
//                                    String mt = getValueFromKeyMultiRecords($soap_34_extract1,"record","MT_TYPE_KEY","NOT_HAVE_FUNRING_FUNRING_ME","MT_TYPE_VALUE");
//                                    if(mt == null || mt.equals(""))
//                                        mt = getValueFromKeyMultiRecords($soap_8_extract1,"record","MT_TYPE_KEY","NOT_HAVE_FUNRING_FUNRING_ME","MT_TYPE_VALUE");
//                                    ret = sendSms(context.get("sharingkey"),mt);
//
//                                }
//                                return false;
//                            }
//
//                        }
//                        else{
//                            System.out.println("Process manually...");
//                            if(response.startsWith("-1|")){
//                                context.put("ErrorCodeAPI","-1"); context.put("ErrorDescAPI","He thong khong tra cuu duoc thong tin thue bao");
//                                //for test
//                                //return true;
//                                return false;
//                            }
//                            else
//                                return true;
//                        }
//
//                    }catch(Exception ex){
//                        ex.printStackTrace();
//                    }
//
//                }
//            }catch(Exception ex){
//                ex.printStackTrace();
//                context.put("ErrorCodeAPI","-1"); context.put("ErrorDescAPI","He thong loi");
//                return false;
//            }
//        }
//
//
//        if(context.get("SERVICE_KEY").equals("MOBILEINTERNET") ||context.get("SERVICE_KEY").equals("FASTCONNECT")){
//            if(info !=null && info[0] =="2"){
//                if(packageCode.toUpperCase().equals("MAX10") ||packageCode.toUpperCase().equals("MAX30") ){
//                    isBill = true;
//                }
//                else isBill = false;
//                isChangePackage = true;
//            }
//            else if (info !=null && info[0] =="3") {
//                isBill = true;
//                isChangePackage = true;
//            }
//            price = "0";
//            String policyTypeName = "";
//            if(info!=null && info[0] == "0"){
//                if(context.get("check_huy_status").equals("0")){
//                    try{
//                        if(false){
//                            request = """
//						<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:vms="http://vms.neo">
//						   <soapenv:Header/>
//						   <soapenv:Body>
//						      <vms:queryXml>
//							 <!--Optional:-->
//							 <vms:Service>checkLoiDungCheckPriceSpecific</vms:Service>
//							 <!--Optional:-->
//							 <vms:Provider>default</vms:Provider>
//							 <!--Optional:-->
//							 <vms:ParamSize>2</vms:ParamSize>
//							 <!--Optional:-->
//							 <vms:P1>$msisdn</vms:P1>
//							    <vms:P2>$serviceid</vms:P2>
//						      </vms:queryXml>
//						   </soapenv:Body>
//						</soapenv:Envelope>
//						""";
//
//                            ret = new Activation().parseXMLtext(new Activation().soapCall(context.get("dataflow_param:sqlmodule"),request),"//*[local-name() = 'return']");
//                            System.out.println(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+"|AAAAAAAAAAA:"+msisdn);
//                            try{
//                                price = getValueFromKey(ret,"PRICE");
//                                policyTypeName = getValueFromKey(ret,"POLICY_TYPE_NAME");
//
//                            }catch(Exception ex){System.out.println(ex.getMessage()+"||||AAAAAAAA");}
//                            System.out.println(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+"|AAAAAAAAAAA:"+msisdn);
//                            //co cau hinh chinh sach chong loi dung
//                            if(!policyTypeName.equals("")){
//                                if(Double.parseDouble(context.get("PACKAGE_PRICE")) <= Double.parseDouble(price)){
//                                    //get list package co gia cao hon
//                                    request = """
//								<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:vms="http://vms.neo">
//								   <soapenv:Header/>
//								   <soapenv:Body>
//								      <vms:value>
//									 <!--Optional:-->
//									 <vms:Service>get_list_package_high_price</vms:Service>
//									 <!--Optional:-->
//									 <vms:Provider>default</vms:Provider>
//									 <!--Optional:-->
//									 <vms:ParamSize>2</vms:ParamSize>
//									 <!--Optional:-->
//									 <vms:P1>$serviceid</vms:P1>
//									    <vms:P2>$price</vms:P2>
//								      </vms:value>
//								   </soapenv:Body>
//								</soapenv:Envelope>
//								""";
//                                    ret = new Activation().parseXMLtext(new Activation().soapCall(context.get("dataflow_param:sqlmodule"),request),"//*[local-name() = 'return']");
//                                    if(ret.equals(",")) ret = "";
//                                    if(ret.startsWith(",")) ret = ret.substring(1);
//                                    System.out.println(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+"|AAAAAAAAAAA:"+msisdn);
//                                    context.put("ErrorCodeAPI","22");context.put("ErrorDescAPI","Thong bao ban khong duoc gioi thieu goi cuoc nay cho thue bao, cac goi co the duoc gioi thieu la "+ret);
//                                    if(sendSmsForSharing && context.get("channel").equals("SMS")){
//                                        String mt = "";
//                                        if(ret.equals("")){
//                                            mt = getValueFromKeyMultiRecords($soap_34_extract1,"record","MT_TYPE_KEY","NOTICE_CHAN_GIOITHIEU_PSC_M0","MT_TYPE_VALUE").replaceAll("\\{MSISDN}",String.valueOf($msisdn)).replaceAll("\\{SERVICE_KEY}",context.get("SERVICE_KEY")).replaceAll("\\{PACKAGE_CODE}",context.get("PACKAGE_CODE")).replaceAll("\\{PACKAGE_PRICE}",context.get("PACKAGE_PRICE"));
//                                            if(mt == null || mt.equals(""))
//                                                mt = getValueFromKeyMultiRecords($soap_8_extract1,"record","MT_TYPE_KEY","NOTICE_CHAN_GIOITHIEU_PSC_M0","MT_TYPE_VALUE").replaceAll("\\{MSISDN}",String.valueOf($msisdn)).replaceAll("\\{SERVICE_KEY}",context.get("SERVICE_KEY")).replaceAll("\\{PACKAGE_CODE}",context.get("PACKAGE_CODE")).replaceAll("\\{PACKAGE_PRICE}",context.get("PACKAGE_PRICE"));
//                                        }
//                                        else{
//                                            mt = getValueFromKeyMultiRecords($soap_34_extract1,"record","MT_TYPE_KEY","NOTICE_CHAN_GIOITHIEU_PSC_M0","MT_TYPE_VALUE").replaceAll("\\{MSISDN}",String.valueOf($msisdn)).replaceAll("\\{SERVICE_KEY}",context.get("SERVICE_KEY")).replaceAll("\\{PACKAGE_CODE}",context.get("PACKAGE_CODE")).replaceAll("\\{PACKAGE_PRICE}",context.get("PACKAGE_PRICE")).replaceAll("\\{LIST_PACKAGE}",ret);
//                                            if(mt == null || mt.equals(""))
//                                                mt = getValueFromKeyMultiRecords($soap_8_extract1,"record","MT_TYPE_KEY","NOTICE_CHAN_GIOITHIEU_PSC_M0","MT_TYPE_VALUE").replaceAll("\\{MSISDN}",String.valueOf($msisdn)).replaceAll("\\{SERVICE_KEY}",context.get("SERVICE_KEY")).replaceAll("\\{PACKAGE_CODE}",context.get("PACKAGE_CODE")).replaceAll("\\{PACKAGE_PRICE}",context.get("PACKAGE_PRICE")).replaceAll("\\{LIST_PACKAGE}",ret);
//                                        }
//                                        ret = sendSms(context.get("sharingkey"),mt);
//                                        System.out.println("ret:"+ret);
//                                    }
//                                    System.out.println(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+"|AAAAAAAAAAA:"+msisdn);
//
//                                    return false;
//                                }
//                            }
//                        }
//                    }catch(Exception ex){
//                        System.out.println(ex.getMessage());
//                    }
//                }
//            }
//        }
//        if(context.get("SERVICE_KEY").equals("NHACCHO") || context.get("SERVICE_KEY").equals("TANG_NHACCHO")){
//            if(context.get("partner_key").equals("ZING")){
//                startTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
//                String result = doCommand("Kiem tra dich vu Funring",context.get("sharingkey"),context.get("msisdn"),context.get("package_code"),context.get("CHANNEL"),"","","","","","");
//
//                int cmdResultId = -1;
//                try{
//                    def rootNode = new XmlSlurper().parseText(result);
//                    String errorCode = rootNode.Parameter.ErrorCode.text();
//                    String errorDesc = rootNode.Parameter.ErrorDesc.text();
//                    System.out.println("ErrorCode:"+errorCode);
//                    if (!errorCode.equals("0")){
//                        context.put("ErrorCodeAPI",errorCode);
//                        context.put("ErrorDescAPI",errorDesc);
//                        return false;
//                    }
//                    else{
//                        //success
//                        try{
//                            request = rootNode.Parameter.cmdRequest_with_value.text();
//                            String response = rootNode.Parameter.cmdresponse_with_value.text();
//                            context.put(rootNode.Parameter.cmdRequest_with_value.name(),rootNode.Parameter.cmdRequest_with_value.text());
//                            context.put(rootNode.Parameter.cmdresponse_with_value.name(),rootNode.Parameter.cmdresponse_with_value.text());
//                            try{
//                                cmdResultId = Integer.parseInt(rootNode.record.COMMANDS_RESULTS_ID.text());
//                            }catch(Exception ex){}
//                            if(cmdResultId>=0){
//                                String cmdStatus = rootNode.record.COMMANDS_STATUS.text();
//
//                                if(!cmdStatus.equals("INUSE")){
//                                    //dang ky dich vu funring
//                                    String service = "";
//                                    if(context.get("NHACCHO")) service = "Dang ky dich vu Funring";
//                                    else service = "FUNRING_DANG_KY_DICH_VU_THUE_BAO_KHAC";
//                                    String msisdnParameter = "";
//                                    if(context.get("NHACCHO")) msisdnParameter = msisdn;
//                                    else msisdnParameter = context.get("content");
//                                    if(msisdnParameter.startsWith("0")) msisdnParameter = msisdnParameter.substring(1);
//                                    if(!msisdnParameter.startsWith("84")) msisdnParameter = "84"+msisdnParameter;
//                                    result = doCommand(service,context.get("sharingkey"),msisdn,context.get("package_code"),context.get("CHANNEL"),"TO_USER_PHONE_NUMBER",msisdnParameter,"","","","");
//                                    cmdResultId = -1;
//                                    try{
//                                        rootNode = new XmlSlurper().parseText(result);
//                                        errorCode = rootNode.Parameter.ErrorCode.text();
//                                        errorDesc = rootNode.Parameter.ErrorDesc.text();
//
//                                        if (!errorCode.equals("0")){
//                                            context.put("ErrorCodeAPI",errorCode);
//                                            context.put("ErrorDescAPI",errorDesc);
//                                            return false;
//                                        }
//                                        else{
//                                            //success
//                                            try{
//                                                request = rootNode.Parameter.cmdRequest_with_value.text();
//                                                response = rootNode.Parameter.cmdresponse_with_value.text();
//                                                context.put(rootNode.Parameter.cmdRequest_with_value.name(),rootNode.Parameter.cmdRequest_with_value.text());
//                                                context.put(rootNode.Parameter.cmdresponse_with_value.name(),rootNode.Parameter.cmdresponse_with_value.text());
//                                                try{
//                                                    cmdResultId = Integer.parseInt(rootNode.record.COMMANDS_RESULTS_ID.text());
//                                                }catch(Exception ex){}
//                                                if(cmdResultId>=0){
//                                                    cmdStatus = rootNode.record.COMMANDS_STATUS.text();
//
//                                                    if(!cmdStatus.toUpperCase().equals("SUCCESS")){
//                                                        //thong bao loi dang ky khong thanh cong cho thue bao
//                                                        String mt = rootNode.record.MESSAGE_SMS.text().replaceAll("\\{MSISDN}",msisdn);
//                                                        charging = "0";
//                                                        transactionStatus = "0";
//                                                        context.put("ErrorCodeAPI",transactionStatus);context.put("ErrorDescAPI",mt);
//                                                        if(channel.equals("SMS")){
//                                                            sendSms(msisdn,mt);
//                                                        }
//                                                        if(context.get("SEND_SMS").equals("1") && context.get("channel").equals("SMS")){
//                                                            mt = getValueFromKeyMultiRecords($soap_34_extract1,"record","MT_TYPE_KEY","TANG_NHAC_CHO_REGISTER_FAILD_DB","MT_TYPE_VALUE");
//                                                            if(mt == null || mt.equals(""))
//                                                                mt = getValueFromKeyMultiRecords($soap_8_extract1,"record","MT_TYPE_KEY","TANG_NHAC_CHO_REGISTER_FAILD_DB","MT_TYPE_VALUE");
//                                                            ret = sendSms(context.get("sharingkey"),mt);
//                                                        }
//                                                        return false;
//                                                    }
//                                                    else{
//                                                        System.out.println("register funring successfully...");
//                                                    }
//
//
//                                                }
//                                                else{
//                                                    System.out.println("Process manually...");
//                                                    if(response.startsWith("-1|")){
//                                                        context.put("ErrorCodeAPI","-1"); context.put("ErrorDescAPI","He thong khong tra cuu duoc thong tin thue bao");
//                                                        //for test
//                                                        //return true;
//                                                        return false;
//                                                    }
//                                                    else
//                                                        return true;
//                                                }
//
//                                            }catch(Exception ex){
//                                                ex.printStackTrace();
//                                            }
//
//                                        }
//                                    }catch(Exception ex){
//                                        ex.printStackTrace();
//                                        context.put("ErrorCodeAPI","-1"); context.put("ErrorDescAPI","He thong loi");
//                                        return false;
//                                    }
//                                }
//
//                            }
//                            else{
//                                System.out.println("Process manually...");
//                                if(response.startsWith("-1|")){
//                                    context.put("ErrorCodeAPI","-1"); context.put("ErrorDescAPI","He thong khong tra cuu duoc thong tin thue bao");
//                                    //for test
//                                    //return true;
//                                    return false;
//                                }
//                                else
//                                    return true;
//                            }
//
//                        }catch(Exception ex){
//                            ex.printStackTrace();
//                        }
//
//                    }
//                }catch(Exception ex){
//                    ex.printStackTrace();
//                    context.put("ErrorCodeAPI","-1"); context.put("ErrorDescAPI","He thong loi");
//                    return false;
//                }
//            }
//        }
//        //register for subscriber
//        try{
//            startTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
//            String utilUrl = context.get("dataflow_param:utilmodule");
//            String request = "";
//            String packageCodeAPI = context.get("PACKAGE_CODE_API");
//            if(content!=null){
//                if(context.get("SERVICE_KEY").equals("ZING_TANG_NHACCHO")||context.get("SERVICE_KEY").equals("TANG_NHAC_CHO")){
//                    if(content.startsWith("0")) content = content.substring(1);
//                    if(!content.startsWith("84")) content = "84"+content;
//                }
//            }
//
//            if (is_doi_goi.equals("1")) {
//                isChangePackage = true;
//                isBill = true;
//            }
//            //System.out.println("------- Bat dau dang ky goi: " + isChangePackage);
//
//            if(is_doi_goi.equals("1") || (isChangePackage == true && context.get("HAVE_CHANGE_PACKAGE").equals("1") && context.get("CHANGE_PACKAGE").equals("1"))){
//                request = """
//					<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:vms="http://vms.neo">
//					   <soapenv:Header/>
//					   <soapenv:Body>
//						  <vms:registerChangeService>
//							 <!--Optional:-->
//							 <vms:scriptShopId>$script_shop_id</vms:scriptShopId>
//							 <!--Optional:-->
//							 <vms:sharingkey>$sharingkey</vms:sharingkey>
//							 <!--Optional:-->
//							 <vms:msisdn>$msisdn</vms:msisdn>
//							 <!--Optional:-->
//							 <vms:packagecode>$packageCode</vms:packagecode>
//							 <vms:channelkey>$channel</vms:channelkey>
//							 <!--Optional:-->
//							 <vms:P1>PACKAGE_CODE</vms:P1>
//							 <!--Optional:-->
//							 <vms:V1>$packageCode</vms:V1>
//							 <!--Optional:-->
//							 <vms:P2>PACKAGE_CODE_API</vms:P2>
//							 <!--Optional:-->
//							 <vms:V2>$packageCodeAPI</vms:V2>
//							 <!--Optional:-->
//							 <vms:P3></vms:P3>
//							 <!--Optional:-->
//							 <vms:V3></vms:V3>
//						  </vms:registerChangeService>
//					   </soapenv:Body>
//					</soapenv:Envelope>
//					""";
//            }
//            else{
//                if(packageCode.equals("{DYNAMIC}"))
//                    request = """
//						<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:vms="http://vms.neo">
//						   <soapenv:Header/>
//						   <soapenv:Body>
//						      <vms:registerService>
//						         <!--Optional:-->
//						         <vms:scriptShopId>$script_shop_id</vms:scriptShopId>
//						         <!--Optional:-->
//						         <vms:sharingkey>$sharingkey</vms:sharingkey>
//						         <!--Optional:-->
//						         <vms:msisdn>$msisdn</vms:msisdn>
//						         <!--Optional:-->
//						         <vms:packagecode>$packagecode</vms:packagecode>
//						         <vms:channelkey>$channel</vms:channelkey>
//						         <!--Optional:-->
//						         <vms:P1>PACKAGE_CODE</vms:P1>
//						         <!--Optional:-->
//						         <vms:V1>$packagecode</vms:V1>
//						         <!--Optional:-->
//						         <vms:P2>TONE_ID</vms:P2>
//						         <!--Optional:-->
//						         <vms:V2>$packagecode</vms:V2>
//						         <!--Optional:-->
//						         <vms:P3>TO_USER_PHONE_NUMBER</vms:P3>
//						         <!--Optional:-->
//						         <vms:V3>$content</vms:V3>
//						      </vms:registerService>
//						   </soapenv:Body>
//						</soapenv:Envelope>
//						""";
//                else
//                    request = """
//						<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:vms="http://vms.neo">
//						   <soapenv:Header/>
//						   <soapenv:Body>
//						      <vms:registerService>
//						         <vms:scriptShopId>$script_shop_id</vms:scriptShopId>
//						         <vms:sharingkey>$sharingkey</vms:sharingkey>
//						         <vms:msisdn>$msisdn</vms:msisdn>
//						         <vms:packagecode>$packageCode</vms:packagecode>
//						         <vms:channelkey>$channel</vms:channelkey>
//						         <vms:P1>PACKAGE_CODE</vms:P1>
//						         <vms:V1>$packageCode</vms:V1>
//						         <vms:P2>PACKAGE_CODE_API</vms:P2>
//						         <vms:V2>$packageCodeAPI</vms:V2>
//						         <vms:P3></vms:P3>
//						         <vms:V3></vms:V3>
//						      </vms:registerService>
//						   </soapenv:Body>
//						</soapenv:Envelope>
//						""";
//            }
//            System.out.println(request);
//            //String transId = new neo.service.Activation().parseXMLtext(new neo.service.Activation().soapCall(context.get("dataflow_param:sqlmodule"),request),"//*[local-name() = 'return']");
//            String result = new Activation().parseXMLtext(new Activation().soapCall(utilUrl,request),"//*[local-name() = 'return']");
//            System.out.println("result:"+result);
//            cmdResultId = -1;
//            try{
//                rootNode = new XmlSlurper().parseText(result);
//                errorCode = rootNode.Parameter.ErrorCode.text();
//                errorDesc = rootNode.Parameter.ErrorDesc.text();
//                String statusCommand = rootNode.record.COMMANDS_STATUS.text();
//
//
//                if (!errorCode.equals("0")){
//                    context.put("ErrorCodeAPI",errorCode);
//                    context.put("ErrorDescAPI",errorDesc);
//                    return false;
//                }
//                else{
//                    //success
//                    try{
//                        request = rootNode.Parameter.cmdRequest_with_value.text();
//                        response = rootNode.Parameter.cmdresponse_with_value.text();
//                        context.put(rootNode.Parameter.cmdRequest_with_value.name(),rootNode.Parameter.cmdRequest_with_value.text());
//                        context.put(rootNode.Parameter.cmdresponse_with_value.name(),rootNode.Parameter.cmdresponse_with_value.text());
//                        try{
//                            cmdResultId = Integer.parseInt(rootNode.record.COMMANDS_RESULTS_ID.text());
//                        }catch(Exception ex){ex.printStackTrace();}
//                        if(cmdResultId>=0){
//
//                            cmdStatus = rootNode.record.COMMANDS_STATUS.text();
//
//                            String mt = rootNode.record.MESSAGE_SMS.text();
//
//                            if(mt.equals("{msg_forward}")){
//                                mt = rootNode.Parameter.cmdresponse_with_value.text();
//                                mt = mt.substring(rootNode.record.COMMANDS_RET_VAL.text().length()).trim();
//
//                            }
//                            else{
//                                if(context.get("PACKAGE_DYNAMIC").equals("1"))
//                                    mt = mt.replaceAll("\\{MSISDN}",(String)msisdn).replaceAll("\\{PACKAGE_CODE}","$packagecode");
//                                else
//                                    mt = mt.replaceAll("\\{MSISDN}",(String)msisdn).replaceAll("\\{PACKAGE_CODE}",packageCode);
//                            }
//                            String message_other = rootNode.record.MESSAGE_OTHER.text();
//                            if(message_other.equals("{msg_forward}")) {
//                                message_other = rootNode.Parameter.cmdresponse_with_value.text();
//                                message_other = message_other.substring(rootNode.record.COMMANDS_RET_VAL.text().length()).trim();
//                            }
//                            else{
//                                if(context.get("PACKAGE_DYNAMIC").equals("1"))
//                                    message_other = message_other.replaceAll("\\{MSISDN}",(String)msisdn).replaceAll("\\{PACKAGE_CODE}","$packagecode");
//                                else
//                                    message_other = message_other.replaceAll("\\{MSISDN}",(String)msisdn).replaceAll("\\{PACKAGE_CODE}",packageCode);
//                            }
//                            message_other = message_other.replaceAll("\\{SERVICE_KEY}",context.get("SERVICE_KEY_SMS"));
//                            mt = mt.replaceAll("\\{SERVICE_KEY}",context.get("SERVICE_KEY_SMS"));
//
//                            if(!cmdStatus.toUpperCase().equals("SUCCESS")){
//                                //thong bao loi dang ky khong thanh cong cho thue bao
//                                charging = "0";
//                                transactionStatus = "0";
//                                System.out.println("transactionResponse:mt:"+mt+"|||"+msisdn);
//                                transactionResponse = mt;
//                                result = sendSms((String)msisdn,mt);
//                                context.put("ErrorCodeAPI",transactionStatus);context.put("ErrorDescAPI",mt);
//                                return false;
//                            }
//                            else{
//                                System.out.println("register successfully...");
//                                if(context.get("SERVICE_KEY").equals("NHAC_CHO_FUNRING")
//                                        ||context.get("SERVICE_KEY").equals("TANG_NHAC_CHO")||context.get("SERVICE_KEY").equals("ZING_TANG_NHACCHO")){
//                                    //get song name
//                                    request = """
//									<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:vms="http://vms.neo">
//									   <soapenv:Header/>
//									   <soapenv:Body>
//									      <vms:sendCommand>
//									         <vms:cmd>FUN.GETTONES -toneid=$packagecode</vms:cmd>
//									         <vms:channel>MSOCIAL_$CHANNEL_KEY</vms:channel>
//									         <vms:vasgatehost>$VASGATE_SERVER_IP</vms:vasgatehost>
//									         <vms:vasgateport>$VASGATE_SERVER_PORT</vms:vasgateport>
//									      </vms:sendCommand>
//									   </soapenv:Body>
//									</soapenv:Envelope>
//									""";
//                                    System.out.println(request);
//                                    String infosFunring = "";
//                                    try{
//                                        infosFunring = new Activation().parseXMLtext(new Activation().soapCall(context.get("dataflow_param:utilmodule"),request),"//*[local-name() = 'return']");
//                                        System.out.println("---infosFunring:"+infosFunring+"-----");
//                                        //---infosFunring:6327292|Gat di nuoc mat|5000-----
//                                        context.put("CHECK_BAI_HAT_FUNRING_REQUEST",request);
//                                        context.put("CHECK_BAI_HAT_FUNRING_RESPONSE",infosFunring);
//                                    }catch(Exception ex){
//                                        System.out.println(ex.getMessage());
//                                    }
//
//                                    if(infosFunring.indexOf("|")>=0){
//                                        mt = mt.replaceAll("\\{SONG_NAME}",infosFunring.split("\\|")[1]).replaceAll("\\{PACKAGE_PRICE}",infosFunring.split("\\|")[2]);
//                                        message_other = message_other.replaceAll("\\{SONG_NAME}",infosFunring.split("\\|")[1]).replaceAll("\\{PACKAGE_PRICE}",infosFunring.split("\\|")[2]);
//                                    }
//                                    else{
//                                        mt = mt.replaceAll("\\{SONG_NAME}","$packagecode").replaceAll("\\{PACKAGE_PRICE}",(String)context.get("PACKAGE_PRICE"));
//                                        message_other = message_other.replaceAll("\\{SONG_NAME}","$packagecode").replaceAll("\\{PACKAGE_PRICE}",(String)context.get("PACKAGE_PRICE"));
//                                    }
//
//                                }
//                                transactionResponse = mt;
//                                //Thanh cong roi thi can lay gia cuoc thuc tru de chot hoa hong
//                                if(chargingType.equals("0")){
//                                    //tru cuoc offline
//                                    transactionStatus = "1";
//                                    context.put("ErrorCodeAPI",transactionStatus);context.put("ErrorDescAPI",mt);
//                                    price = Double.parseDouble(context.get("PACKAGE_PRICE"));
//                                    charging = "0";
//                                }
//                                else{
//                                    transactionStatus = "1";
//                                    registerInfo = getPriceFromResult(response,context.get("SERVICE_KEY"));
//                                    System.out.println("registerInfo:"+registerInfo);
//return status|charging|price|error_code
//
//                                    String[] infoRegister = registerInfo.split("\\|");
//                                    charging = infoRegister[1];
//                                    price = Double.parseDouble(infoRegister[2]);
//                                    chargingStatus = infoRegister[3];
//
//                                }
//                            }
//                            System.out.println("mt:"+mt);
//                            System.out.println("message_other:"+message_other);
//                            System.out.println("SEND_SMS:"+context.get("SEND_SMS")+",,,"+msisdn);
//
//                            result = sendSms((String)msisdn,mt);
//                            System.out.println("result:"+result);
//                            String tmpMsg = "";
//                            if (!context.get("SERVICE_KEY").equals("NHAC_CHO_FUNRING"))
//                                tmpMsg = getValueFromKeyMultiRecords($soap_8_extract1,"record","MT_TYPE_KEY","CAIDAT_GOI_DICHVU_THANHCONG_DIEMBAN","MT_TYPE_VALUE");
//                            else
//                                tmpMsg = getValueFromKeyMultiRecords($soap_8_extract1,"record","MT_TYPE_KEY","CAIDAT_DICHVU_THANHCONG_DIEMBAN","MT_TYPE_VALUE");
//                            tmpMsg = tmpMsg.replaceAll("\\{SERVICE_KEY}",context.get("SERVICE_KEY"));
//                            tmpMsg = tmpMsg.replaceAll("\\{MSISDN}","$msisdn");
//                            tmpMsg = tmpMsg.replaceAll("\\{PACKAGE_CODE}","$packagecode");
//
//                            if(transactionStatus.equals("1")){
//                                //thanh cong
//
//                                if(context.get("SEND_SMS").equals("1") && context.get("channel").equals("SMS")){
//                                    result = sendSms("$sharingkey",tmpMsg);
//                                    System.out.println("result:"+result);
//                                }
//                            }else{
//                                //that bai
//                                if(context.get("SEND_SMS").equals("1") && context.get("channel").equals("SMS")){
//                                    result = sendSms("$sharingkey",tmpMsg);
//                                    System.out.println("result:"+result);
//                                }
//                            }
//                        }
//                        else{
//                            System.out.println("Process manually...");
//                            String mt = "lenh chua duoc cau hinh map code";
//                            charging = "0";
//                            transactionStatus = "0";
//                            transactionResponse = mt;
//                            if(response.startsWith("-1|")){
//                                context.put("ErrorCodeAPI","-1"); context.put("ErrorDescAPI","He thong loi!");
//                                //for test
//                                //return true;
//                                return false;
//                            }
//                            else{
//                                context.put("ErrorCodeAPI",transactionStatus);context.put("ErrorDescAPI",mt);
//                                return true;
//                            }
//                        }
//
//                    }catch(Exception ex){
//                        ex.printStackTrace();
//                    }
//                }
//            }catch(Exception ex){
//                ex.printStackTrace();
//                context.put("ErrorCodeAPI","-1"); context.put("ErrorDescAPI","He thong loi");
//                result = sendSms((String)msisdn,"He thong dang ban, xin moi thu lai sau!");
//                return false;
//            }
//        }catch(Exception ex){
//            System.out.println(ex.getMessage());
//            result = sendSms((String)msisdn,"He thong dang ban, xin moi thu lai sau!");
//            context.put("ErrorCodeAPI","-1"); context.put("ErrorDescAPI","Khong dang ky duoc dich vu cho thue bao");
//        }finally{
//            try{
//                context.put("ErrorCodeAPI",(String)transactionStatus);
//                context.put("ErrorDescAPI",transactionResponse);
//                //updateTransaction
//                String chargingTime = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
//                String monitorDate = "";
//                java.util.Calendar cal = java.util.Calendar.getInstance();
//                String register_days = context.get("REGISTERS_DAYS");
//                if (register_days == null || register_days.equals("")) register_days = "0";
//                cal.add(java.util.Calendar.DATE,Integer.parseInt(register_days));
//                monitorDate = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(cal.getTime());
//                result = updateTransaction(String.valueOf(transactionId),transactionStatus,context.get("ErrorDescAPI"),Integer.parseInt(context.get("CHANNEL_ID")),context.get("packagecode"),
//                        price,Integer.parseInt(charging),Integer.parseInt(chargingType),chargingStatus,chargingTime,0,0,0,0,"","","",0,Integer.parseInt(context.get("SERVICE_ID")));
//                System.out.println("msisdn="+msisdn+",transaction_id="+transactionId+",transaction_status:"+transactionStatus+",update_result:"+result);
//                if(transactionStatus.equals("1") && result.equals("1")){
//                    if(result.equals("1")){
//                        int tmp = 0; if(mobType.equals("MG")) {tmp =2;} else if(mobType.equals("MC")) {tmp =1};
//                        int type_cs = 1; double price_new = price;
//                        if (price_old>0) {price_new = price - price_old; type_cs=3};
//                        result = getHoahong(String.valueOf(transactionId),price_new,tmp,startActiveDate,type_cs);
//                        System.out.println("HOAHONG:"+result+"||"+transactionId+"||" +msisdn + ", type_cs: " + type_cs + ", price_new: " + price_new);
//                        try{
//                            String[] hhInfo = result.split(",");
//                            //update hoa hong cho giao dich
//                            result = updateTransaction(String.valueOf(transactionId),transactionStatus,context.get("ErrorDescAPI"),Integer.parseInt(context.get("CHANNEL_ID")),context.get("packagecode"),
//                                    price,Integer.parseInt(charging),Integer.parseInt(chargingType),chargingStatus,chargingTime,Double.parseDouble(hhInfo[0]),Double.parseDouble(hhInfo[1]),Double.parseDouble(hhInfo[2]),Double.parseDouble(hhInfo[3]),(String)tmp,startActiveDate,monitorDate,Integer.parseInt(hhInfo[4]),Integer.parseInt(context.get("SERVICE_ID")));
//
//                            //System.out.println("update hoa hong cho giao dich:"+result);
//                        }catch(Exception ex){
//                            context.put("ErrorCodeAPI","61"); context.put("ErrorDescAPI","Giao dich khong duoc chot hoa hong "+result);
//                        }
//                    }
//                }
//            }catch(Exception ex){ex.printStackTrace();}
//            context.put("debug_end_time",System.currentTimeMillis());
//            System.out.println("DATAFLOW_DELAYS:"+(String)transactionId+"|"+((Long)context.get("debug_end_time")-(Long)context.get("debug_start_time"))/1000);
//            logSql(msisdn,(String)transactionId,"REGISTER_SERVICE","11","REGISTER_SERVICE",context.get("cmdRequest_with_value"),context.get("cmdresponse_with_value").replaceAll("&","va")+stepResult,context.get("ErrorCodeAPI")+"|"+context.get("ErrorDescAPI"),startTime,new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()),"sharingkey=$sharingkey,serviceid=$serviceid,packagecode=$packagecode,channel=$channel,dataflow:registerService",(String)$script_shop_id);
//            //new neo.service.Activation().logMessage(msisdn+"##"+(String)transactionId+"##"+"REGISTER_SERVICE"+"##"+"11"+"##"+"REGISTER_SERVICE"+"##"+context.get("cmdRequest_with_value")+"##"+context.get("cmdresponse_with_value")+"##"+context.get("ErrorCodeAPI")+"|"+context.get("ErrorDescAPI")+"##"+startTime+"##"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+"##"+"sharingkey=$sharingkey"+"##"+"serviceid=$serviceid"+"##"+"packagecode=$packagecode"+"##"+"channel=$channel"+"##"+"dataflow:registerService"+"##"+(String)$script_shop_id);
//
//        }
//    }
//    stepResult = true;
//    return true;
//}catch(Exception ex){
//    ex.printStackTrace();
//    result = sendSms((String)msisdn,"He thong dang ban, xin moi thu lai sau!");
//    //System.out.println(ex.getMessage());
//    context.put("ErrorCodeAPI","-1"); context.put("ErrorDescAPI",ex.getMessage());
//    logSql(msisdn,(String)transactionId,"REGISTER_SERVICE","11","REGISTER_SERVICE",context.get("cmdRequest_with_value"),context.get("cmdresponse_with_value").replaceAll("&","va")+stepResult,context.get("ErrorCodeAPI")+"|"+context.get("ErrorDescAPI"),startTime,new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()),"sharingkey=$sharingkey,serviceid=$serviceid,packagecode=$packagecode,channel=$channel,dataflow:registerService",(String)$script_shop_id);
//    return false;
//}
