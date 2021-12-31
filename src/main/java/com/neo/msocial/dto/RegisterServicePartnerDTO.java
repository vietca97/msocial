package com.neo.msocial.dto;

public class RegisterServicePartnerDTO {

    private String service;
    private String provider;
    private String paramSize;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getParamSize() {
        return paramSize;
    }

    public void setParamSize(String paramSize) {
        this.paramSize = paramSize;
    }
}
