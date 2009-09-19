package com.rokon.examples;

import rokon.Emitter;
import rokon.Rokon;
import rokon.Sprite;
import rokon.Texture;
import rokon.Transition;
import rokon.Backgrounds.ScrollingBackground;
import rokon.Handlers.InputHandler;
import rokon.SpriteModifiers.Spin;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;

public class Example99 extends Activity {
	
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
		
		rokon.setBackgroundColor(0.5f, 0.5f, 0.5f);

		crab = rokon.createTextureFromResource(R.drawable.crab);
		texture = rokon.createTextureFromResource(R.drawable.particle);
		rokon.prepareTextureAtlas();
		
		rokon.setInputHandler(new InputHandler() {
			public void onTouchEvent(MotionEvent event, boolean hotspot) {
				if(Rokon.getTime() - lastClick > 1000) {
					rokon.setTransition(new Transition(5000));
				}
			}
		});
		Sprite sprite = new Sprite(100, 100, 150, 150, crab);
		sprite.addModifier(new Spin(0.5f));
		rokon.addSprite(sprite);
		
		Thread gameThread = new Thread(new GameThread());
        gameThread.start();
    }
    
    public class GameThread implements Runnable {
    	public void run() {

    	}
    }
}
