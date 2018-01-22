package tp.pr3.control.commands;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import tp.pr3.exceptions.InvalidFilenameException;
import tp.pr3.logic.multigames.Game;
import tp.pr3.util.MyStringUtils;

/**
 * @author Sergio Ulloa
 *
 */
public class SaveCommand extends Command {
	
	// ================================================================================
	// Atributos
	// ================================================================================
	
	private String filename;
	private boolean filename_confirmed;
	
	// ================================================================================
	// Constructores
	// ================================================================================

	public SaveCommand() {
		super("save <filename>", "allows to save the complete state of the game in a text file.\n");
	}
	
	public SaveCommand(String filename) {
		super("save " + filename, "allows to save the complete state of the game in a text file.\n");
		this.filename = filename;
	}

	// ================================================================================
	// Métodos
	// ================================================================================
	
	@Override
	public boolean execute(Game game) {
		try(BufferedWriter out = new BufferedWriter(new FileWriter(filename))) {
			out.write("This file stores a saved 2048 game");
			out.newLine();
			game.store(out);
			
			System.out.println("Game successfully saved to file; use load command to reload it.");
		} catch (IOException e) {
			System.err.println("Se ha producido un error al guardar la partida");
		}
		
		return false;
	}

	@Override
	protected Command parse(String[] commandWords, Scanner in) throws InvalidFilenameException {
		Command command = null;
		
		try {
			if (commandWords[0].toUpperCase().equals("SAVE")) {
				if(commandWords.length == 2) {
					String filenameString = commandWords[1];
					
					String filename = confirmFileNameStringForWrite(filenameString, in);
					if (filename != null)
						command = new SaveCommand(filename);
				}
				else if (commandWords.length > 2)
					throw new InvalidFilenameException("Invalid filename: the filename contains spaces");
				else {
					throw new InvalidFilenameException("Save must be followed by a filename");
				}
			}
		} catch (InvalidFilenameException e) {
			throw e;
		}
		
		return command;
	}
	
	private String confirmFileNameStringForWrite(String filenameString, Scanner in) 
			throws InvalidFilenameException {
		String loadName = filenameString;
	    filename_confirmed = false;
	    
	    while (!filename_confirmed) {
	    	if (MyStringUtils.validFileName(loadName)) {
	    		File file = new File(loadName);
	            if (!file.exists())
	            	filename_confirmed = true;
	            else {
	            	loadName = getLoadName(filenameString, in);
            	}
    		} 
	    	else {
    			throw new InvalidFilenameException("Invalid filename: the filename contains invalid characters");
    		}
	    }
	    
	    return loadName;
	}
	
	public String getLoadName(String filenameString, Scanner in) throws InvalidFilenameException {
	    String newFilename = null;
	    boolean yesOrNo = false;
	    
	    while (!yesOrNo) {
	        System.out.print("The file already exists; do you want to overwrite it? (Y/N)" + ": ");
	        String[] responseYorN = in.nextLine().toLowerCase().trim().split("\\s+");
	        
	        if (responseYorN.length == 1) {
	           switch (responseYorN[0]) {
	               case "y": 
            	   {
            		   yesOrNo = true;
            		   newFilename = filenameString;
            		   filename_confirmed = true;
            		   break;
            	   }
	               case "n": 
            	   {
            		   System.out.print("Please enter another filename: ");
            		   newFilename = in.nextLine();
            		   
            		   if(newFilename.contains(" ")) 
            			   throw new InvalidFilenameException("Invalid filename: the filename contains spaces");
            		   else if(!MyStringUtils.validFileName(newFilename))
            			   throw new InvalidFilenameException("Invalid filename: the filename contains invalid characters");
            		   
            		   yesOrNo = true;
            		   break;
            	   }
	               default: 
	               {
	            	   System.err.println("You must enter only Y or N");
	               }
	           }
	        } 
	        else System.err.println("You must enter only Y or N");
	    }
	    return newFilename;
	}

}
