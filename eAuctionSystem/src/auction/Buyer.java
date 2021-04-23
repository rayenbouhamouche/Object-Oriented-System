package auction;

import java.util.LinkedList;
import java.util.List;
import auction.*;

public final class Buyer extends User {

	//Array list 
	private List<Auction> wins = new LinkedList<Auction>();
	private List<Bid> bids = new LinkedList<Bid>();
	
	public Buyer(String username, String password) throws Exception {
		super (username, password);
	}
	//Methods
public void victory(Auction auction) {
	
	}
	
	public List<Bid> getBids() {
		for (Bid bid : bids) {
			return bids;
		}
		return null;
	}
	

}
