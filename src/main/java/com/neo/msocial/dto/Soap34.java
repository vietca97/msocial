package com.neo.msocial.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Soap34 {

    private String MT_TYPE_KEY;
    public static final String mtTypeKey = "MT_TYPE_KEY";

    private String MT_TYPE_VALUE;
    public static final String mtTypeValue = "MT_TYPE_VALUE";

    public Soap34(String MT_TYPE_KEY, String MT_TYPE_VALUE) {
        this.MT_TYPE_KEY = MT_TYPE_KEY;
        this.MT_TYPE_VALUE = MT_TYPE_VALUE;
    }

    @JsonProperty("MT_TYPE_KEY")
    public String getMT_TYPE_KEY() {
        return MT_TYPE_KEY;
    }

    public void setMT_TYPE_KEY(String MT_TYPE_KEY) {
        this.MT_TYPE_KEY = MT_TYPE_KEY;
    }

    @JsonProperty("MT_TYPE_VALUE")
    public String getMT_TYPE_VALUE() {
        return MT_TYPE_VALUE;
    }

    public void setMT_TYPE_VALUE(String MT_TYPE_VALUE) {
        this.MT_TYPE_VALUE = MT_TYPE_VALUE;
    }
}
