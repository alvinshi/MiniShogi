package minishogi.piece;

import minishogi.core.Board;
import minishogi.core.Player;

/**
 * Represents a King in MiniShogi
 * @author alvinshi
 *
 */
public final class KingPiece extends AbstractPiece{
	private static final char DEFAULT_SYMBOL = 'K';

	/**
	 * King Piece Constructor
	 * @param owner : the owner of the piece
	 */
	public KingPiece(Player owner) {
		super(DEFAULT_SYMBOL, owner);
	}
	
	@Override
	public boolean promote(int endRow, Board board) {
		return false;
	}

	@Override
	protected void demote() {
	}

	@Override
	public boolean isWithinMoveRange(int startRow, int startCol, int endRow, int endCol, Board board) {
		return MoveCheckUtils.kingPieceMoveCheck(startRow, startCol, endRow, endCol);
	}

	@Override
	public boolean isLegalDrop(int row, int col, Board board) {
		return true;
	}
}
