package minishogi.core;

/**
 * Represents a King in MiniShogi
 * @author alvinshi
 *
 */
final class KingPiece extends AbstractPiece{
	private static final char DEFAULT_SYMBOL = 'K';

	
	KingPiece(Player owner) {
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
}
