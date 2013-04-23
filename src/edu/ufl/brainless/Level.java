package edu.ufl.brainless;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.content.res.Resources;
import android.graphics.Canvas;

public class Level {
	private Player player; // Player	
	private ArrayList<Enemy> enemies;
	private static final String TAG = Level.class.getSimpleName();
	private Vector2 direction = new Vector2(1,0);
	private GameThread thread;
	private HUD hud;
	//private Enemy enemy=new Enemy(ResourceManager.getBitmap(R.drawable.enemy),100f,200f,0f,direction,2f,100,25,false);
	public static int bites = 0; //non-static in jonthan's code
	private int zombieTimer = 0;
	private int zombieInterval = 600;
	private static Paint textPaint;
	private static final int WAITING_FOR_SURFACE = 0;
	private static final int COUNTDOWN = 1;
	private static final int RUNNING = 2;
	private static final int GAMEOVER = 3;
	private static final int HEALTHPACK = 4;
	private int mode = WAITING_FOR_SURFACE;
	private ArrayList<Actor> items;
	private ArrayList<Actor> ammunitions;
	private int healthTimer = 0;
	private int healthInterval = 600;
	Actor ammo = new Actor(ResourceManager.getBitmap(R.drawable.ammo), 200f, 275f, 0, new Vector2(0,0), 0);
	//Sprite healthPack = new Sprite(ResourceManager.getBitmap(R.drawable.health_pack), 100f,200f,0);

	public Player getPlayer() {
		return player;
	}

	public Level(GameThread thread, HUD hud) {
		player = new Player(ResourceManager.getBitmap(R.drawable.player), 0, 0, 0f, 5f, 100, false, new MachineGun());
		player.setCenter(new Vector2(400, 240));
		enemies= new ArrayList<Enemy>();
		addZombie();
		items = new ArrayList<Actor>();
		ammunitions = new ArrayList<Actor>();
		ammunitions.add(ammo); //this makes it into a healthpack. but then it moves.
		
		this.thread = thread;
		this.hud = hud;
	}

	public void addHealth(){
		//Random rand = new Random();
		//public Actor(Bitmap texture, float x, float y, float angle, Vector2 direction, float speed) {
		Vector2 newVector = new Vector2(0,0);
		Actor healthPack = new Actor(ResourceManager.getBitmap(R.drawable.health_pack), 100f,200f, 0, newVector, 0);
		items.add(healthPack);
	}

	public void addZombie() {
		Random rnd = new Random();
		int selection = rnd.nextInt(4);
		addZombie(selection);			
	}

	// adds zombie to spawn from off the screen, 0 = top, 1 = right, 2 = down, 3 = left
	public void addZombie(int selection) {
		Random rnd = new Random();
		Enemy temp = new Enemy(ResourceManager.getBitmap(R.drawable.zombie),0,0,0f,direction,2f,100,25,false);
		Vector2 tempPos = new Vector2(0,0);
		if (selection == 0) { // top
			tempPos.X = rnd.nextInt(800-(int)temp.rect.width) + temp.rect.width/2;
			tempPos.Y = -temp.rect.height/2;
		}
		else if (selection == 1) { // right
			tempPos.X = 800 + temp.rect.width/2;
			tempPos.Y = rnd.nextInt(480-(int)temp.rect.height) + temp.rect.height/2;
		}
		else if (selection == 2) { // down
			tempPos.X = rnd.nextInt(800-(int)temp.rect.width) + temp.rect.width/2;
			tempPos.Y = 800 + temp.rect.height/2;
		}
		else if (selection == 3) { // left
			tempPos.X = -temp.rect.width/2;
			tempPos.Y = rnd.nextInt(480-(int)temp.rect.height) + temp.rect.height/2;
		}
		temp.setCenter(tempPos);
		enemies.add(temp);
	}

	public void update(HUD hud) {
		player.update(hud);
		if (++zombieTimer >= zombieInterval) {
			zombieTimer = 0;
			addZombie();
		}
		if(++healthTimer >= healthInterval){
			healthTimer = 0;
			addHealth();
		}

		for(int i = enemies.size()-1; i>= 0;i--) {
			enemies.get(i).update(player.position);
		}
		collisionCheck();
		for(int i = items.size()-1; i>= 0;i--) {
			items.get(i).update(player.position);
		}
		for(int i = ammunitions.size()-1; i>= 0;i--){
			ammunitions.get(i).update(player.position);
		}
		if (player.isDead()){
			//restart();
			gameOver();
		}
	}

