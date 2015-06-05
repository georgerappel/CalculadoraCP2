package com.testemedia.mediacp2;

/**
 * Created by Lucas Braga & George Rappel on 27/05/2015.
 */
public class Timetable {

    String horario, domingo, segunda, terca, quarta, quinta, sexta, sabado;

    public Timetable(String horario){
        this.horario = horario;
        setDiasVazios();
    }

    public Timetable(){
        this.horario = "";
        setDiasVazios();
    }

    public String getHorario(){ return horario; }
    public String getDomingo(){ return domingo; }
    public String getSegunda(){ return segunda; }
    public String getTerca(){ return terca; }
    public String getQuarta(){ return quarta; }
    public String getQuinta(){ return quinta; }
    public String getSexta(){ return sexta; }
    public String getSabado(){ return sabado; }

    public void setHorario(String text){ this.horario = text; }
    public void setDomingo(String text){ this.domingo = text; }
    public void setSegunda(String text){ this.segunda = text; }
    public void setTerca(String text){ this.terca = text; }
    public void setQuarta(String text){ this.quarta = text; }
    public void setQuinta(String text){ this.quinta = text; }
    public void setSexta(String text){ this.sexta = text; }
    public void setSabado(String text){ this.sabado = text; }

    public void setDiasVazios(){
        this.domingo = "";
        this.segunda = "";
        this.terca = "";
        this.quarta = "";
        this.quinta = "";
        this.sexta = "";
        this.sabado = "";
    }


}
