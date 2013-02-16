package edu.ufl.brainless;

import android.graphics.Bitmap;

public class Sprite {
	
	private static final String TAG = Sprite.class.getSimpleName();
	
	protected Bitmap texture;
	
	// X and Y positions that correspond with top-left point of Sprite
	protected float x;
	protected float y;
	
	public Sprite(Bitmap texture, float x, float y) {
		this.texture = texture;
		this.x = x;
		this.y = y;
	}
	
	public void draw() {
		// Draw texture at position
	}
}
