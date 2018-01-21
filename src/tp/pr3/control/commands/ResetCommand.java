package tp.pr3.control.commands;

import tp.pr3.exceptions.ResetCommandException;
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
	public boolean execute(Game game) throws ResetCommandException {
		game.reset();
		throw new ResetCommandException("Game restarted!");
	}
}
