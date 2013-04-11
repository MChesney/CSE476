package edu.msu.cse.boggle.droiddraw;


import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Activity;
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
	Button exit;
	Button draw;
	private CountDownTimer cdTimer;
	
	/**
	 * The player information 
	 */
	private long totaltime= 130000;
	/* TODO private Game players = new Game();
	private String category="" ;
	private String hint="" ;
	private String answer="" ;*/
	
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
		exit = (Button)findViewById(R.id.finish);
		draw = (Button)findViewById(R.id.drawagain);
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
	    			  if (Game.getEditor()==2)
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
		if (Game.getEditor()==2) {
			Game.setEditor(1);
    	} else {
    		Game.setEditor(2);
    	}
		startActivity(intent);
		finish();
	}
	
	public void onFinishGame(View view) {		
		Intent intent = new Intent(this, ClosingActivity.class);
		startActivity(intent);
		finish();
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
        		Game.setScore(Game.PLAYERONE, (int)totaltime/100);
        		exit.setEnabled(true);
        	} else {
        		Game.setScore(Game.PLAYERTWO, (int)totaltime/100);
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
                // Create a cloud object and get the XML
                Cloud cloud = new Cloud();
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
                        
                            /*while(xml.nextTag() == XmlPullParser.START_TAG) {
                                if(xml.getName().equals("segment")) {
                                	
                                    drawView.loadXml(xml);
                                    break;
                                }
                                
                                Cloud.skipToEndTag(xml);
                            }*/
                        	
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
                // TODO right location???
                final boolean fail1 = fail;
            }
        }).start();
		
	}
	
}
