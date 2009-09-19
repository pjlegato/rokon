/*
 * 
 * 	This example loads up a texture and a sprite.
 * 
 */

package com.rokon.examples;

import rokon.Rokon;
import rokon.Sprite;
import rokon.Texture;
import android.app.Activity;
import android.os.Bundle;

public class Example2 extends Activity {

	Sprite droidSprite;
	Texture droidTexture;
	
	Rokon rokon;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rokon = Rokon.createEngine(this);
        rokon.setFullscreen();
        rokon.fixPortrait();
        rokon.init();

        //	Textures need to be loaded before they can be assigned to sprites, 
        //	they can be loaded from resources or bitmaps (createTextureFromBitmap) 
        droidTexture = rokon.createTextureFromResource(R.drawable.droid);  
        
        //	After all textures are loaded, they are packed into an atlas texture
        //	An atlas texture contains all the textures you have loaded into it,
        //	and saves significant amounts of time when rendering your scene.
    	rokon.prepareTextureAtlas();
        
        //	A sprite is the main object you will need for games, it can be moved, 
        //	shaped, textured and more.
        droidSprite = new Sprite(35, 115, 250, 250);
        droidSprite.setTexture(droidTexture);
        
        //	A sprite will not show until it is added to the engine
        //	By default it will be added to layer 0. Lower layers are drawn
        //	before higher layers, and therefore are covered by them.
        rokon.addSprite(droidSprite); 
        
        Thread gameThread = new Thread(new GameThread());
        gameThread.start();
    }
    
    public class GameThread implements Runnable {
    	public void run() {

    	}
    }
    
}