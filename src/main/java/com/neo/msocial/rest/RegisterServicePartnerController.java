package com.neo.msocial.rest;

import com.neo.msocial.dto.RegisterServicePartnerDTO;
import com.neo.msocial.groovy.DefUtils;
import com.neo.msocial.utils.RedisUtils;
import com.neo.msocial.utils.UtilServices;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegisterServicePartnerController {

    @Value("${vasgate.soapapi}")
    private String test;

    private final UtilServices utilServices;
    private final RedisUtils context;

    private final DefUtils defUtils;
    public RegisterServicePartnerController(UtilServices utilServices, RedisUtils context,DefUtils defUtils) {
        this.context = context;
        this.utilServices = utilServices;
        this.defUtils = defUtils;
    }

    @PostMapping("/step1")
    public String step1(@RequestBody RegisterServicePartnerDTO dto) {
        return utilServices.callSoapVasGate(dto);
    }

    @GetMapping("/step2")
    public String step2(@RequestParam(value = "service", required = true) String service) {
        //dataflow_param:sqlmodule
        return context.get(service);
    }
    @GetMapping("/step3")
    public String step3(
            @RequestParam(value = "service") String service,
            @RequestParam(value = "provider") String provider,
            @RequestParam(value = "paramSize") String paramSize
    ) {
        return defUtils.logSqlTest(service,provider,paramSize);
    }

    @GetMapping("/test")
    public String test() {
        return test;
    }


}
