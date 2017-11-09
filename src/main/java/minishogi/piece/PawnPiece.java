package minishogi.piece;

import minishogi.core.Board;
import minishogi.core.Piece;
import minishogi.core.Player;

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
		super(DEFAULT_SYMBOL, owner);
	}
	
	@Override
	public void promote() {
		promoted = false;
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
		promoted = true;
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

	@Override
	protected boolean isWithinMoveRange(int startRow, int startCol, int endRow, int endCol, Board board) {
		if (promoted) {
			return PieceMoveUtil.goldGeneralPieceMoveCheck(startRow, startCol, endRow, endCol, facing);
		}
		return PieceMoveUtil.pawnPieceMoveCheck(startRow, startCol, endRow, endCol, facing);
	}
}
