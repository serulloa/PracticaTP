package tp.pr3.control.commands;

import java.util.Scanner;

import tp.pr3.exceptions.UnknownDirectionException;
import tp.pr3.logic.Direction;
import tp.pr3.logic.multigames.Game;

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
	public boolean execute(Game game) {
		boolean ret = !game.move(dir);
		return ret;
	}

	@Override
	protected Command parse(String[] commandWords, Scanner in) throws UnknownDirectionException {
		Command ret = null;
		
		if(commandWords[0].toUpperCase().equals("MOVE") && commandWords.length == 2) {
			switch (commandWords[1].toUpperCase()) {
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
				case "":
				{
					throw new UnknownDirectionException("You must specify a direction");
				}
				default:
					throw new UnknownDirectionException("Unknown direction");
			}
		}
		
		else if(commandWords[0].toUpperCase().equals("MOVE") && commandWords.length == 1) {
			throw new UnknownDirectionException("You must specify a direction");
		}
		
		return ret;
	}

}
