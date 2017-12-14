package tp.pr2.logic.multigames;

import java.util.Random;

import tp.pr2.logic.Board;
import tp.pr2.logic.Cell;
import tp.pr2.logic.Position;

/**
 * @author Serigo Ulloa López
 */
public interface GameRules {
	
	// ================================================================================
	// Métodos
	// ================================================================================

	/**
	 * Incorpora una nueva celda con valor aleatorio en la posición pos del tablero board.
	 * 
	 * @param board	Tablero donde se incorporará la celda
	 * @param pos	Posicion del tablero donde se incorporará
	 * @param rand	Semilla de aleatoriedad
	 */
	void addNewCellAt(Board board, Position pos, Random rand);
	
	/**
	 * Fusiona dos celdas y devuelve el número de puntos obtenidos.
	 * 
	 * @param self	Una de las celdas.
	 * @param other	Otra de las celdas.
	 * @return	Devuelve los puntos obtenidos al fusionar las celdas.
	 */
	int merge(Cell self, Cell other);
	
	/**
	 * Devuelve el mejor valor de tablero según las reglas del juego, comprobando si es un
	 * valor ganador y si se ha ganado el juego.
	 * 
	 * @param board Tablero que se quiere comprobar.
	 * @return Devuelve el mejor valor disponible.
	 */
	int getWinValue(Board board);
	
	/**
	 * Devuelve si el juego se ha ganado o no.
	 * 
	 * @param board Tablero que se quiere comprobar.
	 * @return True si se ha ganado, false en caso contrario.
	 */
	boolean win(Board board);
	
	/**
	 * Devuelve si el juego se ha perdido o no.
	 * 
	 * @param board Tablero que se quiere comprobar.
	 * @return True si se ha perdido, false en caso contrario.
	 */
	boolean lose(Board board);
}
