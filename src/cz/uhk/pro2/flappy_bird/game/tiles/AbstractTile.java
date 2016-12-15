package cz.uhk.pro2.flappy_bird.game.tiles;

import java.awt.Graphics;
import java.awt.Image;
import cz.uhk.pro2.flappy_bird.game.Tile;

public abstract class AbstractTile implements Tile {
	Image image;

	public AbstractTile(Image image) {
		this.image = image;
	}
	
	@Override
	public void draw(Graphics g, int x, int y) {
		//g.drawRect(x, y, Tile.SIZE, Tile.SIZE);
		g.drawImage(image, x, y, null);
	}
}