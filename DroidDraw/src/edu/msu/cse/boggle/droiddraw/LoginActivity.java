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

public class LoginActivity extends Activity {
	
	private Cloud cloud = new Cloud();
	
	static final String KEY_USERNAME = "username";
	static final String KEY_PASSWORD = "password";
	static final String KEY_REMEMBER = "remember";
	
	public String username;
	public String getUsername() {
		return username;
	}

	public String password;
	public CheckBox cb;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		SharedPreferences prefs =PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		if (prefs.getBoolean(KEY_REMEMBER, true))
		{
			username = prefs.getString(KEY_USERNAME, "Default Value if not found");
			password = prefs.getString(KEY_PASSWORD, "Password Not Found");
			
			EditText one = (EditText) findViewById(R.id.playerOneEdit);
			EditText two = (EditText) findViewById(R.id.PlayerPassword);
			cb = (CheckBox) this.findViewById(R.id.checkBox1);
			
			one.setText(username);
			two.setText(password);
			cb.setChecked(true);
		}
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void onStartGame(View view) {
		EditText one = (EditText) findViewById(R.id.playerOneEdit);
		EditText two = (EditText) findViewById(R.id.PlayerPassword);
		
		
		final String playerName = one.getText().toString().trim();
		final String playerPassword = two.getText().toString();
		checkLogin(playerName, playerPassword);
		
		if (playerName.length() == 0) {
			one.requestFocus();
		} else if (playerPassword.length() == 0) {
			two.requestFocus();
		} else {

			final ContextWrapper activity = this;
			final Handler mainHandler = new Handler(this.getMainLooper());
	        
			new Thread(new Runnable() {
				@Override
	            public void run() {
					final boolean loggedIn = cloud.loginUser(playerName, playerPassword);
					
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
                            	Toast.makeText(activity, R.string.login_fail, Toast.LENGTH_SHORT).show();       
                            }
                        }    
					});
				}
			}).start();
		}
	}
	
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
		EditText two = (EditText) findViewById(R.id.PlayerPassword);
		
		
		final String playerName = one.getText().toString().trim();
		final String playerPassword = two.getText().toString();
		checkLogin(playerName, playerPassword);
	}
	
	public void checkLogin(String playerName, String playerPassword){
		
		CheckBox rememberCB = (CheckBox) findViewById(R.id.checkBox1);
		
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
	    Editor ed = prefs.edit();
	    
		if(rememberCB.isChecked()){
		    ed.putString(KEY_USERNAME, playerName);
		    ed.putString(KEY_PASSWORD, playerPassword);
		    ed.putBoolean(KEY_REMEMBER, true);
		    ed.commit();
		    
		    
		}else{
			ed.putBoolean(KEY_REMEMBER, false);
			ed.commit();
		}
	}
}
