package com.testemedia.mediacp2;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.text.DecimalFormat;

/**
 * Editado by George on 12/04/2015.
 */
public class CalcularMedias extends AppCompatActivity implements View.OnClickListener {

    EditText nota1Tri, nota2Tri, nota3Tri, resultado3Tri, resultadoMA, resultadoPFV;
    Button terceiro, ma;
    TextView textoResultado;
    final String ID = "ca-app-pub-3567961859053683/7232838256";
    private InterstitialAd interstitial;
    boolean mostrouInterstitial = false;
    int clickCount = 0;
    int clickMinAd = 2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medias_layout);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException e){
            e.printStackTrace();
        }

        // Criar o anncio intersticial.
        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId(ID);
        AdRequest adRequest2 = new AdRequest.Builder().build();
        interstitial.loadAd(adRequest2);

        nota1Tri = (EditText) findViewById(R.id.nota1Tri);
        nota2Tri = (EditText) findViewById(R.id.nota2Tri);
        nota3Tri = (EditText) findViewById(R.id.nota3Tri);
        textoResultado = (TextView) findViewById(R.id.situacao);
        resultado3Tri = (EditText) findViewById(R.id.resultado3Tri);
        resultadoMA = (EditText) findViewById(R.id.resultadoMA);
        resultadoPFV = (EditText) findViewById(R.id.resultadoPFV);

        terceiro = (Button) findViewById(R.id.terceiro);
        ma = (Button) findViewById(R.id.ma);

        terceiro.setOnClickListener(this);
        ma.setOnClickListener(this);
    }

    public void onClick(View v) {

        // Esconde o teclado ao clicar no bot?o caso o teclado ainda esteja aberto.
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        float nota1, nota2, nota3, result;
        DecimalFormat forma = new DecimalFormat("0.00");
        String formatado;

        switch (v.getId()) {
            case R.id.terceiro:
                clickCount++;
                if(clickCount == clickMinAd){
                    if(interstitial.isLoaded()) {
                        interstitial.show();
                    } else {
                        clickCount--;
                    }
                }

                if ((nota1Tri.getText().toString().length() == 0)
                        || (nota2Tri.getText().toString().length() == 0)) {
                    limparNotas("Preencha todas as notas.");
                } else {

                    nota1 = Float.parseFloat(nota1Tri.getText().toString());
                    nota2 = Float.parseFloat(nota2Tri.getText().toString());

                    if ((nota1 > 10) || (nota2 > 10)) {

                        limparNotas("Ops. Nota invalida.");

                    } else {

                        result = ((70 - ((nota1 * 3) + (nota2 * 3))) / 4);

                        if (result > 10)
                            textoResultado.setText("Voce esta na PFV.");
                        else
                            textoResultado.setText("Boa prova.");

                        forma = new DecimalFormat("0.00");
                        formatado = forma.format(result);

                        resultado3Tri.setText(formatado + " pts.");
                        resultadoMA.setText("");
                        resultadoPFV.setText("");
                    }
                }
                break;
            case R.id.ma:
                clickCount++;
                if(clickCount == clickMinAd){
                    if(interstitial.isLoaded()) {
                        interstitial.show();
                    } else {
                        clickCount--;
                    }
                }

                if ((nota1Tri.getText().toString().length() == 0)
                        || (nota2Tri.getText().toString().length() == 0)
                        || (nota3Tri.getText().toString().length() == 0)) {

                    limparNotas("Preencha todas as notas.");

                } else {

                    nota1 = Float.parseFloat(nota1Tri.getText().toString());
                    nota2 = Float.parseFloat(nota2Tri.getText().toString());
                    nota3 = Float.parseFloat(nota3Tri.getText().toString());

                    if ((nota1 > 10) || (nota2 > 10) || (nota3 > 10)) {

                        limparNotas("Ops. Nota Invalida.");

                    } else {

                        resultado3Tri.setText("");

                        result = ((nota1 * 3) + (nota2 * 3) + (nota3 * 4)) / 10;
                        formatado = forma.format(result);
                        resultadoMA.setText(formatado + " pts.");

                        if (result >= 7) {
                            textoResultado.setText("Aprovado. Parabens!");
                            resultadoPFV.setText("");
                        } else {
                            textoResultado.setText("PFV. Boa sorte.");
                            result = (25 - (result * 3)) / 2;
                            formatado = forma.format(result);
                            resultadoPFV.setText(formatado + " pts.");
                        }
                    }
                }
                break;
            default:
                break;
        }
    }

    void limparNotas(String mensagem) {
        textoResultado.setText(mensagem);
        resultadoMA.setText("");
        resultadoPFV.setText("");
        resultado3Tri.setText("");
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
