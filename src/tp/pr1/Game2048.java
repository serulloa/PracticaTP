package tp.pr1;

import java.util.Random;
import java.util.Scanner;

/**
 * @author Sergio Ulloa
 */
public class Game2048 {

	public static void main(String[] args) {		
		Game game = new Game(4, 2, new Random());
		Controller control = new Controller(game, new Scanner(System.in));
		
		control.run();
	}

}
