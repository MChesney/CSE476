package edu.msu.cse.boggle.droiddraw;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class ClosingActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_closing);
		
		Players players = new Players();
		
		Bundle infoFromPrevActivity = getIntent().getExtras();
		if (infoFromPrevActivity != null) {
			players.loadPlayers(infoFromPrevActivity);
		}
		
		TextView winnerName = (TextView) this.findViewById(R.id.winnerName);
		TextView winnerScore = (TextView) this.findViewById(R.id.winnerScore);
		TextView loserName = (TextView) this.findViewById(R.id.loserName);
		TextView loserScore = (TextView) this.findViewById(R.id.loserScore);
		
		if (players.getScore(Players.PLAYERONE) > players.getScore(Players.PLAYERTWO)) {
			winnerName.setText(players.getName(Players.PLAYERONE));
			winnerScore.setText(""+players.getScore(Players.PLAYERONE));
			
			loserName.setText(players.getName(Players.PLAYERTWO));
			loserScore.setText(""+players.getScore(Players.PLAYERTWO));			
		} else {
			winnerName.setText(players.getName(Players.PLAYERTWO));
			winnerScore.setText(""+players.getScore(Players.PLAYERTWO));
			
			loserName.setText(players.getName(Players.PLAYERONE));
			loserScore.setText(""+players.getScore(Players.PLAYERONE));
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_closing, menu);
		return true;
	}
	
	public void onNewGame(View view) {
		Intent intent = new Intent(this, OpeningActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		finish();
	}

}
