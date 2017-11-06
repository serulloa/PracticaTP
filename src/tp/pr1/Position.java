/**
 * 
 */
package tp.pr1;

/**
 * @author Sergio Ulloa
 *
 */
public class Position {

	// ================================================================================
	// Atributos
	// ================================================================================

	private int row; // Fila
	private int column; // Columna

	// ================================================================================
	// Constructores
	// ================================================================================

	public Position(int r, int c) {
		this.row = r;
		this.column = c;
	}

	// ================================================================================
	// Métodos
	// ================================================================================
	
	
	/**
	 * Dependiendo del valor de direction, x e y toman determinados valores que luego
	 * se suman al valor de Position actual para acceder a la siguiente posición. 
	 * 
	 * @param direction es un enumerado que representa la dirección
	 * @param n es el tamaño del tablero
	 * @return la posición vecina según la dirección dada, si hay algún error devuelve
	 * (-1, -1)
	 * 
	 */
	public Position neighbour(Direction direction, int n) {
		int x = 0;
		int y = 0;
		int auxRow, auxColumn;
		
		switch (direction) {
			case UP:
			{
				y = 1;
			}
			case DOWN:
			{
				y = -1;
			}
			case LEFT:
			{
				x = -1;
			}
			case RIGHT:
			{
				x = 1;
			}
		}
		
		auxRow = row + y;
		auxColumn = column + x;
		
		if(auxRow >= n || auxColumn >= n || auxRow < 0 || auxColumn < 0) {
			auxRow = -1;
			auxColumn = -1;
		}
		
		Position ret = new Position(auxRow, auxColumn);

		return ret;
	}

	// ================================================================================
	// Getters y Setters
	// ================================================================================

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public void setColumn(int column) {
		this.column = column;
	}
}
