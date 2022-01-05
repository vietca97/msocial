package com.neo.msocial.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.neo.msocial.constant.Parameter;
import com.neo.msocial.constant.RequestUrl;
import com.neo.msocial.dto.*;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Component
public class SystemParameterServices {


    public List<Soap35> getDataStep2() {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(RequestUrl.StepUrl.STEP_2_URL, String.class);
        String result = response.substring(response.indexOf("["), response.indexOf("]") + 1);
        Type typeOfObjectsList = new TypeToken<ArrayList<SystemParameter>>() {
        }.getType();
        List<SystemParameter> objectsList = new Gson().fromJson(result, typeOfObjectsList);
        List<Soap35> listResul = getDataConvertSingle(objectsList);
        System.out.println(listResul.toString());
        return listResul;
    }

    String getUrl(String... parameter) {
        String url = parameter[0];
        for (int i = 1; i < parameter.length; i++) {

        }
        return "";
    }

    public List<Soap35> getDataConvertSingle(List<SystemParameter> lst) {
        List<Soap35> listResult = new ArrayList<>();
        Soap35 dto = new Soap35();
        for (SystemParameter obj : lst) {
            switch (obj.getSYSTEM_PARAMETER().split(":")[0]) {
                case Parameter.JOB_EXP_DATA_DIR_EXP_LOCAL:
                    dto.setJOB_EXP_DATA_DIR_EXP_LOCAL(obj.getSYSTEM_PARAMETER().split(":")[0]);
                    break;
                case Parameter.JOB_EXP_DATA_DIR_FTP:
                    dto.setJOB_EXP_DATA_DIR_FTP(obj.getSYSTEM_PARAMETER().split(":")[0]);
                    break;
                case Parameter.LIST_EMAIL_REPORT_DUYTRI:
                    dto.setLIST_EMAIL_REPORT_DUYTRI(obj.getSYSTEM_PARAMETER().split(":")[0]);
                    break;
                case Parameter.LIST_EMAIL_REPORT_JOB:
                    dto.setLIST_EMAIL_REPORT_JOB(obj.getSYSTEM_PARAMETER().split(":")[0]);
                    break;
                case Parameter.LIST_EMAIL_REPORT_PHATSINH:
                    dto.setLIST_EMAIL_REPORT_PHATSINH(obj.getSYSTEM_PARAMETER().split(":")[0]);
                    break;
                case Parameter.LOCK_TIME:
                    dto.setLOCK_TIME(obj.getSYSTEM_PARAMETER().split(":")[0]);
                    break;
                case Parameter.LOGIN_MAX:
                    dto.setLOGIN_MAX(obj.getSYSTEM_PARAMETER().split(":")[0]);
                    break;
                case Parameter.LOGSYSTEM:
                    dto.setLOGSYSTEM(obj.getSYSTEM_PARAMETER().split(":")[0]);
                    break;
                case Parameter.ON_OFF_LOCK:
                    dto.setON_OFF_LOCK(obj.getSYSTEM_PARAMETER().split(":")[0]);
                    break;
                case Parameter.SERVER_FROM:
                    dto.setSERVER_FROM(obj.getSYSTEM_PARAMETER().split(":")[0]);
                    break;
                case Parameter.SERVER_HOST:
                    dto.setSERVER_HOST(obj.getSYSTEM_PARAMETER().split(":")[0]);
                    break;
                case Parameter.SERVER_PASS:
                    dto.setSERVER_PASS(obj.getSYSTEM_PARAMETER().split(":")[0]);
                    break;
                case Parameter.SERVER_PORT:
                    dto.setSERVER_PORT(obj.getSYSTEM_PARAMETER().split(":")[0]);
                    break;
                case Parameter.SERVER_USER:
                    dto.setSERVER_USER(obj.getSYSTEM_PARAMETER().split(":")[0]);
                    break;
            }
        }
        listResult.add(dto);
        return listResult;
    }

    public List<Soap37> getDataStep3(List<Soap35> lst) {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(RequestUrl.StepUrl.STEP_3_URL + "&P1="+ lst.get(0).getSERVER_USER() + "&P2="+ lst.get(0).getSERVER_PASS(), String.class);
        String result = response.substring(response.indexOf("["),response.indexOf("]") + 1);
        Type typeOfObjectsList = new TypeToken<ArrayList<Soap37>>() {}.getType();
        List<Soap37> objectsList = new Gson().fromJson(result, typeOfObjectsList);
        return objectsList;
    }

    public List<Soap15>  getDataStep8() {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(RequestUrl.StepUrl.STEP_8_URL, String.class);
        String result = response.substring(response.indexOf("["),response.indexOf("]") + 1);
        Type typeOfObjectsList = new TypeToken<ArrayList<Soap15>>() {}.getType();
        List<Soap15> objectsList = new Gson().fromJson(result, typeOfObjectsList);
        return objectsList;
    }

