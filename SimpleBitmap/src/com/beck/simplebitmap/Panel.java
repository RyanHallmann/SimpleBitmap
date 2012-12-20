package com.beck.simplebitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;



public class Panel extends SurfaceView implements SurfaceHolder.Callback{
	//GLOBAL VARIABLES
	private CanvasThread canvasthread;
	
    //Variable used to hold our bitmap
	Bitmap bm;
    //Variable used to hold the current rotation value of the bitmap
    private int rotation = 0;
    //A Matrix is used to define size, position, and rotation 
    //of a graphic. We can set up a matrix and use it when drawing.
    private Matrix position; 
    //x and y hold the width and height of our canvas.
    int x;
    int y;
    //END GLOBAL VARIABLES
    
    
    public Panel(Context context, AttributeSet attrs) {
		super(context, attrs); 
		// TODO Auto-generated constructor stub
	   
		getHolder().addCallback(this);
	    canvasthread = new CanvasThread(getHolder(), this);
	    setFocusable(true);
	    //Here we load our Bitmap variable with the graphic ship.png in the drawable-hdpi folder.
	    bm = BitmapFactory.decodeResource(getResources(), R.drawable.ship);
	    //Here we initialize our position Matrix.
        position = new Matrix();
	 
	}

	 


	 public Panel(Context context) {
		   super(context);
		    getHolder().addCallback(this);
		  
		    setFocusable(true);

	    }


	@Override
	public void onDraw(Canvas canvas) {
       //x and y are **global** variables defined at the top of this class. We need to know how big our screen
	   // is. The center on a phone will be different than the center point on a
	   // tablet.
		
	
		x = canvas.getWidth();
        y = canvas.getHeight();			   
		
        update();	
        canvas.drawColor(Color.BLACK);
		
        //The Matrix position is updated within the update() method.
	    
	    canvas.drawBitmap(bm,position, null);
		
	   
	  
		
	}

	 public void update() {
	      
	       //Matrix m is a **local** variable. We will configure m and use it
		   // set the value of our global Matrix position.
		   Matrix m = new Matrix();
	       
		   //Current rotation value
		   m.postRotate(rotation, bm.getWidth()/2, bm.getHeight()/2);
	       
		   //This is where we define the center of the screen.
		   //x = canvas.getWidth();   --Look in onDraw
		   //y = canvas.getHeight();  --Look in on Draw
		   //Example: If the canvas width is 10, then x/2 would equal 5, --the horizontal center.
		   //We use positionx and positiony to draw the center of the bitmap to the center of the screen.
		   
		   int positionx = ((x/2) - bm.getWidth()/2);
	       int positiony = ((y/2) - bm.getHeight()/2);
	      
	       //WE UNCOMMENT THIS BLOCK IN LESSON 3
	       /*
	       float speedx= (float) Math.sin(rotation*(Math.PI/180)) * speed;
           float speedy = (float) Math.cos(rotation*(Math.PI/180)) * speed;
                   positionx -= speedx;
                   positiony += speedy;
	       */
	       m.postTranslate(positionx, positiony);

	        // This method sets the value of of our **global** variable (position) to the **local** variable (m)
	        position.set(m);

	        //update the rotation value. 
	        //We call update every frame refresh in onDraw.
	        rotation += 2;
	    }




	 
	 public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
		
	}
	
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		  canvasthread = new CanvasThread(getHolder(), this);
		canvasthread.setRunning(true);
	       
	    	canvasthread.start();
	    	
	    	
		
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	boolean retry = true;
		canvasthread.setRunning(false);
		while (retry) {
			try {
				canvasthread.join();
				retry = false;
			} catch (InterruptedException e) {
				// we will try it again and again...
			}
			
		}

	 





	 
	
	}


}   