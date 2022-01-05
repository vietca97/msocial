package com.neo.msocial.dto;

public class Step9DTO {
    private String MT_TYPE_NAME;
    public  static final String mtTypeName="MT_TYPE_NAME";

    private String MT_TYPE_DEFAULT;
    public  static final String mtTypeDefault="MT_TYPE_DEFAULT";

    private String MT_TYPE_KEY;
    public  static final String mtTypeKey="MT_TYPE_KEY";

    private String MT_TYPE_VALUE;
    public  static final String mtTypeValue="MT_TYPE_VALUE";

    private String MT_TYPE_DESC;
    public  static final String mtTypeDesc="MT_TYPE_DESC";

    public Step9DTO() {
    }

    public Step9DTO(String MT_TYPE_NAME, String MT_TYPE_DEFAULT, String MT_TYPE_KEY, String MT_TYPE_VALUE, String MT_TYPE_DESC) {
        this.MT_TYPE_NAME = MT_TYPE_NAME;
        this.MT_TYPE_DEFAULT = MT_TYPE_DEFAULT;
        this.MT_TYPE_KEY = MT_TYPE_KEY;
        this.MT_TYPE_VALUE = MT_TYPE_VALUE;
        this.MT_TYPE_DESC = MT_TYPE_DESC;
    }

    public String getMT_TYPE_NAME() {
        return MT_TYPE_NAME;
    }

    public void setMT_TYPE_NAME(String MT_TYPE_NAME) {
        this.MT_TYPE_NAME = MT_TYPE_NAME;
    }

    public String getMT_TYPE_DEFAULT() {
        return MT_TYPE_DEFAULT;
    }

    public void setMT_TYPE_DEFAULT(String MT_TYPE_DEFAULT) {
        this.MT_TYPE_DEFAULT = MT_TYPE_DEFAULT;
    }

    public String getMT_TYPE_KEY() {
        return MT_TYPE_KEY;
    }

    public void setMT_TYPE_KEY(String MT_TYPE_KEY) {
        this.MT_TYPE_KEY = MT_TYPE_KEY;
    }

    public String getMT_TYPE_VALUE() {
        return MT_TYPE_VALUE;
    }

    public void setMT_TYPE_VALUE(String MT_TYPE_VALUE) {
        this.MT_TYPE_VALUE = MT_TYPE_VALUE;
    }

    public String getMT_TYPE_DESC() {
        return MT_TYPE_DESC;
    }

    public void setMT_TYPE_DESC(String MT_TYPE_DESC) {
        this.MT_TYPE_DESC = MT_TYPE_DESC;
    }

    @Override
    public String toString() {
        return "Step9{" +
                "MT_TYPE_NAME='" + MT_TYPE_NAME + '\'' +
                ", MT_TYPE_DEFAULT='" + MT_TYPE_DEFAULT + '\'' +
                ", MT_TYPE_KEY='" + MT_TYPE_KEY + '\'' +
                ", MT_TYPE_VALUE='" + MT_TYPE_VALUE + '\'' +
                ", MT_TYPE_DESC='" + MT_TYPE_DESC + '\'' +
                '}';
    }
}
