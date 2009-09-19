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
import rokon.SpriteModifiers.Blink;
import rokon.SpriteModifiers.Colorize;
import rokon.SpriteModifiers.Fade;
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
    	rokon.setBackgroundColor(0.3f, 0.3f, 0.7f);
        rokon.init();

        crabTexture = rokon.createTextureFromResource(R.drawable.crab); 
    	rokon.prepareTextureAtlas();

    	Sprite sprite = new Sprite(0, 0, 100, 100, crabTexture);
    	sprite.addModifier(new Blink(250));
    	rokon.addSprite(sprite);

    	sprite = new Sprite(100, 0, 100, 100, crabTexture);
    	sprite.addModifier(new Colorize(1));
    	rokon.addSprite(sprite);

    	sprite = new Sprite(200, 0, 100, 100, crabTexture);
    	sprite.addModifier(new Fade(1000, -1));
    	rokon.addSprite(sprite);

    	sprite = new Sprite(300, 0, 100, 100, crabTexture);
    	sprite.addModifier(new Spin(1));
    	rokon.addSprite(sprite);
    	
    	sprite = new Sprite(200, 100, 200, 200, crabTexture);
    	sprite.addModifier(new Spin(0.2f));
    	sprite.addModifier(new Fade(1000, -1));
    	sprite.addModifier(new Colorize(1));
    	rokon.addSprite(sprite);

        Thread gameThread = new Thread(new GameThread());
        gameThread.start();
    }
    
    public class GameThread implements Runnable {
    	public void run() {
    		
    	}
    }
}