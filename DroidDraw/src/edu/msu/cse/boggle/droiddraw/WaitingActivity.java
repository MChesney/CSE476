package edu.msu.cse.boggle.droiddraw;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.ContextWrapper;


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
	
	/*@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
		final ContextWrapper activity = this;
		final Handler mainHandler = new Handler(this.getMainLooper());
		
		new Thread(new Runnable() {
			@Override
            public void run() {
				
				Cloud cloud = new Cloud();
				final boolean didEndGame = cloud.finishGame();
			
				mainHandler.post(new Runnable() {

                    @Override
                    public void run() {
                        if(!didEndGame) {
                        	Toast.makeText(activity, "Problem with Connection", Toast.LENGTH_SHORT).show();
                        } else {
                        	finish();
                        }
                    }    
				});
			}
		}).start();
	}*/
	
}
