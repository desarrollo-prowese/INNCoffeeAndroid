package com.rakshasa.inncoffee.ui.mispuntos;

public class PuntosClass {


    public PuntosClass (String texto,String comanda,String fecha,String precio, String lugar) {
        this.texto = texto;
        this.comanda = comanda;
        this.fecha = fecha;
        this.precio = precio;
        this.lugar = lugar;
    }
    public PuntosClass (String textos,String comandas) {
        this.textos = textos;
        this.comandas = comandas;
    }

    public String getLista () {
        return lista;
    }

    public void setLista (String lista) {
        this.lista = lista;
    }

    public String lista;

    public String getPrecio () {
        return precio;
    }

    public void setPrecio (String precio) {
        this.precio = precio;
    }

    public String precio;

    public String getLugar () {
        return lugar;
    }

    public void setLugar (String lugar) {
        this.lugar = lugar;
    }

    public String lugar;

    public String comanda;

    public String getComandas () {
        return comandas;
    }

    public void setComandas (String comandas) {
        this.comandas = comandas;
    }

    public String getTextos () {
        return textos;
    }

    public void setTextos (String textos) {
        this.textos = textos;
    }

    public String comandas;

    public String getComanda () {
        return comanda;
    }

    public void setComanda (String comanda) {
        this.comanda = comanda;
    }

    public String getFecha () {
        return fecha;
    }

    public void setFecha (String fecha) {
        this.fecha = fecha;
    }

    public String fecha;

    public String getTexto() {
        return texto;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public PuntosClass (String lista) {
        this.lista = lista;

    }

    private String textos;

    private String texto;
    private String uid;


    @Override
    public boolean equals(Object obj) {
        if(obj instanceof PuntosClass){
            PuntosClass PuntosClass= (PuntosClass) obj;
            return this.uid.equals(PuntosClass.getUid());
        }
        else
            return  false;
    }
}



