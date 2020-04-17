package com.example.inncoffee.ui.pago.tpvvinapplibrary.webviewPayment.domain.model.response;

import com.google.gson.annotations.SerializedName;

public class GenericResponseWV {
    @SerializedName("code")
    private String code;
    @SerializedName("datosPeticion")
    private SignResponseWV datosPeticion;
    @SerializedName("desc")
    private String desc;

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

    public SignResponseWV getDatosPeticion() {
        return this.datosPeticion;
    }

    public void setDatosPeticion(SignResponseWV signResponseWV) {
        this.datosPeticion = signResponseWV;
    }
}
