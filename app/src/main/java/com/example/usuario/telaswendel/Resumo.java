package com.example.usuario.telaswendel;

import android.arch.persistence.room.*;

@Entity
public class Resumo {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo
    private String titulo;

    @ColumnInfo
    private String nome;

    @ColumnInfo
    private String horas;

    @ColumnInfo
    private String minutos;

    @ColumnInfo
    private String dia;

    public Resumo(){}

    public Resumo(String tit, String bol, String h, String m, String d){
        this.titulo = tit;
        this.nome = bol;
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

    public String getNome() {
        return nome;
    }

    public void setNome(String bolsista) {
        this.nome = bolsista;
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
}
