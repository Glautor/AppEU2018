package com.example.usuario.telaswendel;

import android.arch.persistence.room.*;


@Entity
public class User {


    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo
    public int matricula;

    @ColumnInfo
    public String nome;

    public User(){}

    public User(int mat, String nome){
        this.id = id;
        this.matricula = mat;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


}

Wendel