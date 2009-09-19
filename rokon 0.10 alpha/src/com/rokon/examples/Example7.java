/*
 * 
 * 	This example demonstrates modifiers, and how they can be used.
 * 	At this early stage, there are not many around. But it is quite easy
 * 	to build new modifiers. They are essentially routines which can be 
 * 	applied to many sprites at the same time without repeating your code.
 * 
 * 	For example, you could create a modifier which plays an animation, moves
 * 	a sprite, then destroys the sprite. This could be called from one line and
 * 	used over and over again.
 * 
 */
package com.rokon.examples;

import rokon.Rokon;
import rokon.Sprite;
import rokon.Texture;
import rokon.SpriteModifiers.Spin;
import android.app.Activity;
import android.os.Bundle;

public class Example7 extends Activity {

	Texture crabTexture;

	Rokon rokon;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rokon = Rokon.createEngine(this);
        rokon.setFullscreen();
        rokon.fixLandscape();
        rokon.init();

        crabTexture = rokon.createTextureFromResource(R.drawable.crab); 
    	rokon.prepareTextureAtlas();
    	
    	for(int i = 0; i < 5; i++) {
    		Sprite sprite = new Sprite((int)(Math.random() * 480), (int)(Math.random() * 320), 100, 100, crabTexture);
    		//	Each sprite is assigned the Spin modifier
    		//	This is a very basic modifier, which rotates the sprite
    		//	at a given frequency (1 = 1 full rotation per second)
    		//	It is important to remember when rotating objects like this
    		//	that you create time-based rotations rather than frame based,
    		//	this will keep your game looking constant even if frame rate
    		//	drops below the noticable limit. Modifiers can be removed by 
    		//	removeModifier(SpriteModifier). There is no limit to the number
    		//	of active  modifiers, and they can all be wiped by resetModifiers()
    		sprite.addModifier(new Spin((float)Math.random() * 2));    		
    		rokon.addSprite(sprite);
    	}

        Thread gameThread = new Thread(new GameThread());
        gameThread.start();
    }
    
    public class GameThread implements Runnable {
    	public void run() {
    		
    	}
    }
}