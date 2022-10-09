//**************************** DO NOT MODIFY THIS CLASSS**********************************//


public abstract class Payment {
	
	public double amount;	// amount to be paid
	public String method;	// name of the payment method e.g., CASH, CARD, EWALLET
	
	public static final String ERROR = "Sorry, you have insufficient balance!";  
							// example of error message
	
	/**
	 * Constructor initializes the payment method's name and amount to be paid
	 * @param method
	 * @param amount
	 */
	public Payment(String method, double amount){
		this.method = method;
		this.amount = amount;
	}
	
	public String paymentInformation() {
		return "Method: " + method + ", Amount: " + amount;
	}
	
	public String toString() {
		return method + "::" + amount;
	}
	
	/**
	 * Abstract method paid()
	 * @return boolean
	 */
	public abstract boolean paid();
	
}

//*****************************************************************************//

