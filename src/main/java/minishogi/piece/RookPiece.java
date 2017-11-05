package minishogi.piece;

import java.util.Set;

import minishogi.core.Board;
import minishogi.core.Player;

/**
 * Represents a Rook in MiniShogi
 * @author alvinshi
 *
 */
public final class RookPiece extends AbstractPiece{
	private static final char DEFAULT_SYMBOL = 'R';
	private boolean promoted;

	/**
	 * Rook Piece Constructor
	 * @param owner : the owner of the new piece
	 */
	public RookPiece(Player owner) {
		super(DEFAULT_SYMBOL, owner, Move.getRookMoves());
		promoted = false;
	}

	@Override
	public boolean promote(int endRow, Board board) {
		if (!canPromote(endRow, board)) {
			return false;
		}
		Set<Move> moves = Move.getRookMoves();
		moves.addAll(Move.getKingMoves());
		setMoves(moves);
		promoted = true;
		return true;
	}

	@Override
	protected void demote() {
		setMoves(Move.getRookMoves());
		promoted = false;
	}

	@Override
	public boolean isLegalDrop(int row, int col, Board board) {
		return true;
	}
}
