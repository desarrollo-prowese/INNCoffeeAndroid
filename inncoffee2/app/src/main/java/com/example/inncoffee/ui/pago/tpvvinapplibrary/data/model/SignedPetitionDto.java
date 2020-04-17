package com.example.inncoffee.ui.pago.tpvvinapplibrary.data.model;


public class SignedPetitionDto {
    private String firma;
    private String mensaje;

    public SignedPetitionDto(String str, String str2) {
        this.firma = str;
        this.mensaje = str2;
    }

    public String getFirma() {
        return this.firma;
    }

    public void setFirma(String str) {
        this.firma = str;
    }

    public String getMensaje() {
        return this.mensaje;
    }

    public void setMensaje(String str) {
        this.mensaje = str;
    }
}
