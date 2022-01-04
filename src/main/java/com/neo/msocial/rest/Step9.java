package com.neo.msocial.rest;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.neo.msocial.constant.StepUrl;
import com.neo.msocial.dto.Step9DTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@RestController
public class Step9 {


    @GetMapping("/Step9")
    public List<Step9DTO> getDataStep9() {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(StepUrl.STEP_9_URL, String.class);
        String result = response.substring(response.indexOf("["),response.indexOf("]")+1);

        Type typeOfObjectsList = new TypeToken<ArrayList<Step9DTO>>() {}.getType();
        List<Step9DTO> objectsList = new Gson().fromJson(result, typeOfObjectsList);
        System.out.println("=="+objectsList);
        return objectsList;
    }

}
