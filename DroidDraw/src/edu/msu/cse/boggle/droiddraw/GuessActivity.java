package edu.msu.cse.boggle.droiddraw;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
//import android.widget.EditText;
import android.widget.TextView;

public class GuessActivity extends Activity {
	
	/**
	 * The drawing view in this activity's view
	 */
	private DrawView drawView;
	TextView hintdisplay;
	TextView timerText;
	TextView answerText;
	EditText edit;
	Button guess;
	Button exit;
	private CountDownTimer cdTimer;
	//private Integer count=1;
	//MediaPlayer mp = MediaPlayer.create(this, R.raw.lose);
	
	/**
	 * The player information 
	 */
	private long totaltime= 130000;
	private Players players = new Players();
	private String category="" ;
	private String hint="" ;
	private String answer="" ;
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_guess);
		
		drawView = (DrawView) this.findViewById(R.id.drawViewGuess);
		
		Bundle infoFromPrevActivity = getIntent().getExtras();
		if (bundle != null) {
			drawView.loadView(bundle);
			players.loadPlayers(bundle);
			//hint=bundle.getString("clue");
			
		} else if (infoFromPrevActivity != null) {
			drawView.loadView(infoFromPrevActivity);
			players.loadPlayers(infoFromPrevActivity);
		}
		
		
		category = players.getCategory();
		drawView.setEditable(false);
		hint=this.getIntent().getExtras().getString("clue");
		answer=this.getIntent().getExtras().getString("answer");
		TextView playerOne = (TextView) this.findViewById(R.id.playerOne);
		TextView playerTwo = (TextView) this.findViewById(R.id.playerTwo);
		TextView categoryText = (TextView) this.findViewById(R.id.category);
		String playerOneInfo = players.getName(Players.PLAYERONE) + ": " + players.getScore(Players.PLAYERONE);
		String playerTwoInfo = players.getName(Players.PLAYERTWO) + ": " + players.getScore(Players.PLAYERTWO);
		
		playerOne.setText(playerOneInfo);
		playerTwo.setText(playerTwoInfo);
		categoryText.setText(category);
		 edit=(EditText) findViewById(R.id.editText1);
		 guess= (Button)findViewById(R.id.guess);
		 exit= (Button)findViewById(R.id.button1);
		 hintdisplay = (TextView) this.findViewById(R.id.clue);
		 timerText = (TextView) this.findViewById(R.id.time);  
		 answerText=(TextView) this.findViewById(R.id.Answer);  
		 if (players.getEditor()==1)
			{
			exit.setEnabled(false);
			players.setEditor(2);
			}
		
     	else
     		players.setEditor(1);
		
		 final MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.lose);
     	
	      cdTimer=  new CountDownTimer(totaltime, 1000) {  
	            public void onTick(long m) {  
	            totaltime= m;
	            timerText.setText("Time Remaining: "+m/1000);
	            answerText.setText("");
	             if(m/1000 <=60)
	            {
	            	hintdisplay.setText(hint);
	            }
	            
	            }
	            public void onFinish() {  
	            	timerText.setText("Done!");
	            	
	            	answerText.setText(answer);
	            	mp.start();
		            
	            	edit.setEnabled(false);
	            	 guess.setEnabled(false);
	            	
	            }  
	        }.start(); 
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
	
	public void onGuessButton(View view){
		if((edit.getText().toString().toLowerCase()).equals(answer.toLowerCase()))
		{
			final MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.win);
			mp.start();
			timerText.setText("Done!");
			cdTimer.cancel();
        	answerText.setText("YOU WIN!");
        	edit.setEnabled(false);
        	guess.setEnabled(false);
        	if (players.getEditor()==1)
        	{
        		players.setScore("Players.one", 10);
        	}
        	else
        		players.setScore("Players.two", 10);
		}
		else
		{
			edit.setText("");
			answerText.setText("Wrong");
		}
		
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
        drawView.saveView(outState);
        players.savePlayers(outState);
	}
	
}
