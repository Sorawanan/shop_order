import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Testing methods for analyzing data
 */
class TestCaseTask6 {

	static double ep = 1.0;	// epsilon

	@BeforeAll
	public static void test0initData() {

		Customer.runningID = 0;
		DataManagement.customerData.clear();
		DataManagement.customerData.put(1, new Customer("Andrew"));
		DataManagement.customerData.put(2, new CustomerOnline("Elle", 50));
		DataManagement.customerData.put(3, new Customer("Catty"));

		DataManagement.stockData.clear();
		DataManagement.stockData.put("apple", new Item("apple", 10, true, 100));
		DataManagement.stockData.put("banana", new Item("banana", 20, false, 200));
		DataManagement.stockData.put("popcorn", new Item("popcorn", 30, false, 200));
		DataManagement.stockData.put("honey", new Item("honey", 40, false, 200));
		DataManagement.stockData.put("ice cream", new Item("ice cream", 50, false, 200));
		DataManagement.stockData.put("bread", new Item("bread", 100, true, 200));

		Order.runningID = 0;
		Order a = new Order();
		a.setCustomerID(1);
		a.addItem("apple", true);
		a.addItem("banana", true);
		a.calGrandTotal();
		a.setPaymentMethod("CARD");
		a.setPaymentStatus("PAID");

		Order b = new Order();
		b.setCustomerID(1);
		b.addItem("apple", true);
		b.addItem("banana", true);
		b.calGrandTotal();
		b.setPaymentStatus("PAID");
		b.setPaymentMethod("CASH");

		Order c = new Order();
		c.setCustomerID(1);
		c.addItem("apple", true);
		c.addItem("bread", true);
		c.addItem("apple", true);
		c.calGrandTotal();
		c.setPaymentStatus("VOIDED");
		c.setPaymentMethod("CASH");

		Order d = new Order();
		d.setCustomerID(2);
		d.addItem("ice cream", true);
		d.addItem("bread", true);
		d.calGrandTotal();
		d.setPaymentStatus("PAID");
		d.setPaymentMethod("CASH");

		Order e = new Order();
		e.setCustomerID(2);
		e.addItem("honey", true);
		e.addItem("popcorn", true);
		e.calGrandTotal();
		e.setPaymentStatus("PAID");
		e.setPaymentMethod("EWALLET");

		DataManagement.orderData.clear();
		DataManagement.orderData.put(a.getOrderID(), a);
		DataManagement.orderData.put(b.getOrderID(), b);
		DataManagement.orderData.put(c.getOrderID(), c);
		DataManagement.orderData.put(d.getOrderID(), d);
		DataManagement.orderData.put(e.getOrderID(), e);

		assertEquals(30.07, a.getGrandTotal(), ep);
		assertEquals(30.07, b.getGrandTotal(), ep);
		assertEquals(128.40, c.getGrandTotal(), ep);
		assertTrue(d.getCustomer() instanceof CustomerOnline);
		assertEquals(182.00, d.getGrandTotal(), ep);
		assertEquals(95.00, e.getGrandTotal(), ep);

	}

	@Test
	public void test1FiterSubTotal() {
		assertEquals(5, DataManagement.orderData.size(), 0.1);

		double total = DataManagement.filterSubTotal(Order.Status.PAID, "CASH");
		assertEquals(180.00, total, ep);
	}

	@Test
	public void test2FilterGrandTotal() {
		assertEquals(5, DataManagement.orderData.size(), 0.1);

		double total = DataManagement.filterGrandTotal(Order.Status.VOIDED, "CASH");
		assertEquals(128.40, total, ep);
	}

	@Test
	public void test3GroupGrandTotal() {
		assertEquals(5, DataManagement.orderData.size(), 0.1);

		Map<String, Double> group = DataManagement.groupGrandTotalByPaymentMethod();
		assertEquals(212.70, group.get("CASH"), ep);
		assertEquals(30.70, group.get("CARD"), ep);
		assertEquals(95.00, group.get("EWALLET"), ep);
	}

	@Test
	public void test4SortItems() {
		System.out.println("Unordered list");
		for(Integer oid: DataManagement.orderData.keySet()) {
			System.out.println(DataManagement.orderData.get(oid).log()
					+ ", grand total = " + DataManagement.orderData.get(oid).getGrandTotal());
		}
		System.out.println("------------------------------------");

		// sorted the order in ascending order from small to large grand total amount
		List<Order> sorted = DataManagement.sortPaidGrandTotal(true);
		System.out.println("Ordered list");
		System.out.println("------------------------------------");
		for(Order o: sorted) {
			System.out.println(o.log() + ", grand total = " + o.getGrandTotal());
		}

		assertEquals(30.70, sorted.get(0).getGrandTotal(), ep);
		assertEquals(30.70, sorted.get(1).getGrandTotal(), ep);
		assertEquals(95.00, sorted.get(2).getGrandTotal(), ep);
		assertEquals(182.00, sorted.get(3).getGrandTotal(), ep);

		assertEquals(1, sorted.get(0).getOrderID(), 0);
		assertEquals(2, sorted.get(1).getOrderID(), 0);
		assertEquals(5, sorted.get(2).getOrderID(), 0);
		assertEquals(4, sorted.get(3).getOrderID(), 0);

	}


}