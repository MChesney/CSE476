package edu.msu.cse.boggle.droiddraw;


import java.util.Locale;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class GuessActivity extends Activity {
	
	private static final String TIME = "time";
	private static final String ANS = "ans";
	private static final String ANSWER = "answer";
	private static final String CLUE = "clue";
	
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
	Button draw;
	private CountDownTimer cdTimer;
	
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
		answerText=(TextView) this.findViewById(R.id.Answer);
		if (bundle != null) {
			drawView.loadView(bundle);
			players.loadPlayers(bundle);
			
			totaltime = bundle.getLong(TIME);
			answerText.setText(bundle.getCharSequence(ANS));
			
		} else if (infoFromPrevActivity != null) {
			drawView.loadView(infoFromPrevActivity);
			players.loadPlayers(infoFromPrevActivity);
		}
		
			
		hint=this.getIntent().getExtras().getString(CLUE);
		answer=this.getIntent().getExtras().getString(ANSWER);
		TextView playerOne = (TextView) this.findViewById(R.id.playerOne);
		TextView playerTwo = (TextView) this.findViewById(R.id.playerTwo);
		
		TextView categoryText = (TextView) this.findViewById(R.id.category);
		
		String playerOneInfo = players.getName(Players.PLAYERONE) + ": " + players.getScore(Players.PLAYERONE);
		String playerTwoInfo = players.getName(Players.PLAYERTWO) + ": " + players.getScore(Players.PLAYERTWO);
		
		playerOne.setText(playerOneInfo);
		playerTwo.setText(playerTwoInfo);
		
		category = players.getCategory();
		categoryText.setText(category);
		
		drawView.setEditable(false);
		edit=(EditText) findViewById(R.id.editText1);
		guess= (Button)findViewById(R.id.guess);
		exit= (Button)findViewById(R.id.finish);
		draw= (Button)findViewById(R.id.drawagain);
		draw.setEnabled(false);
		exit.setEnabled(false);
		hintdisplay = (TextView) this.findViewById(R.id.clue);
		timerText = (TextView) this.findViewById(R.id.time);  
     	
	    cdTimer = new CountDownTimer(totaltime, 1000) {  
	    	  public void onTick(long m) {  
	            	totaltime= m;
	            
	            	timerText.setText("Time Left: " + m/1000);
	            	if(m/1000 <=60)
	            	{
	            		hintdisplay.setText("Clue: " + hint);
	            	}
	    	  }
	            
	    	  public void onFinish() {  
	            	
	    		  if (!(answerText.getText().toString()).equals("YOU WIN!")) {
	    			  answerText.setText("Answer: "+ answer);
	    			  timerText.setText("Done!");
	    			  edit.setEnabled(false);
	    			  guess.setEnabled(false);
	    			  draw.setEnabled(true);
	    			  if (players.getEditor()==2)
	    			  {
	    				  exit.setEnabled(true);
	    			  }
	    		  }
	    	  }  
	    }.start(); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_guess, menu);
		return true;
	}
	
	public void onDrawPicture(View view) {
		Intent intent = new Intent(this, EditActivity.class);
		Bundle bundle = new Bundle();
		if (players.getEditor()==2) {
			players.setEditor(1);
    	} else {
    		players.setEditor(2);
    	}
		players.savePlayers(bundle);
		intent.putExtras(bundle);
		startActivity(intent);
		finish();
	}
	
	public void onFinishGame(View view) {		
		Intent intent = new Intent(this, ClosingActivity.class);
		Bundle bundle = new Bundle();
		players.savePlayers(bundle);
		intent.putExtras(bundle);
		startActivity(intent);
		finish();
	}
	
	public void onGuessButton(View view) {
		if((edit.getText().toString().toLowerCase(Locale.getDefault()).replaceAll(" ", "")).equals(answer.toLowerCase(Locale.getDefault()).replaceAll(" ", "")))
		{
			final MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.win);
			mp.start();
			
			timerText.setText("Done!");
			cdTimer.cancel();
        	answerText.setText("YOU WIN!");
        	edit.setEnabled(false);
        	guess.setEnabled(false);
        	 draw.setEnabled(true);
        	if (players.getEditor()==2) {
        		players.setScore(Players.PLAYERONE, (int)totaltime/100);
        		exit.setEnabled(true);
        	} else {
        		players.setScore(Players.PLAYERTWO, (int)totaltime/100);
        	}
        	totaltime=0;
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
		
		outState.putLong(TIME, totaltime);
		outState.putCharSequence(ANS, answerText.getText());
        drawView.saveView(outState);
        players.savePlayers(outState);
	}
	
}
