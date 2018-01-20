package tp.pr3.control.commands;

import tp.pr3.control.Controller;
import tp.pr3.logic.multigames.Game;

public class RedoCommand extends NoParamsCommand {
	
	// ================================================================================
	// Constructores
	// ================================================================================
	
	public RedoCommand() {
		super("redo", "allows to redo the latest undone movement.\n");
	}
	
	// ================================================================================
	// Métodos
	// ================================================================================

	@Override
	public void execute(Game game, Controller controller) {
		boolean ok = game.redo();
		if(!ok) {
			controller.setNoPrintGameState();
			controller.printError("Nothing to redo");
		}
	}

}
