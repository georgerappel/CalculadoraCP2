package com.testemedia.mediacp2.timetable;

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
    private static final String DATABASE_NAME = "TimeTable";


    private static final String TABLE_LISTA = "Lista";


    private static final String _ID = "ID";
    private static final String KEY_LABEL_DIA = "DiaSemana";
    public static final String KEY_HORARIO = "Horario";
    public static final String KEY_MATERIA = "Materia";
    public static final String KEY_PROFESSOR ="Professor";

    public SqlTimetable(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TIME_TABLE = "CREATE TABLE " + TABLE_LISTA+ "("
                + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_LABEL_DIA + " INTEGER," + KEY_HORARIO
                + " TEXT," + KEY_MATERIA + " TEXT," + KEY_PROFESSOR + " TEXT);";
        sqLiteDatabase.execSQL(CREATE_TIME_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_LISTA);
        onCreate(sqLiteDatabase);

    }

    public void addMateria(Timetable timetable){
        //Abre a DB
        SQLiteDatabase db = this.getWritableDatabase();

        // Declara uma variavel para os valores.
        ContentValues values = new ContentValues();

        //Inserindo as informacoes do parametro na variavel.
        values.put(KEY_HORARIO, timetable.getHorario());
        values.put(KEY_PROFESSOR, timetable.getProfessor());
        values.put(KEY_LABEL_DIA, timetable.getLabelSemana());
        values.put(KEY_MATERIA, timetable.getMateria());

        // Inserindo os valores na tabela.
        db.insertOrThrow(TABLE_LISTA, null, values);

        //Fecha a DB
        db.close();
    }


    public void updateMateria(Timetable timetable) {

        SQLiteDatabase db = this.getWritableDatabase();

        // this is a key value pair holder used by android's SQLite functions
        ContentValues values = new ContentValues();
        values.put(KEY_HORARIO, timetable.getHorario());
        values.put(KEY_PROFESSOR, timetable.getProfessor());
        values.put(KEY_LABEL_DIA, timetable.getLabelSemana());
        values.put(KEY_MATERIA, timetable.getMateria());

        // Atualiza o registro na tabela.
        db.update(TABLE_LISTA, values, "ID =" + timetable.getId(), null);

        db.close(); // Fechando a database.
    }

    public void deleteMateria(Timetable timetable) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // Checa se o nome nao e nulo nem esta vazio.
        if (timetable.getMateria() != null && timetable.getMateria() != "")
            db.delete(TABLE_LISTA, "ID = ?",
                    new String[] { Integer.toString(timetable.getId()) }); // args

        // Fechando a DB
        db.close();
    }

    public int getCount() {
        String countQuery = "SELECT  * FROM " + TABLE_LISTA;
        int count = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        if(cursor != null && !cursor.isClosed()){
            count = cursor.getCount();
            cursor.close();
        }
        return count;
    }

    public Cursor getRows(int label){
        //Abre a DB
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT rowid _id,* FROM "+TABLE_LISTA+ " WHERE " +KEY_LABEL_DIA + " = " + label;
        return db.rawQuery(query,null);
    }

    public static Timetable cursorToTimetable(Cursor cursor, int position){
        cursor.moveToPosition(position);
        Timetable timetable = new Timetable(cursor.getInt(cursor.getColumnIndex(_ID)),
                cursor.getString(cursor.getColumnIndex(KEY_HORARIO)),
                cursor.getString(cursor.getColumnIndex(KEY_PROFESSOR)),
                cursor.getString(cursor.getColumnIndex(KEY_MATERIA)),
                cursor.getInt(cursor.getColumnIndex(KEY_LABEL_DIA)));
        return timetable;
    }


}
