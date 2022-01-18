package com.neo.msocial.request.sendunicastdf;

import com.neo.msocial.dto.Soap12;
import com.neo.msocial.dto.Soap34;
import com.neo.msocial.dto.Soap8;

import java.util.List;

public class RequestChongLoiDung {
    private List<Soap8> lstSoap8;
    private List<Soap12> lstSoap12;
    private List<Soap34> lstSoap34;
    private String script_shop_id;
    private String msisdn;
    private String serviceid;
    private String sharingkey;

    public String getSharingkey() {
        return sharingkey;
    }

    public void setSharingkey(String sharingkey) {
        this.sharingkey = sharingkey;
    }

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

    public List<Soap34> getLstSoap34() {
        return lstSoap34;
    }

    public void setLstSoap34(List<Soap34> lstSoap34) {
        this.lstSoap34 = lstSoap34;
    }

    public String getScript_shop_id() {
        return script_shop_id;
    }

    public void setScript_shop_id(String script_shop_id) {
        this.script_shop_id = script_shop_id;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getServiceid() {
        return serviceid;
    }

    public void setServiceid(String serviceid) {
        this.serviceid = serviceid;
    }
}
