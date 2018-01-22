package tp.pr3.control.commands;

import java.util.Scanner;

import tp.pr3.exceptions.EmptyStackException;
import tp.pr3.exceptions.ExitCommandException;
import tp.pr3.exceptions.FileNotFoundException;
import tp.pr3.exceptions.InvalidFilenameException;
import tp.pr3.exceptions.ResetCommandException;
import tp.pr3.exceptions.SaveFormatException;
import tp.pr3.exceptions.UnknownDirectionException;
import tp.pr3.exceptions.UnknownGameException;
import tp.pr3.logic.multigames.Game;


/**
 * @author Sergio Ulloa
 */
public abstract class Command {
	
	// ================================================================================
	// Atributos
	// ================================================================================
	
	private String helpText;
	private String commandText;
	protected final String commandName;
	
	// ================================================================================
	// Constructores
	// ================================================================================	

	public Command(String commandInfo, String helpInfo) {
		commandText = commandInfo;
		helpText = helpInfo;
		String[] commandInfoWords = commandText.split("\\s+");
		commandName = commandInfoWords[0];
	}
	
	// ================================================================================
	// Métodos
	// ================================================================================
	
	/**
	 * Método que se encarga de ejecutar en la partida "game" el comando que se ha introducido
	 * 
	 * @param game Partida sobre la que se ejecuta el comando
	 * @return Devuelve un booleano que informa al controlador sobre si se tiene que imprimir
	 * 			la partida o no
	 * @throws EmptyStackException En caso de que no haya ningún movimiento que deshacer o 
	 * 								rehacer
	 * @throws ExitCommandException En caso de que se haya introducido el comando exit, no es
	 * 								error, sino una excepción lógica
	 * @throws ResetCommandException En caso de que se haya introducido el comando reset, no
	 * 									es un error, sino una excepción lógica
	 * @throws SaveFormatException En caso de que se haya introducido el comando load y el fichero
	 * 								no posea el formato requerido
	 */
	public abstract boolean execute(Game game) 
			throws EmptyStackException, ExitCommandException, ResetCommandException, SaveFormatException;
	
	/**
	 * Método que se encarga de hacer el parsing del comando introducido
	 * 
	 * @param commandWords Comando introducido dividido por " "
	 * @param in Scanner que se utiliza para leer texto de consola
	 * @return Devuelve un Command con el comando a ejecutar en el siguiente paso
	 * @throws UnknownDirectionException En caso de haber introducido el comando move con una
	 * 										dirección desconocida
	 * @throws UnknownGameException En caso de haber introducido el comando play con un tipo de
	 * 								juego desconocido
	 * @throws InvalidFilenameException En caso de haber introducido el comando save con un nombre
	 * 									de fichero inválido
	 * @throws FileNotFoundException En caso de haber introducido el comando load con un nombre de
	 * 									fichero inexistente
	 */
	protected abstract Command parse(String[] commandWords, Scanner in) 
			throws UnknownDirectionException, UnknownGameException, InvalidFilenameException, FileNotFoundException;
	
	/**
	 * Método que establece el texto de ayuda del comando this
	 * 
	 * @return Un string con la ayuda sobre el comando this
	 */
	protected String helpText() {
		return " " + commandText + ": " + helpText;
	}
}