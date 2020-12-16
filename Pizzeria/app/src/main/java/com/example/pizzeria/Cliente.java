package com.example.pizzeria;

import java.io.Serializable;

public class Cliente implements Serializable {

    private String nombre;
    private String direccion;
    private String telefono;
    private String recogida;

    public Cliente(String nombre, String telefono, String recogida, String direccion){
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.recogida = recogida;
    }

    public Cliente(String nombre, String telefono, String recogida){
        this.nombre = nombre;
        this.telefono = telefono;
        this.recogida = recogida;
    }


    public String getNombre(){
        return this.nombre;
    }

    public String getTelefono(){
        return this.telefono;
    }

    public String getDireccion(){
        return this.direccion;
    }

    public void setDireccion(String direccion) {this.direccion = direccion; }

    public String getRecogida(){
        return this.recogida;
    }



}
