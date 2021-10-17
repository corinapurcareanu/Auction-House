
/**
 * Exceptie ce este aruncata atunci cand nu este gasit un produs
 */
@SuppressWarnings("serial")
public class ProductNotFoundException extends Exception{
	public ProductNotFoundException(String message) {
		super(message);
	}
}
