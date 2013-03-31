package edu.msu.cse.boggle.droiddraw;

import java.util.Random;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

public class EditActivity extends Activity {

	private String category = "";
	private static final int GOT_COLOR = 1;
	
	/**
	 * The drawing view in this activity's view
	 */
	private DrawView drawView;

	/**
	 * The players
	 */
	private Players players = new Players();
	private enum Thickness{Small, Med, Large};
	private Thickness thickness = Thickness.Small;
	private float smallThickness = 5.0f;
	private float medThickness = 10.0f;
	private float largeThickness = 15.0f;
	private TextView lineButton;
	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_edit);
		
		drawView = (DrawView) this.findViewById(R.id.drawViewEdit);
		drawView.setEditable(true);
		
		lineButton = (TextView) this.findViewById(R.id.linewidth);
		lineButton.setText("Line Width: S");
		
		Bundle infoFromPrevActivity = getIntent().getExtras();
		if (bundle != null) {
			drawView.loadView(bundle);
			players.loadPlayers(bundle);
			category = players.getCategory();
			setThickness(drawView.getThickness());
		} else if (infoFromPrevActivity != null) {
			players.loadPlayers(infoFromPrevActivity);
			createCategory();
			popUp();
		}
		
		
		TextView playerOne = (TextView) this.findViewById(R.id.playerOne);
		TextView playerTwo = (TextView) this.findViewById(R.id.playerTwo);
		
		TextView categoryText = (TextView) this.findViewById(R.id.category);
		
		String playerOneInfo = players.getName(Players.PLAYERONE) + ": " + players.getScore(Players.PLAYERONE);
		String playerTwoInfo = players.getName(Players.PLAYERTWO) + ": " + players.getScore(Players.PLAYERTWO);
		
		playerOne.setText(playerOneInfo);
		playerTwo.setText(playerTwoInfo);
		
		categoryText.setText(category);
		
	}
	
	/**
	 * Alert the players as to whose turn it is
	 */
	private void popUp(){
		AlertDialog.Builder builder = new AlertDialog.Builder(drawView.getContext());
	        
	    // Parameterize the builder
	    builder.setTitle("Player Turn");
	    if (players.getEditor() == 1){
	    	builder.setMessage(players.getName(Players.PLAYERONE) + " turn");
	    } else {
	    	builder.setMessage(players.getName(Players.PLAYERTWO) + " turn");
	    }
	    builder.setPositiveButton(android.R.string.ok, null);
	        
	    // Create the dialog box and show it
	    AlertDialog alertDialog = builder.create();
	    alertDialog.show();
	}
	
	/** 
	 * Set the thickness of the paint brush
	 * I believe only used in OnCreate()
	 */
	private void setThickness(float amount){
		if(amount == smallThickness){
			thickness = Thickness.Small;
			lineButton.setText("Line Width: S");
		}else if(amount == medThickness){
			thickness = Thickness.Med;
			lineButton.setText("Line Width: M");
		}else if(amount == largeThickness){
			thickness = Thickness.Large;
			lineButton.setText("Line Width: L");
		}
	}
	
	/**
	 * Pick the category to be used for this round
	 */
	private void createCategory(){
		Random random = new Random();
		int num = random.nextInt(5);
		switch(num){
		case 0:
			category = "Animal";
			break;
		case 1:
			category = "Building";
			break;
		case 2:
			category = "Object";
			break;
		case 3:
			category = "Action";
			break;
		case 4: 
			category = "MSU";
			break;
		
		}
		players.setCategory(category);
	}
	
	public void onThicknessButton(View view){
		switch(thickness){
		case Small:
			drawView.setThickness(medThickness);
			thickness = Thickness.Med;
			lineButton.setText("Line Width: M");
			break;
		case Med:
			drawView.setThickness(largeThickness);
			thickness = Thickness.Large;
			lineButton.setText("Line Width: L");
			break;
		case Large:
			drawView.setThickness(smallThickness);
			thickness = Thickness.Small;
			lineButton.setText("Line Width: S");
			break;
		}
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
	
	public void onDoneButton(View view){
		Intent intent = new Intent(this, GuessActivity.class);
		Bundle bundle = new Bundle();
		drawView.saveView(bundle);
		players.savePlayers(bundle);
		intent.putExtras(bundle);
		String cluename = ((EditText)findViewById(R.id.clueEdit)).getText().toString();
		intent.putExtra("clue", cluename);
		String ansname = ((EditText)findViewById(R.id.answerEdit)).getText().toString();
		intent.putExtra("answer", ansname);
		startActivity(intent);
		finish();
	}
	
	/**
	 * Do nothing when back is pressed
	 */
	@Override
	public void onBackPressed() {}
		
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == GOT_COLOR && resultCode == Activity.RESULT_OK) {
			// This is a color response
			int color = data.getIntExtra(ColorSelectActivity.COLOR, Color.BLACK);
			drawView.setColor(color);
		
		}
	}
	
	@Override
	protected void onSaveInstanceState(Bundle bundle) {
		super.onSaveInstanceState(bundle);
		
		drawView.saveView(bundle);
		players.savePlayers(bundle);
		
		
	}
}
