package cz.uhk.pro2.flappy_bird.game;

import java.awt.Color;
import java.awt.Graphics;

public class Bird implements TickAware {
	
	//physics
	static final double koefUp = -5.0;
	static final double koefDown = 2.0;
	static final int tickFlyingUp = 4;
	
	//birdie midpoint
	int viewportX;
	double viewportY;
	
	//falling speed/raising speed
	double velocity = koefDown;
	
	//ticks between starting to fall after raising
	int ticksToFall = 0;
	
	public Bird(int initX, int initY){
		this.viewportX = initX;
		this.viewportY = initY;
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
	
	@Override
	public void tick(long ticksSinceStart) {
		if(ticksToFall > 0){ //birdie flying upward -> waiting
			ticksToFall--;
		}else{ //birdie falls
			velocity = koefDown;
			ticksToFall = tickFlyingUp;
		}
		viewportY += velocity;
	}
	
}
