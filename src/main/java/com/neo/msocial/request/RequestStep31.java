package com.neo.msocial.request;

import com.neo.msocial.dto.Soap34;
import com.neo.msocial.dto.Soap8;

import java.util.List;

public class RequestStep31 {

    List<Soap8> lstSoap8;
    List<Soap34> lstSoap34;
    String msisdn;
    String sharingKey;
    String serviceId ;
    String packageCode ;
    String channel;
    String scriptShopId;
    String checkStartDate;

    public List<Soap8> getLstSoap8() {
        return lstSoap8;
    }

    public void setLstSoap8(List<Soap8> lstSoap8) {
        this.lstSoap8 = lstSoap8;
    }

    public List<Soap34> getLstSoap34() {
        return lstSoap34;
    }

    public void setLstSoap34(List<Soap34> lstSoap34) {
        this.lstSoap34 = lstSoap34;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getSharingKey() {
        return sharingKey;
    }

    public void setSharingKey(String sharingKey) {
        this.sharingKey = sharingKey;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
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

    public String getScriptShopId() {
        return scriptShopId;
    }

    public void setScriptShopId(String scriptShopId) {
        this.scriptShopId = scriptShopId;
    }

    public String getCheckStartDate() {
        return checkStartDate;
    }

    public void setCheckStartDate(String checkStartDate) {
        this.checkStartDate = checkStartDate;
    }
}
