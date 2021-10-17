import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Clasa pentru casa de licitatii ce contine lista de produse,
 * lista de clienti, lista de brokeri, un map ce are ca cheie o licitatie
 * si ca valori clientii ce doresc sa cumpere acel produs, si un administrator
 */
public class AuctionsHouse {
	private ArrayList<Product> products;
	private ArrayList<Client> clients;
	private ArrayList<Broker> brokers;
	private Map<Auction, ArrayList <Client>> auctions;
	private Administrator administrator;
	private static AuctionsHouse uniqueHouse;
	
	/**
	 * Constructor pentru elemente de tip casa de licitatii
	 */
	private AuctionsHouse() {
		products = new ArrayList<Product>();
		clients = new ArrayList<Client>();
		auctions = new LinkedHashMap<Auction, ArrayList <Client>>();
		brokers = new ArrayList<Broker>();
	}
	
	/**
	 * Metoda ce instantiaza un sigur obiect de tip casa de licitatii
	 * folosind Sigleton Design Pattern
	 * @return casa de licitatii
	 */
	public static AuctionsHouse Instance() {
		if(uniqueHouse == null) {
			uniqueHouse = new AuctionsHouse();
		}
		return uniqueHouse;
	}
	
	/**
	 * Setter pentru administrator
	 * @param id
	 * @param name
	 */
	public void setAdministrator(int id, String name) {
		administrator = Administrator.Instance(id, name);
	}
	
	/**
	 * Getter pentru lista de produse
	 * @return lista de produse
	 */
	public ArrayList<Product> getProducts(){
		return products;
	}
	
	/**
	 * Getter pentru lista de clienti
	 * @return lista de clienti
	 */
	public ArrayList<Client> getClients(){
		return clients;
	}	
	
	/**
	 * Getter pentru lista de brokeri
	 * @return lista de brokeri
	 */
	public ArrayList<Broker> getBrokers(){
		return brokers;
	}
	
	/**
	 * Getter pentru administator
	 * @return administratorul
	 */
	public Administrator getAdministrator() {
		return administrator;
	}
	
	/**
	 * Getter pentru map-ul ce contine licitatii si clientii doritori sa participe
	 * @return map-ul
	 */
	public Map<Auction, ArrayList <Client>> getAuctions(){
		return auctions;
	}
	
	/**
	 * Metoda pentru a parcurge celula cu celula o linie dintr-ul fisier
	 * Excel si returneaza o lista cu toate elementele convertite in String
	 * @param cellIterator
	 * @return
	 */
	public ArrayList<String> parseRow(Iterator<Cell> cellIterator) {
		ArrayList<String> arguments = new ArrayList<String>();
        while (cellIterator.hasNext()) {
	        Cell cell = (Cell)cellIterator.next();
	       	switch (cell.getCellType()) {
	       	case STRING:
	       		arguments.add(cell.getStringCellValue());
	       		break;
	       	case NUMERIC:
	       		int cellValue = (int)cell.getNumericCellValue();
	       		arguments.add(String.valueOf(cellValue));
	            break;
	        default:
	       	}
        }
        return arguments;
	}

	/**
	 * Metoda ce adauga un broker
	 * @param arguments
	 */
	public void addBroker(ArrayList<String> arguments) {
		Broker broker = new Broker(Integer.valueOf(arguments.get(0)), arguments.get(1));
		brokers.add(broker);
	}
	
	/**
	 * Metoda ce adauga un client in functie de tip
	 * @param typeName
	 * @param arguments
	 */
	public void addClient(String typeName, ArrayList<String> arguments) {
		ClientType type;
		ClientFactory clientFactory = new ClientFactory();
		if(typeName.equals("Individuals")) {
			type = ClientType.Individual;
		}
		else {
			type = ClientType.LegalPerson;
		}
		Client client = clientFactory.createClient(type, arguments);
		clients.add(client);
	}
	
	/**
	 * Metoda ce adauga un element, in functie de fisierul din care este citit
	 * @param filename
	 * @param sheetName
	 * @param arguments
	 */
	public void addElement(String filename, String sheetName, ArrayList<String> arguments) {
		/*
		 * Pentru produse se porneste un thread in care administratorul le adauga in casa de licitatii
		 */
		if(filename.equals("Products.xlsx")) {
			Thread add = new Thread(() -> administrator.addProduct(sheetName, arguments, this));
			add.start();
		}
		else if(filename.equals("Clients.xlsx")) {
			addClient(sheetName, arguments);
		}
		else {
			addBroker(arguments);
		}
	}

