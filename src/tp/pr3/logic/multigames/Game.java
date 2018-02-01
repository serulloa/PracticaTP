package tp.pr3.logic.multigames;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Random;

import tp.pr3.exceptions.EmptyStackException;
import tp.pr3.exceptions.SaveException;
import tp.pr3.logic.Board;
import tp.pr3.logic.Direction;
import tp.pr3.logic.MoveResults;
import tp.pr3.logic.reundo.GameState;
import tp.pr3.logic.reundo.GameStateStack;

/**
 * @author Sergio Ulloa
 */
public class Game {
	
	// ================================================================================
	// Atributos
	// ================================================================================
	
	private Board board; 				// Tablero
	private int size; 					// Dimensión del tablero
	private int initCells; 				// Número de baldosas no nulas iniciales
	private Random myRandom; 			// Comportamiento aleatorio del juego
	private int score;					// Puntuación del usuario
	private int highest;				// Máximo token conseguido
	private boolean losen;				// Partida perdida
	private boolean finished;			// Partida acabada
	private GameStateStack undoStack;	// Movimientos para deshacer
	private GameStateStack redoStack;	// Movimientos para rehacer
	private GameType currentGame;		// Guarda las reglas del juego actual
	
	// ================================================================================
	// Constructores
	// ================================================================================
	
	public Game(GameType game, int size, int initCells, long seed) {
		this.size = size;
		this.initCells = initCells;
		this.myRandom = new Random(seed);
		this.score = 0;
		this.losen = false;
		this.finished = false;
		
		this.board = new Board(size);
		
		// Inicializamos los GameStateStacks
		undoStack = new GameStateStack();
		redoStack = new GameStateStack();
		
		this.currentGame = game;
		this.currentGame.getRules().initBoard(this.board, initCells, this.myRandom);
		this.highest = currentGame.getRules().getWinValue(this.board);
	}
	
	// ================================================================================
	// Métodos
	// ================================================================================
	
	/**
	 * Realiza el movimiento de las fichas así como la creación de la nueva con el
	 * valor aleatorio
	 * 
	 * @param dir Es la dirección del movimiento
	 * @return Devuelve un booleano con información de si se debe imprimir la partida o no
	 * @throws EmptyStackException si hay algún error al eliminar del stack undo 
	 * 								el movimiento no realizado
	 */
	public boolean move(Direction dir) throws EmptyStackException {
		if(!losen && !finished) {
			redoStack = new GameStateStack();
			undoStack.push(getState());
			
			MoveResults results = board.executeMove(dir, currentGame.getRules());
			
			score += results.getPoints();
			highest = currentGame.getRules().getWinValue(board);
			if(currentGame.getRules().win(board)) finished = true;
			
			if(results.isMoved()) currentGame.getRules().addNewCell(board, myRandom);
			else {
				try {
					undoStack.pop();
				} catch (EmptyStackException e) {
					throw e;
				}
			}
			
			if(currentGame.getRules().lose(board)) losen = true;
		}
		else
			losen = true;
		
		return losen || finished;
	}
	
	/**
	 * Método que inicializa la partida
	 */
	public void reset() {
		score = 0;
		board = new Board(size);
		losen = false;
		finished = false;
		
		currentGame.getRules().initBoard(board, initCells, myRandom);
		highest = currentGame.getRules().getWinValue(board);
	}
	
	public String toString() {
		String ret = "\n";
		
		ret += board.toString();
		ret += "best value: " + String.valueOf(highest);
		ret += "		score: " + String.valueOf(score) + "\n";
		if(losen) ret += "Game over\n";
		if(finished) ret += "Well done!\n";
		
		return ret;
	}
	
	/**
	 * Devuelve el atributo booleano losen de la clase Game, que indica
	 * 
	 * @return Game.losen
	 */
	public boolean isLosen() {
		return losen;
	}
	
