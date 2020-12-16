package com.example.notas;

import java.io.Serializable;

/**
 * Note with Title.
 */
public class Nota implements Serializable {
    /**
     * It is the text of the note
     */
    private String texto;
    /**
     * It is the title of the note.
     */
    private String titulo;

    public Nota(String titulo, String texto){
        this.texto = texto;
        this.titulo = titulo;
    }

    public String getText(){
        return this.texto;
    }

    public void setText(String texto){
        this.texto = texto;
    }

    public String getTitle(){
        return this.titulo;
    }

    public void setTitle(String titulo){
        this.titulo = titulo;
    }

}
