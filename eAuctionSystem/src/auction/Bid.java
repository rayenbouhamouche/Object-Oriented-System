package auction;

import java.io.Serializable;
import java.time.LocalDateTime;
import auction.*;

public class Bid implements Serializable {
	//Create Variables
	private double amount;
	private Buyer who;
	private LocalDateTime when;

	//Create Constructor 
	public Bid(double price, Buyer who, LocalDateTime when) {
		this.amount= price;
		this.who= who;
		this.when= when;
	}
	
	//Methods
	public String toString() {
		return "Bid [Price=" + amount + ", who=" + who + ", when=" + when + "]";
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Buyer getWho() {
		return who;
	}

	public void setWho(Buyer who) {
		this.who = who;
	}

	public LocalDateTime getWhen() {
		return when;
	}

	public void setWhen(LocalDateTime when) {
		this.when = when;
	}

}
