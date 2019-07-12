package com.testemedia.mediacp2.proeja;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
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
public class ProejaAnual extends AppCompatActivity implements View.OnClickListener {
    EditText nota1cert, nota2cert, resultado;
    Button calcular;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.proejaanual_layout);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException e){
            e.printStackTrace();
        }

        nota1cert = findViewById(R.id.cert1);
        nota2cert = findViewById(R.id.cert2);
        resultado = findViewById(R.id.resultado);
        calcular = findViewById(R.id.calcular);

        calcular.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // Esconde o teclado ao clicar no bot�o caso o teclado ainda esteja
        // aberto.
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(
                getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        float nota1Float, nota2Float, resultadoFloat;

        if ((nota1cert.getText().toString().length() == 0) || (nota2cert.getText().toString().length() == 0))  {
            Toast.makeText(getApplicationContext(), "Prencha todas as notas!", Toast.LENGTH_SHORT).show();
            resultado.setText("");
        }
        else{
            nota1Float = Float.parseFloat(nota1cert.getText().toString());
            nota2Float = Float.parseFloat(nota2cert.getText().toString());

        switch (view.getId()) {
            case R.id.calcular:
                if ((nota1Float > 10) || (nota2Float > 10)) {
                    Toast.makeText(getApplicationContext(), "Ops. Nota inválida.", Toast.LENGTH_SHORT).show();
                    resultado.setText("");
                } else {
                    resultadoFloat = ((nota1Float + nota2Float)) / 2;
                    DecimalFormat forma = new DecimalFormat("0.00"); // Define
                    // a
                    // forma
                    // do
                    // numero
                    // decimal.
                    String formatado = forma.format(resultadoFloat); // Formata para
                    // uma
                    // string
                    if (resultadoFloat < 5) {

                        Toast.makeText(getApplicationContext(), "PFV. Boa sorte.", Toast.LENGTH_LONG).show();
                        resultado.setText(formatado + " pts.");
                    } else {
                        Toast.makeText(getApplicationContext(), "Aprovado. Parabéns!", Toast.LENGTH_LONG).show();
                        resultado.setText(formatado + " pts.");
                    }

                }
                break;
            default:
                break;
            }
        }
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
