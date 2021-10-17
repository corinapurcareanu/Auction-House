
/**
 * Clasa ce mosteneste clasa de tip Product si reprezinta un tip particular de produs
 */
public class Painting extends Product{
	private String artistName;
	private Colors color;

	/**
	 * Constructor pentru tablou
	 * @param id
	 * @param name
	 * @param minPrice
	 * @param year
	 * @param artistName
	 * @param color
	 */
	public Painting(int id, String name, double minPrice,
			int year, String artistName, Colors color) {
		super(id, name, minPrice, year);
		this.artistName = artistName;
		this.color = color;
	}
	
	/**
	 * Getter pentru numele artistului
	 * @return numele artistului
	 */
	public String getArtistName() {
		return artistName;
	}
	
	/**
	 * Getter pentru culoare
	 * @return culoarea tabloului
	 */
	public Colors getColor() {
		return color;
	}
}
