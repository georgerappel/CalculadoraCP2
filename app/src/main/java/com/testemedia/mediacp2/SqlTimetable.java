package com.testemedia.mediacp2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Lucas Braga & George Rappel on 27/05/2015.
 */
public class SqlTimetable extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "timeTable";


    private static final String TABLE_GRADE_HORARIA = "GradeHoraria";


    private static final String _ID = "ID";
    private static final String KEY_HORARIO = "Horario";
    private static final String KEY_DOMINGO = "Domingo";
    private static final String KEY_SEGUNDA = "Segunda";
    private static final String KEY_TERCA = "Terça";
    private static final String KEY_QUARTA = "Quarta";
    private static final String KEY_QUINTA = "Quinta";
    private static final String KEY_SEXTA = "Sexta";
    private static final String KEY_SABADO = "Sábado";

    public SqlTimetable(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TIME_TABLE = "CREATE TABLE " + TABLE_GRADE_HORARIA+ "("
                + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_HORARIO
                + " TEXT," + KEY_DOMINGO + " TEXT," + KEY_SEGUNDA + " TEXT,"
                + KEY_TERCA + " TEXT," + KEY_QUARTA + " TEXT," + KEY_QUINTA
                + " TEXT," + KEY_SEXTA + " TEXT, " + KEY_SABADO + " TEXT)";
        sqLiteDatabase.execSQL(CREATE_TIME_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_GRADE_HORARIA);
        onCreate(sqLiteDatabase);

    }

    public void addHorario(Timetable timetable) {
        //Abre a DB
        SQLiteDatabase db = this.getWritableDatabase();
        // Declara uma variavel para os valores.
        ContentValues values = new ContentValues();

        //Inserindo as informacoes do parametro na variavel.
        values.put(KEY_HORARIO, timetable.getHorario());
        values.put(KEY_DOMINGO, timetable.getDomingo());
        values.put(KEY_SEGUNDA, timetable.getSegunda());
        values.put(KEY_TERCA, timetable.getTerca());
        values.put(KEY_QUARTA, timetable.getQuarta());
        values.put(KEY_QUINTA, timetable.getQuinta());
        values.put(KEY_SEXTA, timetable.getSexta());
        values.put(KEY_SABADO, timetable.getSabado());

        // Inserindo os valores na tabela.
        db.insertOrThrow(TABLE_GRADE_HORARIA, null, values);

        //Fecha a DB
        db.close();
    }

    public int encontrarId(String horario) {
        SQLiteDatabase db = this.getWritableDatabase();
        int id;
        // Define e faz a selecao na tabela.
        String selection = "SELECT " + _ID + " FROM " + TABLE_GRADE_HORARIA + " WHERE Horario = '" + horario + "'";
        Cursor cursor = db.rawQuery(selection, null);
        // Move o cursor para o primeiro (e unico) resultado.
        if (cursor.moveToFirst()) {
            // Pega o valor do ID e retorna-o.
            id = cursor.getInt(cursor.getColumnIndex(_ID));
            db.close();
            cursor.close();
            return id;
        } else {
            // Caso nao retorne resultados, o valor sera -1.
            db.close();
            cursor.close();
            return -1;
        }
    }



}
