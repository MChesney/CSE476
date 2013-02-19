package edu.msu.cse.boggle.droiddraw;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
//import android.widget.EditText;
import android.widget.TextView;

public class GuessActivity extends Activity {
	
	/**
	 * The drawing view in this activity's view
	 */
	private DrawView drawView;
	
	/**
	 * The player information 
	 */
	private Players players = new Players();

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_guess);
		
		drawView = (DrawView) this.findViewById(R.id.drawViewGuess);
		drawView.setEditable(false);
		
		// TODO
		Bundle infoFromPrevActivity = getIntent().getExtras();
		if (bundle != null) {
			drawView.loadView(bundle);
			players.loadPlayers(bundle);
		} else if (infoFromPrevActivity != null) {
			drawView.loadView(infoFromPrevActivity);
			players.loadPlayers(infoFromPrevActivity);
		}
		
		TextView playerOne = (TextView) this.findViewById(R.id.playerOne);
		TextView playerTwo = (TextView) this.findViewById(R.id.playerTwo);
		
		String playerOneInfo = players.getName(Players.PLAYERONE) + ": " + players.getScore(Players.PLAYERONE);
		String playerTwoInfo = players.getName(Players.PLAYERTWO) + ": " + players.getScore(Players.PLAYERTWO);
		
		playerOne.setText(playerOneInfo);
		playerTwo.setText(playerTwoInfo);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_guess, menu);
		return true;
	}
	
	public void onDrawPicture(View view){
		Intent intent = new Intent(this, EditActivity.class);
		Bundle bundle = new Bundle();
		players.savePlayers(bundle);
		intent.putExtras(bundle);
		startActivity(intent);
		finish();
	}
	
	public void onFinishGame(View view){
		Intent intent = new Intent(this, ClosingActivity.class);
		Bundle bundle = new Bundle();
		players.savePlayers(bundle);
		intent.putExtras(bundle);
		startActivity(intent);
		finish();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
        drawView.saveView(outState);
	}
}
