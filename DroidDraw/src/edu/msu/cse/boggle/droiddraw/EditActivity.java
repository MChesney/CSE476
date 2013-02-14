package edu.msu.cse.boggle.droiddraw;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;

public class EditActivity extends Activity {

	private static final int GOT_COLOR = 1;
	
	/*
	 * The drawing view in this activity's view
	 */
	private DrawView drawView;
	
	/*
	 * The players
	 */
	//private Players players = new Players();
	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_edit);
		
		drawView = (DrawView) this.findViewById(R.id.drawViewEdit);
		
		/*if (bundle != null) {
			drawView.loadInstanceState(bundle);
		}*/
	}
	
	/*@Override
	protected void onSaveInstanceState(Bundle bundle) {
		super.onSaveInstanceState(bundle);
		
		drawView.saveInstanceState(bundle);
	}*/

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_edit, menu);
		return true;
	}
	
	public boolean onDoneButton(View view){
		Intent intent = new Intent(this, GuessActivity.class);
		startActivity(intent);
		return true;
	}
	
	public boolean onLineColor(View view){
		Intent intent = new Intent(this,  ColorSelectActivity.class);
		startActivityForResult(intent, GOT_COLOR);
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == GOT_COLOR && resultCode == Activity.RESULT_OK) {
			// This is a color response
			int color = data.getIntExtra(ColorSelectActivity.COLOR, Color.BLACK);
			drawView.setColor(color);
		
		}
	}
		

}
