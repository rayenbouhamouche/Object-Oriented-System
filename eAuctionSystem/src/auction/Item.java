package auction;

import java.io.Serializable;

public class Item implements Serializable{
	//Create Variables
	private String name;
	private static String description;
	private String location;
	private Status status;
	
	//Create Constructor
	public Item (String name, String description, String location, Status status) {

	this.setName(name);
	this.setDescription(description);
	this.setLocation(location);
	this.setStatus(status);
}
	//Methods
	public String toString() {
		return name;
	}
	
	public String getName() {
		return this.name;	
	}
	
	public static String getDescription() {
		return description;
	}
	
	public String getLocation() {
		return this.location;
	}
	public Status getStatus() {
		return this.status;
	}
	
	
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	public void setLocation(String location	) {
		this.location = location;
	}
	public static void setStatus(Status status) {
		status = status;
	}

	
}
			