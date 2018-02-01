package tp.pr3.control.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import tp.pr3.exceptions.FileNotFoundException;
import tp.pr3.exceptions.InvalidFilenameException;
import tp.pr3.exceptions.SaveException;
import tp.pr3.logic.multigames.Game;
import tp.pr3.logic.multigames.GameType;
import tp.pr3.util.MyStringUtils;

/**
 * @author Sergio Ulloa
 *
 */
public class LoadCommand extends Command {
	
	// ================================================================================
	// Atributos
	// ================================================================================
	
	private String filename;
	private boolean filename_confirmed;
	
	// ================================================================================
	// Constructores
	// ================================================================================

	public LoadCommand() {
		super("load <filename>", "allows to load a previously saved game from a text file.\n");
	}
	
	public LoadCommand(String filename) {
		super("load " + filename, "allows to load a previously saved game from a text file.\n");
		this.filename = filename;
	}
	
	// ================================================================================
	// Métodos
	// ================================================================================

	@Override
	public boolean execute(Game game) throws SaveException, IOException {
		boolean ret = false;
		
		try (BufferedReader in = new BufferedReader(new FileReader(filename))) {
			String first = in.readLine();
			
			if(first.equals("This file stores a saved 2048 game")) {
				GameType type = game.load(in);
				System.out.println("Game successfully loaded from file: " + type.toString());
				ret = true;
			}
			else {
				throw new SaveException("Load failed: invalid file format");
			}
			
		} catch (java.io.FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw new SaveException("Se ha producido un error al cargar la partida");
		} catch (NumberFormatException e) {
			throw e;
		}
		
		return ret;
	}

	@Override
	protected Command parse(String[] commandWords, Scanner in) 
			throws InvalidFilenameException, FileNotFoundException {
		Command command = null;
		
		try {
			if(commandWords[0].toUpperCase().equals("LOAD")) {
				if(commandWords.length == 2) {
					String filenameString = commandWords[1];
					
					String filename = confirmFileNameStringForRead(filenameString, in);
					if (filename != null)
						command = new LoadCommand(filename);
				}
				else if (commandWords.length > 2)
					throw new InvalidFilenameException("Invalid filename: the filename contains spaces");
				else {
					throw new InvalidFilenameException("Load must be followed by a filename");
				}
			}
		} catch (InvalidFilenameException e) {
			throw e;
		}
		
		return command;
	}
	
	private String confirmFileNameStringForRead(String filenameString, Scanner in) 
			throws InvalidFilenameException, FileNotFoundException {
		String loadName = filenameString;
	    filename_confirmed = false;
	    
	    while (!filename_confirmed) {
	    	if (MyStringUtils.validFileName(loadName)) {
	    		File file = new File(loadName);
	            if (file.exists())
	            	filename_confirmed = true;
	            else {
	            	throw new FileNotFoundException("File not found");
            	}
    		} 
	    	else {
    			throw new InvalidFilenameException("Invalid filename: the filename contains invalid characters");
    		}
	    }
	    
	    return loadName;
	}

}
