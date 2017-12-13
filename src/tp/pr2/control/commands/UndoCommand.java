package tp.pr2.control.commands;

import tp.pr2.control.Controller;
import tp.pr2.logic.multigames.Game;

public class UndoCommand extends NoParamsCommand {
	
	// ================================================================================
	// Constructores
	// ================================================================================
	
	public UndoCommand() {
		super("undo", "allows to undo the latest move.\n");
	}

	// ================================================================================
	// Métodos
	// ================================================================================
	
	@Override
	public void execute(Game game, Controller controller) {
		// TODO Auto-generated method stub

	}

}
