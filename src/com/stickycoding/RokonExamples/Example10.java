//	Vibration

package com.stickycoding.RokonExamples;

import com.stickycoding.Rokon.RokonActivity;

public class Example10 extends RokonActivity {
	
    public void onCreate() {
        createEngine(480, 320, true);
    }

	@Override
	public void onLoad() {
		
	}

	@Override
	public void onLoadComplete() {

	}

	@Override
	public void onGameLoop() {

	}
	
	@Override
	public void onTouch(int x, int y, boolean hotspot) {
		rokon.vibrate(50);
	}
	
}