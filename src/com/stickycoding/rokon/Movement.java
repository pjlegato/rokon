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
	public static int getVelocity(int type, int distance, long startTime, long now, long endTime) {
		if(now > endTime || now < startTime) {
			return 0;
		}
		return getVelocity(type, distance, FP.fromInt((int)(endTime - startTime)), FP.div(FP.fromInt((int)(endTime - now)), FP.fromInt((int)(endTime - startTime))));
	}
	
	public static int getVelocity(int type, int distance, int time, int position) {
		switch(type) {
			case LINEAR:
				return getVelocityLinear(distance, time, position);
			}
		return 1;
	}
	
	public static int getVelocityLinear(int distance, int time, int position) {
		return FP.div(distance, time);
	}

}
