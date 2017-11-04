package minishogi.game;

/**
 * MiniShogi Game API
 * @author alvinshi
 *
 */
public interface MiniShogi {
	
	/**
	 * Start a new game
	 * @throws Exception : Fail to start a game due to misconfiguration
	 */
	void newGame() throws Exception;
}
