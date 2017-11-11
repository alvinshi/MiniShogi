package minishogi.game;

import java.util.List;
import java.util.Scanner;

import minishogi.core.GameListener;
import minishogi.core.MiniShogiImpl;
import minishogi.utils.Utils;
import minishogi.utils.Utils.TestCase;

/**
 * An example of using terminal to drive the MiniShogi game API
 * @author alvinshi
 *
 */
public class Main {
	private static final String IM_FLAG = "-i";
	private static final String FM_FLAG = "-f";
	
	/**
	 * run the interaction mode
	 * @throws Exception : failed to load the file
	 */
	public static void runInteractionMode() throws Exception{
		MiniShogiImpl game = new MiniShogiImpl(new InteractionModeGameListener());
	    Scanner in = new Scanner(System.in);
	    while (!game.hasEnd()) {
			String[] move = (in.nextLine()).split("\\s+");
			if (move[0].equals("move")) {
				game.move(move[1], move[2], move.length == 4);
			}
			else {
				game.drop(move[1].charAt(0), move[2]);
			}
	    }
	    in.close();
	}
	
	/**
	 * run the file mode using the file in the specified path
	 * @param path : the file to run
	 * @throws Exception : failed to load the file
	 */
	public static void runFileMode(String path) throws Exception {
		TestCase tc = Utils.parseTestCase(path);
		MiniShogiImpl game = new MiniShogiImpl(tc);
		List<String> moves = tc.moves;
		GameListener gl = new FileModeGameListener(game, moves.size());
		game.registerGameListener(gl);
		while (!game.hasEnd() && (!moves.isEmpty())) {
			String[] move = (moves.remove(0)).split("\\s+");
			if (move[0].equals("move")) {
				game.move(move[1], move[2], move.length == 4);
			}
			else {
				game.drop(move[1].charAt(0), move[2]);
			}
		}
	}
	
	/**
	 * The main method
	 * @param args : args format
	 * @throws Exception : System Error
	 */
	public static void main(String[] args) throws Exception {
		if (args[0].equals(IM_FLAG)) {
			runInteractionMode();
		}
		else if (args[0].equals(FM_FLAG)) {
			runFileMode(args[1]);
		}
	}
}
