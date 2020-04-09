package com.example.inncoffee.ui.mispedidos;

public class MisPedidosClass {


    public MisPedidosClass(String texto,String precio) {
        this.texto = texto;
        this.precio = precio;
    }

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



