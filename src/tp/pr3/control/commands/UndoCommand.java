package tp.pr3.control.commands;

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
	public boolean execute(Game game) throws EmptyStackException {
		try {
			game.undo();
			return true;
		} catch (EmptyStackException e) {
			throw e;
		}
	}

}
