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
public class ProejaFinal extends AppCompatActivity implements View.OnClickListener {
    EditText mediaAnual, notaPFV, resultado;
    Button calcular;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.proejafinal_layout);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException e){
            e.printStackTrace();
        }

        mediaAnual = findViewById(R.id.anual);
        notaPFV = findViewById(R.id.notapfv);
        resultado = findViewById(R.id.resultado);
        calcular = findViewById(R.id.calcular);

        calcular.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        // Esconde o teclado ao clicar no bot�o caso o teclado ainda esteja
        // aberto.
        try {
            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(
                    getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            //do nothing
        }

        float anual, pfv, resultadoFloat;

        if ((mediaAnual.getText().toString().length() == 0) || (notaPFV.getText().toString().length() == 0))  {
            Toast.makeText(getApplicationContext(), "Prencha todas as notas!", Toast.LENGTH_SHORT).show();
            resultado.setText("");
        }
        else{
            anual = Float.parseFloat(mediaAnual.getText().toString());
            pfv = Float.parseFloat(notaPFV.getText().toString());

            switch (view.getId()) {
                case R.id.calcular:
                    if ((anual > 10) || (pfv > 10)) {
                        Toast.makeText(getApplicationContext(), "Ops. Nota inválida.", Toast.LENGTH_SHORT).show();
                        resultado.setText("");
                    } else {
                        resultadoFloat = ((3*anual)+(2*pfv))/5;
                        DecimalFormat forma = new DecimalFormat("0.00"); // Define
                        // a
                        // forma
                        // do
                        // numero
                        // decimal.
                        String formatado = forma.format(resultadoFloat); // Formata para
                        // uma
                        // string
                        resultado.setText(formatado + " pts.");
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
