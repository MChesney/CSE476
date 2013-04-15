package edu.msu.cse.boggle.droiddraw;

import android.content.Context;
import android.content.Intent;

import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService extends GCMBaseIntentService {
	public static final String SENDER_ID = "600039815168";
	public static final String START = "start";
	public static final String GUESS = "guess";
	public static final String END = "end";
	
	public static final String PLAYER = "player";
	public static final String PLAYERONESCORE = "p1score";
	public static final String PLAYERTWOSCORE = "p2score";
	public static final String DRAWID = "drawid";
	public static final String DRAWER = "drawer";

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
			String gameId = message.getStringExtra("gameid");
			
			Game.setName(Game.PLAYERONE, playerOne);
			Game.setName(Game.PLAYERTWO, playerTwo);
			Game.setGameId(gameId);
			
			if (playerOne.equals(Game.getName(Game.PLAYERSELF))) {
				Intent intent = new Intent(this, EditActivity.class);
				// TODO
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			}
			if (playerTwo.equals(Game.getName(Game.PLAYERSELF))) {
				Game.setWaitStatus(Game.WAITFORTURN);
				Intent intent = new Intent(this, WaitingActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		
		// Game ready for someone to guess
		} else if (msg.equals(GUESS)) {
			Game.setDrawID(message.getStringExtra(DRAWID));			
			String drawer = message.getStringExtra(DRAWER);
			
			Integer p1score = Integer.parseInt(message.getStringExtra(PLAYERONESCORE));
			Integer p2score = Integer.parseInt(message.getStringExtra(PLAYERTWOSCORE));
			Game.setScore(Game.PLAYERONE, p1score);
			Game.setScore(Game.PLAYERTWO, p2score);
			
			if (!drawer.equals(Game.getName(Game.PLAYERSELF))) {
				Intent intent = new Intent(this, GuessActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent); 
			}
		
		// Game ending
		} else if (msg.equals(END)) {
			Game.setScore(Game.PLAYERONE, Integer.parseInt(message.getStringExtra(PLAYERONESCORE)));
			Game.setScore(Game.PLAYERTWO, Integer.parseInt(message.getStringExtra(PLAYERTWOSCORE)));
			
			Intent intent = new Intent(this, ClosingActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
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
