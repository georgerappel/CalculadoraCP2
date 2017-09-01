package com.testemedia.mediacp2.timetable;

/**
 * Created by George Rappel on 04/11/2015.
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.testemedia.mediacp2.R;

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

    //variavel que serve pra guardar o item selecionado na lista do tipo timetable
    public static Timetable selected;

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
        // Query pra consulta no banco de dados ordenado por horrio de acordo com o dia da semana
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

            todoAdapter.notifyDataSetChanged();

            lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                               int pos, long id) {
                    // TODO Auto-generated method stub
                    // A funcao "getItemAtPosition" na verdade retorna um cursor com todos os dados
                    // da lista. Ento, usa-se a funo "cursorToTimetable" passando o cursor e a
                    // posio do elemento que deseja. Ele retorna o elemento.
                    Timetable elementoClicado = SqlTimetable.cursorToTimetable((Cursor) lista.getItemAtPosition(pos), pos);
                    //mundando o valor da variavel "selected" para a instncia da classe timetable selecionado
                    //ou seja, a varivel SELECTED passa a ter o valor do elemento clicado.
                    selected(elementoClicado);
                    Log.v("long clicked", "pos: " + elementoClicado.toString());
                    registerForContextMenu(lista);
                    return false;
                }
            });
        }
        db.close();
        return rootView;
    }

    //muda a varivel selected para uma instncia da classe timetable que contm o elemento selecionado
    public void selected(Timetable materia){
        selected = materia;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.list_timetable) {
            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.timetable_longpress, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()) {
            case R.id.edit:
                Intent intent = new Intent(getActivity(), EditarTimetable.class);
                intent.putExtra("horario",selected.getHorario());
                intent.putExtra("professor",selected.getProfessor());
                intent.putExtra("materia",selected.getMateria());
                intent.putExtra("dia",selected.getLabelSemana());
                intent.putExtra("id",selected.getId());
                getActivity().startActivity(intent);
                return true;
            case R.id.delete:

                new AlertDialog.Builder(getActivity())
                        .setTitle("Excluir materia")
                        .setMessage("Tem certeza que voce deseja excluir essa materia?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                // remove stuff here
                                SqlTimetable db = new SqlTimetable(getActivity());
                                //passamos como parmetro a varivel selected que contm o valor do item na lista selecionado
                                db.deleteMateria(selected);
                                db.close();
                                //atualiza a pgina
                                getActivity().recreate();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


}