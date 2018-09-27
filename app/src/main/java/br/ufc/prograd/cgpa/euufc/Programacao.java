package br.ufc.prograd.cgpa.euufc;

import android.arch.persistence.room.*;

@Entity
public class Programacao {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo
    private String tema;

    @ColumnInfo
    private String atividades;

    @ColumnInfo
    private String horario;

    @ColumnInfo
    private String dataEvento;

    @ColumnInfo
    private String palestrante;

    @ColumnInfo
    private String mediador;

    @ColumnInfo
    private String local;

    public Programacao(){}

    public Programacao(String tema, String atividades, String horario, String dataEvento, String palestrante, String mediador, String local){
        this.tema = tema;
        this.atividades = atividades;
        this.horario = horario;
        this.dataEvento = dataEvento;
        this.palestrante = palestrante;
        this.mediador = mediador;
        this.local = local;
    }

    public int getId(){ return id; }

    public void setId(int id){ this.id = id; }

    public String getTema(){ return tema; }

    public void setTema(String tema){ this.tema = tema; }

    public String getAtividades(){ return atividades; }

    public void setAtividades(String atividades){ this.atividades = atividades; }

    public String getHorario(){ return horario; }

    public void setHorario(String horario){ this.horario = horario; }

    public String getDataEvento(){ return dataEvento; }

    public void setDataEvento(String dataEvento){ this.dataEvento = dataEvento; }

    public String getPalestrante(){ return palestrante; }

    public void setPalestrante(String palestrante){ this.palestrante = palestrante; }

    public String getMediador(){ return mediador; }

    public void setMediador(String mediador){ this.mediador = mediador; }

    public String getLocal(){ return local; }

    public void setLocal(String local){ this.local = local; }

}
