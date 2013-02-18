package edu.ufl.brainless;

import android.view.MotionEvent;
import java.lang.Math;
import android.util.Log;

public class InputManager {
	
	/*  Notes:
	 *  InputManager is created by GamePanel class. GamePanel passes itself into InputManager, which uses GamePanel to calculate the locations of stickNeutral and actionButton.
	 *  When GamePanel receives an MotionEvent, it calls InputManager.passEvent(Event). passEvent calculates input properties and stores them to send to other classes using
	 *  checkInput. Player calls InputManager.checkInput() and InputManager returns input array int size 3. input[0] = neutral/tilt/move (0,1,2), input[1] = angle,
	 *  input[2] = button (0 = not pressed, 1 = pressed). If needed, checkInput can be overloaded for multiple classes e.g. checkInput(Weapon) returns something else.
	 *  
	 *  Parameters defined in constructor: (Arbitrary values for now)
	 *  stickNeutral: x = getWidth()/6. y = getHeight()*1/4.
	 *  tiltRadius: 30px. moveRadius = 70px.
	 *  actionButton: x = getWidth()/6. y = getHeight()*3/4. Radius = 50px.	
	 *  
	 *  0,0 is on the top left of the screen.
	 *  I'm starting to wonder whether it might be better to call methods from the actor classes using event listeners e.g. onTouch calls Player.action or something.  
	 *  Information such as locations of buttons are stored in int. MotionEvent.getX and getY return float. Consult with group if angle should be float.
	 *  
	 *  Should input information be calculated on request or in every cycle?
	 *  
	 *  Implementation possibly problematic for held-down MotionEvents. Possible: if button or motion press is detected, store getPointerId. When a loop doesn't detect a 
	 *  button press, run InputManager.noEvent(), which clears input properties. Run check when clearing input properties to see whether inputs are held down or not.
	 *  The problem is that, with how I've implemented it, a button that is pressed stays pressed because the button status is set when an event happens. Also possible:
	 *  store latest pointers on passEvent, check status of pointers on checkInput.
	 *  
	 *  Need to test how motion pointers work.
	 *  
	 *  getPressure can return higher than 1 "depending on calibration of input device". Need to test.
	 *  
	 *  To do:
	 *  passEvent(Event): define how to calculate stick angle in both halves of method.
	 *  noEvent(): everything.
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
	
	private int[] input;		// Input array. 0 = stickPosition, 1 = stickAngle, 2 = buttonPress.
	private int stickPosition;	// Stick property. 0 = neutral, 1 = tilt, 2 = move.
	private int stickAngle;		// Angle of stick input.
	private int buttonPress;	// Button property. 0 = not pressed, 1 = pressed.
		
	public InputManager(GamePanel panel) {
		//constructor
	}
	
	public int[] checkInput() {
		// return input array
		Log.d(TAG, "InputManager called to check input.");
		return input;
	}
	
	public void passEvent(MotionEvent event) {
		Log.d(TAG, "InputManager called to receive input.");
		
		double tempMath; // Stores previous calculation so I don't have to calculate it again.
		float eventX = event.getX();
		float eventY = event.getY();
		
		// If distance between event and stick is less than the radius of the stick range, input is stick input.
		// Change stick position and angle. Then check button pointer to see if it is no longer pressed.
		tempMath = Math.sqrt((double) (eventX - stickNeutral[0])*(eventX - stickNeutral[0])+(eventY - stickNeutral[1])*(eventX - stickNeutral[1]));
		Log.d(TAG, "Event distance from stick: " + (int) tempMath);
		
		if(tempMath <= moveRadius) {
			// Change stick pointer.
			sPointer = event.getPointerId(0);	
			
			// Check to see if event is tilt or move.
			if(tempMath <= tiltRadius) { stickPosition = 1;	Log.d(TAG, "Stick tilted."); } // Tilt
			else { stickPosition = 2; Log.d(TAG, "Stick moved."); } // Move
			
			// Adjust stick angle.
			
			// Check button pointer. If button is no longer pressed by last motion pointer, buttonPress = 0. Else buttonPress >= 1.
			if(bPointer != 0) {	buttonPress = (int) event.getPressure(bPointer); 
				if(buttonPress == 0) { Log.d(TAG, "Button released."); }
				else { Log.d(TAG, "Button p."); }
			}
		}
			
		// If distance between event and button is less than the radius of the button, input is button input.
		// Change button status. Then check stick pointer to see if it's no longer pressed. If it is, adjust values.
		else {
			tempMath = Math.sqrt((double) (eventX - actionButton[0])*(eventX - actionButton[0])+(eventY - actionButton[1])*(eventX - actionButton[1]));
			if(tempMath <= abRadius) {			
				// Change button pointer.
				bPointer = event.getPointerId(0);
				
				// Change button status to pressed.
				buttonPress = (int) event.getPressure(bPointer);
				
				// Check stick pointer. If pressure >= 1, change properties.
				if(sPointer != 0) {
					// Stick is still held.
					if(event.getPressure(sPointer) >= 1) {
						// Calculate sPointer distance.
						float sPX = event.getX(sPointer);
						float sPY = event.getY(sPointer);
						tempMath = Math.sqrt((double) (sPX - stickNeutral[0])*(sPX - stickNeutral[0])+(sPY - stickNeutral[1])*(sPY - stickNeutral[1]));
					
						// Check to see if sPointer is tilt or move.
						if(tempMath <= tiltRadius) { stickPosition = 1;	} // Tilt
						else { stickPosition = 2; } // Move
						
						// Calculate stick angle.
						
					}
				}
			}
		}
	}
	
	public void noEvent() {
		// check to see whether old pointers still relevant
	}
	
} 