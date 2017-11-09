package minishogi.core;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import minishogi.piece.KingPiece;
import minishogi.utils.Facing;
import minishogi.utils.PieceMove;
import minishogi.utils.Utils;

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
	private final Map<Player, Map<String, Integer>> kingLocations;
	
	private static int addr2Row(String address) {
		int i = Integer.valueOf(address.substring(1,2));
		return BOARD_SIZE - i;
	}
	
	private static int addr2Col(String address) {
		char c = address.charAt(0);
		return (int)c - (int)'a';
	}
	
	private static String index2addr(int row, int col) {
		char a = (char)(((int)'a') + col);
		int b = BOARD_SIZE - row;
		return a + String.valueOf(b);
	}
	
	private static boolean isValidAddr(String address) {
		return address.matches(ADDRESS_PATTERN);
	}
	
	Board() {
		board = new Piece[BOARD_SIZE][BOARD_SIZE];
		kingLocations = new HashMap<>();
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
	
	/**
	 * Get the Piece based on the address string
	 * @param address : the address string
	 * @return return the piece on the address
	 *         return null otherwise;
	 */
	public Piece getPiece(String address) {
		if (!isValidAddr(address)) return null;
		int row = addr2Row(address);
		int col = addr2Col(address);
		return getPiece(row, col);
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
	
	/**
	 * Remove the piece at the specified address
	 * @param row : row index
	 * @param col : column index
	 */
	public void removePiece(int row, int col) {
		board[row][col] = null;
	}
	
	/**
	 * place a piece on the board "without checks"
	 * @param p : the piece to be placed
	 * @param row : row index to place
	 * @param col : column index to place
	 */
	public void placePiece(Piece p, int row, int col) {
		if (p instanceof KingPiece) updateKingLocation(p, row, col);
		board[row][col] = p;
	}
		
	boolean placePiece(Piece p, String address) {
		if (!isValidAddr(address)) return false;
		int row = addr2Row(address);
		int col = addr2Col(address);
		placePiece(p, row, col);
		return true;
	}
	
	private void updateKingLocation(Piece p, int row, int col) {
		Player owner = p.getOwner();
		Map<String, Integer> loc;
		if (kingLocations.containsKey(owner)) {
			loc = kingLocations.get(owner);
		}
		else {
			loc = new HashMap<>();
		}
		loc.put("row", row);
		loc.put("col", col);
		kingLocations.put(owner, loc);
	}
		
	boolean isValidMove(int startRow, int startCol, int endRow, int endCol, boolean promote, Player currentPlayer) {
		Piece p = getPiece(startRow, startCol);
		if (p == null) return false;
		if (p.getOwner() != currentPlayer) return false;
		if (!p.isValidMove(startRow, startCol, endRow, endCol, this)) return false;
		if (promote) {
			if (!p.promote(endRow, this)) return false;
		}
		Piece pAtEndAddr = getPiece(endRow, endCol);
		if (pAtEndAddr != null && pAtEndAddr.getOwner() == currentPlayer) return false;
		return true;
	}
	
	boolean makeMove(String fromAddr, String toAddr, boolean promote, Player currentPlayer) {
		//Invalid Address
		if (!isValidAddr(fromAddr) || !isValidAddr(toAddr)) return false;
		
		int startRow = addr2Row(fromAddr);
		int startCol = addr2Col(fromAddr);
		int endRow = addr2Row(toAddr);
		int endCol = addr2Col(toAddr);
		
		if (!isValidMove(startRow, startCol, endRow, endCol, promote, currentPlayer)) {
			return false;
		}
		
		//Finish checking, make the move
		Piece p = getPiece(startRow, startCol);
		Piece pAtEndAddr = getPiece(endRow, endCol);
		if (pAtEndAddr != null) {
			//Capture the piece
			removePiece(endRow, endCol);
			pAtEndAddr.capture(currentPlayer);
			currentPlayer.addCapturedPiece(pAtEndAddr);
		}
		removePiece(startRow, startCol);
		placePiece(p, endRow, endCol);
		//Cannot move into a check position
		Player opponent = getOpponent(currentPlayer);
		if (isCheck(opponent)) return false;
		return true;
	}
	
	boolean makeDrop(Piece p, String address, Player currentPlayer) {
		if (!isValidAddr(address)) return false;
		int row = addr2Row(address);
		int col = addr2Col(address);
		if (getPiece(row, col) != null) return false;
		if (!p.isLegalDrop(row, col, this)) return false;
		return true;
	}
	
	private Player getOpponent(Player currentPlayer) {
		for (Map.Entry<Player, Map<String, Integer>> e : kingLocations.entrySet()) {
			if (e.getKey() != currentPlayer) return e.getKey();
		}
		return null;
	}
	
	/**
	 * Get the location of the opponent's king on the board
	 * @param currentPlayer : the current player
	 * @return : a map of row to row number and col to column number
	 */
	public Map<String, Integer> getOpponentKingLocation(Player currentPlayer) {
		return kingLocations.get(getOpponent(currentPlayer));
	}
	
	/**
	 * Check is the row and column are in bound
	 * @param row : row number
	 * @param col : column number
	 * @return : true if is in bound
	 */
	public boolean inBound(int row, int col) {
		if (row < 0 || row >= BOARD_SIZE) return false;
		if (col < 0 || col >= BOARD_SIZE) return false;
		return true;
	}
	
	private boolean isCheck(Player currentPlayer) {
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int col = 0; col < BOARD_SIZE; col++) {
				Piece p = board[row][col];
				if (p != null && p.getOwner() == currentPlayer) {
					Map<String, Integer> kingLoc = getOpponentKingLocation(currentPlayer);
					if (p.isValidMove(row, col, kingLoc.get("row"), kingLoc.get("col"), this)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Check if it is checkMate in the currentPlayer's turn
	 * @param currentPlayer : the player who checks
	 * @return : true if it is a checkMate
	 */
	public boolean isCheckMate(Player currentPlayer) {
		if (!isCheck(currentPlayer)) return false;
		Player opponent = getOpponent(currentPlayer);
		List<String> strategies = unCheckStrategies(opponent);
		return strategies.isEmpty();
	}
	
	private List<String> unCheckStrategies(Player currentPlayer) {
		List<String> strategies = new LinkedList<>();
		//Try all moves
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int col = 0; col < BOARD_SIZE; col++) {
				Piece p = board[row][col];
				if (p != null && p.getOwner() == currentPlayer) {
					for (PieceMove m : p.getAllValidMoves(row, col, this)) {
						int endRow = row + m.getDeltaRow();
						int endCol = col + m.getDeltaCol();
						if (canUncheckMove(row, col, endRow, endCol, currentPlayer)) {
							String from  = index2addr(row, col);
							String to = index2addr(endRow, endCol);
							strategies.add(Utils.stringifyMove(from, to));
						}
					}
				}
			}
		}
		//Try all drops
		Map<Character, List<Piece>> capturedPieces = currentPlayer.getAllCapturedPieces();
		for (Map.Entry<Character, List<Piece>> e : capturedPieces.entrySet()) {
			if (e.getValue().size() > 0) {
				//Get the piece but do not remove
				Piece p = e.getValue().get(0);
				for (int row = 0; row < BOARD_SIZE; row++) {
					for (int col = 0; col < BOARD_SIZE; col++) {
						if (canUncheckDrop(p, row, col, currentPlayer)) {
							String to = index2addr(row, col);
							strategies.add(Utils.stringifyDrop(p, to));
						}
					}
				}
			}
		}
		return strategies;
	}
	
	private boolean canUncheckMove(int startRow, int startCol, int endRow, int endCol, Player currentPlayer) {
		if (!isValidMove(startRow, startCol, endRow, endCol, false, currentPlayer)) {
			return false;
		}
		//Try the move
		Piece p = getPiece(startRow, startCol);
		Piece pAtEndAddr = getPiece(endRow, endCol);
		if (pAtEndAddr != null) {
			//Remove but do not capture
			removePiece(endRow, endCol);
		}
		removePiece(startRow, startCol);
		placePiece(p, endRow, endCol);
		boolean succeed = isCheck(currentPlayer);
		//Undo the move
		placePiece(p, startRow, startCol);
		placePiece(pAtEndAddr, endRow, endCol);
		return succeed;
	}
	
	private boolean canUncheckDrop(Piece p, int row, int col, Player currentPlayer) {
		if (getPiece(row, col) != null) return false;
		if (!p.isLegalDrop(row, col, this)) return false;
		placePiece(p, row, col);
		boolean succeed = isCheck(currentPlayer);
		//Undo the drop
		removePiece(row, col);
		return succeed;
	}
}
