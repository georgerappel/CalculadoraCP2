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

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.testemedia.mediacp2.R;

import java.text.DecimalFormat;

/**
 * Created by Lucas on 03/02/2015.
 */
public class Nota2cert extends AppCompatActivity implements View.OnClickListener {

    EditText nota1cert, resultado;
    Button calcular;
    private InterstitialAd interstitial;
    final String ID = "ca-app-pub-3567961859053683/7232838256";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.proeja2cert_layout);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException e){
            e.printStackTrace();
        }

        AdView adView = (AdView) this.findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        nota1cert = (EditText)findViewById(R.id.cert1);
        resultado = (EditText)findViewById(R.id.resultado);
        calcular = (Button)findViewById(R.id.calcular);
        calcular.setOnClickListener(this);

        // Criar o anúncio intersticial.
        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId(ID);
        AdRequest adRequest2 = new AdRequest.Builder().build();
        interstitial.loadAd(adRequest2);

    }

    @Override
    public void onStop() {
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
        super.onStop();
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
