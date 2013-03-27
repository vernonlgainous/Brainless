package edu.ufl.brainless;

import android.content.res.Resources;
import android.graphics.Canvas;

public class Level {
	private Player player; // Player
	
	private static final String TAG = Level.class.getSimpleName();

	public Level() {
		player = new Player(ResourceManager.getBitmap(R.drawable.player), 400f, 100f, 0f, 3f, 100, false, new Weapon("Pistol", 8, 3, 1000, 25));
	}
	
	public void update(HUD hud) {
		player.update(hud);
	}
	
	public void draw(Canvas canvas) {
		player.draw(canvas);
	}
}
