package com.example.usuario.telaswendel;

import android.arch.persistence.room.*;

import java.util.Date;

@Entity(foreignKeys = @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "user_id"),
indices = {@Index(value = {"id_check","user_id"})})
public class Check {
    @PrimaryKey(autoGenerate = true)
    private int id_check;

    private int user_id;

    @ColumnInfo
    private Date dHourIn;

    @ColumnInfo
    private Date dhourOut;

    @ColumnInfo
    private boolean atServidor;

    public Check(){}

    public Check(int Uid, Date dHI, boolean atServidor){
        this.user_id = Uid;
        this.dHourIn = dHI;
        this.atServidor = atServidor;
    }

    public int getId_check() {
        return id_check;
    }

    public void setId_check(int id_check) {
        this.id_check = id_check;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Date getDHourIn() {
        return dHourIn;
    }

    public void setDHourIn(Date dHourIn) {
        this.dHourIn = dHourIn;
    }

    public Date getDhourOut() {
        return dhourOut;
    }

    public void setDhourOut(Date dhourOut) {
        this.dhourOut = dhourOut;
    }

    public boolean isAtServidor() {
        return atServidor;
    }

    public void setAtServidor(boolean atServidor) {
        this.atServidor = atServidor;
    }
}
