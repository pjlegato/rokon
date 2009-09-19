package com.rokon.examples;

import rokon.Emitter;
import rokon.Rokon;
import rokon.Texture;
import rokon.Backgrounds.ScrollingBackground;
import rokon.Emitters.BasicEmitter;
import rokon.Emitters.ExplosionEmitter;
import rokon.Handlers.InputHandler;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;

/**
 * @author Richard Taylor
 * Example10 shows how to use the BasicEmitter and ExplosionEmitter classes from the particle engine
 */
public class Example10 extends Activity {
	
	Rokon rokon;
	Texture texture;
	Texture crab;
	Emitter emitter;
	ScrollingBackground background;
	boolean done = false;
	long lastClick = 0;
	
	public void onCreate(Bundle b) {
		super.onCreate(b);
		rokon = Rokon.createEngine(this);
		rokon.setFullscreen();
		rokon.fixLandscape();
		rokon.init();

		crab = rokon.createTextureFromResource(R.drawable.crab);
		texture = rokon.createTextureFromResource(R.drawable.particle);
		rokon.prepareTextureAtlas();
		
		rokon.setInputHandler(new InputHandler() {
			public void onTouchEvent(MotionEvent event, boolean hotspot) {
				if(Rokon.getTime() - lastClick > 250) {
					rokon.getLayer(0).addEmitter(new ExplosionEmitter(event.getX(), event.getY(), texture, 50, -80, 80, -80, 80, 0, 0, 50, 100, 75, 140, 30, 50, 0.3f, 0.5f));;
					lastClick = Rokon.getTime();
				}
			}
		});
		BasicEmitter emitter = new BasicEmitter(200, 275, 20, texture, 0, -10, 10, -50, -125, 0, 0, 0, -50, 50, 120, 50, 120, 0.3f, 0.8f);
		rokon.getLayer(0).addEmitter(emitter);
		
		Thread gameThread = new Thread(new GameThread());
        gameThread.start();
    }
    
    public class GameThread implements Runnable {
    	public void run() {

    	}
    }
}
