package com.testemedia.mediacp2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.analytics.tracking.android.EasyTracker;
import com.matabii.dev.scaleimageview.ScaleImageView;

public class Calendario extends Activity implements View.OnClickListener{
	
	ImageButton calendariopadrao, calendario3;
	ScaleImageView calendario;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendario_layout);
		
		calendariopadrao = (ImageButton)findViewById(R.id.calendariopadrao);
		calendario3 = (ImageButton)findViewById(R.id.calendario3);
		calendario = (ScaleImageView)findViewById(R.id.calendario);
		
		calendariopadrao.setOnClickListener(this);
		calendario3.setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View v){
		
		switch(v.getId()){
		case R.id.calendariopadrao:
			calendario.setImageResource(R.drawable.calend);
			calendario.setVisibility(View.VISIBLE);
			calendario3.setVisibility(View.GONE);
			calendariopadrao.setVisibility(View.GONE);			
			break;
		case R.id.calendario3:
			calendario.setImageResource(R.drawable.calend3);
			calendario.setVisibility(View.VISIBLE);
			calendario3.setVisibility(View.GONE);
			calendariopadrao.setVisibility(View.GONE);				
			break;
		
		}
		
		
	}
	
	@Override
	public void onBackPressed() {
		if(calendario.getVisibility() == View.VISIBLE){
			calendario.setVisibility(View.GONE);
			calendario3.setVisibility(View.VISIBLE);
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
		overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
	}
	
	@Override
	public void onStop() {
		super.onStop();
		EasyTracker.getInstance(this).activityStop(this);
	}
	
	
}