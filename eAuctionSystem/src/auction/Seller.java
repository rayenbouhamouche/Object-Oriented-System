package auction;

import java.util.LinkedList;
import java.util.List;



public class Seller extends User implements BlockedSession {
	//Create Variables 
	private Status status;
	private boolean blocked = true;
	private List<Item> items = new LinkedList<Item>();
	
	//Create Constructor 
	public Seller(String username, String password) throws Exception {
		// TODO Auto-generated constructor stub
		super (username, password);
	}

	
	//Methods
	public static boolean isBlocked() {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean setBlocked() {
		return blocked;
	}

	public List<Item> getItems() {
		// TODO Auto-generated method stub
		return items;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}

	public Status getStatus() {
		// TODO Auto-generated method stub
		return status;
	}
	
	public Item getItemByDescription(String description) {
		for (Item items : items) {
				return items;
			}
		return null;
	}

	

}
