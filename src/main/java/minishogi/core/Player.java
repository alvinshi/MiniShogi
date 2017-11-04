package minishogi.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a single player in the MiniShogi
 * @author alvinshi
 *
 */
public final class Player {
	private final boolean isUpperPlayer;
	private final List<Piece> capturedPieces;
	private final Facing facing;
	
	Player(boolean isUpperPlayer) {
		this.isUpperPlayer = isUpperPlayer;
		capturedPieces = new ArrayList<>();
		if (isUpperPlayer) {
			facing = Facing.DOWN;
		}
		else {
			facing = Facing.UP;
		}
	}
	
	/**
	 * return the facing of the current player's piece
	 * @return : Facing.UP if one is not a upper player,
	 *           Facing.DOWN otherwise
	 */
	public Facing getFacing() {
		return facing;
	}
	
	/**
	 * process the symbol of a piece based on the player
	 * @param originalSymbol : the orginal symbol of the piece
	 * @return : uppercase if one is upper player
	 *           lowercase otherwise
	 */
	public char getSymbol(char originalSymbol) {
		if (isUpperPlayer) return Character.toUpperCase(originalSymbol);
		else return Character.toLowerCase(originalSymbol);
	}
	
	void addCapturedPiece(Piece e) {
		e.capture(this);
		capturedPieces.add(e);
	}
}
