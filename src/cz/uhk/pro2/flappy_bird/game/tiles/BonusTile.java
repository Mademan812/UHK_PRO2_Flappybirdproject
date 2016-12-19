package cz.uhk.pro2.flappy_bird.game.tiles;

import java.awt.Graphics;
import java.awt.Image;

import cz.uhk.pro2.flappy_bird.game.Tile;

public class BonusTile extends AbstractTile implements Cloneable{
	private boolean active = true;
	private Tile empty;
	
	public BonusTile(Image image, Tile empty) {
		super(image);
		this.empty = empty;
	}
	
	public boolean isActive(){
		return active;
	}
	
	public void setActive(boolean active){
		this.active = active;
	}
	
	@Override
	public void draw(Graphics g, int x, int y){
		if(active){
			super.draw(g, x, y);
		}else{
			empty.draw(g, x, y);
		}
	}
	
	@Override
	public BonusTile clone(){
		return new BonusTile(image, empty);
	}
}
