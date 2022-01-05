package com.neo.msocial.dto;

public class Soap2 {

    private String ErrorCode;

    private String ErrorDesc;

    private String time_validate;

    public String getTime_validate() {
        return time_validate;
    }

    public void setTime_validate(String time_validate) {
        this.time_validate = time_validate;
    }

    public String getErrorDesc() {
        return ErrorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        ErrorDesc = errorDesc;
    }

    public String getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(String errorCode) {
        ErrorCode = errorCode;
    }
}
