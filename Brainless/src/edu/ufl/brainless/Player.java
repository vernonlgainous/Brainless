package edu.ufl.brainless;

import android.graphics.Bitmap;

public class Player extends Actor {
	public int health; //health of player ranges from 0 to 100
	public boolean isDead;
	
	private static final String TAG = Player.class.getSimpleName();
	
	public Player(Bitmap texture, float x, float y) { //default constructor
		super(texture, x, y);
		health = 100; //every player starts out with full health
		isDead = false;
		// TODO Auto-generated constructor stub
	}
	public Player(Bitmap texture, float x, float y, int health, boolean isDead) {		
		super(texture, x, y);
		this.isDead = isDead;
		this.health = health;
		// TODO Auto-generated constructor stub
	}
	
	// Get Set methods
	
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
	
	//death/ damage method
	public boolean isDead(){
		if(this.isDead == true){
			return true;
		}
		else{
			return false;
		}
	}
	
	public void Damage(int DamageAmount){
		if(health - DamageAmount > 0){
			this.health = this.health - DamageAmount;
		}
		else{
			this.health = 0;
			this.setIsDead(true);
			this.Death();
		}
	}
	
	public void Death(){
		if (this.isDead()){
			// TODO execute player death
		}		
	}	
}
