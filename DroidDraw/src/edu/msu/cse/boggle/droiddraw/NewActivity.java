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
		
		final String playerOneName = one.getText().toString();
		String playerPasswordcheck = two.getText().toString();
		final String playerPassword = three.getText().toString();
		
		if (playerOneName.length() == 0) {
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
					final boolean loggedIn = cloud.addUser(playerOneName, playerPassword);
					
					mainHandler.post(new Runnable() {

                        @Override
                        public void run() {
                            if(loggedIn) {
                            	Intent intent = new Intent(activity,  EditActivity.class);
                        		Bundle bundle = new Bundle();
                        		Players players = new Players();
                        		players.setName(Players.PLAYERONE, playerOneName);
                        		players.setName(Players.PLAYERTWO, "temp2");
                        		players.savePlayers(bundle);
                        		intent.putExtras(bundle);
                        		startActivity(intent);
                            }else {
                                // Failure
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
	
}
