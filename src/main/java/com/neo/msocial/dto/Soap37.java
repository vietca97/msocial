package com.neo.msocial.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Soap37 {

    private String CHECK_ACCOUNT_API;
    public  static final String checkAccountApi="CHECK_ACCOUNT_API";


    @JsonProperty("CHECK_ACCOUNT_API")
    public String getCHECK_ACCOUNT_API() {
        return CHECK_ACCOUNT_API;
    }

    public void setCHECK_ACCOUNT_API(String CHECK_ACCOUNT_API) {
        this.CHECK_ACCOUNT_API = CHECK_ACCOUNT_API;
    }
}
