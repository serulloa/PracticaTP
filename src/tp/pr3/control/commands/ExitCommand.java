package tp.pr3.control.commands;

import tp.pr3.exceptions.ExitCommandException;
import tp.pr3.logic.multigames.Game;

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
	public boolean execute(Game game) throws ExitCommandException {
		game.finish();
		throw new ExitCommandException("Game over");
	}

}
