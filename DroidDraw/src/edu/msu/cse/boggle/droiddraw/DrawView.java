package edu.msu.cse.boggle.droiddraw;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

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
		drawing = new Drawing(context, this);
	}

	public DrawView(Context context, AttributeSet attrs) {
		super(context, attrs);
		drawing = new Drawing(context, this);
	}

	public DrawView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		drawing = new Drawing(context, this);
	}
	
	/**
	 * Draw the canvas (Drawing)
	 */
	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		drawing.draw(canvas);
	}
	
	/**
	 * Handle a touch event
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return drawing.onTouchEvent(this, event);
	}
	
	/**
	 * Get the drawing
	 */
	public void inval()
	{
		invalidate();
	}
	public Drawing getDrawing() {
		return drawing;
	}
	
	/**
	 * Set the stroke color
	 */
	public void setColor(int color) {
		drawing.setCurrColor(color);
	}
	
	/**
	 * Set the stroke thickness
	 */
	public void setThickness(float thickness) {
		drawing.setCurrThickness(thickness);
	}

	public float getThickness(){
		return drawing.getCurrThickness();
	}
	/**
	 * Find out if the canvas is editable
	 */
	public boolean isEditable() {
		return drawing.isEditable();
	}

	/**
	 * Set whether or not the canvas is editable
	 */
	public void setEditable(boolean isEditable) {
		drawing.setEditable(isEditable);
	}
	
	/**
	 * Load the drawing
	 */
	public void loadView(Bundle bundle) {
		drawing.loadDrawing(bundle);
	}
	
	/**
	 * Save the drawing
	 */
	public void saveView(Bundle bundle) {
		drawing.saveDrawing(bundle);
	}
	
    public void saveXml(XmlSerializer xml) throws IOException {
    	drawing.saveXml(xml);
    }
	
    public void loadXml(XmlPullParser xml) throws IOException, XmlPullParserException {
        drawing.loadXml(xml);
        
        post (new Runnable() {
        	@Override
        	public void run() {
        		invalidate();
        	}
        });
    }
	
}
