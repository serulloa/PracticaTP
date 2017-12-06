package tp.pr2.control.commands;

import tp.pr2.control.Controller;
import tp.pr2.logic.multigames.Game;

/**
 * @author Sergio Ulloa
 */
public abstract class NoParamsCommand extends Command {
	
	// ================================================================================
	// Constructores
	// ================================================================================	
	
	public NoParamsCommand(String commandInfo, String helpInfo) {
		super(commandInfo, helpInfo);
	}

	// ================================================================================
	// Métodos
	// ================================================================================
	
	@Override
	public Command parse(String[] commandWords, Controller controller) {
		// TODO Auto-generated method stub
		
		
		return null;
	}

}
