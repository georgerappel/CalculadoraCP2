package com.testemedia.mediacp2;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.DecimalFormat;

/**
 * Editado by George on 12/04/2015.
 */
public class CalcularMedias extends Activity implements View.OnClickListener {

    EditText nota1Tri, nota2Tri, nota3Tri, resultado3Tri, resultadoMA, resultadoPFV;
    Button terceiro, ma;
    TextView textoResultado;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medias_layout);

        AdView adView = (AdView) this.findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

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

        //Define o tracker desse click e coloca na categoria Bot?es>Clique>Calculo MA
        EasyTracker tracker = EasyTracker.getInstance(CalcularMedias.this);

        float nota1, nota2, nota3, result;
        DecimalFormat forma = new DecimalFormat("0.00");
        String formatado;

        switch (v.getId()) {
            case R.id.terceiro:
                tracker.send(MapBuilder.createEvent("Botoes", "Clique", "Terceiro", null).build());
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
                tracker.send(MapBuilder.createEvent("Botoes", "Clique", "Calculo M.A.", null).build());
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


    @Override
    public void onStop() {
        super.onStop();
        EasyTracker.getInstance(this).activityStop(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        EasyTracker.getInstance(this).activityStart(this);
    }

    void limparNotas(String mensagem) {
        textoResultado.setText(mensagem);
        resultadoMA.setText("");
        resultadoPFV.setText("");
        resultado3Tri.setText("");
    }
}
