package minishogi.piece;

import minishogi.core.Board;
import minishogi.core.Player;

/**
 * Represents a Bishop in MiniShogi
 * @author alvinshi
 *
 */
public final class BishopPiece extends AbstractPiece{
	private static final char DEFAULT_SYMBOL = 'B';

	/**
	 * Bishop Piece Constructor
	 * @param owner : the owner of the piece
	 */
	public BishopPiece(Player owner) {
		super(DEFAULT_SYMBOL, owner);
	}
	
	@Override
	public void promote() {
		promoted = true;
	}

	@Override
	public boolean promote(int startRow, int endRow, Board board ) {
		if (!canPromote(startRow, endRow, board)) {
			return false;
		}
		promote();
		return true;
	}

	@Override
	protected void demote() {
		promoted = false;
	}

	@Override
	public boolean isLegalDrop(int row, int col, Board board) {
		return true;
	}

	@Override
	protected boolean isWithinMoveRange(int startRow, int startCol, int endRow, int endCol, Board board) {
		if (promoted) {			
			return (PieceMoveUtil.bishopPieceMoveCheck(startRow, startCol, endRow, endCol, board) ||
					PieceMoveUtil.kingPieceMoveCheck(startRow, startCol, endRow, endCol));
		}
		else {
			return PieceMoveUtil.bishopPieceMoveCheck(startRow, startCol, endRow, endCol, board);
		}
	}
}
