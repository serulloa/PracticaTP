package tp.pr2.control.commands;

import tp.pr2.control.Controller;
import tp.pr2.logic.multigames.Game;

public class ExitCommand extends NoParamsCommand {

	// ================================================================================
	// Constructores
	// ================================================================================
	
	public ExitCommand() {
		super("exit", "terminate the program.\n");
	}
	
	// ================================================================================
	// Métodos
	// ================================================================================	

	@Override
	public void execute(Game game, Controller controller) {
		game.finish();
		controller.exit();
	}
}
