package edu.ufl.brainless;

import android.util.Log;

public class Rectangle {
	public float X;
	public float Y;
	public float width;
	public float height;
	public float W;
	public float Z;
	private static final String TAG = Rectangle.class.getSimpleName();

	public Rectangle() {
		X = 0;
		Y = 0;
		width = 0;
		height = 0;
	}

	public Rectangle(float X, float Y, float width, float height) {
		this.X = X;
		this.Y = Y;
		this.width = width;
		this.height = height;
		this.W=X+width;
		this.Z=Y+height;
	}


	
	public static boolean Intersects(Rectangle a, Rectangle b) {
		Log.d(TAG,"a: "+a.X+","+a.Y);
		Log.d(TAG,"b: "+b.X+","+b.Y);
		if(a.X <= b.X && a.X + a.width >= b.X || b.X <= a.X && b.X + b.width >= a.X) {
			if(a.Y <= b.Y && a.Y + a.height >= b.Y || b.Y <= a.Y && b.Y + b.height >= a.Y)
				return true;
		}
		return false;
	}
	

	/*
	public boolean Intersects(Rectangle a, Rectangle b) {
		if (a.X + a.width > b.X && b.X + b.width > a.X)
			if (a.Y + a.height > b.Y && b.Y + b.height > a.Y)
				return true;
			else return false;
		else
			return false;
	}
	*/
}