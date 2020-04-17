package com.example.inncoffee.ui.pago.tpvvinapplibrary;

public class ErrorResponse {
    private String code;
    private String desc;

    public ErrorResponse(String str, String str2) {
        this.code = str;
        this.desc = str2;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String str) {
        this.code = str;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String str) {
        this.desc = str;
    }

    public String toString() {
        return "ErrorResponse{code='" + this.code + '\'' + ", desc='" + this.desc + '\'' + '}';
    }
}
