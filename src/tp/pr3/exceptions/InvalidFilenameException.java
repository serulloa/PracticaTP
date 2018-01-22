package tp.pr3.exceptions;

/**
 * @author Sergio Ulloa
 *
 */
public class InvalidFilenameException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public InvalidFilenameException(String message) {
		super(message);
	}

}
