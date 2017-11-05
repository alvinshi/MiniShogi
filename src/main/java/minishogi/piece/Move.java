package minishogi.piece;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import minishogi.core.Board;
import minishogi.core.Facing;

/**
 * Represents a move
 * @author alvinshi
 *
 */
public enum Move {
	N (-1, 0),
	DN (-2, 0),
	S (1, 0),
	DS (2, 0),
	W (0, -1),
	DW (0, -2),
	E (0, 1),
	DE (0, 2),
	NW (-1, -1),
	DNW (-2, -2),
	SW (1, -1),
	DSW (2, -2),
	NE (-1, 1),
	DNE (-2, 2),
	SE (1, 1),
	DSE (2, 2);
	
	private static final Move[] KING_MOVES_ARRAY = 
			new Move[] {N, S, W, E, NW, SW, NE, SE};
	private static final Move[] ROOK_MOVES_ARRAY = 
			new Move[] {N, S, W, E, NW, SW, NE, SE};
	private static final Move[] BISHOP_MOVES_ARRAY = 
			new Move[] {NW, SW, NE, SE, DNW, DSW, DNE, DSE};
	private static final Move[] GOLD_GENERAL_MOVES_ARRAY = 
			new Move[] {N, S, W, E};
	private static final Move[] SILVER_GENERAL_MOVES_ARRAY = 
			new Move[] {NW, SW, NE, SE};
	
	private final int deltaRow;
	private final int deltaCol;
	Move(int deltaRow, int deltaCol) {
		this.deltaRow = deltaRow;
		this.deltaCol = deltaCol;
	}
	
	/**
	 * get the change in number of rows
	 * @return : delta_row
	 */
	public int getDeltaRow() {
		return deltaRow;
	}
	
	/**
	 * get the change in number of columns
	 * @return delta_col
	 */
	public int getDeltaCol() {
		return deltaCol;
	}
	
	/**
	 * check if the move will skip a block
	 * @return : return true if the move skips a block
	 */
	public boolean isTwoSteps() {
		return (Math.abs(deltaRow) > 1 || Math.abs(deltaCol) > 1);
	}
	
	static Set<Move> getKingMoves() {
		return new HashSet<>(Arrays.asList(KING_MOVES_ARRAY));
	}
	
	static Set<Move> getBishopMoves() {
		return new HashSet<>(Arrays.asList(BISHOP_MOVES_ARRAY));
	}
	
	static Set<Move> getRookMoves() {
		return new HashSet<>(Arrays.asList(ROOK_MOVES_ARRAY));
	}
	
	static Set<Move> getGoldGeneralMoves(Facing facing) {
		Set<Move> moves = new HashSet<>(Arrays.asList(GOLD_GENERAL_MOVES_ARRAY));
		if (facing == Facing.DOWN) {
			moves.add(SE);
			moves.add(SW);
		}
		else {
			moves.add(NE);
			moves.add(NW);
		}
		return moves;
	}
	
	static Set<Move> getSilverGeneralMoves(Facing facing) {
		Set<Move> moves = new HashSet<>(Arrays.asList(SILVER_GENERAL_MOVES_ARRAY));
		if (facing == Facing.DOWN) {
			moves.add(S);
		}
		else {
			moves.add(N);
		}
		return moves;
	}
	
	static Set<Move> getPawnMoves(Facing facing) {
		Set<Move> moves = new HashSet<>();
		if (facing == Facing.DOWN) {
			moves.add(S);
		}
		else {
			moves.add(N);
		}
		return moves;
	}
	
	boolean isEqual(int deltaRow, int deltaCol) {
		return ((this.deltaRow == deltaRow) && (this.deltaCol == deltaCol));
	}
}
