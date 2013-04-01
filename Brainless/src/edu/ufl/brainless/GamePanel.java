package edu.ufl.brainless;

import android.util.Log;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

	private static final String TAG = GameThread.class.getSimpleName();

	private GameThread thread;

	public GamePanel(Context context) {
		super(context);

		// adding callback(this) to the surface holder to intercept events
		getHolder().addCallback(this);

		thread = new GameThread(getHolder(), this);

		// make the GamePanel focusable so it can handle events
		setFocusable(true);
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

	}

	public void surfaceCreated(SurfaceHolder holder) {
		thread.setRunning(true);
		thread.start();		
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// try again shutting down the thread
			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		thread.addEventToHud(event);
		int action = event.getAction() & MotionEvent.ACTION_MASK;

		switch (action) {
        case MotionEvent.ACTION_DOWN:
        case MotionEvent.ACTION_POINTER_DOWN:  
        	//Log.d(TAG, "ACTION_DOWN");
        	return true;
        case MotionEvent.ACTION_UP:        
        case MotionEvent.ACTION_POINTER_UP:
        	//Log.d(TAG, "ACTION_UP");
        	return true;
        case MotionEvent.ACTION_CANCEL:
            Log.d(TAG, "ACTION_CANCEL");
            return true;
        case MotionEvent.ACTION_MOVE:
        	return true;
        }

		return super.onTouchEvent(event);
	}

	@Override
	public void onDraw(Canvas canvas) {
		//player = new Player(ResourceManager.getBitmap(R.drawable.player), 400f, 100f, 0f, 5f, 100, false, new Weapon("Pistol", 8, 3, 1000, 25));
		//canvas.drawBitmap(ResourceManager.getBitmap(R.drawable.background2), 0, 0, null);
		//canvas.drawPicture(picture)
		canvas.drawColor(Color.BLACK);
	}
}