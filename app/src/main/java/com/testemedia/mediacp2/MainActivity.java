package com.testemedia.mediacp2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {

	Button  botaoMA, botaoBoletim,
		 botaoTimetable, botaoProeja, botaoCalendario;
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

		botaoMA.setOnClickListener(this);
		botaoBoletim.setOnClickListener(this);
        botaoTimetable.setOnClickListener(this);
        botaoProeja.setOnClickListener(this);
        botaoCalendario.setOnClickListener(this);

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
        String yourShareText = " - Calcule e salve suas médias com Calculadora CP2! " + playStoreLink;
        Intent shareIntent = ShareCompat.IntentBuilder.from(this).setType("text/plain").setText(yourShareText).getIntent();
        // Set the share Intent
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.informacoes:
                tracker.send(MapBuilder.createEvent("Informacoes Actionbar", "", "", null).build());
                Intent Info = new Intent(this, Info.class);
                this.startActivity(Info);
                return true;
            case R.id.calendario:
                Intent Calendario = new Intent(this, Calendario.class);
                this.startActivity(Calendario);
                return true;
            case R.id.share:
                tracker.send(MapBuilder.createEvent("COMPARTILHAR Actionbar", "", "", null).build());
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
		Intent Boletim = new Intent(this, menucad.class);
        Intent Time = new Intent(this,MenuTimetable.class);
        Intent Proeja = new Intent(this,Proeja.class);
        Intent Calendario2 = new Intent(this, Calendario.class);

		switch (v.getId()) {
		case R.id.botaoMA:
			this.startActivity(NotaMA);
			break;
		case R.id.botaoBoletim:
			this.startActivity(Boletim);
			break;
        case R.id.botaoProeja:
             this.startActivity(Proeja);
             break;
        case R.id.timetable:
            this.startActivity(Time);
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
