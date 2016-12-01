package cz.uhk.pro2.flappy_bird.game.tiles;

import java.awt.Color;
import java.awt.Graphics;

import cz.uhk.pro2.flappy_bird.game.Tile;

public class WallTile implements Tile {

	@Override
	public void draw(Graphics g, int x, int y) {
		g.setColor(Color.MAGENTA);
		g.fillRect(x, y, Tile.SIZE, Tile.SIZE);
	}

}
