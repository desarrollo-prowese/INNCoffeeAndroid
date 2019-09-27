package com.example.inncoffee.Model;

public class Objetos {
    public String nombre;
    public String imagelink;
    public String precio;
    public String imageAlergia;
    public String precioEntera;
    public String precioMedia;
    public String alergia;
    public String alergiaLista;
    // public Button seleccionarPan;

    public Objetos() {
    }

    public Objetos(String nombre, String imagelink, String precio, String imageAlergia, String precioEntera, String precioMedia, String alergia, String alergiaLista) {
        this.nombre = nombre;
        this.imagelink = imagelink;
        this.precio = precio;
        this.imageAlergia = imageAlergia;
        this.precioEntera = precioEntera;
        this.precioMedia = precioMedia;
        this.alergia = alergia;
        this.alergiaLista = alergiaLista;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagelink() {
        return imagelink;
    }

    public void setImagelink(String imagelink) {
        this.imagelink = imagelink;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getImageAlergia() {
        return imageAlergia;
    }

    public void setImageAlergia(String imageAlergia) {
        this.imageAlergia = imageAlergia;
    }

    public String getPrecioEntera() {
        return precioEntera;
    }

    public void setPrecioEntera(String precioEntera) {
        this.precioEntera = precioEntera;
    }

    public String getPrecioMedia() {
        return precioMedia;
    }

    public void setPrecioMedia(String precioMedia) {
        this.precioMedia = precioMedia;
    }

    public String getAlergia() {
        return alergia;
    }

    public void setAlergia(String alergia) {
        this.alergia = alergia;
    }

    public String getAlergiaLista() {
        return alergiaLista;
    }

    public void setAlergiaLista(String alergiaLista) {
        this.alergiaLista = alergiaLista;
    }
}
