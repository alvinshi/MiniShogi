package minishogi.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a single player in the MiniShogi
 * @author alvinshi
 *
 */
final class Player {
	private final boolean isUpperPlayer;
	private final List<AbstractPiece> capturedPieces;
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
	
	Facing getFacing() {
		return facing;
	}
	
	char getSymbol(char originalSymbol) {
		if (isUpperPlayer) return Character.toUpperCase(originalSymbol);
		else return Character.toLowerCase(originalSymbol);
	}
	
	void addCapturedPiece(AbstractPiece e) {
		e.capture(this);
		capturedPieces.add(e);
	}
}
