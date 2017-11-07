package minishogi.piece;

import minishogi.core.Board;
import minishogi.core.Player;
import minishogi.utils.PieceMove;

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
		super(DEFAULT_SYMBOL, owner, PieceMove.getGoldGeneralMoves(owner.getFacing()));
	}
	
	@Override
	public void promote() {		
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
