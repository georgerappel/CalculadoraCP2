package com.testemedia.mediacp2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.analytics.tracking.android.EasyTracker;
import com.matabii.dev.scaleimageview.ScaleImageView;

public class Calendario extends Activity implements View.OnClickListener{
	
	ImageButton calendariopadrao, calendarioTerceirao;
	ScaleImageView calendario;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendario_layout);
		
		calendariopadrao = (ImageButton)findViewById(R.id.calendariopadrao);
		calendarioTerceirao = (ImageButton)findViewById(R.id.calendarioTerceirao);
		calendario = (ScaleImageView)findViewById(R.id.calendario);
		
		calendariopadrao.setOnClickListener(this);
		calendarioTerceirao.setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View v){
		
		switch(v.getId()){
		case R.id.calendariopadrao:
			calendario.setImageResource(R.drawable.calend);
			calendario.setVisibility(View.VISIBLE);
			calendarioTerceirao.setVisibility(View.GONE);
			calendariopadrao.setVisibility(View.GONE);			
			break;
		case R.id.calendarioTerceirao:
			calendario.setImageResource(R.drawable.calend3);
			calendario.setVisibility(View.VISIBLE);
			calendarioTerceirao.setVisibility(View.GONE);
			calendariopadrao.setVisibility(View.GONE);				
			break;
		
		}
		
		
	}
	
	@Override
	public void onBackPressed() {
		if(calendario.getVisibility() == View.VISIBLE){
			calendario.setVisibility(View.GONE);
			calendarioTerceirao.setVisibility(View.VISIBLE);
			calendariopadrao.setVisibility(View.VISIBLE);
		} else if(calendario.getVisibility() == View.GONE){
			super.onBackPressed();
		}
	}
	
	@Override
	public void onStart() {
		super.onStart();
		EasyTracker.getInstance(this).activityStart(this);
	}
	
	@Override
	public void onPause(){
		super.onPause();
	}
	
	@Override
	public void onStop() {
		super.onStop();
		EasyTracker.getInstance(this).activityStop(this);
	}
	
	
}