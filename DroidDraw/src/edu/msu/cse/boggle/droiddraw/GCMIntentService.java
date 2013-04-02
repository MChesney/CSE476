package edu.msu.cse.boggle.droiddraw;

import android.content.Context;
import android.content.Intent;

import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService extends GCMBaseIntentService {
	public static final String SENDER_ID = "600039815168";

	public GCMIntentService() {
		super(SENDER_ID);
		// TODO Auto-generated constructor stub
	}

	public GCMIntentService(String... senderIds) {
		super(senderIds);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onError(Context arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onMessage(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onRegistered(Context arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onUnregistered(Context arg0, String arg1) {
		// TODO Auto-generated method stub

	}

}
