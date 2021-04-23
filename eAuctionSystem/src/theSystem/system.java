package theSystem;

//import the important files to run the program 

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import auction.*;

//start system
public class system  {

	// Scanner and path for string data  
	private static final Scanner Scan = new Scanner(System.in);
	//Location of the folder for the data file
	private static final String PATH="C:\\Data\\";
	
	//arrays 
	private List<User> users = new LinkedList<User>();
	private  List<Auction> auctions = Collections.synchronizedList(new LinkedList<Auction>());
	private List<Item> items = Collections.synchronizedList(new LinkedList<Item>());
	private List<Bid> bids = Collections.synchronizedList(new LinkedList<Bid>());
	
	//Variables 
	private Seller seller;
	private Buyer buyer;
	private Admin admin;
	
	public system() throws Exception {
		try {
			
			deSerialise();
			
			//Create Users
			 users.add(new Seller("Sorren", "_Sorren")); 
			 users.add(new Seller("Glyn", "_Glyn"));
			 users.add(new Buyer("Mark", "_Mark")); 
			 users.add(new Buyer("Kirsty", "_Kirsty"));
			 users.add(new Buyer("Andy", "_Andy"));
			 users.add(new Admin("Admin", "_Admin"));
		
			// Items
			 Seller.class.cast(users.get(0)).getItems().add(new Item("Apple", "Fruit", "Liverpool", Status.NEW));
			 Seller.class.cast(users.get(0)).getItems().add(new Item("Orange", "Fruit", "Liverpool", Status.NEW));
			 Seller.class.cast(users.get(0)).getItems().add(new Item("Plum", "Fruit", "Liverpool", Status.NEW));
			
			// Active Auction
			
			 auctions.add(new Auction(3.00, 5.00, LocalDateTime.now().plusSeconds(70),
		     Status.ACTIVE, new Item("Apple", "Fruit", "Liverpool", Status.NEW), Seller.class.cast(users.get(1))));
			 
			 auctions.add(new Auction(1.50, 2.50, LocalDateTime.now().plusSeconds(70),
			 Status.ACTIVE, new Item("Orange", "Fruit", "Liverpool", Status.NEW), Seller.class.cast(users.get(1))));
			 
			
			// Pending Auction
			
			auctions.add(new Auction(3.00, 5.00, LocalDateTime.now().plusSeconds(120),
			 Status.PENDING, new Item("Apple", "Fruit", "Liverpool", Status.NEW), Seller.class.cast(users.get(1))));
			
			auctions.add(new Auction(1.50, 2.50, LocalDateTime.now().plusSeconds(120),
			Status.PENDING, new Item("Orange", "Fruit", "Liverpool", Status.NEW), Seller.class.cast(users.get(1))));
			
			//Closed Auction
			 auctions.add(new Auction(4.50, 6.50, LocalDateTime.now().plusSeconds(120),
			 Status.CLOSED, new Item("Plum", "Fruit", "Liverpool", Status.NEW), Seller.class.cast(users.get(1))));
			
			//bids
			  auctions.get(0).placeBid(3.50, Buyer.class.cast(users.get(2)),
			  LocalDateTime.now());
			  
			 
		} catch (Exception e) {
			System.out.print(e.getMessage()+ "\\n");
		}
		System.out.println("-- eAuction System --");
		System.out.println();

		String choice = "";
		do {
			//Program main menu
			System.out.println("-- MAIN MENU --");
			System.out.println("1 - [S]etup Account");
			System.out.println("2 - [L]og In");
			System.out.println("3 - [B]rowse Auctions");
			System.out.println("4 - [Q]uit");
			System.out.print("Pick : ");

			choice = Scan.next().toUpperCase();

			switch (choice) {
			
			// User Setup Account
			case "1":
			case "S": {
				String username, password, type;
				System.out.println("Create an account please!");
				System.out.println("Please enter your username! : ");
				username = Scan.next();
				
				System.out.println("Please enter your password!");
				password= Scan.next();
				System.out.println("choose your type between [S]eller or [B]uyer!");
				type=Scan.next();
				if (type.toUpperCase().charAt(0) == 'S') {
					type = "Seller";
				}
				else if (type.toUpperCase().charAt(0) == 'B') {
					type = "Buyer";
				}
				else {
					System.out.println("\n Invalid type !\n");
					break;
				}
				
				setupAccount(username,password,type);
				break;
			} 
			
			//User Login
			case "2":
			case "L": {
				logIn();
				break;
			}
			
			case "3":
			case "B": {
				browseAuction();
				break;
			}
		}
	}while (!choice.equals("Q") & !choice.equals("4"));
		Scan.close(); //closing Scanner 
		System.out.println("\n-- GOODBYE --");
		serialise();
		System.exit(0);
	}
	
	
	public void setupAccount(String username, String password, String type) throws Exception {
		// TODO Auto-generated method stub
		
		if (type.equals("Seller")) {
			users.add(new Seller(username, password));
		}
		if (type.equals("Admin")) {
			users.add(new Admin(username, password));
		}
		if (type.equals("Buyer")) {
			users.add(new Buyer(username, password));
		}

		
		
		// user.add(new User(newUsername, newPassword));
		System.out.println("\n--  Your account has been succefully created --\n");
	}
	
	
	private void logIn() {
		
		System.out.print("Please Enter Your Username : ");
		String username = Scan.next();

		User user = getUsername(username);
		//A check to see if the username entered is an actual account in the system
		if (user == null) {
			System.out.println("User not found!");
			logIn();
		
		}
		System.out.print("Thanks "+username+", Now Enter Your Password : ");
		String password = Scan.next();
		
		
		try {

			if (user != null) {
				if (user.checkPassword(password)) {
					if (Seller.class.isInstance(user)) {
						seller = Seller.class.cast(user);
						if (!Seller.isBlocked()) {
							sellerMenu();
						} else {
							System.out.println("-- THIS SELLER IS BLOCKED --\n");
						}
					} else if (Buyer.class.isInstance(user)) {
						buyer = Buyer.class.cast(user);
						buyerMenu();
					} else if (Admin.class.isInstance(user)) {
						admin = Admin.class.cast(user);
						adminMenu();
					}
				}
			} else {
				System.out.println("-- PLEASE ENTER YOUR VALID DETAILS --\n");
			}
		} catch (Exception e) {
			System.out.println("");
		}

	}
	
		
	public void browseAuction() {
		System.out.print("\n--- BROWSING THE ACTIVE AUCTIONS --- \n");
		System.out.println("");
		List<Auction> auctions = Collections.synchronizedList(new LinkedList<Auction>());
		
		//Loop through all auctions & Add active auctions to list
		for (Auction auction : this.auctions) {
			if (auction.getStatus().equals(Status.ACTIVE)) {
				auctions.add(auction);
			}
		}
		if (auctions.isEmpty()) {
			System.out.println("\n--- NO ACTIVE AUCTIONS IN PLACE ---\n");
		}

		for (Auction auction : auctions) {
			System.out.println(auction.toString());
		}
	}


