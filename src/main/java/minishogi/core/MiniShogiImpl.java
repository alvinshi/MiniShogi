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
	private static final int MOVE_LIMIT = 400;
	
	private int turn;
	private boolean gameOver = true;
	private Queue<Player> playerQueue;
	private Player upperPlayer;
	private Player lowerPlayer;
	private Player currentPlayer;
	private Board board;
	private List<GameListener> gameListeners;
	
	private void nextTurn() {
		turn++;
		if (turn > MOVE_LIMIT && !gameOver) {
			gameOver = true;
			for (GameListener gl : gameListeners) {
				gl.tie();
			}
		}
		if (gameOver) return;
		currentPlayer = playerQueue.poll();
		playerQueue.add(currentPlayer);
		for (GameListener gl : gameListeners) {
			gl.nextTurn(currentPlayer.toString());
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
		upperPlayer = new Player(true);
		lowerPlayer = new Player(false);
		
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
		
		playerQueue.add(lowerPlayer);
		playerQueue.add(upperPlayer);
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
		upperPlayer = new Player(true);
		lowerPlayer = new Player(false);
		playerQueue.add(lowerPlayer);
		playerQueue.add(upperPlayer);
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
		List<String> strategies = new LinkedList<>();
		Player opponent = getOpponent();
		boolean isCheck = false;
		if (legalMove) {
			if (board.isCheck(currentPlayer)) {
				isCheck = true;
				strategies = board.unCheckStrategies(opponent);
				if (strategies.size() == 0) {
					gameOver = true;
				}
			}
		}
		else {
			gameOver = true;
		}
		
		//Inform the observer
		for (GameListener gl : gameListeners) {
			gl.moveMade(currentPlayer.toString(), fromAddr, toAddr, promote, board.getSnapShot());
			List<String> upper = upperPlayer.getAllCapturedPiecesSnapShot();
			List<String> lower = lowerPlayer.getAllCapturedPiecesSnapShot();
			gl.capturedPieces(upper, lower);
		}
		if (legalMove) {
			if (isCheck && !gameOver) {
				for (GameListener gl : gameListeners) {
					gl.check(opponent.toString(), strategies);
				}
			}
			else if (isCheck && gameOver) {
				for (GameListener gl : gameListeners) {
					gl.checkMate(currentPlayer.toString());
				}
			}
		}
		else {
			for (GameListener gl : gameListeners) {
				gl.invalidMove(getOpponent().toString());
			}
			gameOver = true;
		}
		
		//Move on
		if (!gameOver) {
			nextTurn();
		}
		return legalMove;
	}

	@Override
	public boolean drop(char piece, String address) {
		if (gameOver) return false;
		Piece p = currentPlayer.getPiece(piece);
		boolean legalDrop = true;
		if (p == null) {
			gameOver = true;
			legalDrop = false;
		}
		else {
			legalDrop = board.makeDrop(p, address, currentPlayer);
		}
		if (!legalDrop && p != null) {
			currentPlayer.addCapturedPieceToTheFront(p);
		}
		List<String> strategies = new LinkedList<>();
		Player opponent = getOpponent();
		boolean isCheck = false;
		
		if (legalDrop) {
			if (board.isCheck(currentPlayer)) {
				isCheck = true;
				strategies = board.unCheckStrategies(opponent);
				if (strategies.size() == 0) {
					gameOver = true;
				}
			}
		}
		else {
			gameOver = true;
		}
		//Inform the observer
		for (GameListener gl : gameListeners) {
			gl.dropMade(currentPlayer.toString(), String.valueOf(piece), address, board.getSnapShot());
			List<String> upper = upperPlayer.getAllCapturedPiecesSnapShot();
			List<String> lower = lowerPlayer.getAllCapturedPiecesSnapShot();
			gl.capturedPieces(upper, lower);
		}
		if (legalDrop) {
			if (isCheck && !gameOver) {
				for (GameListener gl : gameListeners) {
					gl.check(opponent.toString(), strategies);
				}
			}
			else if (isCheck && gameOver) {
				for (GameListener gl : gameListeners) {
					gl.checkMate(currentPlayer.toString());
				}
			}
		}
		else {
			for (GameListener gl : gameListeners) {
				gl.invalidMove(getOpponent().toString());
			}
			gameOver = true;
		}
		nextTurn();
		return legalDrop;
	}

	@Override
	public void registerGameListener(GameListener gameListener) {
		gameListeners.add(gameListener);
	}

	@Override
	public void clearGameListener() {
		gameListeners = new ArrayList<>();
	}

	@Override
	public boolean hasEnd() {
		return gameOver;
	}
	
	private Player getOpponent() {
		return playerQueue.peek();
	}
}
