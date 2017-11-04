package minishogi.core;

/**
 * Represents a board in the MiniShogi
 * @author alvinshi
 *
 */
public final class Board {
	private static final int BOARD_SIZE = 5;
	private static final int UP_PROMOTION_ROW = 0;
	private static final int DOWN_PROMOTION_ROW = BOARD_SIZE - 1;
	private static final String ADDRESS_PATTERN = "[a-e][1-5]";
	
	private final Piece[][] board;
	
	private static int addr2Row(String address) {
		int i = Integer.valueOf(address.substring(1,2));
		return BOARD_SIZE - i;
	}
	
	private static int addr2Col(String address) {
		char c = address.charAt(0);
		return (int)c - (int)'a';
	}
	
	private static boolean isValidAddr(String address) {
		return address.matches(ADDRESS_PATTERN);
	}
	
	Board() {
		board = new Piece[BOARD_SIZE][BOARD_SIZE];
	}
	
	/**
	 * Get the Piece on the specified location
	 * @param row : row number (1~5)
	 * @param col : col number (a~e)
	 * @return return the piece on the request location,
	 *         return null if not
	 */
	public Piece getPiece(int row, int col) {
		return board[row][col];
	}
	
	/**
	 * Get the promote row number based on the facing of the piece
	 * @param facing : UP or DOWN
	 * @return : the row index of the promotion row
	 */
	public int getPromoteRow(Facing facing) {
		if (facing == Facing.UP) return UP_PROMOTION_ROW;
		else return DOWN_PROMOTION_ROW;
	}
	
	boolean placePiece(Piece p, String address) {
		if (!isValidAddr(address)) return false;
		int row = addr2Row(address);
		int col = addr2Col(address);
		board[row][col] = p;
		return true;
	}
}
