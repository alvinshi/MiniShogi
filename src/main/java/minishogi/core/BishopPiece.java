package minishogi.core;

/**
 * Represents a Bishop in MiniShogi
 * @author alvinshi
 *
 */
final class BishopPiece extends AbstractPiece{
	private static final char DEFAULT_SYMBOL = 'B';
	private boolean promoted;

	BishopPiece(Player owner) {
		super(DEFAULT_SYMBOL, owner);
		promoted = false;
	}

	@Override
	public boolean promote(int endRow, Board board ) {
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
					MoveCheckUtils.bishopPieceMoveCheck(startRow, startCol, endRow, endCol, board));
		}
		else {
			return MoveCheckUtils.bishopPieceMoveCheck(startRow, startCol, endRow, endCol, board);
		}
	}

}
