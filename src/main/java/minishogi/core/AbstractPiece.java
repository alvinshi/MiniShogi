package minishogi.core;

/**
 * Abstract class representing the basic concept of a piece in MiniShogi 
 * @author alvinshi
 *
 */
abstract class AbstractPiece implements Piece{
	private char symbol;
	private Player owner;
	protected boolean promoted;
	protected Facing facing;
	
	protected AbstractPiece(char symbol, Player owner) {
		this.symbol = symbol;
		this.owner = owner;
		this.facing = owner.getFacing();
	}
		
	public final void capture(Player p) {
		owner = p;
		symbol = p.getSymbol(symbol);
		facing = owner.getFacing();
		demote();
	}
	
	public abstract boolean promote(int endRow, Board board);
	
	protected abstract void demote();
	
	public abstract boolean isWithinMoveRange(int startRow, int startCol, int endRow, int endCol, Board board);
	
	@Override
	public String toString() {
		return String.valueOf(symbol);
	}	
}
