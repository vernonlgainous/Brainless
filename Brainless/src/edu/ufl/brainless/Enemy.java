package edu.ufl.brainless;

import android.graphics.Bitmap;
import android.util.Log;

public class Enemy extends Actor {
	public int health; //health of enemy ranges from 0 to 100
	public boolean isDead;
	public int damage;

	private static final String TAG = Enemy.class.getSimpleName();

	//constructors
	public Enemy(Bitmap texture, float x, float y, float angle, Vector2 direction, float speed) {//default constructor		
		super(texture, x, y, angle, direction, speed);
		health = 100; //every enemy starts out with full health
		isDead = false;
		damage = 25;
	}
	public Enemy(Bitmap texture, float x, float y, float angle, Vector2 direction, float speed, int health, int damage, boolean isDead) {		
		super(texture, x, y, angle, direction, speed);		
		this.isDead = isDead;
		this.health = health;
		this.damage = damage;
	}
	
	public void update(Vector2 player){
		Log.d(TAG, "enemy position "+position.X+" "+position.Y);
		this.direction.X=player.X-this.position.X;
		this.direction.Y=player.Y-this.position.Y;
		direction.Normalize();
		this.angle = (float)(Math.atan2(direction.Y, direction.X) * 180 / Math.PI);
		//checkDirection(angle);

		super.update();
	}
	
	/*
	public void checkDirection(float angle) {
		if(angle > -45 && angle <= 45)
			super.LoadBitmap(ResourceManager.getBitmap(R.drawable.enemy_right));
		else if(angle > 45 && angle <= 135)
			super.LoadBitmap(ResourceManager.getBitmap(R.drawable.enemy_down));
		else if(angle > 135 || angle <= -135)
			super.LoadBitmap(ResourceManager.getBitmap(R.drawable.enemy_left));
		else if(angle > -135 && angle <= -45)
			super.LoadBitmap(ResourceManager.getBitmap(R.drawable.enemy_up));
	}
	*/

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
		if(health - DamageAmount > 0) {
			health -= DamageAmount;
			SoundManager.playSound("player_injured", 0.8f, false);
		}
		else {
			health = 0;
			this.setIsDead(true);
			this.death();
		}
	}

	public void death(){
		if (this.isDead()){
			super.LoadBitmap(ResourceManager.getBitmap(R.drawable.zombie_dead));
		}		
	}
	
}
