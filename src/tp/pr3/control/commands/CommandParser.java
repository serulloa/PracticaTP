package tp.pr3.control.commands;

import java.util.Scanner;

import tp.pr3.exceptions.UnknownCommandException;
import tp.pr3.exceptions.UnknownDirectionException;
import tp.pr3.exceptions.UnknownGameException;

/**
 * @author Sergio Ulloa
 */
public class CommandParser {
	
	// ================================================================================
	// Atributos
	// ================================================================================
	
	private static Command[] availableCommands = { new HelpCommand(), new ResetCommand(),
			new ExitCommand(), new MoveCommand(), new UndoCommand(), new RedoCommand(),
			new PlayCommand()};
	
	// ================================================================================
	// Métodos
	// ================================================================================
	
	
	/**
	 *  Este método le pasa, a su vez, el input a un objeto comando de cada una de las 
	 *  clases concretas (que, son subclases de Command) para averiguar cuál de ellos lo
	 *  acepta como correcto.
	 * 
	 * @param commandWords Palabras que conforman el comando
	 * @param controller Controlador desde el que se invoca
	 * @return En caso de éxitoevuelve un objeto de la clase comando a la que corresponde la entrada
	 * 			y, en caso de fallo, devuelve null.
	 * @throws Exception En caso de que se produzca un error en el parseo de alguno de los comandos
	 */
	public static Command parseCommand(String[] commandWords, Scanner in) 
			throws Exception {
		Command ret = null;
		
		try {
			for(Command com: availableCommands) {
				Command aux = com.parse(commandWords, in);
				if(aux != null) {
					ret = aux;
					break;
				}
			}
			
			if(ret == null) {
				throw new UnknownCommandException("Unknown command");
			}
		} catch (UnknownDirectionException | UnknownGameException e) {
			throw e;
		}
		
		return ret;
	}
	
	/**
	 * Método que reúne todos los textos de ayuda de los comandos para juntarlos en un
	 * sólo string
	 * 
	 * @return El String con el texto de todos los comandos
	 */
	public static String commandHelp() {
		String ret = "";
		
		for(Command com: availableCommands) {
			ret += com.helpText();
		}
		
		return ret;
	}
}
