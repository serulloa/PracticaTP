package tp.pr1;

/**
 * @author Sergio Ulloa
 */
public class Cell {
	
	// ================================================================================
	// Atributos
	// ================================================================================
	
	private int value;
	
	// ================================================================================
	// Constructores
	// ================================================================================
	
	public Cell(int value) {
		this.value = value;
	}
	
	// ================================================================================
	// Métodos
	// ================================================================================
	
	/**
	 * Comprueba si la celda está vacía o no, es decir, si su valor es igual a 0 o no
	 * 
	 * @return True si está vacía y 0 si no lo está
	 */
	public boolean isEmpty() {
		boolean ret = false;
		
		if(value == 0)
			ret = true;
		
		return ret;
	}
	
	/**
	 * Comprueba si las dos celdas pueden fusionarse
	 * 
	 * @param neighbour Es la celda vecina
	 * @return True si las dos celdas pueden fusionarse, false en caso contrario
	 */
	public boolean doMerge(Cell neighbour) {
		boolean ret = false;
		
		if(neighbour.value == value)
			ret = true;
		
		return ret;
	}

	// ================================================================================
	// Getters y Setters
	// ================================================================================
	
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
