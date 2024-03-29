package minishogi.piece;

import minishogi.core.Board;
import minishogi.core.Player;

/**
 * Represents a Gold General in MiniShogi
 * @author alvinshi
 *
 */
public final class GoldGeneralPiece extends AbstractPiece{
	private static final char DEFAULT_SYMBOL = 'G';

	/**
	 * Gold General Piece Constructor
	 * @param owner : the owner of the new piece
	 */
	public GoldGeneralPiece(Player owner) {
		super(DEFAULT_SYMBOL, owner);
	}
	
	@Override
	public void promote() {		
	}

	@Override
	public boolean promote(int startRow, int endRow, Board board) {
		return false;
	}

	@Override
	protected void demote() {
	}

	@Override
	public boolean isLegalDrop(int row, int col, Board board) {
		return true;
	}

	@Override
	protected boolean isWithinMoveRange(int startRow, int startCol, int endRow, int endCol, Board board) {
		return PieceMoveUtil.goldGeneralPieceMoveCheck(startRow, startCol, endRow, endCol, facing);
	}
}
