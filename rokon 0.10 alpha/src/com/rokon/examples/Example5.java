/*
 * 
 * 	This example shows how tiled animations can be used in your game.
 *	They can be set to a predefined number of loops or contiuous.
 *	AnimationHandler allows you to manage an event fired when an animation ends.
 * 
 */
package com.rokon.examples;

import rokon.Hotspot;
import rokon.Rokon;
import rokon.Sprite;
import rokon.Texture;
import rokon.Handlers.AnimationHandler;
import rokon.Handlers.InputHandler;
import android.app.Activity;
import android.os.Bundle;

public class Example5 extends Activity {

	Sprite catSprite;
	Sprite explosionSprite;
	Sprite buttonSprite;

	Texture catTexture;
	Texture explosionTexture;
	Texture buttonTexture;

	Rokon rokon;
	Hotspot hotspot;
	
	long lastTrigger = 0;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rokon = Rokon.createEngine(this);
        rokon.setFullscreen();
        rokon.fixLandscape();
        rokon.init();

        catTexture = rokon.createTextureFromResource(R.drawable.cat);
        explosionTexture = rokon.createTextureFromResource(R.drawable.explosion); 
        buttonTexture = rokon.createTextureFromResource(R.drawable.button); 
    	rokon.prepareTextureAtlas();
    	
    	//	The cat and explosion textures are tiled, that is, they are one image
    	//	but inside that one image are several different states of that texture.
    	//	This is the most efficient way of handling animated sprites. You must
    	//	define either the number rows/columns or the size of each tile.
    	explosionTexture.setTileCount(5, 5);
    	catTexture.setTileCount(4, 2);
    	
    	buttonSprite = new Sprite(5, 5, 100, 50, buttonTexture);
    	catSprite = new Sprite(50, 50, 150, 150, catTexture);
    	explosionSprite = new Sprite(25, -50, 450, 450, explosionTexture);
        rokon.addSprite(buttonSprite);
        rokon.addSprite(catSprite);
        rokon.addSprite(explosionSprite, 1);
    	
    	//	For now, we will hide the explosion sprite so it will not be drawn
        //	However the sprite is still loaded into the layer
        explosionSprite.setVisible(false);
        
        //	AnimationHandler allows you to track the end of each animation cycle
        //	and the end of the animation. This may be useful in some games
        explosionSprite.setAnimationHandler(new myAnimationHandler());
        
        //	The cat sprite is set to an infinite loop of the texture tiles
        //	The tiles are counted as index, reading left to right top to bottom
        //	The lowest tile (top left) is 1. The cat animation has 6 frames
        //	The time (in ms) between each frame is defined as 250
        catSprite.animate(1, 6, 100);
        
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
    		//	When the button is pressed, trigger an explosion animation.
    		//	The animation is set for one loop, and will trigger the
    		//	events in your AnimationHandler as defined on creation. 
    		//	The animation is set for 1 loop. The final argument allows
    		//	you to define whether the sprite returns back to the start tile
    		//	when it is completed. In our case, this makes no difference.
    		//	lastTrigger is used so the explosion is not triggered more than
    		//	once per second.
    		long now = System.currentTimeMillis();
    		if(now - lastTrigger > 1000) {
        		explosionSprite.setVisible(true);
        		explosionSprite.animate(1, 25, 25, 1, false);
        		lastTrigger = now;
    		}
    	}
    }
    
    public class myAnimationHandler extends AnimationHandler {
    	public void finished() { 
    		//	When the animation is finished, it makes sense for us to
    		//	hide the sprite, as the animation tiles do not contain a
    		//	completely blank animation tile.
    		explosionSprite.setVisible(false);
    	}
    	
    	//	remainingLoops will be -1 if the animation is continuous
    	public void endOfLoop(int remainingLoops) { }
    }
    
}