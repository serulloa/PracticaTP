package tp.pr3.exceptions;

/**
 * @author Sergio Ulloa
 *
 */
public class UnknownGameException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public UnknownGameException(String message) {
		super(message);
	}
	
}
