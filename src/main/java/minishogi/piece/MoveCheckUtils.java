package minishogi.piece;

import minishogi.core.Board;
import minishogi.core.Facing;

/**
 * utility functions to validate the move. The intention of this util class is
 * to facilitate code reuse across various pieces especially for promoted pieces.
 * @author alvinshi
 *
 */
final class MoveCheckUtils {	
	private static boolean hasMoved(int startRow, int startCol, int endRow, int endCol) {
		return  !(startRow == endRow && startCol == endCol);
	}
	
	static boolean canPromote(int row, Facing facing, Board board) {
		return row == board.getPromoteRow(facing);
	}
	
	static boolean kingPieceMoveCheck(int startRow, int startCol, int endRow, int endCol) {
		if (!hasMoved(startRow, startCol, endRow, endCol)) return false;
		int deltaRow = Math.abs(endRow - startRow);
		int deltaCol = Math.abs(endCol - startCol);
		return (deltaRow <= 1 && deltaCol <= 1);
	}
	
	static boolean rookPieceMoveCheck(int startRow, int startCol, int endRow, int endCol, Board board) {
		if (!hasMoved(startRow, startCol, endRow, endCol)) return false;
		int deltaRow = Math.abs(endRow - startRow);
		int deltaCol = Math.abs(endCol - startCol);
		if (deltaRow == 2 || deltaCol == 2) {
			int midRow = (startRow + endRow) / 2;
			int midCol = (startCol + endCol) / 2;
			if (board.getPiece(midRow, midCol) != null) return false;
		}
		return (Integer.max(deltaRow, deltaCol) <= 2 && Integer.min(deltaRow, deltaCol) <= 0);
	}
	
	static boolean bishopPieceMoveCheck(int startRow, int startCol, int endRow, int endCol, Board board) {
		if (!hasMoved(startRow, startCol, endRow, endCol)) return false;
		int deltaRow = Math.abs(endRow - startRow);
		int deltaCol = Math.abs(endCol - startCol);
		if (deltaRow == 2) {
			int midRow = (startRow + endRow) / 2;
			int midCol = (startCol + endCol) / 2;
			if (board.getPiece(midRow, midCol) != null) return false;
		}
		return (deltaRow <= 2 && deltaRow == deltaCol);
	}
	
	static boolean goldGeneralPieceMoveCheck(int startRow, int startCol, int endRow, int endCol, Facing facing) {
		if (!hasMoved(startRow, startCol, endRow, endCol)) return false;
		int deltaRow = endRow - startRow;
		int deltaCol = endCol - startCol;
		if (deltaRow == 0 && (deltaCol == 1 || deltaCol == -1)) return true;
		else if (deltaCol == 0 && (deltaRow == 1 || deltaRow == -1)) return true;
		else if (facing == Facing.DOWN) return (deltaRow == 1 && (deltaCol == -1 || deltaCol == -1));
		else return (deltaRow == -1 && (deltaCol == -1 || deltaCol == -1));
	}
	
	static boolean silverGeneralPieceMoveCheck(int startRow, int startCol, int endRow, int endCol, Facing facing) {
		if (!hasMoved(startRow, startCol, endRow, endCol)) return false;
		int deltaRow = endRow - startRow;
		int deltaCol = endCol - startCol;
		if (Math.abs(deltaRow) == 1 && Math.abs(deltaCol) == 1) return true;
		else if (facing == Facing.DOWN) return (deltaRow == 1 && deltaCol == 0);
		else return (deltaRow == -1 && deltaCol == 0);
	}
	
	static boolean pawnPieceMoveCheck(int startRow, int startCol, int endRow, int endCol, Facing facing) {
		if (!hasMoved(startRow, startCol, endRow, endCol)) return false;
		int deltaRow = endRow - startRow;
		int deltaCol = endCol - startCol;
		if (facing == Facing.DOWN) return (deltaRow == 1 && deltaCol == 0);
		else return (deltaRow == -1 && deltaCol == 0);
	}
}
