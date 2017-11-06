package minishogi.piece;

import minishogi.core.Board;
import minishogi.core.Player;
import minishogi.utils.PieceMove;

/**
 * Represents a King in MiniShogi
 * @author alvinshi
 *
 */
public final class KingPiece extends AbstractPiece{
	private static final char DEFAULT_SYMBOL = 'K';

	/**
	 * King Piece Constructor
	 * @param owner : the owner of the piece
	 */
	public KingPiece(Player owner) {
		super(DEFAULT_SYMBOL, owner, PieceMove.getKingMoves());
	}
	
	@Override
	public boolean promote(int endRow, Board board) {
		return false;
	}

	@Override
	protected void demote() {
	}

	@Override
	public boolean isLegalDrop(int row, int col, Board board) {
		return true;
	}
}
