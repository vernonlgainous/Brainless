package edu.ufl.brainless;

import android.graphics.Bitmap;

public class Bullet extends Actor {
	
	private static final String TAG = Bullet.class.getSimpleName();

	public Bullet(Bitmap texture, float x, float y, float angle, Vector2 direction, float speed) {
		super(texture, x, y, angle, direction, speed);
		// TODO Auto-generated constructor stub
	}

	public void update() {
		Vector2.Add(position, Vector2.Multiply(direction, speed));
	}
}
