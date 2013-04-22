package edu.ufl.brainless;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import java.lang.Override;

public class SplashScreen extends Activity{
	private Thread mSplashThread;
	private static final String TAG = SplashScreen.class.getSimpleName();
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		final SplashScreen sPlashScreen = this;
		mSplashThread = new Thread(){
			@Override
			public void run(){
				boolean loop = true;
				while(loop) {
					try{
						synchronized(this){
							wait(5000);
						}
					}
					catch (InterruptedException ex){
						Log.d(TAG, ex.toString());
					}	
					finish();
					Intent intent = new Intent();
					intent.setClass(sPlashScreen, GameActivity.class);
					Log.d(TAG, "Starting game.");
					startActivity(intent);
					loop = false;
				}
			}
		};
		mSplashThread.start();
	}
	 @Override
	    public boolean onTouchEvent(MotionEvent evt)
	    {
	        if(evt.getAction() == MotionEvent.ACTION_DOWN)
	        {
	            synchronized(mSplashThread){
	                mSplashThread.notifyAll();
	            }
	        }
	        return true;
	    } 
	
	

}
