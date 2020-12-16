package com.example.pizzeria;

import java.io.Serializable;

public class Pizza implements Serializable {

    private String nombre;
    private String precio;
    private int pizzaImage;
    private int cantidad;

    public Pizza(String nombre, String precio, int pizzaImage, int cantidad){
        this.nombre = nombre;
        this. precio = precio;
        this.pizzaImage = pizzaImage;
        this.cantidad = cantidad;
    }
    public Pizza(String nombre, String precio, int cantidad){
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public String getNombre(){
        return this.nombre;
    }

    public String getPrecio(){
        return this.precio;
    }

    public int getPizzaImage(){
        return this.pizzaImage;
    }

    public int getIntCantidad(){
        return this.cantidad;
    }

    public String getStringCantidad(){
        return String.valueOf(this.cantidad);
    }

    public void setCantidad(int cantidad){
        this.cantidad = cantidad;
    }

}
