package com.testemedia.mediacp2.boletim;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.testemedia.mediacp2.R;

public class Cadastrar extends AppCompatActivity implements View.OnClickListener {

    Button salve;
    EditText nota1, nota2, nota3, nome;
    SqlCadastro db = new SqlCadastro(this); // Objeto de Banco criado por nos.
    Materias materia; // Objeto materia.
    int duration = Toast.LENGTH_LONG; // Duracaoo do Toast

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastrar_layout);

        nome = (EditText) findViewById(R.id.nome);
        nota1 = (EditText) findViewById(R.id.nota1);
        nota2 = (EditText) findViewById(R.id.nota2);
        nota3 = (EditText) findViewById(R.id.nota3);
        salve = (Button) findViewById(R.id.salvar);
        salve.setOnClickListener(this);
    }

    public void onClick(View v) {

        float nota1tri, nota2tri, nota3tri;
        String nomeMat;

        switch (v.getId()) {
            case R.id.salvar:

                if ((nota1.getText().toString().length() == 0)
                        || (nome.getText().toString().trim().length() == 0)) {

                    Context context = getApplicationContext();
                    CharSequence text = "Preencha todos os campos.";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                } else {

                    // Salva o nome e as notas das matérias.
                    nomeMat = nome.getText().toString();
                    try {
                        nota1tri = Float.parseFloat(nota1.getText().toString());
                    } catch (Exception e){
                        nota1tri = 0;
                    }

                    if (nota2.getText().toString().length() == 0)
                        nota2tri = 0;
                    else
                        nota2tri = Float.parseFloat(nota2.getText().toString());

                    if (nota1tri > 10 || nota1tri < 0 || nota2tri > 10
                            || nota2tri < 0) {

                        Context context = getApplicationContext();
                        CharSequence text = "Nota invalida.";
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                    } else {

                        if (nota3.getText().toString().length() == 0) {
                            materia = new Materias(nomeMat, nota1tri, nota2tri);
                            db.addMateria(materia);
                            db.close();
                            Context context = getApplicationContext();
                            CharSequence text = "Disciplina Cadastrada!";
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                            finalizarAtividade();
                        } else { // Se existir nota 3, salva a nota 3 e usa no
                            // construtor.
                            nota3tri = Float.parseFloat(nota3.getText().toString());
                            if (nota3tri < 0 || nota3tri > 10) {

                                Context context = getApplicationContext();
                                CharSequence text = "Nota 3º Tri. invalida.";
                                Toast toast = Toast.makeText(context, text,
                                        duration);
                                toast.show();

                            } else {
                                materia = new Materias(nomeMat, nota1tri, nota2tri,
                                        nota3tri);
                                db.addMateria(materia);
                                db.close();
                                Context context = getApplicationContext();
                                CharSequence text = "Disciplina Cadastrada!";
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                                finalizarAtividade();
                            }
                        }
                    }
                }
                break;
            default:
                break;
        }

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finalizarAtividade();
    }

    private void finalizarAtividade(){
        Intent i = new Intent(this, Boletim.class);
        startActivity(i);
        finish();
    }

}
