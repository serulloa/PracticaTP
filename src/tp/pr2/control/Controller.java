package tp.pr2.control;

import java.util.Scanner;

import tp.pr2.control.commands.Command;
import tp.pr2.control.commands.CommandParser;
import tp.pr2.logic.multigames.Game;

/**
 * @author Sergio Ulloa
 */
public class Controller {
	
	// ================================================================================
	// Atributos
	// ================================================================================
	
	private Game game; 				// Partida
	private Scanner in; 			// Para leer de la consola las órdenes del usuario
	private boolean finished; 		// Indica si se ha ejecutado el comando exit
	private boolean printGameState;	// Indica si se debe imprimir el tablero en la sig interacción
	
	// ================================================================================
	// Constructores
	// ================================================================================
	
	public Controller(Game game, Scanner in) {
		this.game = game;
		this.in = in;
		this.finished = false;
		this.printGameState = true;
	}
	
	// ================================================================================
	// Métodos
	// ================================================================================
	
	/**
	 * Método que controla el funcionamiento del juego según el comando introducido por
	 * el usuario. En caso de que se introduzca un comando erróneo, se mostrará un
	 * error por pantalla
	 */
	public void run() {
		Command command;
		
		while(!finished) {
			if(printGameState)
				System.out.println(game.toString());
			printGameState = true;
			
			if(game.isLosen()) System.out.println("Game is finished, type 'RESET' to play another game");
			System.out.print("Command > ");
			
			command = parseIn();
			if(command != null) command.execute(game, this);
		}
	}
	
	/**
	 * Método que sirve para parsear la entrada, la convierte a mayúsculas y crea
	 * un array de strings con cada una de las palabras
	 * 
	 * @return El array de strings previamente mencionado
	 */
	private Command parseIn() {
		String[] commandWords = in.nextLine().toUpperCase().split(" ", -1);
		
		Command ret = CommandParser.parseCommand(commandWords, this);
		
		return ret;
	}
	
	/**
	 * Método que al invocarse pone a true la variable finished, la cual termina la partida
	 * y cierra el programa.
	 */
	public void exit() {
		System.out.println("Game over");
		finished = true;
	}
	
	/**
	 * Obtiene la ayuda del juego desde las clases Command y luego la imprime en pantalla.
	 */
	public void help() {
		System.out.println(CommandParser.commandHelp());
		System.out.println();
	}
	
	/**
	 * Imprime un mensaje que indica el reinicio de la partida y establece el atributo
	 * finished a false, para indicar que la partida ya no está terminada.
	 */
	public void reset() {
		System.out.println("Game restarted!");
		finished = false;
	}
	
	/**
	 * Establece printGameState a false para que no se imprima por pantalla el tablero
	 * por segunda vez. Suele invocarse al haberse producido un error en el input.
	 */
	public void setNoPrintGameState() {
		this.printGameState = false;
	}
	
	/**
	 * Método para la impresión de errores por pantalla.
	 * 
	 * @param error String con el error a imprimir.
	 */
	public void printError(String error) {
		System.err.println(error);
	}
	
	/**
	 * Método que pide el tamaño del tablero cuando se cambia de tipo de juego.
	 * 
	 * @return Un entero con el tamaño.
	 */
	public int askBoardSize() {
		boolean ok = false;
		String cadena = null;
		int size = 4;
		
		while(!ok) {
			System.out.print("Please enter the size of the board: ");
			cadena = in.nextLine();
			String words[] = cadena.split(" ");
			
			if(cadena.isEmpty()) System.out.println("Using the default size of the board: 4");
			else size = Integer.valueOf(words[0]);
			
			if(words.length > 1)
				System.err.println("Please provide a single positive integer or press return.");
			else if(size <= 0)
				System.err.println("The size of the board must be positive.");
			else
				ok = true;
		}
		
		return size;
	}
	
	/**
	 * Método que pide el número de celdas iniciales al cambiar de juego.
	 * 
	 * @return Un entero con el número de celdas iniciales.
	 */
	public int askIniCells(int size) {
		boolean ok = false;
		String line = null;
		int cells = 2;
		
		while(!ok) {
			System.out.print("Please enter the number of initial cells: ");
			line = in.nextLine();
			String words[] = line.split(" ");
			
			if(line.isEmpty()) System.out.println("Using the default number of initial cells: 2");
			else cells = Integer.valueOf(words[0]);
			
			if(words.length > 1)
				System.err.println("Please provide a single positive integer or press return.");
			else if(cells <= 0)
				System.err.println("The number of initial dells must be positive.");
			else if(cells > size*size)
				System.err.println("The number of initial cells must be less than the number of cells on the board");
			else
				ok = true;
		}
		
		return cells;
	}
	
	/**
	 * Método que pide la semilla para el generador aleatorio de valores al cambiar de juego.
	 * 
	 * @return Un entero con la semilla.
	 */
	public int askSeed() {
		boolean ok = false;
		String line = null;
		int seed = 1000;
		
		while(!ok) {
			System.out.print("Please enter the seed for the pseudo-random number generator: ");
			line = in.nextLine();
			String words[] = line.split(" ");
			
			if(line.isEmpty()) System.out.println("Using the default seed for the pseudo-random number generator: 1000");
			else seed = Integer.valueOf(words[0]);
			
			if(words.length > 1)
				System.err.println("Please provide a single positive integer or press return.");
			else if(seed <= 0)
				System.err.println("The size of the board must be positive.");
			else
				ok = true;
		}
		
		return seed;
	}
}
