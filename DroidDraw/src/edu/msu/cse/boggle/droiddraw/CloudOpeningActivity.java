package edu.msu.cse.boggle.droiddraw;

import com.google.android.gcm.GCMRegistrar;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class CloudOpeningActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cloud_opening);
		GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);
		String regId = GCMRegistrar.getRegistrationId(this);
		if (regId.equals("")) {
			GCMRegistrar.register(this, GCMIntentService.SENDER_ID);
		} else {
			Game.setGcmId(regId);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_cloud_opening, menu);
		return true;
	}
	
	public void onNew(View view) {
		
		Intent intent = new Intent(this,  NewActivity.class);
		startActivity(intent);
	}
	
	public void onLogin(View view) {
		
		Intent intent = new Intent(this,  LoginActivity.class);
		startActivity(intent);
		
	}

}
