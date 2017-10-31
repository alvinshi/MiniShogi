package minishogi.core;

/**
 * Represents a King in MiniShogi
 * @author alvinshi
 *
 */
final class KingPiece extends Piece{
	private static final char DEFAULT_SYMBOL = 'K';

	
	protected KingPiece(Player owner) {
		super(DEFAULT_SYMBOL, owner);
	}
	
	@Override
	boolean promote(int endRow) {
		return false;
	}

	@Override
	boolean demote() {
		return false;
	}

	@Override
	boolean isWithinMoveRange(int startRow, int startCol, int endRow, int endCol, Board board) {
		return MoveCheckUtils.kingPieceMoveCheck(startRow, startCol, endRow, endCol);
	}
}
