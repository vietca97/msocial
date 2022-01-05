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
    public List<Step2DTO> step2() {
        //dataflow_param:sqlmodule
        return systemParameterServices.getDataStep2();
    }

    @GetMapping("/step3")
    public List<Step3DTO> step3(
    ) {
        return systemParameterServices.getDataStep3();
    }

    @GetMapping("/step8")
    public List<Step8DTO> step8(
    ) {
        List<Step8DTO> result = systemParameterServices.getDataStep8();
        return result;
    }

    @GetMapping("/step9")
    public List<Step9DTO> step9(
    ) {
        return systemParameterServices.getDataStep9();
    }

    @GetMapping("/step10")
    public List<Step10DTO> step10(
    ) {
        return systemParameterServices.getDataStep10();
    }

    @GetMapping("/step11")
    public List<Step11DTO> step11(
    ) {
        return systemParameterServices.getDataStep11();
    }

    @GetMapping("/step13")
    public List<Step13DTO> step13(
    ) {
        return systemParameterServices.getDataStep13();
    }

    @PostMapping("/step18")
    public void step18(
            @RequestBody List<SystemParameterDTO_Step2> $soap_35_extract1,
            @RequestBody List<Step10DTO> $soap_9_extract1,
            @RequestBody List<Step11DTO> $soap_12_extract1,
            @RequestBody List<Step13DTO> $soap_28_extract1
    ) {

        try{
            for(SystemParameterDTO_Step2 record : $soap_35_extract1){
                context.put("",record.getJOB_EXP_DATA_DIR_EXP_LOCAL());
            }

            for(Step10DTO record : $soap_9_extract1){
                context.put("",record.getHAVE_CHANGE_PACKAGE());
            }

            for(Step11DTO record : $soap_12_extract1){
                context.put("",record.getPARTNER_TYPE());
            }

            for(Step13DTO record : $soap_28_extract1){
                context.put("",record.getSCRIPT_TYPE_ID());
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
