package minishogi.core;

import java.util.List;

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
	 * @param player : the player who made the move
	 * @param fromAddr : from address
	 * @param toAddr : to address
	 * @param promote : if promote a piece
	 * @param board : a snapshot of the board
	 */
	void moveMade(String player, String fromAddr, String toAddr, boolean promote, String[][] board);
	
	/**
	 * A valid drop has been made on the board
	 */
	void dropMade();
	
	/**
	 * A invalid move/drop detected
	 */
	void invalidMove();
	

	/**
	 * A check happened
	 * @param sadPerson : the player who is in check
	 * @param strategies : possible strategies
	 */
	void check(String sadPerson, List<String> strategies);
	
	/**
	 * List the captured pieces of each player
	 * @param upper : list of pieces captured by the upper player
	 * @param lower : list of pieces captured by the lower player
	 */
	void capturedPieces(List<String> upper, List<String> lower);

}
