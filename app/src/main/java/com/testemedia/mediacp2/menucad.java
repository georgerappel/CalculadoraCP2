package com.testemedia.mediacp2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;

public class menucad extends Activity implements View.OnClickListener{	
	Button boletim, cadastrar, instrucoes, editar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menucad_layout);
		boletim = (Button)findViewById(R.id.boletim);
		cadastrar = (Button)findViewById(R.id.cadastrar);
		instrucoes = (Button)findViewById(R.id.instrucoes);
		editar = (Button)findViewById(R.id.editar);
		
		boletim.setOnClickListener(this);
		cadastrar.setOnClickListener(this);
		instrucoes.setOnClickListener(this);
		editar.setOnClickListener(this);

	}

	@Override
	public void onStart() {
		super.onStart();
		EasyTracker.getInstance(this).activityStart(this);
	}
	
	public void onClick(View v) {

		Intent cadastro = new Intent(this, Cadastrar.class);
		Intent boletim = new Intent(this, Boletim.class);
		Intent inst = new Intent(this, Instrucoes.class);
		Intent editar = new Intent(this, EditarLista.class);
		
		EasyTracker tracker = EasyTracker.getInstance(menucad.this);
		
			switch (v.getId()) {
			case R.id.boletim:
				tracker.send(MapBuilder.createEvent("Botoes", "Boletim-Geral", "Visualizar Boletim", null).build());
				this.startActivity(boletim);
				break;
			case R.id.cadastrar:
				tracker.send(MapBuilder.createEvent("Botoes", "Boletim-Geral", "Acesso Cadastro Materia", null).build());
				this.startActivity(cadastro);
				break;
			case R.id.instrucoes:				
				tracker.send(MapBuilder.createEvent("Botoes", "Boletim-Geral", "Ver Instrucoes", null).build());
                this.startActivity(inst);
				break;
			case R.id.editar:				
				tracker.send(MapBuilder.createEvent("Botoes", "Boletim-Geral", "Editar ou Remover Materias", null).build());
                this.startActivity(editar);
				break;
			default:
				break;
			}
			
	}

	
	@Override
    public void onStop() {
        super.onStop();
        getWindow().setWindowAnimations(android.R.anim.slide_out_right);
        EasyTracker.getInstance(this).activityStop(this);
    }

}


