
/**
 * Exceptie ce este aruncata atunci cand o licitatie este intrerupta
 * pana sa se realizeze numarul maxim de pasi
 */
@SuppressWarnings("serial")
public class AuctionInterruptedException extends Exception{
	public AuctionInterruptedException(String message) {
		super(message);
	}
}
