package minishogi.piece;

import minishogi.core.Board;
import minishogi.core.Facing;
import minishogi.core.Piece;
import minishogi.core.Player;

/**
 * Abstract class representing the basic concept of a piece in MiniShogi 
 * @author alvinshi
 *
 */
public abstract class AbstractPiece implements Piece{
	private char symbol;
	private Player owner;
	protected boolean promoted;
	protected Facing facing;
	
	protected AbstractPiece(char symbol, Player owner) {
		this.symbol = symbol;
		this.owner = owner;
		this.facing = owner.getFacing();
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
	
	@Override
	public abstract boolean isWithinMoveRange(int startRow, int startCol, int endRow, int endCol, Board board);
	
	@Override
	public abstract boolean isLegalDrop(int row, int col, Board board);
	
	@Override
	public String toString() {
		return String.valueOf(symbol);
	}	
}
