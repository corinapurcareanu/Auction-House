
/**
 * Clasa ce mosteneste clasa Client, fiind un tip particular de client
 */
public class Individual extends Client {
	private String birthDate;
	/**
	 * Constructor pentru persoana fizica
	 * @param id
	 * @param name
	 * @param adress
	 * @param birthDate
	 */
	protected Individual(int id, String name, String adress, String birthDate) {
		super(id, name, adress);
		this.birthDate = birthDate;
	}
	
	/**
	 * Getter pentru data nasterii
	 * @return data nasterii
	 */
	public String getBirthDate() {
		return birthDate;
	}

	@Override
	/**
	 * Metoda ce trasmite un mesaj de la brokeri.Daca acestia ii anunta
	 *  ca s-a terminat licitatia, creste numarul de participari
	 */
	public void update(String message) {
		if(message.equals("Auction has ended")) {
			increaseNoParticipations();
		}
		
	}
}
