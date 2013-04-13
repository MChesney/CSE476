package edu.msu.cse.boggle.droiddraw;

import java.io.Serializable;

import com.google.android.gcm.GCMRegistrar;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CloudOpeningActivity extends Activity {
	
	private Cloud cloud = new Cloud();

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
	
	// This is only a temporary function for the checkpoint
	/*public void onStartGame(View view) {
		
		Intent intent = new Intent(this,  EditActivity.class);
		Game.setName(Game.PLAYERONE, "temp1");
		Game.setName(Game.PLAYERTWO, "temp2");
		startActivity(intent);
		
	}*/
//	public void onNewUser(View view) {
//		final AlertDialog.Builder alert_builder = new AlertDialog.Builder(this);
//		
//		LinearLayout layout = new LinearLayout(this);
//		layout.setOrientation(1); //Vertical
//		
//		final EditText username = new EditText(this);
//		final EditText password = new EditText(this);
//		
//		username.setHint("");
//		password.setHint("");
//		
//		username.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
//		username.setActivated(true);
//		password.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
//		
//		final TextView usernameTitle = new TextView(this);
//		final TextView passwordTitle = new TextView(this);
//		
//		usernameTitle.setText("Username");
//		passwordTitle.setText("Password");
//		
//		layout.addView(usernameTitle);
//		layout.addView(username);
//		layout.addView(passwordTitle);
//		layout.addView(password);
//		
//		alert_builder.setView(layout);
//		alert_builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//			
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				params.usernameText = username.getText().toString();
//				params.passwordText = password.getText().toString();
//				new Thread(new Runnable() {
//					@Override
//		            public void run() {
//						cloud.addUser(params.usernameText, params.passwordText);
//				}
//			}).start();
//			}
//		});
//		
//		alert_builder.setCancelable(false);
//		alert_builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				dialog.cancel();
//			}
//		});
//		
//		alert_builder.show();
//	}
//	
//	public void onLogin(View view) {
//		final AlertDialog.Builder alert_builder = new AlertDialog.Builder(this);
//		
//		LinearLayout layout = new LinearLayout(this);
//		layout.setOrientation(1); //Vertical
//		
//		final EditText username = new EditText(this);
//		final EditText password = new EditText(this);
//		
//		username.setHint("");
//		password.setHint("");
//		
//		username.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
//		username.setActivated(true);
//		password.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
//		
//		final TextView usernameTitle = new TextView(this);
//		final TextView passwordTitle = new TextView(this);
//		
//		usernameTitle.setText("Username");
//		passwordTitle.setText("Password");
//		
//		layout.addView(usernameTitle);
//		layout.addView(username);
//		layout.addView(passwordTitle);
//		layout.addView(password);
//		
//		alert_builder.setView(layout);
//		alert_builder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
//			
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				params.usernameText = username.getText().toString();
//				params.passwordText = password.getText().toString();
//				new Thread(new Runnable() {
//					@Override
//		            public void run() {
//						cloud.loginUser(params.usernameText, params.passwordText);
//				}
//			}).start();
//			}
//		});
//		
//		alert_builder.setCancelable(false);
//		alert_builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				dialog.cancel();
//			}
//		});
//		
//		alert_builder.show();
//	}
	
	public void onNew(View view) {
		
		Intent intent = new Intent(this,  NewActivity.class);
		startActivity(intent);
	}
	
	public void onLogin(View view) {
		
		Intent intent = new Intent(this,  LoginActivity.class);
		startActivity(intent);
		
	}
	
	private static class Parameters implements Serializable {
		private static final long serialVersionUID = -1246586533002567020L;
		
		private String usernameText;
		private String passwordText;

	}
    /**
     * The current parameters
     */
    private Parameters params = new Parameters();

}
