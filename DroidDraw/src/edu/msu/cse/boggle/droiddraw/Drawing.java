package edu.msu.cse.boggle.droiddraw;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class Drawing {
	
	public static final float INITIAL_THICKNESS = (float) 5.0;
	
	/*
	 * The class for a single line segment
	 * Connected by two points, with color and thickness (stroke width)
	 */
    private class Segment {
        /**
         * Color of segment
         */
        private int color = Color.BLACK;

		/*
         * Thickness of segment
         */
        private float thickness = INITIAL_THICKNESS; 
        
        /*
         * Last point of segment
         */
        private Point lastPoint = null;
        
        /*
         * Current point of segment
         */
        private Point currPoint = null;
        
		public Point getLastPoint() {
			return lastPoint;
		}

		public Point getCurrPoint() {
			return currPoint;
		}   
		
		public int getColor() {
			return color;
		}
		
		public float getThickness() {
			return thickness;
		}
		
		/*
		 * Constructor for segment
		 */
		public Segment(Point lastPoint, Point currPoint, int color, float thickness) {
			this.lastPoint = new Point(lastPoint);
			this.currPoint = new Point(currPoint);
			this.color = color;
			this.thickness = thickness;
		}
           
    }
    
    private class Point {
    	/* 
    	 * x-value of point
    	 */
    	public float x = 0;
    	
    	/*
    	 * y-value of point
    	 */
    	public float y = 0;
    	
    	/*
    	 * Constructor for Point with two integers
    	 */
    	public Point(float x, float y) {
    		this.x = x;
    		this.y = y;
    	}
    	
    	/*
    	 * Constructor for Point with Point (essentially copy constructor)
    	 */
    	public Point(Point point) {
    		this.x = point.x;
    		this.y = point.y;
    	}
    }
    
    /*
     * Array of segments that make up the drawing
     */
    private ArrayList<Segment> segments = new ArrayList<Segment>();
    
    /**
     * Most recent X touch when dragging
     */
    private float lastX;
    
    /**
     * Most recent Y touch when dragging
     */
    private float lastY;
    
    /*
     * The current color
     */
    private int currColor = Color.BLACK;
    
    /*
     * The current paint
     */
    private Paint currPaint;
    
    /*
     * The current thickness
     */
    private float currThickness = INITIAL_THICKNESS;

	/*
     * The drawing view in this activity's view
     */
    //private DrawView drawView;
    
    public int getCurrColor() {
		return currColor;
	}

	public void setCurrColor(int currColor) {
		this.currColor = currColor;
		currPaint.setColor(currColor);
	}

	public float getCurrThickness() {
		return currThickness;
	}

	public void setCurrThickness(float currThickness) {
		this.currThickness = currThickness;
		currPaint.setStrokeWidth(currThickness);
	}
	
	/*
	 * Constructor for Drawing
	 * Initializes paint color and thickness
	 */
	public Drawing(Context context, DrawView drawView) {
		//this.drawView = drawView;
		currPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		currPaint.setColor(currColor);
		currPaint.setStrokeWidth(currThickness);
	}
	
	/*
	 * Draw the drawing by iterating through the array of segments
	 */
	public void draw(Canvas canvas) {
		float lastX, lastY, currX, currY;
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		for (Segment segment : segments) {
			lastX = segment.getLastPoint().x;
			lastY = segment.getLastPoint().y;
			currX = segment.getCurrPoint().x;
			currY = segment.getCurrPoint().y;
			paint.setColor(segment.getColor());
			paint.setStrokeWidth(segment.getThickness());
			canvas.drawLine(lastX, lastY, currX, currY, paint);
		}
	}
	
	/*
	 * Handle a touch event from the view.
	 * @param view The view that is the source of the touch
	 * @param event The motion event describing the touch
	 * @return true if the touch is handled
	 */
	public boolean onTouchEvent(View view, MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		
		switch(event.getActionMasked()) {
		case MotionEvent.ACTION_DOWN:
			lastX = x;
			lastY = y;
			return true;
			
        case MotionEvent.ACTION_UP:
        case MotionEvent.ACTION_CANCEL:
        	return true;
        	
        case MotionEvent.ACTION_MOVE:
        	move(x, y);
        	lastX = x;
        	lastY = y;
        	view.invalidate();
        	return true;
		}
		return false;
	}
	
	/*
	 * Every time the finger moves, add a segment to the array of segments
	 */
	public void move(float x, float y) {
		Segment segment = new Segment(new Point(lastX, lastY), new Point(x, y), currColor, currThickness);
		segments.add(segment);
	}

}
