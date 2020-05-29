package com.example.lucas.todrobot;

/**
 * Created by Lucas on 27/07/2016.
 */
public class Errado {
    public int id;
    public int tipo;
    public Errado(int id, int t){
        setId(id);
        setTipo(t);
    }
    private void setId(int id){
        this.id = id;
    }
    private void setTipo(int t){
        tipo = t;
    }

}
