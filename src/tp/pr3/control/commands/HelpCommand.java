package tp.pr3.control.commands;

import tp.pr3.logic.multigames.Game;

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
	public boolean execute(Game game) {
		System.out.println(CommandParser.commandHelp());
		System.out.println();
		
		return true;
	}
}
