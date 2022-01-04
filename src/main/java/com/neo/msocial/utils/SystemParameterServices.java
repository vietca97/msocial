package com.neo.msocial.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.neo.msocial.constant.Parameter;
import com.neo.msocial.constant.StepUrl;
import com.neo.msocial.dto.*;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Component
public class SystemParameterServices {


    public List<SystemParameterDTO_Step2> getDataStep2() {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(StepUrl.STEP_2_URL, String.class);
        String result = response.substring(response.indexOf("["), response.indexOf("]") + 1);
        Type typeOfObjectsList = new TypeToken<ArrayList<Step2DTO>>() {
        }.getType();
        List<Step2DTO> objectsList = new Gson().fromJson(result, typeOfObjectsList);
        List<SystemParameterDTO_Step2> listResul = getDataConvertSingle(objectsList);
        System.out.println(listResul.toString());
        return listResul;
    }

    String getUrl(String... parameter) {
        String url = parameter[0];
        for (int i = 1; i < parameter.length; i++) {

        }
        return "";
    }

    public List<SystemParameterDTO_Step2> getDataConvertSingle(List<Step2DTO> lst) {
        List<SystemParameterDTO_Step2> listResult = new ArrayList<>();
        for (Step2DTO obj : lst) {
            SystemParameterDTO_Step2 dto = new SystemParameterDTO_Step2();
            switch (obj.getSYSTEM_PARAMETER().split(":")[0]) {
                case Parameter.JOB_EXP_DATA_DIR_EXP_LOCAL:
                    dto.setJOB_EXP_DATA_DIR_EXP_LOCAL(obj.getSYSTEM_PARAMETER().split(":")[0]);
                    listResult.add(dto);
                    break;
                case Parameter.JOB_EXP_DATA_DIR_FTP:
                    dto.setJOB_EXP_DATA_DIR_FTP(obj.getSYSTEM_PARAMETER().split(":")[0]);
                    listResult.add(dto);
                    break;
                case Parameter.LIST_EMAIL_REPORT_DUYTRI:
                    dto.setLIST_EMAIL_REPORT_DUYTRI(obj.getSYSTEM_PARAMETER().split(":")[0]);
                    listResult.add(dto);
                    break;
                case Parameter.LIST_EMAIL_REPORT_JOB:
                    dto.setLIST_EMAIL_REPORT_JOB(obj.getSYSTEM_PARAMETER().split(":")[0]);
                    listResult.add(dto);
                    break;
                case Parameter.LIST_EMAIL_REPORT_PHATSINH:
                    dto.setLIST_EMAIL_REPORT_PHATSINH(obj.getSYSTEM_PARAMETER().split(":")[0]);
                    listResult.add(dto);
                    break;
                case Parameter.LOCK_TIME:
                    dto.setLOCK_TIME(obj.getSYSTEM_PARAMETER().split(":")[0]);
                    listResult.add(dto);
                    break;
                case Parameter.LOGIN_MAX:
                    dto.setLOGIN_MAX(obj.getSYSTEM_PARAMETER().split(":")[0]);
                    listResult.add(dto);
                    break;
                case Parameter.LOGSYSTEM:
                    dto.setLOGSYSTEM(obj.getSYSTEM_PARAMETER().split(":")[0]);
                    listResult.add(dto);
                    break;
                case Parameter.ON_OFF_LOCK:
                    dto.setON_OFF_LOCK(obj.getSYSTEM_PARAMETER().split(":")[0]);
                    listResult.add(dto);
                    break;
                case Parameter.SERVER_FROM:
                    dto.setSERVER_FROM(obj.getSYSTEM_PARAMETER().split(":")[0]);
                    listResult.add(dto);
                    break;
                case Parameter.SERVER_HOST:
                    dto.setSERVER_HOST(obj.getSYSTEM_PARAMETER().split(":")[0]);
                    listResult.add(dto);
                    break;
                case Parameter.SERVER_PASS:
                    dto.setSERVER_PASS(obj.getSYSTEM_PARAMETER().split(":")[0]);
                    listResult.add(dto);
                    break;
                case Parameter.SERVER_PORT:
                    dto.setSERVER_PORT(obj.getSYSTEM_PARAMETER().split(":")[0]);
                    listResult.add(dto);
                    break;
                case Parameter.SERVER_USER:
                    dto.setSERVER_USER(obj.getSYSTEM_PARAMETER().split(":")[0]);
                    listResult.add(dto);
                    break;
            }
        }
        return listResult;
    }

    public List<Step3DTO> getDataStep3() {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(StepUrl.STEP_3_URL, String.class);
        String result = response.substring(response.indexOf("["),response.indexOf("]") + 1);
        Type typeOfObjectsList = new TypeToken<ArrayList<Step3DTO>>() {}.getType();
        List<Step3DTO> objectsList = new Gson().fromJson(result, typeOfObjectsList);
        return objectsList;
    }

    public List<Step8DTO>  getDataStep8() {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(StepUrl.STEP_8_URL, String.class);
        String result = response.substring(response.indexOf("["),response.indexOf("]") + 1);
        Type typeOfObjectsList = new TypeToken<ArrayList<Step8DTO>>() {}.getType();
        List<Step8DTO> objectsList = new Gson().fromJson(result, typeOfObjectsList);
        return objectsList;
    }

    public List<Step9DTO> getDataStep9() {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(StepUrl.STEP_9_URL, String.class);
        String result = response.substring(response.indexOf("["),response.indexOf("]")+1);
        Type typeOfObjectsList = new TypeToken<ArrayList<Step9DTO>>() {}.getType();
        List<Step9DTO> objectsList = new Gson().fromJson(result, typeOfObjectsList);
        return objectsList;
    }

    public static List<Step10DTO> getDataStep10() {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(StepUrl.STEP_10_URL, String.class);
        String result = response.substring(response.indexOf("["), response.indexOf("]") + 1);
        Type typeOfObjectsList = new TypeToken<List<Step10DTO>>() {}.getType();
        List<Step10DTO> objectsList = new Gson().fromJson(result, typeOfObjectsList);
        System.out.println(objectsList);
        return objectsList;
    }

    public static List<Step11DTO> getDataStep11() {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(StepUrl.STEP_11_URL, String.class);
        String result = response.substring(response.indexOf("["), response.indexOf("]") + 1);
        String resultReplace = result.replace("\\","");
        Type typeOfObjectsList = new TypeToken<List<Step11DTO>>() {}.getType();
        List<Step11DTO> objectsList = new Gson().fromJson(resultReplace, typeOfObjectsList);
        return objectsList;
    }

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
