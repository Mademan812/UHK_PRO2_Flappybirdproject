package cz.uhk.pro2.flappy_bird.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import cz.uhk.pro2.flappy_bird.game.tiles.BonusTile;
import cz.uhk.pro2.flappy_bird.game.tiles.WallTile;

public class World implements TickAware {
	Tile[][] tiles;
	int shift = 30;
	int viewportWidth = 200; //TODO
	int j2;
	int points;
	Bird bird;
	boolean gameOver;
	
	public void setViewportWidth(int viewportWidth/*, Image imageOfTheBird*/) {
		this.viewportWidth = viewportWidth;
	}
	
	public World(Tile[][] tiles, Image imageOfTheBird){
		this.tiles = tiles;
		bird = new Bird(viewportWidth/2, tiles.length*Tile.SIZE/2, imageOfTheBird);
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
		if(!gameOver){
		//with every tick the game goes by on pixel
		//(number of tick and went off pixels is equal)
		shift = (int)tickSinceStart;
		//birdie knows about ticks
		bird.tick(tickSinceStart);
		}
	}
	
	public int getHeightPix(){
		return tiles.length*Tile.SIZE;
	}
	
	public void changeBirdiesVelocity(){
		bird.changeVelocity();
	}
	
	public void drawAndDetectCollisions(Graphics g){
		int minJ = shift/Tile.SIZE; //j-souradnice prvni dlazdice vlevo kterou je nutne kreslit
		//pocet dlazdic (na sirku), kolik je nutne kreslit (do viewportu)
		// + 2 protoze muze chybet cast bunky vlevo a vpravo kvuli obema celociselnym delenim
		int countJ = viewportWidth/Tile.SIZE + 2;
		for(int i = 0; i < tiles.length; i++){
			for(int j = minJ; j <= minJ+countJ; j++){
				//chceme aby level bezel porad dokola, takze modJ se 
				//na konci pole vraci zase na 0; tile[0].length je pocet sloupcu
				int modJ = j % tiles[0].length; 
				Tile t = tiles[i][modJ];
				if(t != null){
					//v bunce je nejaka dlazdice
					//vykreslime ji
					int viewportX = j * Tile.SIZE - shift;
					int viewportY = i * Tile.SIZE;
					if(j == minJ + countJ){
						if(t instanceof BonusTile){
							((BonusTile) t).setActive(true);//TODO bonus tile
						}
					}
					t.draw(g, viewportX, viewportY);
					// otestujeme moznou kolizi dlazdice s ptakem
					if (t instanceof WallTile){
						// t je zed
						// otestujeme, jestli dlazdice t koliduje s ptakem
						if (bird.collidesWithRectangle(viewportX, viewportY, Tile.SIZE, Tile.SIZE)){
							gameOver = true; // doslo ke kolizi, hra ma skoncit
						}
					} else if(t instanceof BonusTile){
						if (bird.collidesWithRectangle(viewportX, viewportY, Tile.SIZE, Tile.SIZE)){
							if(((BonusTile) t).isActive()) points++;
							((BonusTile) t).setActive(false);
						}
					}
				}
			}
		}
		g.setColor(new Color(0xff0000));
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		g.drawString("" + points, 5, 20);
		bird.draw(g);
	}
	
	public void reset(){
		gameOver = false;
		bird = new Bird(30, 100, bird.getImageOfTheBird());
		for(int i = 0; i < tiles.length; i++){
			for(int k = 0; k < tiles[0].length; k++){
				if(tiles[i][k] instanceof BonusTile){
					((BonusTile)tiles[i][k]).setActive(true);
				}
			}
		}
		points = 0;
	}
	
	public boolean isGameOver(){
		return gameOver;
	}
}
