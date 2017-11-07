package minishogi.piece;

import java.util.Set;

import minishogi.core.Board;
import minishogi.core.Player;
import minishogi.utils.PieceMove;

/**
 * Represents a Bishop in MiniShogi
 * @author alvinshi
 *
 */
public final class BishopPiece extends AbstractPiece{
	private static final char DEFAULT_SYMBOL = 'B';

	/**
	 * Bishop Piece Constructor
	 * @param owner : the owner of the piece
	 */
	public BishopPiece(Player owner) {
		super(DEFAULT_SYMBOL, owner, PieceMove.getBishopMoves());
	}
	
	@Override
	public void promote() {
		Set<PieceMove> moves = PieceMove.getBishopMoves();
		moves.addAll(PieceMove.getKingMoves());
		setMoves(moves);
		promoted = true;
	}

	@Override
	public boolean promote(int endRow, Board board ) {
		if (!canPromote(endRow, board)) {
			return false;
		}
		promote();
		return true;
	}

	@Override
	protected void demote() {
		setMoves(PieceMove.getBishopMoves());
		promoted = false;
	}

	@Override
	public boolean isLegalDrop(int row, int col, Board board) {
		return true;
	}
}
