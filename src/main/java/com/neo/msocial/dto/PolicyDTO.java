package com.neo.msocial.dto;

public class PolicyDTO {

    private String POLICY_TYPE_NAME;
    public static final String policyTypeName = "POLICY_TYPE_NAME";

    private String PRICE;
    public static final String price = "PRICE";

    public String getPRICE() {
        return PRICE;
    }

    public void setPRICE(String PRICE) {
        this.PRICE = PRICE;
    }

    public String getPOLICY_TYPE_NAME() {
        return POLICY_TYPE_NAME;
    }

    public void setPOLICY_TYPE_NAME(String POLICY_TYPE_NAME) {
        this.POLICY_TYPE_NAME = POLICY_TYPE_NAME;
    }
}
