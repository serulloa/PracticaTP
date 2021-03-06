package tp.pr2.control.commands;

import tp.pr2.control.Controller;

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
	protected Command parse(String[] commandWords, Controller controller) {
		Command ret = null;
		
		if(commandWords.length == 1) {
			switch (commandWords[0]) {
				case "EXIT":
				{
					ret = new ExitCommand();
					break;
				}
				case "HELP":
				{
					ret = new HelpCommand();
					break;
				}
				case "RESET":
				{
					ret = new ResetCommand();
					break;
				}
				case "UNDO":
				{
					ret = new UndoCommand();
					break;
				}
				case "REDO":
				{
					ret = new RedoCommand();
					break;
				}
				default:
					controller.setNoPrintGameState();
			}
		}
		
		return ret;
	}

}
