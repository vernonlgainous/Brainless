package edu.ufl.brainless;

public class Rectangle {
	public float X;
	public float Y;
	public float width;
	public float height;
	
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
	}
	
	public static boolean Intersects(Rectangle a, Rectangle b) {
		if (a.X + a.width > b.X && b.X + b.width > a.X)
			if (a.Y + a.height > b.Y && b.Y + b.height > a.Y)
				return true;
			else return false;
		else
			return false;
	}
}
