package com.neo.msocial.rest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.neo.msocial.constant.StepUrl;
import com.neo.msocial.dto.Step8DTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@RestController
public class Step8 {

    @GetMapping("/step8")
    public List<Step8DTO>  getDataStep8() {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(StepUrl.STEP_8_URL, String.class);
        String result = response.substring(response.indexOf("["),response.indexOf("]") + 1);
        Type typeOfObjectsList = new TypeToken<ArrayList<Step8DTO>>() {}.getType();
        List<Step8DTO> objectsList = new Gson().fromJson(result, typeOfObjectsList);
        return objectsList;
    }
}
