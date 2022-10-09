

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Implements Loggable interface in Item, Customer, EWallet, and Order
 * Testing log method in those classes
 * The log method is used in the Data class to write content to the data log files.
 *
 */
class TestCaseTask5{

	double e = 1.0;	// epsilon
	static String SAMPLE_PATH = Paths.get("").toAbsolutePath().toString()
			+ File.separator + "testcase_log" +  File.separator;

	static String STOCK_FILE = "C:\\Users\\Chaiyakorn\\Desktop\\MUICT_OOP-Project02\\P02GroceryStore v1.1\\FileOutput\\StockLog.txt";
	static String CUSTOMER_FILE = "C:\\Users\\Chaiyakorn\\Desktop\\MUICT_OOP-Project02\\P02GroceryStore v1.1\\FileOutput\\CustomerLog.txt";
	static String WALLET_FILE = "C:\\Users\\Chaiyakorn\\Desktop\\MUICT_OOP-Project02\\P02GroceryStore v1.1\\FileOutput\\WalletsLog.txt";
	static String ORDER_FILE = "C:\\Users\\Chaiyakorn\\Desktop\\MUICT_OOP-Project02\\P02GroceryStore v1.1\\FileOutput\\OrderLog.txt";


	@BeforeAll
	public static void test0DeleteLogFiles() {
		DataManagement.removeLogFile(STOCK_FILE);
		DataManagement.removeLogFile(CUSTOMER_FILE);
		DataManagement.removeLogFile(WALLET_FILE);
		DataManagement.removeLogFile(ORDER_FILE);
	}

	@Test
	public void test1StoreItems() {

		List<Item> items = new ArrayList<Item>();
		items.add(new Item("cleaner", 30.0, false));
		items.add(new Item("butter", 460.00, false, 20));
		items.add(new Item("cocoa", 320.00, true));
		items.add(new Item("coffee", 190.00, false, 2));
		items.add(new Item("cereals", 440.00, true));

		assertEquals("cleaner,30.00,false,2147483647", items.get(0).log());
		assertEquals("butter,460.00,false,20", items.get(1).log());
		assertEquals("cocoa,320.00,true,2147483647", items.get(2).log());
		assertEquals("coffee,190.00,false,2", items.get(3).log());
		assertEquals("cereals,440.00,true,2147483647", items.get(4).log());

		DataManagement.storeItems(STOCK_FILE, false, items);
		// If you cannot implement task 2 and task 4,
		// you can also compare your output file (testcase_log/stocks.txt)
		// with the expected output file (testcase_log/stocks_expected.text)
	}

	@Test
	public void test2StoreCustomers() {
		Customer.runningID = 0;
		List<Customer> customers = new ArrayList<Customer>();
		customers.add(new Customer("Andrew"));
		customers.add(new CustomerOnline("Bob", 50.0));
		customers.add(new CustomerOnline("Charis", 200.0));
		customers.add(new CustomerOnline(5, "Ema", 100.0));

		assertEquals("1,Andrew", customers.get(0).log());
		assertEquals("2,Bob,50.00", customers.get(1).log());
		assertEquals("3,Charis,200.00", customers.get(2).log());
		assertEquals("5,Ema,100.00", customers.get(3).log());

		DataManagement.storeCustomers(CUSTOMER_FILE, false, customers);
		// If you cannot implement task 2 and task 4,
		// you can simply compare your output file (testcase_log/customers.txt)
		// with the expected output file (testcase_log/customers_expected.text)
	}

	@Test
	public void test3StoreEWallets() {
		Customer.runningID = 0;
		List<Customer> customers = new ArrayList<Customer>();
		customers.add(new Customer("Andrew"));
		customers.add(new CustomerOnline("Bob", 50.0));
		customers.add(new CustomerOnline("Charis", 200.0));
		customers.add(new CustomerOnline(5, "Ema", 100.0));
		for(Customer c: customers) {
			DataManagement.customerData.put(c.getCustID(), c);
		}

		List<EWallet> wallets = new ArrayList<EWallet>();
		wallets.add(new EWallet(2, "BBB", "B123", 2000.00));
		wallets.add(new EWallet(3, "CCC", "C123", 20000.00));
		wallets.add(new EWallet(5, "EEE", "E123", 50000.00));

		assertEquals("2,BBB,2014896,2000.00", wallets.get(0).log());
		assertEquals("3,CCC,2044687,20000.00", wallets.get(1).log());
		assertEquals("5,EEE,2104269,50000.00", wallets.get(2).log());

		DataManagement.storeEWallets(WALLET_FILE, false, wallets);
		// If you cannot implement task 2 and task 4,
		// you can also compare your output file (testcase_log/wallets.txt)
		// with the expected output file (testcase_log/wallets_expected.text)
	}

