package cz.uhk.pro2.flappy_bird.game.tiles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

import cz.uhk.pro2.flappy_bird.game.Tile;

public class WallTile implements Tile {
	Image image;
	
	public WallTile (Image image){
		this.image = image;
	}
	
	@Override
	public void draw(Graphics g, int x, int y) {
		/*g.setColor(Color.MAGENTA);
		g.fillRect(x, y, Tile.SIZE, Tile.SIZE);*/
		g.drawImage(image, x, y, new ImageObserver());
	}

}
