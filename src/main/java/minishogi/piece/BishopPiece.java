package minishogi.piece;

import java.util.Set;

import minishogi.core.Board;
import minishogi.core.Player;

/**
 * Represents a Bishop in MiniShogi
 * @author alvinshi
 *
 */
public final class BishopPiece extends AbstractPiece{
	private static final char DEFAULT_SYMBOL = 'B';
	private boolean promoted;

	/**
	 * Bishop Piece Constructor
	 * @param owner : the owner of the piece
	 */
	public BishopPiece(Player owner) {
		super(DEFAULT_SYMBOL, owner, Move.getBishopMoves());
		promoted = false;
	}

	@Override
	public boolean promote(int endRow, Board board ) {
		if (!canPromote(endRow, board)) {
			return false;
		}
		Set<Move> moves = Move.getBishopMoves();
		moves.addAll(Move.getKingMoves());
		setMoves(moves);
		promoted = true;
		return true;
	}

	@Override
	protected void demote() {
		setMoves(Move.getBishopMoves());
		promoted = false;
	}

	@Override
	public boolean isLegalDrop(int row, int col, Board board) {
		return true;
	}
}
