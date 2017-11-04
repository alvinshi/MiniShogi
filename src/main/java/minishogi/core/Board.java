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
	 * Get the size of the board
	 * @return : board size
	 */
	public int getBoardSize() {
		return BOARD_SIZE;
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
	
	private void removePiece(int row, int col) {
		board[row][col] = null;
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
	
	void placePiece(Piece p, int row, int col) {
		board[row][col] = p;
	}
	
	boolean makeMove(String fromAddr, String toAddr, boolean promote, Player currentPlayer) {
		//Invalid Address
		if (!isValidAddr(fromAddr) || !isValidAddr(toAddr)) return false;
		
		int startRow = addr2Row(fromAddr);
		int startCol = addr2Col(fromAddr);
		int endRow = addr2Row(toAddr);
		int endCol = addr2Col(toAddr);
		
		Piece p = getPiece(startRow, startCol);
		if (p == null) return false;
		if (p.getOwner() != currentPlayer) return false;
		if (!p.isWithinMoveRange(startRow, startCol, endRow, endCol, this)) return false;
		if (promote) {
			if (!p.promote(endRow, this)) return false;
		}
		Piece pAtEndAddr = getPiece(endRow, endCol);
		if (pAtEndAddr != null && pAtEndAddr.getOwner() == currentPlayer) return false;
		
		//Finish checking, make the move
		if (pAtEndAddr != null) {
			//Capture the piece
			removePiece(endRow, endCol);
			pAtEndAddr.capture(currentPlayer);
			currentPlayer.addCapturedPiece(pAtEndAddr);
		}
		removePiece(startRow, startCol);
		placePiece(p, endRow, endCol);
		return true;
	}
	
	boolean makeDrop(Piece p, String address, Player currentPlayer) {
		if (!isValidAddr(address)) return false;
		int row = addr2Row(address);
		int col = addr2Col(address);
		if (getPiece(row, col) != null) return false;
		if (!p.isLegalDrop(row, col, this)) return false;
		//TODO : check checkmate
		return true;
	}
}
