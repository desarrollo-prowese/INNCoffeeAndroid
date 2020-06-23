package com.rakshasa.inncoffee.ui.mispedidos;

import com.google.firebase.database.DataSnapshot;

public class MisPedidosClass {


    public MisPedidosClass(String texto,String precio,String precio2) {
        this.texto = texto;
        this.precio = precio;
        this.precio2 = precio2;
    }
    public MisPedidosClass(String texto,String precio) {
        this.texto = texto;
        this.precio = precio;
    }


    public MisPedidosClass (String texto,String comanda,String fecha,String precio) {
        this.texto = texto;
        this.comanda = comanda;
        this.fecha = fecha;
        this.precio = precio;
    }

    public String comanda;


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

    public MisPedidosClass(String texto) {
        this.texto = texto;

    }


    public String getPrecio2 () {
        return precio2;
    }

    public void setPrecio2 (String precio2) {
        this.precio2 = precio2;
    }

    private String precio2;
    private String texto;
    private String uid;

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    private String precio;


    @Override
    public boolean equals(Object obj) {
        if(obj instanceof MisPedidosClass){
            MisPedidosClass MisPedidosClass= (MisPedidosClass) obj;
            return this.uid.equals(MisPedidosClass.getUid());
        }
        else
            return  false;
    }
}