	@Test
	public void test4StoreOrders() {

		// [2021-04-10]
		// Removed init...() methods and changed to manual adding
		List<Item> items = new ArrayList<Item>();
		items.add(new Item("cleaner", 30.0, false));
		items.add(new Item("butter", 460.00, false, 20));
		items.add(new Item("cocoa", 320.00, true));
		items.add(new Item("coffee", 190.00, false, 2));
		items.add(new Item("cereals", 440.00, true));
		DataManagement.stockData = new HashMap<String, Item>();
		for(Item i: items) {
			DataManagement.stockData.put(i.getName(), i);
		}

		Customer.runningID = 0;
		List<Customer> customers = new ArrayList<Customer>();
		customers.add(new Customer("Andrew"));
		customers.add(new CustomerOnline("Bob", 50.0));
		customers.add(new CustomerOnline("Charis", 200.0));
		customers.add(new CustomerOnline(5, "Ema", 100.0));
		DataManagement.customerData = new HashMap<Integer, Customer>();
		for(Customer c: customers) {
			DataManagement.customerData.put(c.getCustID(), c);
		}

		List<EWallet> wallets = new ArrayList<EWallet>();
		wallets.add(new EWallet(2, "BBB", "B123", 2000.00));
		wallets.add(new EWallet(3, "CCC", "C123", 20000.00));
		wallets.add(new EWallet(5, "EEE", "E123", 50000.00));
		DataManagement.walletData = new HashMap<Integer, EWallet>();
		for(EWallet w: wallets) {
			DataManagement.walletData.put(w.getCustID(), w);
		}

		// End change

		Order a = new Order();
		assertTrue(a.setCustomerID(1));
		assertTrue(a.addItem("butter"));
		assertTrue(a.addItem("cocoa"));
		assertEquals(Order.Status.PAID, a.makePayment(new PaymentCash(a.calGrandTotal(), 1000)));
		assertEquals("1,1,butter|cocoa,PAID::CASH", a.log());

		Order b = new Order();
		assertTrue(b.setCustomerID(2));
		assertTrue(b.addItem("cereals"));
		assertTrue(b.addItem("cocoa"));
		assertEquals(Order.Status.PENDING, b.getPaymentStatus());
		assertEquals("2,2,cereals|cocoa,PENDING::UNKNOWN", b.log());

		Order c = new Order(5);
		assertTrue(c.setCustomerID(2));		// customer's id = 2
		assertTrue(c.addItem("cereals"));
		assertTrue(c.addItem("cocoa"));
		EWallet w = DataManagement.walletData.get(2);	// get wallet of customer's id 2
		assertEquals(Order.Status.PAID, c.makePayment(new PaymentEWallet(c.calGrandTotal(), w, "BBB", "B123")));
		assertEquals("5,2,cereals|cocoa,PAID::EWALLET", c.log());

		Order d = new Order();
		assertTrue(d.setCustomerID(2));		// customer's id = 2
		assertTrue(d.addItem("coffee"));
		assertTrue(d.addItem("cereals"));
		EWallet w2 = DataManagement.walletData.get(2);	// get wallet of customer's id 2
		// unauthorized E-Wallet
		assertEquals(Order.Status.VOIDED, d.makePayment(new PaymentEWallet(d.calGrandTotal(), w2, "BBB", "D123")));
		assertEquals("6,2,coffee|cereals,VOIDED::EWALLET", d.log());

		List<Order> orders = new ArrayList<Order>();
		orders.add(a);
		orders.add(b);
		orders.add(c);
		orders.add(d);

		DataManagement.storeOrders(ORDER_FILE, false, orders);
		// If you cannot implement task 2 and task 4,
		// you can also compare your output file (testcase_log/orders.txt)
		// with the expected output file (testcase_log/orders_expected.text)
	}
}