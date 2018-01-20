package tp.pr3.logic;

/**
 * @author Sergio Ulloa
 */
public class MoveResults {
	
	// ================================================================================
	// Atributos
	// ================================================================================
	
	private boolean moved; 	// para identificar si ha habido movimiento
	private int points; 	// para almacenar el número de puntos
							// obtenidos en el movimiento
	
	// ================================================================================
	// Constructores
	// ================================================================================
	
	public MoveResults(boolean moved, int points) {
		this.moved = moved;
		this.points = points;
	}
	
	// ================================================================================
	// Getters y Setters
	// ================================================================================
	
	public boolean isMoved() {
		return moved;
	}
	
	public int getPoints() {
		return points;
	}
}
