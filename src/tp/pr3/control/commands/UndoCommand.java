package tp.pr3.control.commands;

import tp.pr3.control.Controller;
import tp.pr3.exceptions.EmptyStackException;
import tp.pr3.logic.multigames.Game;

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
	public void execute(Game game, Controller controller) throws EmptyStackException {
		try {
			game.undo();
		} catch (EmptyStackException e) {
			throw e;
		}
	}

}
