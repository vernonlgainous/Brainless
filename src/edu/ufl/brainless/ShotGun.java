package edu.ufl.brainless;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.util.Log;

public class ShotGun extends Weapon{
	
	public ShotGun(){
		super("Shot Gun", 8, 3, 90, 25, 6);
	}
	
	public boolean shoot(float x, float y, float angle, Vector2 direction,
			float speed) {
		if (attackTimer == 0 && reloadTimer == 0) {
			if (this.ammoRemaining == 0) { // out of ammo
				return false;
			} else if (this.ammoRemaining != 0 && this.ammoInClip == 0) { // reload
																			// weapon
				SoundManager.playSound("pistol_reload", 0.5f, false);
				reloading = true;
				return false;
			} else { // fire bullet
				attackTimer = 60 / shotsPerSecond;
				SoundManager.playSound("submachine", 1.0f, false);
				Bullet b1 = new Bullet(
						ResourceManager.getBitmap(R.drawable.bullet), x , y,
						angle, direction, speed);
				Bullet b2 = new Bullet(
						ResourceManager.getBitmap(R.drawable.bullet), x , y,
						angle, direction, speed);
				bullets.add(b1); 
				bullets.add(b2);// still needs work on bullet spray
				this.ammoRemaining--;
				this.ammoInClip--;
				if (this.ammoRemaining == 0) {
					this.numberOfClips = 0;
				}
				return true;
			}
		} else
			return false;
	}
	
	public void update(Vector2 playerPosition){
		super.update(playerPosition);
	}
}