package tp.pr2.control;

import java.util.Scanner;

import tp.pr2.control.commands.CommandParser;
import tp.pr2.logic.Direction;
import tp.pr2.logic.multigames.Game;

/**
 * @author Sergio Ulloa
 */
public class Controller {
	
	// ================================================================================
	// Atributos
	// ================================================================================
	
	private Game game; 	// Partida
	private Scanner in; // Para leer de la consola las órdenes del usuario
	private boolean finished;
	
	// ================================================================================
	// Constructores
	// ================================================================================
	
	public Controller(Game game, Scanner in) {
		this.game = game;
		this.in = in;
		this.finished = false;
	}
	
	// ================================================================================
	// Métodos
	// ================================================================================
	
	/**
	 * Método que controla el funcionamiento del juego según el comando introducido por
	 * el usuario. En caso de que se introduzca un comando erróneo, se mostrará un
	 * error por pantalla
	 */
	public void run() {
		boolean error = false;
		String[] command;
		
		while(!finished) {
			if(!error)
				System.out.println(game.toString());
			error = false;
			
			if(game.isLosen()) System.out.println("Game is finished, type 'RESET' to play another game");
			System.out.print("Command > ");
			
			command = parseIn();
			if(command.length < 0 || command.length > 2)
				command[0] = "ERROR";
			
			switch(command[0]) {
				case "MOVE":
				{
					if(command.length == 2) {
						switch(command[1]) {
							case "UP":
							{
								game.move(Direction.UP);
								break;
							}
							case "DOWN":
							{
								game.move(Direction.DOWN);
								break;
							}
							case "LEFT":
							{
								game.move(Direction.LEFT);
								break;
							}
							case "RIGHT":
							{
								game.move(Direction.RIGHT);
								break;
							}
							default:
							{
								System.out.println("Unknown direction for move command");
								error = true;
							}
						}
					}
					else {
						System.out.println("Move must be followed by a direction: up, down, left or right");
						error = true;
					}
					
					break;
				}
				case "RESET":
				{
					game.reset();
					break;
				}
				case "EXIT":
				{
					System.out.println("Game over");
					finished = true;
					break;
				}
				case "HELP":
				{
					printHelp();
					break;
				}
				default:
				{
					System.out.println("Unknown command");
					error = true;
				}
			}
		}
	}
	
	/**
	 * Método que sirve para parsear la entrada, la convierte a mayúsculas y crea
	 * un array de strings con cada una de las palabras
	 * 
	 * @return El array de strings previamente mencionado
	 */
	private String[] parseIn() {
		String[] ret = in.nextLine().toUpperCase().split(" ", -1);
		
		return ret;
	}
	
	/**
	 * Método que al invocarse pone a true la variable finished, la cual termina la partida
	 * y cierra el programa.
	 */
	public void exit() {
		System.out.println("Game over");
		finished = true;
	}
	
	public void help() {
		System.out.println(CommandParser.commandHelp());
	}
	
	public void reset() {
		System.out.println("Game restarted!");
		finished = false;
	}
	
	/**
	 * Escribe un mensaje de ayuda por pantalla
	 */
	private void printHelp() {
		System.out.println("Move <direction>: execute a move in one of the four directions, up, down, left, right");
		System.out.println("Reset: start a new game");
		System.out.println("Help: print this help message");
		System.out.println("Exit: terminate the program");
	}
}
