package com.example.inncoffee.Model;

public class Category {
    private String title;
    private String precio;

    public Category() {
        super();
    }

    public Category(String title, String precio) {
        super();
        this.title = title;
        this.precio = precio;
    }

    public String getTitle() {
        return title;
    }

    public void setTittle(String title) {
        this.title = title;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

}
