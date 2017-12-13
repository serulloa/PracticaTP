package tp.pr2.logic.multigames;

import java.util.Random;

import tp.pr2.logic.Board;
import tp.pr2.logic.Direction;
import tp.pr2.logic.MoveResults;

/**
 * @author Sergio Ulloa
 */
public class Game {
	
	// ================================================================================
	// Atributos
	// ================================================================================
	
	private Board board; 		// Tablero
	private int size; 			// Dimensión del tablero
	private int initCells; 		// Número de baldosas no nulas iniciales
	private Random myRandom; 	// Comportamiento aleatorio del juego
	private int score;			// Puntuación del usuario
	private int highest;		// Máximo token conseguido
	private boolean losen;		// Partida perdida
	private boolean finished;	// Partida acabada
	
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
	 */
	public void undo() {
		
	}
	
	/**
	 * Rehace el último movimiento que se ha deshecho.
	 */
	public void redo() {
		
	}
}
