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
	private Sprite button;
	private Sprite reloadButton;
	protected Sprite healthBar;

	private float stickCenterX = 100;
	private float stickCenterY = 375;
	private float buttonCenterX = 700;
	private float buttonCenterY = 375;

	// Stick locations
	private float tiltRadius;		// Range where player is tilting stick.
	private float moveRadius;		// Range where player is moving stick.
	private Vector2 stickAngle = new Vector2(0,0);		// Angle of stick input.

	private float buttonRadius;
	private float reloadButtonRadius;

	private int stickPointerId = -1;
	private boolean[] buttonPointers = new boolean[10];
	private boolean[] reloadButtonPointers = new boolean[10];
	public static final int MOVE = 2;
	public static final int TILT = 1;
	public static final int NEUTRAL = 0;
	
	public HUD() {
		stick = new Sprite(ResourceManager.getBitmap(R.drawable.stick_foreground), 45, 355, 0);
		stickBackground = new Sprite(ResourceManager.getBitmap(R.drawable.stick_background), 10, 320, 0);
		stick.setCenter(new Vector2(100,375));
		stickBackground.setCenter(new Vector2(100, 375));
		tiltRadius = stickBackground.rect.width/6;
		moveRadius = stickBackground.rect.width/2;
		//Log.d(TAG, "Stick position: " + stick.position.toString());

		healthBar = new Sprite(ResourceManager.getBitmap(R.drawable.health_bar), 585, 15,0);

		button = new Sprite(ResourceManager.getBitmap(R.drawable.shoot_button), 10, 320, 0);
		button.setCenter(new Vector2(buttonCenterX, buttonCenterY));
		reloadButton = new Sprite(ResourceManager.getBitmap(R.drawable.reload_button_t), 10, 320, 0);
		reloadButton.setCenter(new Vector2(buttonCenterX - 300, buttonCenterY));
		buttonRadius = button.rect.width;
		reloadButtonRadius = reloadButton.rect.width;
	}

	public void passEvent(MotionEvent event) {
		this.event = event;
		int action = event.getAction() & MotionEvent.ACTION_MASK;
		int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
		int pointerId = event.getPointerId(pointerIndex);

		try {
			switch (action) {
				case MotionEvent.ACTION_DOWN:
				case MotionEvent.ACTION_POINTER_DOWN:
					isPointerStickInput(event, pointerId);
					buttonPointers[pointerId] = isPointerButtonInput(event, pointerId);
					reloadButtonPointers[pointerId] = isReloadPointerButtonInput(event,pointerId);
					break;
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_POINTER_UP:
					if(stickPointerId == pointerId){
						stickPointerId = -1;
						resetHUD();
					}
					buttonPointers[pointerId] = false;
					reloadButtonPointers[pointerId] = false;
					break;
				case MotionEvent.ACTION_CANCEL:
					buttonPointers[pointerId] = false;
					reloadButtonPointers[pointerId] = false;
					stickPointerId = -1;
					break;
				case MotionEvent.ACTION_MOVE:
					isPointerStickInput(event, pointerIndex);
					buttonPointers[pointerId] = isPointerButtonInput(event, pointerIndex);
					reloadButtonPointers[pointerId] = isReloadPointerButtonInput(event,pointerId);
					if(!checkForHeldStick())
						resetHUD();
					break;
			}
		}
		catch(IllegalArgumentException e) {
			Log.d(TAG, e.toString());
		}
	}

	private boolean isPointerStickInput(MotionEvent event, int pointerIndex) {
		Vector2 eventVector = new Vector2(event.getX(pointerIndex), event.getY(pointerIndex));
		boolean result = false;
		if (Vector2.Distance(stick.getCenter(), eventVector) <= moveRadius) {
			result = true;
			stickPointerId = event.getPointerId(pointerIndex);
		}
		return result;
	}

	private boolean isPointerButtonInput(MotionEvent event, int pointerIndex) {
		Vector2 eventVector = new Vector2(event.getX(pointerIndex), event.getY(pointerIndex));
		boolean result = false;
		if (Vector2.Distance(button.getCenter(), eventVector) <= buttonRadius) {
			result = true;
		}
		return result;
	}

	private boolean isReloadPointerButtonInput(MotionEvent event, int pointerIndex) {
		Vector2 eventVector = new Vector2(event.getX(pointerIndex), event.getY(pointerIndex));
		boolean result = false;
		if (Vector2.Distance(reloadButton.getCenter(), eventVector) <= reloadButtonRadius) {
			result = true;
		}
		return result;
	}

	private boolean checkForHeldStick() {
		boolean result = false;
		int stickPointerIndex = 0;
		if(event != null && isStickPressed()) {
			if (event.getPointerCount() > 1)
				stickPointerIndex = event.findPointerIndex(stickPointerIndex);
			Vector2 stickVector = new Vector2(event.getX(stickPointerIndex), event.getY(stickPointerIndex));
			if (Vector2.Distance(stickBackground.getCenter(), stickVector) > moveRadius) {
				stickVector = Vector2.Subtract(stickVector, stickBackground.getCenter());
				stickVector.Normalize(moveRadius);
				stickVector = Vector2.Add(stickVector, stickBackground.getCenter());
			}
			stick.setCenter(stickVector);
			result = true;
		}
		return result;
	}


	public boolean isStickPressed() {
		return stickPointerId > -1;
	}

	public boolean isButtonPressed() {
		boolean result = false;
		for (int i = 0; i < buttonPointers.length; i++) {
			if (buttonPointers[i])
				result = true;
		}
		return result;
	}

	public boolean isReloadButtonPressed() {
		boolean result = false;
		for (int i = 0; i < reloadButtonPointers.length; i++) {
			if (reloadButtonPointers[i])
				result = true;
		}
		return result;
	}

	// calls updateCheckPointer for each pointer in event