	public void collisionCheck(){
		//Log.d(TAG,"checking for collision");
		//Log.d(TAG,"rectangle height/width "+player.rect.height+" "+player.rect.width);

		ArrayList<Bullet> tempBullets = player.getBullets();


		// check bullet collisions
		for(int i = tempBullets.size() - 1; i >= 0; i--) {
			for(Enemy e : enemies) {
				if (Rectangle.Intersects(tempBullets.get(i).rect, e.rect) &&  !e.isDead()) {
					e.inflictDamage(player.getWeapon().weaponDamage);
					player.removeBullet(i);
				}
			}
		}
		boolean healthUsed = false;
		for (int i = items.size() - 1; i >= 0; i--){
			if (Rectangle.Intersects(player.rect, items.get(i).rect) && player.getHealth() != 100 && healthUsed != true){
				items.get(i).clear();
				healthUsed = true;
				//Level.draw(canvas);
				//redraw the health bar to reflect the changes then DONE!!!!
				//hud.healthBar.LoadBitmap(ResourceManager.getBitmap(R.drawable.health_bar + getHealthBarName()));
				
				//healthBar.clear();
				//healthBar.draw(canvas);
				items.remove(items.get(i));
				player.addHealth(50);
				hud.healthBar.LoadBitmap(ResourceManager.getBitmap(R.drawable.health_bar + getHealthBarName()));
				//mode = HEALTHPACK;
				//restart();
			}
			else {
				//do nothing
			}
		}
		boolean ammoUsed = false;
		for (int i = ammunitions.size() - 1; i >= 0; i--){
			if(Rectangle.Intersects(player.rect, ammunitions.get(i).rect) && healthUsed != true){
				ammunitions.get(i).clear();
				player.getWeapon().setNumberOfClips(player.getWeapon().getNumberOfClips()+1);
				ammoUsed = true;
				ammunitions.remove(ammunitions.get(i));
				//restart();
			}
		}

		for(int i = enemies.size() - 1; i >= 0; i--) {
			if (Rectangle.Intersects(player.rect, enemies.get(i).rect) && !enemies.get(i).isDead()){
				player.inflictDamage(enemies.get(i).damage);
				//Log.d(TAG,"collision detected!");
				//bites++;
				//decrement health
				//hud.healthBar.clear();
				hud.healthBar = new Sprite(ResourceManager.getBitmap(R.drawable.health_bar + getHealthBarName()), 585, 15,0);
				player.isShoved(enemies.get(i));
			}
		}
	}

	public int getHealthBarName(){
		int temp = 0;
		if(player.getHealth() == 100){
			temp = 0;
		}else if(player.getHealth() >= 75){
			temp = 1;
		}else if(player.getHealth() >= 50){
			temp = 2;
		}else if(player.getHealth() >= 25){
			temp = 3;
		}else if(player.getHealth() >= 0){
			temp = 4;
		}else if(player.getHealth() == 0){
			temp = 5;
		}
		return temp;
	}

	// restart the game
	public void restart(){
		//Log.d(TAG,"restart" + bites);
		enemies.clear();
		player = new Player(ResourceManager.getBitmap(R.drawable.player), 0, 0, 0f, 5f, 100, false, new MachineGun());
		player.setCenter(new Vector2(400, 240));
		hud.healthBar = new Sprite(ResourceManager.getBitmap(R.drawable.health_bar + bites), 585, 15,0);
		if (bites >=5){
			mode = GAMEOVER;
		}
	}

	public void gameOver(){
		mode = GAMEOVER;
	}

	//draw the game over screen.
	public static void drawGameOverScreen(Canvas canvas, float screenHeight, float screenWidth) {
		textPaint = new Paint();
		textPaint.setColor(Color.LTGRAY);
		textPaint.setTextSize(100);
		textPaint.setTextAlign(Paint.Align.CENTER);
		textPaint.setTextSize(100);
		canvas.drawText("GAME OVER", screenWidth / 2, (float) (screenHeight * 0.50), textPaint);
		textPaint.setTextSize(25);
		//canvas.drawText("You reached level " + level, screenWidth / 2, (float) (screenHeight * 0.60), textPaint);
		//canvas.drawText("Press 'Back' for Main Menu", screenWidth / 2, (float) (screenHeight * 0.85), textPaint);
	}


	public void draw(Canvas canvas) {		
		for (int i = enemies.size() - 1; i >= 0; i--)
			enemies.get(i).draw(canvas);
		for (int i = items.size() - 1; i >= 0; i--)
			items.get(i).draw(canvas);
		player.draw(canvas);
		hud.healthBar.draw(canvas);
		ammo.draw(canvas);
		if (mode == GAMEOVER){
			Level.drawGameOverScreen(canvas, 500f, 900f);
			//mode = WAITING_FOR_SURFACE;
			bites = 0;
		}
	}
}