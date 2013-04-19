package edu.ufl.brainless;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.util.Log;

public class Weapon extends Item {
	int shotsPerSecond; // Number of shots weapon fires in second
	float attackTimer = 0; // Timer to see if we can fire shot again, counts down, 1 sec = 60 frames
	int ammoRemaining;
	final int constAmmoInClip; //keep track of original number of bullets in clip
	int ammoInClip; // changes as the gun is fired
	int numberOfClips;
	int reloadDuration;
	float reloadTimer; // Timer to see if reload is complete, counts up, 1 sec = 60 frames
	int weaponDamage;
	boolean reloading = false; // true if weapon is reloading
	public ArrayList<Bullet> bullets = new ArrayList<Bullet>();//1
	private static final String TAG = Weapon.class.getSimpleName();

	//constructors
	public Weapon(){ // default constructor
		super();
		shotsPerSecond = 1;
		numberOfClips = 3; //default have 3 clips
		constAmmoInClip = 10; //default ammo in clip is 10
		ammoInClip = constAmmoInClip;
		ammoRemaining = numberOfClips*ammoInClip; 		
		reloadDuration = 120;
		weaponDamage = 50;		
	}	

	public Weapon(String weaponName, int constAmmoInClip, int numberOfClips,
			int reloadDuration, int weaponDamage, int shotsPerSecond){
		super(weaponName);
		this.constAmmoInClip = constAmmoInClip;
		this.numberOfClips = numberOfClips;
		this.ammoInClip = constAmmoInClip;
		this.ammoRemaining = numberOfClips*ammoInClip;		
		this.reloadDuration = reloadDuration;
		this.weaponDamage = weaponDamage;
		if (shotsPerSecond >= 0)
			this.shotsPerSecond = shotsPerSecond;
		else
			shotsPerSecond = 1;
	}

	// setters and getters
	public int getAmmoRemaining() {
		return ammoRemaining;
	}

	public void setAmmoRemaining(int ammoRemaining) {
		this.ammoRemaining = ammoRemaining;
	}

	public int getAmmoInClip() {
		return ammoInClip;
	}

	public void setAmmoInClip(int ammoInClip) {
		this.ammoInClip = ammoInClip;
	}

	public int getreloadDuration() {
		return reloadDuration;
	}

	public void setreloadDuration(int reloadDuration) {
		this.reloadDuration = reloadDuration;
	}

	public int getWeaponDamage() {
		return weaponDamage;
	}

	public void setWeaponDamage(int weaponDamage) {
		this.weaponDamage = weaponDamage;
	}

	public int getNumberOfClips() {
		return numberOfClips;
	}

	public void setNumberOfClips(int numberOfClips) {
		this.numberOfClips = numberOfClips;
	}

	public ArrayList<Bullet> getBullets() {
		return bullets;
	}

	//shoot and reload methods
	public boolean shoot(float x, float y, float angle, Vector2 direction, float speed){
		if (attackTimer == 0 && reloadTimer == 0) {
			if (this.ammoRemaining == 0){ // out of ammo
				return false;
			}		
			else if(this.ammoRemaining != 0 && this.ammoInClip == 0){ // reload weapon
				SoundManager.playSound("pistol_reload", 0.5f, false);
				reloading = true;
				return false;
			}
			else{ // fire bullet
				attackTimer = 60/shotsPerSecond;
				SoundManager.playSound("submachine", 1.0f, false);
				Bullet b1 = new Bullet(ResourceManager.getBitmap(R.drawable.bullet),x, y,angle,direction,speed);//1
				bullets.add(b1);						
				this.ammoRemaining--;
				this.ammoInClip--;
				if(this.ammoRemaining == 0){
					this.numberOfClips = 0;
				}
				return true;			
			}
		}
		else
			return false;
	}

	public void update(Vector2 playerPosition) {
		if (attackTimer > 0) {
			attackTimer--;

		}

		if (reloading) {

			if (++reloadTimer >= reloadDuration)
				reload();
		}

		for(int i = bullets.size() - 1; i >= 0; i--) {
			Bullet temp = bullets.get(i);
			temp.update(playerPosition);

			// Check to see if bullet is off-screen, delete if it is
			if (temp.position.X < -temp.rect.width || temp.position.X > 800 || temp.position.Y > 480 || temp.position.Y < -temp.rect.height)
				bullets.remove(temp);
		}
	}

	public void removeBullet(int index) {
		bullets.remove(index);
	}

	public void reload(){
	    if(numberOfClips > 0){
			reloading = false;
			reloadTimer = 0;
			numberOfClips--;
			ammoInClip = constAmmoInClip;
		}
	}

	public void draw(Canvas canvas) {
		for(Bullet b : bullets) {
			b.draw(canvas);
		}
	}
}