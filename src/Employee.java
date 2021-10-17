
/**
 * Clasa abastracta pentru angajati ce contine id-ul si numele acestora
 */
public abstract class Employee {
	private int id;
	private String name;
	
	/**
	 * Constructor pentru obiecte de tip Employee
	 * @param id
	 * @param name
	 */
	protected Employee(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	/**
	 * Getter pentru id
	 * @return id-ul angajatului
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Getter pentru nume
	 * @return numele angajatului
	 */
	public String getName() {
		return name;
	}
	
}
