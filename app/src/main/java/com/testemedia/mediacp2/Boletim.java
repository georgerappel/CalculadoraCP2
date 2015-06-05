package com.testemedia.mediacp2;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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

public class Boletim extends MainActivity implements View.OnClickListener {
    private InterstitialAd interstitial;

    SqlCadastro mSql = new SqlCadastro(this);
    int auxTrimestre, id;
    Context context;
    int duration;
    Materias materia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boletim_layout);

        // ------ Declaração das variaveis locais.
        Button atualizar;
        SQLiteDatabase db; // Variavel do tipo Database
        final String ID = "ca-app-pub-3567961859053683/7232838256";
        int numeroLinha = 1;

        SqlCadastro helper = new SqlCadastro(this);
        db = helper.getWritableDatabase();

        context = this;
        duration = Toast.LENGTH_SHORT;

        atualizar = (Button) findViewById(R.id.atualizar);
        atualizar.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });

        // Criar o anúncio intersticial.
        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId(ID);
        AdRequest adRequest2 = new AdRequest.Builder().build();
        interstitial.loadAd(adRequest2);

        TableLayout tl = (TableLayout) findViewById(R.id.maintable);

        SQLiteStatement statement = db
                .compileStatement("SELECT MAX(ID) FROM Materias"); // Statement
        float count = statement.simpleQueryForLong(); // Retorna o resultado da
        if (count == 0) { // Caso a lista n�o tenha cadastros

        } else { // Caso tenha algum cadastro

            for (int current = 0; current < count; current++) {

                // Declarando uma linha da tabela e os Views dos dados.
                TableRow tr = new TableRow(this);
                TextView nomeTV = new TextView(this);
                TextView Tri1TV = new TextView(this);
                TextView Tri2TV = new TextView(this);
                TextView PreTri3TV = new TextView(this);
                TextView Tri3TV = new TextView(this);
                TextView PrePFVTV = new TextView(this);
                TextView PFVTV = new TextView(this);
                TextView MATV = new TextView(this);

                // adiciona um ID � tabela
                tr.setId(100 + current);

                // Busca o nome da mat�ria de acordo com o ID e retorna pra
                // uma
                // String.
                String textoNome = "SELECT Nome FROM Materias WHERE ID="
                        + Float.toString(current + 1); // Define a pesquisa.
                try {
                    SQLiteStatement nomeid = db.compileStatement(textoNome); // Faz
                    // a
                    // pesquisa
                    String nome_id = nomeid.simpleQueryForString(); // Retorna a
                    // pesquisa
                    // como
                    // String.
                    // Texto do TextView � setado para o nome da mat�ria.
                    nomeTV.setId(current);
                    nomeTV.setText(nome_id); // Seta o texto do TextView
                    nomeTV.setTextColor(Color.BLACK); // Seta a cor do texto.
                    nomeTV.setGravity(Gravity.CENTER);
                    nomeTV.setTextSize(15);
                    tr.addView(nomeTV); // Adiciona o TextView � Linha
                    // (TableRow
                    // tr).

                    // Busca a nota do primeiro trimestre do ID
                    String textoTri1 = "SELECT Tri1 FROM Materias WHERE ID="
                            + Float.toString(current + 1);
                    SQLiteStatement Tri1id = db.compileStatement(textoTri1); // Faz
                    // a
                    // busca
                    // a
                    // partir
                    // da
                    // string
                    // acima.
                    String Tri1_id = Tri1id.simpleQueryForString();
                    // Cria o TextView da nota, seta a nota a partir da String
                    Tri1TV.setId(current);
                    Tri1TV.setText(formatar(Tri1_id));
                    Tri1TV.setGravity(Gravity.CENTER);
                    Tri1TV.setTextSize(15);
                    Tri1TV.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            criarDialog(v.getId(), 1);
                        }
                    });
                    if (Float.parseFloat(Tri1_id) == 0)
                        Tri1TV.setTextColor(Color.BLACK);
                    else if (Float.parseFloat(Tri1_id) < 5)
                        Tri1TV.setTextColor(Color.RED);
                    else if (Float.parseFloat(Tri1_id) < 7)
                        Tri1TV.setTextColor(Color.rgb(73, 161, 120));
                    else
                        Tri1TV.setTextColor(Color.BLUE);
                    tr.addView(Tri1TV);

                    // Create a TextView to house the value of the after-tax
                    // income
                    String textoTri2 = "SELECT Tri2 FROM Materias WHERE ID="
                            + Float.toString(current + 1);
                    SQLiteStatement Tri2id = db.compileStatement(textoTri2);
                    String Tri2_id = Tri2id.simpleQueryForString();

                    Tri2TV.setId(current);
                    Tri2TV.setText(formatar(Tri2_id));
                    Tri2TV.setGravity(Gravity.CENTER);
                    Tri2TV.setTextSize(15);
                    Tri2TV.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            criarDialog(v.getId(), 2);
                        }
                    });
                    if (Float.parseFloat(Tri2_id) == 0)
                        Tri2TV.setTextColor(Color.BLACK);
                    else if (Float.parseFloat(Tri2_id) < 5)
                        Tri2TV.setTextColor(Color.RED);
                    else if (Float.parseFloat(Tri2_id) < 7)
                        Tri2TV.setTextColor(Color.rgb(73, 161, 120));
                    else
                        Tri2TV.setTextColor(Color.BLUE);
                    tr.addView(Tri2TV);

                    // Create a TextView to house the value of the after-tax
                    // income
                    String textoPreTri3 = "SELECT Est3Tri FROM Materias WHERE ID="
                            + Float.toString(current + 1);
                    SQLiteStatement PreTri3id = db
                            .compileStatement(textoPreTri3);
                    String PreTri3_id = PreTri3id.simpleQueryForString();

                    PreTri3TV.setId(current);
                    PreTri3TV.setText(formatar(PreTri3_id));
                    PreTri3TV.setTextColor(Color.GRAY);
                    PreTri3TV.setGravity(Gravity.CENTER);
                    PreTri3TV.setTextSize(15);
                    tr.addView(PreTri3TV);

                    // Create a TextView to house the value of the after-tax
                    // income
                    String textoTri3 = "SELECT Tri3 FROM Materias WHERE ID="
                            + Float.toString(current + 1);
                    SQLiteStatement Tri3id = db.compileStatement(textoTri3);
                    String Tri3_id = Tri3id.simpleQueryForString();

                    Tri3TV.setId(current);
                    Tri3TV.setText(formatar(Tri3_id));
                    Tri3TV.setGravity(Gravity.CENTER);
                    Tri3TV.setTextSize(15);
                    Tri3TV.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            criarDialog(v.getId(), 3);
                        }
                    });
                    if (Float.parseFloat(Tri3_id) == 0)
                        Tri3TV.setTextColor(Color.BLACK);
                    else if (Float.parseFloat(Tri3_id) < 5)
                        Tri3TV.setTextColor(Color.RED);
                    else if (Float.parseFloat(Tri3_id) < 7)
                        Tri3TV.setTextColor(Color.rgb(73, 161, 120));
                    else
                        Tri3TV.setTextColor(Color.BLUE);
                    tr.addView(Tri3TV);

                    // Create a TextView to house the value of the after-tax
                    // income
                    String textoMA = "SELECT MA FROM Materias WHERE ID="
                            + Float.toString(current + 1);
                    SQLiteStatement MAid = db.compileStatement(textoMA);
                    String MA_id = MAid.simpleQueryForString();
                    MATV.setId(current);
                    MATV.setText(formatar(MA_id));
                    MATV.setTextSize(15);
                    if (Float.parseFloat(MA_id) == 0)
                        MATV.setTextColor(Color.BLACK);
                    else
                        MATV.setTextColor(Color.BLUE);
                    MATV.setGravity(Gravity.CENTER);
                    tr.addView(MATV);

                    // Create a TextView to house the value of the after-tax
                    // income
                    String textoPrePFV = "SELECT EstPFV FROM Materias WHERE ID="
                            + Float.toString(current + 1);
                    SQLiteStatement PrePFVid = db.compileStatement(textoPrePFV);
                    String PrePFV_id = PrePFVid.simpleQueryForString();

                    PrePFVTV.setId(current);
                    PrePFVTV.setText(formatar(PrePFV_id));
                    PrePFVTV.setTextColor(Color.GRAY);
                    PrePFVTV.setGravity(Gravity.CENTER);
                    PrePFVTV.setTextSize(15);
                    tr.addView(PrePFVTV);

                    // Create a TextView to house the value of the after-tax
                    // income
                    String textoPFV = "SELECT PFV FROM Materias WHERE ID="
                            + Float.toString(current + 1);
                    SQLiteStatement PFVid = db.compileStatement(textoPFV);
                    String PFV_id = PFVid.simpleQueryForString();

                    PFVTV.setId(current);
                    PFVTV.setText(formatar(PFV_id));
                    PFVTV.setTextSize(15);
                    PFVTV.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            criarDialog(v.getId(), 4);
                        }
                    });
                    if (Float.parseFloat(PFV_id) == 0)
                        PFVTV.setTextColor(Color.BLACK);
                    else
                        PFVTV.setTextColor(Color.BLUE);
                    PFVTV.setGravity(Gravity.CENTER);
                    tr.addView(PFVTV);

                    // Adiciona a TableRow (Linha de Tabela) no layout.
                    tl.addView(tr, numeroLinha);
                    numeroLinha++;

                    // No final, retorna ao in�cio e faz tudo com pr�ximo
                    // ID.
                } catch (SQLiteException e) {
                    e.printStackTrace();
                }
            }
        }

        db.close();
        /*if (prefs.getInt("toast", 1) < 5) {
            CharSequence text = "NOVIDADE! Agora voc� pode editar suas notas direto a partir do Boletim!";
			Toast toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
			toast.show();
			edit.putInt("toast", prefs.getInt("toast", 1) + 1);
			edit.apply();
		}*/
    }

    public void carregarAnuncio() {
        Editor editControle;
        SharedPreferences prefs, controle;

        controle = this.getSharedPreferences("anuncios", Context.MODE_PRIVATE);
        editControle = controle.edit();

        if (controle.getBoolean("key", true)) {
            Log.e("entrou ", "IsLoaded");
            if (interstitial.isLoaded()) {
                Log.e("entrou ", "virou false");
                editControle.putBoolean("key", false);
                interstitial.show();
            }
        } else {
            Log.e("entrou ", "virou true");
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
        carregarAnuncio();
        super.onStop();
        EasyTracker.getInstance(this).activityStop(this);
    }

    public static String formatar(String text) {

        float nota = Float.parseFloat(text);
        DecimalFormat Formatacao = new DecimalFormat("0.00");
        text = Formatacao.format(nota);
        return text;
    }

    public void criarDialog(int numeroMateria, final int trimestre) {
        EasyTracker tracker = EasyTracker.getInstance(Boletim.this);
        Materias materia;

        tracker.send(MapBuilder.createEvent("Boletim", "Editar",
                "Edicao Direta do Boletim", null).build());
        auxTrimestre = trimestre;
        id = numeroMateria + 1;
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
