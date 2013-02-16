package edu.ufl.brainless;

/**
 * The Grid class is a two-dimensional representation of a level and
 * is responsible for drawing tiles and performing collision detection
 * between actors and the edges of a level.
 *
 */
public class Grid {
	
	private static final String TAG = Grid.class.getSimpleName();
	
	Sprite[][] grid;
	
	public Grid(Sprite[][] grid) {
		this.grid = grid;
	}
}
