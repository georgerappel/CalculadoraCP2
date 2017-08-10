package com.testemedia.mediacp2.timetable;

/**
 * Created by Lucas Braga & George Rappel on 27/05/2015.
 */
public class Timetable {

    private String professor, horario, materia;
    private int labelSemana;
    private int id;

    public Timetable(String horario, String professor, String materia, int labelSemana){
        this.horario = horario;
        this.professor = professor;
        this.materia = materia;
        this.labelSemana = labelSemana;
        id = -1;
    }

    public Timetable(int id, String horario, String professor, String materia, int labelSemana){
        this(horario, professor, materia, labelSemana);
        this.id = id;
    }

    public String getProfessor(){return professor;}
    public String getHorario(){return horario;}
    public String getMateria(){return materia;}
    public int getLabelSemana(){return labelSemana;}
    public int getId(){
        return id;
    }

    public void setProfessor(String professor){this.professor = professor;}
    public void setMateria(String materia){this.materia = materia;}
    public void setHorario(String horario){this.horario = horario;}
    public void setLabelSemana(int labelSemana){this.labelSemana = labelSemana;}

    @Override
    public String toString(){
        return "ID: " + id + " | " + materia + " | Prof. " + professor + " | " + horario;
    }

}
