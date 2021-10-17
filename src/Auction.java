
/**
 * Clasa pentru licitatii ce contine informatii despre
 * id-ul, numarul de participanti, id-ul prodului si numarul
 * maxim de pasi
 */
public class Auction {
	private int id;
	private int noParticipants;
	private int productId;
	private int noMaxSteps;	
	
	/**
	 * Constructor pentru obiecte de tipul Auction
	 */
	public Auction() {

	}
	
	/**
	 * Setter pentru id
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Setter pentru numarul de participanti
	 * @param noParticipants
	 */
	public void setNoParticipants(int noParticipants) {
		this.noParticipants = noParticipants;
	}
	
	/**
	 * Setter pentru id-ul produsului
	 * @param productId
	 */
	public void setProductId(int productId) {
		this.productId = productId;
	}
	
	/**
	 * Setter pentru numarul maxim de pasi
	 * @param noMaxSteps
	 */
	public void setNoMaxSteps(int noMaxSteps) {
		this.noMaxSteps = noMaxSteps;
	}
	
	/**
	 * Getter pentru id
	 * @return id-ul licititatiei
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Getter pentru numarul de participanti
	 * @return numarul de participanti
	 */
	public int getNoParticipants() {
		return noParticipants;
	}
	
	/**
	 * Getter pentru id-ul produsului
	 * @return id-ul produsului
	 */
	public int getProductId() {
		return productId;
	}
	
	/**
	 * Getter pentru numarul maxim de pasi
	 * @return numarul masin de pasi
	 */
	public int getNoMaxSteps() {
		return noMaxSteps;
	}
}
