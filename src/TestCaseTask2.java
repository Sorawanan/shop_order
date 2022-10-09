import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class TestCaseTask2 {

	double e = 1.0;	// epsilon
	String SAMPLE_PATH = Paths.get("").toAbsolutePath().toString()
			+ File.separator + "testcase_init" +  File.separator;

	String STOCK_FILE = SAMPLE_PATH + "stocks.txt";
	String CUSTOMER_FILE = SAMPLE_PATH + "customers.txt";
	String WALLET_FILE = SAMPLE_PATH + "wallets.txt";

	Map<Integer, Customer> customerData;
	Map<Integer, EWallet> walletData;
	Map<String, Item> stockData;


	@BeforeEach
	public void befor() {
		customerData = new HashMap<Integer, Customer>();
		walletData = new HashMap<Integer, EWallet>();
		stockData = new HashMap<String, Item>();
	}


	@Test
	public void testInitCustomer() {
		customerData = DataManagement.initCustomer("C:\\Users\\User\\Code\\Project02\\testcase_init\\customers.txt");
		for(int key: customerData.keySet()) {
			System.out.println(key + "->" + customerData.get(key));
		}
		assertEquals(10, customerData.size());
		assertEquals(12, Customer.runningID);

		// Andrew is a general customer
		Customer andrew = customerData.get(1);
		System.out.println(andrew);
		assertEquals("Andrew", andrew.getName());
		assertFalse(andrew instanceof CustomerOnline);

		// Ema is an online customer
		Customer ema = customerData.get(5);
		assertEquals("Ema", ema.getName());
		assertTrue(ema instanceof CustomerOnline);
		assertEquals(100.00, ((CustomerOnline) ema).getDistance(), e);
	}

	@Test
	public void testInitStock() {
		stockData = DataManagement.initStock("C:\\Users\\User\\Code\\Project02\\testcase_init\\stocks.txt");
		for(String key: stockData.keySet()) {
			System.out.println(key + "->" + stockData.get(key));
		}
		assertTrue(stockData.size() == 13);

		//name::yogurt,price::270.00,taxable::false,qty::2147483647
		Item yogurt = stockData.get("yogurt");
		assertEquals(270, yogurt.getPrice(), e);
		assertFalse(yogurt.getTaxable());
		assertEquals(Integer.MAX_VALUE, yogurt.getQuantity());

		//name::milk,price::210.00,taxable::true,qty::1000
		Item milk = stockData.get("milk");
		assertEquals(210.0, milk.getPrice(), e);
		assertTrue(milk.getTaxable());
		assertEquals(1000, milk.getQuantity(), e);
	}

	@Test
	public void testInitWallet() {
		walletData = DataManagement.initWallet("C:\\Users\\User\\Code\\Project02\\testcase_init\\wallets.txt");
		for(int key: walletData.keySet()) {
			System.out.println(key + "->" + walletData.get(key));
		}
		assertEquals(4, walletData.size());

		// CID::2,username::BBB,encodedpassword::2014896,balance::2000.00
		EWallet b = walletData.get(2);
		assertEquals("BBB", b.getUserName());
		assertEquals(2014896, b.getEncodedPassword());
		assertEquals(2000.00, b.getBalance(), e);

		// CID::5,username::EEE,encodedpassword::2104269,balance::500.00
		EWallet c = walletData.get(5);
		assertEquals("EEE", c.getUserName());
		assertEquals(2104269, c.getEncodedPassword());
		assertEquals(500.00, c.getBalance(), e);

	}

}