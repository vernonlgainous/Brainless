package edu.ufl.brainless;

import android.graphics.Bitmap;

public class Enemy extends Actor {
	public int health; //health of enemy ranges from 0 to 100
	public boolean isDead;

	private static final String TAG = Enemy.class.getSimpleName();

	//constructors
	public Enemy(Bitmap texture, float x, float y, float angle, Vector2 direction, float speed) {//default constructor		
		super(texture, x, y, angle, direction, speed);
		health = 100; //every enemy starts out with full health
		isDead = false;
		// TODO Auto-generated constructor stub
	}
	public Enemy(Bitmap texture, float x, float y, float angle, Vector2 direction, float speed, int health, boolean isDead) {		
		super(texture, x, y, angle, direction, speed);		
		this.isDead = isDead;
		this.health = health;
		// TODO Auto-generated constructor stub
	}

	// getters and setters
	public int getHealth(){
		return health;
	}

	public void setHealth(int health){
		this.health = health;
	}

	public boolean getIsDead(){
		return isDead;
	}

	public void setIsDead(boolean isDead){
		this.isDead = isDead;
	}

	//death and damage methods
	public boolean isDead(){
		if(this.isDead == true){
			return true;
		}
		else{
			return false;
		}
	}

	public void inflictDamage(int DamageAmount){
		if(health - DamageAmount > 0)
			health -= DamageAmount;
		else {
			health = 0;
			this.setIsDead(true);
			this.death();
		}
	}

	public void death(){
		if (this.isDead()){
			// TODO execute enemy death
		}		
	}
}
