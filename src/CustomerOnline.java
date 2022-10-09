//GROUP 12
//Student ID: 6388012, 6388019, 6388022
//Section 1


public class CustomerOnline extends Customer implements Loggable{

	//**************************** DO NOT MODIFY **********************************//
	private double distance;	// delivery distance

	//*****************************************************************************//

	/**
	 * Constructor initializes customer's name and delivery distance
	 * @param name
	 * @param distance
	 */
	public CustomerOnline(String name, double distance) {
		//******************* YOUR CODE HERE ******************
		super(name);
		this.distance = distance;
		//*****************************************************
	}

	/**
	 * Constructor initializes customer's ID, name and delivery distance
	 * @param id
	 * @param name
	 * @param distance
	 */
	public CustomerOnline(int id, String name, double distance) {
		//******************* YOUR CODE HERE ******************
		super(id,name);
		this.distance = distance;
		//*****************************************************
	}

	public String log(){
		return this.getCustID()+","+this.getName()+","+df.format(this.distance);
	}

	//**************************** DO NOT MODIFY **********************************//
	public double getDistance() {
		return this.distance;
	}

	@Override
	public String toString() {
		return super.toString() + "," + distance;
	}
	//*****************************************************************************//


}