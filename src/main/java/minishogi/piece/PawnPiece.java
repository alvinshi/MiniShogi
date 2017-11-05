package minishogi.piece;

import java.util.Map;

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
		super(DEFAULT_SYMBOL, owner, Move.getPawnMoves(owner.getFacing()));
		promoted = false;
	}

	@Override
	public boolean promote(int endRow, Board board) {
		if (!canPromote(endRow, board)) {
			return false;
		}
		setMoves(Move.getGoldGeneralMoves(facing));
		promoted = true;
		return true;
	}

	@Override
	protected void demote() {
		setMoves(Move.getPawnMoves(facing));
		promoted = false;
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
		//Cannot lead to an immediate check
		Map<String, Integer> kingLocation = board.getOpponentKingLocation(owner);
		if (isWithinMoveRange(row, col, kingLocation.get("row"), kingLocation.get("col"), board)) return false;
		return true;
	}

}
