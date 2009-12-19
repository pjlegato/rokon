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

public class Example1 extends RokonActivity {

	public Texture backgroundTexture;
	public FixedBackground background;
	public MyMenu myMenu;

	public Texture carTexture;
	public Texture car2Texture;
		
    public void onCreate() {
        //A default width and height are needed for the engine, this will be scaled on any screens with different sizes.
    	
    	createEngine("graphics/loading.png", 480, 320, true);
    }

	@Override
	public void onLoad() {
		backgroundTexture = rokon.createTexture("graphics/backgrounds/beach.png");
		carTexture = rokon.createTexture("graphics/sprites/car.png");
		car2Texture = rokon.createTexture("graphics/sprites/car2.png");
		rokon.prepareTextureAtlas();
		background = new FixedBackground(backgroundTexture);
		myMenu = new MyMenu();
	}

	@Override
	public void onLoadComplete() {
		rokon.showMenu(myMenu);
	}

	@Override
	public void onGameLoop() {

	} 
	
	public class MyMenu extends Menu {
		
		MenuImage image = new MenuImage(50, 50, car2Texture);
		
		public MyMenu() {
			setBackground(background);
			setStartTransition(new MenuFade(2000));
			addMenuObject(new MenuButton(1, 150, 100, carTexture, car2Texture));
			addMenuObject(image);
		}
		
		public void onMenuObjectTouchDown(MenuObject menuObject) { 
			Debug.print("TOUCH DOWN");
		}
		
		public void onMenuObjectTouchUp(MenuObject menuObject) {
			Debug.print("TOUCH UP");
			image.slideOutDown(500);
			closeMenu(5000);
		}
	}
}