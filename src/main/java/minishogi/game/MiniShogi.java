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
	
	/**
	 * Move the piece at "fromAddr" to "toAddr"
	 * @param fromAddr : From Address format [a-e][1-5]
	 * @param toAddr : To Address format [a-e][1-5]
	 * @param promote : If to promote
	 * @return : true if the move is valid, false otherwise
	 */
	boolean move(String fromAddr, String toAddr, boolean promote);
	
	/**
	 * Drop the piece at address
	 * @param piece : the piece to drop
	 * @param address : the address to drop
	 * @return : true if the drop is valid, false otherwise
	 */
	boolean drop(char piece, String address);
}
