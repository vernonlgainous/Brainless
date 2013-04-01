package edu.ufl.brainless;

import java.util.ArrayList;
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

public class Level extends HUD {
	private Player player; // Player	
	private ArrayList<Enemy> enemies;
	private static final String TAG = Level.class.getSimpleName();
	private Vector2 direction = new Vector2(1,0);
	//private Enemy enemy=new Enemy(ResourceManager.getBitmap(R.drawable.enemy),100f,200f,0f,direction,2f,100,25,false);
	public int bites = 0;
	private int zombieTimer = 0;
	private int zombieInterval = 600;
	private static Paint textPaint;
	private static final int WAITING_FOR_SURFACE = 0;
	private static final int COUNTDOWN = 1;
	private static final int RUNNING = 2;
	private static final int GAMEOVER = 3;
	private int mode = WAITING_FOR_SURFACE;
	
	public Player getPlayer() {
		return player;
	}
	
	public Level() {
		player = new Player(ResourceManager.getBitmap(R.drawable.player), 0, 0, 0f, 3f, 100, false, new Weapon("Pistol", 8, 3, 90, 25, 8));
		player.setCenter(new Vector2(400, 240));
		enemies= new ArrayList<Enemy>();
		addZombie();
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
			
		for(Enemy e : enemies) {
			if (!e.isDead())
				e.update(player.position);
		}
		collisionCheck();
		if (player.isDead())
			restart();		
	}
	
	public void collisionCheck(){
		Log.d(TAG,"checking for collision");
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

		for(Enemy e : enemies) {
			if (Rectangle.Intersects(player.rect, e.rect) && !e.isDead())
				player.inflictDamage(e.damage);
				Log.d(TAG,"collision detected!");
				
				//decrement health
				healthBar.clear();
		}
	}
	
	// restart the game
	public void restart(){
		Log.d(TAG,"restart" + bites);
		enemies.clear();
		player = new Player(ResourceManager.getBitmap(R.drawable.player), 0, 0, 0f, 5f, 100, false, new Weapon("Pistol", 8, 3, 90, 25, 8));
		player.setCenter(new Vector2(400, 240));
		healthBar = new Sprite(ResourceManager.getBitmap(R.drawable.health_bar + bites), 585, 15,0);
		if (bites ==5){
			mode = GAMEOVER;
		}
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
		for (Enemy e : enemies)
			e.draw(canvas);
		player.draw(canvas);
		healthBar.draw(canvas);
		if (mode == GAMEOVER){
			Level.drawGameOverScreen(canvas, 500f, 900f);
			//mode = WAITING_FOR_SURFACE;
			bites = 0;
		}
	}
}
