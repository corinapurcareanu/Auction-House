
/**
 * Clasa ce mosteneste clasa de tip Product si reprezinta un tip particular de produs
 */
public class Furniture extends Product {
	private String type;
	private String material;
	
	/**
	 * Constructor pentru mobila
	 * @param id
	 * @param name
	 * @param minPrice
	 * @param year
	 * @param type
	 * @param material
	 */
	public Furniture(int id, String name, double minPrice,
			int year, String type, String material) {
		super(id, name, minPrice, year);
		this.type = type;
		this.material = material;
	}

	/**
	 * Getter pentru tip
	 * @return tipul mobilei
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Getter pentru material
	 * @return materialul mobilei
	 */
	public String getMaterial() {
		return material;
	}
}
