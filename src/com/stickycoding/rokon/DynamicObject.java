package com.stickycoding.rokon;

import com.stickycoding.rokon.Handler.TerminalAngularVelocityHandler;
import com.stickycoding.rokon.Handler.TerminalSpeedHandler;
import com.stickycoding.rokon.Handler.TerminalVelocityHandler;

/**
 * DynamicObject.java
 * A child of StaticObject, DynamicObject has functions for time dependent movement and rotation
 * 
 * There are two types of translational movement, one based on X and Y, and one based on a magnitude and angle
 * The two methods can be used together, though it is recommended to avoid this - it may be easy to get confused
 * 
 * @author Richard
 */

public class DynamicObject extends StaticObject {
	
	protected TerminalAngularVelocityHandler terminalAngularVelocityHandler;
	protected TerminalSpeedHandler terminalSpeedHandler;
	protected TerminalVelocityHandler terminalVelocityHandler;
	
	protected int accelerationX, accelerationY, speedX, speedY, terminalSpeedX, terminalSpeedY;
	protected boolean useTerminalSpeedX, useTerminalSpeedY;
	protected int acceleration, velocity, velocityAngle, velocityXFactor, velocityYFactor, terminalVelocity;
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
	 * Called when the DynamicObject is added to a Layer
	 */
	protected void onAdd() {
		
	}
	
	/**
	 * Called when the DynamicObject is removed from a Layer
	 */
	protected void onRemove() {
		terminalAngularVelocityHandler = null;
		terminalSpeedHandler = null;
		terminalVelocityHandler = null;
	}

	
	/**
	 * Sets the TerminalAngularVelocityHandler for this DynamicObject
	 * 
	 * @param terminalAngularVelocityHandler a valid class interfacing TerminalAngularVelocityHandler
	 */
	public void setTerminalAngularVelocityHandler(TerminalAngularVelocityHandler terminalAngularVelocityHandler) {
		this.terminalAngularVelocityHandler = terminalAngularVelocityHandler;
	}
	
	/**
	 * Sets the TerminalSpeedHandler for this DynamicObject
	 * 
	 * @param terminalSpeedHandler a valid class interfacing TerminalSpeedHandler
	 */
	public void setTerminalSpeedHandler(TerminalSpeedHandler terminalSpeedHandler) {
		this.terminalSpeedHandler = terminalSpeedHandler;
	}
	
