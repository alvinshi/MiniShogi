package minishogi.core;

import java.util.LinkedList;
import java.util.List;

import minishogi.utils.Facing;

/**
 * Represents a single player in the MiniShogi
 * @author alvinshi
 *
 */
public final class Player {
	private final String name;
	private final boolean isUpperPlayer;
	private final List<Piece> capturedPieces;
	private final Facing facing;
	
	Player(boolean isUpperPlayer) {
		this.isUpperPlayer = isUpperPlayer;
		capturedPieces = new LinkedList<>(); //Keep the insertion order
		if (isUpperPlayer) {
			facing = Facing.DOWN;
			name = "UPPER";
		}
		else {
			facing = Facing.UP;
			name = "lower";
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
	
	/**
	 * Add a captured piece
	 * @param p : the piece to add
	 */
	public void addCapturedPiece(Piece p) {
		capturedPieces.add(p);
	}
	
	void addCapturedPieceToTheFront(Piece p) {
		capturedPieces.add(0, p);
	}
	
	/**
	 * Get a captured piece based on the symbol
	 * @param symbol : the symbol of the piece
	 * @return : the piece
	 */
	public Piece getPiece(char symbol) {
		for (Piece p : capturedPieces) {
			if (Character.toLowerCase(p.getSymbol()) == symbol) {
				capturedPieces.remove(p);
				return p;
			}
		}
		return null;
	}
	
	List<Piece> getAllCapturedPieces() {
		return capturedPieces;
	}
	
	List<String> getAllCapturedPiecesSnapShot() {
		List<String> rtn = new LinkedList<>();
		for (Piece p : capturedPieces) {
			rtn.add(p.toString());
		}
		return rtn;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
