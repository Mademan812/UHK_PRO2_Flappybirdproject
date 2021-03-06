package cz.uhk.pro2.flappy_bird.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Ellipse2D;

public class Bird implements TickAware {
	
	//physics
	static final double koefUp = -5.0;
	static final double koefDown = 2.0;
	static final int tickFlyingUp = 4;
	
	//birdie
	private Ellipse2D.Float birdBoundary;
	
	//birdie midpoint
	int viewportX;
	double viewportY;
	
	//falling speed/raising speed
	double velocity = koefDown;
	
	//ticks between starting to fall after raising
	int ticksToFall = 0;
	
	Image imageOfTheBird;
	
	public Bird(int initialX, int initialY, Image imageOfTheBird) {
		this.viewportX = initialX;
		this.viewportY = initialY;
		this.imageOfTheBird = imageOfTheBird;
	}
	
	public void changeVelocity(){
		velocity = koefUp;
		ticksToFall = tickFlyingUp;
	}
	
	public void draw(Graphics g){
		g.setColor(Color.CYAN);
		g.fillOval(viewportX - Tile.SIZE/2, (int)viewportY - Tile.SIZE/2, Tile.SIZE, Tile.SIZE);
		
		//debug
		g.setColor(Color.BLACK);
		g.drawString(viewportX+", "+viewportY, viewportX, (int)viewportY);
	}
	
	public boolean collidesWithRectangle(int x, int y, int w, int h){
		// test birdie intersecting
		return birdBoundary.intersects(x, y, w, h);		
	}	
	
	public Image getImageOfTheBird() {
		return imageOfTheBird;
	}
	
	@Override
	public void tick(long ticksSinceStart) {
		if(ticksToFall > 0){ //birdie flying upward -> waiting
			ticksToFall--;
		}else{ //birdie falls
			velocity = koefDown;
			ticksToFall = tickFlyingUp;
		}
		viewportY += velocity;
		birdBoundary = new Ellipse2D.Float((int)viewportX - Tile.SIZE/2, (int)viewportY - Tile.SIZE/2, Tile.SIZE, Tile.SIZE);
	}
	
}
