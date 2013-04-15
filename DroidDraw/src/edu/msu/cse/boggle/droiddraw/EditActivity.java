package edu.msu.cse.boggle.droiddraw;

import java.util.Random;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class EditActivity extends Activity {

	private static final int GOT_COLOR = 1;
	
	/**
	 * The drawing view in this activity's view
	 */
	private DrawView drawView;

	private enum Thickness{Small, Med, Large};
	private Thickness thickness = Thickness.Small;
	private float smallThickness = 5.0f;
	private float medThickness = 10.0f;
	private float largeThickness = 15.0f;
	private TextView lineButton;
	
	private Cloud cloud = new Cloud();
	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_edit);
		
		drawView = (DrawView) this.findViewById(R.id.drawViewEdit);
		drawView.setEditable(true);
		
		lineButton = (TextView) this.findViewById(R.id.linewidth);
		lineButton.setText("Line Width: S");
		
		//Bundle infoFromPrevActivity = getIntent().getExtras();
		if (bundle != null) {
			drawView.loadView(bundle);
			setThickness(drawView.getThickness());
		} /*else if (infoFromPrevActivity != null) {
			popUp();
		}*/
		createCategory();
		
		TextView playerOne = (TextView) this.findViewById(R.id.playerOne);
		TextView playerTwo = (TextView) this.findViewById(R.id.playerTwo);
		
		TextView categoryText = (TextView) this.findViewById(R.id.category);
		
		String playerOneInfo = Game.getName(Game.PLAYERONE) + ": " + Game.getScore(Game.PLAYERONE);
		String playerTwoInfo = Game.getName(Game.PLAYERTWO) + ": " + Game.getScore(Game.PLAYERTWO);
		
		playerOne.setText(playerOneInfo);
		playerTwo.setText(playerTwoInfo);
		
		categoryText.setText(Game.getCategory());
		
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
			Game.setCategory("Animal");
			break;
		case 1:
			Game.setCategory("Building");
			break;
		case 2:
			Game.setCategory("Object");
			break;
		case 3:
			Game.setCategory("Action");
			break;
		case 4: 
			Game.setCategory("MSU");
			break;
		}
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

		final ContextWrapper activity = this;
		final Handler mainHandler = new Handler(this.getMainLooper());
		
		Game.setHint(((EditText)findViewById(R.id.clueEdit)).getText().toString());
		Game.setAnswer(((EditText)findViewById(R.id.answerEdit)).getText().toString());
		
		new Thread(new Runnable() {
			@Override
            public void run() {
				
				final boolean didFinishDrawing = cloud.addDrawing(drawView);
			
				mainHandler.post(new Runnable() {

                    @Override
                    public void run() {
                        if(didFinishDrawing) {
                        	Game.setWaitStatus(Game.WAITFORGUESS);
                        	Intent intent = new Intent(activity,  WaitingActivity.class);
                        	//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    		startActivity(intent);
                    		finish();
                        } else {
                            // Failure
                        	// TODO two users already logged in
                        	Toast.makeText(activity, "Problem with OnDoneButton", Toast.LENGTH_SHORT).show();
                        }
                    }    
				});
			}
		}).start();
		
	}
	
	/**
	 * Do nothing when back is pressed
	 * TODO
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
	}
}
