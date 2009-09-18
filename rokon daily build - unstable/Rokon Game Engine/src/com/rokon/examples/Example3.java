/*
 * 
 * 	This example loads TTF text, handles screen touches and shows Hotspots
 * 
 */

package com.rokon.examples;

import rokon.Font;
import rokon.Hotspot;
import rokon.Rokon;
import rokon.Sprite;
import rokon.Text;
import rokon.Texture;
import rokon.Handlers.InputHandler;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;

public class Example3 extends Activity {

	int score = 0;
	
	Sprite crabSprite;
	Texture crabTexture;

	Rokon rokon;
	Hotspot hotspot;
	
	Font font;
	Text text;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rokon = Rokon.createEngine(this);
        rokon.setFullscreen();
        rokon.fixPortrait();
        rokon.init();
        
        //	TTF fonts can be loaded into the engine, they are drawn out onto a
        //	texture. For this reason, they must be brought in before prepareTextureAtlas
        font = rokon.createFont("fonts/amarurgt.ttf");
        
        // 	The Text class is optimized for short, dynamic text. Consider using
        //	premade textures for longer, fixed text. Future versions will handle both.
        //	The final parameter is scale, which is defined through text height
        text = new Text("0", font, 10, 10, 32);
        
        //	Text objects are handled much like sprites, held on layers.
        //	Note, a layer will draw its sprites before its text, therefore text 
        //	will appear ontop. Again, by default, text will be put onto layer 0
        rokon.addText(text);
        
        crabTexture = rokon.createTextureFromResource(R.drawable.crab); 
    	rokon.prepareTextureAtlas();
    	crabSprite = new Sprite(35, 115, 70, 70);
    	crabSprite.setTexture(crabTexture);
        rokon.addSprite(crabSprite);

        //	InputHandler handles all your screen touches
        rokon.setInputHandler(new myInputHandler());
        
        //	Hotspots are predefined areas that will trigger the onHotspotTouched
        //	method of your InputHandler. Hotspots can be defined by coordinates,
        //	or by a sprite's position. Note, if your sprite's position changes
        //	you will have to update the hotspot yourself.
        hotspot = new Hotspot(crabSprite);
        rokon.addHotspot(hotspot);
        
        Thread gameThread = new Thread(new GameThread());
        gameThread.start();
    }
    
    public class GameThread implements Runnable {
    	public void run() {
    		
    	}
    }
    
    public class myInputHandler extends InputHandler {
    	//	This is called every time there is a touch on screen, hotspot is true
    	//	if an onHotspotTouched method is also triggered.
    	public void onTouchEvent(MotionEvent event, boolean hotspot) {
    		
    	}
    	
    	//	This is called when a registered hotspot has been touched.
    	public void onHotspotTouched(Hotspot hotspot) {
    		//	If the sprite is touched, move it somewhere else randomly on screen.
    		crabSprite.setXY((int)(Math.random() * 300), (int)(Math.random() * 400));
    		
    		//	The hotspot has to be updated manually.
    		hotspot.update(crabSprite);

    		//	Text can be updated, here it will be updated with the last touch
    		text.setText(String.valueOf(score++));
    	}
    }
    
}