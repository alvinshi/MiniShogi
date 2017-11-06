package minishogi.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import minishogi.utils.Facing;

/**
 * Represents a single player in the MiniShogi
 * @author alvinshi
 *
 */
public final class Player {
	private final boolean isUpperPlayer;
	private final Map<Character, List<Piece>> capturedPieces;
	private final Facing facing;
	
	Player(boolean isUpperPlayer) {
		this.isUpperPlayer = isUpperPlayer;
		capturedPieces = new HashMap<>();
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
	 * @param originalSymbol : the original symbol of the piece
	 * @return : upper case if one is upper player
	 *           lower case otherwise
	 */
	public char getSymbol(char originalSymbol) {
		if (isUpperPlayer) return Character.toUpperCase(originalSymbol);
		else return Character.toLowerCase(originalSymbol);
	}
	
	void addCapturedPiece(Piece p) {
		char symbol = Character.toLowerCase(p.getSymbol());
		if (capturedPieces.containsKey(symbol)) {
			capturedPieces.get(symbol).add(p);
		}
		else {
			List<Piece> pieces = new ArrayList<>();
			pieces.add(p);
			capturedPieces.put(symbol, pieces);
		}
	}
	
	Piece getPiece(char symbol) {
		if (capturedPieces.containsKey(symbol)) {
			try {
				Piece p = capturedPieces.get(symbol).remove(0);
				return p;
			} catch (IndexOutOfBoundsException e) {
				return null;
			}
		}
		else {
			return null;
		}
	}
	
	Map<Character, List<Piece>> getAllCapturedPieces() {
		return capturedPieces;
	}
}
