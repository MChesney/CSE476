package edu.msu.cse.boggle.droiddraw;

import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class NewActivity extends Activity {
	
	private Cloud cloud = new Cloud();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new);
	}

		
public void onStartGame(View view) {
		
		EditText one = (EditText) findViewById(R.id.playerOneEdit);
		EditText two = (EditText) findViewById(R.id.playerPasswordcheck);
		EditText three = (EditText) findViewById(R.id.playerPassword);
		
		final String playerName = one.getText().toString().trim();
		String playerPasswordcheck = two.getText().toString();
		final String playerPassword = three.getText().toString();
		
		if (playerName.length() == 0) {
			one.requestFocus();
		} else if (playerPasswordcheck.length() == 0) {
			two.requestFocus();
		} else if (playerPassword.length() == 0) {
			three.requestFocus();
		} else if (!playerPasswordcheck.contentEquals(playerPassword)) {
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
			 		    alertDialogBuilder.setTitle("Error");
			 			// set dialog message
						alertDialogBuilder
							.setMessage("Your passwords don't match")
							.setPositiveButton("OK",null)
						    .setCancelable(true);
			 				// create alert dialog
							AlertDialog alertDialog = alertDialogBuilder.create();
			 				// show it
							alertDialog.show();
						
		} else if (playerPasswordcheck.contentEquals(playerPassword)) {
			final ContextWrapper activity = this;
			final Handler mainHandler = new Handler(this.getMainLooper());
			new Thread(new Runnable() {
				@Override
	            public void run() {
					final boolean loggedIn = cloud.addUser(playerName, playerPassword);
					
					mainHandler.post(new Runnable() {

                        @Override
                        public void run() {
                            if(loggedIn) {
                            	Game.setName(Game.PLAYERSELF, playerName);
                            	Game.setPassword(playerPassword);
                            	Game.setWaitStatus(Game.WAITFORPLAYER);
                            	Intent intent = new Intent(activity,  WaitingActivity.class);
                        		startActivity(intent);
                            } else {
                                // Failure
                            	// TODO two users already logged in
                            	Toast.makeText(activity, R.string.user_already_exists, Toast.LENGTH_SHORT).show();
                                }
                            }    
                        });
			}
		}).start();
		
		
	}}
	

	public void onInstructions(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        
        // Parameterize the builder
        builder.setTitle(R.string.instructions);
        builder.setMessage(R.string.rules_of_game);
        builder.setPositiveButton(android.R.string.ok, null);
        
        // Create the dialog box and show it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
		
	}
	public void onRememberChecked(View view){
		
		EditText one = (EditText) findViewById(R.id.playerOneEdit);
		EditText two = (EditText) findViewById(R.id.playerPassword);
		
		final String playerName = one.getText().toString().trim();
		final String playerPassword = two.getText().toString();
		
		checkLogin(playerName, playerPassword);
	}
	public void checkLogin(String playerName, String playerPassword){
		
		CheckBox rememberCB = (CheckBox) findViewById(R.id.checkBox1);
		
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
	    Editor ed = prefs.edit();
	    
		if(rememberCB.isChecked()){
		    ed.putString(LoginActivity.KEY_USERNAME, playerName);
		    ed.putString(LoginActivity.KEY_PASSWORD, playerPassword);
		    ed.putBoolean(LoginActivity.KEY_REMEMBER, true);
		    ed.commit();
		    
		    
		}else{
			ed.putBoolean(LoginActivity.KEY_REMEMBER, false);
			ed.commit();
		}
	}
}
