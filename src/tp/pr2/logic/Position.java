package tp.pr2.logic;

/**
 * @author Sergio Ulloa
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
	 * se suman al valor de Position actual para acceder a la siguiente posición
	 * 
	 * @param direction Es un enumerado que representa la dirección
	 * @param n Es el tamaño del tablero
	 * @return La posición vecina según la dirección dada, si hay algún error devuelve
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
				break;
			case DOWN:
			{
				y = -1;
			}
				break;
			case LEFT:
			{
				x = -1;
			}
				break;
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
	
	public boolean equals(Position pos) {
		boolean ok = false;
		
		if(row == pos.row && column == pos.column) ok = true;
		return ok;
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
