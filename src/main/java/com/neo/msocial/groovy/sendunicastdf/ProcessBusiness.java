package com.neo.msocial.groovy.sendunicastdf;

import com.neo.msocial.dto.TransactionIdDto;
import com.neo.msocial.service.Activation;
import com.neo.msocial.utils.GenericsRequest;
import com.neo.msocial.utils.RedisUtils;
import com.neo.msocial.utils.UtilServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.*;
import java.util.*;

@Component
public class ProcessBusiness {

    @Autowired
    private RedisUtils context;

    @Autowired
    private UtilServices utilServices;

    @Autowired
    private Activation activation;

    @Autowired
    private GenericsRequest<TransactionIdDto> transactionIdDtoGenericsRequest;

    String logSql(String msisdn, String transactionId, String stepName, String stepIndex, String stepKey, String stepInput, String stepOutput, String stepAction, String startTime, String endTime, String inputParameter, String scriptShopId) {
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
        String result = activation.soapCall(context.get("dataflow_param:sqlmodule"), str_soap.toString());
        System.out.println(result);
    }

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

//    String getValueFromKey (String body, String key) {
//            String ret = "";
//            try
//
//    {
//        def rootNode = new XmlSlurper().parseText(body);
//        for (def record : rootNode.record.children()) {
//            if (record.name().equals(key)) {
//                ret = record.text();
//                break;
//            }
//        }
//    }catch(
//    Exception ex)
//
//    {
//        //ex.printStackTrace();
//        ret = "";
//    }
//            return ret;
//}
//    def getValueFromKeyMultiRecords = {String body, String rootTag, String tagName, String key, String value ->
//            String ret = new neo.service.ParseXml().getValueFromKey(body, rootTag, tagName, key, value);
////System.out.println("RET:"+ret);
//            return ret;
//                    }

