package cz.uhk.pro2.flappy_bird.game;

import java.awt.Graphics;

/**
 * Represents game object placed into World matrix
 * 
 * @author volejon1
 *
 */
public interface Tile {
	/**
	 * Height and width of a tile
	 */
	static final int SIZE = 20;
	/**
	 * Draws game object on canvas g
	 * 
	 * @param x is x-axis of the World
	 * @param y is y-axis of the World
	 * @param g
	 * 
	 */
	void draw(Graphics g, int x, int y);
}
