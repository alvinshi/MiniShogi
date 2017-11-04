package minishogi.game;

import minishogi.core.MiniShogiImpl;

/**
 * An example of using terminal to drive the MiniShogi game API
 * @author alvinshi
 *
 */
public class Main {
	private static final String ERROR_MSG = 
		"Failed to start MiniShogi due to file damage, check the game config or reinstall the game";
	
	/**
	 * The main method
	 * @param args : args format
	 * @throws Exception : System Error
	 */
	public static void main(String[] args) throws Exception {
		MiniShogi game = new MiniShogiImpl();
		try {
			game.newGame();
		} catch (Exception e) {
			throw new Exception(ERROR_MSG);
		}
	}
}
