package edu.ufl.brainless;

import android.graphics.Bitmap;

public class Actor extends Sprite {
	protected Vector2 direction;
	protected float speed;
	
	private static final String TAG = Actor.class.getSimpleName();

	public Actor(float x, float y, float angle) {
		super(x, y, angle);
		direction = new Vector2(0,0);
		speed = 0;
	}
	
	public Actor(Bitmap texture, float x, float y, float angle, Vector2 direction, float speed) {
		super(texture, x, y, angle);
		this.direction = direction;
		this.speed = speed;
	}
	
	public void update() {
		position.X += direction.X * speed;
		position.Y += direction.Y * speed;
	}
}
