package edu.ufl.brainless;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;

import java.lang.Override;

public class SplashScreen extends Activity{
	private Thread mSplashThread;
	private static final String TAG = SplashScreen.class.getSimpleName();
	SplashScreen sPlashScreen;
	boolean mute = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		//Remove title bar
				this.requestWindowFeature(Window.FEATURE_NO_TITLE);

				//Remove notification bar
				this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.splash);
		sPlashScreen = this;
		
		
		
		
		ImageView startGame = (ImageView) findViewById(R.id.start_game);
		startGame.setClickable(true);
		startGame.setOnClickListener(new OnClickListener() {
	        @Override
	        public void onClick(View v) {
	        	
	        	Intent intent = new Intent();
				intent.setClass(sPlashScreen, GameActivity.class);
				intent.putExtra("mute", mute);
				Log.d(TAG, "Starting game.");
				startActivity(intent);
				finish();
	        }
		});
		
		ImageView highScores = (ImageView) findViewById(R.id.high_scores);
		highScores.setClickable(true);
		highScores.setOnClickListener(new OnClickListener() {
	        @Override
	        public void onClick(View v) {
	        	
	        	Intent intent = new Intent();
				intent.setClass(sPlashScreen, GameActivity.class);
				intent.putExtra("mute", mute);
				Log.d(TAG, "Starting game.");
				startActivity(intent);
				finish();
	        }
		});
		
		ImageView exitButton = (ImageView) findViewById(R.id.exit_game);
		exitButton.setClickable(true);
		exitButton.setOnClickListener(new OnClickListener() {
	        @Override
	        public void onClick(View v) {
	        	
	        	finish();
	        }
		});
		
		CheckBox muteCheckBox = (CheckBox)findViewById(R.id.mute_checkbox);

		muteCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
	    	   @Override
	    	   public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
	    		   if(isChecked)
	    		   {
	    			   mute = true;
	    			   Log.d(TAG, "Muted.");
	    		   }
	    		   else
	    			   mute = false;
	    	   }
	    });
	}
	 @Override
    public boolean onTouchEvent(MotionEvent evt)
    {
	 	return super.onTouchEvent(evt);
    }

}
