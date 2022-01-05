package com.neo.msocial.dto;

public class Step2DTO {

    private String SYSTEM_PARAMETER;

    public String getSYSTEM_PARAMETER() {
        return SYSTEM_PARAMETER;
    }

    public void setSYSTEM_PARAMETER(String SYSTEM_PARAMETER) {
        this.SYSTEM_PARAMETER = SYSTEM_PARAMETER;
    }

    @Override
    public String toString() {
        return "Step2DTO{" +
                "SYSTEM_PARAMETER='" + SYSTEM_PARAMETER + '\'' +
                '}';
    }
}
