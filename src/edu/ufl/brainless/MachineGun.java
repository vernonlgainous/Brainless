package edu.ufl.brainless;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.util.Log;

public class MachineGun extends Weapon{
	
	public MachineGun(){
		super("Machine Gun", 30, 10, 90, 50, 15);
	}
	
	public boolean shoot(float x, float y, float angle, Vector2 direction,
			float speed){
		return super.shoot(x, y, angle, direction, speed);
		
	}
	public void update(Vector2 playerPosition){
		super.update(playerPosition);
	}
}