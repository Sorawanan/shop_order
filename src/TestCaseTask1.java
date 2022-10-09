import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class TestCaseTask1 {
	double e = 1.0;	// epsilon

	@BeforeEach
	public void befor() {
		Customer.runningID = 0;
	}


	@Test
	public void testCustomer() {
		Customer a = new Customer("Alex");
		assertEquals(1, a.getCustID());

		Customer b = new Customer(3, "Bob");
		assertEquals(3, b.getCustID());

		Customer c = new Customer("Catty");
		assertEquals(4, c.getCustID());

	}

	@Test
	public void testOnlineCutomer() {
		CustomerOnline a = new CustomerOnline("Alex", 10);
		assertEquals(1, a.getCustID());
		assertEquals(10, a.getDistance(), e);

		Customer b = new CustomerOnline(3, "Bob", 15);
		assertEquals(3, b.getCustID());

		Customer c = new CustomerOnline("Catty", 20);
		assertEquals(4, c.getCustID());

	}

	@Test
	public void testItem() {
		Item a = new Item("apple", 100);
		assertEquals("apple", a.getName());
		assertEquals(100, a.getPrice(), 0);
		assertFalse(a.getTaxable());
		assertEquals(Integer.MAX_VALUE, a.getQuantity(), 0);

		Item b = new Item("bread", 150, true);
		assertEquals("bread", b.getName());
		assertEquals(150, b.getPrice(), 0);
		assertTrue(b.getTaxable());
		assertEquals(Integer.MAX_VALUE, b.getQuantity(), 0);

		Item c = new Item("coffee", 200, true, 2);
		assertEquals("coffee", c.getName());
		assertEquals(200, c.getPrice(), 0);
		assertTrue(c.getTaxable());
		assertEquals(2, c.getQuantity(), 0);
	}

	@Test
	public void testCreditCard() {
		// invalid card
		CreditCard a = new CreditCard("123", CreditCard.CardType.VISA);
		assertFalse(a.isValid());

		// the card's number does not match with card's type
		CreditCard b = new CreditCard("1234567890123456", CreditCard.CardType.VISA);
		assertFalse(b.isValid());

		// valid VISA
		CreditCard c = new CreditCard("4234567890123456", CreditCard.CardType.VISA);
		assertTrue(c.isValid());
		assertEquals("4234 5678 9012 3456", c.getFormattedCardNumber());

		// valid AMERICANEXPRESS
		CreditCard d = new CreditCard("373456789012345", CreditCard.CardType.AMERICANEXPRESS);
		assertTrue(d.isValid());
		assertEquals("3734 567890 12345", d.getFormattedCardNumber());

		// valid JCB
		CreditCard e = new CreditCard("3550567890123456", CreditCard.CardType.JCB);
		assertTrue(e.isValid());
		assertEquals("3550 5678 9012 3456", e.getFormattedCardNumber());

		// valid MASTERCARD
		CreditCard f = new CreditCard("5234567890123456", CreditCard.CardType.MASTERCARD);
		assertTrue(f.isValid());
		assertEquals("5234 5678 9012 3456", f.getFormattedCardNumber());
	}

	@Test
	public void testDigitalWallet() {
		String pwd = "Ik@nDo1T";
		// create with plain password
		EWallet a = new EWallet(1, "aaa", pwd, 1000);
		assertEquals(1000, a.getBalance(), e);

		// create with encoded password
		EWallet b = new EWallet(1, "bbb", pwd.hashCode(), 2000);
		assertEquals(2000, b.getBalance(), e);

		// test deductBalance/ addBalance
		assertTrue(a.deductBalance(50));
		assertEquals(1000 - 50, a.getBalance(), e);
		assertTrue(a.addBalance(200));
		double current = 1000 - 50 + 200;
		assertEquals(current, a.getBalance(), e);

		// test invalid input parameter
		assertFalse(a.deductBalance(0));
		assertEquals(current, a.getBalance(), e);
		assertFalse(a.addBalance(-200));
		assertEquals(current, a.getBalance(), e);
	}

	@Test
	public void testCashPayment() {
		PaymentCash p = new PaymentCash(100, 300);
		assertEquals(0, p.getChange(), e);
		assertTrue(p.paid());
		assertEquals(200, p.getChange(), e);

		PaymentCash p2 = new PaymentCash(300, 100);
		assertEquals(0, p2.getChange(), e);
		assertFalse(p2.paid());
		assertEquals(0, p2.getChange(), e);
	}

	@Test
	public void testCreditCardPayment() {
		// valid payment
		PaymentCreditCard a = new PaymentCreditCard(100, "4234567890123456", CreditCard.CardType.VISA);
		assertTrue(a.authorize());
		assertTrue(a.paid());

		// unauthorized card
		PaymentCreditCard b = new PaymentCreditCard(10000, "4234567890123456", CreditCard.CardType.JCB);
		assertFalse(b.authorize());
		assertFalse(b.paid());

		// exceed limit, invalid payment
		CreditCard c = new CreditCard("4234567890123456", CreditCard.CardType.VISA);
		PaymentCreditCard d = new PaymentCreditCard(10000, c);
		assertTrue(d.authorize());
		assertFalse(d.paid());
	}

	@Test
	public void testEWalletPayment() {
		EWallet w1 = new EWallet(1, "aaa", "1@3@9", 1000);
		EWallet w2 = new EWallet(2, "bbb", "1@3@0", 1500);

		// valid payment
		PaymentEWallet a = new PaymentEWallet(100, w1, "aaa", "1@3@9");
		assertTrue(a.authorize());
		assertTrue(a.paid());
		assertEquals(900, w1.getBalance(), e);

		// unauthorized wallet, invalid payment
		PaymentEWallet b = new PaymentEWallet(100, w1, "aaa", "1@3@0");
		assertFalse(b.authorize());
		assertFalse(b.paid());
		assertEquals(900, w1.getBalance(), e);


		// exceed balance, invalid payment
		PaymentEWallet c = new PaymentEWallet(2000, w2, "bbb", "1@3@0");
		assertTrue(c.authorize());
		assertFalse(c.paid());
		assertEquals(1500, w2.getBalance(), e);

	}
}
