package com.testemedia.mediacp2;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;
import com.testemedia.mediacp2.timetable.TimeTableActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

	Button  botaoMA, botaoBoletim,
		 botaoTimetable, botaoProeja, botaoCalendario, botaoSobre;
    private ShareActionProvider mShareActionProvider;
    EasyTracker tracker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        tracker = EasyTracker.getInstance(MainActivity.this);

        botaoMA = (Button) findViewById(R.id.botaoMA);
		botaoBoletim = (Button) findViewById(R.id.botaoBoletim);
        botaoTimetable = (Button) findViewById(R.id.timetable);
        botaoProeja = (Button) findViewById(R.id.botaoProeja);
        botaoCalendario = (Button) findViewById(R.id.botaoCalendario);
        botaoSobre = (Button)findViewById(R.id.botaoSobre);

		botaoMA.setOnClickListener(this);
		botaoBoletim.setOnClickListener(this);
        botaoTimetable.setOnClickListener(this);
        botaoProeja.setOnClickListener(this);
        botaoCalendario.setOnClickListener(this);
        botaoSobre.setOnClickListener(this);
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        getMenuInflater().inflate(R.menu.main_activity_actions, menu);
        // Fetch and store ShareActionProvider
        if(Build.VERSION.SDK_INT >= 14) {
            MenuItem menuItem = menu.findItem(R.id.share);
            mShareActionProvider = new ShareActionProvider(this);
            onShareAction();
            MenuItemCompat.setActionProvider(menuItem, mShareActionProvider);
        }
        return super.onCreateOptionsMenu(menu);
    }

    private void onShareAction(){
        // Create the share Intent
        String playStoreLink = "https://play.google.com/store/apps/details?id=" + getPackageName();
        String yourShareText = " - Calcule e salve suas notas com Calculadora CP2! " + playStoreLink;
        Intent shareIntent = ShareCompat.IntentBuilder.from(this).setType("text/plain").setText(yourShareText).getIntent();
        // Set the share Intent
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        EasyTracker tracker = EasyTracker.getInstance(MainActivity.this);
        switch (item.getItemId()) {
            case R.id.share:
                tracker.send(MapBuilder.createEvent("Menubar", "Compartilhar",
                        "Compartilhar", null).build());
                onShareAction();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

	@Override
	public void onStart() {
		super.onStart();
		EasyTracker.getInstance(this).activityStart(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent NotaMA = new Intent(this, CalcularMedias.class);
        Intent Proeja = new Intent(this, com.testemedia.mediacp2.proeja.Proeja.class);
        Intent Calendario2 = new Intent(this, Calendario.class);
        Intent TimeTable = new Intent(this,TimeTableActivity.class);
        Intent Info = new Intent(this, Info.class);

		switch (v.getId()) {
		case R.id.botaoMA:
			this.startActivity(NotaMA);
			break;
		case R.id.botaoBoletim:
            Intent acao = new Intent(this, com.testemedia.mediacp2.boletim.Boletim.class);
            tracker.send(MapBuilder.createEvent("Botoes", "Boletim-Geral", "Visualizar Boletim", null).build());
            this.startActivity(acao);
			break;
        case R.id.botaoProeja:
             this.startActivity(Proeja);
             break;
        case R.id.timetable:
            this.startActivity(TimeTable);
            break;
        case R.id.botaoSobre:
            this.startActivity(Info);
            break;
        case R.id.botaoCalendario:
            this.startActivity(Calendario2);
            break;
		default:
			break;
		}

	}

	@Override
	protected void onStop() {
		super.onStop();
		EasyTracker.getInstance(this).activityStop(this);
	}

}
