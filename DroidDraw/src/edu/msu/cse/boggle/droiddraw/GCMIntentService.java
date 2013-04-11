package edu.msu.cse.boggle.droiddraw;

import android.content.Context;
import android.content.Intent;

import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService extends GCMBaseIntentService {
	public static final String SENDER_ID = "600039815168";
	public static final String START = "start";
	public static final String DRAW = "draw";
	public static final String GUESS = "guess";
	public static final String END = "end";
	
	public static final String PLAYER = "player";
	public static final String PLAYERONESCORE = "playerOneScore";
	public static final String PLAYERTWOSCORE = "playerTwoScore";
	public static final String DRAWID = "drawid";

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
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
			
		// Game ready for someone to draw
		} else if (msg.equals(DRAW)) {
			int player = message.getIntExtra(PLAYER, -1);
			
			int playerOneScore = message.getIntExtra(PLAYERONESCORE, Game.getScore(Game.PLAYERONE));
			int playerTwoScore = message.getIntExtra(PLAYERTWOSCORE, Game.getScore(Game.PLAYERONE));
			Game.setScore(Game.PLAYERONE, playerOneScore);
			Game.setScore(Game.PLAYERTWO, playerTwoScore);
			Game.setEditor(player);
			
			if (player == Game.getSelfNumber()) {
				Intent intent = new Intent(this, EditActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			} else {
				Game.setWaitStatus(Game.WAITFORDRAW);
				Intent intent = new Intent(this, WaitingActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		
		// Game ready for someone to guess
		} else if (msg.equals(GUESS)) {
			String player = message.getStringExtra(PLAYER);
			String drawid = message.getStringExtra(DRAWID);
			
			int playerNum = -1;
			if (player.equals("1")) {
				playerNum = 1;
				//Game.setEditor(1);
			} else {
				playerNum = 2;
				//Game.setEditor(2);
			}
			
			if (playerNum == Game.getSelfNumber()) {
				// fetch drawing
				
				Intent intent = new Intent(this, GuessActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			} else {
				Game.setWaitStatus(Game.WAITFORGUESS);
				Intent intent = new Intent(this, WaitingActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		
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
