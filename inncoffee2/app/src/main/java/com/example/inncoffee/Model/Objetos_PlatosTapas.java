package com.example.inncoffee.Model;

public class Objetos_PlatosTapas {
    String alergia;
    String nombre;
    Double precioPlato;
    Double precioTapa;
    String imagelink;

    public Objetos_PlatosTapas() {
    }

    public Objetos_PlatosTapas(String alergia, String nombre, Double precioPlato, Double precioTapa, String imagelink) {
        this.alergia = alergia;
        this.nombre = nombre;
        this.precioPlato = precioPlato;
        this.precioTapa = precioTapa;
        this.imagelink = imagelink;
    }

    public String getAlergia() {
        return alergia;
    }

    public void setAlergia(String alergia) {
        this.alergia = alergia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecioPlato() {
        return precioPlato;
    }

    public void setPrecioPlato(Double precioPlato) {
        this.precioPlato = precioPlato;
    }

    public Double getPrecioTapa() {
        return precioTapa;
    }

    public void setPrecioTapa(Double precioTapa) {
        this.precioTapa = precioTapa;
    }

    public String getImagelink() {
        return imagelink;
    }

    public void setImagelink(String imagelink) {
        this.imagelink = imagelink;
    }

}
