/*
 * 
 * 	This example demonstrates the two types of audio libraries within Rokon.
 * 	RokonMusic utilizes MediaPlayer, and is optimized for longer, background 
 * 	music tracks with low priority. RokonAudio utilizes SoundPool, this is 
 * 	optimized for quick, short sound effects that are high priority. That is,
 * 	they need to be played as soon as they are called.
 * 
 * 	It is an improvement of Example5, and will sound an explosion when pressed
 * 	Background music will play from the beginning.
 * 
 */
package com.rokon.examples;

import rokon.AudioStream;
import rokon.Hotspot;
import rokon.Rokon;
import rokon.RokonAudio;
import rokon.RokonMusic;
import rokon.SoundFile;
import rokon.Sprite;
import rokon.Texture;
import rokon.Handlers.AnimationHandler;
import rokon.Handlers.InputHandler;
import android.app.Activity;
import android.os.Bundle;

public class Example6 extends Activity {
	
	RokonAudio audio;
	SoundFile explosionSound;

	Sprite catSprite;
	Sprite explosionSprite;
	Sprite buttonSprite;

	Texture catTexture;
	Texture explosionTexture;
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

        catTexture = rokon.createTextureFromResource(R.drawable.cat);
        explosionTexture = rokon.createTextureFromResource(R.drawable.explosion); 
        buttonTexture = rokon.createTextureFromResource(R.drawable.button); 
    	rokon.prepareTextureAtlas();
    	
    	explosionTexture.setTileCount(5, 5);
    	catTexture.setTileCount(4, 2);
    	
    	buttonSprite = new Sprite(5, 5, 100, 50, buttonTexture);
    	catSprite = new Sprite(50, 50, 150, 150, catTexture);
    	explosionSprite = new Sprite(25, -50, 450, 450, explosionTexture);
        rokon.addSprite(buttonSprite);
        rokon.addSprite(catSprite);
        rokon.addSprite(explosionSprite, 1);
    	
        explosionSprite.setVisible(false);
        explosionSprite.setAnimationHandler(new myAnimationHandler());
        catSprite.animate(1, 6, 100);
        
		rokon.setInputHandler(new myInputHandler());
        hotspot = new Hotspot(buttonSprite);
        rokon.addHotspot(hotspot);
        
        //	RokonAudio is optimized for sound effects, using SoundPool
        audio = new RokonAudio();
        
        //	Sound files are used to store effects, these should be loaded
        //	before the game begins.
        explosionSound = audio.createSoundFile("sounds/explode.mp3");

        //	RokonMusic utilizes the MediaPlayer library, and is optimized for
        //	low priority, long tracks. Only one track can be played at a time.
        //	It is a static library, and does not need to be initialised.
        //	Files are played from the assets folder. The second parameter
        //	defines whether the track will be repeated when its finished.
        //RokonMusic.play("sounds/iron-man.mp3", true);
        
        
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
        		explosionSprite.setVisible(true);
        		explosionSprite.animate(1, 25, 25, 1, false);
        		lastTrigger = now;
        		//	Our audio file can be played, there are various settings
        		//	which can be changed, such as volume and rate. But for now
        		//	it is fine to use maximum volume and a normal speed.
        		@SuppressWarnings("unused")
				AudioStream stream = explosionSound.play();
        		//	An AudioStream is returned, this can be used to stop, pause
        		//	or alter the sound as it actually plays. For us, here, it
        		//	is of no use.
        		
    		}
    	}
    }
    
    public class myAnimationHandler extends AnimationHandler {
    	public void finished() { 
    		explosionSprite.setVisible(false);
    	}
    }
    
}