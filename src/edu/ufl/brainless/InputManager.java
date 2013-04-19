package edu.ufl.brainless;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;

@SuppressLint("WrongCall")
public class InputManager {

	/*  Notes:
	 *  InputManager is created by GamePanel class. GamePanel passes itself into InputManager, which uses GamePanel to calculate the locations of stickNeutral and actionButton.
	 *  When GamePanel receives an MotionEvent, it calls InputManager.passEvent(Event), which stores the event as an attribute. GameThread runs update() every thread cycle,
	 *  which processes the event into its input properties.
	 *  When Player runs InputManager.checkInput(), InputManager returns an int array[3] called input. input[0] = neutral/tilt/move (0,1,2), input[1] = angle,
	 *  input[2] = button (0 = not pressed, 1 = pressed). If needed, checkInput can be overloaded for multiple classes e.g. checkInput(Weapon) returns something else.
	 *  
	 *  Parameters defined in constructor: (Arbitrary values for now)
	 *  stickNeutral: x = getWidth()/6. y = getHeight()*1/4.
	 *  tiltRadius: 30px. moveRadius = 70px.
	 *  actionButton: x = getWidth()/6. y = getHeight()*3/4. Radius = 50px.	
	 *  
	 *  0,0 is on the top left of the screen.
	 *  Information such as locations of buttons are stored in type int. MotionEvent.getX and getY return type float. Consult with group if angle should be type float.
	 *  
	 *  Should input information be calculated on request or in every cycle?
	 *  
	 *  InputManager doesn't make distinctions between the action button being held down and the action button being pressed and released between every update interval.
	 *  
	 *  Need to test how motion pointers work.
	 *  
	 *  getPressure can return higher than 1 "depending on calibration of input device". Need to test.
	 *  
	 *  To do:
	 *  I'm not sure right now.
	 *  Make real parameters for stick and button position?
	 */

	private static final String TAG = GameThread.class.getSimpleName();

	private MotionEvent event;	// Contains input
	private GamePanel panel;	// Used to call getHeight, getWidth, etc.
	private int[] screenSize;	// 0 = width. 1 = height.


	// Stick locations
	private int[] stickNeutral;	// Location of stick at rest. 0 = x. 1 = y.
	private int tiltRadius;		// Range where player is tilting stick.
	private int moveRadius;		// Range where player is moving stick.

	// Button locations
	private int[] actionButton; // Location of action button. 0 = x. 1 = y.
	private int abRadius;		// Radius of the action button.

	// Input properties
	private int bPointer = 0; 		// Pointer to data which determines whether button is held down or not. 
	private int sPointer = 0;		// Pointer to data which determines whether stick location is held or not.


	private int stickPosition;	// Stick property. 0 = neutral, 1 = tilt, 2 = move.
	private int stickAngle;		// Angle of stick input.
	private int buttonPress;	// Button property. 0 = not pressed, 1 = pressed.

	public InputManager(GamePanel panel) {
		screenSize = new int[2];
		screenSize[0] = panel.getWidth();
		screenSize[1] = panel.getHeight();
		Log.d(TAG, "Screen size: " + screenSize[0] + "x" + screenSize[1] + ".");

		stickNeutral = new int[2];
		stickNeutral[0] = screenSize[0]/6;
		stickNeutral[1] = screenSize[1]/4;
		tiltRadius = 30;
		moveRadius = 70;
		Log.d(TAG, "Stick position (" + stickNeutral[0] + "," + stickNeutral[1] + ").");

		actionButton = new int[2];
		actionButton[0] = screenSize[0]/6;
		actionButton[1] = screenSize[1]*3/4;
		Log.d(TAG, "Button position (" + actionButton[0] + "," + actionButton[1] + ").");
		abRadius = 50;

		this.panel = panel;

		drawButtons();
	}

	public int[] checkInput() {
		// return input array
		Log.d(TAG, "stickPosition = " + stickPosition + "; stickAngle = " + stickAngle + "; buttonPress = " + buttonPress);
		int[] input = new int[3];		// Input array. 0 = stickPosition, 1 = stickAngle, 2 = buttonPress.
		input[0] = stickPosition;
		input[1] = stickAngle;
		input[2] = buttonPress;

		return input;
	}
	public void passEvent(MotionEvent event) {
		this.event = event;
	}

	// calls updateCheckPointer for each pointer in event
	public void update() {
		//Log.d(TAG, "Updating input attributes.");
		if(event != null) { if(event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
			for(int c = 0; c < event.getPointerCount(); c++) { updateCheckPointer(c); }
		}}
	}

	// updates input properties based on the pointer passed to it
	private void updateCheckPointer(int pointerIndex) {

		double tempMath; // Stores previous calculation so it's easier for me to read.
		float eventX = event.getX(pointerIndex);
		float eventY = event.getY(pointerIndex);

		// If distance between event and stick is less than the radius of the stick range, input is stick input.
		tempMath = Math.sqrt((double) (eventX - stickNeutral[0])*(eventX - stickNeutral[0])+(eventY - stickNeutral[1])*(eventY - stickNeutral[1]));
		if(tempMath <= moveRadius) {
			// Check to see if event is tilt or move.
			if(tempMath <= tiltRadius) { stickPosition = 1;} // Tilt
			else { stickPosition = 2; } // Move

			// Adjust stick angle.
			int deltaX = (int) eventX - stickNeutral[0];
			int deltaY = (int) eventY - stickNeutral[1];			
			stickAngle = (int) (Math.atan2(deltaY, deltaX) * 180 / Math.PI);
			if (stickAngle < 0) { stickAngle += 360; }

			if(stickPosition == 1) { Log.d(TAG, "Stick tilted, angle = " + stickAngle); }
			else { Log.d(TAG, "Stick moved, angle = " + stickAngle); }
		}

		// If distance between event and button is less than the radius of the button, input is button input.
		else {
			tempMath = Math.sqrt((double) (eventX - actionButton[0])*(eventX - actionButton[0])+(eventY - actionButton[1])*(eventY - actionButton[1]));			
			if(tempMath <= abRadius) {			
				Log.d(TAG, "Button input.");
				// Change button status to pressed.
				buttonPress = (int) event.getPressure(bPointer);
			}
		}
	}

	// not sure if this is needed with new implementation
	public void noEvent() {
		// check to see whether old pointers still relevant

	}

	// test
	public void drawButtons() {
		Canvas canvas = new Canvas();
		Paint p = new Paint();
		p.setColor(0);
		canvas.drawCircle((float) actionButton[0], (float) actionButton[1], (float) abRadius, p);
		canvas.drawCircle((float) stickNeutral[0], (float) stickNeutral[1], (float) moveRadius, p);
		p.setColor(0);
		canvas.drawCircle((float) stickNeutral[0], (float) stickNeutral[1], (float) tiltRadius, p);
		panel.onDraw(canvas);
		Log.d(TAG, "Drawing...");
	}

} 