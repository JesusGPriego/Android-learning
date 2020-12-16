package com.example.pizzeria;

import java.io.Serializable;

public class Bebida implements Serializable {

    private String nombre;
    private String precio;
    private int imgBebida;
    private int cantidad;


    public Bebida(String nombre, String precio, int imgResource, int cantidad){
        this.precio = precio;
        this.nombre = nombre;
        this.imgBebida = imgResource;
        this.cantidad = cantidad;
    }

    public Bebida(String nombre, String precio, int cantidad){
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad =  cantidad;
    }


    public String getNombre() {
        return nombre;
    }


    public String getPrecio() {
        return precio;
    }



    public int getImgBebida() {
        return imgBebida;
    }

    public void setCantidad(int cantidad){
        this.cantidad = cantidad;
    }

    public int getIntCantidad(){
        return this.cantidad;
    }

    public String getCantidad(){
        return String.valueOf(this.cantidad);
    }

}
