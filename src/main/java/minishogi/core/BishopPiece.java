package minishogi.core;

/**
 * Represents a Bishop in MiniShogi
 * @author alvinshi
 *
 */
final class BishopPiece extends Piece{
	private static final char DEFAULT_SYMBOL = 'B';
	private boolean promoted;

	protected BishopPiece(Player owner) {
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
					MoveCheckUtils.bishopPieceMoveCheck(startRow, startCol, endRow, endCol, board));
		}
		else {
			return MoveCheckUtils.bishopPieceMoveCheck(startRow, startCol, endRow, endCol, board);
		}
	}

}
