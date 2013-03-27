package edu.ufl.brainless;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;

public class Sprite {
	
	private static final String TAG = Sprite.class.getSimpleName();
	
	protected Bitmap texture;
	public Rectangle rect;
	protected float angle;
	
	public Rectangle r;
	
	// Vector2 that corresponds with top-left point of Sprite
	public Vector2 position;
	
	public Sprite() {
		position = new Vector2(0,0);
		rect = new Rectangle(position.X, position.Y, texture.getWidth(), texture.getHeight());
		angle = 0;
	}
	
	public Sprite(float x, float y, float angle) {
		position = new Vector2(x, y);
		rect = new Rectangle(position.X, position.Y, 0, 0);
		this.angle = angle;
	}
	
	
	public Sprite(Bitmap texture, float x, float y, float angle) {
		this.texture = texture;
		position = new Vector2(x, y);
		rect = new Rectangle(position.X, position.Y, texture.getWidth(), texture.getHeight());
		this.angle = angle;
	}
	
	public void Load(Resources res) {
		texture = BitmapFactory.decodeResource(res, R.drawable.player);
		rect.width = texture.getWidth();
		rect.height = texture.getHeight();
	}
	
	public void LoadBitmap(Bitmap texture) {
		this.texture = texture;
		rect.width = texture.getWidth();
		rect.height = texture.getHeight();
	}
	
	public void update() {
		angle += 1f;
		if (angle > 360)
			angle = 0;
	}
	
	// moves position to be centered around parameter pos
	public void setCenter(Vector2 pos) {
		position.X = pos.X - rect.width/2;
		position.Y = pos.Y - rect.height/2;
	}
	
	// gets center position of sprite
	public Vector2 getCenter() {
		return new Vector2(position.X + rect.width/2, position.Y + rect.height/2);
	}
	
	public void draw(Canvas canvas) {
		Matrix matrix = new Matrix();		
		matrix.postRotate(angle,rect.width/2,rect.height/2);
		matrix.postTranslate(position.X, position.Y);
		canvas.drawBitmap(texture, matrix, null);
		//canvas.drawBitmap(texture, position.X, position.Y, null);
	}
}
