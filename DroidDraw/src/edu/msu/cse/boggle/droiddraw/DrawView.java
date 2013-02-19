package edu.msu.cse.boggle.droiddraw;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class DrawView extends View {
	
	/**
	 * The actual drawing
	 */
	private Drawing drawing;
	
	/**
	 * If the drawing is editable (can draw lines) or can only be manipulated
	 */
	private boolean isEditable;

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
		isEditable = false;
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		drawing.draw(canvas);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (isEditable) {
			return drawing.onTouchEventEditable(this, event);
		} else {
			return drawing.onTouchEvent(this, event);
		}
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
		return isEditable;
	}

	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}
	
}
