package minishogi.core;

/**
 * Represents a Silver General in MiniShogi
 * @author alvinshi
 *
 */
final class SilverGeneralPiece extends AbstractPiece{
	private static final char DEFAULT_SYMBOL = 'S';
	private boolean promoted;

	SilverGeneralPiece(Player owner) {
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
		if (!promoted) {
			return MoveCheckUtils.silverGeneralPieceMoveCheck(startRow, startCol, endRow, endCol, facing);
		}
		else {
			return MoveCheckUtils.goldGeneralPieceMoveCheck(startRow, startCol, endRow, endCol, facing);
		}
	}

}
