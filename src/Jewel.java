
/**
 * Clasa ce mosteneste clasa de tip Product si reprezinta un tip particular de produs
 */
public class Jewel extends Product{
	private String material;
	private boolean isPrecious;
	
	/**
	 * Constructor pentru bijuterie
	 * @param id
	 * @param name
	 * @param minPrice
	 * @param year
	 * @param material
	 * @param isPrecious
	 */
	public Jewel(int id, String name, double minPrice, 
			int year, String material, boolean isPrecious) {
		super(id, name, minPrice, year);
		this.material = material;
		this.isPrecious = isPrecious;
	}
	
	/**
	 * Getter pentru material
	 * @return materialul din care e confectionata bijuteria
	 */
	public String getMaterial() {
		return material;
	}
	
	/**
	 * Getter pentru valoarea de adevar a faptului ca este pretioasa
	 * @return adevarat sau fals
	 */
	public boolean getPrecious() {
		return isPrecious;
	}

}
