package com.neo.msocial.request;

import com.neo.msocial.dto.Soap12;
import com.neo.msocial.dto.Soap28;
import com.neo.msocial.dto.Soap35;
import com.neo.msocial.dto.Soap9;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public class RequestStep18 {
    private List<Soap35> lstSoap35;
    private List<Soap9> lstSoap9;
    private List<Soap12> lstSoap12;
    private List<Soap28> lstSoap28;

    public List<Soap35> getLstSoap35() {
        return lstSoap35;
    }

    public void setLstSoap35(List<Soap35> lstSoap35) {
        this.lstSoap35 = lstSoap35;
    }

    public List<Soap9> getLstSoap9() {
        return lstSoap9;
    }

    public void setLstSoap9(List<Soap9> lstSoap9) {
        this.lstSoap9 = lstSoap9;
    }

    public List<Soap12> getLstSoap12() {
        return lstSoap12;
    }

    public void setLstSoap12(List<Soap12> lstSoap12) {
        this.lstSoap12 = lstSoap12;
    }

    public List<Soap28> getLstSoap28() {
        return lstSoap28;
    }

    public void setLstSoap28(List<Soap28> lstSoap28) {
        this.lstSoap28 = lstSoap28;
    }
}
