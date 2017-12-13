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
			else {
				printGameState = false;
				System.err.println("ERROR: Unknown command.");
			}
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
}
