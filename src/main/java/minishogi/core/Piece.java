package minishogi.core;

/**
 * Represents a piece in MiniShogi
 * @author alvinshi
 *
 */
interface Piece {
	void capture(Player p);
	
	boolean promote(int endRow, Board board);
	
	boolean isWithinMoveRange(int startRow, int startCol, int endRow, int endCol, Board board);
}
