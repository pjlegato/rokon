package com.stickycoding.RokonExamples;

import com.stickycoding.Rokon.RokonActivity;

/**
 * @author Richard
 * Simply loads the engine, and displays a red background
 */
public class Example1 extends RokonActivity {
		
    public void onCreate() {
        //A default width and height are needed for the engine, this will be scaled on any screens with different sizes.
    	createEngine("graphics/loading.png", 480, 320, true);
    	rokon.setBackgroundColor(1, 0, 0);
    }
}