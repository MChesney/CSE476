package edu.msu.cse.boggle.droiddraw;

import java.io.Serializable;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class Drawing {
	
	// Strings for saving/loading the drawing
	public static final String DRAWING = "drawing";
	public static final String PARAMETERS = "parameters";
	public static final String COLORS = "colors";
	public static final String THICKNESSES = "thicknesses";
	public static final String START_POINTS = "start_points";
	public static final String END_POINTS = "end_points";
	public static final String EDITABLE = "editable";
	
	public static final float INITIAL_THICKNESS = (float) 5.0;
	
	/**
	 * The class for a single line segment
	 * Connected by two points, with color and thickness (stroke width)
	 */
    private class Segment {
        /**
         * Color of segment
         */
        private int color = Color.BLACK;

		/**
         * Thickness of segment
         */
        private float thickness = INITIAL_THICKNESS; 
        
        /**
         * Last point of segment
         */
        private Point lastPoint = null;
        
        /**
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
		
		/**
		 * Constructor for segment
		 */
		public Segment(Point lastPoint, Point currPoint, int color, float thickness) {
			this.lastPoint = new Point(lastPoint);
			this.currPoint = new Point(currPoint);
			this.color = color;
			this.thickness = thickness;
		}
           
    }
    
    /**
     * Local class to handle the touch status for one touch.
     * We will have one object of this type for each of the 
     * two possible touches.
     */
    private class Touch {
        /**
         * Touch id
         */
        public int id = -1;
        
        /**
         * Current touch location
         */
        public Point currTouch = new Point(0,0);       
        
        /**
         * Previous touch location
         */
        public Point lastTouch = new Point(0,0);
        
        /**
         * Change in values from previous
         */
        public Point deltas = new Point(0,0);       
        
        /**
         * Copy the current values to the previous values
         */
        public void copyToLast() {
            lastTouch.x = currTouch.x;
            lastTouch.y = currTouch.y;
        }
        
        /**
         * Compute the values of dX and dY
         */
        public void computeDeltas() {
            deltas.x = currTouch.x - lastTouch.x;
            deltas.y = currTouch.y - lastTouch.y;
        }
    }
    
    private class Point {
    	/** 
    	 * x-value of point
    	 */
    	public float x = 0;
    	
    	/**
    	 * y-value of point
    	 */
    	public float y = 0;
    	
    	/**
    	 * Constructor for Point with two integers
    	 */
    	public Point(float x, float y) {
    		this.x = x;
    		this.y = y;
    	}
    	
    	/**
    	 * Constructor for Point with Point (essentially copy constructor)
    	 */
    	public Point(Point point) {
    		this.x = point.x;
    		this.y = point.y;
    	}
    }
    
    private static final class Parameters implements Serializable {
		private static final long serialVersionUID = -8605848629519027132L;

		/**
    	 * The amount of X translation that has occurred
    	 */
    	public float translateX = 0;
    	
    	/**
    	 * The amount of Y translation that has occurred
    	 */
    	public float translateY = 0;
    	
    	/**
    	 * The drawing scale
    	 */
    	public float scale = 1;
    	
    	/**
    	 * The drawing rotation angle
    	 */
    	public float angle = 0;
    	
        /**
         * The current color
         */
        private int currColor = Color.BLACK;
        
        /**
         * The current thickness
         */
        private float currThickness = INITIAL_THICKNESS;
    	
    }
    
    /**
     * Array of segments that make up the drawing
     */
    private ArrayList<Segment> segments = new ArrayList<Segment>();
    
    /**
     * Whether or not the drawing is editable
     */
    private boolean isEditable = false;
    
    /**
     * Most recent X touch when dragging
     */
    private float lastX;
    
    /**
     * Most recent Y touch when dragging
     */
    private float lastY;
    
    /**
     * The current paint
     */
    private Paint currPaint;
    
    /**
     * First touch status
     */
    private Touch touch1 = new Touch();
    
    /**
     * Second touch status
     */
    private Touch touch2 = new Touch();
    
    /**
     * The current drawing parameters
     */
    private Parameters params = new Parameters();

	/**
     * The drawing view in this activity's view
     */
    private DrawView drawView;
	
	/**
	 * Constructor for Drawing
	 * Initializes paint color and thickness
	 */
	public Drawing(Context context, DrawView drawView) {
		this.drawView = drawView;
		currPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		currPaint.setColor(params.currColor);
		currPaint.setStrokeWidth(params.currThickness);
	}
	
    public int getCurrColor() {
		return params.currColor;
	}

	public void setCurrColor(int currColor) {
		params.currColor = currColor;
		currPaint.setColor(currColor);
	}

	public float getCurrThickness() {
		return params.currThickness;
	}

	public void setCurrThickness(float currThickness) {
		params.currThickness = currThickness;
		currPaint.setStrokeWidth(currThickness);
	}
	
	public boolean isEditable() {
		return isEditable;
	}

	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}

	/**
	 * Draw the drawing by iterating through the array of segments
	 */
	public void draw(Canvas canvas) {
		// Rotate, scale, and translate the drawing
		canvas.save();
		canvas.translate(params.translateX, params.translateY);
		canvas.rotate(params.angle);
		canvas.scale(params.scale, params.scale);
	
		float prevX, prevY, currX, currY;
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		for (Segment segment : segments) {
			prevX = segment.getLastPoint().x;
			prevY = segment.getLastPoint().y;
			currX = segment.getCurrPoint().x;
			currY = segment.getCurrPoint().y;
			paint.setColor(segment.getColor());
			paint.setStrokeWidth(segment.getThickness());
			canvas.drawLine(prevX, prevY, currX, currY, paint);
		}
		canvas.restore();
	}
	
	/**
	 * Handle a touch event from the view.
	 * @param view The view that is the source of the touch
	 * @param event The motion event describing the touch
	 * @return true if the touch is handled
	 */
	public boolean onTouchEvent(View view, MotionEvent event) {
		if (isEditable) {
			return onTouchEventEdit(view, event);
		} else {
			return onTouchEventMove(view, event);
		}
	}
	
	/**
	 * Handle a touch event from the view.
	 * This handles multitouch.
	 * @param view The view that is the source of the touch
	 * @param event The motion event describing the touch
	 * @return true if the touch is handled
	 */
	public boolean onTouchEventMove(View view, MotionEvent event) {
		int id = event.getPointerId(event.getActionIndex());
        
        switch(event.getActionMasked()) {
        case MotionEvent.ACTION_DOWN:
            touch1.id = id;
            touch2.id = -1;
            getPositions(event);
        	touch1.copyToLast();
            return true;
            
        case MotionEvent.ACTION_POINTER_DOWN:
            if(touch1.id >= 0 && touch2.id < 0) {
                touch2.id = id;
                getPositions(event);
                touch2.copyToLast();
                return true;
            }
            break;
            
        case MotionEvent.ACTION_UP:
        case MotionEvent.ACTION_CANCEL:
            touch1.id = -1;
            touch2.id = -1;
            drawView.invalidate();
            return true;
            
        case MotionEvent.ACTION_POINTER_UP:
            if(id == touch2.id) {
                touch2.id = -1;
            } else if(id == touch1.id) {
                // Make what was touch2 now be touch1 by 
                // swapping the objects.
                Touch t = touch1;
                touch1 = touch2;
                touch2 = t;
                touch2.id = -1;
            }
            drawView.invalidate();
            return true;
            
        case MotionEvent.ACTION_MOVE:
            getPositions(event);
            move();
            return true;
        }
      
        return false;
	}
	
	/**
	 * Handle a touch event from the view.
	 * This handles editing the drawing.
	 * @param view The view that is the source of the touch
	 * @param event The motion event describing the touch
	 * @return true if the touch is handled
	 */
	public boolean onTouchEventEdit(View view, MotionEvent event) {
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
        	addSegments(x, y);
        	lastX = x;
        	lastY = y;
        	view.invalidate();
        	return true;
		}
		return false;
		
		/*int id = event.getPointerId(event.getActionIndex());
        
		float x = event.getX();
		float y = event.getY();
		
		switch(event.getActionMasked()) {
		case MotionEvent.ACTION_DOWN:
            touch1.id = id;
            touch2.id = -1;
            getPositions(event);
        	touch1.copyToLast();
			lastX = x;
			lastY = y;
			return true;
			
        case MotionEvent.ACTION_POINTER_DOWN:
            if(touch1.id >= 0 && touch2.id < 0) {
                touch2.id = id;
                getPositions(event);
                touch2.copyToLast();
                return true;
            }
            break;
			
        case MotionEvent.ACTION_UP:
        case MotionEvent.ACTION_CANCEL:
            touch1.id = -1;
            touch2.id = -1;
            drawView.invalidate();
        	return true;
        	
        case MotionEvent.ACTION_POINTER_UP:
            if(id == touch2.id) {
                touch2.id = -1;
            } else if(id == touch1.id) {
                // Make what was touch2 now be touch1 by 
                // swapping the objects.
                Touch t = touch1;
                touch1 = touch2;
                touch2 = t;
                touch2.id = -1;
            }
            drawView.invalidate();
            return true;
        	
        case MotionEvent.ACTION_MOVE:
        	if (touch2.id == -1) {
            	addSegments(x, y);
            	lastX = x;
            	lastY = y;
            	drawView.invalidate();
        	} else {
        		getPositions(event);
        		move();
        	}

        	return true;
		}
		return false;*/
	}
	
    /**
     * Get the positions for the two touches and put them
     * into the appropriate touch objects.
     * @param event the motion event
     */
    private void getPositions(MotionEvent event) {
        for(int i=0;  i<event.getPointerCount();  i++) {
            
            // Get the pointer id
            int id = event.getPointerId(i);
            
            // Convert to image coordinates
            float x = event.getX(i); 
            float y = event.getY(i);
            
            
            if(id == touch1.id) {
            	touch1.copyToLast();
                touch1.currTouch.x = x;
                touch1.currTouch.y = y;
            } else if(id == touch2.id) {
            	touch2.copyToLast();
                touch2.currTouch.x = x;
                touch2.currTouch.y = y;
            }
        }
        
        drawView.invalidate();
    }
    
    /**
     * Handle movement of the touches
     */
    private void move() {
        // If no touch1, we have nothing to do
        // This should not happen, but it never hurts
        // to check.
        if(touch1.id < 0) { 
            return;
        }
        
        if(touch1.id >= 0) {
            // At least one touch
            // We are moving
            touch1.computeDeltas();
            
            params.translateX += touch1.deltas.x;
            params.translateY += touch1.deltas.y;
        }
        
        if(touch2.id >= 0) {
            // Two touches
            
            /*
             * Rotation
             */
            float angle1 = angle(touch1.lastTouch.x, touch1.lastTouch.y, touch2.lastTouch.x, touch2.lastTouch.y);
            float angle2 = angle(touch1.currTouch.x, touch1.currTouch.y, touch2.currTouch.x, touch2.currTouch.y);
            float da = angle2 - angle1;
            rotate(da, touch1.currTouch.x, touch1.currTouch.y);
            
            /*
             * Scaling
             */
            float distance1 = distance(touch1.lastTouch.x, touch1.lastTouch.y, touch2.lastTouch.x, touch2.lastTouch.y);
            float distance2 = distance(touch1.currTouch.x, touch1.currTouch.y, touch2.currTouch.x, touch2.currTouch.y);
            float dd = distance2/distance1;
            scale(dd);
        }
    }
    
    /**
     * Rotate the image around the point x1, y1
     * @param dAngle Angle to rotate in degrees
     * @param x1 rotation point x
     * @param y1 rotation point y
     */
    public void rotate(float dAngle, float x1, float y1) {
        params.angle += dAngle;
        
        // Compute the radians angle
        double rAngle = Math.toRadians(dAngle);
        float ca = (float) Math.cos(rAngle);
        float sa = (float) Math.sin(rAngle);
        float xp = (params.translateX - x1) * ca - (params.translateY - y1) * sa + x1;
        float yp = (params.translateX - x1) * sa + (params.translateY - y1) * ca + y1;

        params.translateX = xp;
        params.translateY = yp;
    }
    
    /**
     * Determine the angle for two touches
     * @param x1 Touch 1 x
     * @param y1 Touch 1 y
     * @param x2 Touch 2 x
     * @param y2 Touch 2 y
     * @return computed angle in degrees
     */
    private float angle(float x1, float y1, float x2, float y2) {
        float dx = x2 - x1;
        float dy = y2 - y1;
        return (float) Math.toDegrees(Math.atan2(dy, dx));
    }
    
    /**
     * Scale the image
     * @param dDistance Amount to scale the hat
     */   
    public void scale(float dDistance) {
    	params.scale *= dDistance;
    }
    
    /**
     * Determine the distance for two touches
     * @param x1 Touch 1 x
     * @param y1 Touch 1 y
     * @param x2 Touch 2 x
     * @param y2 Touch 2 y
     * @return computed distance in degrees
     */
    private float distance(float x1, float y1, float x2, float y2) {
    	float dx = x2 - x1;
    	float dy = y2 - y1;
    	
    	return (float) Math.sqrt(Math.pow(dx,2)+Math.pow(dy,2));
    }
    
	
	/**
	 * Every time the finger moves, add a segment to the array of segments
	 */
	public void addSegments(float x, float y) {
		// Calculate new x and y points from image scale, rotation, and translation
        double rAngle = Math.toRadians(-params.angle);
		float ca = (float) Math.cos(rAngle);
        float sa = (float) Math.sin(rAngle);
	
		float prevX, prevY, currX, currY;
		prevX = (lastX - params.translateX)*ca - (lastY - params.translateY)*sa;
		prevY = (lastX - params.translateX)*sa + (lastY - params.translateY)*ca;
		currX = (x - params.translateX)*ca - (y - params.translateY)*sa;
		currY = (x - params.translateX)*sa + (y - params.translateY)*ca;	
		
		prevX = prevX/params.scale;
		prevY = prevY/params.scale;
		currX = currX/params.scale;
		currY = currY/params.scale;	
			
		Segment segment = new Segment(new Point(prevX, prevY), new Point(currX, currY), params.currColor, params.currThickness);
		segments.add(segment);
	}
	
	/**
	 * Load the drawing 
	 */
	public void loadDrawing(Bundle bundle) {
		// Load the drawing parameters
		params = (Parameters) bundle.getSerializable(PARAMETERS);
		isEditable = bundle.getBoolean(EDITABLE);
		
		// Load the drawing segments
		int [] colors = bundle.getIntArray(COLORS);
		float [] thicknesses = bundle.getFloatArray(THICKNESSES);
		float [] startPoints = bundle.getFloatArray(START_POINTS);
		float [] endPoints = bundle.getFloatArray(END_POINTS);
		
		for (int i = 0; i < colors.length; i++) {
			Point prevPoint = new Point(startPoints[i*2], startPoints[i*2+1]);
			Point currPoint = new Point(endPoints[i*2], endPoints[i*2+1]);
			Segment segment = new Segment(prevPoint, currPoint, colors[i], thicknesses[i]);
			segments.add(segment);
		}

	}
	
	/**
	 * Save the drawing
	 */
	public void saveDrawing(Bundle bundle) {
		// Save the drawing parameters
		bundle.putSerializable(PARAMETERS, params);
		bundle.putBoolean(EDITABLE, isEditable);
		
		// Save the drawing segments
		int [] colors = new int[segments.size()];
		float [] thicknesses = new float[segments.size()];
		float [] startPoints = new float[segments.size()*2];
		float [] endPoints = new float[segments.size()*2];
		
		for (int i=0; i < segments.size(); i++) {
			Segment segment = segments.get(i);
			colors[i] = segment.getColor();
			thicknesses[i] = segment.getThickness();
			startPoints[i*2] = segment.getLastPoint().x;
			startPoints[i*2+1] = segment.getLastPoint().y;
			endPoints[i*2] = segment.getCurrPoint().x;
			endPoints[i*2+1] = segment.getCurrPoint().y;
		}
		
		bundle.putIntArray(COLORS, colors);
		bundle.putFloatArray(THICKNESSES, thicknesses);
		bundle.putFloatArray(START_POINTS, startPoints);
		bundle.putFloatArray(END_POINTS, endPoints);
	}
	
    public ArrayList<Segment> getSegments() {
    	ArrayList<Segment> segs = new ArrayList<Segment>(segments.size());
    	for (Segment item : segments) segs.add(item);
    	return segs;
    }

}
