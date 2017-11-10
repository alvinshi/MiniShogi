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
	 * @param player : the name of the player
	 */
	void nextTurn(String player);
	
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
	 * @param player : the player who dropped
	 * @param piece : the symbol of the piece to drop
	 * @param address : to address
	 * @param board : a snapshot of the board
	 */
	void dropMade(String player, String piece, String address, String[][] board);
	
	/**
	 * A invalid move/drop detected
	 * @param winner : name of the winner
	 */
	void invalidMove(String winner);
	

	/**
	 * A check happened
	 * @param sadPerson : the player who is in check
	 * @param strategies : possible strategies
	 */
	void check(String sadPerson, List<String> strategies);
	
	/**
	 * A checkMate happened
	 * @param winner : the winner
	 */
	void checkMate(String winner);
	
	/**
	 * List the captured pieces of each player
	 * @param upper : list of pieces captured by the upper player
	 * @param lower : list of pieces captured by the lower player
	 */
	void capturedPieces(List<String> upper, List<String> lower);
	
	/**
	 * The game has tied
	 */
	void tie();

}