	/**
	 * Establece el atributo finish a true para indicar la finalización de la
	 * partida.
	 * 
	 * @return Devuelve true
	 */
	public boolean finish() {
		finished = true;
		return true;
	}
	
	/**
	 * Deshace el último movimiento que se ha realizado.
	 * 
	 * @throws EmptyStackException Si la pila está vacía.
	 */
	public void undo() throws EmptyStackException {
		try {
			GameState temp = getState();
			setState(undoStack.pop());
			redoStack.push(temp);
		} catch (EmptyStackException e) {
			throw new EmptyStackException("Nothing to undo");
		}
	}
	
	/**
	 * Rehace el último movimiento que se ha deshecho.
	 * 
	 * @throws EmptyStackException 
	 */
	public void redo() throws EmptyStackException {
		try {
			GameState temp = getState();
			setState(redoStack.pop());
			undoStack.push(temp);
		} catch (EmptyStackException e) {
			throw new EmptyStackException("Nothing to redo");
		}
	}
	
	/**
	 * Obtiene el estado de la partida invocando al método getState de Board.
	 * 
	 * @return Devuelve un GameState con el estado actual de la partida.
	 */
	private GameState getState() {
		GameState ret = new GameState(board.getState(), score);
		return ret;
	}
	
	/**
	 * Restablece el juego al estado aState e invocando el método setState de Board.
	 * 
	 * @param aState Estado al que se quiere restablecer la partida.
	 */
	private void setState(GameState aState) {
		score = aState.getScore();
		board.setState(aState.getBoardState());
	}

	/**
	 * Se utiliza para resetear la partida cuando hay un cambio de tipo de juego.
	 * 
	 * @param rules Nuevas reglas a utilizar, correspondientes al nuevo tipo de juego.
	 * @param boardSize Tamaño del nuevo tablero.
	 * @param initialCells Número de celdas iniciales.
	 * @param randomSeed Semilla de aleatoriedad.
	 */
	public void changeGame(GameType game, int boardSize, int initialCells, int randomSeed) {
		this.currentGame = game;
		this.size = boardSize;
		this.initCells = initialCells;
		this.myRandom = new Random(randomSeed);
		
		score = 0;
		board = new Board(size);
		losen = false;
		finished = false;
		
		currentGame.getRules().initBoard(board, initCells, myRandom);
		highest = currentGame.getRules().getWinValue(board);
		
		// Inicializamos los GameStateStacks
		undoStack = new GameStateStack();
		redoStack = new GameStateStack();
	}

	/**
	 * Método que guarda en archivo una partida y a su vez llama al método store de Board
	 * 
	 * @param out Buffer donde se escribirán los datos
	 * @throws IOException En caso de que se produzca un error al escribir en fichero
	 */
	public void store(BufferedWriter out) throws IOException {
		board.store(out);
		out.write(this.initCells + "\t" +  this.score + "\t" + this.currentGame.externalise());
	}

	/**
	 * Método que carga de archivo una partida y a su vez llama al método load de Board
	 * 
	 * @param in Buffer del que se lee los datos
	 * @return Devuelve el tipo de juego para que el método execute() de LoadCommand pueda mostrar
	 * 			un mensaje de confirmación
	 * @throws IOException En caso de que se produzca un error en la lectura de fichero
	 * @throws SaveException En caso de que el archivo tenga un formato inválido
	 */
	public GameType load(BufferedReader in) throws IOException, SaveException {
		board.load(in);
		String line = in.readLine();
		String fields[] = line.split("\t");
		
		currentGame = GameType.parse(fields[2]);
		if (currentGame == null) throw new SaveException("Load failed: invalid file format");
		
		size = board.getBoardSize();
		initCells = Integer.valueOf(fields[0]);
		score = Integer.valueOf(fields[1]);
		
		losen = false;
		finished = false;
		
		highest = currentGame.getRules().getWinValue(board);
		
		undoStack = new GameStateStack();
		redoStack = new GameStateStack();
		
		return currentGame;
	}
	
}
