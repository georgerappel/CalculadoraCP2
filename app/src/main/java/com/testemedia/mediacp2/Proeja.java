package com.testemedia.mediacp2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.testemedia.mediacp2.PROEJA.Nota2cert;
import com.testemedia.mediacp2.PROEJA.ProejaAnual;
import com.testemedia.mediacp2.PROEJA.ProejaFinal;
import com.testemedia.mediacp2.PROEJA.ProejaPFV;
import com.testemedia.mediacp2.PROEJA.ProejaSobre;

/**
 * Created by Lucas on 03/02/2015.
 */
public class Proeja extends Activity implements View.OnClickListener {

    Button botaoCertificacao2, botaoPVF, botaoMediaAnual, botaoMediaFinal, botaoSobre;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menuproeja_layout);

        botaoCertificacao2 = (Button) findViewById(R.id.certificacao2);
        botaoPVF = (Button) findViewById(R.id.pvf);
        botaoMediaAnual =(Button) findViewById(R.id.mediaAnual);
        botaoMediaFinal = (Button) findViewById(R.id.mediaFinal);
        botaoSobre = (Button)findViewById(R.id.sobre);

        botaoCertificacao2.setOnClickListener(this);
        botaoPVF.setOnClickListener(this);
        botaoMediaAnual.setOnClickListener(this);
        botaoMediaFinal.setOnClickListener(this);
        botaoSobre.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        Intent nota2cert = new Intent(this, Nota2cert.class);
        Intent notaPVF = new Intent (this, ProejaPFV.class);
        Intent mediaAnual = new Intent (this, ProejaAnual.class);
        Intent mediaFinal = new Intent (this, ProejaFinal.class);
        Intent sobre = new Intent (this, ProejaSobre.class);

        switch (v.getId()) {
            case R.id.certificacao2:
                this.startActivity(nota2cert);
                break;
            case R.id.pvf:
                this.startActivity(notaPVF);
                break;
            case R.id.mediaAnual:
                this.startActivity(mediaAnual);
                break;
            case R.id.mediaFinal:
                this.startActivity(mediaFinal);
                break;
            case R.id.sobre:
                this.startActivity(sobre);
                break;
            default:
                break;
        }
    }





}