package com.neo.msocial.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Soap9 {
    private String HAVE_CHANGE_PACKAGE;
    public static final String haveChangePackage = "HAVE_CHANGE_PACKAGE";

    private String CHANGE_PACKAGE;
    public static final String changePackage = "CHANGE_PACKAGE";

    private String HAVE_CHECK_HUY_WITH_5000;
    public static final String haveCheckHuyWith5000 = "HAVE_CHECK_HUY_WITH_5000";

    private String NEED_CHECK_SERVICE;
    public static final String needCheckService = "NEED_CHECK_SERVICE";

    private String HAVE_CHECK_HUY;
    public static final String haveCheckHuy = "HAVE_CHECK_HUY";

    private String SERVICE_KEY_SMS;
    public static final String serviceKeySms = "SERVICE_KEY_SMS";

    private String SERVICE_ID;
    public static final String serviceId = "SERVICE_ID";

    private String SERVICE_NAME;
    public static final String serviceName = "SERVICE_NAME";

    private String SERVICE_NAME_SMS;
    public static final String serviceNameSms = "SERVICE_NAME_SMS";

    private String SERVICE_KEY;
    public static final String serviceKey = "SERVICE_KEY";

    private String SERVICE_INFO;
    public static final String serviceInfo = "SERVICE_INFO";

    private String HAVE_MAINTAIN;
    public static final String haveMaintain = "HAVE_MAINTAIN";

    private String PACKAGE_DYNAMIC;
    public static final String packageDynamic = "PACKAGE_DYNAMIC";

    private String PACKAGE_CODE;
    public static final String packageCode = "PACKAGE_CODE";

    private String PACKAGE_CODE_API;
    public static final String packageCodeApi = "PACKAGE_CODE_API";

    private String CAPACITY;
    public static final String capacity = "CAPACITY";

    private String PACKAGE_PRICE;
    public static final String packagePrice = "PACKAGE_PRICE";

    private String SPLIT_TIP;
    public static final String splitTip = "SPLIT_TIP";

    private String PACKAGE_CODE_STATUS;
    public static final String packageCodeStatus = "PACKAGE_CODE_STATUS";

    private String PACKAGE_CODE_ID;
    public static final String packageCodeId = "PACKAGE_CODE_ID";

    private String PACKAGE_TYPE;
    public static final String packageType = "PACKAGE_TYPE";

    private String PACKAGE_CODE_SMS;
    public static final String packageCodeSms = "PACKAGE_CODE_SMS";

    private String PACKAGE_CYCLE;
    public static final String packageCycle = "PACKAGE_CYCLE";

    private String LIKE_MI_OR_VAS;
    public static final String likeMiOrVas = "LIKE_MI_OR_VAS";

    public Soap9() {
    }

    public Soap9(String HAVE_CHANGE_PACKAGE, String CHANGE_PACKAGE, String HAVE_CHECK_HUY_WITH_5000, String NEED_CHECK_SERVICE, String HAVE_CHECK_HUY, String SERVICE_KEY_SMS, String SERVICE_ID, String SERVICE_NAME, String SERVICE_NAME_SMS, String SERVICE_KEY, String SERVICE_INFO, String HAVE_MAINTAIN, String PACKAGE_DYNAMIC, String PACKAGE_CODE, String PACKAGE_CODE_API, String CAPACITY, String PACKAGE_PRICE, String SPLIT_TIP, String PACKAGE_CODE_STATUS, String PACKAGE_CODE_ID, String PACKAGE_TYPE, String PACKAGE_CODE_SMS, String PACKAGE_CYCLE, String LIKE_MI_OR_VAS) {
        this.HAVE_CHANGE_PACKAGE = HAVE_CHANGE_PACKAGE;
        this.CHANGE_PACKAGE = CHANGE_PACKAGE;
        this.HAVE_CHECK_HUY_WITH_5000 = HAVE_CHECK_HUY_WITH_5000;
        this.NEED_CHECK_SERVICE = NEED_CHECK_SERVICE;
        this.HAVE_CHECK_HUY = HAVE_CHECK_HUY;
        this.SERVICE_KEY_SMS = SERVICE_KEY_SMS;
        this.SERVICE_ID = SERVICE_ID;
        this.SERVICE_NAME = SERVICE_NAME;
        this.SERVICE_NAME_SMS = SERVICE_NAME_SMS;
        this.SERVICE_KEY = SERVICE_KEY;
        this.SERVICE_INFO = SERVICE_INFO;
        this.HAVE_MAINTAIN = HAVE_MAINTAIN;
        this.PACKAGE_DYNAMIC = PACKAGE_DYNAMIC;
        this.PACKAGE_CODE = PACKAGE_CODE;
        this.PACKAGE_CODE_API = PACKAGE_CODE_API;
        this.CAPACITY = CAPACITY;
        this.PACKAGE_PRICE = PACKAGE_PRICE;
        this.SPLIT_TIP = SPLIT_TIP;
        this.PACKAGE_CODE_STATUS = PACKAGE_CODE_STATUS;
        this.PACKAGE_CODE_ID = PACKAGE_CODE_ID;
        this.PACKAGE_TYPE = PACKAGE_TYPE;
        this.PACKAGE_CODE_SMS = PACKAGE_CODE_SMS;
        this.PACKAGE_CYCLE = PACKAGE_CYCLE;
        this.LIKE_MI_OR_VAS = LIKE_MI_OR_VAS;
    }
    @JsonProperty("HAVE_CHANGE_PACKAGE")
    public String getHAVE_CHANGE_PACKAGE() {
        return HAVE_CHANGE_PACKAGE;
    }

    @JsonProperty("HAVE_CHANGE_PACKAGE")
    public void setHAVE_CHANGE_PACKAGE(String HAVE_CHANGE_PACKAGE) {
        this.HAVE_CHANGE_PACKAGE = HAVE_CHANGE_PACKAGE;
    }

    @JsonProperty("CHANGE_PACKAGE")
    public String getCHANGE_PACKAGE() {
        return CHANGE_PACKAGE;
    }

    @JsonProperty("CHANGE_PACKAGE")
    public void setCHANGE_PACKAGE(String CHANGE_PACKAGE) {
        this.CHANGE_PACKAGE = CHANGE_PACKAGE;
    }

    @JsonProperty("HAVE_CHECK_HUY_WITH_5000")
    public String getHAVE_CHECK_HUY_WITH_5000() {
        return HAVE_CHECK_HUY_WITH_5000;
    }

    @JsonProperty("HAVE_CHECK_HUY_WITH_5000")
    public void setHAVE_CHECK_HUY_WITH_5000(String HAVE_CHECK_HUY_WITH_5000) {
        this.HAVE_CHECK_HUY_WITH_5000 = HAVE_CHECK_HUY_WITH_5000;
    }

    @JsonProperty("NEED_CHECK_SERVICE")
    public String getNEED_CHECK_SERVICE() {
        return NEED_CHECK_SERVICE;
    }

    @JsonProperty("NEED_CHECK_SERVICE")
    public void setNEED_CHECK_SERVICE(String NEED_CHECK_SERVICE) {
        this.NEED_CHECK_SERVICE = NEED_CHECK_SERVICE;
    }

    @JsonProperty("HAVE_CHECK_HUY")
    public String getHAVE_CHECK_HUY() {
        return HAVE_CHECK_HUY;
    }

    @JsonProperty("HAVE_CHECK_HUY")
    public void setHAVE_CHECK_HUY(String HAVE_CHECK_HUY) {
        this.HAVE_CHECK_HUY = HAVE_CHECK_HUY;
    }

    @JsonProperty("SERVICE_KEY_SMS")
    public String getSERVICE_KEY_SMS() {
        return SERVICE_KEY_SMS;
    }

    @JsonProperty("SERVICE_KEY_SMS")
    public void setSERVICE_KEY_SMS(String SERVICE_KEY_SMS) {
        this.SERVICE_KEY_SMS = SERVICE_KEY_SMS;
    }

    @JsonProperty("SERVICE_ID")
    public String getSERVICE_ID() {
        return SERVICE_ID;
    }

    @JsonProperty("SERVICE_ID")
    public void setSERVICE_ID(String SERVICE_ID) {
        this.SERVICE_ID = SERVICE_ID;
    }

    @JsonProperty("SERVICE_NAME")
    public String getSERVICE_NAME() {
        return SERVICE_NAME;
    }

    @JsonProperty("SERVICE_NAME")
    public void setSERVICE_NAME(String SERVICE_NAME) {
        this.SERVICE_NAME = SERVICE_NAME;
    }

    @JsonProperty("SERVICE_KEY")
    public String getSERVICE_KEY() {
        return SERVICE_KEY;
    }

    @JsonProperty("SERVICE_KEY")
    public void setSERVICE_KEY(String SERVICE_KEY) {
        this.SERVICE_KEY = SERVICE_KEY;
    }

    @JsonProperty("SERVICE_INFO")
    public String getSERVICE_INFO() {
        return SERVICE_INFO;
    }

    @JsonProperty("SERVICE_INFO")
    public void setSERVICE_INFO(String SERVICE_INFO) {
        this.SERVICE_INFO = SERVICE_INFO;
    }

    @JsonProperty("HAVE_MAINTAIN")
    public String getHAVE_MAINTAIN() {
        return HAVE_MAINTAIN;
    }

    @JsonProperty("HAVE_MAINTAIN")
    public void setHAVE_MAINTAIN(String HAVE_MAINTAIN) {
        this.HAVE_MAINTAIN = HAVE_MAINTAIN;
    }

    @JsonProperty("PACKAGE_DYNAMIC")
    public String getPACKAGE_DYNAMIC() {
        return PACKAGE_DYNAMIC;
    }

    @JsonProperty("PACKAGE_DYNAMIC")
    public void setPACKAGE_DYNAMIC(String PACKAGE_DYNAMIC) {
        this.PACKAGE_DYNAMIC = PACKAGE_DYNAMIC;
    }

    @JsonProperty("PACKAGE_CODE")
    public String getPACKAGE_CODE() {
        return PACKAGE_CODE;
    }

    @JsonProperty("PACKAGE_CODE")
    public void setPACKAGE_CODE(String PACKAGE_CODE) {
        this.PACKAGE_CODE = PACKAGE_CODE;
    }

    @JsonProperty("PACKAGE_CODE_API")
    public String getPACKAGE_CODE_API() {
        return PACKAGE_CODE_API;
    }

    @JsonProperty("PACKAGE_CODE_API")
    public void setPACKAGE_CODE_API(String PACKAGE_CODE_API) {
        this.PACKAGE_CODE_API = PACKAGE_CODE_API;
    }

    @JsonProperty("CAPACITY")
    public String getCAPACITY() {
        return CAPACITY;
    }

    @JsonProperty("CAPACITY")
    public void setCAPACITY(String CAPACITY) {
        this.CAPACITY = CAPACITY;
    }

    @JsonProperty("PACKAGE_PRICE")
    public String getPACKAGE_PRICE() {
        return PACKAGE_PRICE;
    }

    @JsonProperty("PACKAGE_PRICE")
    public void setPACKAGE_PRICE(String PACKAGE_PRICE) {
        this.PACKAGE_PRICE = PACKAGE_PRICE;
    }

    @JsonProperty("PACKAGE_CODE_STATUS")
    public String getPACKAGE_CODE_STATUS() {
        return PACKAGE_CODE_STATUS;
    }

    @JsonProperty("PACKAGE_CODE_STATUS")
    public void setPACKAGE_CODE_STATUS(String PACKAGE_CODE_STATUS) {
        this.PACKAGE_CODE_STATUS = PACKAGE_CODE_STATUS;
    }

    @JsonProperty("PACKAGE_CODE_ID")
    public String getPACKAGE_CODE_ID() {
        return PACKAGE_CODE_ID;
    }

    @JsonProperty("PACKAGE_CODE_ID")
    public void setPACKAGE_CODE_ID(String PACKAGE_CODE_ID) {
        this.PACKAGE_CODE_ID = PACKAGE_CODE_ID;
    }

    @JsonProperty("PACKAGE_TYPE")
    public String getPACKAGE_TYPE() {
        return PACKAGE_TYPE;
    }

    @JsonProperty("PACKAGE_TYPE")
    public void setPACKAGE_TYPE(String PACKAGE_TYPE) {
        this.PACKAGE_TYPE = PACKAGE_TYPE;
    }

    @JsonProperty("PACKAGE_CODE_SMS")
    public String getPACKAGE_CODE_SMS() {
        return PACKAGE_CODE_SMS;
    }

    @JsonProperty("PACKAGE_CODE_SMS")
    public void setPACKAGE_CODE_SMS(String PACKAGE_CODE_SMS) {
        this.PACKAGE_CODE_SMS = PACKAGE_CODE_SMS;
    }

    @JsonProperty("PACKAGE_CYCLE")
    public String getPACKAGE_CYCLE() {
        return PACKAGE_CYCLE;
    }

    @JsonProperty("PACKAGE_CYCLE")
    public void setPACKAGE_CYCLE(String PACKAGE_CYCLE) {
        this.PACKAGE_CYCLE = PACKAGE_CYCLE;
    }

    @JsonProperty("LIKE_MI_OR_VAS")
    public String getLIKE_MI_OR_VAS() {
        return LIKE_MI_OR_VAS;
    }

    @JsonProperty("LIKE_MI_OR_VAS")
    public void setLIKE_MI_OR_VAS(String LIKE_MI_OR_VAS) {
        this.LIKE_MI_OR_VAS = LIKE_MI_OR_VAS;
    }

    @JsonProperty("SERVICE_NAME_SMS")
    public String getSERVICE_NAME_SMS() {
        return SERVICE_NAME_SMS;
    }

    @JsonProperty("SERVICE_NAME_SMS")
    public void setSERVICE_NAME_SMS(String SERVICE_NAME_SMS) {
        this.SERVICE_NAME_SMS = SERVICE_NAME_SMS;
    }

    @JsonProperty("SPLIT_TIP")
    public String getSPLIT_TIP() {
        return SPLIT_TIP;
    }

    @JsonProperty("SPLIT_TIP")
    public void setSPLIT_TIP(String SPLIT_TIP) {
        this.SPLIT_TIP = SPLIT_TIP;
    }

    @Override
    public String toString() {
        return "Step10DTO{" +
                "HAVE_CHANGE_PACKAGE='" + HAVE_CHANGE_PACKAGE + '\'' +
                ", CHANGE_PACKAGE='" + CHANGE_PACKAGE + '\'' +
                ", HAVE_CHECK_HUY_WITH_5='" + HAVE_CHECK_HUY_WITH_5000 + '\'' +
                ", NEED_CHECK_SERVICE='" + NEED_CHECK_SERVICE + '\'' +
                ", HAVE_CHECK_HUY='" + HAVE_CHECK_HUY + '\'' +
                ", SERVICE_KEY_SMS='" + SERVICE_KEY_SMS + '\'' +
                ", SERVICE_ID='" + SERVICE_ID + '\'' +
                ", SERVICE_NAME='" + SERVICE_NAME + '\'' +
                ", SERVICE_NAME_SMS='" + SERVICE_NAME_SMS + '\'' +
                ", SERVICE_KEY='" + SERVICE_KEY + '\'' +
                ", SERVICE_INFO='" + SERVICE_INFO + '\'' +
                ", HAVE_MAINTAIN='" + HAVE_MAINTAIN + '\'' +
                ", PACKAGE_DYNAMIC='" + PACKAGE_DYNAMIC + '\'' +
                ", PACKAGE_CODE='" + PACKAGE_CODE + '\'' +
                ", PACKAGE_CODE_API='" + PACKAGE_CODE_API + '\'' +
                ", CAPACITY='" + CAPACITY + '\'' +
                ", PACKAGE_PRICE='" + PACKAGE_PRICE + '\'' +
                ", SPLIT_TIP='" + SPLIT_TIP + '\'' +
                ", PACKAGE_CODE_STATUS='" + PACKAGE_CODE_STATUS + '\'' +
                ", PACKAGE_CODE_ID='" + PACKAGE_CODE_ID + '\'' +
                ", PACKAGE_TYPE='" + PACKAGE_TYPE + '\'' +
                ", PACKAGE_CODE_SMS='" + PACKAGE_CODE_SMS + '\'' +
                ", PACKAGE_CYCLE='" + PACKAGE_CYCLE + '\'' +
                ", LIKE_MI_OR_VAS='" + LIKE_MI_OR_VAS + '\'' +
                '}';
    }
}
