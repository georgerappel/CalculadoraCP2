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

    public void setHorario(String arg){ this.horario = arg; }
    public void setDomingo(String arg){ this.domingo = arg; }
    public void setSegunda(String arg){ this.segunda = arg; }
    public void setTerca(String arg){ this.terca = arg; }
    public void setQuarta(String arg){ this.quarta = arg; }
    public void setQuinta(String arg){ this.quinta = arg; }
    public void setSexta(String arg){ this.sexta = arg; }
    public void setSabado(String arg){ this.sabado = arg; }

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
