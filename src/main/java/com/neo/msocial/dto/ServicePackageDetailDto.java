package com.neo.msocial.dto;

public class ServicePackageDetailDto {
    private String PACKAGE_CODE;
    private String PACKAGE_PRICE;
    private String CAPACITY;
    private String PACKAGE_CYCLE;

    public String getPACKAGE_CYCLE() {
        return PACKAGE_CYCLE;
    }

    public void setPACKAGE_CYCLE(String PACKAGE_CYCLE) {
        this.PACKAGE_CYCLE = PACKAGE_CYCLE;
    }

    public String getCAPACITY() {
        return CAPACITY;
    }

    public void setCAPACITY(String CAPACITY) {
        this.CAPACITY = CAPACITY;
    }

    public String getPACKAGE_CODE() {
        return PACKAGE_CODE;
    }

    public void setPACKAGE_CODE(String PACKAGE_CODE) {
        this.PACKAGE_CODE = PACKAGE_CODE;
    }

    public String getPACKAGE_PRICE() {
        return PACKAGE_PRICE;
    }

    public void setPACKAGE_PRICE(String PACKAGE_PRICE) {
        this.PACKAGE_PRICE = PACKAGE_PRICE;
    }
}
