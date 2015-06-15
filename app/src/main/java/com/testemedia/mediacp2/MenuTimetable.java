package com.testemedia.mediacp2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;

/**
 * Created by Lucas on 09/12/2014.
 */
public class MenuTimetable extends Activity implements View.OnClickListener {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menutimetable_layout);

        TableLayout tl = (TableLayout) findViewById(R.id.timetable);

        /*
         ---- PARA ADICIONAR LINHA ENTRE AS TABELAS
        View v = new View(this);
        v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 1));
        v.setBackgroundColor(Color.rgb(51, 51, 51));
        tr.addView(mTvDate);
        tr.addView(mTvResult);

        tl.addView(tr);
        tl.addView(v);


         */


    }

    @Override
    public void onClick(View view) {

    }
}
