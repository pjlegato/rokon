/*
 * 
 * 	This example shows how to start up a blank OpenGL screen using Rokon.
 * 
 */

package com.rokon.examples;

import rokon.Rokon;
import android.app.Activity;
import android.os.Bundle;

public class Example1 extends Activity {

	Rokon rokon;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //	The first thing you need to do before anything, is create 
        //	the engine
        rokon = Rokon.createEngine(this);
        
        //	Removes the title bar
        rokon.setFullscreen();
        
        //	Fixes the orientation, if left to be set manually it can 
        //	cause FPS drops.
        rokon.fixPortrait();
        
        //	Creates the Renderer and SurfaceView,
        rokon.init();
        
        //	A separate game thread will keep graphics running smoothly  
        //	while you handle game logic
        Thread gameThread = new Thread(new GameThread());
        gameThread.start();
    }
    
    public class GameThread implements Runnable {
    	public void run() {
    		//	Usually a loop in here would handl your game logic, 
    		//	but since we are just filling a blank screen it is 
    		//	okay to leave it here.
    	}
    }
}