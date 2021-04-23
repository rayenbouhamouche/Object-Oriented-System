package auction;

import java.io.Serializable;

/**
 * <i> User class is abstract. </i>
 * @author Rayen Bouhamouche, Matty Johnson, Harry Wolfendale
 * @see <I>Seller</i>
 * @see <i>Buyer</i>
 */
public class User implements Serializable {
	
	//Create Variables 
	protected String username;
	protected String password;
	
	//User Constructor
	public User(String username, String password) throws Exception {
		this.username = username;
		if(isValid(password)) {
			this.password = password;
		} else {
			throw new Exception ("Wrong Password, Try Again Please");
		}
	}
	
	//Methods 
	@Override
	public String toString() {
		return username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		String notShowing="";
		for (int i = 0; i < this.password.length(); i++) {
			notShowing += "*";
		}
		return notShowing;
	}

	public void setPassword(String password) {
		this.password = password;
	}
//Java Doc here maybe
	public boolean checkPassword(String password) {
		if(this.password.equals(password)) {
			return true;
		} else {
			return false;
		}
	}
	
	protected boolean isValid (String password) {
		if (password.length()>=3) return true;
		return false; 
	}

	

}