package com.neo.msocial.rest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.neo.msocial.constant.Parameter;
import com.neo.msocial.constant.StepUrl;
import com.neo.msocial.dto.Step2DTO;
import com.neo.msocial.dto.SystemParameterDTO_Step2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@RestController
public class Step2 {

    @GetMapping("/step2")
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


//    public List<SystemParameterDTO_Step2> getDataConvertMulti(List<DataMsocial> lst) {
//        List<SystemParameterDTO_Step2> listResult = new ArrayList<>();
//        for (DataMsocial obj : lst) {
//            SystemParameterDTO_Step2 dto = new SystemParameterDTO_Step2();
//            switch (obj.getSYSTEM_PARAMETER().split(":")[0]) {
//                case Parameter.JOB_EXP_DATA_DIR_EXP_LOCAL:
//                    dto.setJOB_EXP_DATA_DIR_EXP_LOCAL(obj.getSYSTEM_PARAMETER().split(":")[0]);
//                    listResult.add(dto);
//                    break;
//            }
//        }
//        return listResult;
//    }

}
