package com.testemedia.mediacp2;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Created by George Rappel & Lucas Braga on 15/06/2015.
 */
public class MenuTimetable extends Activity implements View.OnClickListener {

    int id_tupla = 0; // ---- Controla os ids dos TextViews e o loop durante o preenchimento

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menutimetable_layout);

        final String _ID = "ID";

        String KEY_HORARIO = "Horario";
        String KEY_DOMINGO = "Domingo";
        String KEY_SEGUNDA = "Segunda";
        String KEY_TERCA = "Terca";
        String KEY_QUARTA = "Quarta";
        String KEY_QUINTA = "Quinta";
        String KEY_SEXTA = "Sexta";
        String KEY_SABADO = "Sabado";

        // ---- TableLayout da atividade onde serao colocadas as linhas de horarios
        TableLayout tl = (TableLayout) findViewById(R.id.timetable);

        // ---- Inicializando e lendo o banco de dados.
        SqlTimetable helper = new SqlTimetable(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        TableRow tr;

        // ---- Faz uma consulta para pegar as linhas
        String selection = "SELECT * FROM GradeHoraria";
        Cursor cursor = db.rawQuery(selection, null);

        if (cursor.moveToFirst())

        {
            do {
                if (cursor.getString(cursor.getColumnIndex(KEY_HORARIO)) != null) {

                    id_tupla = cursor.getInt(cursor.getColumnIndex(_ID));

                    tr = new TableRow(this);
                    tr.setId(id_tupla);

                    tr.addView(barra_divisoria());
                    tr.addView(tv_escreve_disciplina(cursor.getString(cursor.getColumnIndex(KEY_HORARIO))));
                    tr.addView(barra_divisoria());
                    tr.addView(tv_escreve_disciplina(cursor.getString(cursor.getColumnIndex(KEY_DOMINGO))));
                    tr.addView(barra_divisoria());
                    tr.addView(tv_escreve_disciplina(cursor.getString(cursor.getColumnIndex(KEY_SEGUNDA))));
                    tr.addView(barra_divisoria());
                    tr.addView(tv_escreve_disciplina(cursor.getString(cursor.getColumnIndex(KEY_TERCA))));
                    tr.addView(barra_divisoria());
                    tr.addView(tv_escreve_disciplina(cursor.getString(cursor.getColumnIndex(KEY_QUARTA))));
                    tr.addView(barra_divisoria());
                    tr.addView(tv_escreve_disciplina(cursor.getString(cursor.getColumnIndex(KEY_QUINTA))));
                    tr.addView(barra_divisoria());
                    tr.addView(tv_escreve_disciplina(cursor.getString(cursor.getColumnIndex(KEY_SEXTA))));
                    tr.addView(barra_divisoria());
                    tr.addView(tv_escreve_disciplina(cursor.getString(cursor.getColumnIndex(KEY_SABADO))));
                    tr.addView(barra_divisoria());

                    tl.addView(tr);
                    tl.addView(linha_divisoria());

                    id_tupla++;
                }
            } while (cursor.moveToNext()); // Move cursor para a proxima tupla, enquanto houver.
        }

        cursor.close();
        db.close();
        helper.close();
    }

    @Override
    public void onClick(View view) {

    }

    public View linha_divisoria() {
        View v = new View(this);
        v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 1));
        v.setBackgroundColor(Color.rgb(170, 170, 170));
        return v;
    }

    public View barra_divisoria() {
        View v = new View(this);
        v.setLayoutParams(new TableRow.LayoutParams(2, TableRow.LayoutParams.MATCH_PARENT));
        v.setBackgroundColor(Color.rgb(170, 170, 170));
        return v;
    }

    public TextView tv_escreve_disciplina(String disciplina){
        TextView tv = new TextView(this);
        tv.setId(id_tupla);
        tv.setText(disciplina);
        tv.setTextColor(Color.BLACK);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(19);
        return tv;
    }
}
