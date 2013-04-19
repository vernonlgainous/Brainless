package edu.ufl.brainless;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.util.Log;

public class HandGun extends Weapon{
	public HandGun(){
		super("Hand Gun", 8, 3, 90, 25, 6);
	}
	
	public boolean shoot(float x, float y, float angle, Vector2 direction,
			float speed){
		return super.shoot(x, y, angle, direction, speed);
		
	}
	public void update(Vector2 playerPosition){
		super.update(playerPosition);
	}
}