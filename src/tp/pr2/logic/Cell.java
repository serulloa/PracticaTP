package tp.pr2.logic;

import tp.pr2.logic.multigames.GameRules;

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
	 * Comprueba si las dos celdas pueden fusionarse y, en caso positivo, las fusiona
	 * 
	 * @param neighbour Es la celda vecina
	 * @return True si las dos celdas pueden fusionarse, false en caso contrario
	 */
	public int doMerge(Cell neighbour, GameRules rules) {		
		return rules.merge(this, neighbour);
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
