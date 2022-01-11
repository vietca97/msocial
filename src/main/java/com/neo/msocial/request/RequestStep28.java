package com.neo.msocial.request;

import com.neo.msocial.dto.Soap34;
import com.neo.msocial.dto.Soap8;

import java.util.List;

public class RequestStep28 {

    private List<Soap34> lstSoap34;
    private List<Soap8> lstSoap8;
    private String scriptShopId;
    private String msisdn;
    private String sharingKey;
    private String packageCode;
    private String channel;

    public List<Soap34> getLstSoap34() {
        return lstSoap34;
    }

    public void setLstSoap34(List<Soap34> lstSoap34) {
        this.lstSoap34 = lstSoap34;
    }

    public List<Soap8> getLstSoap8() {
        return lstSoap8;
    }

    public void setLstSoap8(List<Soap8> lstSoap8) {
        this.lstSoap8 = lstSoap8;
    }

    public String getScriptShopId() {
        return scriptShopId;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public void setScriptShopId(String scriptShopId) {
        this.scriptShopId = scriptShopId;
    }

    public String getSharingKey() {
        return sharingKey;
    }

    public void setSharingKey(String sharingKey) {
        this.sharingKey = sharingKey;
    }

    public String getPackageCode() {
        return packageCode;
    }

    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
