import java.util.ArrayList;

/**
 * Clasa ce implementeaza Factory design pattern pentru obiecte de tipul Client,
 * returnand un anumit tip de client
 */
public class ClientFactory {
	
	/**
	 * Metoda ce creeaza un anumit tip de client in functie de parametrii specificati
	 * @param type typul clientului din enum-ul cu tipuri de clienti
	 * @param arguments restul argumentelor pentru crearea clientului
	 * @return un anumit tip de client
	 */
	public Client createClient(ClientType type, ArrayList<String> arguments) {
		switch(type) {
		case Individual:
			return new Individual(Integer.valueOf(arguments.get(0)), arguments.get(1),
					arguments.get(2), arguments.get(3));
		case LegalPerson:
			Company company = null;
			if(arguments.get(3).equals("SRL")) {
				company = Company.SRL;
			}
			else if(arguments.get(3).equals("SA")) {
				company = Company.SA;
			}
			else {
				throw new IllegalArgumentException("Unkown type of client");
			}
			return new LegalPerson(Integer.valueOf(arguments.get(0)), arguments.get(1),
					arguments.get(2), company, Double.parseDouble(arguments.get(4).substring(1)));
		}
		throw new IllegalArgumentException("Unkown product type:  " + type);
	}
}
