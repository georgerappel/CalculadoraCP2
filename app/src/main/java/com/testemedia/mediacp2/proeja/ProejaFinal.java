package com.testemedia.mediacp2.proeja;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.testemedia.mediacp2.R;

import java.text.DecimalFormat;

/**
 * Created by Lucas on 03/02/2015.
 */
public class ProejaFinal extends Activity implements View.OnClickListener {
    EditText mediaAnual, notaPFV, resultado;
    Button calcular;
    private InterstitialAd interstitial;
    final String ID = "ca-app-pub-3567961859053683/7232838256";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.proejafinal_layout);


        AdView adView = (AdView) this.findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        // Criar o anúncio intersticial.
        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId(ID);
        AdRequest adRequest2 = new AdRequest.Builder().build();
        interstitial.loadAd(adRequest2);


        mediaAnual = (EditText)findViewById(R.id.anual);
        notaPFV = (EditText)findViewById(R.id.notapfv);
        resultado = (EditText)findViewById(R.id.resultado);
        calcular = (Button)findViewById(R.id.calcular);


        calcular.setOnClickListener(this);


    }

    @Override
    public void onStop() {
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
        super.onStop();
        EasyTracker.getInstance(this).activityStop(this);
    }

    @Override
    public void onClick(View view) {

        // Esconde o teclado ao clicar no bot�o caso o teclado ainda esteja
        // aberto.
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(
                getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

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
}
