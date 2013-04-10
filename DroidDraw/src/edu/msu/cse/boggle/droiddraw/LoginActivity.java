package edu.msu.cse.boggle.droiddraw;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContextWrapper;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	
	private Cloud cloud = new Cloud();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}

		
public void onStartGame(View view) {
		
		EditText one = (EditText) findViewById(R.id.playerOneEdit);
		EditText two = (EditText) findViewById(R.id.PlayerPassword);
		
		
		final String playerName = one.getText().toString().trim();
		final String playerPassword = two.getText().toString();
		
		
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
                            	Game.setWaitStatus(Game.WAITFORPLAYER);
                            	Intent intent = new Intent(activity,  WaitingActivity.class);
                    			startActivity(intent);
                            }else {
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
	
}
