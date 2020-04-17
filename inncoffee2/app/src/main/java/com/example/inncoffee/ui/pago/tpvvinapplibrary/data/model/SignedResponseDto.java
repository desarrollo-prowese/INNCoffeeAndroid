package com.example.inncoffee.ui.pago.tpvvinapplibrary.data.model;



public class SignedResponseDto {

    private String firma;
    private String mensaje;

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
