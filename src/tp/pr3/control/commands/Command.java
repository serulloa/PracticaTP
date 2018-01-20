package tp.pr3.control.commands;

import tp.pr3.control.Controller;
import tp.pr3.exceptions.EmptyStackException;
import tp.pr3.exceptions.UnknownDirectionException;
import tp.pr3.logic.multigames.Game;


/**
 * @author Sergio Ulloa
 */
public abstract class Command {
	
	// ================================================================================
	// Atributos
	// ================================================================================
	
	private String helpText;
	private String commandText;
	protected final String commandName;
	
	// ================================================================================
	// Constructores
	// ================================================================================	

	public Command(String commandInfo, String helpInfo) {
		commandText = commandInfo;
		helpText = helpInfo;
		String[] commandInfoWords = commandText.split("\\s+");
		commandName = commandInfoWords[0];
	}
	
	// ================================================================================
	// Métodos
	// ================================================================================
	
	public abstract void execute(Game game, Controller controller) throws EmptyStackException;
	
	protected abstract Command parse(String[] commandWords, Controller controller) 
			throws UnknownDirectionException;
	
	protected String helpText() {
		return " " + commandText + ": " + helpText;
	}
}