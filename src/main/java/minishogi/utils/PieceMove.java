package minishogi.utils;

import java.util.Objects;

/**
 * Represents a move
 * @author alvinshi
 *
 */
public class PieceMove {	
	private final int deltaRow;
	private final int deltaCol;
	/**
	 * Constructor for a PieceMove
	 * @param deltaRow : delta row
	 * @param deltaCol : delta column
	 */
	public PieceMove(int deltaRow, int deltaCol) {
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
	 * Check if the input is logically equal to the enum
	 * @param deltaRow : delta row
	 * @param deltaCol : delta column
	 * @return : true if logically equal
	 */
	public boolean isEqual(int deltaRow, int deltaCol) {
		return ((this.deltaRow == deltaRow) && (this.deltaCol == deltaCol));
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof PieceMove)) return false;
		PieceMove m = (PieceMove)o;
		return (m.deltaCol == deltaCol)&&(m.deltaRow == deltaRow);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(deltaCol, deltaRow);
	}
}
