package com.neo.msocial.rest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.neo.msocial.constant.StepUrl;
import com.neo.msocial.dto.StepDTO13;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.List;

@RestController
public class Step13 {

    @GetMapping("Step13")
    public List<StepDTO13> getDataStep13(){
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(StepUrl.STEP_13_URL, String.class);
        String result = response.substring(response.indexOf("["),response.indexOf("]")+1);
        String resultReplace= result.replace("\\","");;
        Type typeOfObjectsList = new TypeToken<StepDTO13>(){}.getType();
        List<StepDTO13> objectsList = new Gson().fromJson(resultReplace, typeOfObjectsList);
        return objectsList;

    }
}
