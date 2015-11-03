package com.testemedia.mediacp2;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.text.DecimalFormat;

public class Boletim extends Activity implements View.OnClickListener {
    final int TAM_LETRA = 17;
    SqlCadastro mSql = new SqlCadastro(this);
    int auxTrimestre, id, contador = 0;
    Context context;
    int duration;
    Materias materia = new Materias();
    private InterstitialAd interstitial;

    public static String formatar(String text) {
        float nota = Float.parseFloat(text);
        DecimalFormat Formatacao = new DecimalFormat("0.00");
        text = Formatacao.format(nota);
        return text;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boletim_layout);

        // ---- Table columns
        final String _ID = "ID";
        final String KEY_NOME = "Nome";
        final String KEY_NOTA1 = "Tri1";
        final String KEY_NOTA2 = "Tri2";
        final String KEY_NOTA3 = "Tri3";
        final String KEY_PRENOTA3 = "Est3Tri";
        final String KEY_MA = "MA";
        final String KEY_PREPFV = "EstPFV";
        final String KEY_PFV = "PFV";

        // ------ Declaração das variaveis locais.
        Button atualizar;
        final String ID = "ca-app-pub-3567961859053683/7232838256";
        int numeroLinha = 1;

        SqlCadastro helper = new SqlCadastro(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        context = this;
        duration = Toast.LENGTH_SHORT;

        atualizar = (Button) findViewById(R.id.atualizar);
        atualizar.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = getIntent();
                startActivity(intent);
                finish();
            }
        });

        // Criar o anúncio intersticial.
        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId(ID);
        AdRequest adRequest2 = new AdRequest.Builder().build();
        interstitial.loadAd(adRequest2);

        TableLayout tl = (TableLayout) findViewById(R.id.maintable);
        TableRow tr;

        String selection = "SELECT * FROM Materias";
        Cursor cursor = db.rawQuery(selection, null);


        if (cursor.moveToFirst())

        {
            do {
                if (cursor.getString(cursor.getColumnIndex(_ID)) != null || cursor.getString(cursor.getColumnIndex(_ID)) != "") {

                    int id_materia = cursor.getInt(cursor.getColumnIndex(_ID));

                    tr = new TableRow(this);
                    tr.setId(id_materia);

                    tr.addView(criar_tv_nome(cursor.getString(cursor.getColumnIndex(KEY_NOME))));
                    tr.addView(criar_tv_colorido(cursor.getString(cursor.getColumnIndex(KEY_NOTA1)), 1, id_materia));
                    tr.addView(criar_tv_colorido(cursor.getString(cursor.getColumnIndex(KEY_NOTA2)), 2, id_materia));
                    tr.addView(criar_tv_cinza(cursor.getString(cursor.getColumnIndex(KEY_PRENOTA3))));
                    tr.addView(criar_tv_colorido(cursor.getString(cursor.getColumnIndex(KEY_NOTA3)), 3, id_materia));
                    tr.addView(criar_tv_media_anual(cursor.getString(cursor.getColumnIndex(KEY_MA))));
                    tr.addView(criar_tv_cinza(cursor.getString(cursor.getColumnIndex(KEY_PREPFV))));
                    tr.addView(criar_tv_colorido(cursor.getString(cursor.getColumnIndex(KEY_PFV)), 4, id_materia));

                    tl.addView(tr);
                }
            } while (cursor.moveToNext()); // Move cursor para a proxima tupla, enquanto houver.
        }

        cursor.close();
        db.close();
    }

    public void onClick(View v) {
    }

    public TextView criar_tv_nome(String text) {
        TextView tv = new TextView(this);
        tv.setId(contador + 1);
        tv.setText(text);
        tv.setTextColor(Color.BLACK);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(TAM_LETRA);
        return tv;
    }

    public TextView criar_tv_media_anual(String text) {
        TextView tv = new TextView(this);
        tv.setId(contador + 1);
        tv.setText(formatar(text));

        if (Float.parseFloat(text) == 0)
            tv.setTextColor(Color.BLACK);
        else
            tv.setTextColor(Color.BLUE);

        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(TAM_LETRA);
        return tv;
    }

    // ---- TextView cinza - Usado para Est PFV e Est 3Tri.
    public TextView criar_tv_cinza(String text) {
        TextView tv = new TextView(this);
        tv.setId(contador + 1);
        tv.setText(formatar(text));
        tv.setTextColor(Color.GRAY);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(TAM_LETRA);
        return tv;
    }

    // ---- TextView Colorido - texto colorido para 1, 2 e 3 trimestres e PFV.
    public TextView criar_tv_colorido(String text, int trimestre, int id_materia) {
        TextView tv = new TextView(this);

        // ---- Ver funcaoo para explicacao.
        tv.setId(aumentar_id(id_materia, trimestre));
        tv.setText(formatar(text));

        if (Float.parseFloat(text) == 0)
            tv.setTextColor(Color.BLACK);
        else if (Float.parseFloat(text) < 5)
            tv.setTextColor(Color.RED);
        else if (Float.parseFloat(text) < 7)
            tv.setTextColor(Color.GREEN);
        else
            tv.setTextColor(Color.BLUE);

        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(TAM_LETRA);
        tv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                criarDialog(diminuir_id(v.getId()), determinar_trimestre_pelo_id(v.getId()));
            }
        });
        return tv;
    }

    public void carregarAnuncio() {
        Editor editControle;
        SharedPreferences controle;

        controle = this.getSharedPreferences("anuncios", Context.MODE_PRIVATE);
        editControle = controle.edit();

        if (controle.getBoolean("key", true)) {
            if (interstitial.isLoaded()) {
                editControle.putBoolean("key", false);
                interstitial.show();
            }
        } else {
            editControle.putBoolean("key", true);
        }
        editControle.commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        EasyTracker.getInstance(this).activityStart(this);
    }

    @Override
    public void onStop() {
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
        super.onStop();
        EasyTracker.getInstance(this).activityStop(this);
    }

    public int aumentar_id(int id_materia, int trimestre) {
        // ---- O id é determinado com o ID na tabela somado de 100 * trimestre.
        // ---- Na hora de checar qual é o trimestre, basta ler a função "descobrir_trimestre"
        return id_materia + 100 * trimestre;
    }

    public int diminuir_id(int id) {
        return id - 100 * (determinar_trimestre_pelo_id(id));
    }

    public int determinar_trimestre_pelo_id(int id) {
        if (id > 400)
            return 4;
        else if (id > 300)
            return 3;
        else if (id > 200)
            return 2;
        else
            return 1;
    }

    public void criarDialog(int numeroMateria, final int trimestre) {
        EasyTracker tracker = EasyTracker.getInstance(Boletim.this);

        tracker.send(MapBuilder.createEvent("Boletim", "Editar",
                "Edicao Direta do Boletim", null).build());
        auxTrimestre = trimestre;
        id = numeroMateria;
        float nota = 0;
        materia = mSql.buscarId(id);
        String texto = "Erro.";

        if (trimestre == 1) {
            nota = materia.getNota1();
            texto = "Alterar nota do 1° trimestre:";
        } else if (trimestre == 2) {
            nota = materia.getNota2();
            texto = "Alterar nota do 2° trimestre:";
        } else if (trimestre == 3) {
            nota = materia.getNota3();
            texto = "Alterar nota do 3° trimestre:";
        } else if (trimestre == 4) {
            nota = materia.getPFV(); // 4 - NOTA DA PFV
            texto = "Alterar nota da PFV:";
        }

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogo);

        TextView titulo = (TextView) dialog.findViewById(R.id.titulo);
        titulo.setText(materia.getNome());

        TextView texto1 = (TextView) dialog.findViewById(R.id.texto1);
        texto1.setText(texto);

        EditText notaedit = (EditText) dialog.findViewById(R.id.notaedit);
        notaedit.setText(Float.toString(nota));

        Button salvar = (Button) dialog.findViewById(R.id.salvar);
        salvar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText notaedit = (EditText) dialog
                        .findViewById(R.id.notaedit);
                if (notaedit.getText().length() == 0) {
                    CharSequence text = "Preencha o campo.";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else if (Float.parseFloat(notaedit.getText().toString()) < 0
                        || Float.parseFloat(notaedit.getText().toString()) > 10) {
                    // Nota fora do limite
                    CharSequence text = "Nota invalida.";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else {
                    salvarNota(Float.parseFloat(notaedit.getText().toString()));
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    public void salvarNota(float nota) {

        if (auxTrimestre == 1) {
            materia.setNota1(nota);
        } else if (auxTrimestre == 2) {
            materia.setNota2(nota);
        } else if (auxTrimestre == 3) {
            materia.setNota3(nota);
        } else if (auxTrimestre == 4) {
            materia.setPFV(nota);
        }
        mSql.atualizarMateria(materia, id);
        mSql.close();
        CharSequence text = "Nota atualizada!";
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

}
