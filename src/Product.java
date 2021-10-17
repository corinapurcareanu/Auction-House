
/**
 * Clasa petru obiectele de tipul produs, ce contine
 * informatii despre id-ul, numele, pretul cu care a fost vandut,
 * pretul minim de vanzare si anul fabricarii produsului
 */
public abstract class Product{
	private int id;
	private String name;
	private double sellingPrice;
	private double minPrice; 
	private int year;
	
	/**
	 * Constructor pentru obiecte de tip produs
	 * @param id
	 * @param name
	 * @param minPrice
	 * @param year
	 */
	protected Product(int id, String name, double minPrice, int year) {
		this.id = id;
		this.name = name;
		this.minPrice = minPrice;
		this.year = year;
	}
	
	/**
	 * Getter pentru id
	 * @return id-ul produsului
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Getter pentru nume
	 * @return numele produsului
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Getter pentru pretul cu care a fost vandut
	 * @return pretul cu care a fost vandut
	 */
	public double getSellingPrice() {
		return sellingPrice;
	}
	
	/**
	 * Getter pentru pretul minim de vanzare
	 * @return pretul minim de vanzare
	 */
	public double getMinPrice() {
		return minPrice;
	}
	
	/**
	 * Getter pentru anul in care a fost fabricat
	 * @return anul in care a fost fabricat
	 */
	public int getYear() {
		return year;
	}
}
