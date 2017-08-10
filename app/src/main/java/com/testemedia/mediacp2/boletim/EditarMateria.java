package com.testemedia.mediacp2.boletim;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;
import com.testemedia.mediacp2.R;

public class EditarMateria extends Activity implements View.OnClickListener {

	Button salvar;
	EditText nota1, nota2, nota3, nome, notaPFV;
	SqlCadastro db = new SqlCadastro(this); // Objeto de Banco criado por n�s.
	Materias materia;
	int id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editarmateria_layout);
		Intent intent = getIntent();
		id = intent.getIntExtra("id", -1);
		if (id == -1) {
			Context context = getApplicationContext();
			CharSequence ToastText = "Isso não é uma disciplina.";
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(context, ToastText, duration);
			toast.show();
			Intent intentError = new Intent(this, EditarLista.class);
			finish();
			startActivity(intentError);
		} else {
			materia = db.buscarId(id);
			nome = (EditText) findViewById(R.id.nome);
			nota1 = (EditText) findViewById(R.id.nota1);
			nota2 = (EditText) findViewById(R.id.nota2);
			nota3 = (EditText) findViewById(R.id.nota3);
			notaPFV = (EditText) findViewById(R.id.notaPFV);
			salvar = (Button) findViewById(R.id.salvar);
			salvar.setOnClickListener(this);

			nome.setText(materia.getNome());
			nota1.setText(Float.toString(materia.getNota1()));
			nota2.setText(Float.toString(materia.getNota2()));
			nota3.setText(Float.toString(materia.getNota3()));
			notaPFV.setText(Float.toString(materia.getPFV()));
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		float nota1_, nota2_, nota3_, notaPFV_;
		String nome_;

		Context context = getApplicationContext();
		int duration = Toast.LENGTH_LONG;
		
		EasyTracker tracker = EasyTracker.getInstance(EditarMateria.this);
		tracker.send(MapBuilder.createEvent("Boletim", "Editar",
				"SalvarEdicaoMateria", null).build());

		switch (v.getId()) {
		case R.id.salvar:
			if ((nota1.getText().toString().length() == 0)
					|| (nota2.getText().toString().length() == 0)
					|| (nota3.getText().toString().length() == 0)
					|| (nome.getText().toString().length() == 0)
					|| (notaPFV.getText().toString().length() == 0)) {

				CharSequence text = "ERRO! Preencha todos os campos.";
				Toast toast = Toast.makeText(context, text, duration);
				toast.show();

			} else {
				nota1_ = Float.parseFloat(nota1.getText().toString());
				nota2_ = Float.parseFloat(nota2.getText().toString());
				nota3_ = Float.parseFloat(nota3.getText().toString());
				notaPFV_ = Float.parseFloat(notaPFV.getText().toString());
				nome_ = nome.getText().toString();

				if ((nota1_ > 10) || (nota1_ < 0) || (nota2_ > 10)
						|| (nota2_ < 0) || (nota3_ > 10) || (nota3_ < 0)
						|| (notaPFV_ > 10) || (notaPFV_ < 0)) {
					CharSequence text = "Nota inválida.";
					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
				} else {
					Materias materia = new Materias(nome_, nota1_, nota2_,
							nota3_, notaPFV_);
					db.atualizarMateria(materia, id);
					db.close();
					
					CharSequence text = "Disciplina atualizada!";
					Toast toast = Toast.makeText(context, text, duration);
					toast.show();

					Intent Lista = new Intent(this, EditarLista.class);
					Lista.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					finish();
					startActivity(Lista);
				}
			}
			break;
		default:
			break;
		}

	}

	@Override
	public void onStart() {
		super.onStart();
		EasyTracker.getInstance(this).activityStart(this);
	}
}
