package edu.ufl.brainless;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.Display;
import android.view.WindowManager;

public class Player extends Actor {
	private int health; // health of player ranges from 0 to 100
	private boolean isDead;
	private Weapon heldWeapon;

	private static final String TAG = Player.class.getSimpleName();

	public Player(float x, float y, float angle) { // default constructor
		super(x, y, angle);
		heldWeapon = new Weapon();
		health = 100; // every player starts out with full health
		isDead = false;
		// TODO Auto-generated constructor stub
	}
	
	public Player(Bitmap texture, float x, float y, float angle, Vector2 direction, float speed) { // default constructor
		super(texture, x, y, angle, direction, speed);
		heldWeapon = new Weapon();
		health = 100; // every player starts out with full health
		isDead = false;
		// TODO Auto-generated constructor stub
	}

	public Player(Bitmap texture, float x, float y, float angle, float speed, int health, boolean isDead, Weapon weapon) {
		super(texture, x, y, angle, new Vector2(0,0), speed);
		heldWeapon = weapon;
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
	
	public Weapon getWeapon() {
		return heldWeapon;
	}
	
	public void setWeapon(Weapon weapon) {
		this.heldWeapon = weapon;
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
	
	public void update(Vector2 direction) {
		this.direction = direction;
		this.angle = (float)(Math.atan2(direction.Y, direction.X) * 180 / Math.PI);
		super.update();
		
		// Check if player is outside of screen
		if (position.X < 0)
			position.X = 0;
		else if (position.X > 800 - rect.width)
			position.X = 800 - rect.width;
		if (position.Y < 0)
			position.Y = 0;
		else if (position.Y > 480 - rect.height)
			position.Y = 480 - rect.height;
	}
}
