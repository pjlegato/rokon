/*
 * 
 * 	Example8 shows how to use the in-built collision detection algorithms.
 * 	For now, they are very basic, and only work as rectangles. I am looking
 * 	at implementing more a accurate (polygon based) approach. 
 * 
 */
package com.rokon.examples;

import rokon.Hotspot;
import rokon.Rokon;
import rokon.Sprite;
import rokon.Texture;
import rokon.Handlers.CollisionHandler;
import rokon.Handlers.InputHandler;
import android.app.Activity;
import android.os.Bundle;

public class Example8 extends Activity {

	Sprite arrowSprite;
	Sprite stopSprite;
	Sprite buttonSprite;

	Texture arrowTexture;
	Texture stopTexture;
	Texture buttonTexture;
	
	long lastTrigger = 0;

	Rokon rokon;
	Hotspot hotspot;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rokon = Rokon.createEngine(this);
        rokon.setFullscreen();
        rokon.fixLandscape();
        rokon.init();

        arrowTexture = rokon.createTextureFromResource(R.drawable.arrow);
        stopTexture = rokon.createTextureFromResource(R.drawable.stop); 
        buttonTexture = rokon.createTextureFromResource(R.drawable.button); 
    	rokon.prepareTextureAtlas();
    	
    	buttonSprite = new Sprite(5, 5, 100, 50, buttonTexture);
    	arrowSprite = new Sprite(5, 100, 150, 150, arrowTexture);
    	stopSprite = new Sprite(300, 50, 150, 150, stopTexture);
        rokon.addSprite(buttonSprite);
        rokon.addSprite(arrowSprite);
        rokon.addSprite(stopSprite);
        
        //	CollisionHandler provides you with the ability to run an event
        //	when a sprite collides with another sprite. A single CollisionHandler
        //	can be assigned to multiple Sprite's
        myCollisionHandler myCol = new myCollisionHandler();
        arrowSprite.setCollisionHandler(myCol);
        
		rokon.setInputHandler(new myInputHandler());
        hotspot = new Hotspot(buttonSprite);
        rokon.addHotspot(hotspot);
        
        Thread gameThread = new Thread(new GameThread());
        gameThread.start();
    }
    
    public class GameThread implements Runnable {
    	public void run() {
    		
    	}
    }
    
    public class myInputHandler extends InputHandler {
    	
    	public void onHotspotTouched(Hotspot hotspot) {
    		long now = System.currentTimeMillis();
    		if(now - lastTrigger > 1000) {
    			arrowSprite.resetDynamics();
    			arrowSprite.setXY(5, 100);
    			arrowSprite.setVelocity(100, 0);
    			stopSprite.setVisible(true);
        		lastTrigger = now;
        		
    	        //	Sprite must be assigned with any sprites that they are to 
    	        //	register collisions with by using addCollisionSprite(Sprite)
    			arrowSprite.addCollisionSprite(stopSprite);
    		}
    	}
    }
    
    public class myCollisionHandler extends CollisionHandler {
    	//	This method is triggered when a sprite collides with another sprite.
    	public void collision(Sprite source, Sprite target) { 
    		target.setVisible(false);
    		source.resetDynamics();
    		source.removeCollisionSprite(target);
    	}
    }
    
}