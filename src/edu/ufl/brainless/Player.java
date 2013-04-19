package edu.ufl.brainless;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

public class Player extends Actor {
	private int health; // health of player ranges from 0 to 100
	private boolean isDead;
	private Weapon heldWeapon;
	boolean reloadFlag = true;
	private static final String TAG = Player.class.getSimpleName();
	public int invinc_timer = 0;

	public Player(float x, float y, float angle) { // default constructor
		super(x, y, angle);
		heldWeapon = new Weapon();
		health = 100; // every player starts out with full health
		isDead = false;
		cameraPositionCalculate();
		// TODO Auto-generated constructor stub
		Log.d(TAG, "Player created.");
	}

	public Player(Bitmap texture, float x, float y, float angle, Vector2 direction, float speed) { // default constructor
		super(texture, x, y, angle, direction, speed);
		heldWeapon = new Weapon();
		health = 100; // every player starts out with full health
		isDead = false;
		cameraPositionCalculate();
		// TODO Auto-generated constructor stub
		Log.d(TAG, "Player created.");
	}

	public Player(Bitmap texture, float x, float y, float angle, float speed, int health, boolean isDead, Weapon weapon) {
		super(texture, x, y, angle, new Vector2(0,0), speed);
		heldWeapon = weapon;
		this.isDead = isDead;
		this.health = health;
		cameraPositionCalculate();
		// TODO Auto-generated constructor stub
		Log.d(TAG, "Player created.");
	}

	// getters and setters
	public int getHealth(){
		return health;
	}

	public void setHealth(int health){
		this.health = health;
	}

	//death and damage methods
	public boolean isDead(){
		return this.isDead;
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



	public void inflictDamage(int DamageAmount){
		if(health - DamageAmount > 0 && invinc_timer <= 0) {
			health -= DamageAmount;
			SoundManager.playSound("player_injured", 1.2f, false);
			invinc_timer = 90; //if you get hit, and you aren't invulnerable, make yourself invulnerable for 1.5 seconds
		}
		else if (health - DamageAmount <= 0 && invinc_timer <= 0){
			health = 0;
			SoundManager.playSound("player_injured", 1.0f, false);
			this.setIsDead(true);
			this.death(); //if you get hit, and you would die, and aren't invulnerable, die
		}
	}

	public void collision(Enemy x){
		this.setIsDead(true);

	}

	public void death(){
		this.isDead = true;
	}

	/*
	public void checkDirection(float angle) {
		if(angle > -45 && angle <= 45)
			super.LoadBitmap(ResourceManager.getBitmap(R.drawable.player_right));
		else if(angle > 45 && angle <= 135)
			super.LoadBitmap(ResourceManager.getBitmap(R.drawable.player_down));
		else if(angle > 135 || angle <= -135)
			super.LoadBitmap(ResourceManager.getBitmap(R.drawable.player_left));
		else if(angle > -135 && angle <= -45)
			super.LoadBitmap(ResourceManager.getBitmap(R.drawable.player_up));
	}*/

	public void update(HUD hud) {
		// update player movement
		direction = hud.getPlayerDirection();
		this.angle = (float)(Math.atan2(direction.Y, direction.X) * 180 / Math.PI);
		if(hud.isPlayerMoving() == HUD.TILT) {
			position.X += direction.X * 0;
			position.Y += direction.Y * 0;
			this.rect.X=position.X;
			this.rect.Y=position.Y;
			//checkDirection(angle);
		}
		else if(hud.isPlayerMoving() == HUD.MOVE) {
			super.update();
			//checkDirection(angle);
		}

		// Check if player is outside of screen
		if (position.X < 0)
			position.X = 0;
		else if (position.X > 800 - rect.width)
			position.X = 800 - rect.width;
		if (position.Y < 0)
			position.Y = 0;
		else if (position.Y > 480 - rect.height)
			position.Y = 480 - rect.height;

		// Check if player fired weapon
		if (hud.isButtonPressed()) {
			heldWeapon.shoot(getCenter().X, getCenter().Y, angle, direction, speed);
		}
		if (hud.isReloadButtonPressed() && reloadFlag){ //reload button works, but there is no reload delay or sound effect
			heldWeapon.reload();
			this.reloadFlag = false;
		}
		
		if(!hud.isReloadButtonPressed()){
			this.reloadFlag = true;
		}
		heldWeapon.update(position);
		if(invinc_timer > 0){
			invinc_timer --;
		}
	}

	public ArrayList<Bullet> getBullets() {
		return heldWeapon.getBullets();
	}

	public void removeBullet(int index) {
		heldWeapon.removeBullet(index);
	}
	
	public void cameraPositionCalculate(){
		cameraPosition = new Vector2(Camera.playerPosition.X, Camera.playerPosition.Y);
	}
	
	public void isShoved(Enemy e){
		position.X += e.direction.X * e.knockback;
		position.Y += e.direction.Y * e.knockback;
		this.rect.X=position.X;
		this.rect.Y=position.Y;
	}
	
	@Override
	public void draw(Canvas canvas) {
		heldWeapon.draw(canvas);
		super.draw(canvas);
	}
}