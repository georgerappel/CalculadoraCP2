package com.testemedia.mediacp2;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * Created by Lucas on 02/11/2015.
 */
public class EditarTimetable extends Activity implements View.OnClickListener {
    EditText materia, professor;
    TimePicker timePicker;
    Button salvar, cancelar;
    SqlTimetable db = new SqlTimetable(this);
    static int diaDaSemana, id;
    static String mat, prof, hr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_timetable);

        if(getIntent().getExtras().get("professor") != null) {
            prof = getIntent().getExtras().getString("professor");
        }
        if(getIntent().getExtras().get("horario") != null){
            hr = getIntent().getExtras().getString("horario");
        }

        if(getIntent().getExtras().get("materia") != null){
            mat = getIntent().getExtras().getString("materia");
        }

        if(getIntent().getExtras().get("dia") != null){
            diaDaSemana = getIntent().getExtras().getInt("dia");
        }

        if(getIntent().getExtras().get("id") != null){
            id = getIntent().getExtras().getInt("id");
        }

        materia = (EditText) findViewById(R.id.adcmateria);
        professor = (EditText)findViewById(R.id.adcprofessor);
        timePicker = (TimePicker)findViewById(R.id.timePicker);
        salvar = (Button) findViewById(R.id.savebutton);
        cancelar = (Button)findViewById(R.id.cancelbutton);
        salvar.setOnClickListener(this);
        cancelar.setOnClickListener(this);

        materia.setText(mat);
        professor.setText(prof);
        timePicker.setCurrentHour(Integer.parseInt(hr.substring(0, 1)));
        timePicker.setCurrentMinute(Integer.parseInt(hr.substring(3,4)));

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.savebutton:
                String minutos = timePicker.getCurrentMinute().toString();
                String hora = timePicker.getCurrentHour().toString();
                if(minutos.length() == 1)
                    minutos = "0" + minutos;
                if(hora.length() == 1)
                    hora = "0" + hora;
                String horario = hora + ":" + minutos;
                if(professor.getTextSize() == 0 || materia.getTextSize() == 0 ) {
                    Toast.makeText(getBaseContext(), "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
                } else {
                    Timetable timetable = new Timetable(horario, professor.getText().toString(), materia.getText().toString(), diaDaSemana);
                    db.updateMateria(timetable,id);
                    Toast.makeText(getBaseContext(), "Materia atualizada com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            case R.id.cancelbutton:
                finish();
                break;
            default:
                break;
        }

    }
}

