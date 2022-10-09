import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Order implements Loggable {

	//**************************** DO NOT MODIFY **********************************//
	public static int runningID = 0;					// static variable for assigning a unique ID to an order
	public static final double TAX_RATE = 0.07;			// fixed tax rate
	public static final double SHIPPING_RATE = 0.50;	// fixed shipping rate

	public enum Status{PENDING, PAID, VOIDED};			// Payment status of an order

	private int orderID;			// unique order ID
	private Customer customer;		// customer object who place this order
	private ArrayList<Item> items = new ArrayList<Item>();	// list of items
	private double subTotal = 0.0;				// sub total of this order
	private double tax = 0.0;					// tax amount
	private double shippingFee = 0.0;			// shipping fee (for online customer only)
	private double grandTotal = 0.0;			// summation of sub total, tax, and shipping fee
	private Status paymentStatus = Status.PENDING;	// the payment status
	private String paymentMethod = "UNKNOWN";		// if payment is pending, the method is unknown yet

	//*****************************************************************************//
	/**
	 * Constructor to initialize orderID according to the running ID
	 * The first order's ID is 1. The second order's ID is 2, and so on.
	 */
	public Order() {
		//******************* YOUR CODE HERE ******************
		this.orderID = ++runningID;
		//*****************************************************
	}

	/**
	 * Constructor to initialize orderID as the given orderID parameter
	 * If the provided order ID is larger than the running ID, the running ID will be set to equal to that provided ID.
	 * @param orderID
	 */

	public Order(int orderID) {
		//******************* YOUR CODE HERE ******************
		this.orderID = orderID;
		if(orderID > runningID){
			runningID = orderID;
		}
		//*****************************************************
	}

	/**
	 * Set a Customer object based on the given customer's id (cid) to the customer instance field.
	 * The customer variable can be set if the Customer object was found in the DataManagement.customerData
	 *
	 * @param cid
	 * @return return true if the customer can be set, otherwise return false
	 */
	public boolean setCustomerID(int cid) {
		//******************* YOUR CODE HERE ******************
		for(Customer eachCustomer : DataManagement.customerData.values()){
			if(cid == eachCustomer.getCustID()){
				this.customer = eachCustomer;
				return true;
			}
		}
		return false;
		//*****************************************************
	}


	/**
	 * For the new order, an item will be added into the items list and decrease the quantity of item in stock by one.
	 * The item can be added to the list if the item is found in the stockData and it is available.
	 *
	 * @param itemName is the name of item
	 * @return return true if the item can be added, otherwise return false.
	 */
	public boolean addItem(String itemName){
		//******************* YOUR CODE HERE ******************
		for(Item eachItem : DataManagement.stockData.values()){
			if(itemName.equals(eachItem.getName()) && eachItem.isAvailable()){
				items.add(eachItem);
				eachItem.sold();
				return true;
			}
		}
		return false;
		//*****************************************************
	}


	/**
	 * For the new order, an item will be added into the items list and decrease the quantity of item in stock by one.
	 * The item can be added to the list if the item is found in the stockData and it is available.
	 *
	 * But if this is the past order, an item will just be added into the list and no change on the quantity of item in stock.
	 * The item can be added to the list if the item exists in the stock
	 *
	 * @param itemName is the name of item
	 * @param pastOrder indicates whether this is the past order or not
	 * @return return true if the item can be added, otherwise return false.
	 */
	public boolean addItem(String itemName, boolean pastOrder){
		//******************* YOUR CODE HERE ******************
		for (Item eachItem : DataManagement.stockData.values()) {
			if (itemName.equals(eachItem.getName()) && eachItem.isAvailable()) {
				items.add(eachItem);
				if(!pastOrder) {
					eachItem.sold();
				}
				return true;
			}
		}
		return false;
		//*****************************************************
	}


	/**
	 * Make a payment to this order based on the given payment method.
	 * If the payment is valid and can be paid, the paymentStatus will be changed to Status.PAID and
	 * the paymentMethod will be set to the type of payment method (CASH, CARD, or EWALLET).
	 *
	 * Otherwise, all items in the items list will be re-stocked and the paymentStatus will be changed to Status.VOIDED
	 *
	 * @param payment which can be either cash, credit card, or e-wallet
	 * @return
	 */
	public Status makePayment(Payment payment){
		//******************* YOUR CODE HERE ******************
		if(payment.method.equals("CASH") || payment.method.equals("CARD") || payment.method.equals("EWALLET")){
			setPaymentMethod(payment.method);
			if(payment.paid()) {
				setPaymentStatus(Status.PAID.name());
			}else{
				Set<Item> itemSet = new HashSet<Item>(items);  // ArrayList "items": "Apple", "Papaya", "Banana", "Orange", "Apple"
				// 	   Set "itemSet: "Apple", "Papaya", "Orange", "Banana"
				for (Item eachItem: itemSet){
					eachItem.restock();
				}
				setPaymentStatus(Status.VOIDED.name());
			}
			return getPaymentStatus();
		}
		return null;
		//*****************************************************
	}

	/**
	 * Calculate the shipping for online customer by multiplying the distance with the SHIPPING_RATE
	 * @return the value of shippingFee
	 */
	public double calShippingFee(){
		//******************* YOUR CODE HERE ******************
		shippingFee = 0;
		if(customer instanceof CustomerOnline) {
			return shippingFee = ((CustomerOnline) customer).getDistance() * SHIPPING_RATE;
		}
		return 0;
		//*****************************************************
	}

	/**
	 * Calculate the sub total by combining the price of all items in the list
	 * @return the value of sub total in this order
	 */
	public double calSubTotal(){
		//******************* YOUR CODE HERE ******************
		subTotal = 0;
		for (Item eachItem: items) {
			subTotal += eachItem.getPrice();
		}
		return subTotal;
		//*****************************************************
	}

	/**
	 * Calculate the total tax amount from all taxable items. Tax amount of an item is the price * TAX_RATE
	 * @return the total tax amount
	 */
	public double calTax(){
		tax = 0;
		//******************* YOUR CODE HERE ******************
		for(Item eachItem : items){
			if(eachItem.getTaxable()){
				tax += TAX_RATE * eachItem.getPrice();
			}
		}
		return tax;
		//*****************************************************
	}

	/**
	 * Calculate the grand total of this order by combining sub total, tax amount, and shipping fee all together
	 * @return the value of grand total
	 */
	public double calGrandTotal(){
		calSubTotal();
		calTax();
		calShippingFee();
		grandTotal = subTotal + tax + shippingFee;
		return grandTotal;
	}

	public String log(){
		StringBuilder itemList = new StringBuilder();
		for(int i = 0 ; i< items.size() ;i++){
			itemList.append(items.get(i).getName()+"|");
		}
		return this.orderID+","+this.customer.getCustID()+","+String.valueOf(itemList.substring(0,itemList.length()-1))+","+paymentStatus+"::"+paymentMethod;
	}


	//**************************** DO NOT MODIFY **********************************//
	public int getOrderID() {
		return orderID;
	}

	public double getSubTotal() {
		return subTotal;
	}

	public double getTax() {
		return tax;
	}

	public double getShippingFee(){
		return shippingFee;
	}

	public double getGrandTotal(){
		return this.grandTotal;
	}

	public Customer getCustomer(){
		return customer;
	}

	public ArrayList<Item> getItems(){
		return items;
	}

	public Status getPaymentStatus() {
		return this.paymentStatus;
	}

	public String getPaymentMethod() {
		return this.paymentMethod;
	}

	public void prettyPrint() {
		String divider = "----------------------------";
		String border = "****************************";
		System.out.println(border);
		System.out.printf("%-15s %-4d \n", "Order ID:", orderID);
		System.out.printf("%-15s %-4s \n", "Customer:", customer.getCustID() + " " + customer.getName());
		System.out.printf("%-15s %-4s \n", "Payment Status:", paymentMethod + "|" + paymentStatus);
		System.out.println(divider);
		for(Item i: items){
			System.out.printf("%-15s [%10s] \n", i.getName(), i.getPrice());
		}
		System.out.println(divider);
		System.out.printf("%-15s [%10.2f] \n", "Sub Total:", subTotal);
		// to retain precision
		System.out.printf("%-15s [%10.2f] \n", "Tax:", tax);
		System.out.printf("%-15s [%10.2f] \n", "Shipping:", shippingFee);
		System.out.println(divider);
		System.out.printf("%-15s [%10.2f] \n", "Grand Total:", grandTotal);
		System.out.println(border);
	}

	public void setPaymentStatus(String status) {
		this.paymentStatus = Status.valueOf(status);
	}

	public void setPaymentMethod(String method) {
		this.paymentMethod = method;
	}
	//*****************************************************************************//

}
