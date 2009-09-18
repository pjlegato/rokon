/*
 * 
 * 	This example loads up three sprites, and when tapped, sets them all moving.
 * 	Examples of accelerating, terminal velocities and rotation.
 * 
 */
package com.rokon.examples;

import rokon.Hotspot;
import rokon.Rokon;
import rokon.Sprite;
import rokon.Texture;
import rokon.Handlers.DynamicsHandler;
import rokon.Handlers.InputHandler;
import rokon.SpriteModifiers.Spin;
import android.app.Activity;
import android.os.Bundle;

public class Example4 extends Activity {
	
	DynamicsHandler crabSpriteHandler;

	Sprite crabSprite1;
	Sprite crabSprite2;
	Sprite crabSprite3;
	Texture crabTexture;
	
	Sprite buttonSprite;
	Texture buttonTexture;

	Rokon rokon;
	Hotspot hotspot;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rokon = Rokon.createEngine(this);
        rokon.setFullscreen();
        rokon.fixLandscape();
        rokon.init();

        crabTexture = rokon.createTextureFromResource(R.drawable.crab); 
        buttonTexture = rokon.createTextureFromResource(R.drawable.button); 
    	rokon.prepareTextureAtlas();
    	
    	buttonSprite = new Sprite(5, 5, 100, 50, buttonTexture);
        crabSprite1 = new Sprite(10, 50, 80, 80, crabTexture);
        crabSprite2 = new Sprite(10, 150, 80, 80, crabTexture);
        crabSprite3 = new Sprite(10, 250, 80, 80, crabTexture);
        
        rokon.addSprite(buttonSprite);
        rokon.addSprite(crabSprite1);
        rokon.addSprite(crabSprite2);
        rokon.addSprite(crabSprite3);
		
		//	DynamicsHandler allows you to trigger events when terminal velocities
		//	are reached. This would be useful in certain types of games. In this
		//	example nothing will happen, but we will show you how to use it.
		//	This can be cancelled with resetDynamicsHandler()
		crabSprite2.setDynamicsHandler(new myDynamicsHandler());

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
    		//	It would be sensible to put something in here which prevents
    		//	your events being triggered several times for one tap, as a touch
    		//	is called every frame for the length of the touch. There is
    		//	no real need for this example, however.
    		crabSprite1.setXY(10, 50);
    		crabSprite2.setXY(10, 150);
    		crabSprite3.setXY(10, 250);

    		
    		//	Rokon has some built in dynamics, which can handle acceleration
    		//	and terminal velocities. First, they must be reset.
    		crabSprite1.resetDynamics();
    		crabSprite2.resetDynamics();
    		crabSprite3.resetDynamics();
    		
    		//	Crab 1 will accelerate indefinately
    		//	Crab 2 will accelerate to a predefined terminal velocity
    		//	Crab 3 will accelerate instantly to a velocity.
    		crabSprite1.accelerate(25, 0);
    		crabSprite2.accelerate(50, 0, 100, 0);
    		crabSprite3.setVelocity(50, 0);
    	
    		//	Sprite modifiers will be introduced more later on, but basically
    		//	they are a way of handling visual or physical changes to a sprite
    		//	that can be applied over a number of sprites without clogging up
    		//	your game logic.
    		//	Crab 3 will rotate at a frequency of 1 cycle per second
    		crabSprite3.resetModifiers();
    		crabSprite3.setRotation(0);
    		crabSprite3.addModifier(new Spin(1));
    	}
    }
    
    public class myDynamicsHandler extends DynamicsHandler {
    	public void reachedTerminalVelocityX() {
    		
    	}
    	
    	public void reachedTerminalVelocityY() {
    		
    	}
    }
    
}