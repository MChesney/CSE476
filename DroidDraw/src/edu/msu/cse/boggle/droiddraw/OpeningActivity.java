package edu.msu.cse.boggle.droiddraw;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class OpeningActivity extends Activity {

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
			Players players = new Players();
			players.setName(Players.PLAYERONE, playerOneName);
			players.setName(Players.PLAYERTWO, playerTwoName);
			players.savePlayers(bundle);
			intent.putExtras(bundle);
			startActivity(intent);
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
