package auction;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import auction.*;

public final class Auction implements BlockedSession, Serializable{
	
	private Seller seller;
	private Item item;
	private double startAmount;
	private double reserveAmount;
	private LocalDateTime closeDate;
	private	Status status;
	
	
	//Auction Constructor 
	public Auction(Double startAmount, Double reserveAmount, LocalDateTime selectStartDate, Status status, Item item, Seller seller) {
		this.startAmount = startAmount;
		this.reserveAmount = reserveAmount;
		this.closeDate = selectStartDate;
		this.status = status;
		this.item = item;
		this.seller = seller;
		
		
}
	@Override	
	public String toString() {
		return String.format("The Item : %s \nStarting Price : £%.2f \nReserve Price : £%.2f \n"+"Closing Date : " + closeDate
				+ "\nSeller : " + Seller.class.cast(seller) + "\n", item, startAmount, reserveAmount);
	}

public void placeBid(double amount, Buyer buyer, LocalDateTime when) {
	new Bid(amount, buyer, when);
}

public Status verify() {
	
	return status = Status.PENDING;
	
}

public synchronized void close() {
	status = Status.CLOSED;
	
	Bid highest;
	
	if((highest = getHighestBid()) != null) {
		((Buyer) highest.getWho()).victory(this);
	}
}

private Bid getHighestBid() {
	return null;
  }

public Status getStatus() {
	return status;
}

public void setStatus(Status status) {
	// TODO Auto-generated method stub
	this.status = status;
}

public boolean isBlocked() {
	if(Status.BLOCKED != null) {
		return true;
	}
	return false;
}




public void activate() {
	status = Status.ACTIVE;
}



public void setStartPrice(double startAmount) {
	this.startAmount = startAmount;
}
public Item getItem() {
	// TODO Auto-generated method stub
	return item;
}
public LocalDateTime getCloseDate() {
	return closeDate;
}

public synchronized String getCloseDateFormat() {
	return closeDate.format(DateTimeFormatter.ofPattern("dd MM yy HH:mm"));
	
}

public void setCloseDate(LocalDateTime closeDate) {
	this.closeDate = closeDate;
}
public Double getStartPrice() {
	// TODO Auto-generated method stub
	return null;
}
public void setUnBlocked() {
	// TODO Auto-generated method stub
	this.status = Status.ACTIVE;
}



}