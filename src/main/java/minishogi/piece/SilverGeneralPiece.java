package minishogi.piece;

import java.util.Set;

import minishogi.core.Board;
import minishogi.core.Player;
import minishogi.utils.PieceMove;

/**
 * Represents a Silver General in MiniShogi
 * @author alvinshi
 *
 */
public final class SilverGeneralPiece extends AbstractPiece{
	private static final char DEFAULT_SYMBOL = 'S';

	/**
	 * Silver General Piece Constructor 
	 * @param owner : the owner of the piece
	 */
	public SilverGeneralPiece(Player owner) {
		super(DEFAULT_SYMBOL, owner, PieceMove.getSilverGeneralMoves(owner.getFacing()));
	}
	
	@Override
	public void promote() {
		Set<PieceMove> moves = PieceMove.getSilverGeneralMoves(facing);
		moves.addAll(PieceMove.getGoldGeneralMoves(facing));
		setMoves(moves);
		promoted = true;
	}

	@Override
	public boolean promote(int endRow, Board board) {
		if (!canPromote(endRow, board)) {
			return false;
		}
		promote();
		return true;
	}

	@Override
	protected void demote() {
		setMoves(PieceMove.getSilverGeneralMoves(facing));
		promoted = false;
	}

	@Override
	public boolean isLegalDrop(int row, int col, Board board) {
		return true;
	}
}
