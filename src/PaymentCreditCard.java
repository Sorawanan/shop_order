//GROUP 12
//Student ID: 6388012, 6388019, 6388022
//Section 1

public class PaymentCreditCard extends Payment implements Authorization {

	//**************************** DO NOT MODIFY **********************************//

	private CreditCard card;	// CredtiCard information for this payment

	//*****************************************************************************//

	/**
	 * Constructor initializes the payment method's name as "CARD", and paid amount as given amount value
	 * Then initialize the card instance field as a new CreditCard object with the given card's number, and card's type.
	 * @param amount (paid amount)
	 * @param number (credit card's number)
	 * @param type   (credit card's type)
	 */
	public PaymentCreditCard(double amount, String number, CreditCard.CardType type) {
		//******************* YOUR CODE HERE ******************
		super("CARD",amount);
		card = new CreditCard(number,type);
		//*****************************************************

	}

	/**
	 * Constructor initializes the payment method's name as "CARD", paid amount as given amount value, and card as given credit card
	 * @param amount
	 * @param c
	 */
	public PaymentCreditCard(double amount, CreditCard c) {
		//******************* YOUR CODE HERE ******************
		super("CARD",amount);
		card = c;
		//*****************************************************
	}

	/**
	 * If the payment is authorized and the paid amount is less than or equal to the CARDLIMIT, return true
	 * Otherwise, print the payment error message and return false.
	 */
	public boolean paid() {
		//******************* YOUR CODE HERE ******************
		if(authorize() && amount<= CreditCard.CARDLIMIT){
			return true;
		}else {
			System.out.println(Payment.ERROR);
			return false;
		}
		//*****************************************************
	}


	/**
	 * Unlike read-world case, if the card information is valid (see CreditCard class), return true.
	 * If the card is invalid, print the authorization error message and return false.
	 */
	//@Override
	public boolean authorize() {
		//******************* YOUR CODE HERE ******************
		if(card.isValid()) return true;
		else {
			System.out.println(Payment.ERROR);
			return false;
		}
		//*****************************************************
	}






	//**************************** DO NOT MODIFY **********************************//
	public String toString() {
		return "CARD::" + card.getNumber();
	}
	//*****************************************************************************//

}