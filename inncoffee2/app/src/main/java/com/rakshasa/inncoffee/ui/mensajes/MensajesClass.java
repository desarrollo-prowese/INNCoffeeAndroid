package com.rakshasa.inncoffee.ui.mensajes;

public class MensajesClass {


    public MensajesClass(String uid,String texto) {
        this.texto = texto;
        this.uid = uid;
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

    public MensajesClass(String texto) {
        this.texto = texto;

    }



    private String texto;
    private String uid;


    @Override
    public boolean equals(Object obj) {
        if(obj instanceof MensajesClass){
            MensajesClass MensajesClass= (MensajesClass) obj;
            return this.uid.equals(MensajesClass.getUid());
        }
        else
            return  false;
    }
}



