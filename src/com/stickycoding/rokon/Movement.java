package com.stickycoding.rokon;

/**
 * Movement.java
 * Defines several different types of movement, which can be applied to varying situations
 * 
 * @author Richard
 */
public class Movement {
	
	public static final int LINEAR = 0;
	
	/**
	 * @param type method used for movement
	 * @param distance length to be travelled
	 * @param startTime ticks at the start of movement
	 * @param now ticks right now
	 * @param endTime ticks at the end
	 * @return the velocity, as a factor of maximum
	 */
	public static float getVelocity(int type, float distance, long startTime, long now, long endTime) {
		if(now > endTime || now < startTime) {
			return 0;
		}
		return getVelocity(type, distance, (int)(endTime - startTime), (int)(endTime - now) / (int)(endTime - startTime));
	}
	
	public static float getVelocity(int type, float distance, int time, int position) {
		switch(type) {
			case LINEAR:
				return getVelocityLinear(distance, time, position);
			}
		return 1;
	}
	
	public static float getVelocityLinear(float distance, int time, float position) {
		return distance / time;
	}

}