	public void sellerMenu() throws Exception {
		String menuChoice;
		do {
			System.out.println("\n- SELLER MENU --");
			System.out.println("1 - [C]reate an Item");
			System.out.println("2 - [S]tart an Auction");
			System.out.println("3 - [V]iew Bid Current Bid on Auction");
			System.out.println("4 - [P]ending Auctions");
			System.out.println("5 - [Q]uit");
			System.out.print("Pick : ");
			
			menuChoice = Scan.next().toUpperCase();
			
			switch (menuChoice) {
			case "1":
			case "C": {
				createItem();
				break;
			}
			case "2":
			case "S": {
				placeAuction();
				break;
			}
			case "3":
			case "V": {
				viewBidAuction();
				break;
			}
			case "4":
			case "P": {
				pendingAuction();
				break;
			}
			}
		} while (!menuChoice.equals("Q") & !menuChoice.equals("5"));
		System.out.println("");
	}
	

	private void createItem() throws Exception {
		System.out.print("Enter your item's name : ");
		String name = Scan.next(); 
		
		System.out.print("Enter your item description ");
		String description = Scan.next();
		
		System.out.print("where does your item locate at? ");
		String location = Scan.next();
		
		System.out.print("Is Your Item: [N]ew, [U]sed ");
		String itemStatus= Scan.next();
		Status itemsStatus;
		
		//status of the item 
		for (Item item : this.items) {
			if (item.getStatus().equals(Status.NEW)) {
				items.add(item);
			}
			if (item.getStatus().equals(Status.USED)) {
				items.add(item); // added to the list of items
			}
			if (items.isEmpty()) {
				System.out.print("\n-- THERE IS NO ITEM AVAILABLE --\n");
				return;
			} 
		}
		
		
		System.out.println("What's the starting amount you wishing for ?");
		String startPrice = Scan.next();
		double startAmount;
		startAmount = Double.parseDouble(startPrice);
			
		System.out.printf("what's your closing date? (format should be like this -> dd-MM-yyyy HH:mm -> example 19-04-2020 17:00): ");
		String closingDate = Scan.nextLine();
		LocalDateTime closeDate;
		try {
			DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
			closeDate = LocalDateTime.parse(closingDate, format);
		}
		catch(Exception e) {
			System.out.println("Date format not correct");
			sellerMenu();
		}
		
		try {
			Seller currentUser = null;
			
			placeAuction();
		}
		catch (Exception e) {
			System.out.println(e.toString()); 
		}				
		
		System.out.println("Auction Created!");		
	}
	

