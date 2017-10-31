package minishogi.core;

/**
 * Represents a Gold General in MiniShogi
 * @author alvinshi
 *
 */
final class GoldGeneralPiece extends Piece{
	private static final char DEFAULT_SYMBOL = 'G';

	protected GoldGeneralPiece(Player owner) {
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
		return MoveCheckUtils.goldGeneralPieceMoveCheck(startRow, startCol, endRow, endCol, facing);
	}
}
