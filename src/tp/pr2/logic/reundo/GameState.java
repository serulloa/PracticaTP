package tp.pr2.logic.reundo;

public class GameState {
	
	// ================================================================================
	// Atributos
	// ================================================================================
	
	private int[][] boardState;	// Estado del tablero
	private int score;			// Puntuación
	
	// ================================================================================
	// Métodos
	// ================================================================================
	
	/**
	 * Devuelve el estado actual del tablero.
	 * 
	 * @return int[][] GameState.boardState
	 */
	public int[][] getBoardState() {
		return boardState;
	}
	
	
	/**
	 * Devuelve la puntuación actual de la partida.
	 * 
	 * @return int GameState.score
	 */
	public int getScore() {
		return score;
	}
}
