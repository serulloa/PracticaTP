package tp.pr2;

import java.util.Scanner;

import tp.pr2.control.Controller;
import tp.pr2.logic.multigames.Game;
import tp.pr2.logic.multigames.Rules2048;

/**
 * @author Sergio Ulloa
 */
public class Game2048 {

	public static void main(String[] args) {		
		if(args.length >= 2) {
			Game game;
			
			if(args.length == 3) {
				game = new Game(new Rules2048(), Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
			}
			else {
				game = new Game(new Rules2048(), Integer.parseInt(args[0]), Integer.parseInt(args[1]), 1000);
			}
			
			Controller control = new Controller(game, new Scanner(System.in));
			control.run();
		}
		else
			System.out.println("ERROR: Board size and initial cells are required arguments.");
	}

}
