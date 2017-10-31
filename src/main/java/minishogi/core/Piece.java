package minishogi.core;

/**
 * Abstract class representing the concept of a piece in MiniShogi 
 * @author alvinshi
 *
 */
abstract class Piece {
	private char symbol;
	private Player owner;
	protected boolean promoted;
	protected Facing facing;
	
	protected Piece(char symbol, Player owner) {
		this.symbol = symbol;
		this.owner = owner;
		this.facing = owner.getFacing();
	}
		
	final void capture(Player p) {
		owner = p;
		symbol = p.getSymbol(symbol);
		facing = owner.getFacing();
		demote();
	}
	
	abstract boolean promote(int endRow);
	
	abstract boolean demote();
	
	abstract boolean isWithinMoveRange(int startRow, int startCol, int endRow, int endCol, Board board);
	
	@Override
	public String toString() {
		return String.valueOf(symbol);
	}	
}
