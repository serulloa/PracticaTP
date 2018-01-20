package tp.pr3.control.commands;

import tp.pr3.control.Controller;
import tp.pr3.logic.multigames.Game;

public class ResetCommand extends NoParamsCommand {

	// ================================================================================
	// Constructores
	// ================================================================================
	
	public ResetCommand() {
		super("reset", "restart a new game.\n");
	}
	
	// ================================================================================
	// Métodos
	// ================================================================================	

	@Override
	public void execute(Game game, Controller controller) {
		game.reset();
		controller.reset();
	}
}
