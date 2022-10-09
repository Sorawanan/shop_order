import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestCaseTask4 {

	double e = 1.0;	// epsilon
	String SAMPLE_PATH = Paths.get("").toAbsolutePath().toString()
			+ File.separator + "testcase_init" +  File.separator;

	String STOCK_FILE = "stocks.txt";//add location of file
	String CUSTOMER_FILE = "customers.txt";//add location of file
	String WALLET_FILE = "wallets.txt";//add location of file
	String ORDER_FILE = "orders.txt";//add location of file

	Map<String, Item> stockData;
	Map<Integer, Customer> customerData;
	Map<Integer, EWallet> walletData;
	Map<Integer, Order> orderData;


	@BeforeEach
	public void befor() {
		Customer.runningID = 0;
		Order.runningID = 0;

		stockData = new HashMap<String, Item>();
		customerData = new HashMap<Integer, Customer>();
		walletData = new HashMap<Integer, EWallet>();
		orderData = new HashMap<Integer, Order>();
	}

	@Test
	public void testInitData() {

		stockData = DataManagement.initStock(STOCK_FILE);
		customerData = DataManagement.initCustomer(CUSTOMER_FILE);
		walletData = DataManagement.initWallet(WALLET_FILE);
		orderData = DataManagement.initOrder(ORDER_FILE);

		// Test Customer Data
		System.out.println("---- CUSTOMER ----");
		for(int cid: customerData.keySet()) {
			System.out.println(customerData.get(cid));
		}
		assertEquals(10, customerData.size());

		// Test Stock Data
		System.out.println("---- STOCK ----");
		for(String name: stockData.keySet()) {
			System.out.println(stockData.get(name));
		}
		assertTrue(stockData.size() == 13);

		// Test Wallet Data
		System.out.println("---- EWALLET ----");
		for(int cid: walletData.keySet()) {
			System.out.println(walletData.get(cid));
		}
		assertEquals(4, walletData.size());

		// Test Order Data
		System.out.println("---- ORER ----");
		for(int oid: orderData.keySet()) {
			orderData.get(oid).calGrandTotal();
			System.out.println(orderData.get(oid));
		}
		assertEquals(8, orderData.size());
	}
}
