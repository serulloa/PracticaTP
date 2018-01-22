package tp.pr3.control.commands;

import java.util.Scanner;

import tp.pr3.exceptions.UnknownGameException;
import tp.pr3.logic.multigames.Game;
import tp.pr3.logic.multigames.GameType;

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
		super("play <game>", GameType.externaliseAll() + ", allows the user to switch games.\n");
	}
	
	public PlayCommand(GameType gameType) {
		super("play <game>", GameType.externaliseAll() + ", allows the user to switch games.\n");
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
	public boolean execute(Game game) {		
		game.changeGame(gameType, boardSize, initialCells, randomSeed);
		return true;
	}

	@Override
	protected Command parse(String[] commandWords, Scanner in) throws UnknownGameException {
		Command ret = null;
		
		if(commandWords[0].toUpperCase().equals("PLAY") && commandWords.length == 2) {
			GameType gameType = GameType.parse(commandWords[1].toLowerCase());
			
			if(gameType != null) {
				System.out.println("*** You have chosen to play the game: " + gameType.toString() + " ***");
				int size = askBoardSize(in);
				int cells = askIniCells(size, in);
				int rand = askSeed(in);
				
				ret = new PlayCommand(gameType, size, cells, rand);
			}
		}
		
		else if(commandWords[0].toUpperCase().equals("PLAY") && commandWords.length == 1) {
			throw new UnknownGameException("You must specify a game type");
		}
		
		return ret;
	}
	
	/**
	 * Método que pide el tamaño del tablero cuando se cambia de tipo de juego.
	 * 
	 * @return Un entero con el tamaño.
	 */
	public int askBoardSize(Scanner in) {
		boolean ok = false;
		String cadena = null;
		int size = 4;
		
		while(!ok) {			
			try {
				System.out.print("Please enter the size of the board: ");
				cadena = in.nextLine();
				String words[] = cadena.split(" ");
				
				if(cadena.isEmpty()) System.out.println("Using the default size of the board: 4");
				else size = Integer.valueOf(words[0]);
				
				if(words.length > 1)
					System.err.println("Please provide a single positive integer or press return.");
				else if(size <= 0)
					System.err.println("The size of the board must be positive.");
				else
					ok = true;
			} catch (NumberFormatException e) {
				System.err.println("Please provide a single positive or press return.");
			}
		}
		
		return size;
	}
	
	/**
	 * Método que pide el número de celdas iniciales al cambiar de juego.
	 * 
	 * @return Un entero con el número de celdas iniciales.
	 */
	public int askIniCells(int size, Scanner in) {
		boolean ok = false;
		String line = null;
		int cells = 2;
		
		while(!ok) {
			try {
				System.out.print("Please enter the number of initial cells: ");
				line = in.nextLine();
				String words[] = line.split(" ");
				
				if(line.isEmpty()) System.out.println("Using the default number of initial cells: 2");
				else cells = Integer.valueOf(words[0]);
				
				if(words.length > 1)
					System.err.println("Please provide a single positive integer or press return.");
				else if(cells <= 0)
					System.err.println("The number of initial dells must be positive.");
				else if(cells > size*size)
					System.err.println("The number of initial cells must be less than the number of cells on the board");
				else
					ok = true;
			} catch (NumberFormatException e) {
				System.err.println("Please provide a single positive or press return.");
			}
		}
		
		return cells;
	}
	
	/**
	 * Método que pide la semilla para el generador aleatorio de valores al cambiar de juego.
	 * 
	 * @return Un entero con la semilla.
	 */
	public int askSeed(Scanner in) {
		boolean ok = false;
		String line = null;
		int seed = 1000;
		
		while(!ok) {
			try {
				System.out.print("Please enter the seed for the pseudo-random number generator: ");
				line = in.nextLine();
				String words[] = line.split(" ");
				
				if(line.isEmpty()) System.out.println("Using the default seed for the pseudo-random number generator: 1000");
				else seed = Integer.valueOf(words[0]);
				
				if(words.length > 1)
					System.err.println("Please provide a single positive integer or press return.");
				else if(seed <= 0)
					System.err.println("The size of the board must be positive.");
				else
					ok = true;
			} catch (NumberFormatException e) {
				System.err.println("Please provide a single positive or press return.");
			}
		}
		
		return seed;
	}

}
