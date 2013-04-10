package edu.msu.cse.boggle.droiddraw;

import android.os.Bundle;
import android.widget.TextView;
import android.app.Activity;


public class WaitingActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_waiting);
	
		TextView textView = (TextView) findViewById(R.id.waiting);
		textView.setText(Game.getWaitStatus());
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		TextView textView = (TextView) findViewById(R.id.waiting);
		textView.setText(Game.getWaitStatus());
	}	
	
}
