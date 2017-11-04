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
	private boolean promoted;

	/**
	 * Pawn Piece Constructor 
	 * @param owner : the owner of the piece
	 */
	public PawnPiece(Player owner) {
		super(DEFAULT_SYMBOL, owner);
		promoted = false;
	}

	@Override
	public boolean promote(int endRow, Board board) {
		if (!MoveCheckUtils.canPromote(endRow, facing, board)) {
			return false;
		}
		promoted = true;
		return true;
	}

	@Override
	protected void demote() {
		promoted = false;
	}

	@Override
	public boolean isWithinMoveRange(int startRow, int startCol, int endRow, int endCol, Board board) {
		boolean isLegalMove = false;
		if (!promoted) {
			isLegalMove = MoveCheckUtils.pawnPieceMoveCheck(startRow, startCol, endRow, endCol, facing);
		}
		else {
			isLegalMove = MoveCheckUtils.goldGeneralPieceMoveCheck(startRow, startCol, endRow, endCol, facing);
		}
		// Pawn will be promoted automatically
		if (isLegalMove && (board.getPromoteRow(facing) == endRow)) {
			promoted = true;
		}
		return isLegalMove;
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
		if (board.getPromoteRow(owner.getFacing()) == row) return false;
		return true;
	}

}
