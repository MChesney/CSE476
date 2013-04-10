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
		
		if (msg.equals(START)) {
			String playerOne = message.getStringExtra("playerone");
			String playerTwo = message.getStringExtra("playertwo");
		} else if (msg.equals(DRAW)) {
			String drawid = message.getStringExtra("id");
			// fetch drawing
		} else if (msg.equals(GUESS)) {
			// fetch guess data
			// I think just updated score
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
