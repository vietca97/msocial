package com.neo.msocial.rest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.neo.msocial.constant.StepUrl;
import com.neo.msocial.dto.Step10DTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.List;

@RestController
public class Step10 {
    @GetMapping("/step10")
    public static List<Step10DTO> getDataStep10() {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(StepUrl.STEP_10_URL, String.class);
        String result = response.substring(response.indexOf("["), response.indexOf("]") + 1);
        Type typeOfObjectsList = new TypeToken<List<Step10DTO>>() {}.getType();
        List<Step10DTO> objectsList = new Gson().fromJson(result, typeOfObjectsList);
        System.out.println(objectsList);
        return objectsList;
    }
}
