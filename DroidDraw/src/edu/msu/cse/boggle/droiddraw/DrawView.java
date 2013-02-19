package edu.msu.cse.boggle.droiddraw;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class DrawView extends View {
	
	/**
	 * The actual drawing
	 */
	private Drawing drawing;

	public DrawView(Context context) {
		super(context);
		init(context);
	}

	public DrawView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public DrawView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	
	private void init(Context context) {
		drawing = new Drawing(context, this);
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		drawing.draw(canvas);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return drawing.onTouchEvent(this, event);
	}
	
	public Drawing getDrawing() {
		return drawing;
	}
	
	public void setColor(int color) {
		drawing.setCurrColor(color);
	}
	
	public void setThickness(float thickness) {
		drawing.setCurrThickness(thickness);
	}

	public boolean isEditable() {
		return drawing.isEditable();
	}

	public void setEditable(boolean isEditable) {
		drawing.setEditable(isEditable);
	}
	
	public void loadView(Bundle bundle) {
		drawing.loadDrawing(bundle);
	}
	
	public void saveView(Bundle bundle) {
		drawing.saveDrawing(bundle);
	}
	
}
