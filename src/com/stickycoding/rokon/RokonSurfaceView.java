package com.stickycoding.rokon;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

/**
 * RokonSurfaceView.java
 * The SurfaceView used for OpenGL rendering
 * @author Richard
 */
public class RokonSurfaceView extends GLSurfaceView {
        
	private RokonRenderer renderer;
	private RokonActivity rokonActivity;
	
	public RokonSurfaceView(RokonActivity rokonActivity) {
	    super(rokonActivity);
	    this.rokonActivity = rokonActivity;
	    renderer = new RokonRenderer(rokonActivity);
	    setRenderer(renderer);
	}

    public boolean onTouchEvent(final MotionEvent event) {

        try {
        	Thread.sleep(16);
        } catch (Exception e) { }
        return true;
    }

}
