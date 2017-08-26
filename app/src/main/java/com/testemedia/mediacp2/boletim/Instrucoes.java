package com.testemedia.mediacp2.boletim;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.analytics.tracking.android.EasyTracker;
import com.testemedia.mediacp2.R;

public class Instrucoes extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.instrucoes_layout);
	}


	@Override
	public void onStart() {
		super.onStart();
		EasyTracker.getInstance(this).activityStart(this);
	}
	
	@Override
	public void onStop() {
		super.onStop();
		EasyTracker.getInstance(this).activityStop(this);
	}
}