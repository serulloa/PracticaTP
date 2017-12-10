package tp.pr2.control.commands;

import tp.pr2.control.Controller;
import tp.pr2.logic.multigames.Game;

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
		controller.setNoPrintGameState();
	}
}
