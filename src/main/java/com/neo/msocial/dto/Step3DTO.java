package com.neo.msocial.dto;

public class Step3DTO {
    public String CHECK_ACCOUNT_API;

    public Step3DTO() {
    }

    public Step3DTO(String CHECK_ACCOUNT_API) {
        this.CHECK_ACCOUNT_API = CHECK_ACCOUNT_API;
    }

    public String getCHECK_ACCOUNT_API() {
        return CHECK_ACCOUNT_API;
    }

    public void setCHECK_ACCOUNT_API(String CHECK_ACCOUNT_API) {
        this.CHECK_ACCOUNT_API = CHECK_ACCOUNT_API;
    }

    @Override
    public String toString() {
        return "Step3DTO{" +
                "CHECK_ACCOUNT_API='" + CHECK_ACCOUNT_API + '\'' +
                '}';
    }
}
