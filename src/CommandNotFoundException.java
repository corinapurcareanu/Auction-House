/**
 * Exceptie care apare atunci cand nu se gaseste o Comanda
 */
@SuppressWarnings("serial")
public class CommandNotFoundException extends Exception {
	public CommandNotFoundException(String message) {
		super(message);
	}
}
