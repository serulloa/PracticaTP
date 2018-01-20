package tp.pr2.logic.multigames;

import java.util.Random;
import tp.pr2.logic.Board;
import tp.pr2.logic.Direction;
import tp.pr2.logic.MoveResults;
import tp.pr2.logic.reundo.GameState;
import tp.pr2.logic.reundo.GameStateStack;

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
	private GameRules currentRules;		// Guarda las reglas del juego actual
	
	// ================================================================================
	// Constructores
	// ================================================================================
	
	public Game(GameRules rules, int size, int initCells, long seed) {
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
		
		this.currentRules = rules;
		this.currentRules.initBoard(this.board, initCells, this.myRandom);
		this.highest = currentRules.getWinValue(this.board);
	}
	
	// ================================================================================
	// Métodos
	// ================================================================================
	
	/**
	 * Realiza el movimiento de las fichas así como la creación de la nueva con el
	 * valor aleatorio
	 * 
	 * @param dir Es la dirección del movimiento
	 */
	public void move(Direction dir) {
		if(!losen && !finished) {
			redoStack = new GameStateStack();
			undoStack.push(getState());
			
			MoveResults results = board.executeMove(dir, currentRules);
			
			score += results.getPoints();
			highest = currentRules.getWinValue(board);
			if(currentRules.win(board)) finished = true;
			
			if(results.isMoved()) currentRules.addNewCell(board, myRandom);
			else undoStack.pop();
			
			if(currentRules.lose(board)) losen = true;
		}
		else
			losen = true;
	}
	
	/**
	 * Método que inicializa la partida
	 */
	public void reset() {
		score = 0;
		board = new Board(size);
		losen = false;
		finished = false;
		
		currentRules.initBoard(board, initCells, myRandom);
		highest = currentRules.getWinValue(board);
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
	 */
	public void finish() {
		finished = true;
	}
	
	/**
	 * Deshace el último movimiento que se ha realizado.
	 * 
	 * @return 	Devuelve true en caso de que se haya podido deshacer el movimiento y false
	 * 			en caso contrario
	 */
	public boolean undo() {
		boolean ok = false;
		
		if(!undoStack.isEmpty()) {
			redoStack.push(getState());
			setState(undoStack.pop());
			ok = true;
		}
		
		return ok;
	}
	
	/**
	 * Rehace el último movimiento que se ha deshecho.
	 * 
	 * @return 	Devuelve true en caso de que se haya podido rehacer el movimiento y false
	 * 			en caso contrario
	 */
	public boolean redo() {
		boolean ok = false;
		
		if(!redoStack.isEmpty()) {
			undoStack.push(getState());
			setState(redoStack.pop());
			ok = true;
		}
		
		return ok;
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
	public void changeGame(GameRules rules, int boardSize, int initialCells, int randomSeed) {
		this.currentRules = rules;
		this.size = boardSize;
		this.initCells = initialCells;
		this.myRandom = new Random(randomSeed);
		
		score = 0;
		board = new Board(size);
		losen = false;
		finished = false;
		
		currentRules.initBoard(board, initCells, myRandom);
		highest = currentRules.getWinValue(board);
		
		// Inicializamos los GameStateStacks
		undoStack = new GameStateStack();
		redoStack = new GameStateStack();
	}
}
