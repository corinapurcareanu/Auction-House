import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * 
 * @author Purcareanu Corina
 * 321 CB
 *
 */
public class Main {
	/**
	 * Metoda ce citeste comenzile din fisier
	 * @param file
	 * @param scan
	 * @throws NumberFormatException
	 * @throws ProductNotFoundException
	 * @throws CommandNotFoundException
	 */
	public static void Read(File file, Scanner scan) throws NumberFormatException, ProductNotFoundException, CommandNotFoundException{
		AuctionsHouse house = AuctionsHouse.Instance();
		while(scan.hasNextLine()) {
			String line = scan.nextLine();
			String[] words = line.split(" ");
			if(words[0].equals("setAdministrator") == true) {
				house.setAdministrator(Integer.valueOf(words[1]), words[2] + " " + words[3]);
			}
			else if(words[0].equals("createLists") == true) {
				house.createLists();
			}
			else if(words[0].equals("client") == true) {
				try {
					house.wantsToBid(Integer.valueOf(words[1]), Integer.valueOf(words[5].substring(1)), 
							Integer.valueOf(words[3]));
				}
				catch (NumberFormatException e) {
					System.err.println(e.getMessage());
				}
				catch (ProductNotFoundException e) {
					System.err.println(e.getMessage());
				} 
				catch (AuctionInterruptedException e) {
					System.err.println(e.getMessage());
				}
			}
			else {
				throw new CommandNotFoundException("Command Not Found");
			}
		}
	}
	public static void main(String[] args){
		Scanner scan;
		scan = new Scanner(System.in);
		File file = new File(scan.next());
		scan.close();
		try {
			scan = new Scanner(file);
			try {
				Read(file, scan);
			} 
			catch (NumberFormatException e) {
				System.err.println(e.getMessage());
			} 
			catch (ProductNotFoundException e) {
				System.err.println(e.getMessage());
			} 
			catch (CommandNotFoundException e) {
				System.err.println(e.getMessage());
			}
			scan.close();
			
		} 
		catch (FileNotFoundException e1) {
			System.err.println(e1.getMessage());
		}		
	}
}

