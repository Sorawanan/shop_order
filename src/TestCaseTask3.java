import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

class TestCaseTask3 {

	double e = 1.0;	// epsilon
	int distance = 100;

	@BeforeEach
	public void befor() {
		Customer.runningID = 0;
		Order.runningID = 0;

		Customer cus = new Customer("Alex");
		DataManagement.customerData.put(cus.getCustID(), cus);

		CustomerOnline cusOnline = new CustomerOnline("Bob", distance);
		DataManagement.customerData.put(cusOnline.getCustID(), cusOnline);

		ArrayList<Item> items = new ArrayList<Item>();
		items.add(new Item("apple", 100, false));
		items.add(new Item("bread", 150, true));
		items.add(new Item("coffee", 200, true, 1));
		for(Item i: items) {
			DataManagement.stockData.put(i.getName(), i);
		}
	}

	@Test
	public void testValidOrder() {
		Order a = new Order();
		assertFalse(a.setCustomerID(0));	// invalid customer's ID

		assertTrue(a.setCustomerID(1));
		assertTrue(a.addItem("apple"));
		assertTrue(a.addItem("bread"));
		assertTrue(a.addItem("coffee"));
		assertEquals(1, a.getOrderID());

		// no payment yet
		assertEquals(Order.Status.PENDING, a.getPaymentStatus());
		assertEquals(450.00, a.calSubTotal(), e);
		double tax = (150 * Order.TAX_RATE) + (200 * Order.TAX_RATE);
		assertEquals(tax, a.calTax(), e);
		assertEquals(0.00, a.calShippingFee(), e);
		double grand = 450.00 + tax;
		assertEquals(grand, a.calGrandTotal(), e);

		// add payment method
		PaymentCash cash = new PaymentCash(a.getGrandTotal(), 500);
		assertEquals(Order.Status.PAID, a.makePayment(cash));
		assertEquals("CASH", a.getPaymentMethod());
		assertEquals(500 - grand, cash.getChange(), e);
	}

	@Test
	public void testUnavailableItem() {
		Order a = new Order();
		assertEquals(1, a.getOrderID());

		assertTrue(a.setCustomerID(1));
		assertTrue(a.addItem("coffee"));	// there is only one coffee left in the stock
		assertFalse(a.addItem("coffee"));	// coffee is no longer available
		assertEquals(200.00, a.calSubTotal(), e);
	}

	@Test
	public void testInvalidPayment() {

		Order a = new Order();
		assertTrue(a.setCustomerID(2));					// online customer's ID
		assertEquals(1, a.getOrderID());

		// add items
		assertTrue(a.addItem("apple", false));
		assertTrue(a.addItem("bread", false));
		assertTrue(a.addItem("coffee", false));

		// no payment yet
		assertEquals(Order.Status.PENDING, a.getPaymentStatus());
		assertEquals(450.00, a.calSubTotal(), e);
		double tax = (150 * Order.TAX_RATE) + (200 * Order.TAX_RATE);
		assertEquals(tax, a.calTax(), e);
		double ship = distance * Order.SHIPPING_RATE;
		assertEquals(ship, a.calShippingFee(), e);
		double grand = 450.00 + ship + tax;
		assertEquals(grand, a.calGrandTotal(), e);

		// add payment method, but it was invalid
		PaymentCash cash = new PaymentCash(a.getGrandTotal(), 500);
		assertEquals(Order.Status.VOIDED, a.makePayment(cash));
		assertEquals("CASH", a.getPaymentMethod());
		assertEquals(0, cash.getChange(), e);
	}

}