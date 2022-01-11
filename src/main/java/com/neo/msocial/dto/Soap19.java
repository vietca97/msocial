package com.neo.msocial.dto;

import lombok.Data;


public class Soap19 {
    private String REFUSEDPERSERVICETOTAL;
    public static final String refusedperservicetotal = "REFUSEDPERSERVICETOTAL";

    public Soap19(String REFUSEDPERSERVICETOTAL) {
        this.REFUSEDPERSERVICETOTAL = REFUSEDPERSERVICETOTAL;
    }

    public String getREFUSEDPERSERVICETOTAL() {
        return REFUSEDPERSERVICETOTAL;
    }

    public void setREFUSEDPERSERVICETOTAL(String REFUSEDPERSERVICETOTAL) {
        this.REFUSEDPERSERVICETOTAL = REFUSEDPERSERVICETOTAL;
    }
}
