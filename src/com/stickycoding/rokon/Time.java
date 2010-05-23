package com.stickycoding.rokon;

/**
 * Time.java
 * Keeps track of the game time, frames, loops etc.
 * 
 * @author Richard
 */
public class Time {
	
	protected static long ticks;
	protected static int ticksSinceLastFrame;
	protected static int ticksFraction;
	protected static long lastTicks;
	
	protected static void update() {
		lastTicks = ticks;
		ticks = System.currentTimeMillis();
		if(lastTicks == 0) {
			return;
		}
		ticksSinceLastFrame = FP.fromInt((int)(ticks - lastTicks));
		ticksFraction = FP.div(ticksSinceLastFrame, FP.ONE_THOUSAND);
	}
	
	public static long getTicks() {
		return ticks;
	}
	
	public static int getTicksSinceLastFrame() {
		return ticksSinceLastFrame;
	}
	
	public static int getTicksFraction() {
		return ticksFraction;
	}
	
	public static long getLastTicks() {
		return lastTicks;
	}

}
