//GROUP 12
//Student ID: 6388012, 6388019, 6388022
//Section 1

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DataManagement {

	public static final String SAMPLE_PATH = Paths.get("").toAbsolutePath().toString()
			+ File.separator + "sample" + File.separator;

	public static final String STOCK_FILE = SAMPLE_PATH + "stocks.txt";
	public static final String CUSTOMER_FILE = SAMPLE_PATH + "customers.txt";
	public static final String WALLET_FILE = SAMPLE_PATH + "wallets.txt";
	public static final String ORDER_FILE = SAMPLE_PATH + "orders.txt";

	//**************************** DO NOT MODIFY **********************************//

	public static Map<Integer, Customer> customerData = new HashMap<Integer, Customer>();
	//The key of customerData is the customer's ID

	public static Map<Integer, EWallet> walletData = new HashMap<Integer, EWallet>();
	//The key of walletData is the customer's ID associated with that EWallet

	public static Map<String, Item> stockData = new HashMap<String, Item>();
	//The key of stockData is the item's name

	public static Map<Integer, Order> orderData = new HashMap<Integer, Order>();
	//The key of orderData is the order's ID

	//*****************************************************************************//

	/**
	 * Reads data line by line from the text file.
	 * For any valid line with correct customer's format, recreates a Customer object, and put in the customerData map.
	 * The invalid line will be skipped.
	 * <p>
	 * Note that customer can be either general customer or an online customer.
	 *
	 * @param filename that keeps customers data
	 * @return Map collection of customers read from the text file
	 */
	public static Map<Integer, Customer> initCustomer(String filename) {
		//******************* YOUR CODE HERE ******************
		try {
			FileReader fileReader = new FileReader(new File(filename));
			BufferedReader reader = new BufferedReader(fileReader);
			String eachCustomer = "";
			int CustID = 0;
			String name = null;
			double distance = 0;
			while ((eachCustomer = reader.readLine()) != null) {
				Pattern validCustomer = Pattern.compile(("([\\d]+)[,]+([A-Z]+[a-zA-Z]+)"));
				Pattern validOnlineCustomer = Pattern.compile(("([\\d]+)[,]+([A-Z]+[a-zA-Z]+)+[,]+([\\d]+[\\d\\.]+)"));
				Matcher isValidCustomer = validCustomer.matcher(eachCustomer);
				Matcher isValidOnlineCustomer = validOnlineCustomer.matcher(eachCustomer);
				if (isValidCustomer.matches()) {
					CustID = Integer.parseInt(isValidCustomer.group(1));
					name = isValidCustomer.group(2);
					Customer customer = new Customer(CustID, name);
					customerData.put(CustID, customer);
				} else if (isValidOnlineCustomer.matches()) {
					CustID = Integer.parseInt(isValidOnlineCustomer.group(1));
					name = isValidOnlineCustomer.group(2);
					distance = Double.parseDouble(isValidOnlineCustomer.group(3));
					CustomerOnline customerOnline = new CustomerOnline(CustID, name, distance);
					customerData.put(CustID, customerOnline);
				}

			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return customerData;
		//*****************************************************
	}

	/**
	 * Reads data line by line from the text file.
	 * For any valid line with correct wallet's format, recreates a EWallet object, and put in the walletData map.
	 * The invalid line will be skipped.
	 * <p>
	 * Note that the password in the log file must be encoded password (positive or negative number only)
	 *
	 * @param filename that keeps e-wallet data
	 * @return Map collection of wallets read from the text file
	 */
	public static Map<Integer, EWallet> initWallet(String filename) {
		//******************* YOUR CODE HERE ******************
		try {
			FileReader fileReader = new FileReader(new File(filename));
			BufferedReader reader = new BufferedReader(fileReader);
			String eachWallet = "";
			int CustID = 0;
			String user = null;
			int pwd = 0;
			double balance = 0;
			while ((eachWallet = reader.readLine()) != null) {
				Pattern validWallet = Pattern.compile(("([\\d]+)[,]+([a-zA-Z]+)+[,]+([\\d|\\-]+[\\d]+)+[,]+([\\d]+[\\d\\.]+)"));
				Matcher isValidWallet = validWallet.matcher(eachWallet);
				if (isValidWallet.matches()) {
					CustID = Integer.parseInt(isValidWallet.group(1));
					user = isValidWallet.group(2);
					pwd = Integer.parseInt(isValidWallet.group(3));
					balance = Double.parseDouble(isValidWallet.group(4));
					EWallet eWallet = new EWallet(CustID, user, pwd, balance);
					walletData.put(CustID, eWallet);
				}

			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return walletData;
		//*****************************************************

	}


	/**
	 * Reads data line by line from the text file.
	 * For any valid line with correct item's format, recreates an Item object, and put in the stockData map.
	 * The invalid line will be skipped.
	 *
	 * @param filename that keeps stocks data
	 * @return Map collection of items read from the text file
	 */
	public static Map<String, Item> initStock(String filename) {

		//******************* YOUR CODE HERE ******************

		try {
			FileReader fileReader = new FileReader(new File(filename));
			BufferedReader reader = new BufferedReader(fileReader);
			String eachItem = "";
			String name = null;
			double price = 0;
			boolean taxable;
			int qty = 0;
			while ((eachItem = reader.readLine()) != null) {
				Pattern validItem = Pattern.compile(("([\\w\\W]+)[,]+([\\d]+[\\d\\.]+)[,]+([true|false]+)[,]+([\\d]+)"));
				Matcher isValidItem = validItem.matcher(eachItem);
				if (isValidItem.matches()) {
					name = isValidItem.group(1);
					price = Double.parseDouble(isValidItem.group(2));
					taxable = Boolean.parseBoolean(isValidItem.group(3));
					qty = Integer.parseInt(isValidItem.group(4));
					Item item = new Item(name, price, taxable, qty);
					stockData.put(name, item);
				}

			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return stockData;

		//*****************************************************

	}

	/**
	 * Reads data line by line from the text file.
	 * For any valid line with correct order's format, recreates an Order object, and put in the orderData map.
	 * The invalid line will be skipped.
	 * <p>
	 * Note that if the qty is equal to 2147483647, this mean the item has unlimited supply
	 *
	 * @param filename that keeps orders data
	 * @return Map collection of orders read from the text file
	 */

	public static Map<Integer, Order> initOrder(String filename) {
		//******************* YOUR CODE HERE ******************
		try {
			FileReader fileReader = new FileReader(new File(filename));
			BufferedReader reader = new BufferedReader(fileReader);
			String eachOrder = "";
			int orderID;
			int custID;
			String[] items;
			String paymentStatus;
			String paymentMethod;
			while ((eachOrder = reader.readLine()) != null) {
				Pattern validOrder = Pattern.compile(("([\\d]+)[,]+([\\d]+)[,]+([\\w]+[\\w\\W]+)[,]+([PAID|PENDING|VOIDED]+)[::]+([UNKNOWN|CASH|CARD|EWALLET]+)"));
				Matcher isValidOrder = validOrder.matcher(eachOrder);
				if (isValidOrder.matches()) {
					orderID = Integer.parseInt(isValidOrder.group(1));
					custID = Integer.parseInt(isValidOrder.group(2));
					items = isValidOrder.group(3).split("[\\|]");
					paymentStatus = isValidOrder.group(4);
					paymentMethod = isValidOrder.group(5);
					Order order = new Order(orderID);
					order.setCustomerID(custID);
					order.setPaymentStatus(paymentStatus);
					order.setPaymentMethod(paymentMethod);
					for (String eachItem : items) {
						order.addItem(eachItem);
					}
					orderData.put(orderID, order);
				}
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return orderData;
		//*****************************************************

	}

	//**************************** DO NOT MODIFY **********************************//

	/**
	 * Initialize customerData, stockData, walletData, and orderData by calling initCustomer, initStock, initWallet, and initOrder respectively
	 */
	public static void initData() {
		initStock(STOCK_FILE);
		initCustomer(CUSTOMER_FILE);
		initWallet(WALLET_FILE);
		initOrder(ORDER_FILE);
	}


	/**
	 * Delete existing log files if any
	 */
	public static void removeLogFile(String filename) {

		try {
			File log = new File(filename);
			Files.deleteIfExists(log.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * A convenient method to write str to the File identified by filename.
	 * Only works with newer version of Java.
	 *
	 * @param filename
	 * @param append
	 * @param str
	 */
	public static void writeDataLog(String filename, Boolean append, String str) {
		try {
			FileWriter fileWriter = new FileWriter(filename, append);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			printWriter.println(str);
			printWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	//*****************************************************************************//

	/**
	 * Write the list of items into the given text file
	 * If the append is true, the list of items will be appended into the existing log file
	 * Otherwise, the content in the existing log file will be replaced with this new list of items.
	 *
	 * @param filename
	 * @param append:  whether append the text file or not
	 * @param items:   list of items
	 */
	public static void storeItems(String filename, Boolean append, List<Item> items) {
		//******************* YOUR CODE HERE ******************
		try {
			FileWriter writer = new FileWriter(filename, append);
			for (int i = 0; i < items.size(); i++) {
				writer.write(items.get(i).log() + "\n");
			}
			writer.close();
		} catch (FileNotFoundException e) {
			System.out.println("This file is invalid");
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
		//*****************************************************
	}

	/**
	 * Write the list of customers into the given text file
	 * If the append is true, the list of customers will be appended into the existing log file
	 * Otherwise, the content in the existing log file will be replaced with this new list of customers.
	 *
	 * @param filename
	 * @param append
	 * @param customers
	 */
	public static void storeCustomers(
			String filename, Boolean append, List<Customer> customers) {

		//******************* YOUR CODE HERE ******************
		try {
			FileWriter writer = new FileWriter(filename, append);
			for (int i = 0; i < customers.size(); i++) {
				writer.write(customers.get(i).log() + "\n");
			}
			writer.close();
		} catch (FileNotFoundException e) {
			System.out.println("This file is invalid");
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
		//*****************************************************
	}

	/**
	 * Write the list of wallets into the given text file
	 * If the append is true, the list of wallets will be appended into the existing log file
	 * Otherwise, the content in the existing log file will be replaced with this new list of wallets.
	 *
	 * @param filename
	 * @param append
	 * @param wallets
	 */
	public static void storeEWallets(
			String filename, Boolean append, List<EWallet> wallets) {

		//******************* YOUR CODE HERE ******************
		try {
			FileWriter writer = new FileWriter(filename, append);
			for (int i = 0; i < wallets.size(); i++) {
				writer.write(wallets.get(i).log() + "\n");
			}
			writer.close();
		} catch (FileNotFoundException e) {
			System.out.println("This file is invalid");
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
		//*****************************************************
	}

	/**
	 * Write the list of orders into the given text file
	 * If the append is true, the list of orders will be appended into the existing log file
	 * Otherwise, the content in the existing log file will be replaced with this new list of orders.
	 *
	 * @param append
	 * @param orders
	 */

	public static void storeOrders(
			String filename, Boolean append, List<Order> orders) {

		//******************* YOUR CODE HERE ******************
		try {
			FileWriter writer = new FileWriter(filename, append);
			for (int i = 0; i < orders.size(); i++) {
				writer.write(orders.get(i).log() + "\n");
			}
			writer.close();
		} catch (FileNotFoundException e) {
			System.out.println("This file is invalid");
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
		//*****************************************************
	}


	/**
	 * Filter the orders data (in the Map Collection) by the order's status and/or payment method
	 * and calculate the summation of the sub-total amount of those filtered ordered.
	 *
	 * @param status
	 * @param method
	 * @return total summation of the sub-total of the orders matched with the given condition
	 */
	public static double filterSubTotal(Order.Status status, String method) {
		//******************* YOUR CODE HERE ******************
		double subtotal = 0;
		for (Order eachOrder : orderData.values()) {
			if (eachOrder.getPaymentStatus() == status && eachOrder.getPaymentMethod().equals(method)) {
				subtotal += eachOrder.getSubTotal();
			}
		}
		return subtotal;
		//*****************************************************
	}

	/**
	 * Filter the orders data (in the Map Collection) by the order's status and/or payment method
	 * and calculate the summation of the grand total amount of those filtered ordered.
	 *
	 * @param status
	 * @param method
	 * @return total summation of the grand total of the orders matched with the given condition
	 */

	public static double filterGrandTotal(Order.Status status, String method) {
		//******************* YOUR CODE HERE ******************
		double grandtotal = 0;
		for (Order eachOrder : orderData.values()) {
			if (eachOrder.getPaymentStatus() == status && eachOrder.getPaymentMethod().equals(method)) {
				grandtotal += eachOrder.getGrandTotal();
			}
		}
		return grandtotal;

		//*****************************************************

	}

	/**
	 * Calculate the grand total payment group by each payment method for all paid ordered
	 * The voided or pending orders are ignored
	 *
	 * @return Map<String, Double> where key is the method name, and value is the total of grand total payment
	 */
	public static Map<String, Double> groupGrandTotalByPaymentMethod() {

		//******************* YOUR CODE HERE ******************
		Map<String, Double> groupingGrandtotal = new HashMap<String, Double>();
		double grandTotalCASH = 0;
		double grandTotalCARD = 0;
		double grandTotalEWALLET = 0;
		for (Order eachOrder : orderData.values()) {
			//CASH , CARD , EWALLET --> Double grandtotal-> each grandtotal of each paymentmethod
			if (eachOrder.getPaymentStatus() == Order.Status.PAID) {
				switch (eachOrder.getPaymentMethod()) {
					case "CASH":
						grandTotalCASH += eachOrder.getGrandTotal();
						break;
					case "CARD":
						grandTotalCARD += eachOrder.getGrandTotal();
						break;
					case "EWALLET":
						grandTotalEWALLET += eachOrder.getGrandTotal();
						break;
				}
			}
		}
		groupingGrandtotal.put("CASH", grandTotalCASH);
		groupingGrandtotal.put("CARD", grandTotalCARD);
		groupingGrandtotal.put("EWALLET", grandTotalEWALLET);
		return groupingGrandtotal;
		//*****************************************************

	}

	/**
	 * Sort the orders data (in Map Collection) by their grandTotal
	 * If asc is true, the grandTotal is sorted in ascending order
	 * Otherwise, the grandTotal is sorted in descending order
	 * Only "paid" orders are included in the output
	 * If the amounts are equal, the order ID will be used later.
	 *
	 * @param asc indicate ascending or descending order
	 * @return the sorted list of orders
	 */
	public static ArrayList<Order> sortPaidGrandTotal(boolean asc) {

		//******************* YOUR CODE HERE ******************
		ArrayList<Order> GrandTotalList = new ArrayList<Order>();
		for (Order eachOrder : orderData.values()) {
			if (eachOrder.getPaymentStatus() == Order.Status.PAID) {
				GrandTotalList.add(eachOrder);
			}
		}
		if (asc) {
			for (int i = 0; i < GrandTotalList.size() - 1; i++) {
				for (int j = 0; j < GrandTotalList.size() - i - 1; j++) {
					if (GrandTotalList.get(j).getGrandTotal() > GrandTotalList.get(j + 1).getGrandTotal()) {
						Order tempOrder = GrandTotalList.get(j);
						GrandTotalList.set(j,GrandTotalList.get(j + 1));
						GrandTotalList.set(j+1,tempOrder);
					}
				}
			}
		} else {
			for (int i = 0; i < GrandTotalList.size()-1; i++) {
				for (int j = 0; j < GrandTotalList.size() - i - 1; j++) {
					if (GrandTotalList.get(j).getGrandTotal() < GrandTotalList.get(j + 1).getGrandTotal()) {
						Order tempOrder = GrandTotalList.get(j);
						GrandTotalList.set(j,GrandTotalList.get(j + 1));
						GrandTotalList.set(j+1,tempOrder);
					}
				}
				//*****************************************************
			}
		}
		return GrandTotalList;
	}

}