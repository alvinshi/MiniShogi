package minishogi.piece;

import minishogi.core.Board;
import minishogi.utils.Facing;

/**
 * utility functions to validate the move. The intention of this util class is
 * to facilitate code reuse across various pieces especially for promoted pieces.
 * @author alvinshi
 *
 */
class PieceMoveUtil {
	static boolean kingPieceMoveCheck(int startRow, int startCol, int endRow, int endCol) {
		int deltaRow = Math.abs(endRow - startRow);
		int deltaCol = Math.abs(endCol - startCol);
		return (deltaRow <= 1 && deltaCol <= 1);
	}
	
	static boolean rookPieceMoveCheck(int startRow, int startCol, int endRow, int endCol, Board board) {
		int deltaRow = endRow - startRow;
		int deltaCol = endCol - startCol;
		if (Math.min(Math.abs(deltaRow), Math.abs(deltaCol)) != 0) return false;
		if (deltaCol == 0) {
			int dRow = (deltaRow < 0) ? -1 : 1;
			for (int i = 1; i < Math.abs(deltaRow); i++) {
				int row = startRow + dRow * i;
				if (board.getPiece(row, startCol) != null) return false;
			}
			return true;
		}
		else {
			assert(deltaRow == 0);
			int dCol = (deltaCol < 0) ? -1 : 1;
			for (int i = 1; i < Math.abs(deltaCol); i++) {
				int col = startCol + dCol * i;
				if (board.getPiece(startRow, col) != null) return false;
			}
			return true;
		}
	}
	
	static boolean bishopPieceMoveCheck(int startRow, int startCol, int endRow, int endCol, Board board) {
		int deltaRow = endRow - startRow;
		int deltaCol = endCol - startCol;
		if (Math.abs(deltaRow) != Math.abs(deltaCol)) return false;
		int dRow = (deltaRow < 0) ? -1 : 1;
		int dCol = (deltaCol < 0) ? -1 : 1;
		for (int i = 1; i < Math.abs(deltaRow); i++) {
			int row = startRow + i * dRow;
			int col = startCol + i * dCol;
			if (board.getPiece(row, col) != null) {
				return false;
			}
		}
		return true;
	}
	
	static boolean goldGeneralPieceMoveCheck(int startRow, int startCol, int endRow, int endCol, Facing facing) {
		int deltaRow = endRow - startRow;
		int deltaCol = endCol - startCol;
		if (deltaRow == 0 && (deltaCol == 1 || deltaCol == -1)) return true;
		else if (deltaCol == 0 && (deltaRow == 1 || deltaRow == -1)) return true;
		else if (facing == Facing.DOWN) return (deltaRow == 1 && (deltaCol == -1 || deltaCol == 1));
		else return (deltaRow == -1 && (deltaCol == -1 || deltaCol == 1));
	}
	
	static boolean silverGeneralPieceMoveCheck(int startRow, int startCol, int endRow, int endCol, Facing facing) {
		int deltaRow = endRow - startRow;
		int deltaCol = endCol - startCol;
		if (Math.abs(deltaRow) == 1 && Math.abs(deltaCol) == 1) return true;
		else if (facing == Facing.DOWN) return (deltaRow == 1 && deltaCol == 0);
		else return (deltaRow == -1 && deltaCol == 0);
	}
	
	static boolean pawnPieceMoveCheck(int startRow, int startCol, int endRow, int endCol, Facing facing) {
		int deltaRow = endRow - startRow;
		int deltaCol = endCol - startCol;
		if (facing == Facing.DOWN) return (deltaRow == 1 && deltaCol == 0);
		else return (deltaRow == -1 && deltaCol == 0);
	}
}
