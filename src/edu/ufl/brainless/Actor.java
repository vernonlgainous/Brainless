package edu.ufl.brainless;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class Actor extends Sprite {
	protected Vector2 direction;
	protected float speed;
	public Vector2 cameraPosition;

	private static final String TAG = Actor.class.getSimpleName();

	public Actor(float x, float y, float angle) {
		super(x, y, angle);
		direction = new Vector2(0,0);
		cameraPosition = calculateCameraPosition(Camera.playerPosition);
		speed = 0;
	}

	public Actor(Bitmap texture, float x, float y, float angle, Vector2 direction, float speed) {
		super(texture, x, y, angle);
		this.direction = direction;
		this.speed = speed;
		cameraPosition = calculateCameraPosition(Camera.playerPosition);
	}
	
	public Vector2 calculateCameraPosition(Vector2 playerPosition) {
		Vector2 result = Vector2.Subtract(position, playerPosition);
		result = Vector2.Add(result, Camera.playerPosition);
		return result;
	}

	public void update() {
		position.X += direction.X * speed;
		position.Y += direction.Y * speed;
		this.rect.X=position.X;
		this.rect.Y=position.Y;
	}
	
	public void update(Vector2 playerPosition) {
		position.X += direction.X * speed;
		position.Y += direction.Y * speed;
		this.rect.X=position.X;
		this.rect.Y=position.Y;
		cameraPosition = calculateCameraPosition(playerPosition);
	}
	
	@Override
	public void draw(Canvas canvas) {
		Matrix matrix = new Matrix();		
		matrix.postRotate(angle,rect.width/2,rect.height/2);
		matrix.postTranslate(cameraPosition.X, cameraPosition.Y);
		canvas.drawBitmap(texture, matrix, null);
		//canvas.drawBitmap(texture, position.X, position.Y, null);
	}
}