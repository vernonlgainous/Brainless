package edu.ufl.brainless;

public class Vector2 {
	public float X;
	public float Y;
	
	public Vector2() {
		X = 0;
		Y = 0;
	}
	
	public Vector2(float X, float Y) {
		this.X = X;
		this.Y = Y;
	}
	
	// Static methods
	public static Vector2 Add(Vector2 a, Vector2 b) {
		return new Vector2(a.X + b.X, a.Y + b.Y);
	}
	
	public static Vector2 Subtract(Vector2 a, Vector2 b) {
		return new Vector2(a.X - b.X, a.Y - b.Y);
	}
	
	public static Vector2 Multiply(Vector2 a, Vector2 b) {
		return new Vector2(a.X * b.X, a.Y * b.Y);
	}
	
	public static Vector2 Multiply(Vector2 a, float b) {
		return new Vector2(a.X * b, a.Y * b);
	}
	
	public static Vector2 Divide(Vector2 a, Vector2 b) {
		return new Vector2(a.X / b.X, a.Y / b.Y);
	}
	
	public static Vector2 Divide(Vector2 a, float b) {
		return new Vector2(a.X / b, a.Y / b);
	}
	
	public static float Distance(Vector2 a, Vector2 b) {
		return (float)Math.sqrt(Math.pow(a.X - b.X, 2) + Math.pow(a.Y - b.Y, 2));
	}
	
	public void Normalize() {
		float norm = (float)Math.sqrt(Math.pow(X,2) + Math.pow(Y,2));
		X /= norm;
		Y /= norm;
	}
	
	public float Length() {
		return (float)Math.sqrt(Math.pow(X, 2) + Math.pow(Y, 2));
	}
	
	public String toString() {
		return "(" + X + "," + Y + ")";
	}
}
