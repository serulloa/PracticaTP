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
	default boolean lose(Board board) {
		boolean losen = false;
		
		if(!board.canMove(this))
			losen = true;
		
		return losen;
	}
	
	/**
	 * Crea un tablero llamando al constructor de Board.
	 * 
	 * @param size Tamaño del tablero.
	 * @return Devuelve el tablero creado.
	 */
	default Board createBoard(int size) {
		return new Board(size);
	}
	
	/**
	 * Elige una posición libre de board e invoca el método addNewCellAt() para añadir una 
	 * celda en esa posición.
	 * 
	 * @param board Tablero donde se quiere colocar la nueva celda.
	 * @param rand Semilla de aleatoriedad.
	 */
	default void addNewCell(Board board, Random rand) {
		Position[] positions = new Position[board.getBoardSize()*board.getBoardSize()];
		int cont = board.emptyCells(positions);
		int index = rand.nextInt(cont);
		Position pos = positions[index];
		
		addNewCellAt(board, pos, rand);
	}
	
	/**
	 * Inicializa board eligiendo numCells posiciones libres, e invoca el método 
	 * addNewCellAt() para añadir nuevas celdas en esas posiciones.
	 * 
	 * @param board Tablero a inicializar.
	 * @param numCells Número de celdas que tendrán un valor inicial.
	 * @param rand Semilla de aleatoriedad.
	 */
	default void initBoard(Board board, int numCells, Random rand) {		
		int cont = 0;
		Position[] positions = new Position[numCells];
		
		while(cont < numCells) {
			int row = rand.nextInt(board.getBoardSize());
			int col = rand.nextInt(board.getBoardSize());
			boolean ok = true;
			Position pos = new Position(row, col);
			
			for(int i = 0; i < cont; i++)
				if(pos.equals(positions[i])) ok = false;
			
			if(ok) {
				positions[cont] = pos;
				cont++;
			}
		}
		
		for(Position pos : positions)
			addNewCellAt(board, pos, rand);
	}
}
