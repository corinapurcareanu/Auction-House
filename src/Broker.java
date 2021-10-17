import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Clasa ce mosteneste Employee si reperezinta un tip particular de angajat
 */
public class Broker extends Employee implements Subject{
	private double commision;
	private ArrayList<Client> clients;
	private static Lock lock = new ReentrantLock();
	
	/**
	 * Constructor pentru broker
	 * @param id
	 * @param name
	 */
	public Broker(int id, String name) {
		super(id, name);
		commision = 0;
		clients = new ArrayList<Client>();
	}
	
	/**
	 * Metoda ce adauga o suma de bani la comision
	 * @param newCommision
	 */
	public void addCommision(double newCommision) {
		commision += newCommision;
	}
	
	/**
	 * Getter pentru lista clientilor
	 * @return lista clientilor
	 */
	public ArrayList<Client> getClients(){
		return clients;
	}
	
	/**
	 * Getter pentru comision
	 * @return comisionul
	 */
	public double getCommision() {
		return commision;
	}
	
	/**
	 * Metoda ce elimina un produs din lista casei de licitatii dupa ce a fost vandut
	 * @param productId
	 * @param house
	 */
	public void removeProduct(int productId, AuctionsHouse house) {
		lock.lock();
		try {
			for(int i = 0; i < house.getProducts().size(); i++) {
				if(house.getProducts().get(i).getId() == productId) {
					house.getProducts().remove(i);
					break;
				}
			}
		}
		finally {
			lock.unlock();
		}
	}
	
	/**
	 * Metoda ce solicita clientilor sa ofere o suma de bani la licitatie
	 * @param minPrice
	 * @param prices
	 */
	public void askClientsToBid(double minPrice, ArrayList<Double> prices) {
		for(int i = 0; i < clients.size(); ) {
			/*
			 * Daca clientul poate oferi o suma mai mare decat maximul de pana acum,
			 * ii ofera o suma de bani si e adaugata intr-o lista cu toate sumele oferite
			 * de toti clientii la pasul respectiv pentru a fi gasit maximul de casa de licitatii
			 */
			if(minPrice <= clients.get(i).getMaxPrice()) {
				double offeredPrice = clients.get(i).offerMoney(minPrice);
				prices.add(offeredPrice);
				i++;
			}
			/*
			 * Daca nu poate oferi o suma mai mare, clientul este eliminat,
			 * si ii creste numarul de participari
			 */
			else {
				clients.get(i).increaseNoParticipations();
				clients.remove(i);
			}
		}
	}
	
	/**
	 * Metoda prin care brokerul cauta un produs in casa de licitatii
	 * @param productId
	 * @param house
	 * @return produsul gasit sau null daca nu il gaseste
	 */
	public Product findProduct(int productId, AuctionsHouse house) {
		for(int i = 0; i < house.getProducts().size(); i++) {
			if(productId == house.getProducts().get(i).getId()) {
				return house.getProducts().get(i);
			}
		}
		return null;
	}
	
	/**
	 * Metoda prin care isi anunta clientii ca s-a terminat licitatia
	 * @param max
	 * @param winningClient
	 * @param auction
	 * @param house
	 */
	public void announceEndOfAuction(double max, Client winningClient, Auction auction, AuctionsHouse house) {
		Product soldProduct = findProduct(auction.getProductId(), house);
		Notify("Auction has ended");
		while(clients.size() > 0) {
			Client currentCrient = clients.get(0);
			if(soldProduct != null) {
				/*
				 * Daca se afla la el clientul castigator, elimina produsul din lista si ii ia
				 * comision clientului in functie de ce tip de client este si de cate ori a mai participat
				 */
				if(currentCrient.getId() == winningClient.getId() && max >= soldProduct.getMinPrice()) {
					currentCrient.increaseNoWonAuctions();
					currentCrient.addProduct(soldProduct);
					Thread remove = new Thread(() -> house.getProducts().remove(soldProduct));
					remove.start();
					if(currentCrient instanceof Individual) {
						if(currentCrient.getNoParticipations() <= 5) {
							addCommision(max*0.2);
						}
						else {
							addCommision(max*0.15);
						}
					}
					else {
						if(currentCrient.getNoParticipations() <= 25) {
							addCommision(max*0.25);
						}
						else {
							addCommision(max*0.1);
						}
					}
				}
			}
			/*
			 * Creste numarul de participari al clientilor si sunt eliminati din lista brokerului
			 */
			Detach(currentCrient);
		}
	}

	@Override
	/**
	 * Metoda ce adauga un client din interfata Subject
	 */
	public void Attach(Observer o) {
		clients.add((Client) o);
		
	}

	@Override
	/**
	 * Metoda ce elimina un client din interfata Subject
	 */
	public void Detach(Observer o) {
		clients.remove((Client) o);
	}

	@Override
	/**
	 * Metoda ce anunta toti clientii un mesaj din interfata Subject
	 * @param message
	 */
	public void Notify(String message) {
		for(int i = 0; i < clients.size(); i++) {
			clients.get(i).update(message);
		}
		
	}
}
