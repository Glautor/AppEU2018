package com.example.usuario.telaswendel;

import android.arch.persistence.room.*;

import java.io.Serializable;

@Entity
public class Resumo implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo
    private String titulo;

    @ColumnInfo
    private String servidor;

    @ColumnInfo
    private String horas;

    @ColumnInfo
    private String minutos;

    @ColumnInfo
    private String dia;

    public Resumo(){}

    public Resumo(String tit, String bol, String h, String m, String d){
        this.titulo = tit;
        this.servidor = bol;
        this.horas = h;
        this.minutos = m;
        this.dia = d;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getServidor() {
        return servidor;
    }

    public void setServidor(String bolsista) {
        this.servidor = bolsista;
    }

    public String getHoras() {
        return horas;
    }

    public void setHoras(String horas) {
        this.horas = horas;
    }

    public String getMinutos() {
        return minutos;
    }

    public void setMinutos(String minutos) {
        this.minutos = minutos;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    @Override
    public boolean equals(Object o){
        if (this==o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resumo resumo = (Resumo) o;

        return servidor == resumo.servidor;
    }

    @Override
    public int hashCode() {
        return Integer.parseInt(servidor);
    }

}
