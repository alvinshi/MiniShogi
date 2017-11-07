package minishogi.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import minishogi.core.Piece;

/**
 * Helper Functions which mainly deal with testing and output translation
 * @author alvinshi, box
 *
 */
public class Utils {
	/**
	 * Stringify a move based on from and to addresses
	 * @param from : from address
	 * @param to : to address
	 * @return : string representation of a move
	 */
	public static String stringifyMove(String from, String to) {
		return "move " + from + " " + to;
	}
	
	/**
	 * Stringify a drop based on the piece and the to address
	 * @param p : the piece to be dropped
	 * @param to : to address
	 * @return : string representation of a drop
	 */
	public static String stringifyDrop(Piece p, String to) {
		return "drop " + Character.toLowerCase(p.getSymbol()) + " " + to;
	}
	
	/**
	 * initial position of a piece
	 */
	public static class InitialPosition {
		public final String piece;
		public final String position;
        
        InitialPosition(String pc, String pos) {
            piece = pc;
            position = pos;
        }

        @Override
        public String toString() {
            return piece + " " + position;
        }
    }

    /**
     * a test case
     */
    public static class TestCase {

        public final List<InitialPosition> initialPieces;
        public final List<String> upperCaptures;
        public final List<String> lowerCaptures;
        public final List<String> moves;

        TestCase(List<InitialPosition> ip, List<String> uc, List<String> lc, List<String> m) {
            initialPieces = ip;
            upperCaptures = uc;
            lowerCaptures = lc;
            moves = m;
        }

        @Override
        public String toString() {
            String str = "";

            str += "initialPieces: [\n";
            for (InitialPosition piece : initialPieces) {
                str += piece + "\n";
            }
            str += "]\n";

            str += "upperCaptures: [";
            for (String piece : upperCaptures) {
                str += piece + " ";
            }
            str += "]\n";

            str += "lowerCaptures: [";
            for (String piece : lowerCaptures) {
                str += piece + " ";
            }
            str += "]\n";

            str += "moves: [\n";
            for (String move : moves) {
                str += move + "\n";
            }
            str += "]";

            return str;
        }
    }

    /**
     * Stringify a board 
     * @param board : the board
     * @return : a string representation of the current board
     */
    public static String stringifyBoard(String[][] board) {

        String str = "";

        for (int row = board.length - 1; row >= 0; row--) {
            
            str += Integer.toString(row + 1) + " |";
            for (int col = 0; col < board[row].length; col++) {
                str += stringifySquare(board[col][row]);
            }
            str += System.getProperty("line.separator");
        }

        str += "    a  b  c  d  e" + System.getProperty("line.separator");

        return str;
    }

    private static String stringifySquare(String sq) {

        switch(sq.length()) {
            case 0:
                return "__|";
            case 1:
                return " " + sq + "|";
            case 2:
                return sq + "|";
		default:
			break;
        }

        throw new IllegalArgumentException("Board must be an array of strings like \"\", \"P\", or \"+P\"");
    }

    /**
     * parse a test case into a test case object
     * @param path : file path of the test case
     * @return : a test case object
     * @throws Exception : error
     */
    public static TestCase parseTestCase(String path) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line = br.readLine().trim();
        List<InitialPosition> initialPieces = new ArrayList<InitialPosition>();
        while (!line.equals("")) {
            String[] lineParts = line.split(" ");
            initialPieces.add(new InitialPosition(lineParts[0], lineParts[1]));
            line = br.readLine().trim();
        }
        line = br.readLine().trim();
        List<String> upperCaptures = Arrays.asList(line.substring(1, line.length() - 1).split(" "));
        line = br.readLine().trim();
        List<String> lowerCaptures = Arrays.asList(line.substring(1, line.length() - 1).split(" "));
        line = br.readLine().trim();
        line = br.readLine().trim();
        List<String> moves = new ArrayList<String>();
        while (line != null) {
            line = line.trim();
            moves.add(line);
            line = br.readLine();
        }
        br.close();

        return new TestCase(initialPieces, upperCaptures, lowerCaptures, moves);
    }
}
