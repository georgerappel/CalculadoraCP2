package com.testemedia.mediacp2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.matabii.dev.scaleimageview.ScaleImageView;

public class Calendario extends AppCompatActivity implements View.OnClickListener{

    static final String STATE_CALENDARIO_RESOURCE = "calendarioResource", STATE_CALENDARIO_VISIVEL = "calendarioVisivel";
    int idCalendario = 0;

//    Button calendarioMedio, calendarioProeja;
    LinearLayout layoutBotoes;
	ScaleImageView calendario;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendario_layout);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException e){
            e.printStackTrace();
        }

		calendario = (ScaleImageView)findViewById(R.id.calendario);
        layoutBotoes = (LinearLayout)findViewById(R.id.linear_botoes_calendario);

        // Mostra direto porque só tem um calendário
        mostraCalendario(R.drawable.calendario);

        //CÓDIGO ABAIXO COMENTÁDO ENQUANTO HOUVER APENAS UM CALENDÁRIO ÚNICO
//        calendarioMedio = (Button)findViewById(R.id.calendario_medio);
//        calendarioMedio.setOnClickListener(this);
//
//        calendarioPedrinho = (Button)findViewById(R.id.calendario_pedrinho);
//        calendarioPedrinho.setOnClickListener(this);
//        calendarioProeja = (Button)findViewById(R.id.calendario_proeja);
//        calendarioProeja.setOnClickListener(this);

        if (savedInstanceState != null) {
            int calendarioResourceId = savedInstanceState.getInt(STATE_CALENDARIO_RESOURCE);
            int calendarioVisivel = savedInstanceState.getInt(STATE_CALENDARIO_VISIVEL);
            if (calendarioResourceId != 0 && calendarioVisivel != 0) {
                if (calendarioVisivel - 1 == View.VISIBLE) {
                    idCalendario = calendarioResourceId;
                    calendario.setImageResource(calendarioResourceId);
                    layoutBotoes.setVisibility(View.GONE);
                    calendario.setVisibility(View.VISIBLE);
                }
            }
        } else {
            // Mostra direto porque só tem um calendário
            mostraCalendario(R.drawable.calendario);
        }
	}

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(STATE_CALENDARIO_RESOURCE, idCalendario);
        savedInstanceState.putInt(STATE_CALENDARIO_VISIVEL, calendario.getVisibility() + 1);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }
	
	@Override
	public void onClick(View v){
		switch(v.getId()){
//		case R.id.calendario_pedrinho:
//            mostraCalendario(R.drawable.pedrinho);
//			break;
//		case R.id.calendario_medio:
//		    mostraCalendario(R.drawable.calendario);
//			break;
//		case R.id.calendario_proeja:
//            mostraCalendario(R.drawable.proeja);
//            break;
		}
	}

//	CÓDIGO COMENTÁDO EQUANTO HOUVER APENAS UM CALENDÁRIO ÚNICO
//
//	@Override
//	public void onBackPressed() {
//		if(calendario.getVisibility() == View.VISIBLE){
//            calendario.setVisibility(View.GONE);
//            layoutBotoes.setVisibility(View.VISIBLE);
//            idCalendario = 0;
//		} else if(calendario.getVisibility() == View.GONE){
//			super.onBackPressed();
//		}
//	}

	public void mostraCalendario(int resource){
        idCalendario = resource;
        calendario.setImageResource(resource);
        calendario.setVisibility(View.VISIBLE);
        layoutBotoes.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}