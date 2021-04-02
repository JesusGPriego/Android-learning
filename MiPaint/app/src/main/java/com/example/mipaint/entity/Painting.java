package com.example.mipaint.entity;

import android.graphics.Paint;

public class Painting {
    private int posX;
    private int posY;
    private String shape;
    private String color;

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Painting(int posX, int posY, String shape, String color) {
        this.posX = posX;
        this.posY = posY;
        this.shape = shape;
        this.color = color;
    }
}
