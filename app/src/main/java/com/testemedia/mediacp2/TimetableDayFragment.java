package com.testemedia.mediacp2;

/**
 * Created by George Rappel on 04/11/2015.
 */

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * A dummy fragment representing a section of the app, but that simply
 * displays dummy text.
 */
public class TimetableDayFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    public static final String ARG_SECTION_NUMBER = "section_number";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Pega os argumentos adicionados no fragmento para saber o dia da semana.
        final int posicao = getArguments().getInt(ARG_SECTION_NUMBER);
        View rootView;

        // handler is a SQLiteOpenHelper class connecting to SQLite
        SqlTimetable handler = new SqlTimetable(getActivity());
        // Get access to the underlying writeable database
        SQLiteDatabase db = handler.getWritableDatabase();
        // Query pra consulta no banco de dados ordenado por horário de acordo com o dia da semana
        String query = "SELECT rowid _id,* FROM Lista WHERE DiaSemana = " + posicao + " ORDER BY " + SqlTimetable.KEY_HORARIO + " ASC";
        Cursor timeTableCursor = db.rawQuery(query, null);
        // Define o adapter com o Cursor
        TimeTableCursorAdapter todoAdapter = new TimeTableCursorAdapter(getActivity(), timeTableCursor, 0);
        // Define o layout da ListView para este fragmento
        rootView = inflater.inflate(R.layout.timetable_listview, container, false);
        if (timeTableCursor.getCount() == 0) {
            // Se for vazio, deixar vazio?
        } else {
            final ListView lista = (ListView) rootView.findViewById(R.id.list_timetable);
            // Adiciona o adapter a ListView - Populando a lista.
            lista.setAdapter(todoAdapter);
            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // A funcao "getItemAtPosition" na verdade retorna um cursor com todos os dados
                    // da lista. Então, usa-se a função "cursorToTimetable" passando o cursor e a
                    // posição do elemento que deseja. Ele retorna o elemento.
                    Timetable elementoClicado = SqlTimetable.cursorToTimetable((Cursor) lista.getItemAtPosition(position), position);
                    Log.e("Elemento: ", elementoClicado.toString());
                }
            });
        }
        db.close();
        return rootView;
    }
}