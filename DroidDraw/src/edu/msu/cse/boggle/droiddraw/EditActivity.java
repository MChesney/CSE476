package edu.msu.cse.boggle.droiddraw;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

public class EditActivity extends Activity {

	private static final int GOT_COLOR = 1;
	
	/*
	 * The drawing view in this activity's view
	 */
	private DrawView drawView;
	
	/*
	 * The players
	 */
	private Players players = new Players();
	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_edit);
		
		drawView = (DrawView) this.findViewById(R.id.drawViewEdit);
		drawView.setEditable(true);
		
		Bundle infoFromPrevActivity = getIntent().getExtras();
		if (bundle != null) {
			drawView.loadView(bundle);
			players.loadPlayers(bundle);
		} else if (infoFromPrevActivity != null) {
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
	protected void onSaveInstanceState(Bundle bundle) {
		super.onSaveInstanceState(bundle);
		
		drawView.saveView(bundle);
		players.savePlayers(bundle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_edit, menu);
		return true;
	}
	
	public void onDoneButton(View view){
		Intent intent = new Intent(this, GuessActivity.class);
		Bundle bundle = new Bundle();
		drawView.saveView(bundle);
		players.savePlayers(bundle);
		intent.putExtras(bundle);
		startActivity(intent);
		finish();
	}
	
	public void onLineColor(View view){
		Intent intent = new Intent(this,  ColorSelectActivity.class);
		startActivityForResult(intent, GOT_COLOR);
	}
	
	public void onToggleEdit(View view) {
		ToggleButton toggle = (ToggleButton) this.findViewById(R.id.toggleDrawMove);
		boolean isMoving = toggle.isChecked();
		drawView.setEditable(!isMoving);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == GOT_COLOR && resultCode == Activity.RESULT_OK) {
			// This is a color response
			int color = data.getIntExtra(ColorSelectActivity.COLOR, Color.BLACK);
			drawView.setColor(color);
		
		}
	}

}
