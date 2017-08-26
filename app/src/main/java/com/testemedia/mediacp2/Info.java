package com.testemedia.mediacp2;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.google.analytics.tracking.android.EasyTracker;

public class Info extends AppCompatActivity implements View.OnClickListener {

	Context context = this;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info_layout);

		ImageButton faceGeorge = (ImageButton) findViewById(R.id.faceGeorge);
		ImageButton faceLucas = (ImageButton) findViewById(R.id.faceLucas);

		faceGeorge.setOnClickListener(this);
		faceLucas.setOnClickListener(this);

	}
	
	@Override
	public void onStart() {
		super.onStart();
		EasyTracker.getInstance(this).activityStart(this);
	}

	public void onClick(View v) {
		String urlr = "http://www.facebook.com/georgerappel";
		String urlb = "http://www.facebook.com/lucasbragabook";
		Intent fb;

		switch (v.getId()) {
		case R.id.faceGeorge:
			try {
				fb = newFacebookIntent(context.getPackageManager(), urlr);
				startActivity(fb);
			} catch (Exception e) {
				startActivity(new Intent(Intent.ACTION_VIEW,
						Uri.parse("http://www.facebook.com/georgerappel")));
			}
			break;
		case R.id.faceLucas:
			try {
				fb = newFacebookIntent(context.getPackageManager(), urlb);
				startActivity(fb);
			} catch (Exception e) {
				startActivity(new Intent(Intent.ACTION_VIEW,
						Uri.parse("http://www.facebook.com/lucasbragabook")));
			}
			break;
		default:
			break;

		}
	}
	
	public static Intent newFacebookIntent(PackageManager pm, String url) {
	    Uri uri;
	    try {
	        pm.getPackageInfo("com.facebook.katana", 0);
	        // http://stackoverflow.com/a/24547437/1048340
	        uri = Uri.parse("fb://facewebmodal/f?href=" + url);
	    } catch (PackageManager.NameNotFoundException e) {
	        uri = Uri.parse(url);
	    }
	    return new Intent(Intent.ACTION_VIEW, uri);
	}
	
	@Override
	public void onStop() {
		super.onStop();
		EasyTracker.getInstance(this).activityStop(this);
	}
}
