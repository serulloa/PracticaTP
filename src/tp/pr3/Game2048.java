package tp.pr3;

import java.util.Scanner;

import tp.pr3.control.Controller;
import tp.pr3.logic.multigames.Game;
import tp.pr3.logic.multigames.GameType;

/**
 * @author Sergio Ulloa
 */
public class Game2048 {

	public static void main(String[] args) {
		try {
			if(args.length >= 2) {
				Game game;
				
				if(args.length == 3) {
					game = new Game(GameType.ORIG, Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
				}
				else {
					game = new Game(GameType.ORIG, Integer.parseInt(args[0]), Integer.parseInt(args[1]), 1000);
				}
				
				Controller control = new Controller(game, new Scanner(System.in));
				control.run();
			}
			else
				System.err.println("ERROR: Board size and initial cells are required arguments.");
		} catch (NumberFormatException e) {
			System.err.println("ERROR: Please provide a single positive integer for board size and initial cells.");
		}
	}

}
