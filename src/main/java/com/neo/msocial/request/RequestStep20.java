package com.neo.msocial.request;

import com.neo.msocial.dto.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public class RequestStep20 {

    private List<Soap8> lstSoap8;
    private List<Soap12> lstSoap12;
    private List<Soap14> lstSoap14;
    private List<Soap15> lstSoap15;
    private List<Soap16> lstSoap16;
    private List<Soap17> lstSoap17;
    private List<Soap19> lstSoap19;
    private List<Soap34> lstSoap34;
    private String channel;
    private String sharingKeyId;
    private String msisdn;

    public List<Soap8> getLstSoap8() {
        return lstSoap8;
    }

    public void setLstSoap8(List<Soap8> lstSoap8) {
        this.lstSoap8 = lstSoap8;
    }

    public List<Soap12> getLstSoap12() {
        return lstSoap12;
    }

    public void setLstSoap12(List<Soap12> lstSoap12) {
        this.lstSoap12 = lstSoap12;
    }

    public List<Soap14> getLstSoap14() {
        return lstSoap14;
    }

    public void setLstSoap14(List<Soap14> lstSoap14) {
        this.lstSoap14 = lstSoap14;
    }

    public List<Soap15> getLstSoap15() {
        return lstSoap15;
    }

    public void setLstSoap15(List<Soap15> lstSoap15) {
        this.lstSoap15 = lstSoap15;
    }

    public List<Soap16> getLstSoap16() {
        return lstSoap16;
    }

    public void setLstSoap16(List<Soap16> lstSoap16) {
        this.lstSoap16 = lstSoap16;
    }

    public List<Soap17> getLstSoap17() {
        return lstSoap17;
    }

    public void setLstSoap17(List<Soap17> lstSoap17) {
        this.lstSoap17 = lstSoap17;
    }

    public List<Soap19> getLstSoap19() {
        return lstSoap19;
    }

    public void setLstSoap19(List<Soap19> lstSoap19) {
        this.lstSoap19 = lstSoap19;
    }

    public List<Soap34> getLstSoap34() {
        return lstSoap34;
    }

    public void setLstSoap34(List<Soap34> lstSoap34) {
        this.lstSoap34 = lstSoap34;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getSharingKeyId() {
        return sharingKeyId;
    }

    public void setSharingKeyId(String sharingKeyId) {
        this.sharingKeyId = sharingKeyId;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }
}
