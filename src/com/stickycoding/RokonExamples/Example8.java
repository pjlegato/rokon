package com.stickycoding.RokonExamples;

import com.stickycoding.Rokon.Font;
import com.stickycoding.Rokon.RokonActivity;
import com.stickycoding.Rokon.Text;
import com.stickycoding.Rokon.Texture;
import com.stickycoding.Rokon.Backgrounds.FixedBackground;

/**
 * @author Richard
 * Renders TTF fonts
 * (currently broken?)
 */
public class Example8 extends RokonActivity {
	
	public Texture backgroundTexture;
	public FixedBackground background;
	
	public Font font;
	
    public void onCreate() {
        createEngine(480, 320, true);
    }

	@Override
	public void onLoad() {
		backgroundTexture = rokon.createTexture("graphics/backgrounds/beach.png");
		font = rokon.createFont("fonts/256BYTES.TTF");
		rokon.prepareTextureAtlas(1024);
		background = new FixedBackground(backgroundTexture);
		
	}

	@Override
	public void onLoadComplete() {
		rokon.setBackground(background);
	}

	@Override
	public void onGameLoop() {

	}
	
	@Override
	public void onTouchUp(int x, int y, boolean hotspot) {
		Text text = new Text("" + (char)((int)(Math.random() * 26) + 65), font, x, y, (int)(Math.random() * 20) + 5);
		rokon.addText(text);
	}
}