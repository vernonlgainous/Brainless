package edu.ufl.brainless;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

/**
 * The GameActivity class is the main activity for Brainless. It gets and loads
 */
public class GameActivity extends Activity {

	private static final String TAG = GameActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // requesting to turn the title OFF
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // make game full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ResourceManager.init(this);
        // set GamePanel as the View
        setContentView(new GamePanel(this));
        Log.d(TAG, "View added");
        
        // Create and load the SoundManager
        SoundManager.getInstance();
        SoundManager.initSounds(this.getApplicationContext());
        SoundManager.loadSounds();
        SoundManager.loadMedia();        
    }
    
    @Override
    protected void onDestroy() {
    	Log.d(TAG, "Destroying...");
    	SoundManager.pauseMedia();
    	SoundManager.resetMedia();
    	SoundManager.cleanup();
    	super.onDestroy();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "Stopping...");
        SoundManager.pauseMedia();
    	SoundManager.resetMedia();
    	SoundManager.cleanup();
        super.onStop();
    }
}