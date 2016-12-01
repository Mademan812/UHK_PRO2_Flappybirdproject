package cz.uhk.pro2.flappy_bird.game;
/**
 * interface for object that need to be aware of how much time (tick) is off
 * since start of the game
 * @author volejon1
 *
 */
public interface TickAware {
	/**
	 * changes state of gaming entity concerning change of game time
	 * @param ticksSinceStart
	 */
	void tick(long ticksSinceStart);
}
