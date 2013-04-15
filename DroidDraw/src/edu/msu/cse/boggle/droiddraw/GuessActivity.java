package edu.msu.cse.boggle.droiddraw;


import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.app.Activity;
import android.content.ContextWrapper;
import android.content.Intent;
import android.util.Xml;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class GuessActivity extends Activity {
	
	private static final String TIME = "time";
	private static final String ANS = "ans";
	
	private Cloud cloud = new Cloud();
	
	/**
	 * The drawing view in this activity's view
	 */
	private DrawView drawView;
	TextView hintdisplay;
	TextView timerText;
	TextView answerText;
	EditText edit;
	Button guess;
	Button draw;
	
	private CountDownTimer cdTimer;
	private long totaltime = 130000;
	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_guess);	
		
		drawView = (DrawView) this.findViewById(R.id.drawViewGuess);
		
		//Bundle infoFromPrevActivity = getIntent().getExtras();
		answerText=(TextView) this.findViewById(R.id.Answer);
		if (bundle != null) {
			drawView.loadView(bundle);
			
			totaltime = bundle.getLong(TIME);
			answerText.setText(bundle.getCharSequence(ANS));
			
		} else {
			loadCloudDrawing();
			//if (infoFromPrevActivity != null) {
			//drawView.loadView(infoFromPrevActivity);
		}
		
		TextView playerOne = (TextView) this.findViewById(R.id.playerOne);
		TextView playerTwo = (TextView) this.findViewById(R.id.playerTwo);		
		TextView categoryText = (TextView) this.findViewById(R.id.category);
		
		String playerOneInfo = Game.getName(Game.PLAYERONE) + ": " + Game.getScore(Game.PLAYERONE);
		String playerTwoInfo = Game.getName(Game.PLAYERTWO) + ": " + Game.getScore(Game.PLAYERTWO);
		
		playerOne.setText(playerOneInfo);
		playerTwo.setText(playerTwoInfo);
		
		categoryText.setText(Game.getCategory());
		
		drawView.setEditable(false);
		edit = (EditText) findViewById(R.id.editText1);
		guess = (Button)findViewById(R.id.guess);
		draw = (Button)findViewById(R.id.drawagain);
		draw.setEnabled(false);
		hintdisplay = (TextView) this.findViewById(R.id.clue);
		timerText = (TextView) this.findViewById(R.id.time);  
     	
	    cdTimer = new CountDownTimer(totaltime, 1000) {  
	    	  public void onTick(long m) {  
	            	totaltime= m;
	            
	            	timerText.setText("Time Left: " + m/1000);
	            	if(m/1000 <=60)
	            	{
	            		hintdisplay.setText("Clue: " + Game.getHint());
	            	}
	    	  }
	            
	    	  public void onFinish() {  
	            	
	    		  if (!(answerText.getText().toString()).equals("YOU WIN!")) {
	    			  answerText.setText("Answer: "+ Game.getAnswer());
	    			  timerText.setText("Done!");
	    			  edit.setEnabled(false);
	    			  guess.setEnabled(false);
	    			  draw.setEnabled(true);
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
		if (Game.getEditor()==2) {
			Game.setEditor(1);
    	} else {
    		Game.setEditor(2);
    	}
		Intent intent = new Intent(this, EditActivity.class);
		startActivity(intent);
		finish();
	}
	
	public void onFinishGame(View view){

		final ContextWrapper activity = this;
		final Handler mainHandler = new Handler(this.getMainLooper());
		
		new Thread(new Runnable() {
			@Override
            public void run() {
				
				final boolean didEndGame = cloud.finishGame();
			
				mainHandler.post(new Runnable() {

                    @Override
                    public void run() {
                        if(didEndGame) {
                        	Intent intent = new Intent(activity,  ClosingActivity.class);
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
	
	public void onGuessButton(View view) {
		if((edit.getText().toString().toLowerCase(Locale.getDefault()).replaceAll(" ", "")).equals(Game.getAnswer().toLowerCase(Locale.getDefault()).replaceAll(" ", "")))
		{
			final MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.win);
			mp.start();
			
			timerText.setText("Done!");
			cdTimer.cancel();
        	answerText.setText("YOU WIN!");
        	edit.setEnabled(false);
        	guess.setEnabled(false);
        	 draw.setEnabled(true);
        	if (Game.getEditor()==2) {
        		Game.incScore(Game.PLAYERONE, (int)totaltime/100);
        	} else {
        		Game.incScore(Game.PLAYERTWO, (int)totaltime/100);
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
	}
	
	private void loadCloudDrawing() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                InputStream stream = cloud.loadDrawing();
                
                // Test for an error
                boolean fail = stream == null;
                if(!fail) {
                    try {
                        XmlPullParser xml = Xml.newPullParser();
                        xml.setInput(stream, "UTF-8");       
                        
                        xml.nextTag();      // Advance to first tag
                        xml.require(XmlPullParser.START_TAG, null, "droiddraw");
                        String status = xml.getAttributeValue(null, "status");
                        if(status.equals("yes")) {
                        	Game.setHint(xml.getAttributeValue(null, "hint"));
                        	Game.setAnswer(xml.getAttributeValue(null, "answer"));
                        	Game.setCategory(xml.getAttributeValue(null, "category"));
                        	Game.setScore(Game.PLAYERONE, Integer.parseInt(xml.getAttributeValue(null, "p1score")));
                        	Game.setScore(Game.PLAYERTWO, Integer.parseInt(xml.getAttributeValue(null, "p2score")));
                        	
                        	drawView.loadXml(xml);
                        	
                        } else {
                            fail = true;
                        }
                        
                    } catch(IOException ex) {
                        fail = true;
                    } catch(XmlPullParserException ex) {
                        fail = true;
                    } finally {
                        try {
                            stream.close(); 
                        } catch(IOException ex) {
                        }
                    }
                }
            }
        }).start();
		
	}
	
}
