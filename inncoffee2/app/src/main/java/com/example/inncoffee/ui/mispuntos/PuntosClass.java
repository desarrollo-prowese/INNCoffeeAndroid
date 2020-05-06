package com.example.inncoffee.ui.mispuntos;

public class PuntosClass {


    public PuntosClass (String texto,String comanda,String fecha) {
        this.texto = texto;
        this.comanda = comanda;
        this.fecha = fecha;
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

    public PuntosClass (String texto) {
        this.texto = texto;

    }



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



