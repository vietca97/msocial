package com.neo.msocial.request.sendunicastdf;

import com.neo.msocial.dto.Soap12;
import com.neo.msocial.dto.Soap34;
import com.neo.msocial.dto.Soap8;

import java.util.List;

public class RequestProcessBusiness {
    private List<Soap8> lstSoap8;
    private List<Soap34> lstSoap34;
    private String script_shop_id;
    private String msisdn;
    private String channel;
    private String maChiNhanh;
    private String sharing_key_id;
    private String partnerId;
    private String agentId;
    private String channelId;

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

    public String getSharing_key_id() {
        return sharing_key_id;
    }

    public void setSharing_key_id(String sharing_key_id) {
        this.sharing_key_id = sharing_key_id;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

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

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
