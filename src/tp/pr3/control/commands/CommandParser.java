package tp.pr3.control.commands;

import tp.pr3.control.Controller;
import tp.pr3.exceptions.UnknownCommandException;
import tp.pr3.exceptions.UnknownDirectionException;

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
	 * @throws UnknownCommandException En el caso de que no se pueda parsear como ningún parámentro
	 * 			existente.
	 * @throws UnknownDirectionException en el caso de que, habiendose parseado como un comando MOVE,
	 * 			no se haya encontrado una dirección que coincida.
	 */
	public static Command parseCommand(String[] commandWords, Controller controller) 
			throws UnknownCommandException, UnknownDirectionException {
		Command ret = null;
		
		try {
			for(Command com: availableCommands) {
				Command aux = com.parse(commandWords, controller);
				if(aux != null) {
					ret = aux;
					break;
				}
			}
			
			if(ret == null) {
				throw new UnknownCommandException("Unknown command");
			}
		} catch (UnknownDirectionException e) {
			throw e;
		}
		
		return ret;
	}
	
	/**
	 * @return Devuelve un String con toda la ayuda disponible en el juego.
	 */
	public static String commandHelp() {
		String ret = "";
		
		for(Command com: availableCommands) {
			ret += com.helpText();
		}
		
		return ret;
	}
}
