import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Clasa abstracta pentru obiecte de tip Client ce contine
 * data despre id-ul, numele, adresa, numarul de participari,
 * numarul de licitatii castigate ale clientului, precum si 
 * pretul maxim pe care il poate oferi la o licitatie si lista
 * cu produsele castigate.
 */
public abstract class Client implements Observer{
	private int id;
	private String name;
	private String adress;
	private int noParticipations;
	private int noWonAuctions;
	private double maxPrice;
	ArrayList<Product> wonProducts;
	Lock lock = new ReentrantLock();
	
	/**
	 * Constructor pentru Client
	 * @param id 
	 * @param name
	 * @param adress
	 */
	protected Client(int id, String name, String adress) {
		this.id = id;
		this.name = name;
		this.adress = adress;
		this.noParticipations = 0;
		this.noWonAuctions = 0;
		wonProducts = new ArrayList<Product>();
	}
	
	/**
	 * Setter pentru pretul maxim pe care il poate oferi in cadrul unei licitatii
	 * @param maxPrice
	 */
	public void setMaxPrice(double maxPrice) {
		this.maxPrice = maxPrice;
	}
	
	/**
	 * Metoda ce creste cu 1 numarul de participari ale clientului,
	 * apelata cand se sfarseste o licitatie sau cand este eliminat din ea
	 */
	public void increaseNoParticipations(){
		noParticipations += 1;
	}
	
	/**
	 * Metoda ce creste cu 1 numarul de dati in care a castigat clientul
	 */
	public void increaseNoWonAuctions(){
		noWonAuctions += 1;
	}
	
	/**
	 * Metoda ce adauga un produs castigat la lista de produse
	 * @param product
	 */
	public void addProduct(Product product) {
		wonProducts.add(product);
	}
	
	/**
	 * Getter pentru id
	 * @return id-ul clientului
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Getter pentru nume
	 * @return numele clientului
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Getter pentru adresa
	 * @return adresa
	 */
	public String getAdress() {
		return adress;
	}
	
	/**
	 * Getter pentru numar de participari
	 * @return numarul de participari
	 */
	public int getNoParticipations() {
		return noParticipations;
	}
	
	/**
	 * Getter pentru numarul de dati in care a castigat
	 * @return numarul de dati in care a castigat
	 */
	public int getNoWonAuctions() {
		return noWonAuctions;
	}
	
	/**
	 * Getter pentru pretul maxim pe care il poate oferi
	 * @return pretul maxim
	 */
	public double getMaxPrice() {
		return maxPrice;
	}
	
	/**
	 * Metoda prin care clientul citeste lista de produse pentru a vedea
	 * daca produsul dorit de el se afla pe lista
	 * @param maxPrice
	 * @param productId
	 * @param house
	 * @throws ProductNotFoundException arunca exceptia in caz ca nu se afla produsul
	 */
	public void wantsToBuy(double maxPrice, int productId,  AuctionsHouse house)
			throws ProductNotFoundException {
		lock.lock();
		try {
			boolean found = false;
			ArrayList<Product> products = house.getProducts();
			for(int i = 0; i < products.size(); i++) {
				if(products.get(i).getId() == productId) {
					setMaxPrice(maxPrice);
					found = true;
					break;
				}
	
			}
			if(found == false) {
				throw new ProductNotFoundException("Product Not Found");
			}
		}
		finally{
			lock.unlock();
		}
	}
	
	/**
	 * Metoda prin care clientul ofera o anumita suma de bani brokerului,
	 * atunci cand i se solicita in cadrul licitatiei
	 * @param minPrice pretul minim pe care trebuie sa il ofere
	 * @return returneaza pretul oferit
	 */
	public double offerMoney(double minPrice) {
		double offeredPrice = (Math.random()*(maxPrice - minPrice) + minPrice);
		return offeredPrice;
	}

}
