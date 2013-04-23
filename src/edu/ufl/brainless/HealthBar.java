package edu.ufl.brainless;

import java.util.ArrayList;
import java.util.Stack;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

public class HealthBar {
	private static final String TAG = HealthBar.class.getSimpleName();
	
	private int lifeBarPosX = 500;
	private int lifeBarPosY = 15;
	private int lifeBarHeight = 30;
	
	private Player player;					//Reference for health
	private ArrayList<Sprite> tickArray = new ArrayList<Sprite> (50);
	
	private int currentHealth;
	private Rect healthBar;
	
	public HealthBar(Player player)
	{
		this.player = player;
		currentHealth = player.getHealth();
		healthBar = new Rect(lifeBarPosX, lifeBarPosY, lifeBarPosX + currentHealth, lifeBarPosY + lifeBarHeight);
	}
	
	public void update()
	{
		currentHealth = player.getHealth();
		healthBar.set(lifeBarPosX, lifeBarPosY, lifeBarPosX + currentHealth * 2, lifeBarPosY + lifeBarHeight);
	}
	
	public void draw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.RED);
		canvas.drawRect(healthBar, paint);
		
	}
	
}
