package edu.msu.cse.boggle.droiddraw;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class OpeningActivity extends Activity {
	
	/**
	 * The name of the keys to save the names
	 * TODO
	 */
	private final static String PLAYERONE = "player1";
	private final static String PLAYERTWO = "player2";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_opening);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_opening, menu);
		return true;
	}
	
	public void onStartGame(View view) {
		EditText one = (EditText) findViewById(R.id.playerOneEdit);
		EditText two = (EditText) findViewById(R.id.playerTwoEdit);
		
		String playerOneName = one.getText().toString();
		String playerTwoName = two.getText().toString();
		
		if (playerOneName.length() == 0) {
			one.requestFocus();
		} else if (playerTwoName.length() == 0) {
			two.requestFocus();
		} else {
			Intent intent = new Intent(this,  EditActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString(Player.PLAYERONENAME, playerOneName);
			bundle.putString(Player.PLAYERTWONAME, playerTwoName);
			startActivity(intent);
		}
		
	}
	
	public void onInstructions(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        
        // Parameterize the builder
        builder.setTitle(R.string.instructions);
        builder.setMessage(R.string.rules_of_game);
        builder.setPositiveButton(android.R.string.ok, null);
		//builder.setNegativeButton(R.string.shuffle, listener);
        
        // Create the dialog box and show it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
		
	}
	
}
