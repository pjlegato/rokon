package com.stickycoding.RokonExamples;

import com.stickycoding.Rokon.RokonActivity;
import com.stickycoding.Rokon.Texture;
import com.stickycoding.Rokon.Backgrounds.FixedBackground;

/**
 * @author Richard
 * Loading a Texture and applying it to a FixedBackground
 */
public class Example2 extends RokonActivity {
	
	public Texture backgroundTexture;
	public FixedBackground background;
	
    public void onCreate() {
        createEngine(480, 320, true);
    }

	@Override
	public void onLoad() {
		backgroundTexture = rokon.createTexture("graphics/backgrounds/beach.png");
		background = new FixedBackground(backgroundTexture);
		rokon.prepareTextureAtlas(512);
	}

	@Override
	public void onLoadComplete() {
		rokon.setBackground(background);
	}

	@Override
	public void onGameLoop() {

	} 
}