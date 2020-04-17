package com.example.inncoffee.ui.pago.tpvvinapplibrary.webviewPayment.domain.model.petition;

import com.example.inncoffee.ui.pago.tpvvinapplibrary.TPVVConstants;
import com.google.gson.annotations.SerializedName;

public class GenericPetitionWVSign {
    @SerializedName("bundle")
    private String bundle;
    @SerializedName("fuc")
    private String fuc;
    @SerializedName("parametros")
    private DatosPeticionWVSign parametros;
    @SerializedName("so")
    private String so = "Android";
    @SerializedName("terminal")
    private String terminal;
    @SerializedName("version")
    private String version = TPVVConstants.version;

    public String getBundle() {
        return this.bundle;
    }

    public void setBundle(String str) {
        this.bundle = str;
    }

    public String getFuc() {
        return this.fuc;
    }

    public void setFuc(String str) {
        this.fuc = str;
    }

    public String getSo() {
        return this.so;
    }

    public void setSo(String str) {
        this.so = str;
    }

    public String getTerminal() {
        return this.terminal;
    }

    public void setTerminal(String str) {
        this.terminal = str;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String str) {
        this.version = str;
    }

    public DatosPeticionWVSign getParametros() {
        return this.parametros;
    }

    public void setParametros(DatosPeticionWVSign datosPeticionWVSign) {
        this.parametros = datosPeticionWVSign;
    }
}
