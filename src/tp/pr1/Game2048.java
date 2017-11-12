package tp.pr1;

import java.util.Random;
import java.util.Scanner;

/**
 * @author Sergio Ulloa
 */
public class Game2048 {

	public static void main(String[] args) {		
		if(args.length >= 2) {
			Game game;
			
			if(args.length == 3) {
				game = new Game(Integer.parseInt(args[0]), Integer.parseInt(args[1]), new Random(Integer.parseInt(args[2])));
			}
			else {
				game = new Game(Integer.parseInt(args[0]), Integer.parseInt(args[1]), new Random());
			}
			
			Controller control = new Controller(game, new Scanner(System.in));
			control.run();
		}
		else
			System.out.println("ERROR: Board size and initial cells are required arguments.");
	}

}
