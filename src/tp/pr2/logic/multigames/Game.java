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
	
	// ================================================================================
	// Constructores
	// ================================================================================
	
	public Game(int size, int initCells, Random myRandom) {
		this.size = size;
		this.initCells = initCells;
		this.myRandom = myRandom;
		this.score = 0;
		this.highest = 0;
		this.losen = false;
		this.finished = false;
		
		this.board = new Board(size);
		
		for(int i = 0; i < initCells; i++) {
			int aux = nextValue();
			board.newCell(aux, myRandom);
			
			if(aux > highest)
				highest = aux;
		}
		
		// Inicializamos los GameStateStacks
		undoStack = new GameStateStack();
		redoStack = new GameStateStack();
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
		if(!board.isFull() && !losen && !finished) {
			undoStack.push(getState());
			
			MoveResults results = board.executeMove(dir);
			
			score += results.getPoints();
			highest = results.getMaxToken();
			if(highest >= 2048) losen = true;
			
			board.newCell(nextValue(), myRandom);
		}
		else
			losen = true;
	}
	
	/**
	 * Calcula el nuevo valor a introducir en el tablero
	 * [2 -> 90% |
	 *  4 -> 10%]
	 * 
	 * @return Devuelve el nuevo valor
	 */
	private int nextValue() {
		int bound = 9;
		
		int ret = myRandom.nextInt(bound) + 1;
		
		if(ret > 1)
			ret = 2;
		else
			ret = 4;
		
		return ret;
	}
	
	/**
	 * Método que inicializa la partida
	 */
	public void reset() {
		score = 0;
		highest = 0;
		board = new Board(size);
		losen = false;
		
		for(int i = 0; i < initCells; i++) {
			int aux = nextValue();
			board.newCell(aux, myRandom);
			
			if(aux > highest)
				highest = aux;
		}
	}
	
	public String toString() {
		String ret = "";
		
		ret += board.toString();
		ret += "highest: " + String.valueOf(highest);
		ret += "		score: " + String.valueOf(score) + "\n";
		if(board.isFull()) ret += "Game over\n";
		if(highest >= 2048) ret += "Well done!\n";
		
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
}
