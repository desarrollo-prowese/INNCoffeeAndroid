package com.example.inncoffee.ui.ofertas;

public class OfertasClass {


    public OfertasClass(String ofertas, String porcentaje) {
        this.porcentaje = porcentaje;
        this.ofertas = ofertas;
    }

    public OfertasClass(String porcentaje) {
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }



    public String getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(String porcentaje) {
        this.porcentaje = porcentaje;
    }

    private String porcentaje;

    public String getOfertas() {
        return ofertas;
    }

    public void setOfertas(String ofertas) {
        this.ofertas = ofertas;
    }

    private String ofertas;
    private String uid;


    @Override
    public boolean equals(Object obj) {
        if(obj instanceof OfertasClass){
            OfertasClass OfertasClass= (OfertasClass) obj;
            return this.uid.equals(OfertasClass.getUid());
        }
        else
            return  false;
    }
}



