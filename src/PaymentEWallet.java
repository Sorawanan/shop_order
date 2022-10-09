public class PaymentEWallet extends Payment implements Authorization {

	//**************************** DO NOT MODIFY **********************************//
	private String user;		// username to verify this payment transaction
	private String pwd;			// password to verify this payment transaction
	private EWallet wallet;		// EWallet associated with this payment transaction

	//*****************************************************************************//


	/**
	 * Constructor initializes the payment method's name as "EWALLET", and paid amount as given amount value
	 * Then initializes wallet, user, and password as a plain text
	 * @param amount (amount to be paid)
	 * @param wallet (EWallet object related to this payment)
	 * @param user   (given user to authorize this payment)
	 * @param pwd	 (given pwd to authorize this payment)
	 */
	public PaymentEWallet(double amount, EWallet wallet, String user, String pwd) {
		//******************* YOUR CODE HERE ******************
		super("EWALLET", amount);
		this.wallet = wallet;
		this.user = user;
		this.pwd = pwd;
		//*****************************************************
	}

	/**
	 * If the payment is authorized and the wallet has sufficient balance, the paid amount will be deducted from the wallet and return true.
	 * If the payment is authorized but the wallet has insufficient balance, print payment error message and return false.
	 * If the payment is unauthorized, do nothing and return false
	 */

	//@Override
	public boolean paid() {
		//******************* YOUR CODE HERE ******************
		if(authorize() && wallet.getBalance() >= amount){
			wallet.deductBalance(amount);
			return true;
		}else if(authorize() && wallet.getBalance() < amount){
			System.out.println(Payment.ERROR);
			return false;
		}
		else return false;
		//*****************************************************
	}


	/**
	 * To authorize this payment, the user and pwd have to be checked against the username and password of the wallet
	 * If both username and password are matched, this returns true.
	 * Otherwise, print authorization error message and return false
	 */

	//@Override
	public boolean authorize() {
		//******************* YOUR CODE HERE ******************
		if(this.user.equals(wallet.getUserName()) && this.pwd.hashCode() == wallet.getEncodedPassword())
			return true;
		else{
			System.out.println(Payment.ERROR);
			return false;
		}
		//*****************************************************
	}




	//**************************** DO NOT MODIFY **********************************//
	public String toString() {
		return "EWALLET::" + wallet.getCustID();
	}
	//**************************** DO NOT MODIFY **********************************//

}
