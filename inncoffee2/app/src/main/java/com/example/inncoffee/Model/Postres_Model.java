package com.example.inncoffee.Model;

public class Postres_Model {
    public String alergia;
    String imagelink;
    String nombre;
    Double precio;

    public Postres_Model() {
    }

    public Postres_Model(String imagelink, String nombre, Double precio, String Alergia) {
        this.imagelink = imagelink;
        this.nombre = nombre;
        this.precio = precio;
        this.alergia = Alergia;
    }

    public String getAlergia() {
        return alergia;
    }

    public void setAlergia(String alergia) {
        this.alergia = alergia;
    }

    public String getImagelink() {
        return imagelink;
    }

    public void setImagelink(String imageLink) {
        this.imagelink = imageLink;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }
}
