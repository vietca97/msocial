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
    private final RedisUtils redisUtils;
    private final CheckAccountPartner checkAccountPartner;

    private final DefUtils defUtils;

    public RegisterServicePartnerController(UtilServices utilServices, RedisUtils redisUtils, CheckAccountPartner checkAccountPartner, DefUtils defUtils) {
        this.redisUtils = redisUtils;
        this.utilServices = utilServices;
        this.checkAccountPartner = checkAccountPartner;
        this.defUtils = defUtils;
    }

    @PostMapping("/step1")
    public String step1(@RequestBody RegisterServicePartnerDTO dto) {
        return utilServices.callSoapVasGate(dto);
    }

    @GetMapping("/step2")
    public List<SystemParameterDTO_Step2> step2() {

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
        return systemParameterServices.getDataStep8();
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
    public List<StepDTO13> step13(
    ) {
        return systemParameterServices.getDataStep13();
    }


}
