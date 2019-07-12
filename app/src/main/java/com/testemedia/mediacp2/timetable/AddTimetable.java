package com.testemedia.mediacp2.timetable;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.testemedia.mediacp2.R;

/**
 * Created by Lucas  Braga and George Rappel on 10/07/2015.
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

        if(getIntent().getExtras().get("dia") != null){
            diaDaSemana = getIntent().getExtras().getInt("dia");
            Log.e("AddTimetable", "Botou dia " + diaDaSemana);
        } else {
            // No tem dia da semana? Jogar um erro? Toast + finish()?
            diaDaSemana = -1;
        }

        materia = findViewById(R.id.adcmateria);
        professor = findViewById(R.id.adcprofessor);
        timePicker = findViewById(R.id.timePicker);
        salvar = findViewById(R.id.savebutton);
        cancelar = findViewById(R.id.cancelbutton);
        salvar.setOnClickListener(this);
        cancelar.setOnClickListener(this);
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
                if(professor.getText().toString().length() == 0 || materia.getText().toString().length() == 0 ) {
                    Toast.makeText(getBaseContext(), "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
                } else {
                    Timetable timetable = new Timetable(horario, professor.getText().toString(), materia.getText().toString(), diaDaSemana);
                    db.addMateria(timetable);
                    Toast.makeText(getBaseContext(), "Materia adicionada com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                    db.close();
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
