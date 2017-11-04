package minishogi.piece;

import minishogi.core.Board;
import minishogi.core.Player;

/**
 * Represents a Rook in MiniShogi
 * @author alvinshi
 *
 */
public final class RookPiece extends AbstractPiece{
	private static final char DEFAULT_SYMBOL = 'R';
	private boolean promoted;

	/**
	 * Rook Piece Constructor
	 * @param owner : the owner of the new piece
	 */
	public RookPiece(Player owner) {
		super(DEFAULT_SYMBOL, owner);
		promoted = false;
	}

	@Override
	public boolean promote(int endRow, Board board) {
		if (!MoveCheckUtils.canPromote(endRow, facing, board)) {
			return false;
		}
		promoted = true;
		return true;
	}

	@Override
	protected void demote() {
		promoted = false;
	}

	@Override
	public boolean isWithinMoveRange(int startRow, int startCol, int endRow, int endCol, Board board) {
		if (promoted) {
			return (MoveCheckUtils.kingPieceMoveCheck(startRow, startCol, endRow, endCol) ||
					MoveCheckUtils.rookPieceMoveCheck(startRow, startCol, endRow, endCol, board));
		}
		else {
			return MoveCheckUtils.rookPieceMoveCheck(startRow, startCol, endRow, endCol, board);
		}
	}
}
