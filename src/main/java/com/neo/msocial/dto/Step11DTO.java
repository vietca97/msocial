package com.neo.msocial.dto;

public class Step11DTO {
    public String PARTNER_TYPE;
    public String SEND_SMS;
    public String PARTNER_NAME;
    public String PARTNER_KEY;

    public Step11DTO() {
    }

    public Step11DTO(String PARTNER_TYPE, String SEND_SMS, String PARTNER_NAME, String PARTNER_KEY) {
        this.PARTNER_TYPE = PARTNER_TYPE;
        this.SEND_SMS = SEND_SMS;
        this.PARTNER_NAME = PARTNER_NAME;
        this.PARTNER_KEY = PARTNER_KEY;
    }

    public String getPARTNER_TYPE() {
        return PARTNER_TYPE;
    }

    public void setPARTNER_TYPE(String PARTNER_TYPE) {
        this.PARTNER_TYPE = PARTNER_TYPE;
    }

    public String getSEND_SMS() {
        return SEND_SMS;
    }

    public void setSEND_SMS(String SEND_SMS) {
        this.SEND_SMS = SEND_SMS;
    }

    public String getPARTNER_NAME() {
        return PARTNER_NAME;
    }

    public void setPARTNER_NAME(String PARTNER_NAME) {
        this.PARTNER_NAME = PARTNER_NAME;
    }

    public String getPARTNER_KEY() {
        return PARTNER_KEY;
    }

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
