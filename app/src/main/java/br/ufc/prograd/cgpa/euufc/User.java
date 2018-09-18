package br.ufc.prograd.cgpa.euufc;

import android.arch.persistence.room.*;


@Entity
public class User {


    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo
    private int matricula;

    @ColumnInfo
    private String cpf;

    @ColumnInfo
    private String nome;

    @ColumnInfo
    private int horas;

    @ColumnInfo
    private int minutos;

    @ColumnInfo
    private boolean infoCheckout;

    @ColumnInfo
    private int lastCheckId;

    public User(){}

    public User(int mat, String nome, String cpf){
        this.matricula = mat;
        this.nome = nome;
        this.cpf = cpf;
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

    public String getCpf() { return cpf; }

    public void setCpf(String cpf) { this.cpf = cpf; }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    public int getMinutos() {
        return minutos;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    public boolean getInfoCheckout() {
        return infoCheckout;
    }

    public void setInfoCheckout(boolean infoCheckout) {
        this.infoCheckout = infoCheckout;
    }

    public int getLastCheckId() {
        return lastCheckId;
    }

    public void setLastCheckId(int lastCheckId) {
        this.lastCheckId = lastCheckId;
    }

}