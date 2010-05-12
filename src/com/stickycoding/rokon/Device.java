package com.stickycoding.rokon;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Device.java
 * Discovers and retrieves information about the devices hardware
 * @author Richard 
 */
public class Device {
	
	protected static DisplayMetrics displayMetrics;	
	protected static int widthPixels, heightPixels, halfWidthPixels, halfHeightPixels;
	
	/**
	 * Determines several characterstics of the physical device and stores for later usage
	 * This must be called before any other methods in this class are used
	 * 
	 * @param activity a currently active Activity
	 */
	public static void determine(Activity activity) {
		displayMetrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		widthPixels = FP.fromInt(displayMetrics.widthPixels);
		heightPixels = FP.fromInt(displayMetrics.heightPixels);
		halfWidthPixels = FP.div(widthPixels, FP.TWO);
		halfHeightPixels = FP.div(heightPixels, FP.TWO);
	}
	
	/**
	 * Returns the DisplayMetrics object associated with the current screen
	 * 
	 * @return NULL if determine() has not been called previously
	 */
	public static DisplayMetrics getDisplayMetrics() {
		if(displayMetrics == null)
			return null;
		return displayMetrics;
	}
	
	/**
	 * Returns the width of the screen, in pixels
	 * 
	 * @return a fixed point integer
	 */
	public static int getWidthPixels() {
		return widthPixels;
	}

	
	/**
	 * Returns the height of the screen, in pixels
	 * 
	 * @return a fixed point integer
	 */
	public static int getHeightPixels() {
		return heightPixels;
	}

	
	/**
	 * Returns half the width of the screen, in pixels
	 * 
	 * @return a fixed point integer
	 */
	public static int getHalfWidthPixels() {
		return halfWidthPixels;
	}

	
	/**
	 * Returns half the height of the screen, in pixels
	 * 
	 * @return a fixed point integer
	 */
	public static int getHalfHeightPixel() {
		return halfHeightPixels;
	}

}
