package edu.ufl.brainless;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;

/** Heads-Up Display
 * Used to display in-game stick and button controls
*/
public class HUD {
	
	private static final String TAG = HUD.class.getSimpleName();
	
	private MotionEvent event;	// Contains input
	private GamePanel panel;	// Used to call getHeight, getWidth, etc.
	
	private Sprite stick;
	private Sprite stickBackground;

	// Stick locations
	private float tiltRadius;		// Range where player is tilting stick.
	private float moveRadius;		// Range where player is moving stick.
	private float stickAngle;		// Angle of stick input.

	public HUD() {
		stick = new Sprite(ResourceManager.getBitmap(R.drawable.stick_foreground), 45, 355, 0);
		stickBackground = new Sprite(ResourceManager.getBitmap(R.drawable.stick_background), 10, 320, 0);
		stick.setCenter(new Vector2(100,375));
		stickBackground.setCenter(new Vector2(100, 375));
		tiltRadius = stickBackground.rect.width/6;
		moveRadius = stickBackground.rect.width/2;
		Log.d(TAG, "Stick position: " + stick.position.toString());

		/*actionButton = new int[2];
		actionButton[0] = screenSize[0]/6;
		actionButton[1] = screenSize[1]*3/4;
		Log.d(TAG, "Button position: (" + actionButton[0] + "," + actionButton[1] + ").");
		abRadius = 50;*/
	}
	
	public void passEvent(MotionEvent event) {
		this.event = event;
	}

	// calls updateCheckPointer for each pointer in event
	public void update() {
		//Log.d(TAG, "Updating input attributes.");
		if(event != null) { if(event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
			Log.d(TAG, "Pointer count: " + event.getPointerCount());
			for(int c = 0; c < event.getPointerCount(); c++) { updateCheckPointer(c); }
		}}
		else
			resetHUD();
	}

	// updates input properties based on the pointer passed to it
	private void updateCheckPointer(int pointerIndex) {

		Vector2 eventPos = new Vector2(event.getX(pointerIndex), event.getY(pointerIndex));

		// If distance between event and stick is less than the radius of the stick range, input is stick input.
		if(Vector2.Distance(eventPos, stickBackground.getCenter()) <= moveRadius) {
			stick.setCenter(eventPos); 
			// Check to see if event is tilt or move.
			if(Vector2.Distance(eventPos, stickBackground.getCenter()) <= tiltRadius) { 
				// Not enough distance for movement
			}
			else { 
				// Adjust stick angle.
				Vector2 delta = new Vector2(eventPos.X - stickBackground.getCenter().X, eventPos.Y - stickBackground.getCenter().Y);	
				stickAngle = (float)(Math.atan2(delta.Y, delta.X) * 180 / Math.PI);
				if (stickAngle < 0)
					stickAngle += 360;
			}
		}
	}
	
	public void resetHUD() {
		stick.setCenter(stickBackground.getCenter());
	}
	
	public Vector2 getPlayerDirection() {
		if (stick.getCenter().X != stickBackground.getCenter().X || stick.getCenter().Y != stickBackground.getCenter().Y) {
			Vector2 direction = new Vector2(stick.getCenter().X - stickBackground.getCenter().X, stick.getCenter().Y - stickBackground.getCenter().Y);
			direction.Normalize();
			return direction;
		}
		else
			return new Vector2(0,0);
	}

	// test
	public void draw(Canvas canvas) {
		stickBackground.draw(canvas);
		stick.draw(canvas);
	}
}
