package edu.ufl.brainless;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

@SuppressLint("WrongCall")
public class GameThread extends Thread {

	private static final String TAG = GameThread.class.getSimpleName();

	private SurfaceHolder surfaceHolder;
	private GamePanel gamePanel;
	private Level level;
	private HUD hud;
	private boolean running;
	public void setRunning(boolean running) {
		this.running = running;
	}	

	public GameThread(SurfaceHolder surfaceHolder, GamePanel gamePanel) {
		super();
		this.surfaceHolder = surfaceHolder;
		this.gamePanel = gamePanel;
		level = new Level();
		hud = new HUD();
	}

	@Override
	public void run() {
		long tickCount = 0L;
		Log.d(TAG, "Starting game loop");
		//SoundManager.playMedia("theme");
		while (running) {			
			Canvas c = null;
			try {
				c = surfaceHolder.lockCanvas();
				tickCount++;
				hud.update();
				//if changed to level.update(hud.getPlayerDirection()); the
				level.update(hud);
				draw(c);
			}
			catch(IllegalArgumentException e) {
				//Log.d(TAG, e.toString());
			}
			finally {
				surfaceHolder.unlockCanvasAndPost(c);
			}
		}
		Log.d(TAG, "Game loop executed " + tickCount + " times");
		SoundManager.pauseMedia();
    	SoundManager.resetMedia();
	}

	public void addEventToHud(MotionEvent event) {
		hud.passEvent(event);
	}

	public void removeEventFromHud() {
		hud.passEvent(null);
	}

	public void draw(Canvas canvas) {		
		gamePanel.onDraw(canvas);
		level.draw(canvas);
		hud.draw(canvas);
		hud.drawText(canvas, level.getPlayer());
	}
}