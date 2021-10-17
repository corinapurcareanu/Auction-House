import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Clasa ce mosteneste Employee si reperezinta un tip particular de angajat
 */
public class Administrator extends Employee{
	private static Administrator uniqueAdministrator;
	private static Lock lock = new ReentrantLock();
	
	/**
	 * Constructor pentru administrator
	 * @param id
	 * @param name
	 */
	private Administrator(int id, String name) {
		super(id, name);
	}

	/**
	 * Metoda ce se asigura ca exista un singur administrator, folosind
	 * Singleton design pattern
	 * @param id
	 * @param name
	 * @return administratorul
	 */
	public static Administrator Instance(int id, String name) {
		if(uniqueAdministrator == null) {
			uniqueAdministrator = new Administrator(id, name);
		}
		return uniqueAdministrator;
	}
	
	/**
	 * Metoda ce adauga un produs in casa de licitatii
	 * @param typeName
	 * @param arguments
	 */
	public void addProduct(String typeName, ArrayList<String> arguments, AuctionsHouse house) {
		lock.lock();
		try {
			ProductType type;
			ProductFactory productFactory = new ProductFactory();
			if(typeName.equals("Paintings")) {
				type = ProductType.Paintings;
			}
			else if(typeName.equals("Furniture")) {
				type = ProductType.Furniture;
			}
			else {
				type = ProductType.Jewerly;
			}
			Product product = productFactory.createProduct(type, arguments);
	        house.getProducts().add(product);
		}
		finally {
			lock.unlock();
		}
	}
	
}
