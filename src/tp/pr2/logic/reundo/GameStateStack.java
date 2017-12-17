package tp.pr2.logic.reundo;

/**
 * @author Sergio Ulloa López
 */
public class GameStateStack {
	
	// ================================================================================
	// Atributos
	// ================================================================================
	
	private static final int CAPACITY = 20;	// Constante con la capacidad máxima
	private GameState[] buffer;				// Array con los estados de partida
	private int index;						// Apunta siempre al último elemento
	
	// ================================================================================
	// Constructores
	// ================================================================================
	
	public GameStateStack() {
		this.buffer = new GameState[CAPACITY+1];
		this.index = 0;
	}
	
	// ================================================================================
	// Métodos
	// ================================================================================
	
	/**
	 * Obtiene el último elemento que se ha introducido y luego reduce en 1 el índice para
	 * que se mantenga actualizado.
	 * 
	 * @return Devuelve el último elemento del array.
	 */
	public GameState pop() {
		GameState ret = buffer[index];
		index--;
		return ret;
	}
	
	/**
	 * Introduce un nuevo elemento en buffer y luego comprueba si hay más de 20 movimientos
	 * dentro de él. En caso positivo, llama a la función deleteFirst().
	 * 
	 * @param state Estado de la partida que se quiere introducir en buffer.
	 */
	public void push(GameState state) {
		index++;
		buffer[index] = state;
		
		if(index >= CAPACITY) 
			deleteFirst();
	}
	
	/**
	 * Comprueba si buffer tiene o no elementos.
	 * 
	 * @return 	Devuelve un booleano a true en caso de que NO haya elementos y a false en
	 * 			caso contrario.
	 */
	public boolean isEmpty() {
		boolean ret = false;
		if(index == 0) ret = true;
		return ret;
	}
	
	/**
	 * Reordena el array buffer machacando el valor existente en buffer[0]
	 */
	private void deleteFirst() {
		for(int i = 0; i < CAPACITY; i++) {
			buffer[i] = buffer[i+1];
		}
		index--;
	}
}
