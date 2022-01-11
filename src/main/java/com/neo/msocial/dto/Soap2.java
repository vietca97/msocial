package com.neo.msocial.dto;

public class Soap2 {

    private String errorCode;

    private String errorDesc;

    private String timeValidate;
    public static final String time_validate = "time_validate";

    private String partnerId;
    public static final String partner_id = "partner_id";

    private String agentId;
    public static final String agent_id = "agent_id";

    private String serviceId;
    public static final String service_id = "service_id";

    private String packageCodeId;
    public static final String packagecode_id = "packagecode_id";

    private String channelId;
    public static final String channel_id = "channel_id";

    private String maChiNhanh;
    public static final String ma_chi_nhanh = "ma_chi_nhanh";

    private String scriptShopId;
    public static final String script_shop_id = "script_shop_id";

    private String sharingKeyId;
    public static final String sharing_key_id = "sharing_key_id";

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getTimeValidate() {
        return timeValidate;
    }

    public void setTimeValidate(String timeValidate) {
        this.timeValidate = timeValidate;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getPackageCodeId() {
        return packageCodeId;
    }

    public void setPackageCodeId(String packageCodeId) {
        this.packageCodeId = packageCodeId;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getMaChiNhanh() {
        return maChiNhanh;
    }

    public void setMaChiNhanh(String maChiNhanh) {
        this.maChiNhanh = maChiNhanh;
    }

    public String getScriptShopId() {
        return scriptShopId;
    }

    public void setScriptShopId(String scriptShopId) {
        this.scriptShopId = scriptShopId;
    }

    public String getSharingKeyId() {
        return sharingKeyId;
    }

    public void setSharingKeyId(String sharingKeyId) {
        this.sharingKeyId = sharingKeyId;
    }
}

