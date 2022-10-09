public class PaymentCash extends Payment {
	//**************************** DO NOT MODIFY **********************************//

	private double cashTendered;	// Cash tendered is a sum of money given in payment. It may not be equal to the exact amount owed.
	private double change;			// If the cash tendered is more than the amount, the change have to be calculated

	//*****************************************************************************//

	/**
	 * Constructor initializes the payment method's name as "CASH", paid amount, and cash tendered.
	 * The instant file "change" is set to 0 (no change is calculated yet)
	 * @param amount amount
	 * @param cash tendered
	 */
	public PaymentCash(double amount, double cash) { //amount -> price that you have to pay  //cash -> balance
		//******************* YOUR CODE HERE ******************
		super("CASH",amount);
		this.cashTendered = cash;
		change = 0;
		//*****************************************************
	}

	/**
	 * If the cash tendered is more than the amount, the change have to be calculated and return true
	 * Otherwise, print the error message "Sorry, you have insufficient balance!" (Payment.ERROR) and return false
	 */
	public boolean paid() {
		//******************* YOUR CODE HERE ******************
		if(cashTendered > amount){
			change = cashTendered - amount;
			return true;
		}else {
			System.out.println(Payment.ERROR);
			return false;
		}
		//*****************************************************
	}


	/**
	 * @Override paymentInformation
	 * @return string to provide information of this payment
	 * *** note. you have a freedom to design your output here ***
	 */
	public String paymentInformation() {
		//******************* YOUR CODE HERE ******************
		return this.toString() + "^_^";
		//*****************************************************

	}





	//**************************** DO NOT MODIFY **********************************//
	public double getChange() {
		return this.change;
	}

	public String toString() {
		return "CASH::" + cashTendered;
	}
	//*****************************************************************************//

}
