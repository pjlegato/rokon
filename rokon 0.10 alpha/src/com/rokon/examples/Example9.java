/*
 * 
 * 	Example 9 demonstrates some hardware features that are not OpenGL.
 * 	In an attempt to simply the game code as much as possible, a very
 * 	easy to use Accelerometer class can be used. And the engine can vibrate
 * 	your device.
 * 
 */
package com.rokon.examples;

import rokon.Accelerometer;
import rokon.Hotspot;
import rokon.Rokon;
import rokon.RokonAudio;
import rokon.SoundFile;
import rokon.Handlers.AccelerometerHandler;
import rokon.Handlers.InputHandler;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;

public class Example9 extends Activity {

	RokonAudio audio;
	SoundFile explosion;
	
	long lastTrigger = 0;

	Rokon rokon;
	Hotspot hotspot;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rokon = Rokon.createEngine(this);
        rokon.setFullscreen();
        rokon.fixLandscape();
        rokon.init();
        
        //	The Accelerometer class is static, and provides an easy way
        //	to read accelerometer values and detect shakes.
        Accelerometer.startListening(new myAccelerometerHandler());
        
        rokon.setInputHandler(new InputHandler() {
        	public void onTouchEvent(MotionEvent event, boolean hotspot) {
        		//	For this demonstration, a tap on the screen will start
        		//	vibrating the phone for 1000ms
        		rokon.vibrate(1000);
        	}
        });
        
        audio = new RokonAudio();
        explosion = audio.createSoundFile("sounds/explode.mp3");
        
        Thread gameThread = new Thread(new GameThread());
        gameThread.start();
    }
    
    public class GameThread implements Runnable {
    	public void run() {
    		
    	}
    }
    
    public class myAccelerometerHandler extends AccelerometerHandler {

    	//	onChanged is triggered every time the accelerometer values change.
    	public void onChanged(float x, float y, float z) {
    		//	For the purposes of this demonstration, we will do nothing
    		//	with this. But the x y z values are the raw x y z values
    		//	as the accelerometer returns - acceleration in m/s.
    	}
    	
    	//	onShake is triggered when the intensity of a shake exceeds the
    	//	threshold value, defined by Accelerometer.setShakeThreshold
    	//	default is 3000
    	public void onShake(float intensity) {
    		long now = System.currentTimeMillis();
    		if(now - lastTrigger > 1000) {
    			explosion.play();
        		lastTrigger = now;
    		}
    	}
    }
    
}