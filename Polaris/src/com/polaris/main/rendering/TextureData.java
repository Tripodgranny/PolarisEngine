package com.polaris.main.rendering;

import java.nio.ByteBuffer;
/*
 * Decoded byte data of an image stored in a byte buffer
 */

public class TextureData {

	private int width;
	private int height;
	private ByteBuffer buffer;
	
	public TextureData(ByteBuffer buffer, int width, int height) {
		this.buffer = buffer;
		this.width = width;
		this.height = height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public ByteBuffer getBuffer() {
		return buffer;
	}
}
