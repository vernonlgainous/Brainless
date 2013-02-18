package edu.ufl.brainless;

import android.view.MotionEvent;
import android.util.Log;

public class InputManager {
	
	/*  Notes:
	 *  InputManager is created by GamePanel class. GamePanel passes itself into InputManager, which uses GamePanel to calculate the locations of stickNeutral and actionButton.
	 *  When GamePanel receives an MotionEvent, it calls InputManager.passEvent(Event). passEvent calculates input properties and stores them to send to other classes using
	 *  checkInput. Player calls InputManager.checkInput() and InputManager returns input array int size 3. input[0] = neutral/tilt/move (0,1,2), input[1] = angle,
	 *  input[2] = button (0 = not pressed, 1 = pressed). If needed, checkInput can be overloaded for multiple classes e.g. checkInput(Weapon) returns something else.
	 *  
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
	 *  Implemention possibly problematic for held-down MotionEvents. Possible: if button or motion press is detected, store getPointerId. When GamePanel doesn't receive a
	 *  MotionEvent, it runs InputManager.noEvent(), which clears input properties. Run check when clearing input properties to see whether inputs are held down or not. 
	 */
	
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
	private int bPointer; 		// Pointer to data which determines whether button is held down or not. 
	private int sPointer;		// Pointer to data which determines whether stick location is held or not.
	
	private int[] input;		// Input array. 0 = stickPosition, 1 = stickAngle, 2 = buttonPress.
	private int stickPosition;	// Stick property. 0 = neutral, 1 = tilt, 2 = move.
	private int stickAngle;		// Angle of stick input.
	private int buttonPress;	// Button property. 0 = not pressed, 1 = pressed.
		
	public InputManager(GamePanel panel) {
		//constructor
	}
	
	public int[] checkInput() {
		// return input array
		
		return input;
	}
	
	public void passEvent(MotionEvent event) {
		// check to see whether input is stick or button
		// update sPointer if stick or bPointer if button
		
		// calculate stick position
		// calculate stick angle
		// calculate button press
		
		// check to see whether old pointers still relevant
		
	}
	
	public void noEvent() {
		// check to see whether old pointers still relevant
	}
	
} 