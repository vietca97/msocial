package com.neo.msocial.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Soap12 {
    private String PARTNER_TYPE;
    public static final String partnerType = "PARTNER_TYPE";

    private String SEND_SMS;
    public static final String sendSms = "SEND_SMS";

    private String PARTNER_NAME;
    public static final String partnerName = "PARTNER_NAME";

    private String PARTNER_KEY;
    public static final String partnerKey = "PARTNER_KEY";

    public Soap12() {
    }

    public Soap12(String PARTNER_TYPE, String SEND_SMS, String PARTNER_NAME, String PARTNER_KEY) {
        this.PARTNER_TYPE = PARTNER_TYPE;
        this.SEND_SMS = SEND_SMS;
        this.PARTNER_NAME = PARTNER_NAME;
        this.PARTNER_KEY = PARTNER_KEY;
    }

    @JsonProperty("PARTNER_TYPE")
    public String getPARTNER_TYPE() {
        return PARTNER_TYPE;
    }

    @JsonProperty("PARTNER_TYPE")
    public void setPARTNER_TYPE(String PARTNER_TYPE) {
        this.PARTNER_TYPE = PARTNER_TYPE;
    }

    @JsonProperty("SEND_SMS")
    public String getSEND_SMS() {
        return SEND_SMS;
    }

    @JsonProperty("SEND_SMS")
    public void setSEND_SMS(String SEND_SMS) {
        this.SEND_SMS = SEND_SMS;
    }

    @JsonProperty("PARTNER_NAME")
    public String getPARTNER_NAME() {
        return PARTNER_NAME;
    }

    @JsonProperty("PARTNER_NAME")
    public void setPARTNER_NAME(String PARTNER_NAME) {
        this.PARTNER_NAME = PARTNER_NAME;
    }

    @JsonProperty("PARTNER_KEY")
    public String getPARTNER_KEY() {
        return PARTNER_KEY;
    }

    @JsonProperty("PARTNER_KEY")
    public void setPARTNER_KEY(String PARTNER_KEY) {
        this.PARTNER_KEY = PARTNER_KEY;
    }

    @Override
    public String toString() {
        return "Step11DTO{" +
                "PARTNER_TYPE='" + PARTNER_TYPE + '\'' +
                ", SEND_SMS='" + SEND_SMS + '\'' +
                ", PARTNER_NAME='" + PARTNER_NAME + '\'' +
                ", PARTNER_KEY='" + PARTNER_KEY + '\'' +
                '}';
    }
}
