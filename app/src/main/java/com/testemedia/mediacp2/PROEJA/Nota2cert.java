package com.testemedia.mediacp2.PROEJA;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.testemedia.mediacp2.R;

import java.text.DecimalFormat;

/**
 * Created by Lucas on 03/02/2015.
 */
public class Nota2cert extends Activity implements View.OnClickListener {

    EditText nota1cert, resultado;
    Button calcular;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.proeja2cert_layout);

        nota1cert = (EditText)findViewById(R.id.cert1);
        resultado = (EditText)findViewById(R.id.resultado);
        calcular = (Button)findViewById(R.id.calcular);
        calcular.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        // Esconde o teclado ao clicar no bot�o caso o teclado ainda esteja
        // aberto.
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(
                getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);



        float nota1Float, resultadoFloat;

        if ((nota1cert.getText().toString().length() == 0)) {
            Toast.makeText(getApplicationContext(), "Prencha todas as notas!", Toast.LENGTH_SHORT).show();
            resultado.setText("");
        }
        else{

            nota1Float = Float.parseFloat(nota1cert.getText().toString());

            switch (v.getId()) {
                case R.id.calcular:
                    if ((nota1Float > 10)) {
                        Toast.makeText(getApplicationContext(), "Ops. Nota inválida.", Toast.LENGTH_SHORT).show();
                        resultado.setText("");
                    } else {
                        resultadoFloat = 10 - nota1Float;
                        Toast.makeText(getApplicationContext(), "Boa prova.", Toast.LENGTH_SHORT).show();

                        DecimalFormat forma = new DecimalFormat("0.00"); // Define a
                        // forma
                        // do
                        // numero
                        // decimal.
                        String formatado = forma.format(resultadoFloat); // Formata para uma
                        // string

                        resultado.setText(formatado + " pts."); // Mostra a nota
                        // estando ou n�o
                        // aprovado.
                    }
                    break;
                default:
                    break;
            }
        }

    }
}
