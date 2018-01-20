package tp.pr2.control.commands;

import tp.pr2.control.Controller;
import tp.pr2.logic.multigames.Game;
import tp.pr2.logic.multigames.GameRules;
import tp.pr2.logic.multigames.GameType;
import tp.pr2.logic.multigames.Rules2048;
import tp.pr2.logic.multigames.RulesFib;
import tp.pr2.logic.multigames.RulesInverse;

/**
 * @author Sergio Ulloa
 */
public class PlayCommand extends Command {
	
	// ================================================================================
	// Atributos
	// ================================================================================
	
	protected int randomSeed, boardSize, initialCells;
	protected GameType gameType;
	
	// ================================================================================
	// Constructores
	// ================================================================================
	
	public PlayCommand() {
		super("play <game>", "change game to one of these: original, fib, inverse");
	}
	
	public PlayCommand(GameType gameType) {
		super("play <game>", "change game to one of these: original, fib, inverse");
		this.gameType = gameType;
		this.boardSize = 4;
		this.initialCells = 2;
		this.randomSeed = 1000;
	}
	
	public PlayCommand(GameType gameType, int boardSize, int initialCells, int randomSeed) {
		super("play <game>", "change game to one of these: original, fib, inverse");
		this.gameType = gameType;
		this.boardSize = boardSize;
		this.initialCells = initialCells;
		this.randomSeed = randomSeed;
	}
	
	// ================================================================================
	// Métodos
	// ================================================================================

	@Override
	public void execute(Game game, Controller controller) {
		GameRules rules;
		
		switch(gameType) {
			case ORIG:
			{
				rules = new Rules2048();
				break;
			}
			case FIB:
			{
				rules = new RulesFib();
				break;
			}
			case INV:
			{
				rules = new RulesInverse();
				break;
			}
			default:
				rules = new Rules2048();
		}
		
//		game = new Game(rules, boardSize, initialCells, randomSeed);
		game.changeGame(rules, boardSize, initialCells, randomSeed);
	}

	@Override
	protected Command parse(String[] commandWords, Controller controller) {
		Command ret = null;
		
		if(commandWords[0].equals("PLAY") && commandWords.length >= 2) {
			GameType gameType = null;
			
			switch(commandWords[1]) {
				case "ORIGINAL":
				{
					gameType = GameType.ORIG;
					break;
				}
				case "FIB":
				{
					gameType = GameType.FIB;
					break;
				}
				case "INVERSE":
				{
					gameType = GameType.INV;
					break;
				}
			}
			
			if(gameType != null) {				
				int size = controller.askBoardSize();
				int cells = controller.askIniCells(size);
				int rand = controller.askSeed();
				
				ret = new PlayCommand(gameType, size, cells, rand);
			}
		}
		
		return ret;
	}

}
