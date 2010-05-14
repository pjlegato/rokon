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
	
	protected void update() {
		lastTicks = ticks;
		if(lastTicks == 0) {
			return;
		}
		ticks = System.currentTimeMillis();
		ticksSinceLastFrame = FP.fromInt((int)(ticks - lastTicks));
		ticksFraction = FP.div(ticksSinceLastFrame, FP.ONE_THOUSAND);
	}

}
