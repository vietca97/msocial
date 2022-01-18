package com.neo.msocial.rest;


import com.neo.msocial.dto.Soap37;
import com.neo.msocial.dto.Soap7;
import com.neo.msocial.utils.GenericsRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ProCessSubCriBerConfirmController")
public class ProcessSubCriBerConfirmController {
//
//    @Autowired
//    GenericsRequest<Soap7> requestSoap7;

    private final GenericsRequest<Soap7> request7;

    public ProcessSubCriBerConfirmController(GenericsRequest<Soap7> request7) {
        this.request7 = request7;

    }


    @GetMapping("/step1")
    public List<Soap7> getMT_CONFIGURATION(@RequestParam Map<String,String> params){
        return request7.getData(params);
    }
}
