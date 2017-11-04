package minishogi.core;

/**
 * Represents a Gold General in MiniShogi
 * @author alvinshi
 *
 */
final class GoldGeneralPiece extends AbstractPiece{
	private static final char DEFAULT_SYMBOL = 'G';

	protected GoldGeneralPiece(Player owner) {
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
		return MoveCheckUtils.goldGeneralPieceMoveCheck(startRow, startCol, endRow, endCol, facing);
	}
}