	/**
	 * Metoda ce parcurge un fisier Excel
	 * @param filename
	 */
	public void parseExcel(String filename){
		try {
			File file = new File(filename);
			FileInputStream inp = new FileInputStream(file);
			XSSFWorkbook workBook = new XSSFWorkbook (inp);
			for(int i = 0; i < workBook.getNumberOfSheets(); i++) {
				XSSFSheet currentSheet = workBook.getSheetAt(i);
				Iterator<Row> rowIterator = currentSheet.iterator();
				rowIterator.next();
				 while (rowIterator.hasNext()) {
		             Row row = (Row)rowIterator.next();
		             Iterator<Cell> cellIterator = row.cellIterator(); 
		             ArrayList<String> arguments = parseRow(cellIterator);
		             /*
		              * Se adauga elementele in lista potrivita
		              */
		             if(arguments.size() != 0) {
		            	 addElement(filename, workBook.getSheetName(i), arguments); 
		             }
		             else {
		            	 break;
		             }
				 }
			}
			 workBook.close();
			inp.close();
		}
		catch(FileNotFoundException e) {
			System.err.println(e.getMessage());
		}
		catch(IOException e) {
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * Metoda ce creeaza listele pentru cele 3 fisiere
	 */
	public void createLists() {
		parseExcel("Products.xlsx");
		parseExcel("Clients.xlsx");
		parseExcel("Brokers.xlsx");
	}

	/**
	 * Metoda ce cauta un client dupa id
	 * @param clientId
	 * @return clientul sau null daca nu il gaseste
	 */
	public Client findClient(int clientId) {
		for(int i = 0; i < clients.size(); i++) {
			if(clientId == clients.get(i).getId())
				return clients.get(i);
		}
		return null;
	}
	
	/**
	 * Metoda ce returneaza un entry din map atunci cand licitatia contine produsul cu id-ul
	 * cautat. Daca nu exista niciuna, o creeaza si adauga primul client
	 * @param productId
	 * @param client
	 * @return
	 */
	public Map.Entry<Auction, ArrayList<Client>> addClientToEntry(int productId, Client client) {
		for(Map.Entry<Auction, ArrayList<Client>> currentEntry : auctions.entrySet()) {
			if(currentEntry.getKey().getProductId() == productId) {
				currentEntry.getValue().add(client);
				return currentEntry;
			}
		}
		Auction auction = new AuctionBuilder().withId((int)(Math.random()*(99999 - 10000) + 10000))
					.withNoMaxSteps(5).withNoParticipants(6)
					.withProductId(productId).build();
		auctions.put(auction,  new ArrayList<Client>());
		auctions.get(auction).add(client);
		return new AbstractMap.SimpleEntry<Auction, ArrayList<Client>>(auction, auctions.get(auction));
	}
	
	/**
	 * Metoda ce distribuie participantii la o licitatie la cate un broker aleatoriu
	 * @param participants
	 */
	public void distributeParticipants(ArrayList<Client> participants) {
		for(int i = 0; i < participants.size(); i++) {
			int pos = (int)(Math.random()*(brokers.size() - 1));
			brokers.get(pos).Attach(participants.get(i));
		}
	}
	
	/**
	 * Metoda in care un anumit client isi exprima dorinta de a licita pentru un anumit produs
	 * @param clientId
	 * @param maxPrice
	 * @param productId
	 * @throws ProductNotFoundException
	 * @throws AuctionInterruptedException
	 */
	public void wantsToBid(int clientId, double maxPrice, int productId) throws ProductNotFoundException, AuctionInterruptedException{
		Client client = findClient(clientId);
		/*
		 * Se porneste un thread pentru citirea listei de produse de catre fiecare client
		 */
		Thread read = new Thread(() -> {
			try {
				client.wantsToBuy(maxPrice, productId, uniqueHouse);
			}
			catch (ProductNotFoundException e) {
				System.err.println(e.getMessage());
			}
			});
		read.start();
	
		/*
		 * Se adauga clientul in map-ul pentru licitatia respectiva
		 */
		Map.Entry<Auction, ArrayList<Client>> entry = addClientToEntry(productId, client);
		Auction auction = entry.getKey();
		/*
		 * Daca s-a ajuns la numarul de clienti dorinti, se distribuie clientii si incepe licitatia
		 */
		if(auction.getNoParticipants() == auctions.get(auction).size()) {
				ArrayList<Client> participants = entry.getValue();
				distributeParticipants(participants);
				startAuction(auction);
				getAuctions().remove(auction);
		}
	}
	
	/**
	 * Metoda ce creeaza o lista doar cu brokerii ce participa la o licitatie
	 * si isi anunta clientii ca a inceput licitatia
	 * @return lista cu brokerii activi
	 */
	public ArrayList<Broker> createActiveBrokersList(){
		ArrayList<Broker> activeBrokers = new ArrayList<Broker>();
		for(int i = 0; i < brokers.size(); i++) {
			if(brokers.get(i).getClients().size() > 0) {
				activeBrokers.add(brokers.get(i));
				brokers.get(i).Notify("Auction has started");
			}
		}
		return activeBrokers;
	}
	
	/**
	 * Metoda ce gaseste pretul maxim dintr-o lista de preturi
	 * @param prices
	 * @return pretul maxim
	 */
	public double findMax(ArrayList<Double> prices) {
		double max = 0;
		for(int i = 0; i < prices.size(); i ++) {
			if(max < prices.get(i))
				max = prices.get(i);
		}
		return max;
	}
	
	/**
	 * Metoda ce cauta clientul castigator, in functie de pozitia in lista a celei mai mari
	 * sume de bani oferita la ultimul pas
	 * @param activeBrokers
	 * @param pos
	 * @return clientul gasit sau null daca nu exista
	 */
	public Client winningClient(ArrayList<Broker> activeBrokers, int pos) {
		int count = 0;
		for(int i = 0; i < activeBrokers.size(); i++) {
			for(int j = 0; j < activeBrokers.get(i).getClients().size(); j++) {
				if(count == pos)
					return activeBrokers.get(i).getClients().get(j);
				count += 1;
			}
		}
		return null;
	}
	
	/**
	 * Metoda ce porneste o licitatie
	 * @param auction
	 * @throws AuctionInterruptedException exceptie ce este aruncata daca
	 * licitatia ramane fara participanti fara ca sa se fi incheiat numarul de pasi
	 */
	public void startAuction(Auction auction) throws AuctionInterruptedException {
		System.out.println("Auction for product with id " + auction.getProductId() + " has started");
		double max = 50;
		int pos = -1;
		Client winningClient = null;
		ArrayList<Broker> activeBrokers = createActiveBrokersList();
		for(int p = 0; p < auction.getNoMaxSteps(); p++) {
			ArrayList<Double> pricesOneStep = new ArrayList<Double>();
			System.out.println("\tStep " + p + ":");
			for(int i = 0; i < activeBrokers.size(); ) {
				/*
				 * Daca un broker a ramas fara clienti este scos din lista
				 */
				if(activeBrokers.get(i).getClients().size() == 0) {
					activeBrokers.remove(i);
				}
				else {
					brokers.get(i).askClientsToBid(max,  pricesOneStep);
					i++;
				}
			}
			if(pricesOneStep.size() == 0) {
				throw new AuctionInterruptedException("Auction Interrupted");
			}
			max = findMax(pricesOneStep);
			System.out.println("\t\tMax offer: $" + max);
			if(p == auction.getNoMaxSteps() - 1) {
				pos = pricesOneStep.indexOf(max);
			}
		}
		/*
		 * Daca nu s-a ajuns pana la final, se arunca exceptia pentru Licitatie intrerupta
		 */
		if(pos == -1) {
			throw new AuctionInterruptedException("Auction Interrupted");
		}
		winningClient = winningClient(activeBrokers, pos);
		System.out.println("\t" + winningClient.getName() + " is the winner\n");
		/*
		 * La final, brokerii atunta toti participantii inca ramasi ca s-a incheiat licitatia
		 */
		endAuction(max, auction, winningClient, activeBrokers);
		
	}
	
	/**
	 * Metoda ce apeleaza metoda prin care brokerii isi anunta clientii ca s-a terminat licitatia
	 */
	public void endAuction(double max, Auction auction, Client winningClient, ArrayList<Broker> activeBrokers) {
		for(int i = 0; i < activeBrokers.size(); i++) {
			activeBrokers.get(i).announceEndOfAuction(max, winningClient, auction, this);
		}
	}
	
	/**
	 * Metoda ce calculeaza pretul maxim 
	 * @param max
	 * @param offeredPrice
	 * @return pretul maxim
	 */
	public double maxOfferedPrice(double max, double offeredPrice) {
		if(max > offeredPrice) {
			return max;
		}
		return offeredPrice;
	}
	
}
