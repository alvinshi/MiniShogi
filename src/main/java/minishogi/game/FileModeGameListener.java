package minishogi.game;

import java.util.List;

import minishogi.core.GameListener;
import minishogi.utils.Utils;

/**
 * File Mode Game Listener which decides when to output
 * information onto the stdout
 * @author alvinshi
 *
 */
final class FileModeGameListener implements GameListener {
	private final int totalMoves;
	private int currentMove = 0;
	private final MiniShogi game;
	
	FileModeGameListener(MiniShogi game, int totalMoves) {
		this.totalMoves = totalMoves;
		this.game = game;
	}
	

	@Override
	public void nextTurn(String player) {
		currentMove++;
		if (currentMove == totalMoves && !game.hasEnd()) {
			System.out.println(player + "> ");
		}
	}
	


	@Override
	public void boardUpdate(String[][] board) {
		if (currentMove == totalMoves - 1 || game.hasEnd()) {
			System.out.println(Utils.stringifyBoard(board));
		}	
	}

	@Override
	public void moveMade(String player, String fromAddr, String toAddr, boolean promote) {
		if (currentMove == totalMoves - 1 || game.hasEnd()) {
			System.out.print(player + " player action: move " + fromAddr + " " + toAddr);
			if (promote) {
				System.out.println(" promote");
			}
			else {
				System.out.println();
			}
		}		
	}
	
	@Override
	public void capturedPieces(List<String> upper, List<String> lower) {
		if (currentMove == totalMoves - 1 || game.hasEnd()) {
			System.out.print("Captures UPPER: ");
			StringBuilder pieces = new StringBuilder();
			for (String s : upper) {
				pieces.append(" " + s);
			}
			System.out.println(pieces.toString().trim());
			System.out.print("Captures lower: ");
			pieces = new StringBuilder();
			for (String s : lower) {
				pieces.append(" " + s);
			}
			System.out.println(pieces.toString().trim());
			System.out.println();
		}
	}

	@Override
	public void dropMade(String player, String piece, String address) {
		if (currentMove == totalMoves - 1 || game.hasEnd()) {
			System.out.println(player + " player action: drop " + piece + " " + address);
		}
	}

	@Override
	public void invalidMove(String winner) {
		System.out.println(winner + " player wins.  Illegal move.");
	}

	@Override
	public void check(String sadPerson, List<String> strategies) {
		if (currentMove == totalMoves - 1 || game.hasEnd()) {
			System.out.println(sadPerson + " player is in check!");
			System.out.println("Available moves:");
			strategies.sort(String::compareToIgnoreCase);
			for (String s : strategies) {
				System.out.println(s);
			}
		}
	}


	@Override
	public void checkMate(String winner) {
		System.out.println(winner + " player wins.  Checkmate.");
	}


	@Override
	public void tie() {
		System.out.println("Tie game.  Too many moves.");
	}
}
