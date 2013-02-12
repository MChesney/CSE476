package edu.msu.cse.boggle.droiddraw;

import java.util.ArrayList;

import android.graphics.Color;

public class Drawing {
	
	public static final float INITIAL_THICKNESS = (float) 1.0;
	
    private class Segment {

        /**
         * Color of segment
         */
        public int color = Color.BLACK;
        
        /*
         * Thickness of segment
         */
        public float thickness = INITIAL_THICKNESS; 
        
        /*
         * Point 1 of segment
         */
        Point point1 = new Point();
        
        /*
         * Point 1 of segment
         */
        Point point2 = new Point();
           
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
    }
    
    private ArrayList drawing = null;
    
    private int currentColor = 0;
    
    private float currentThickness = 0;

	public Drawing() {
		drawing = new ArrayList();
	}
	
	public void draw() {
		
	}

}
