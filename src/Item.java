public class Item implements Loggable {

	//**************************** DO NOT MODIFY **********************************//
	private String name;				// item's name
	private double price;				// item's price
	private boolean taxable = false;		// default value is false (non-taxable)
	private int qty = Integer.MAX_VALUE;	// default value is unlimited supply (max_value)
	//*****************************************************************************//


	/**
	 * Constructor to initialize name, price
	 * @param name
	 * @param price
	 */
	public Item(String name, double price){
		//******************* YOUR CODE HERE *****************
		this.name=name;
		this.price=price;

		//*****************************************************
	}

	/**
	 * Constructor to initialize name, price, and taxable value
	 * @param name
	 * @param price
	 * @param taxable
	 */

	public Item(String name, double price, boolean taxable){
		//******************* YOUR CODE HERE *****************
		this.name=name;
		this.price=price;
		this.taxable=taxable;

		//*****************************************************
	}

	/**
	 * Constructor to initialize, price, taxable, and remaining quantity in the stock
	 * @param name
	 * @param price
	 * @param taxable
	 * @param qty
	 */
	public Item(String name, double price, boolean taxable, int qty){
		//******************* YOUR CODE HERE ******************
		this.name=name;
		this.price=price;
		this.taxable=taxable;
		this.qty=qty;

		//*****************************************************
	}

	/**
	 * Return true if the quantity of the item is larger than 0
	 * @return
	 */
	public boolean isAvailable(){

		//******************* YOUR CODE HERE ******************
		if(this.qty>0) {
			return true;
		}
		return false;
		//*****************************************************
	}

	/**
	 * Decrease the quantity by one
	 * @return remaining quantity in the stock
	 */
	public int sold(){

		//******************* YOUR CODE HERE ******************
		int Decrease_the_quantity;
		Decrease_the_quantity=--qty;
		return Decrease_the_quantity;
		//*****************************************************
	}

	/**
	 * Increase the quantity by one
	 * @return remaining quantity in the stock
	 */
	public int restock() {

		//******************* YOUR CODE HERE ******************
		int Increase_the_quantity;
		Increase_the_quantity=++qty;
		return Increase_the_quantity;
		//*****************************************************
	}

	public String log(){
		String validFormat= this.getName()+","+df.format(this.getPrice())+","+this.getTaxable()+","+this.getQuantity();
		return validFormat;
	}

	/*public String logBonus(){
		return "|| Item: "+this.getName()+ ", Price: "+df.format(this.getPrice())+", Taxable: "+this.getTaxable()+", Quantity: "+this.getQuantity()+" ||";
	}*/

	public String logBonus(){
		return this.getName()+ "\t\t\t\t\t"+df.format(this.getPrice())+"\t\t\t\t\t"+this.getTaxable()+"\t\t\t\t\t"+this.getQuantity();
	}
	public String logBonus1(){
		return this.getName()+ "\t\t\t\t"+df.format(this.getPrice())+"\t\t\t\t\t"+this.getTaxable()+"\t\t\t\t\t"+this.getQuantity();
	}
	public String logBonus2(){
		return this.getName()+ "\t\t\t\t\t\t"+df.format(this.getPrice())+"\t\t\t\t\t"+this.getTaxable()+"\t\t\t\t\t"+this.getQuantity();
	}
	public String logBonus3(){
		return this.getName()+ "\t\t\t\t"+df.format(this.getPrice())+"\t\t\t\t\t"+this.getTaxable()+"\t\t\t\t\t"+this.getQuantity();
	}
	public String logBonus4(){
		return this.getName()+ "\t\t\t\t\t\t"+df.format(this.getPrice())+"\t\t\t\t\t"+this.getTaxable()+"\t\t\t\t\t"+this.getQuantity();
	}
	public String logBonusS(){
		return this.getName()+ "\t\t\t\t\t"+df.format(this.getPrice())+"\t\t\t\t\t"+this.getTaxable();
	}
	public String logBonus1S(){
		return this.getName()+ "\t\t\t\t"+df.format(this.getPrice())+"\t\t\t\t\t"+this.getTaxable();
	}
	public String logBonus2S(){
		return this.getName()+ "\t\t\t\t\t\t"+df.format(this.getPrice())+"\t\t\t\t\t"+this.getTaxable();
	}
	public String logBonus3S(){
		return this.getName()+ "\t\t\t\t"+df.format(this.getPrice())+"\t\t\t\t\t"+this.getTaxable();
	}
	public String logBonus4S(){
		return this.getName()+ "\t\t\t\t\t\t"+df.format(this.getPrice())+"\t\t\t\t\t"+this.getTaxable();
	}





	//**************************** DO NOT MODIFY **********************************//
	public String getName(){
		return name;
	}

	public double getPrice(){
		return price;
	}

	public boolean getTaxable(){
		return taxable;
	}

	public int getQuantity(){
		return qty;
	}

	@Override
	public String toString(){
		return "name::" + name
				+ ",price::" + price
				+ ",taxable::" + taxable
				+ ",qty::" + qty;
	}
	//*****************************************************************************//


}
