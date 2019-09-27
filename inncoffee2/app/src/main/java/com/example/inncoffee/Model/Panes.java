package com.example.inncoffee.Model;

import android.widget.Button;

public class Panes {
    public String nombre;
    public String imagelink;
    public Button btSeleccionar;

    public Panes() {
    }

    public Panes(String nombre, String imagelink, Button btSeleccionar) {
        this.nombre = nombre;
        this.imagelink = imagelink;
        this.btSeleccionar = btSeleccionar;
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

    public Button getBtSeleccionar() {
        return btSeleccionar;
    }

    public void setBtSeleccionar(Button btSeleccionar) {
        this.btSeleccionar = btSeleccionar;
    }

}