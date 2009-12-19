package com.stickycoding.Rokon.Menu;

import com.stickycoding.Rokon.Background;
import com.stickycoding.Rokon.Debug;
import com.stickycoding.Rokon.Hotspot;
import com.stickycoding.Rokon.Rokon;

public class Menu {
	
	public static final int MAX_OBJECTS = 99;
	
	private Rokon _rokon;
	private Background _background;
	private MenuObject[] _menuObjects = new MenuObject[MAX_OBJECTS];
	
	private MenuTransition _startTransition = null, _exitTransition = null;
	
	private boolean _closingMenu = false;
	private long _closingMenuTimeout;
	private Menu _closingMenuNext;
	
	public void onShow() { }
	public void onStartTransitionComplete() { }
	public void onMenuObjectTouchDown(MenuObject menuObject) { }
	public void onMenuObjectTouch(MenuObject menuObject) { }
	public void onMenuObjectTouchUp(MenuObject menuObject) { }
	public void onExitTransitionBegin() { }
	public void onExit() { }
	
	private int a;
	public void loop() {
		if(_closingMenu) {
			if(Rokon.time > _closingMenuTimeout) {
				if(_closingMenuNext == null)
					Rokon.getRokon().removeMenu();
				else
					Rokon.getRokon().showMenu(_closingMenuNext);
			}
			return;
		}
		for(a = 0; a < _menuObjects.length; a++)
			if(_menuObjects[a] != null)
				_menuObjects[a].loop();
		if(_startTransition != null)
			_startTransition.loop();
		if(_exitTransition != null)
			_exitTransition.loop();
	}
	
	public void exit() {
		if(_exitTransition != null)
			_exitTransition.begin(this);
	}
	
	public void end() {
		_rokon.clearScene();
	}
	
	public void show() {
		_rokon = Rokon.getRokon();
		_rokon.pause();
		_rokon.freeze();
		_rokon.clearScene();
		if(_background != null)
			_rokon.setBackground(_background);
		for(int i = 0; i < _menuObjects.length; i++)
			if(_menuObjects[i] != null)
				_menuObjects[i].addToScene(_rokon, this);
		if(_startTransition != null)
			_startTransition.begin(this);
		onShow();
		_rokon.unfreeze();
		_rokon.unpause();
	}
	
	public void setBackground(Background background) {
		_background = background;
	}
	
	public Background getBackground() {
		return _background;
	}
	
	public MenuTransition getStartTransition() {
		return _startTransition;
	}
	
	public MenuTransition getExitTransition() {
		return _exitTransition;
	}
	
	public void setStartTransition(MenuTransition menuTransition) {
		_startTransition = menuTransition;
	}
	
	public void setExitTransition(MenuTransition menuTransition) {
		_exitTransition = menuTransition;
	}
	
	public MenuObject getMenuObject(int index) {
		return _menuObjects[index];
	}
	
	public int getMenuObjectCount() {
		int j = 0;
		for(int i = 0; i < MAX_OBJECTS; i++)
			if(_menuObjects[i] != null)
				j++;
		return j;
	}
	
	private int _firstEmptyMenuObject() {
		for(int i = 0; i < MAX_OBJECTS; i++)
			if(_menuObjects[i] == null)
				return i;
		return -1;
	}
	
	public void addMenuObject(MenuObject menuObject) {
		if(getMenuObjectCount() < MAX_OBJECTS)
			_menuObjects[_firstEmptyMenuObject()] = menuObject;
		else {
			Debug.print("TOO MANY MENU OBJECTS");
		}
	}
	
	public void removeMenuObject(MenuObject menuObject) {
		for(int i = 0; i < _menuObjects.length; i++)
			if(_menuObjects[i] != null && _menuObjects[i].equals(menuObject)) {
				_menuObjects[i] = null;
			}
	}
	
	private int b;
	private boolean _hotspotActive = false;
	public void onHotspot(Hotspot hotspot) {
		if(_closingMenu)
			return;
		for(b = 0; b < MAX_OBJECTS; b++)
			if(_menuObjects[b] != null && _menuObjects[b].getHotspot() != null && _menuObjects[b].getHotspot().equals(hotspot)) {
				_menuObjects[b].touch(_hotspotActive);
				_hotspotActive = true;
			}
	}
	
	public void resetActiveHotspot() {
		_hotspotActive = false;
	}

	public void gotoMenu(Menu menu) {
		gotoMenu(menu, 0);
	}
	
	public void gotoMenu(Menu menu, int delay) {
		_closingMenuNext = menu;
		_closingMenu = true;
		_closingMenuTimeout = Rokon.time + delay;
	}
	
	public void closeMenu() {
		closeMenu(0);
	}
	
	public void closeMenu(int delay) {
		_closingMenuNext = null;
		_closingMenu = true;
		_closingMenuTimeout = Rokon.time + delay;
	}

}
