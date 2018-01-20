package tp.pr3.control.commands;

import tp.pr3.control.Controller;
import tp.pr3.logic.multigames.Game;

public class HelpCommand extends NoParamsCommand {

	// ================================================================================
	// Constructores
	// ================================================================================
	
	public HelpCommand() {
		super("help", "print this help message.\n");
	}
	
	// ================================================================================
	// Métodos
	// ================================================================================	

	@Override
	public void execute(Game game, Controller controller) {
		controller.help();
	}
}
