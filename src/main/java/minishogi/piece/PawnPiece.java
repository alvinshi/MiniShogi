package minishogi.piece;

import minishogi.core.Board;
import minishogi.core.Piece;
import minishogi.core.Player;
import minishogi.utils.PieceMove;

/**
 * Represents a pawn in MiniShogi
 * @author alvinshi
 *
 */
public final class PawnPiece extends AbstractPiece{
	private static final char DEFAULT_SYMBOL = 'P';

	/**
	 * Pawn Piece Constructor 
	 * @param owner : the owner of the piece
	 */
	public PawnPiece(Player owner) {
		super(DEFAULT_SYMBOL, owner, PieceMove.getPawnMoves(owner.getFacing()));
	}

	@Override
	public boolean promote(int endRow, Board board) {
		if (!canPromote(endRow, board)) {
			return false;
		}
		setMoves(PieceMove.getGoldGeneralMoves(facing));
		return true;
	}

	@Override
	protected void demote() {
		setMoves(PieceMove.getPawnMoves(facing));
	}

	@Override
	public boolean isLegalDrop(int row, int col, Board board) {
		Player owner = getOwner();
		for (int i = 0; i < board.getBoardSize(); i++) {
			Piece p = board.getPiece(i, col);
			if (p != null && p.getOwner() == owner) {
				return false;
			}
		}
		//Cannot be dropped into the promotion zone
		if (board.getPromoteRow(owner.getFacing()) == row) return false;
		//Cannot lead to an immediate checkMate
		board.placePiece(this, row, col);
		boolean checkMate = board.isCheckMate(owner);
		board.removePiece(row, col);
		return !checkMate;
	}

}