	private  void placeAuction() throws Exception {
		try {
			String selectedItem;
			do {
				if (Seller.class.cast(seller).getItems().isEmpty()) {
					System.out.println("\n-- THE ITEM HAS NOT BEEN CREATED--");
					return;
				} else {

					System.out.println("\n" + Seller.class.cast(seller).getItems());

					System.out.println("-- CHOOSE FROM THE ITEM LIST --");
					System.out.print("Item : ");
					selectedItem = Scan.next();
				}
			} while (!selectedItem.equals(Item.getDescription()));

			System.out.print("Start Price : ");
			Double priceChoosed = Double.parseDouble(Scan.next());

			System.out.print("Reserve Price  : ");
			Double reservePriceTaken = Double.parseDouble(Scan.next());

			System.out.print("Closing Date [E.g 15-Apr-2019-12:00] : ");
			LocalDateTime closeDateChoosed = LocalDateTime.parse(Scan.next(),
			DateTimeFormatter.ofPattern("dd-MMM-yyyy-HH:mm"));

			Auction auction = new Auction(priceChoosed, reservePriceTaken, closeDateChoosed, Status.PENDING,
					new Item(selectedItem, null, null, null), Seller.class.cast(seller));

			System.out.print("Activate Auction? [Y/N] : ");
			String choice = Scan.next().toUpperCase();
			do {
				if (choice.equals("Y")) {
					auction.activate();
					auctions.add(auction);
					System.out.print("\n-- AUCTION ACTIVATED --\n");
					return;
				} else if (choice.equals("N")) {
					auction.verify();
					auctions.add(auction);
					System.out.print("\n-- AUCTION NOT ACTIVATED --\n");
					return;
				}
			} while (!choice.equals("Y") || choice.equals("N"));
			// }
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}

	private void viewBidAuction() {
		// TODO Auto-generated method stub
		System.out.print("\n-- BIDING ON AUCTIONS -- \n");
		List<Auction> auctions = new LinkedList<Auction>();

		for (Auction auction : this.auctions) {
			if (auction.getStatus().equals(Status.BID)) {
				auctions.add(auction);
			}
		}
		if (auctions.isEmpty()) {
			System.out.println("\n-- THERE IS NO BID ON THIS AUCTION--\n");
		}

		for (Auction auction : this.auctions) {
			System.out.println(auctions.toString());
		}
	}
	
	
	private void pendingAuction() {
		// TODO Auto-generated method stub
		System.out.print("\n--THE PENDING AUCTIONS -- \n");
		List<Auction> auctions = new LinkedList<Auction>();

		for (Auction auction : this.auctions) {
			if (auction.getStatus().equals(Status.PENDING)) {
				auctions.add(auction);
			}
		}
		if (auctions.isEmpty()) {
			System.out.print("\n-- THERE IS NO PENDING AUCTIONS--\n");
			return;
		}

		for (Auction auction : auctions) {
			System.out.print("\n" + auction.toString());
		}

		String item;
		do {
			System.out.println("CASE SENSETIVE");
			System.out.print("Please Select Auction Via Its DESCRIPTION: ");
			item = Scan.next();

		} while (!item.equals(Item.getDescription()));

		for (Auction auction : auctions) {
			System.out.print("Would you like to activate the auction? [Y/N] : ");
			String choice = Scan.next().toUpperCase();
			do {
				if (choice.equals("Y")) {
					auction.activate();
					auctions.add(auction);
					System.out.print("\n-- AUCTION ACTIVATED --\n");
					return;
				} else if (choice.equals("N")) {
					auction.setStatus(Status.PENDING);
					auctions.add(auction);
					System.out.print("\n-- AUCTION DISACTIVATED --\n");
					return;
				}
			} while (!choice.equals("Y") || choice.equals("N"));
		}
	}
	

	public Auction getAuctionByItemName(String itemName) {
		for (Auction auction : this.auctions) {
			if (auction.getItem().getName().equals(itemName)) return auction;
		}
		return null;
	}
	private void buyerMenu() {
		// TODO Auto-generated method stub
		String menuChoice;
		do {
			System.out.println("\n-- BUYER MENU --");
			System.out.println("1 - [B]id On Auction");
			System.out.println("2 - B[R]owse Auctions");
			System.out.println("3 - [V]iew Won Auctions");
			System.out.println("4 - [Q]uit");
			System.out.print("Choose : ");

			menuChoice = Scan.next().toUpperCase();

			switch (menuChoice) {
			case "1":
			case "B": {
				biddingAuctions();
				break;
			}
			case "2":
			case "R": {
				browseAuction();
				break;
			}
			case "3":
			case "V": {
				viewWonAuction();
				break;
			}
			}
		} while (!menuChoice.equals("Q") & !menuChoice.equals("4"));
		System.out.println("");
		}



	public void biddingAuctions() {
		// TODO Auto-generated method stub
		for (Auction auction : auctions) {
			if (auction.getStatus().equals(Status.ACTIVE)) {
				System.out.println("\n" + auction.toString());
			}
		}
		String selectItem;
		String selectSeller;
		do {
			System.out.print("Select Item Via Description: ");
			selectItem = Scan.next();

		} while (!selectItem.equals(Item.getDescription()));

		do {
			System.out.print("Select Seller : ");
			selectSeller = Scan.next();
		} while (selectSeller.equals(Seller.class.isInstance(users)));
		System.out.print("Select Price : ");
		Double amount = Double.parseDouble(Scan.next());

		for (Auction auction : auctions) {
			if (amount >= auction.getStartPrice()) {
				auction.placeBid(amount, buyer, LocalDateTime.now());
				auction.setStatus(Status.BID);
				System.out.println("\n-- BID PLACED --");
			} else {
				System.out.println("\n-- BID NOT PLACED --");
			}
		}
	}
	
	private void viewWonAuction() {
		
	}	

	public void adminMenu() {
		// TODO Auto-generated method stub
		String menuChoice;

		do {
			System.out.println("\n-- ADMIN MENU --");
			System.out.println("1 - [B]lock Seller");
			System.out.println("2 - Block [A]uction");
			System.out.println("3 - [V]iew Blocked User"); 
			System.out.println("4 - [Q]uit");
			System.out.print("Pick : ");

			menuChoice = Scan.next().toUpperCase();

			switch (menuChoice) {
			case "1":
			case "B": {
				blockUsernameUser();
				break;
			}
			case "2":
			case "A": {
				browseAuction();
				break;
			}
			case "3":
			case "V": {
				viewBlockedUsers(); // ERROR
				break;
			}
		  }
		} while (!menuChoice.equals("Q") & !menuChoice.equals("4"));
		System.out.println("");
	}
	
	@SuppressWarnings("unlikely-arg-type")
	private void blockUsernameUser() {
		// TODO Auto-generated method stub
		System.out.print("What's the seller Username? : ");
		String username = Scan.next();

		User user = getUsername(username);
		do {
			if (Seller.class.isInstance(user)) {
				System.out.println(user.toString());
				break;
			} else {
				System.out.print("\n-- SELLER NOT FOUND! --\n");
				return;
			}
		} while (!username.equals(Seller.class.isInstance(user)));

		System.out.print("Block? [Y/N] : ");
		String choice = Scan.next();
		if (choice.equals("Y")) {
			Seller.class.cast(user).setStatus(Status.BLOCKED);
			System.out.print("\n-- BLOCKED SELLER / BUYER--\n");
			return;
		} else {
			System.out.print("\n-- SELLER / BUYER NOT BLOCKED --\n");
		}
	}
	


	public void viewAuction() {
		System.out.print("\n--- BID ON CURRENT AUCTIONS --- \n");
		List<Auction> auctions = new LinkedList<Auction>(); //array for auctions

		for (Auction auction : this.auctions) {
			if (auction.getStatus().equals(Status.BID)) {
				auctions.add(auction); // add auction to the list
			}
		}
		if (auctions.isEmpty()) {
			System.out.println("\n--- NO BID ON AUCTIONS ---\n");
		}

		for (Auction auction : this.auctions) {
			System.out.println(auctions.toString());
		}
	}
	
	private void viewBlockedUsers() {

		System.out.println("\n-- BLOCKED SELLERS --");

		if (Seller.class.cast(users).getStatus().equals(Status.BLOCKED)) {
			System.out.println(users.toString());
		}
	}
	
	private User getUsername(String username) {

		for (User user : users) {
			if (user.getUsername().equals(username)) {
				return user;
			}
		}
		return null;
	}
	

	
	private Object LocalDateTime() {
		// TODO Auto-generated method stub
		return null;
	}

		public void run() throws Exception {
			// TODO Auto-generated method stub
			new system();
			
		}
		public void deSerialise() {
			ObjectInputStream ois;

			try {
				ois = new ObjectInputStream(new FileInputStream(PATH + "users.ser"));
				users = (List<User>) ois.readObject();
				ois.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

		}

		public void serialise() {
			ObjectOutputStream oos;

			try {
				oos = new ObjectOutputStream(new FileOutputStream(PATH + "users.ser"));
				oos.writeObject(users);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

		
	
	}
			

