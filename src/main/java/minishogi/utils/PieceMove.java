package minishogi.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a move
 * @author alvinshi
 *
 */
public enum PieceMove {
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
	
	private static final PieceMove[] KING_MOVES_ARRAY = 
			new PieceMove[] {N, S, W, E, NW, SW, NE, SE};
	private static final PieceMove[] ROOK_MOVES_ARRAY = 
			new PieceMove[] {N, S, W, E, NW, SW, NE, SE};
	private static final PieceMove[] BISHOP_MOVES_ARRAY = 
			new PieceMove[] {NW, SW, NE, SE, DNW, DSW, DNE, DSE};
	private static final PieceMove[] GOLD_GENERAL_MOVES_ARRAY = 
			new PieceMove[] {N, S, W, E};
	private static final PieceMove[] SILVER_GENERAL_MOVES_ARRAY = 
			new PieceMove[] {NW, SW, NE, SE};
	
	private final int deltaRow;
	private final int deltaCol;
	PieceMove(int deltaRow, int deltaCol) {
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
	
	/**
	 * Get king's possible moves
	 * @return : a set of all possible moves
	 */
	public static Set<PieceMove> getKingMoves() {
		return new HashSet<>(Arrays.asList(KING_MOVES_ARRAY));
	}
	
	/**
	 * Get bishop's possible moves
	 * @return : a set of all possible moves
	 */
	public static Set<PieceMove> getBishopMoves() {
		return new HashSet<>(Arrays.asList(BISHOP_MOVES_ARRAY));
	}
	
	/**
	 * Get rook's possible moves
	 * @return : a set of all possible moves
	 */
	public static Set<PieceMove> getRookMoves() {
		return new HashSet<>(Arrays.asList(ROOK_MOVES_ARRAY));
	}
	
	/**
	 * Get gold general's possible moves based on its facing
	 * @param facing : the facing of the piece
	 * @return : a set of all possible moves
	 */
	public static Set<PieceMove> getGoldGeneralMoves(Facing facing) {
		Set<PieceMove> moves = new HashSet<>(Arrays.asList(GOLD_GENERAL_MOVES_ARRAY));
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
	
	/**
	 * Get silver general's possible moves based on its facing
	 * @param facing : the facing of the piece
	 * @return : a set of all possible moves
	 */
	public static Set<PieceMove> getSilverGeneralMoves(Facing facing) {
		Set<PieceMove> moves = new HashSet<>(Arrays.asList(SILVER_GENERAL_MOVES_ARRAY));
		if (facing == Facing.DOWN) {
			moves.add(S);
		}
		else {
			moves.add(N);
		}
		return moves;
	}
	
	/**
	 * Get pawn's possible moves based on its facing
	 * @param facing : the facing of the piece
	 * @return : a set of all possible moves
	 */
	public static Set<PieceMove> getPawnMoves(Facing facing) {
		Set<PieceMove> moves = new HashSet<>();
		if (facing == Facing.DOWN) {
			moves.add(S);
		}
		else {
			moves.add(N);
		}
		return moves;
	}
	
	/**
	 * Check if the input is logically equal to the enum
	 * @param deltaRow : delta row
	 * @param deltaCol : delta col
	 * @return : true if logically equal
	 */
	public boolean isEqual(int deltaRow, int deltaCol) {
		return ((this.deltaRow == deltaRow) && (this.deltaCol == deltaCol));
	}
}
