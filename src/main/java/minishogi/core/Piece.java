package minishogi.core;

import java.util.Set;

import minishogi.utils.PieceMove;

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
	 * Get the current owner of the piece
	 * @return : the reference to the owner object
	 */
	Player getOwner();
	
	/**
	 * Return the current symbol of the piece 
	 * @return : the symbol
	 */
	char getSymbol();
	
	/**
	 * promote the piece without questioning
	 */
	void promote();
	
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
	 * if the end address is occupied by a piece of the same owner,
	 * the move is invalid too.
	 * @param startRow : start row index (1~5)
	 * @param startCol : start column index (a~e)
	 * @param endRow : end row index (1~5)
	 * @param endCol : end column index (a~e)
	 * @param board : board is needed to check if the move is being blocked
	 * @return : true if the move is with the range, false otherwise
	 */
	boolean isValidMove(int startRow, int startCol, int endRow, int endCol, Board board);
	
	/**
	 * check if the drop is legal
	 * @param row : row to drop
	 * @param col : column to drop
	 * @param board : board is needed to check some special case (e.g. pawn)
	 * @return : true if the drop obeys the rule sets by the piece identity
	 */
	boolean isLegalDrop(int row, int col, Board board);

	/**
	 * Return all possible moves of the piece based on the location and the board status
	 * @param startRow : start row number
	 * @param startCol : start column number
	 * @param board : the board
	 * @return : a set of all possible moves
	 */
	Set<PieceMove> getAllValidMoves(int startRow, int startCol, Board board);
}
