package tp.pr3.control;

import java.util.Scanner;

import tp.pr3.control.commands.Command;
import tp.pr3.control.commands.CommandParser;
import tp.pr3.exceptions.ExitCommandException;
import tp.pr3.exceptions.ResetCommandException;
import tp.pr3.logic.multigames.Game;

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
	
	// ================================================================================
	// Constructores
	// ================================================================================
	
	public Controller(Game game, Scanner in) {
		this.game = game;
		this.in = in;
		this.finished = false;
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
		boolean print = true;
		Command command;
		
		while(!finished) {
			if(print) System.out.println(game.toString());
			print = true;
			
			if(game.isLosen()) System.out.println("Game is finished, type 'RESET' to play another game");
			System.out.print("Command > ");
			
			try {
				
				command = parseIn();
				if(command != null) print = command.execute(game);
				
			} catch (ExitCommandException e) {
				System.err.println(e.getMessage());
				this.finished = true;
				print = false;
			} catch (ResetCommandException e) {
				System.err.println(e.getMessage());
			} catch (java.io.FileNotFoundException e) {
				System.err.println("File not found");
			} catch (Exception e) {
				System.err.println(e.getMessage());
				print = false;
			}
		}
	}
	
	/**
	 * Método que sirve para parsear la entrada, la convierte a mayúsculas y crea
	 * un array de strings con cada una de las palabras
	 * 
	 * @return El array de strings previamente mencionado
	 * @throws Exception En caso de que haya errores en el parseo del comando
	 */
	private Command parseIn() throws Exception {
		String[] commandWords = in.nextLine().split(" ", -1);
		Command ret = null;
		
		try {
			ret = CommandParser.parseCommand(commandWords, in);
		} catch (Exception e) {
			throw e;
		}
		
		return ret;
	}
	
}
