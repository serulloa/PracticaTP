package tp.pr3.exceptions;

/**
 * @author Sergio Ulloa
 *
 */
public class UnknownCommandException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public UnknownCommandException(String message) {
		super(message);
	}

}
