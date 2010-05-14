package com.stickycoding.rokon.Handler;

import com.stickycoding.rokon.DynamicObject;

/**
 * MoveToHandler.java
 * An interface which handles events when a DynamicObject reaches its destination from a moveTo command
 *
 * @author Richard
 *
 */
public interface MoveToHandler {
	
	void onMoveComplete(DynamicObject dynamicObject);

}
