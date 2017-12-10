package tp.pr2.control.commands;

import tp.pr2.control.Controller;

/**
 * @author Sergio Ulloa
 */
public class CommandParser {
	
	// ================================================================================
	// Atributos
	// ================================================================================
	
	private static Command[] availableCommands = { new HelpCommand(), new ResetCommand(),
			new ExitCommand(), new MoveCommand() };
	
	// ================================================================================
	// Métodos
	// ================================================================================
	
	
	/**
	 *  Este método le pasa,
	 *	a su vez, el input a un objeto comando de cada una de las clases concretas (que, 
	 *	son subclases de Command) para averiguar cuál de ellos lo acepta como correcto.
	 * 
	 * @param commandWords Palabras que conforman el comando
	 * @param controller Controlador desde el que se invoca
	 * @return En caso de éxitoevuelve un objeto de la clase comando a la que corresponde la entrada
	 * 			y, en caso de fallo, devuelve null.
	 */
	public static Command parseCommand(String[] commandWords, Controller controller) {
		Command ret = null;
		
		for(Command com: availableCommands) {
			Command aux = com.parse(commandWords, controller);
			if(aux != null)
				ret = aux;
		}
		
		if(ret == null) controller.setNoPrintGameState();
		
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
