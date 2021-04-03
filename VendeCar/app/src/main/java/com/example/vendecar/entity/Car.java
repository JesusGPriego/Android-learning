package com.example.vendecar.entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

//Entity means Room will identify this class as database table.
//Parameter is to indicate table name. Else, table name would be class name.
@Entity(tableName = "car_table")
public class Car {
    //Pointing room this is primary key and it should autogenerate it.
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String marca;

    private String modelo;

    private int km;

    private int anio;

    private int cc;

    private int cv;

    private int precio;

    private int vendido;

    //Room needs a constructor to build item and persist them in the database.
    //We dont create id parameter, since Room will autogenerate it.

    public Car(String marca, String modelo, int km, int anio, int cc, int cv, int precio, int vendido) {
        this.marca = marca;
        this.modelo = modelo;
        this.km = km;
        this.anio = anio;
        this.cc = cc;
        this.cv = cv;
        this.precio = precio;
        this.vendido = vendido;
    }
    //Since we cannot set id in constructor, we need this setter here in order to do update operations.
    public void setId(int id) {
        this.id = id;
    }
    //Getter methods. Room use those to persist data.

    public int getId() {
        return id;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public int getKm() {
        return km;
    }

    public int getAnio() {
        return anio;
    }

    public int getCc() {
        return cc;
    }

    public int getCv(){
        return cv;
    }

    public int getPrecio() {
        return precio;
    }

    public int getVendido() {
        return vendido;
    }
}
