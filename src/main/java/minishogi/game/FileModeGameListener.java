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
	
	FileModeGameListener(int totalMoves) {
		this.totalMoves = totalMoves;
	}
	

	@Override
	public void nextTurn() {
		currentMove++;
	}

	@Override
	public void moveMade(String player, String fromAddr, String toAddr, boolean promote, String[][] board) {
		System.out.print(player + " player action: move " + fromAddr + " " + toAddr);
		if (promote) {
			System.out.println(" promote");
		}
		else {
			System.out.println();
		}
		System.out.println(Utils.stringifyBoard(board));
		
	}
	
	@Override
	public void capturedPieces(List<String> upper, List<String> lower) {
		System.out.print("Captures UPPER:");
		for (String s : upper) {
			System.out.print(" " + s);
		}
		System.out.println("\nCaptures lower:");
		for (String s : lower) {
			System.out.print(" " + s);
		}
		System.out.println();
	}

	@Override
	public void dropMade() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void invalidMove() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void check(String sadPerson, List<String> strategies) {
		System.out.println(sadPerson + " player is in check!");
		System.out.println("Available moves:");
		for (String s : strategies) {
			System.out.println(s);
		}
	}
}
