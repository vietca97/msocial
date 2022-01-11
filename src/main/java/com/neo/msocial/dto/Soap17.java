package com.neo.msocial.dto;

public class Soap17 {
    private String WAITPERSERVICETOTAL;
    public static final String waitperservicetotal = "WAITPERSERVICETOTAL";

    public Soap17(String WAITPERSERVICETOTAL) {
        this.WAITPERSERVICETOTAL = WAITPERSERVICETOTAL;
    }

    public String getWAITPERSERVICETOTAL() {
        return WAITPERSERVICETOTAL;
    }

    public void setWAITPERSERVICETOTAL(String WAITPERSERVICETOTAL) {
        this.WAITPERSERVICETOTAL = WAITPERSERVICETOTAL;
    }
}
