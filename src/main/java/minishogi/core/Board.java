package minishogi.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a board in the MiniShogi
 * @author alvinshi
 *
 */
final class Board {
	private static final int BOARD_SIZE = 5;
	
	private final List<List<Piece>> board;
	
	Board() {
		board = new ArrayList<>(BOARD_SIZE);
		for (int i = 0; i < BOARD_SIZE; i++) {
			board.add(new ArrayList<>(BOARD_SIZE));
		}
	}
	
	Piece getPiece(int row, int col) {
		return board.get(row).get(col);
	}
}
