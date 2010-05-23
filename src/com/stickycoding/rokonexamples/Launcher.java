package com.stickycoding.rokonexamples;

import com.stickycoding.rokon.Debug;
import com.stickycoding.rokon.DrawPriority;
import com.stickycoding.rokon.RokonActivity;
import com.stickycoding.rokon.Scene;
import com.stickycoding.rokon.Sprite;
import com.stickycoding.rokon.Texture;

public class Launcher extends RokonActivity {
	
	Sprite sprite;

	public void onCreate() {
		forceFullscreen();
		forcePortrait();
		setGameSize(480, 800);
		setDrawPriority(DrawPriority.PRIORITY_NORMAL);
		setGraphicsPath("textures/");
		createEngine();
	}
	
	public void onLoadComplete() {
		Debug.print("Loading is complete");
		Scene scene = new Scene(1, 128);

		Texture texture = new Texture("face.png");
		Texture sad = new Texture("sad.png");
		
		for(float i = 1; i < 32; i++) {
			sprite = new Sprite(50 + (i * 4), 50 + (i * 15), 100, 100);
			sprite.setSize(100 - (20 - i), 100 - (20 - i));
			sprite.setAngularAcceleration(i);
			sprite.setAlpha(3f / (2f*i));
			if(i % 2 == 0)
				sprite.setTexture(texture);
			else
				sprite.setTexture(sad);
			scene.add(sprite);
		}
		
		scene.useTexture(texture);
		scene.useTexture(sad);
		
		setScene(scene);
		
	}
	
}