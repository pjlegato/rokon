package com.stickycoding.rokonexamples;

import com.stickycoding.rokon.Debug;
import com.stickycoding.rokon.DrawPriority;
import com.stickycoding.rokon.FP;
import com.stickycoding.rokon.RokonActivity;
import com.stickycoding.rokon.Scene;
import com.stickycoding.rokon.Sprite;

public class Launcher extends RokonActivity {
	
	Sprite sprite;

	public void onCreate() {
		forceFullscreen();
		forcePortrait();
		setGameSize(FP.fromInt(480), FP.fromInt(800));
		setDrawPriority(DrawPriority.PRIORITY_NORMAL);
		createEngine();
	}
	
	public void onLoadComplete() {
		Debug.print("Loading is complete");
		Scene scene = new Scene();
		
		for(int i = 1; i < 20; i++) {
			sprite = new Sprite(FP.fromInt(100 + (i * 5)), FP.fromInt(100 + (i * 5)), FP.fromInt(100), FP.fromInt(100));
			sprite.setAngularAcceleration(FP.mul(FP.PI_OVER_2, FP.fromInt(i)));
			sprite.setAlpha(FP.div(FP.ONE, i));
			scene.add(sprite);
		}
		
		setScene(scene);
		
	}
	
}