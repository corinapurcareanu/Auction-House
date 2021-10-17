import java.util.ArrayList;

/**
 * Clasa ce implementeaza Factory design pattern pentru obiecte de tipul Product,
 * returnand un anumit tip de produs
 */
public class ProductFactory {

	/**
	 *  Metoda ce creeaza un anumit tip de produs in functie de parametrii specificati
	  * @param type typul clientului din enum-ul cu tipuri de produs
	 * @param arguments restul argumentelor pentru crearea produsului
	 * @return un anumit tip de produs
	 */
	public Product createProduct(ProductType type, ArrayList<String> arguments) {
		switch(type) {
		case Paintings:
			Colors color = null;
			if(arguments.get(5).equals("oil")) {
				color = Colors.oil;
			}
			else if(arguments.get(5).equals("tempera")) {
				color = Colors.tempera;
			}
			else if(arguments.get(5).equals("acrylic")) {
				color = Colors.acrylic;
			}
			else {
				throw new IllegalArgumentException("Unkown color");
			}
			return new Painting(Integer.valueOf(arguments.get(0)), arguments.get(1), 
					Double.parseDouble(arguments.get(2).substring(1)), Integer.valueOf(arguments.get(3)),
					arguments.get(4), color);
		case Furniture:
			return new Furniture(Integer.valueOf(arguments.get(0)), arguments.get(1), 
					Double.parseDouble(arguments.get(2).substring(1)), Integer.valueOf(arguments.get(3)),
					arguments.get(4), arguments.get(5));
		case Jewerly:
			boolean value = false;
			if(arguments.get(5).equals("yes")) {
				value = true;
			}
			return new Jewel(Integer.valueOf(arguments.get(0)), arguments.get(1), 
					Double.parseDouble(arguments.get(2).substring(1)), Integer.valueOf(arguments.get(3)),
					arguments.get(4), value);
		}
		throw new IllegalArgumentException("Unkown product type:  " + type);
	}
}
