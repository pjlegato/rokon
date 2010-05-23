package com.stickycoding.rokon;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import android.os.Build;

/**
 * BufferObject.java
 * Some functions for Buffers
 * 
 * @author Richard 
 */

public class BufferObject {

	protected ByteBuffer byteBuffer;
	protected IntBuffer intBuffer;
	protected boolean useIntBuffer;
	
	public BufferObject() {
		if(Build.VERSION.SDK == "3")
			byteBuffer = ByteBuffer.allocate(8*4);
		else
			byteBuffer = ByteBuffer.allocateDirect(8*4);
		byteBuffer.order(ByteOrder.nativeOrder());		
	}
	
	public BufferObject(boolean useIntBuffer) {
		if(useIntBuffer) {
			useIntBuffer = true;

			byteBuffer = ByteBuffer.allocateDirect(8*4);
			byteBuffer.order(ByteOrder.nativeOrder());
			intBuffer = byteBuffer.asIntBuffer();
		} else {
			if(Build.VERSION.SDK == "3")
				byteBuffer = ByteBuffer.allocate(8*4);
			else
				byteBuffer = ByteBuffer.allocateDirect(8*4);
			byteBuffer.order(ByteOrder.nativeOrder());	
		}
	}
	
	public void update(int x, int y, int width, int height) {
		if(useIntBuffer) {
			intBuffer.position(0);
			intBuffer.put(x);
			intBuffer.put(y);
			intBuffer.put(x + width);
			intBuffer.put(y);
			intBuffer.put(x);
			intBuffer.put(y + height);
			intBuffer.put(x + width);
			intBuffer.put(y + height);
			intBuffer.position(0);
		} else {
			byteBuffer.position(0);
			byteBuffer.putInt(x);
			byteBuffer.putInt(y);
			byteBuffer.putInt(x + width);
			byteBuffer.putInt(y);
			byteBuffer.putInt(x);
			byteBuffer.putInt(y + height);
			byteBuffer.putInt(x + width);
			byteBuffer.putInt(y + height);
			byteBuffer.position(0);
		}
	}
	
	public IntBuffer getIntBuffer() {
		return intBuffer;
	}
	
	public ByteBuffer get() {
		return byteBuffer;
	}
	
}
