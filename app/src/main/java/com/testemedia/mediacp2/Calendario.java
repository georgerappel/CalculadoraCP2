package com.testemedia.mediacp2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.matabii.dev.scaleimageview.ScaleImageView;

public class Calendario extends AppCompatActivity implements View.OnClickListener{

    static final String STATE_CALENDARIO_RESOURCE = "calendarioResource", STATE_CALENDARIO_VISIVEL = "calendarioVisivel";
    int idCalendario = 0;

    Button calendarioMedio, calendarioProeja;
    LinearLayout layoutBotoes;
	ScaleImageView calendario;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendario_layout);

		calendario = (ScaleImageView)findViewById(R.id.calendario);
        layoutBotoes = (LinearLayout)findViewById(R.id.linear_botoes_calendario);

        calendarioMedio = (Button)findViewById(R.id.calendario_medio);
        calendarioMedio.setOnClickListener(this);
//        calendarioPedrinho = (Button)findViewById(R.id.calendario_pedrinho);
//        calendarioPedrinho.setOnClickListener(this);
        calendarioProeja = (Button)findViewById(R.id.calendario_proeja);
        calendarioProeja.setOnClickListener(this);

        if ( savedInstanceState != null) {
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
//            idCalendario = R.drawable.pedrinho;
//			calendario.setImageResource(R.drawable.pedrinho);
//			calendario.setVisibility(View.VISIBLE);
//            layoutBotoes.setVisibility(View.GONE);
//			break;
		case R.id.calendario_medio:
            idCalendario = R.drawable.anosfinais;
			calendario.setImageResource(R.drawable.anosfinais);
			calendario.setVisibility(View.VISIBLE);
            layoutBotoes.setVisibility(View.GONE);
			break;
		case R.id.calendario_proeja:
            idCalendario = R.drawable.proeja;
            calendario.setImageResource(R.drawable.proeja);
            calendario.setVisibility(View.VISIBLE);
            layoutBotoes.setVisibility(View.GONE);
            break;
		}
	}
	
	@Override
	public void onBackPressed() {
		if(calendario.getVisibility() == View.VISIBLE){
            calendario.setVisibility(View.GONE);
            layoutBotoes.setVisibility(View.VISIBLE);
            idCalendario = 0;
		} else if(calendario.getVisibility() == View.GONE){
			super.onBackPressed();
		}
	}
}