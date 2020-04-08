package com.example.inncoffee.ui.bebidas;

public class BebidasDB {

    public BebidasDB() {

    }

    public BebidasDB(String nombrearticulo) {
        this.nombrearticulo = nombrearticulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombrearticulo() {
        return nombrearticulo;
    }

    public void setNombrearticulo(String nombrearticulo) {
        this.nombrearticulo = nombrearticulo;
    }

    public String getDescarticulo() {
        return descarticulo;
    }

    public void setDescarticulo(String descarticulo) {
        this.descarticulo = descarticulo;
    }

    public String getTipoPan() {
        return tipoPan;
    }

    public void setTipoPan(String tipoPan) {
        this.tipoPan = tipoPan;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getEnteraomedia() {
        return enteraomedia;
    }

    public void setEnteraomedia(String enteraomedia) {
        this.enteraomedia = enteraomedia;
    }

    public BebidasDB(String titulo, String imagen, String nombrearticulo, String descarticulo, String tipoPan, String precio, String enteraomedia, String alojenos) {
        this.titulo = titulo;
        this.imagen = imagen;
        this.nombrearticulo = nombrearticulo;
        this.descarticulo = descarticulo;
        this.tipoPan = tipoPan;
        this.precio = precio;
        this.enteraomedia = enteraomedia;
        this.alojenos = alojenos;
    }

    private String titulo;


    public String getAlojenos() {
        return alojenos;
    }

    public void setAlojenos(String alojenos) {
        this.alojenos = alojenos;
    }

    private String alojenos;
    private String imagen;
    private String nombrearticulo;
    private String descarticulo;
    private String tipoPan;
    private String precio;
    private String enteraomedia;



}
