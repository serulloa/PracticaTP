package tp.pr2.control.commands;

import tp.pr2.control.Controller;
import tp.pr2.logic.Direction;
import tp.pr2.logic.multigames.Game;

/**
 * @author Sergio Ulloa
 */
public class MoveCommand extends Command {
	
	// ================================================================================
	// Atributos
	// ================================================================================
	
	private Direction dir;

	// ================================================================================
	// Constructores
	// ================================================================================
	
	public MoveCommand() {
		super("move <direction>", "execute a move in one of the directions: up, down, left, right.");
	}
	
	public MoveCommand(Direction dir) {
		super("move " + dir, "execute a move in one of the directions: up, down, left, right.");
		this.dir = dir;
	}
	
	// ================================================================================
	// Métodos
	// ================================================================================	

	@Override
	public void execute(Game game, Controller controller) {
		game.move(dir);
	}

	@Override
	protected Command parse(String[] commandWords, Controller controller) {
		Command ret = null;
		
		if(commandWords[0].equals("MOVE") && commandWords.length == 2) {
			switch (commandWords[1]) {
				case "UP":
				{
					ret = new MoveCommand(Direction.UP);
					break;
				}
				case "DOWN":
				{
					ret = new MoveCommand(Direction.DOWN);
					break;
				}
				case "LEFT":
				{
					ret = new MoveCommand(Direction.LEFT);
					break;
				}
				case "RIGHT":
				{
					ret = new MoveCommand(Direction.RIGHT);
					break;
				}
				default:
					controller.setNoPrintGameState();
			}
		}
		
		return ret;
	}

}
