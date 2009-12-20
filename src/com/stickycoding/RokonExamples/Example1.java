package com.stickycoding.RokonExamples;

import com.stickycoding.Rokon.Debug;
import com.stickycoding.Rokon.RokonActivity;
import com.stickycoding.Rokon.Texture;
import com.stickycoding.Rokon.Backgrounds.FixedBackground;
import com.stickycoding.Rokon.Menu.Menu;
import com.stickycoding.Rokon.Menu.MenuObject;
import com.stickycoding.Rokon.Menu.Objects.MenuButton;
import com.stickycoding.Rokon.Menu.Objects.MenuImage;
import com.stickycoding.Rokon.Menu.Transitions.MenuFade;

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