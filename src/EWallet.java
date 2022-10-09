//GROUP 12
//Student ID: 6388012, 6388019, 6388022
//Section 1


public class EWallet implements Loggable{

	//**************************** DO NOT MODIFY **********************************//
	private int custID;			// customer's ID who owns this wallet
	private String username;
	private int password;		// password will be encoded using hashCode()
	private double balance;		// current balance in this wallet

	//*****************************************************************************//

	/**
	 * Constructor initializes customer's ID, username, encoded password, and balance
	 * @param custID
	 * @param user
	 * @param encodedPwd
	 * @param balance
	 */
	public EWallet(int custID, String user, int encodedPwd, double balance) {

		//******************* YOUR CODE HERE ******************
		this.custID = custID;
		this.username = user;
		this.password = encodedPwd;
		this.balance = balance;
		//*****************************************************
	}

	/**
	 * Similar to the previous constructor, but the password is provided as the plain text.
	 * The plain text password have to be encoded before assigned to password instance field.
	 * @param custID
	 * @param user
	 * @param plainPwd
	 * @param balance
	 */
	public EWallet(int custID, String user, String plainPwd, double balance) {

		//******************* YOUR CODE HERE ******************
		this.custID = custID;
		this.username = user;
		this.password = plainPwd.hashCode();
		this.balance = balance;
		//*****************************************************
	}

	/**
	 * Increase the balance by the given amount and return true.
	 * If the amount is zero or negative,
	 * print a message ("Cannot add balance. Incorrect amount!") and return false.
	 * @param amount to be added
	 * @return boolean
	 */
	public boolean addBalance(double amount){
		//******************* YOUR CODE HERE ******************
		if(amount <= 0){
			System.out.println("Cannot add balance. Incorrect amount!");
			return false;
		}else {
			this.balance += amount;
			return true;
		}
		//*****************************************************
	}

	/**
	 * Decrease the balance by the given amount and return true.
	 * If the amount is zero or negative,
	 * print a  message ("Cannot deduct balance. Incorrect amount!") and return false
	 * @param amount to be deducted
	 * @return boolean
	 */
	public boolean deductBalance(double amount){

		//******************* YOUR CODE HERE ******************
		if(amount <= 0){
			System.out.println("Cannot deduct balance. Incorrect amount!");
			return false;
		}else {
			this.balance -= amount;
			return true;
		}
		//*****************************************************

	}

	public String log(){
		return this.getCustID()+","+this.getUserName()+","+this.getEncodedPassword()+","+df.format(this.getBalance());
	}

	//**************************** DO NOT MODIFY **********************************//

	public int getCustID() {
		return custID;
	}

	public String getUserName() {
		return username;
	}

	public int getEncodedPassword() {
		return password;
	}

	public double getBalance(){
		return balance;
	}

	@Override
	public String toString(){
		return "CID::" + custID
				+ ",username::" + username
				+ ",encodedpassword::" + password
				+ ",balance::" + Loggable.df.format(balance);
	}

	//*****************************************************************************//

}