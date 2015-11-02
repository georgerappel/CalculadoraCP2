package com.testemedia.mediacp2;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Lucas on 03/09/2015.
 */
public class TimeTableCursorAdapter extends CursorAdapter {

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public TimeTableCursorAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.fragment_main_dummy, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView tthorario = (TextView) view.findViewById(R.id.horario);
        TextView ttprofessor = (TextView) view.findViewById(R.id.professor);
        TextView ttmateria = (TextView) view.findViewById(R.id.materia);
        // Extract properties from cursor
        String horario = cursor.getString(cursor.getColumnIndexOrThrow("Horario"));
        String professor = cursor.getString(cursor.getColumnIndexOrThrow("Professor"));
        String materia = cursor.getString(cursor.getColumnIndexOrThrow("Materia"));
        // Populate fields with extracted properties
        tthorario.setText(horario);
        ttprofessor.setText(professor);
        ttmateria.setText(materia);

    }
}
