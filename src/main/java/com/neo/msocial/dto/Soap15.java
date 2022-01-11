package com.neo.msocial.dto;


import com.fasterxml.jackson.annotation.JsonProperty;


public class Soap15 {

    @JsonProperty(value = "SPAM_TEMPLATE_NAME")
    private String SPAM_TEMPLATE_NAME;
    public static final String spamTemplateName = "SPAM_TEMPLATE_NAME";

    //@JsonProperty(value = "SPAM_TEMPLATE_VALUE")
    private String SPAM_TEMPLATE_VALUE;
    public static final String spamTemplateValue = "SPAM_TEMPLATE_VALUE";

    //@JsonProperty(value = "HOUR_IN_LOCK")
    private String HOUR_IN_LOCK;
    public static final String hourInLock = "HOUR_IN_LOCK";

    //@JsonProperty(value = "SPAM_TYPE_NAME")
    private String SPAM_TYPE_NAME;
    public static final String spamTypeName = "SPAM_TYPE_NAME";

    //@JsonProperty(value = "ACTION_TYPE")
    private String ACTION_TYPE;
    public static final String actionType = "ACTION_TYPE";

    public Soap15(String SPAM_TEMPLATE_VALUE) {
        this.SPAM_TEMPLATE_VALUE = SPAM_TEMPLATE_VALUE;
    }

    public Soap15() {
    }

    public String getSPAM_TEMPLATE_NAME() {
        return SPAM_TEMPLATE_NAME;
    }

    public void setSPAM_TEMPLATE_NAME(String SPAM_TEMPLATE_NAME) {
        this.SPAM_TEMPLATE_NAME = SPAM_TEMPLATE_NAME;
    }

    public String getSPAM_TEMPLATE_VALUE() {
        return SPAM_TEMPLATE_VALUE;
    }

    public void setSPAM_TEMPLATE_VALUE(String SPAM_TEMPLATE_VALUE) {
        this.SPAM_TEMPLATE_VALUE = SPAM_TEMPLATE_VALUE;
    }

    public String getHOUR_IN_LOCK() {
        return HOUR_IN_LOCK;
    }

    public void setHOUR_IN_LOCK(String HOUR_IN_LOCK) {
        this.HOUR_IN_LOCK = HOUR_IN_LOCK;
    }

    public String getSPAM_TYPE_NAME() {
        return SPAM_TYPE_NAME;
    }

    public void setSPAM_TYPE_NAME(String SPAM_TYPE_NAME) {
        this.SPAM_TYPE_NAME = SPAM_TYPE_NAME;
    }

    public String getACTION_TYPE() {
        return ACTION_TYPE;
    }

    public void setACTION_TYPE(String ACTION_TYPE) {
        this.ACTION_TYPE = ACTION_TYPE;
    }
}
