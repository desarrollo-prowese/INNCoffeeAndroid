package com.example.inncoffee.Model;

public class Objetos_Pedidos {
    String Fecha;
    String Nombre;
    String Precio;
    String Total;

    public Objetos_Pedidos() {
    }

    public Objetos_Pedidos(String fecha, String nombre, String precio, String total) {
        Fecha = fecha;
        Nombre = nombre;
        Precio = precio;
        Total = total;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getPrecio() {
        return Precio;
    }

    public void setPrecio(String precio) {
        Precio = precio;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }
}
