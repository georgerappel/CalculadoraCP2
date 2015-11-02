package com.testemedia.mediacp2;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.analytics.tracking.android.MapBuilder;

/**
 * Created by Lucas on 10/07/2015.
 */
public class AddTimetable extends Activity implements View.OnClickListener {

    EditText materia, professor;
    TimePicker timePicker;
    Button salvar, cancelar;
    SqlTimetable db = new SqlTimetable(this);
    static int diaDaSemana;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_timetable);

        materia = (EditText) findViewById(R.id.adcmateria);
        professor = (EditText)findViewById(R.id.adcprofessor);
        timePicker = (TimePicker)findViewById(R.id.timePicker);
        salvar = (Button) findViewById(R.id.savebutton);
        cancelar = (Button)findViewById(R.id.cancelbutton);
        salvar.setOnClickListener(this);
        cancelar.setOnClickListener(this);

    }

    //dia da semana
    public static void diaSemana(int label_correspondente_dia_da_semana){
        diaDaSemana=label_correspondente_dia_da_semana;
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.savebutton:
                String am_pm = (timePicker.getCurrentHour() < 12) ? "AM" : "PM";
                String horario = timePicker.getCurrentHour()+":"+ timePicker.getCurrentMinute()+am_pm;
                Timetable timetable = new Timetable(horario, professor.getText().toString(), materia.getText().toString(), diaDaSemana);
                db.addMateria(timetable);
                Toast.makeText(getBaseContext(),"Materia adicionada com sucesso!",Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.cancelbutton:
                finish();
                break;
            default:
                break;
        }

    }
}