/*	public void update() {
		int stickPointerIndex = 0;
		if(event != null && isStickPressed()) {
			if (event.getPointerCount() > 1)
				stickPointerIndex = event.findPointerIndex(stickPointerIndex);
			Vector2 stickVector = new Vector2(event.getX(stickPointerIndex), event.getY(stickPointerIndex));
			if (Vector2.Distance(stickBackground.getCenter(), stickVector) > moveRadius) {
				stickVector = Vector2.Subtract(stickVector, stickBackground.getCenter());
				stickVector.Normalize(moveRadius);
				stickVector = Vector2.Add(stickVector, stickBackground.getCenter());
			}
			stick.setCenter(stickVector);
		}
		else
			resetHUD();

		if (isButtonPressed()) {
			// load pressed button image
		}
		else {
			// load default button image
		}
	}*/

	public void resetHUD() {
		stick.setCenter(stickBackground.getCenter());
	}

	public int isPlayerMoving() {
		int result = NEUTRAL;
		if(isStickPressed()) {
			float distance = Vector2.Distance(stickBackground.getCenter(), stick.getCenter());
			if(distance < tiltRadius) result = TILT;
			else result = MOVE;
		}
		return result;
	}

	public Vector2 getPlayerDirection() {
		if (stick.getCenter().X != stickBackground.getCenter().X || stick.getCenter().Y != stickBackground.getCenter().Y) {
			stickAngle = new Vector2(stick.getCenter().X - stickBackground.getCenter().X, stick.getCenter().Y - stickBackground.getCenter().Y);
			stickAngle.Normalize();
		}
		return stickAngle;
	}

	// test
	public void draw(Canvas canvas) {
		stickBackground.draw(canvas);
		stick.draw(canvas);
		healthBar.draw(canvas);
		button.draw(canvas);
		reloadButton.draw(canvas);
	}

	public void drawText(Canvas canvas, Player player) {
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.WHITE);
		paint.setAntiAlias(true);
		paint.setTextSize(24);
		canvas.drawText("Clip: " + player.getWeapon().ammoInClip + "/" + player.getWeapon().constAmmoInClip, 600, 100, paint);
		canvas.drawText("# Clips: " + player.getWeapon().numberOfClips, 600, 130, paint);
	}
}