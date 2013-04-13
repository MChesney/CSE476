package edu.msu.cse.boggle.droiddraw;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService extends GCMBaseIntentService {
	public static final String SENDER_ID = "600039815168";
	public static final String START = "start";
	public static final String DRAW = "draw";
	public static final String GUESS = "guess";
	public static final String END = "end";
	
	public static final String PLAYER = "player";
	public static final String PLAYERONESCORE = "p1Score";
	public static final String PLAYERTWOSCORE = "p2Score";
	public static final String DRAWID = "drawid";
	
	GuessActivity guessActivity = new GuessActivity();

	public GCMIntentService() {
		super(SENDER_ID);
		// TODO Auto-generated constructor stub
	}

	public GCMIntentService(String... senderIds) {
		super(senderIds);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onError(Context context, String message) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onMessage(Context context, Intent message) {
		String msg = message.getStringExtra("message");
		
		// Game starting
		if (msg.equals(START)) {
			String playerOne = message.getStringExtra("playerOne");
			String playerTwo = message.getStringExtra("playerTwo");
			
			Game.setName(Game.PLAYERONE, playerOne);
			Game.setName(Game.PLAYERTWO, playerTwo);
			Game.setEditor(1);
			
			if (playerOne.equals(Game.getName(Game.PLAYERSELF))) {
				Game.setSelfNumber(1);
				Intent intent = new Intent(this, EditActivity.class);
				// TODO
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			}
			if (playerTwo.equals(Game.getName(Game.PLAYERSELF))) {
				Game.setSelfNumber(2);
				Game.setWaitStatus(Game.WAITFORDRAW);
				Intent intent = new Intent(this, WaitingActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		
		// Game ready for someone to guess
		} else if (msg.equals(GUESS)) {
			Game.setDrawID(message.getStringExtra(DRAWID));
			
			//Integer playerOneScore = Integer.getInteger(message.getStringExtra(PLAYERONESCORE));
			//Integer playerTwoScore = Integer.getInteger(message.getStringExtra(PLAYERTWOSCORE));
			//Game.setScore(Game.PLAYERONE, playerOneScore);
			//Game.setScore(Game.PLAYERTWO, playerTwoScore);
				
			Intent intent = new Intent(this, GuessActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent); 
		
		// Game ending
		} else if (msg.equals(END)) {
			// end the game
		}
	}

	@Override
	protected void onRegistered(Context context, String message) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onUnregistered(Context context, String message) {
		// TODO Auto-generated method stub

	}

}
