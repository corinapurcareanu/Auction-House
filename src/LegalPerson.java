
/**
 * Clasa ce mosteneste clasa Client, fiind un tip particular de client
 */
public class LegalPerson extends Client{
	private Company company;
	private double socialCapital;
	
	/**
	 * Constructor pentru persoana juridica
	 * @param id
	 * @param name
	 * @param adress
	 * @param company
	 * @param socialCapital
	 */
	protected LegalPerson(int id, String name, String adress, Company company, double socialCapital) {
		super(id, name, adress);
		this.company = company;
		this.socialCapital = socialCapital;
	}
	
	/**
	 * Getter pentru companie
	 * @return tipul companiei
	 */
	public Company getCompany() {
		return company;
	}
	
	/**
	 * Getter pentru capitalul social
	 * @return capitalul social
	 */
	public double getSocialCapital() {
		return socialCapital;
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
