package minishogi.core;

/**
 * The game listener interface for the minishogi implementation
 * This is an observer pattern which facilitates the communication
 * between the core and the outside world
 * @author alvinshi
 *
 */
public interface GameListener {
	/**
	 * A new turn has started
	 */
	void nextTurn();
	
	/**
	 * A valid move has been made on the board
	 */
	void moveMade();
	
	/**
	 * A valid drop has been made on the board
	 */
	void dropMade();
	
	/**
	 * A invalid move/drop detected
	 */
	void invalidMove();
	
	/**
	 * Check happened
	 */
	void check();
}
