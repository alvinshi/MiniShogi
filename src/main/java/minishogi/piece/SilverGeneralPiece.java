package minishogi.piece;

import java.util.Set;

import minishogi.core.Board;
import minishogi.core.Player;

/**
 * Represents a Silver General in MiniShogi
 * @author alvinshi
 *
 */
public final class SilverGeneralPiece extends AbstractPiece{
	private static final char DEFAULT_SYMBOL = 'S';
	private boolean promoted;

	/**
	 * Silver General Piece Constructor 
	 * @param owner : the owner of the piece
	 */
	public SilverGeneralPiece(Player owner) {
		super(DEFAULT_SYMBOL, owner, Move.getSilverGeneralMoves(owner.getFacing()));
		promoted = false;
	}

	@Override
	public boolean promote(int endRow, Board board) {
		if (!canPromote(endRow, board)) {
			return false;
		}
		Set<Move> moves = Move.getSilverGeneralMoves(facing);
		moves.addAll(Move.getGoldGeneralMoves(facing));
		setMoves(moves);
		promoted = true;
		return true;
	}

	@Override
	protected void demote() {
		setMoves(Move.getSilverGeneralMoves(facing));
		promoted = false;
	}

	@Override
	public boolean isLegalDrop(int row, int col, Board board) {
		return true;
	}

}
