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
	protected boolean promoted;
	protected Facing facing;
	
	protected AbstractPiece(char symbol, Player owner) {
		this.symbol = owner.getSymbol(symbol);
		this.owner = owner;
		this.facing = owner.getFacing();
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
	public abstract boolean promote(int startRow, int endRow, Board board);
	
	protected abstract void demote();
		
	protected boolean canPromote(int startRow, int endRow, Board board) {
		if (promoted) return false;
		return (startRow == board.getPromoteRow(facing) |
				endRow == board.getPromoteRow(facing));
	}
	
	@Override
	public Set<PieceMove> getAllValidMoves(int startRow, int startCol, Board board) {
		Set<PieceMove> rtn = new HashSet<>();
		for (int row = 0; row < board.getBoardSize(); row++) {
			for (int col = 0; col < board.getBoardSize(); col++) {
				if (isValidMove(startRow, startCol, row, col, board)) {
					int deltaRow = row - startRow;
					int deltaCol = col - startCol;
					rtn.add(new PieceMove(deltaRow, deltaCol));
				}
			}
		}
		return rtn;
	}
	
	@Override 
	public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, Board board) {
		if (startRow == endRow && startCol == endCol) return false;
		Piece p = board.getPiece(endRow, endCol);
		if (p != null && p.getOwner() == owner) return false;
		return isWithinMoveRange(startRow, startCol, endRow, endCol, board);
	}
	
	protected abstract boolean isWithinMoveRange(int startRow, int startCol, int endRow, int endCol, Board board);
	
	@Override
	public abstract boolean isLegalDrop(int row, int col, Board board);
	
	@Override
	public String toString() {
		if (promoted) {
			return "+" + String.valueOf(symbol);
		}
		return String.valueOf(symbol);
	}
	
	/**
	 * Produce a piece based on the symbol
	 * This factory method is there for testing
	 * @param symbol : the symbol
	 * @param upper : upper player reference
	 * @param lower : lower player reference
	 * @return : the corresponding piece
	 */
	public static Piece produce(String symbol, Player upper, Player lower) {
		boolean promoted = false;
		if (symbol.length() == 2) {
			promoted = true;
			symbol = symbol.substring(1);
		}
		char charSymbol = symbol.charAt(0);
		Player owner;
		if (Character.isUpperCase(charSymbol)) {
			owner = upper;
		}
		else {
			owner = lower;
		}
		Piece p;
		switch (Character.toLowerCase(charSymbol)) {
		case 'b' : 
			p = new BishopPiece(owner);
			break;
		case 'k' : 
			p = new KingPiece(owner);
			break;
		case 'p' : 
			p = new PawnPiece(owner);
			break;
		case 'r' : 
			p = new RookPiece(owner);
			break;
		case 's' : 
			p = new SilverGeneralPiece(owner);
			break;
		default : 
			p = new GoldGeneralPiece(owner);
		}
		if (promoted) {
			p.promote();
		}
		return p;
	}
}
