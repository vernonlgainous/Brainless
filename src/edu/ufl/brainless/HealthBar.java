package edu.ufl.brainless;

import java.util.ArrayList;
import java.util.Stack;

import android.graphics.Canvas;
import android.util.Log;

public class HealthBar {
	private static final String TAG = HealthBar.class.getSimpleName();
	
	private final int tickMax = 50;	//50 4px tick marks
	private float tickInitPositionX = 500;
	private float tickInitPositionY = 15;
	private float borderInitPositionX;
	private float borderInitPositionY;
	
	private int tickCount = 0;				//50 ticks drawn. Draw at tickCount * 4 + tickInitPositionX
	private int currentHealth;				//When damage is detected, clear current darkened ticks and make new darkened ticks
	private Player player;					//Reference for health
	private ArrayList<Sprite> tickArray = new ArrayList<Sprite> (50);
	
	private Sprite border;
	
	public HealthBar(Player player)
	{
		this.player = player;
		currentHealth = player.getHealth();
		
		borderInitPositionX = tickInitPositionX - 3;
		borderInitPositionY = tickInitPositionY - 3;
		
		border = new Sprite(ResourceManager.getBitmap(R.drawable.health_border), borderInitPositionX, borderInitPositionY, 0);
		addTick(brightTickCount());
	}
	
	public void update()
	{
		if(brightTickCount() < tickCount)
		{
			removeTick(1);
		}
		
		if(checkStatus() == -1)
		{
			removeTick(tickCount - brightTickCount());
			darkenTick(brightTickCount(currentHealth) - player.getHealth());
		}
		else if(checkStatus() == 1)
		{
			removeTick(tickCount - brightTickCount());
			addTick(brightTickCount() - brightTickCount(currentHealth));
		}
		currentHealth = player.getHealth();
	}
	
	public void draw(Canvas canvas) {
		border.draw(canvas);
		
		for(Sprite s : tickArray)
		{
			s.draw(canvas);
		}
		
	}
	
	private int checkStatus()
	{
		int result = 0; //-1 if lose health, 0 if health is the same, 1 if gained health
		if(player.getHealth() > currentHealth)
		{
			result = 1;
		}
		else if(player.getHealth() < currentHealth)
		{
			result = -1;
		}
		return result;
	}
	
	private int brightTickCount()
	{
		double exactTicks = player.getHealth() / 2;
		int brightTickCount = (int) Math.ceil(exactTicks);
		return brightTickCount;
	}
	
	private int brightTickCount(int health)
	{
		double exactTicks = health / 2;
		int brightTickCount = (int) Math.ceil(exactTicks);
		return brightTickCount;
	}
	
	private void addTick(int ticks)
	{
		Sprite addedTick;
		for(int i = 0; i < ticks; i++)
		{
			addedTick = new Sprite(ResourceManager.getBitmap(R.drawable.health_tick1), tickInitPositionX + (tickCount * 4), tickInitPositionY, 0);
			tickArray.add(addedTick);
			tickCount++;
			if(tickCount > 50)
			{
				Log.d(TAG, "tickCount has incremented above 50.");
			}
		}
	}
	
	
	private void removeTick(int ticks)
	{
		Sprite removedTick;
		for(int i = 0; i < ticks; i++)
		{
			removedTick = tickArray.get(tickCount);
			removedTick.clear();
			tickArray.remove(tickCount);
			tickCount--;
			if(tickCount < 0)
			{
				Log.d(TAG, "tickCount has decremented below 0.");
			}
		}
	}
	
	private void darkenTick(int ticks)
	{
		Sprite darkTick;
		int currentTick;
		for(int i = 0; i < ticks; i++)
		{
			currentTick = tickCount - i;
			tickArray.get(currentTick).clear();
			darkTick = new Sprite(ResourceManager.getBitmap(R.drawable.health_tick2), tickInitPositionX + (currentTick * 4), tickInitPositionY, 0);
			tickArray.set(currentTick, darkTick);
		}
	}
	
}
