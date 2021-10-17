
/**
 * Builder pentru elemente de tipul Auction
 */
public class AuctionBuilder {
	private final Auction auction = new Auction();
	
	/**
	 * Metoda ce seteaza id-ul
	 * @param id
	 * @return builderul
	 */
	public AuctionBuilder withId(int id) {
		auction.setId(id);
		return this;
	}
	
	/**
	 * Metoda ce seteaza numarul de participanti
	 * @param noParticipants
	 * @return builderul
	 */
	public AuctionBuilder withNoParticipants(int noParticipants) {
		auction.setNoParticipants(noParticipants);
		return this;
	}
	
	/**
	 * Metoda ce seteaza id-ul produsului
	 * @param productId
	 * @return builderul
	 */
	public AuctionBuilder withProductId(int productId) {
		auction.setProductId(productId);
		return this;
	}
	
	/**
	 * Metoda ce seteaza numarul maxim de pasi
	 * @param noMaxSteps
	 * @return numarul maxim de pasi
	 */
	public AuctionBuilder withNoMaxSteps(int noMaxSteps) {
		auction.setNoMaxSteps(noMaxSteps);
		return this;
	}
	
	/**
	 * Method which builds the auction
	 * @return the auction
	 */
	public Auction build() {
		return auction;
	}
}
