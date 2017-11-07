package minishogi.piece;

import java.util.Set;

import minishogi.core.Board;
import minishogi.core.Player;
import minishogi.utils.PieceMove;

/**
 * Represents a Rook in MiniShogi
 * @author alvinshi
 *
 */
public final class RookPiece extends AbstractPiece{
	private static final char DEFAULT_SYMBOL = 'R';

	/**
	 * Rook Piece Constructor
	 * @param owner : the owner of the new piece
	 */
	public RookPiece(Player owner) {
		super(DEFAULT_SYMBOL, owner, PieceMove.getRookMoves());
	}
	
	@Override
	public void promote() {
		Set<PieceMove> moves = PieceMove.getRookMoves();
		moves.addAll(PieceMove.getKingMoves());
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
		setMoves(PieceMove.getRookMoves());
		promoted = false;
	}

	@Override
	public boolean isLegalDrop(int row, int col, Board board) {
		return true;
	}
}