    String createTransaction(String script_shop_id, String sharing_key_id, String messageSend, String messageReceive,
                             String msisdn, String transactionStatus, String transactionResponse, String channelIDGT, String channelIDCF,
                             String content_extra, String packageCodeId, String price, String charging, String chargingType, String chargingStatus,
                             String avenueOne, String avenueMonth, String avenueAgentOne, String avenueAgentMonth, String agent_name_id, String partnerId, String serviceId, int flag) {
        String ret = "";

        Map<String,String> params = new HashMap<>();
        params.put("typeQuery","query");
        params.put("Service","get_transaction_id");
        params.put("Provider","default");
        params.put("ParamSize","0");

        try {
            List<TransactionIdDto> transactionIdDtoList = transactionIdDtoGenericsRequest.getData(params);

            String transId = transactionIdDtoList.get(0).getTRANSACTION_ID();
            System.out.println("---ret1:" + transId + "----xxsxs-");
            request = """
                    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:vms="http://vms.neo">
                    	   <soapenv:Header/>
                    	   <soapenv:Body>
                    	      <vms:updateXml>
                    	  		 <!--Optional:-->
                    	         <vms:Service>generate_transaction</vms:Service>
                    	         <!--Optional:-->
                    	         <vms:Provider>default</vms:Provider>
                    	         <!--Optional:-->
                    	         <vms:ParamSize>24</vms:ParamSize>
                    	         <vms:P1>$script_shop_id</vms:P1>
                     		    <vms:P2>$sharing_key_id</vms:P2>
                     		    <vms:P3>$messageSend</vms:P3>
                     		    <vms:P4>$messageReceive</vms:P4>
                     		    <vms:P5>$msisdn</vms:P5>
                     		    <vms:P6>$transactionStatus</vms:P6>
                     		    <vms:P7>$transactionResponse</vms:P7>
                     		    <vms:P8>$channelIDGT</vms:P8>
                     		    <vms:P9>$channelIDCF</vms:P9>
                     		    <vms:P10>$content_extra</vms:P10>
                    		    <vms:P11>$packageCodeId</vms:P11>
                     		    <vms:P12>$price</vms:P12>
                     		    <vms:P13>$charging</vms:P13>
                     		    <vms:P14>$chargingType</vms:P14>
                     		    <vms:P15>$chargingStatus</vms:P15>
                     		    <vms:P16>$avenueOne</vms:P16>
                     		    <vms:P17>$avenueMonth</vms:P17>
                     		    <vms:P18>$avenueAgentOne</vms:P18>
                     		    <vms:P19>$avenueAgentMonth</vms:P19>
                     		    <vms:P20>$agent_name_id</vms:P20>
                     		    <vms:P21>$transId</vms:P21>
                     		    <vms:P22>$partnerId</vms:P22>
                     		    <vms:P23>$serviceId</vms:P23>
                     		    <vms:P24>$flag</vms:P24>
                    	      </vms:updateXml>
                    	   </soapenv:Body>
                    	</soapenv:Envelope>
                    	""";

            StringBuilder str_soap = new StringBuilder();
            str_soap.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:vms=\"http://vms.neo\">");
            str_soap.append("<soapenv:Header/><soapenv:Body>");
            str_soap.append("<vms:updateXml>");
            str_soap.append("<vms:args0>").append(serviceNumber).append("</vms:args0>");
            str_soap.append("<vms:args1>").append(receiver).append("</vms:args1>");
            str_soap.append("<vms:args2>").append(messageSms).append("</vms:args2>");
            str_soap.append("<vms:args3>").append(smsHost).append("</vms:args3>");
            str_soap.append("<vms:args4>").append(smsPort).append("</vms:args4>");
            str_soap.append("<vms:args5>").append(smsLookup).append("</vms:args5>");
            str_soap.append("</vms:sendSms></soapenv:Body></soapenv:Envelope>");

            ret = activation.parseXMLtext(activation.soapCall(context.get("dataflow_param:sqlmodule"), request), "//*[local-name() = 'return']");
            System.out.println("---ret:" + ret + "--AAAAAAAAAAAA---");
            ret = transId;
        } catch (Exception ex) {
            ex.printStackTrace();
            ret = "-1";
        }
        return ret;
    }

    String createTempTrans (String transactionId, String sharingKey, String msisdn) {
            String ret = "";
    String request = """
            <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:vms="http://vms.neo">
               <soapenv:Header/>
               <soapenv:Body>
                  <vms:queryXml>
              		 <!--Optional:-->
                     <vms:Service>select_temp_accept_id</vms:Service>
                     <!--Optional:-->
                     <vms:Provider>default</vms:Provider>
                     <!--Optional:-->
                     <vms:ParamSize>1</vms:ParamSize>
                     <vms:P1>$msisdn</vms:P1>
                  </vms:queryXml>
               </soapenv:Body>
            </soapenv:Envelope>
            """;
                    try

    {
        ret = activation.parseXMLtext(activation.soapCall(context.get("dataflow_param:sqlmodule"), request), "//*[local-name() = 'return']");
        System.out.println("---ret:" + ret + "--BBBBBBBBBBBBBBBB---");
        int tempAcceptId = Integer.parseInt(getValueFromKey(ret, "ACCEPT_ID"));
        System.out.println("tempAcceptId:" + tempAcceptId);
        if (tempAcceptId == 0) tempAcceptId = 1;
        else tempAcceptId += 1;
        ret = String.valueOf(tempAcceptId);
        System.out.println("retAAAAAAAAAAAAAAAAAA:" + ret);
        request = """
                <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:vms="http://vms.neo">
                   <soapenv:Header/>
                   <soapenv:Body>
                      <vms:updateXml>
                  		 <!--Optional:-->
                         <vms:Service>insert_temp_accept_id</vms:Service>
                         <!--Optional:-->
                         <vms:Provider>default</vms:Provider>
                         <!--Optional:-->
                         <vms:ParamSize>4</vms:ParamSize>
                         <vms:P1>$transactionId</vms:P1>
                         <vms:P2>$sharingKey</vms:P2>
                         <vms:P3>$msisdn</vms:P3>
                         <vms:P4>$tempAcceptId</vms:P4>
                      </vms:updateXml>
                   </soapenv:Body>
                </soapenv:Envelope>
                """;
        try {
            System.out.println(request);
            String retval = new neo.service.Activation().parseXMLtext(new neo.service.Activation().soapCall(context.get("dataflow_param:sqlmodule"), request), "//*[local-name() = 'return']");
            System.out.println("---ret:" + retval + "---CCCCCCCCCCCCC--");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }catch(
    Exception ex)

    {
        //ex.printStackTrace();
        return "-1";
    }
                    System.out.println("AAAAAAAAAAAAAAAAAAA:"+ret);
                    return ret;
}
    def kiemtraMI = {String info, String serviceId, String packageCode ->
            String[]retval=new String[9];
        retval[0]="0";
        return retval;

        if(info==null||info.indexOf("DATA_WEB_SUCC")<0){
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
        String pckNeedCheck=info.split("\\|")[0];
        pckNeedCheck=pckNeedCheck.substring(info.indexOf(" ")).trim();
        if(pckNeedCheck.equals(packageCode)){
        retval[0]="1";
        return retval;
        }

        String request="""
	<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:vms="http://vms.neo">
	   <soapenv:Header/>
	   <soapenv:Body>
	      <vms:queryXml>
	  		 <!--Optional:-->
	         <vms:Service>get_service_package_detail</vms:Service>
	         <!--Optional:-->
	         <vms:Provider>default</vms:Provider>
	         <!--Optional:-->
	         <vms:ParamSize>3</vms:ParamSize>
	         <vms:P1>$packageCode</vms:P1>
 		    <vms:P2>$pckNeedCheck</vms:P2>
 		    <vms:P3>$serviceId</vms:P3>
	      </vms:queryXml>
	   </soapenv:Body>
	</soapenv:Envelope>
	""";
        String pckInfo="";
        double usedPckPrice=-1;
        double registerPckPrice=-1;
        String capacity="";
        String cycle="";
        try{
        pckInfo=new neo.service.Activation().parseXMLtext(new neo.service.Activation().soapCall(context.get("dataflow_param:utilmodule"),request),"//*[local-name() = 'return']");
        System.out.println("---pckInfo:"+pckInfo+"-----");
        usedPckPrice=Double.parseDouble(getValueFromKeyMultiRecords(pckInfo,"root","PACKAGE_CODE",pckNeedCheck,"PACKAGE_PRICE"));
        registerPckPrice=Double.parseDouble(getValueFromKeyMultiRecords(pckInfo,"root","PACKAGE_CODE",packageCode,"PACKAGE_PRICE"));
        capacity=Double.parseDouble(getValueFromKeyMultiRecords(pckInfo,"root","PACKAGE_CODE",packageCode,"CAPACITY"));
        cycle=Double.parseDouble(getValueFromKeyMultiRecords(pckInfo,"root","PACKAGE_CODE",packageCode,"PACKAGE_CYCLE"));
        capacityPckNeedCheck=Double.parseDouble(getValueFromKeyMultiRecords(pckInfo,"root","PACKAGE_CODE",pckNeedCheck,"CAPACITY"));
        cyclePckNeedCheck=Double.parseDouble(getValueFromKeyMultiRecords(pckInfo,"root","PACKAGE_CODE",pckNeedCheck,"PACKAGE_CYCLE"));
        if(usedPckPrice.equals("M120")&&(packageCode.equals("M120")||packageCode.equals("KM120"))){
        retval[0]="10";retval[1]=pckNeedCheck;retval[2]=usedPckPrice;retval[3]=pckNeedCheck;retval[4]=usedPckPrice;retval[5]=capacity;retval[6]=cycle;
        return retval;
        }
        if(registerPckPrice==usedPckPrice){
        retval[0]="10";retval[1]=pckNeedCheck;retval[2]=usedPckPrice;retval[3]=pckNeedCheck;retval[4]=usedPckPrice;retval[5]=capacity;retval[6]=cycle;
        return retval;
        }
        //gia goi dang ky > gia goi thue bao dang su dung
        else
        if(registerPckPrice>usedPckPrice){
        retval[0]="2";retval[1]=packageCode;retval[2]=registerPckPrice;retval[3]=pckNeedCheck;retval[4]=usedPckPrice;retval[5]=capacity;retval[6]=cycle;retval[7]=capacityPckNeedCheck;retval[8]=cyclePckNeedCheck;
        }
        else if(registerPckPrice<usedPckPrice){
        retval[0]="3";retval[1]=packageCode;retval[2]=registerPckPrice;retval[3]=pckNeedCheck;retval[4]=usedPckPrice;retval[5]=capacity;retval[6]=cycle;retval[7]=capacityPckNeedCheck;retval[8]=cyclePckNeedCheck;
        }
        else{
        retval[0]="1";
        }
        return retval;
        }catch(Exception ex){
        retval[0]="-1";return retval;
        }
        }

        def checkPkgDaiky={String msisdn,String pkg,String channelName->
        try{
        String utilUrl=context.get("dataflow_param:utilmodule");
        String request="""
			<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:vms="http://vms.neo">
			   <soapenv:Header/>
			   <soapenv:Body>
					<vms:checkPkgDaikyV2>
					<vms:msisdn>$msisdn</vms:msisdn>
					<vms:pkg>$pkg</vms:pkg>
					<vms:channel>$channelName</vms:channel>
					</vms:checkPkgDaikyV2>
			   </soapenv:Body>
			</soapenv:Envelope>
			""";
        String result=new neo.service.Activation().parseXMLtext(new neo.service.Activation().soapCall(utilUrl,request),"//*[local-name() = 'return']");
        return result;
        }catch(Exception e){
        return"-1|"+e.getMessage();
        }
        }

//System.out.println("AAAAAAAAAABCCCCCCCCCC:"+context.get("check_mi_fc_status"));
        String mi_status_info=context.get("cmdresponse_with_value");
        String mi_huy_info=context.get("checkhuy_cmdresponse_with_value");
        String billing_flag=context.get("BILLING_ISCONTINUE");
//System.out.println("BILLING_FLAG:"+billing_flag);
        if(context.get("BILLING_ISCONTINUE").equals("true"))billing_flag="1";
        else billing_flag="0";
        System.out.println("mi_status_info_32:"+mi_status_info);
        System.out.println("mi_huy_info_32:"+mi_huy_info+","+context.get("check_mi_fc_status"));
        String packageCode=context.get("PACKAGE_CODE");
        String serviceId=context.get("serviceid");
        int pckCycle=-1;
        boolean isSendSms=false;
        String startTime=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        boolean stepResult=false;
        String charging="";
        String chargingStatus="";
        long transactionId=-1;
        String mt="";
        if((boolean)context.get("check_mi_fc_status")==false)return false;
        if(context.get("SEND_SMS").equals("1"))isSendSms=true;

        try{
        String[]info=kiemtraMI(mi_status_info,serviceId,packageCode);
	/*
	 * String script_shop_id,String sharing_key_id, String messageSend,String messageReceive,
		String msisdn, String transactionStatus,String transactionResponse, String channelIDGT,String channelIDCF,
		String content, String  packageCodeId, String price, String charging, String chargingType, String chargingStatus,
		String avenueOne,String avenueMonth,String avenueAgentOne,String avenueAgentMonth, String agent_name_id
	 */

        //06.06.2020: Bo sung luat xu ly check Goi dai, dk lai:
        int flag=0;
        //Check tiep: Vao case goi dai khong? neu la goi dai cho DK va = hh 0d?
        String daiky_state=checkPkgDaiky(context.get("msisdn"),context.get("PACKAGE_CODE"),context.get("CHANNEL"));
        //daiky_state="0";
        if(daiky_state!=null&&daiky_state.equals("0")){
        flag=2;//2 la case cho dang ky nhung ko co hoa hong.
        billing_flag="2";
        }

        //Bo sung check huy cho dang ky tiep hh =0:
        String CO_LICH_SU_HUY=context.get("CO_LICH_SU_HUY");
        if(CO_LICH_SU_HUY!=null){
        if(CO_LICH_SU_HUY.equals("1")){
        flag=3;//3 la co Lich su check huy. va HH =0;
        billing_flag="3";
        }
        }

        if(context.get("check_huy_status").equals("1")){
        charging="3";charging_status="CO_LICH_SU_HUY_DICH_VU_30_NGAYS";
        }
        //System.out.println(msisdn+",info[0]:"+info[0]+",check_huy_status:"+context.get("check_huy_status"));
        if(context.get("SERVICE_KEY").equals("MOBILEINTERNET")||context.get("SERVICE_KEY").equals("FASTCONNECT")){

        if(info!=null&&info[0]=="0"){
        if(context.get("check_huy_status").equals("0")){
        try{
        String request="""
					<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:vms="http://vms.neo">
					   <soapenv:Header/>
					   <soapenv:Body>
					      <vms:queryXml>
						 <!--Optional:-->
						 <vms:Service>checkLoiDungCheckPriceSpecific</vms:Service>
						 <!--Optional:-->
						 <vms:Provider>default</vms:Provider>
						 <!--Optional:-->
						 <vms:ParamSize>2</vms:ParamSize>
						 <!--Optional:-->
						 <vms:P1>$msisdn</vms:P1>
						    <vms:P2>$serviceid</vms:P2>
					      </vms:queryXml>
					   </soapenv:Body>
					</soapenv:Envelope>
					""";
        System.out.println("---------DAILV_CLD:"+request);
        String ret=new neo.service.Activation().parseXMLtext(new neo.service.Activation().soapCall(context.get("dataflow_param:sqlmodule"),request),"//*[local-name() = 'return']");
        System.out.println("---------DAILV_CLD:"+request);
        System.out.println("---------DAILV_CLD:"+ret);
        System.out.println(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+"|AAAAAAAAAAA:"+msisdn);
        try{
        price=getValueFromKey(ret,"PRICE");
        policyTypeName=getValueFromKey(ret,"POLICY_TYPE_NAME");
        System.out.println("policyTypeName::::::"+policyTypeName);
        }catch(Exception ex){System.out.println(ex.getMessage()+"||||AAAAAAAA");}
        System.out.println(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+"|AAAAAAAAAAA:"+msisdn);
        //co cau hinh chinh sach chong loi dung
        if(!policyTypeName.equals("")){
        if(Double.parseDouble(context.get("PACKAGE_PRICE"))<=Double.parseDouble(price)){
        //get list package co gia cao hon
        request="""
							<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:vms="http://vms.neo">
							   <soapenv:Header/>
							   <soapenv:Body>
							      <vms:value>
								 <!--Optional:-->
								 <vms:Service>get_list_package_high_price</vms:Service>
								 <!--Optional:-->
								 <vms:Provider>default</vms:Provider>
								 <!--Optional:-->
								 <vms:ParamSize>2</vms:ParamSize>
								 <!--Optional:-->
								 <vms:P1>$serviceid</vms:P1>
								    <vms:P2>$price</vms:P2>
							      </vms:value>
							   </soapenv:Body>
							</soapenv:Envelope>
							""";
        ret=new neo.service.Activation().parseXMLtext(new neo.service.Activation().soapCall(context.get("dataflow_param:sqlmodule"),request),"//*[local-name() = 'return']");
        if(ret.equals(","))ret="";
        if(ret.startsWith(","))ret=ret.substring(1);
        System.out.println(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+"|AAAAAAAAAAA:"+msisdn);
        context.put("ErrorCodeAPI","22");context.put("ErrorDescAPI","Thong bao ban khong duoc gioi thieu goi cuoc nay cho thue bao, cac goi co the duoc gioi thieu la "+ret);
        if(sendSmsForSharing&&context.get("channel").equals("SMS")){
        if(ret.equals("")){
        mt=getValueFromKeyMultiRecords($soap_34_extract1,"record","MT_TYPE_KEY","NOTICE_CHAN_GIOITHIEU_PSC_M0","MT_TYPE_VALUE").replaceAll("\\{MSISDN}",String.valueOf($msisdn)).replaceAll("\\{SERVICE_KEY}",context.get("SERVICE_KEY")).replaceAll("\\{PACKAGE_CODE}",context.get("PACKAGE_CODE")).replaceAll("\\{PACKAGE_PRICE}",context.get("PACKAGE_PRICE"));
        if(mt==null||mt.equals(""))
        mt=getValueFromKeyMultiRecords($soap_8_extract1,"record","MT_TYPE_KEY","NOTICE_CHAN_GIOITHIEU_PSC_M0","MT_TYPE_VALUE").replaceAll("\\{MSISDN}",String.valueOf($msisdn)).replaceAll("\\{SERVICE_KEY}",context.get("SERVICE_KEY")).replaceAll("\\{PACKAGE_CODE}",context.get("PACKAGE_CODE")).replaceAll("\\{PACKAGE_PRICE}",context.get("PACKAGE_PRICE"));
        }
        else{
        mt=getValueFromKeyMultiRecords($soap_34_extract1,"record","MT_TYPE_KEY","NOTICE_CHAN_GIOITHIEU_PSC_M0","MT_TYPE_VALUE").replaceAll("\\{MSISDN}",String.valueOf($msisdn)).replaceAll("\\{SERVICE_KEY}",context.get("SERVICE_KEY")).replaceAll("\\{PACKAGE_CODE}",context.get("PACKAGE_CODE")).replaceAll("\\{PACKAGE_PRICE}",context.get("PACKAGE_PRICE")).replaceAll("\\{LIST_PACKAGE}",ret);
        if(mt==null||mt.equals(""))
        mt=getValueFromKeyMultiRecords($soap_8_extract1,"record","MT_TYPE_KEY","NOTICE_CHAN_GIOITHIEU_PSC_M0","MT_TYPE_VALUE").replaceAll("\\{MSISDN}",String.valueOf($msisdn)).replaceAll("\\{SERVICE_KEY}",context.get("SERVICE_KEY")).replaceAll("\\{PACKAGE_CODE}",context.get("PACKAGE_CODE")).replaceAll("\\{PACKAGE_PRICE}",context.get("PACKAGE_PRICE")).replaceAll("\\{LIST_PACKAGE}",ret);
        }
        ret=sendSms(context.get("sharingkey"),mt);
        System.out.println("ret:"+ret);
        }
        System.out.println(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+"|AAAAAAAAAAA:"+msisdn);

        return false;
        }
        }
        }catch(Exception ex){
        System.out.println(ex.getMessage());
        }
        }
        }
        }
        //System.out.println("---------------------MACHINHANH:"+$ma_chi_nhanh);
        //System.out.println("---------------------MACHINHANH:"+context.get("ma_chi_nhanh"));
        String maChiNhanh=String.valueOf($ma_chi_nhanh);
        String partnerId="";
        if(maChiNhanh.equals("0"))
        partnerId=String.valueOf($partner_id);
        else
        partnerId=maChiNhanh;
        if(context.get("PACKAGE_DYNAMIC").equals("0")){
        transactionId=Long.parseLong(createTransaction(String.valueOf($script_shop_id),String.valueOf($sharing_key_id),"","",String.valueOf($msisdn),"","",String.valueOf($channel_id),"",context.get("content"),context.get("PACKAGE_CODE_ID"),context.get("PACKAGE_PRICE"),charging,context.get("CHARGING_TYPE"),chargingStatus,"","","","",String.valueOf($agent_id),partnerId,String.valueOf(context.get("SERVICE_ID")),Integer.parseInt(billing_flag)));
        }
        else
        transactionId=Long.parseLong(createTransaction(String.valueOf($script_shop_id),String.valueOf($sharing_key_id),"","",String.valueOf($msisdn),"","",String.valueOf($channel_id),"",context.get("packagecode"),context.get("PACKAGE_CODE_ID"),context.get("PACKAGE_PRICE"),charging,context.get("CHARGING_TYPE"),chargingStatus,"","","","",String.valueOf($agent_id),partnerId,String.valueOf(context.get("SERVICE_ID")),Integer.parseInt(billing_flag)));
        System.out.println("TRANSACTION_ID:"+transactionId);
        if(transactionId<0){
        context.put("ErrorCodeAPI","34");context.put("ErrorDescAPI","Loi he thong khong khoi tai duoc giao dich.");
        return false;
        }
        int msgId=Integer.parseInt(createTempTrans(String.valueOf(transactionId),"$sharingkey",String.valueOf($msisdn)));
        System.out.println("FFFFFFFFF:"+msgId);
        if(msgId<0){
        context.put("ErrorCodeAPI","34");context.put("ErrorDescAPI","Loi he thong khong khoi tai duoc giao dich.");
        return false;
        }
        context.put("TRANSACTION_ID",transactionId);
        context.put("ErrorCodeAPI",String.valueOf(transactionId));
        //dich vu chi co 1 goi
        if(context.get("PACKAGE_TYPE").equals("1")){
        mt=getValueFromKeyMultiRecords($soap_8_extract1,"record","MT_TYPE_KEY","MT_GIOI_THIEU_GOI_THANH_CONG","MT_TYPE_VALUE");
        mt=mt.replaceAll("\\{SERVICE_KEY}",context.get("SERVICE_KEY_SMS")).replaceAll("\\{PACKAGE_CODE}",context.get("PACKAGE_CODE_SMS")).replaceAll("\\{PACKAGE_PRICE}",context.get("PACKAGE_PRICE"));
        mt=mt.replaceAll("\\{SERVICE_NUMBER}",context.get("SERVICE_NUMBER")).replaceAll("\\{MSG_ID}",String.valueOf(msgId));
        if(context.get("CAPACITY").equals("")){
        mt=mt.replaceAll("\\{CYCLE_DUNGLUONG}",context.get("PACKAGE_CYCLE")+"ngay");
        }
        else{
        mt=mt.replaceAll("\\{CYCLE_DUNGLUONG}",context.get("CAPACITY"));
        }
        ret=sendSms(String.valueOf($msisdn),mt);
        System.out.println("ret:"+ret);
        if(isSendSms){
        mt=getValueFromKeyMultiRecords($soap_8_extract1,"record","MT_TYPE_KEY","THONG_BAO_DIEM_BAN_GT_TC","MT_TYPE_VALUE");
        mt=mt.replaceAll("\\{msisdn}",String.valueOf($msisdn));
        ret=sendSms("$sharingkey",mt);
        }
        }
        //dich vu co nhieu goi
        else if(context.get("PACKAGE_TYPE").equals("0")){

        if(context.get("SERVICE_KEY").equals("MOBILEINTERNET")&&!packageCode.equals("MAX10")&&!packageCode.equals("MAX30")&&info!=null&&(info[2].equals("2")||info[2].equals("3"))){
        //Ban dang su dung goi {SERVICE_KEY} {PACKAGE_IN_USE} voi dung luong {PACKAGE_IN_USE_CAPACITY}, muc cuoc {PACKAGE_IN_USE_PRICE}dong. Ban co muon chuyen sang goi cuoc {PACKAGECODE}, gia cuoc {PACKAGE_PRICE}/{PACKAGE_CYCLE}ngay do dai ly cua MobiFone gioi thieu khong? Soan Y{CONFIRMID} gui {SERVICE_NUMBER} de xac nhan trong 24 gio hoac Soan N{CONFIRMID} gui {SERVICE_NUMBER} de tu choi.
        //retval[0] = "2";retval[1]=packageCode;retval[2]=registerPckPrice;retval[3]=pckNeedCheck;retval[4]=usedPckPrice;retval[5]=capacity;retval[6]=cycle;retval[7] = capacityPckNeedCheck; retval[8] = cyclePckNeedCheck;
        mt=getValueFromKeyMultiRecords($soap_8_extract1,"record","MT_TYPE_KEY","GIOI_THIEU_DOI_GOI_THANH_CONG","MT_TYPE_VALUE");
        mt=mt.replaceAll("\\{SERVICE_KEY}",context.get("SERVICE_KEY_SMS")).replaceAll("\\{PACKAGE_CODE}",context.get("PACKAGE_CODE_SMS")).replaceAll("\\{PACKAGE_PRICE}",context.get("PACKAGE_PRICE"));
        mt=mt.replaceAll("\\{SERVICE_NUMBER}",context.get("SERVICE_NUMBER")).replaceAll("\\{MSG_ID}",String.valueOf(msgId));
        mt=mt.replaceAll("\\{PACKAGE_IN_USE}",info[3]).replaceAll("\\{PACKAGE_IN_USE_CAPACITY}",info[7]);
        mt=mt.replaceAll("\\{PACKAGE_IN_USE_PRICE}",info[3]).replaceAll("\\{PACKAGE_IN_USE_CAPACITY}",info[4]);
        mt=mt.replaceAll("\\{PACKAGE_PRICE}",info[2]).replaceAll("\\{PACKAGE_CYCLE}",info[6]);
        if(context.get("CAPACITY").equals("")){
        mt=mt.replaceAll("\\{PACKAGE_CYCLE}",context.get("PACKAGE_CYCLE")+"ngay");
        }
        else{
        mt=mt.replaceAll("\\{PACKAGE_CYCLE}",context.get("CAPACITY"));
        }
        ret=sendSms(String.valueOf($msisdn),mt);
        System.out.println("ret:"+ret);
        if(isSendSms){
        mt=getValueFromKeyMultiRecords($soap_8_extract1,"record","MT_TYPE_KEY","THONG_BAO_DIEM_BAN_GT_TC","MT_TYPE_VALUE");
        mt=mt.replaceAll("\\{msisdn}",String.valueOf($msisdn));
        ret=sendSms("$sharingkey",mt);
        }
        }
        else{
        mt=getValueFromKeyMultiRecords($soap_8_extract1,"record","MT_TYPE_KEY","MT_GIOI_THIEU_THANH_CONG","MT_TYPE_VALUE");
        System.out.println(mt);
        System.out.println("msg_id:"+msgId);
        System.out.println("msg_id:"+String.valueOf(msgId));

        mt=mt.replaceAll("\\{SERVICE_KEY}",context.get("SERVICE_KEY_SMS")).replaceAll("\\{PACKAGE_CODE}",context.get("PACKAGE_CODE_SMS")).replaceAll("\\{PACKAGE_PRICE}",context.get("PACKAGE_PRICE"));
        mt=mt.replaceAll("\\{SERVICE_NUMBER}",context.get("SERVICE_NUMBER")).replaceAll("\\{MSG_ID}",(String)msgId).replaceAll("\\{SERVICE_NAME}",context.get("SERVICE_KEY_SMS"));
        if(context.get("CAPACITY").equals("")){
        mt=mt.replaceAll("\\{CYCLE_DUNGLUONG}",context.get("PACKAGE_CYCLE")+"ngay");
        }
        else{
        mt=mt.replaceAll("\\{CYCLE_DUNGLUONG}",context.get("CAPACITY"));
        }
        ret=sendSms(String.valueOf($msisdn),mt);
        System.out.println("ret:"+ret);
        if(isSendSms){
        mt=getValueFromKeyMultiRecords($soap_8_extract1,"record","MT_TYPE_KEY","THONG_BAO_DIEM_BAN_GT_TC","MT_TYPE_VALUE");
        mt=mt.replaceAll("\\{msisdn}",String.valueOf($msisdn));
        ret=sendSms("$sharingkey",mt);
        }
        }
        }
        //dich vu co goi cuoc dong
        else{
        if(context.get("SERVICE_KEY").equals("NHAC_CHO_FUNRING")||context.get("SERVICE_KEY").equals("TANG_NHAC_CHO")||context.get("SERVICE_KEY").equals("ZING_TANG_NHACCHO")){
        String request="""
			<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:vms="http://vms.neo">
			   <soapenv:Header/>
			   <soapenv:Body>
			      <vms:sendCommand>
			         <!--Optional:-->
			         <vms:cmd>FUN.GETTONES -toneid=$packagecode</vms:cmd>
			         <!--Optional:-->
			         <vms:channel>MSOCIAL_$channel</vms:channel>
			         <!--Optional:-->
			         <vms:vasgatehost>$VASGATE_SERVER_IP</vms:vasgatehost>
			         <!--Optional:-->
			         <vms:vasgateport>$VASGATE_SERVER_PORT</vms:vasgateport>
			      </vms:sendCommand>
			   </soapenv:Body>
			</soapenv:Envelope>
			""";
        System.out.println(request);
        String infosFunring="";
        try{
        infosFunring=new neo.service.Activation().parseXMLtext(new neo.service.Activation().soapCall(context.get("dataflow_param:utilmodule"),request),"//*[local-name() = 'return']");
        System.out.println("---infosFunring:"+infosFunring+"-----");
        //---infosFunring:6327292|Gat di nuoc mat|5000-----
        context.put("CHECK_BAI_HAT_FUNRING_REQUEST",request);
        context.put("CHECK_BAI_HAT_FUNRING_RESPONSE",infosFunring);
        }catch(Exception ex){
        System.out.println(ex.getMessage());
        }

        if(infosFunring.equals("")||infosFunring.split("\\|").length!=3){
        mt=getValueFromKeyMultiRecords($soap_8_extract1,"record","MT_TYPE_KEY","GIOI_THIEU_THANH_CONG_NHAC_CHO_MASO","MT_TYPE_VALUE");
        mt=mt.replaceAll("\\{SERVICE_KEY}",context.get("SERVICE_KEY_SMS")).replaceAll("\\{PACKAGE_CODE}","$packagecode").replaceAll("\\{PACKAGE_PRICE}",context.get("PACKAGE_PRICE"));
        mt=mt.replaceAll("\\{SERVICE_NUMBER}",context.get("SERVICE_NUMBER")).replaceAll("\\{MSG_ID}",String.valueOf(msgId));
        if(context.get("CAPACITY").equals("")){
        mt=mt.replaceAll("\\{CYCLE_DUNGLUONG}",context.get("PACKAGE_CYCLE")+"ngay");
        }
        else{
        mt=mt.replaceAll("\\{CYCLE_DUNGLUONG}",context.get("CAPACITY"));
        }
        ret=sendSms(String.valueOf($msisdn),mt);
        System.out.println("ret:"+ret);
        if(isSendSms){
        mt=getValueFromKeyMultiRecords($soap_8_extract1,"record","MT_TYPE_KEY","THONG_BAO_DIEM_BAN_GT_TC","MT_TYPE_VALUE");
        mt=mt.replaceAll("\\{msisdn}",String.valueOf($msisdn));
        ret=sendSms("$sharingkey",mt);
        }

        }
        else{

        try{
        String[]infos=infosFunring.split("\\|");
        System.out.println("INFOS:"+infos.length);
        mt=getValueFromKeyMultiRecords($soap_8_extract1,"record","MT_TYPE_KEY","GIOI_THIEU_THANH_CONG_NHAC_CHO_BAIHAT","MT_TYPE_VALUE");
        mt=mt.replaceAll("\\{SERVICE_KEY}",context.get("SERVICE_KEY_SMS")).replaceAll("\\{PACKAGE_CODE}","$packagecode").replaceAll("\\{PACKAGE_PRICE}",infos[2]);
        mt=mt.replaceAll("\\{SERVICE_NUMBER}",context.get("SERVICE_NUMBER")).replaceAll("\\{MSG_ID}",String.valueOf(msgId));
        mt=mt.replaceAll("\\{PACKAGE_CYCLE}",context.get("PACKAGE_CYCLE"));
        if(context.get("CAPACITY").equals("")){
        mt=mt.replaceAll("\\{CYCLE_DUNGLUONG}",context.get("PACKAGE_CYCLE")+"ngay");
        }
        else{
        mt=mt.replaceAll("\\{CYCLE_DUNGLUONG}",context.get("CAPACITY"));
        }
        mt=mt.replaceAll("\\{MA_BAI_HAT}",infos[1]);
        ret=sendSms(String.valueOf($msisdn),mt);
        System.out.println("ret:"+ret);
        if(isSendSms){
        mt=getValueFromKeyMultiRecords($soap_8_extract1,"record","MT_TYPE_KEY","THONG_BAO_DIEM_BAN_GT_TC","MT_TYPE_VALUE");
        mt=mt.replaceAll("\\{msisdn}",String.valueOf($msisdn));
        ret=sendSms(String.valueOf("$sharingkey"),mt);
        }
        }catch(Exception ex){
        System.out.println(ex.getMessage());
        }

        }
        }
        else{
        System.out.println("not impliment");
        }

        }
        context.put("ErrorDescAPI",mt);
        logSql(msisdn,(String)transactionId,"SEND_TRANSACTION_GIOITHIEU","8","SEND_TRANSACTION_GIOITHIEU","charging="+charging+",chargingStatus="+chargingStatus,"stepResult="+stepResult,context.get("ErrorCodeAPI")+"|"+context.get("ErrorDescAPI"),startTime,new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()),"sharingkey=$sharingkey,serviceid=$serviceid,packagecode=$packagecode,channel=$channel",(String)$script_shop_id);
        stepResult=true;
        return true;
        }catch(Exception ex){
        ex.printStackTrace();
        System.out.println(ex.getMessage());
        context.put("ErrorCodeAPI","-1");context.put("ErrorDescAPI",ex.getMessage());
        logSql(msisdn,(String)transactionId,"SEND_TRANSACTION_GIOITHIEU","8","SEND_TRANSACTION_GIOITHIEU","charging="+charging+",chargingStatus="+chargingStatus,"stepResult="+stepResult,context.get("ErrorCodeAPI")+"|"+context.get("ErrorDescAPI"),startTime,new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()),"sharingkey=$sharingkey,serviceid=$serviceid,packagecode=$packagecode,channel=$channel",(String)$script_shop_id);
        return false;
        }
        finally{
	/*def logSql = {String msisdn, String transactionId, String stepName, int stepID,String stepKey,
	String stepInput,String stepOutput,String stepAction,String startTime, String endTime,String inputParameter ->*/
        //logSql(msisdn,(String)transactionId,"SEND_TRANSACTION_GIOITHIEU","8","SEND_TRANSACTION_GIOITHIEU","charging="+charging+",chargingStatus="+chargingStatus,"stepResult="+stepResult,context.get("ErrorCodeAPI")+"|"+context.get("ErrorDescAPI"),startTime,new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()),"sharingkey=$sharingkey,serviceid=$serviceid,packagecode=$packagecode,channel=$channel",(String)$script_shop_id);

        }

        }
