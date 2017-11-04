package minishogi.core;

/**
 * Represents a piece in MiniShogi
 * @author alvinshi
 *
 */
public interface Piece {
	/**
	 * capture the current piece by player p
	 * The method will update its own symbol and demote itself if
	 * it was promoted previously
	 * @param p : reference to the player who captures the piece
	 */
	void capture(Player p);
	
	/**
	 * promote the piece based on the Board and the row
	 * the piece landed
	 * @param endRow : the row the piece moves to
	 * @param board : provide information about where the promotion row is
	 * @return true if the promotion is valid, false otherwise
	 */
	boolean promote(int endRow, Board board);
	
	/**
	 * check is the move is within the range of the piece
	 * @param startRow : start row index (1~5)
	 * @param startCol : start column index (a~e)
	 * @param endRow : end row index (1~5)
	 * @param endCol : end column index (a~e)
	 * @param board : board is needed to check if the move is being blocked
	 * @return : true if the move is with the range, false otherwise
	 */
	boolean isWithinMoveRange(int startRow, int startCol, int endRow, int endCol, Board board);
}
