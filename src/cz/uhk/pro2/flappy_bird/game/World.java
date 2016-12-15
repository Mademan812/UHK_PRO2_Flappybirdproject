package cz.uhk.pro2.flappy_bird.game;

import java.awt.Graphics;

import cz.uhk.pro2.flappy_bird.game.tiles.WallTile;

public class World implements TickAware {
	Tile[][] tiles;
	int shift = 30;
	int viewportWidth = 200; //TODO
	int j2;
	Bird bird;
	
	public void setViewportWidth(int viewportWidth/*, Image imageOfTheBird*/) {
		this.viewportWidth = viewportWidth;
	}
	
	public World(Tile[][] tiles){
		this.tiles = tiles;
		bird = new Bird(viewportWidth/2, tiles.length*Tile.SIZE/2);
	}
	
	/**
	 * Draws whole game World on canvas g
	 * 
	 * @param g
	 */
	public void draw(Graphics g){
		int minJ = shift/Tile.SIZE; //counts the index of first cell to be drawn (is visible)
		int maxJ = minJ + viewportWidth/Tile.SIZE + 2; //+2 cuz integer dividing shift and viewportWidth
		for(int i = 0; i < tiles.length; i++){
			for(int j = minJ; j < maxJ; j++){
				j2 = j % tiles[i].length; //j2 makes cycling happen
				Tile t = tiles[i][j2];
				if(t != null){
					int screenX = (j * Tile.SIZE) - shift;
					int screenY = i * Tile.SIZE;
					t.draw(g, screenX, screenY);
				}
			}
		}
		//birdie
		bird.draw(g);
	}

	@Override
	public void tick(long tickSinceStart) {
		//with every tick the game goes by on pixel
		//(number of tick and went off pixels is equal)
		shift = (int)tickSinceStart;
		//birdie knows about ticks
		bird.tick(tickSinceStart);
	}
	
	public int getHeightPix(){
		return tiles.length*Tile.SIZE;
	}
	
	public void changeBirdiesVelocity(){
		bird.changeVelocity();
	}
}
