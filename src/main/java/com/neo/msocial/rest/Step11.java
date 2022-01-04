package com.neo.msocial.rest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.neo.msocial.constant.StepUrl;
import com.neo.msocial.dto.Step10DTO;
import com.neo.msocial.dto.Step11DTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.List;

public class Step11 {
    @GetMapping("/step11")
    public static List<Step11DTO> getDataStep11() {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(StepUrl.STEP_11_URL, String.class);
        String result = response.substring(response.indexOf("["), response.indexOf("]") + 1);
        String resultReplace = result.replace("\\","");
        Type typeOfObjectsList = new TypeToken<List<Step11DTO>>() {}.getType();
        List<Step11DTO> objectsList = new Gson().fromJson(resultReplace, typeOfObjectsList);
        return objectsList;
    }
}
