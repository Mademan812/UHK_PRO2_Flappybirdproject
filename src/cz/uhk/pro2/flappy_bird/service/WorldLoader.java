package cz.uhk.pro2.flappy_bird.service;

import java.io.InputStream;

import cz.uhk.pro2.flappy_bird.game.World;

/**
 * common interface for loading level appearance
 * 
 * @author volejon1
 *
 */
public interface WorldLoader {
	/**
	 * loads level
	 * 
	 * @return
	 */
	World loadLevel();
}
