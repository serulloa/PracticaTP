package tp.pr2.logic;

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
	private int maxToken; 	// para llevar el valor más alto tras el movimiento
	
	// ================================================================================
	// Constructores
	// ================================================================================
	
	public MoveResults(boolean moved, int points, int maxToken) {
		this.moved = moved;
		this.points = points;
		this.maxToken = maxToken;
	}
	
	// ================================================================================
	// Getters y Setters
	// ================================================================================
	
	public boolean isMoved() {
		return moved;
	}
	
	public void setMoved(boolean moved) {
		this.moved = moved;
	}
	
	public int getPoints() {
		return points;
	}
	
	public void setPoints(int points) {
		this.points = points;
	}
	
	public int getMaxToken() {
		return maxToken;
	}
	
	public void setMaxToken(int maxToken) {
		this.maxToken = maxToken;
	}
}
