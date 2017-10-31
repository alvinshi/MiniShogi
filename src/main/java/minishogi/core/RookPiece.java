package minishogi.core;

/**
 * Represents a Rook in MiniShogi
 * @author alvinshi
 *
 */
final class RookPiece extends Piece{
	private static final char DEFAULT_SYMBOL = 'R';
	private boolean promoted;

	protected RookPiece(Player owner) {
		super(DEFAULT_SYMBOL, owner);
		promoted = false;
	}

	@Override
	boolean promote(int endRow) {
		if (!MoveCheckUtils.canPromote(endRow, facing)) {
			return false;
		}
		promoted = true;
		return true;
	}

	@Override
	boolean demote() {
		promoted = false;
		return true;
	}

	@Override
	boolean isWithinMoveRange(int startRow, int startCol, int endRow, int endCol, Board board) {
		if (promoted) {
			return (MoveCheckUtils.kingPieceMoveCheck(startRow, startCol, endRow, endCol) ||
					MoveCheckUtils.rookPieceMoveCheck(startRow, startCol, endRow, endCol, board));
		}
		else {
			return MoveCheckUtils.rookPieceMoveCheck(startRow, startCol, endRow, endCol, board);
		}
	}
}
