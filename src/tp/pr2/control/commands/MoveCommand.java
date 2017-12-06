package tp.pr2.control.commands;

import tp.pr2.control.Controller;
import tp.pr2.logic.Direction;
import tp.pr2.logic.multigames.Game;

/**
 * @author Sergio Ulloa
 */
public class MoveCommand extends Command {

	// ================================================================================
	// Constructores
	// ================================================================================
	
	public MoveCommand() {
		super("move <direction>", "execute a move in one of the directions: up, down, left, right.");
	}
	
	// ================================================================================
	// Métodos
	// ================================================================================	

	@Override
	public void execute(Game game, Controller controller) {
		// TODO Auto-generated method stub

	}

	@Override
	public Command parse(String[] commandWords, Controller controller) {
		// TODO Auto-generated method stub
		return null;
	}

}
