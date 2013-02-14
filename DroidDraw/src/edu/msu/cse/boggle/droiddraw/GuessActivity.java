package edu.msu.cse.boggle.droiddraw;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
//import android.widget.EditText;

public class GuessActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guess);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_guess, menu);
		return true;
		//testing
	}
	public void onDrawPicture(View view){
		Intent intent = new Intent(this, EditActivity.class);
		startActivity(intent);
		//works
	
		
	}
	public void onExit(View view){
		Intent intent = new Intent(this, ClosingActivity.class);
		startActivity(intent);
		//works
	
		
	}
}
