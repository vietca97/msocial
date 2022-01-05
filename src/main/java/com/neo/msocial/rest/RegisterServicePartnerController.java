package com.neo.msocial.rest;

import com.neo.msocial.dto.*;
import com.neo.msocial.groovy.CheckAccountPartner;
import com.neo.msocial.groovy.DefUtils;
import com.neo.msocial.utils.RedisUtils;
import com.neo.msocial.utils.SystemParameterServices;
import com.neo.msocial.utils.UtilServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class RegisterServicePartnerController {
    @Autowired
    SystemParameterServices systemParameterServices;

    @Value("${vasgate.soapapi}")
    private String test;

    private final UtilServices utilServices;
    private final RedisUtils context;

    private final CheckAccountPartner checkAccountPartner;

    private final DefUtils defUtils;

    public RegisterServicePartnerController(UtilServices utilServices, RedisUtils context, CheckAccountPartner checkAccountPartner, DefUtils defUtils) {

        this.context = context;
        this.utilServices = utilServices;
        this.checkAccountPartner = checkAccountPartner;
        this.defUtils = defUtils;
    }

    @PostMapping("/step1")
    public String step1(@RequestBody RegisterServicePartnerDTO dto) {
        return utilServices.callSoapVasGate(dto);
    }

    @GetMapping("/step2")
    public List<Soap35> step2() {

        //dataflow_param:sqlmodule
        return systemParameterServices.getDataStep2();
    }

    @GetMapping("/step3")
    public List<Soap37> step3(
    ) {
        return systemParameterServices.getDataStep3();
    }

    @GetMapping("/step4")
    public boolean step4(@RequestBody List<Soap37> soap37) {
        boolean ret = false;
        try {
            //String content = "$content";
            String content = "$body";
        } catch (Exception ex) {
            context.put("content", "");
        }
        try {
            if (Integer.parseInt(soap37.get(0).getCHECK_ACCOUNT_API()) < 1) ret = false;
            else ret = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            ret = false;
        } finally {
            //test thi dong cai nay lai
//            if(!ret){
//                context.put("ErrorCodeAPI","-2");
//                context.put("ErrorDescAPI","Username/Password khong dung");
//            }
            return ret;
        }
    }

    @GetMapping("/step8")
    public List<Soap15> step8(
    ) {
        return systemParameterServices.getDataStep8();
    }

    @GetMapping("/step9")
    public List<Soap8> step9(
    ) {
        return systemParameterServices.getDataStep9();
    }

    @GetMapping("/step10")
    public List<Soap9> step10(
    ) {
        return systemParameterServices.getDataStep10();
    }

    @GetMapping("/step11")
    public List<Soap12> step11(
    ) {
        return systemParameterServices.getDataStep11();
    }

    @GetMapping("/step12")
    public List<Soap14> step12(
    ) {
        return systemParameterServices.getDataStep12();
    }

    @GetMapping("/step13")
    public List<Soap28> step13(
    ) {
        return systemParameterServices.getDataStep13();
    }

    @GetMapping("/step14")
    public List<Soap16> step14(
    ) {
        return systemParameterServices.getDataStep14();
    }

    @GetMapping("/step15")
    public List<Soap17> step15(
    ) {
        return systemParameterServices.getDataStep15();
    }

    @GetMapping("/step16")
    public List<Soap19> step16(
    ) {
        return systemParameterServices.getDataStep16();
    }

    @GetMapping("/step18")
    public void step18(
            @RequestBody List<Soap35> lstSoap35,
            @RequestBody List<Soap9> lstSoap9,
            @RequestBody List<Soap12> lstSoap12,
            @RequestBody List<Soap28> lstSoap28
    ) {
            for(Soap35 record : lstSoap35){
                context.put(record.text().split(":")[0],record.text().split(":")[1]);
            }
            rootNode = new XmlSlurper().parseText($soap_9_extract1);
            for(def record : rootNode.record.children()){
                context.put(record.name(),record.text());
            }
            rootNode = new XmlSlurper().parseText($soap_12_extract1);
            for(def record : rootNode.record.children()){
                context.put(record.name(),record.text());
            }
            rootNode = new XmlSlurper().parseText($soap_28_extract1);
            for(def record : rootNode.record.children()){
                context.put(record.name(),record.text());
            }
    }

    @GetMapping("/step19")
    public void step19(@RequestBody List<Soap34> lst) {
        for (Soap34 record : lst) {
            context.put(Soap34.mtTypeKey, record.getMT_TYPE_KEY());
            context.put(Soap34.mtTypeValue, record.getMT_TYPE_VALUE());
        }
    }


}