    public List<Soap8> getDataStep9() {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(RequestUrl.StepUrl.STEP_9_URL, String.class);
        String result = response.substring(response.indexOf("["),response.indexOf("]")+1);
        Type typeOfObjectsList = new TypeToken<ArrayList<Soap8>>() {}.getType();
        List<Soap8> objectsList = new Gson().fromJson(result, typeOfObjectsList);
        return objectsList;
    }

    public  List<Soap9> getDataStep10() {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(RequestUrl.StepUrl.STEP_10_URL, String.class);
        String result = response.substring(response.indexOf("["), response.indexOf("]") + 1);
        Type typeOfObjectsList = new TypeToken<List<Soap9>>() {}.getType();
        List<Soap9> objectsList = new Gson().fromJson(result, typeOfObjectsList);
        System.out.println(objectsList);
        return objectsList;
    }

    public  List<Soap12> getDataStep11() {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(RequestUrl.StepUrl.STEP_11_URL, String.class);
        String result = response.substring(response.indexOf("["), response.indexOf("]") + 1);
        String resultReplace = result.replace("\\","");
        Type typeOfObjectsList = new TypeToken<List<Soap12>>() {}.getType();
        List<Soap12> objectsList = new Gson().fromJson(resultReplace, typeOfObjectsList);
        return objectsList;
    }

    public  List<Soap14> getDataStep12() {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(RequestUrl.StepUrl.STEP_12_URL, String.class);
        String result = response.substring(response.indexOf("["), response.indexOf("]") + 1);
        String resultReplace = result.replace("\\","");
        Type typeOfObjectsList = new TypeToken<List<Soap14>>() {}.getType();
        List<Soap14> objectsList = new Gson().fromJson(resultReplace, typeOfObjectsList);
        return objectsList;
    }

    public List<Soap28> getDataStep13(){
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(RequestUrl.StepUrl.STEP_13_URL, String.class);
        String result = response.substring(response.indexOf("["),response.indexOf("]")+1);
        String resultReplace= result.replace("\\","");;
        Type typeOfObjectsList = new TypeToken<List<Soap28>>(){}.getType();
        List<Soap28> objectsList = new Gson().fromJson(resultReplace, typeOfObjectsList);
        return objectsList;
    }

    public List<Soap16> getDataStep14(){
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(RequestUrl.StepUrl.STEP_14_URL, String.class);
        String result = response.substring(response.indexOf("["),response.indexOf("]")+1);
        String resultReplace= result.replace("\\","");;
        Type typeOfObjectsList = new TypeToken<List<Soap16>>(){}.getType();
        List<Soap16> objectsList = new Gson().fromJson(resultReplace, typeOfObjectsList);
        return objectsList;
    }

    public List<Soap17> getDataStep15(){
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(RequestUrl.StepUrl.STEP_15_URL, String.class);
        String result = response.substring(response.indexOf("["),response.indexOf("]")+1);
        String resultReplace= result.replace("\\","");;
        Type typeOfObjectsList = new TypeToken<List<Soap17>>(){}.getType();
        List<Soap17> objectsList = new Gson().fromJson(resultReplace, typeOfObjectsList);
        return objectsList;
    }

    public List<Soap19> getDataStep16(){
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(RequestUrl.StepUrl.STEP_16_URL, String.class);
        String result = response.substring(response.indexOf("["),response.indexOf("]")+1);
        String resultReplace= result.replace("\\","");;
        Type typeOfObjectsList = new TypeToken<List<Soap19>>(){}.getType();
        List<Soap19> objectsList = new Gson().fromJson(resultReplace, typeOfObjectsList);
        return objectsList;
    }

    public String getDataCheckLockDb(String sharingKeyId){
        //"return": 0
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(RequestUrl.Check.LOCK_DB + "&P1=" + sharingKeyId, String.class);
        return response.split("\"return\":")[1];
    }

    public List<PolicyDTO> getDataCheckPolicy(String script_shop_id, String msisdn){
        //"return": 0
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(RequestUrl.Check.POLICY + "&P1=" + script_shop_id + "&P2=" + msisdn, String.class);
        String result = response.substring(response.indexOf("["),response.indexOf("]")+1);
        String resultReplace= result.replace("\\","");;
        Type typeOfObjectsList = new TypeToken<List<PolicyDTO>>(){}.getType();
        List<PolicyDTO> objectsList = new Gson().fromJson(resultReplace, typeOfObjectsList);
        return objectsList;
    }

    public List<PolicyDTO> getDataCheckPrice(String script_shop_id, String msisdn, String serviceid){
        //"return": 0
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(RequestUrl.Check.PRICE + "&P1=" + script_shop_id + "&P2=" + msisdn + "&P3=" + serviceid, String.class);
        String result = response.substring(response.indexOf("["),response.indexOf("]")+1);
        String resultReplace= result.replace("\\","");;
        Type typeOfObjectsList = new TypeToken<List<PolicyDTO>>(){}.getType();
        List<PolicyDTO> objectsList = new Gson().fromJson(resultReplace, typeOfObjectsList);
        return objectsList;
    }
}
