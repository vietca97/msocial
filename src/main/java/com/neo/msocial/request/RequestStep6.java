package com.neo.msocial.request;

import com.neo.msocial.dto.Soap2;
import com.neo.msocial.dto.Soap35;

import java.util.List;

public class RequestStep6 {

    private List<Soap35> lstSoap35;
    private List<Soap2> lstSoap2;

    public List<Soap35> getLstSoap35() {
        return lstSoap35;
    }

    public void setLstSoap35(List<Soap35> lstSoap35) {
        this.lstSoap35 = lstSoap35;
    }

    public List<Soap2> getLstSoap2() {
        return lstSoap2;
    }

    public void setLstSoap2(List<Soap2> lstSoap2) {
        this.lstSoap2 = lstSoap2;
    }
}
