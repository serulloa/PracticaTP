package tp.pr3.control.commands;

import tp.pr3.exceptions.EmptyStackException;
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
	public boolean execute(Game game) throws EmptyStackException {
		try {
			game.redo();
			return true;
		} catch (EmptyStackException e) {
			throw e;
		}
	}

}
