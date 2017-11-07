package minishogi.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

import minishogi.game.MiniShogi;
import minishogi.piece.AbstractPiece;
import minishogi.utils.Utils.InitialPosition;
import minishogi.utils.Utils.TestCase;

/**
 * Implementation of MiniShogi
 * @author alvinshi
 *
 */
public final class MiniShogiImpl implements MiniShogi{
	private static final String GAME_INIT_FILE = "config/game/game.init";
	private static final String PIECE_PACKAGE_PATH = "minishogi.piece.";
	private static final int MOVE_LIMIT = 200;
	
	private int turn;
	private boolean gameOver = true;
	private Queue<Player> playerQueue;
	private Player currentPlayer;
	private Board board;
	private List<GameListener> gameListeners;
	
	private void nextTurn() {
		currentPlayer = playerQueue.poll();
		playerQueue.add(currentPlayer);
		turn++;
		if (turn >= MOVE_LIMIT) {
			gameOver = true;
		}
	}
	
	private void placePiece(String symbol, String address, Board board, Player upper, Player lower) {
		Piece p = AbstractPiece.produce(symbol, upper, lower);
		board.placePiece(p, address);
	}
	
	/**
	 * Construct a game based on the test case
	 * This constructor is purely for testing purposes
	 * @param tc : the test case
	 */
	public MiniShogiImpl(TestCase tc) {
		turn = 0;
		gameOver = false;
		gameListeners = new ArrayList<>();
		
		//Initialize Players and the queue
		playerQueue = new LinkedList<>();
		Player upperPlayer = new Player(true);
		Player lowerPlayer = new Player(false);
		
		//The board
		board = new Board();
		List<InitialPosition> ips = tc.initialPieces;
		for (InitialPosition ip : ips) {
			String symbol = ip.piece;
			String address = ip.position;
			placePiece(symbol, address, board, upperPlayer, lowerPlayer);
		}
		List<String> upperCaptures = tc.upperCaptures;
		for (String symbol : upperCaptures) {
			if (symbol.length() < 1) continue;
			Piece p = AbstractPiece.produce(symbol, upperPlayer, lowerPlayer);
			upperPlayer.addCapturedPiece(p);
		}
		List<String> lowerCaptures = tc.lowerCaptures;
		for (String symbol : lowerCaptures) {
			if (symbol.length() < 1) continue;
			Piece p = AbstractPiece.produce(symbol, upperPlayer, lowerPlayer);
			lowerPlayer.addCapturedPiece(p);
		}
		
		//Determine the first player based on the first move
		List<String> moves = tc.moves;
		String[] firstMove = (moves.get(0)).split("\\s+");
		if (firstMove[0].equals("drop")) {
			if (Character.isLowerCase(firstMove[1].charAt(0))) {
				playerQueue.add(lowerPlayer);
				playerQueue.add(upperPlayer);
			}
			else {
				playerQueue.add(upperPlayer);
				playerQueue.add(lowerPlayer);				
			}
		}
		else {
			Piece p = board.getPiece(firstMove[1]);
			if (p.getOwner() == upperPlayer) {
				playerQueue.add(upperPlayer);
				playerQueue.add(lowerPlayer);
			}
			else {
				playerQueue.add(lowerPlayer);
				playerQueue.add(upperPlayer);
			}
		}
		nextTurn();
	}
	
	/**
	 * Default constructor to start a new game
	 * @throws FileNotFoundException : the game.init file cannot be found
	 * @throws InstantiationException : failed to create piece
	 * @throws IllegalAccessException : does not have access to the corresponding piece class
	 * @throws IllegalArgumentException : illegal argument in the constructor
	 * @throws InvocationTargetException : failed to invoke a target
	 * @throws NoSuchMethodException : no constructor found
	 * @throws SecurityException : security
	 * @throws ClassNotFoundException : class is not found
	 */
	public MiniShogiImpl() throws FileNotFoundException, InstantiationException, 
	IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
	NoSuchMethodException, SecurityException, ClassNotFoundException {
		gameListeners = new ArrayList<>();
		newGame();
	}

	@Override
	public void newGame() throws FileNotFoundException, InstantiationException, 
		IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
			NoSuchMethodException, SecurityException, ClassNotFoundException{
		//Initialize the turn
		turn = 0;
		gameOver = false;
		
		//Initialize Players and the queue
		playerQueue = new LinkedList<>();
		Player upperPlayer = new Player(true);
		Player lowerPlayer = new Player(false);
		playerQueue.add(upperPlayer);
		playerQueue.add(lowerPlayer);
		nextTurn();
		
		//Initialize Pieces and Board
		board = new Board();
		Scanner sc;
		sc = new Scanner(new File(GAME_INIT_FILE));
		while (sc.hasNextLine()) {
			String str = sc.nextLine();
			String[] line = str.split("\\s+");
			boolean isUpper = line[1].equals("UPPER");
			String address = line[2];
			Player owner = lowerPlayer;
			if (isUpper) {
				owner = upperPlayer;
			}
			Piece p = (Piece)Class.forName(PIECE_PACKAGE_PATH + line[0])
						.getDeclaredConstructor(minishogi.core.Player.class)
							.newInstance(owner);
			board.placePiece(p, address);
		}
		sc.close();
	}

	@Override
	public boolean move(String fromAddr, String toAddr, boolean promote) {
		if (gameOver) return false;
		boolean legalMove = board.makeMove(fromAddr, toAddr, promote, currentPlayer);
		if (legalMove) {
			nextTurn();
			return true;
		}
		else {
			gameOver = true;
			return false;
		}
	}

	@Override
	public boolean drop(char piece, String address) {
		if (gameOver) return false;
		Piece p = currentPlayer.getPiece(piece);
		if (p == null) {
			gameOver = true;
			return false;
		}
		boolean legalDrop = board.makeDrop(p, address, currentPlayer);
		if (legalDrop) {
			nextTurn();
			return true;
		}
		else {
			gameOver = true;
			return false;
		}
	}

	@Override
	public void registerGameListener(GameListener gameListener) {
		gameListeners.add(gameListener);
	}

	@Override
	public void clearGameListener() {
		gameListeners = new ArrayList<>();
	}
}
