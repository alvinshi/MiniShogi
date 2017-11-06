package minishogi.piece;

import java.util.HashSet;
import java.util.Set;

import minishogi.core.Board;
import minishogi.core.Piece;
import minishogi.core.Player;
import minishogi.utils.Facing;
import minishogi.utils.PieceMove;

/**
 * Abstract class representing the basic concept of a piece in MiniShogi 
 * @author alvinshi
 *
 */
public abstract class AbstractPiece implements Piece{
	private char symbol;
	private Player owner;
	private Set<PieceMove> moves;
	protected boolean promoted;
	protected Facing facing;
	
	protected AbstractPiece(char symbol, Player owner, Set<PieceMove> moves) {
		this.symbol = owner.getSymbol(symbol);
		this.owner = owner;
		this.facing = owner.getFacing();
		this.moves = moves;
		promoted = false;
	}
	
	@Override
	public final void capture(Player p) {
		owner = p;
		symbol = p.getSymbol(symbol);
		facing = owner.getFacing();
		demote();
	}
	
	@Override
	public final Player getOwner() {
		return owner;
	}
	
	@Override
	public final char getSymbol() {
		return symbol;
	}
	
	@Override
	public abstract boolean promote(int endRow, Board board);
	
	protected abstract void demote();
	
	protected void setMoves(Set<PieceMove> moves) {
		this.moves = moves;
	}
	
	protected Set<PieceMove> getMoves() {
		return moves;
	}
	
	protected boolean canPromote(int row, Board board) {
		return row == board.getPromoteRow(facing);
	}
	
	@Override
	public Set<PieceMove> getAllValidMoves(int startRow, int startCol, Board board) {
		Set<PieceMove> rtn = new HashSet<>();
		for (PieceMove m : moves) {
			int endRow = startRow + m.getDeltaRow();
			int endCol = startCol + m.getDeltaCol();
			if (!board.inBound(endRow, endCol)) continue;
			Piece p = board.getPiece(endRow, endCol);
			if (p != null && p.getOwner() == owner) continue;
			if (m.isTwoSteps()) {
				int midRow = (endRow +startRow) / 2;
				int midCol = (endCol +startCol) / 2;
				if (board.getPiece(midRow, midCol) != null) continue;
			}
			rtn.add(m);
		}
		return rtn;
	}
	
	@Override
	public boolean isWithinMoveRange(int startRow, int startCol, int endRow, int endCol, Board board) {
		Set<PieceMove> possibleMoves = getAllValidMoves(startRow, startCol, board);
		int deltaRow = endRow - startRow;
		int deltaCol = endCol - startCol;
		for (PieceMove m : possibleMoves) {
			if (m.isEqual(deltaRow, deltaCol)) return true;
		}
		return false;
	}
	
	@Override
	public abstract boolean isLegalDrop(int row, int col, Board board);
	
	@Override
	public String toString() {
		if (promoted) {
			return "+" + String.valueOf(symbol);
		}
		return String.valueOf(symbol);
	}	
}
