package com.neo.msocial.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.neo.msocial.constant.RequestUrl;
import com.neo.msocial.dto.Soap35;
import com.neo.msocial.dto.SystemParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class GenericsRequest<T> {

    private final Gson gson;
    private static final String BASE_URL = "http://10.252.12.237:4122/services/SqlServices/";

    final
    RestTemplate restTemplate;

    public GenericsRequest(Gson gson, RestTemplate restTemplate) {
        this.gson = gson;
        this.restTemplate = restTemplate;
    }

    //http://10.252.12.237:4122/services/SqlServices/query?Service=system_parameter&Provider=default&ParamSize=0
    public List<T> getData(Map<String, String> params) {
        try {
            String response = "";
            String result = "";
            response = restTemplate.getForObject(getRequestUrl(params), String.class);
            result = response.substring(response.indexOf("["), response.indexOf("]") + 1);


            String resultReplace = result.replace("\\", "");
            Type typeOfObjectsList = new TypeToken<ArrayList<T>>() {
            }.getType();
            String s = "\"{MA_BAI_HAT}\"";
            String formartResultReplace = resultReplace
                    .replaceAll("\"\\{SONG_NAME}\"", "{SONG_NAME}")
                    .replaceAll("\"\\{MA_BAI_HAT}\"", "{MA_BAI_HAT}")
                    ;

            List<T> objectsList = gson.fromJson(formartResultReplace, typeOfObjectsList);
            return objectsList;
        } catch (StringIndexOutOfBoundsException | HttpServerErrorException e) {
            return null;
        }
    }

    //http://10.252.12.237:4122/services/SqlServices/query?Service=system_parameter&Provider=default&ParamSize=0
    public List<T> getDataConfigUraTion(Map<String, String> params) {
        try {
            String response = "";
            String result = "";
            response = restTemplate.getForObject(getRequestUrl(params), String.class);
            result = response.substring(response.indexOf("["), response.indexOf("]") + 1);


            String resultReplace = result.replace("\\", "");
            Type typeOfObjectsList = new TypeToken<ArrayList<T>>() {
            }.getType();
            String s = "\"{MA_BAI_HAT}\"";
            String formartResultReplace = resultReplace
                    .replaceAll("\"\\{SONG_NAME}\"", "{SONG_NAME}")
                    .replaceAll("\"\\{MA_BAI_HAT}\"", "{MA_BAI_HAT}")
                    ;

            List<T> objectsList = gson.fromJson(formartResultReplace, typeOfObjectsList);
            return objectsList;
        } catch (StringIndexOutOfBoundsException | HttpServerErrorException e) {
            return null;
        }
    }

    private String getRequestUrl(Map<String, String> params) {
        StringBuilder urlResponse = new StringBuilder();
        urlResponse.append(BASE_URL);
        urlResponse.append(params.containsKey("typeQuery") ? params.get("typeQuery") : null).append("/") // typeQuery
                .append("?Service=").append(params.containsKey("Service") ? params.get("Service") : null) // service
                .append("&Provider=").append(params.containsKey("Provider") ? params.get("Provider") : null) // provider
                .append("&ParamSize=").append(params.containsKey("ParamSize") ? params.get("ParamSize") : null) // paramSize
                .append("&response=").append(params.containsKey("response") ? params.get("response") : null); // Content type
        for (int i = 0; i < params.size(); i++) {
            if (params.containsKey("P" + (i + 1))) {
                urlResponse.append("&P" + (i + 1)).append("=").append(params.get("P" + (i + 1)));
            }
        }


        return urlResponse.toString();
    }

}
