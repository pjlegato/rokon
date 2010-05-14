package com.stickycoding.rokon;

/**
 * DynamicObject.java
 * A child of StaticObject, DynamicObject has functions for time dependent movement and rotation
 * @author Richard
 *
 */

public class DynamicObject extends StaticObject {
	
	public static final int X = 0, Y = 1;
	
	protected int accelerationX, accelerationY, speedX, speedY, terminalSpeedX, terminalSpeedY;
	protected boolean useTerminalSpeedX, useTerminalSpeedY;
	protected int acceleration, velocity, velocityXFactor, velocityYFactor, terminalVelocity;
	protected boolean useTerminalVelocity;
	protected int angularVelocity, angularAcceleration, terminalAngularVelocity;
	protected boolean useTerminalAngularVelocity;
	
	public DynamicObject(int x, int y, int width, int height, int rotation) {
		super(x, y, width, height, rotation);
	}
	
	public DynamicObject(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
	/**
	 * Stops all the dynamics for this object
	 */
	public void stop() {
		accelerationX = 0;
		accelerationY = 0;
		acceleration = 0;
		speedX = 0;
		speedY = 0;
		velocity = 0;
		velocityXFactor = 0;
		velocityYFactor = 0;
		terminalSpeedX = 0;
		terminalSpeedY = 0;
		terminalVelocity = 0;
		angularVelocity = 0;
		angularAcceleration = 0;
		terminalAngularVelocity = 0;
	}
	
	protected void onUpdate() {
		if(accelerationX != 0) {
			speedX += FP.mul(accelerationX, Time.ticksFraction);
			if(useTerminalSpeedX && ((accelerationX > 0 && speedX > terminalSpeedX) || (accelerationX < 0 && speedY < terminalSpeedX))) {
				accelerationX = 0;
				speedX = terminalSpeedX;
			}
		}
		if(accelerationY != 0) {
			speedY += FP.mul(accelerationY, Time.ticksFraction);
			if(useTerminalSpeedY && ((accelerationY > 0 && speedY > terminalSpeedY) || (accelerationY < 0 && speedY < terminalSpeedY))) {
				accelerationY = 0;
				speedY = terminalSpeedY;
			}
		}
		if(speedX != 0) {
			x += FP.mul(speedX, Time.ticksFraction);
		}
		if(speedY != 0) {
			y += FP.mul(speedY, Time.ticksFraction);
		}
		if(acceleration != 0) {
			velocity += FP.mul(acceleration, Time.ticksFraction);
			if(useTerminalVelocity && ((acceleration > 0 && velocity > terminalVelocity) || (acceleration < 0 && velocity < terminalVelocity))) {
				acceleration = 0;
				velocity = terminalVelocity;
			}
		}
		if(velocity != 0) {
			x += FP.mul(velocityXFactor, FP.mul(velocity, Time.ticksFraction));
			y += FP.mul(velocityYFactor, FP.mul(velocity, Time.ticksFraction));
		}
		if(angularAcceleration != 0) {
			angularVelocity += FP.mul(angularAcceleration, Time.ticksFraction);
			if(useTerminalAngularVelocity && ((angularAcceleration > 0 && angularVelocity > terminalAngularVelocity) || (angularAcceleration < 0 && angularVelocity < terminalAngularVelocity))) {
				angularAcceleration = 0;
				angularVelocity = terminalAngularVelocity;
			}
		}
	}

}
