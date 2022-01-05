package com.neo.msocial.dto;

import lombok.Builder;
import lombok.Data;

public class Step14DTO {
    private String WAITREGISTERTOTAL;
    public static final String waitregistertotal = "WAITREGISTERTOTAL";

    public String getWAITREGISTERTOTAL() {
        return WAITREGISTERTOTAL;
    }

    public void setWAITREGISTERTOTAL(String WAITREGISTERTOTAL) {
        this.WAITREGISTERTOTAL = WAITREGISTERTOTAL;
    }

    public Step14DTO() {
    }

    public Step14DTO(String WAITREGISTERTOTAL) {
        this.WAITREGISTERTOTAL = WAITREGISTERTOTAL;
    }
}
