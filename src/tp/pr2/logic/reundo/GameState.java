package tp.pr2.logic.reundo;

public class GameState {
	
	// ================================================================================
	// Atributos
	// ================================================================================
	
	private int[][] boardState;	// Estado del tablero
	private int score;			// Puntuación
	
	// ================================================================================
	// Constructores
	// ================================================================================
	
	public GameState(int[][] boardState, int score) {
		this.boardState = boardState;
		this.score = score;
	}
	
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