	/**
	 * Sets the TerminalVelocityHandler for this DynamicObject
	 * 
	 * @param terminalVelocityHandler a valid class interfacing TerminalSpeedHandler
	 */
	public void setTerminalVelocityHandler(TerminalVelocityHandler terminalVelocityHandler) {
		this.terminalVelocityHandler = terminalVelocityHandler;
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
		velocityAngle = 0;
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
				if(terminalSpeedHandler != null) {
					terminalSpeedHandler.onTerminalSpeed(this, TerminalSpeedHandler.X);
				}
			}
		}
		if(accelerationY != 0) {
			speedY += FP.mul(accelerationY, Time.ticksFraction);
			if(useTerminalSpeedY && ((accelerationY > 0 && speedY > terminalSpeedY) || (accelerationY < 0 && speedY < terminalSpeedY))) {
				accelerationY = 0;
				speedY = terminalSpeedY;
				if(terminalSpeedHandler != null) {
					terminalSpeedHandler.onTerminalSpeed(this, TerminalSpeedHandler.Y);
				}
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
				if(terminalVelocityHandler != null) {
					terminalVelocityHandler.onTerminalVelocity(this);
				}
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
				if(terminalAngularVelocityHandler != null) {
					terminalAngularVelocityHandler.onTerminalAngularVelocity(this);
				}
			}
		}
	}
	
	/**
	 * Sets speed of the DynamicObject in the X direction
	 * 
	 * @param x positive or negative fixed point integer
	 */
	public void setSpeedX(int x) {
		speedX = x;
	}
	
	/**
	 * Sets speed of the DynamicObject in the Y direction
	 * 
	 * @param y positive or negative fixed point integer
	 */
	public void setSpeedY(int y) {
		speedY = y;
	}
	
	/**
	 * Sets the speed of the DynamicObject on both X and Y axis
	 * 
	 * @param x positive or negative fixed point integer
	 * @param y positive or negative fixed point integer
	 */
	public void setSpeed(int x, int y) {
		speedX = x;
		speedY = y;
	}
	
	/**
	 * Sets the velocity of the DynamicObject
	 * This is along the velocityAngle, and will be north if previously unset
	 * 
	 * @param velocity positive or negative fixed point integer
	 */
	public void setVelocity(int velocity) {
		this.velocity = velocity;
	}
	
	/**
	 * Sets the velocity of the DynamicObject
	 * 
	 * @param velocity positive or negative fixed point integer
	 * @param angle relative to north, in radians
	 */
	public void setVelocity(int velocity, int angle) {
		this.velocity = velocity;
		this.velocityAngle = angle;
		this.velocityXFactor = FP.sin(angle);
		this.velocityYFactor = FP.cos(angle);
	}
	
	/**
	 * Accelerates along the X direction
	 * 
	 * @param accelerationX positive or negative fixed point integer
	 */
	public void accelerateX(int accelerationX) {
		this.accelerationX = accelerationX;
	}
	
	public void accelerateX(int accelerationX, int terminalSpeedX) {
		this.accelerationX = accelerationX;
		this.terminalSpeedX = terminalSpeedX;
		useTerminalSpeedX = true;
	}
	
	/**
	 * Accelerates along the Y direction
	 * 
	 * @param accelerationY positive or negative fixed point integer
	 */
	public void accelerateY(int accelerationY) {
		this.accelerationY = accelerationY;
	}
	
	/**
	 * Accelerates along the Y direction to a maximum speed
	 *
	 * @param accelerationY positive or negative fixed point integer
	 * @param terminalSpeedY the maximum speed to achieve in Y direction
	 */
	public void accelerateY(int accelerationY, int terminalSpeedY) {
		this.accelerationY = accelerationY;
		this.terminalSpeedY = terminalSpeedY;
		useTerminalSpeedY = true;
	}
	
	/**
	 * Accelerates along a given angle
	 * 
	 * @param acceleration magnitude of acceleration
	 * @param angle relative to north, in radians
	 */
	public void accelerate(int acceleration, int angle) {
		this.acceleration = acceleration;
		this.velocityAngle = angle;
		this.velocityXFactor = FP.sin(angle);
		this.velocityYFactor = FP.cos(angle);
	}
	
	/**
	 * Accelerates along a given angle to a terminal velocity
	 * 
	 * @param acceleration magnitude of acceleration
	 * @param angle relative to north, in radians
	 * @param terminalVelocity maximum velocity to reach
	 */
	public void accelerate(int acceleration, int angle, int terminalVelocity) {
		accelerate(acceleration, angle);
		this.terminalVelocity = terminalVelocity;
		useTerminalVelocity = true;
	}
	
	/**
	 * Removes the limit on speed in the X direction
	 */
	public void stopUsingTerminalSpeedX() {
		useTerminalSpeedX = false;
	}
	
	/**
	 * Removes the limit on speed in the Y direction
	 */
	public void stopUsingTerminalSpeedY() {
		useTerminalSpeedY = false;
	}
	
	/**
	 * Removes the limit on speed in both X and Y directions
	 */
	public void stopUsingTerminalSpeed() {
		useTerminalSpeedX = false;
		useTerminalSpeedY = false;
	}
	
	/**
	 * @return TRUE if the DynamicObject is limited to a given speed in the X direction
	 */
	public boolean isUsingTerminalSpeedX() {
		return useTerminalSpeedX;
	}
	
	/**
	 * @return TRUE if the DynamicObject is limited to a given speed in the Y direction
	 */
	public boolean isUsingTerminalSpeedY() {
		return useTerminalSpeedY;
	}
	
	/**
	 * Removes the limit on velocity
	 */
	public void stopUsingTerminalVelocity() {
		useTerminalVelocity = false;
	}
	
	/**
	 * @return TRUE if the DynamicOject is limited to a given terminal velocity
	 */
	public boolean isUsingTerminalVelocity() {
		return useTerminalVelocity;
	}
	
	/**
	 * Removes the limit on angular velocity
	 */
	public void stopUsingTerminalAngularVelocity() {
		useTerminalAngularVelocity = false;
	}
	
	/**
	 * @return TRUE if the DynamicObject is limited to a given terminal angular velocity
	 */
	public boolean isUsingTerminalAngularVelocity() {
		return useTerminalAngularVelocity;
	}

	public int getAccelerationX() {
		return accelerationX;
	}
	
	public int getAccelerationY() {
		return accelerationY;
	}

	public int getAcceleration() {
		return acceleration;
	}
	
	public int getVelocityAngle() {
		return velocityAngle;
	}
	
	public int getVelocity() {
		return velocity;
	}
	
	public int getSpeedX() {
		return speedX;
	}
	
	public int getSpeedY() {
		return speedY;
	}
	
	public int getTerminalSpeedX() {
		return terminalSpeedX;
	}
	
	public int getTerminalSpeedY() {
		return terminalSpeedY;
	}
	
	public int getTerminalVelocity() {
		return terminalVelocity;
	}
	
	public int getAngularVelocity() {
		return terminalAngularVelocity;
	}
	
	public void setTerminalSpeedX(int terminalSpeedX) {
		this.terminalSpeedX = terminalSpeedX;
		useTerminalSpeedX =true;
	}
	
	public void setTerminalSpeedY(int terminalSpeedY) {
		this.terminalSpeedY = terminalSpeedY;
		useTerminalSpeedY = true;
	}
	
	public void setTerminalSpeed(int terminalSpeedX, int terminalSpeedY) {
		this.terminalSpeedX = terminalSpeedX;
		this.terminalSpeedY = terminalSpeedY;
		useTerminalSpeedX = true;
		useTerminalSpeedY = true;
	}
	
	public void setTerminalVelocity(int terminalVelocity) {
		this.terminalVelocity = terminalVelocity;
		useTerminalVelocity = true;
	}
	
	public void setTerminalAngularVelocity(int terminalAngularVelocity) {
		this.terminalAngularVelocity = terminalAngularVelocity;
		useTerminalAngularVelocity = true;
	}
	
	/**
	 * Sets the angular acceleration
	 * 
	 * @param acceleration fixed point integer, in radians 
	 */
	public void setAngularAcceleration(int acceleration) {
		this.angularAcceleration = acceleration;
	}

}
