import java.util.ArrayList;

public class OrderBonus{

    //**************************** DO NOT MODIFY **********************************//
    public static int runningID = 0;					// static variable for assigning a unique ID to an order
    public static final double TAX_RATE = 0.07;			// fixed tax rate
    public static final double SHIPPING_RATE = 0.50;	// fixed shipping rate
    public Customer getCustomer;
    private int orderID;			// unique order ID
    private Customer customer;		// customer object who place this order
    private ArrayList<Item> items = new ArrayList<Item>();	// list of items
    private double subTotal = 0.0;				// sub total of this order
    private double tax = 0.0;					// tax amount
    private double shippingFee = 0.0;			// shipping fee (for online customer only)
    private double grandTotal = 0.0;			// summation of sub total, tax, and shipping fee

    //*****************************************************************************//
    /**
     * Constructor to initialize orderID according to the running ID
     * The first order's ID is 1. The second order's ID is 2, and so on.
     */
    public OrderBonus() {
        //******************* YOUR CODE HERE ******************
        this.orderID = ++runningID;
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

    public Customer getCustomer(){
        return customer;
    }

    public ArrayList<Item> getItems(){
        return items;
    }


    //************************** BONUS SESSION ************************************//

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ArrayList<Item> getItemList() {
        return items;
    }

    public void printItemList() {
        if(items.size() <= 1) {
            System.out.println("\nYou have " + items.size() + " items in your basket");
        }
        else{
            System.out.println("\nYou have " + items.size() + " items in your basket");
        }
        System.out.println("-----------------------------------------------------------------");
        System.out.println("Item\t\t\t\t\t\tPrice\t\t\t\t\tTaxable");
        System.out.println("-----------------------------------------------------------------");
        for(int i=0;i<items.size(); i++) {
            if(items.get(i).getName().equals("ice cream")){
                System.out.println((i+1)+". "+ items.get(i).logBonus1S());
            }
            else if(items.get(i).getName().equals("ham")){
                System.out.println((i+1)+". "+ items.get(i).logBonus2S());
            }
            else if(items.get(i).getName().equals("chocolate")){
                System.out.println((i+1)+". "+ items.get(i).logBonus3S());
            }
            else if(items.get(i).getName().equals("milk")){
                System.out.println((i+1)+". "+ items.get(i).logBonus4S());
            }
            else if(items.get(i).getName().equals("salt")){
                System.out.println((i+1)+". "+ items.get(i).logBonus4S());
            }
            else {
                System.out.println((i+1)+". "+ items.get(i).logBonusS());
            }
        }
        System.out.println();
        System.out.println("-----------------------------------------------------------------");
        System.out.println("Total price in the basket: "+ calSubTotal());
        System.out.println("-----------------------------------------------------------------\n");
    }

    public void printItemListForReceipt() {
        if(items.size() <= 1) {
            System.out.println("\nYou have " + items.size() + " items in your basket");
        }
        else{
            System.out.println("\nYou have " + items.size() + " items in your basket");
        }
        System.out.println("-----------------------------------------------------------------------------------------------");
        System.out.println("Item\t\t\t\t\t\tPrice\t\t\t\t\tTaxable");
        System.out.println("-----------------------------------------------------------------------------------------------");
        for(int i=0;i<items.size(); i++) {
            if(items.get(i).getName().equals("ice cream")){
                System.out.println((i+1)+". "+ items.get(i).logBonus1S());
            }
            else if(items.get(i).getName().equals("ham")){
                System.out.println((i+1)+". "+ items.get(i).logBonus2S());
            }
            else if(items.get(i).getName().equals("chocolate")){
                System.out.println((i+1)+". "+ items.get(i).logBonus3S());
            }
            else if(items.get(i).getName().equals("milk")){
                System.out.println((i+1)+". "+ items.get(i).logBonus4S());
            }
            else if(items.get(i).getName().equals("salt")){
                System.out.println((i+1)+". "+ items.get(i).logBonus4S());
            }
            else {
                System.out.println((i+1)+". "+ items.get(i).logBonusS());
            }
        }
        System.out.println();
        System.out.println("-----------------------------------------------------------------------------------------------");
        System.out.println("Total price in the basket: "+ calSubTotal());
        System.out.println("-----------------------------------------------------------------------------------------------");
    }


    public void modifyItem(String currentItem, Item newItem) {
        int position=findItem(currentItem);
        if(position>=0) {
            items.get(position).restock();
            items.set(position, newItem);
            System.out.println("Grocery item "+(position+1)+" has been modified.");
        }
    }

    private void modifyGroceryItem(int position,Item newItem) {
        items.set(position,newItem);
        System.out.println("Grocery item "+(position+1)+" has been modified.");
    }

    public boolean removeItem(String item) {
        boolean isRemove = false;
        int position = findItem(item);
        if(position >= 0) {
            items.get(position).restock();
            items.remove(position);
            isRemove = true;
        }
        return isRemove;
    }

    private void removeGroceryItem(int position) {
        items.remove(position);
    }

    private int findItem(String searchItem) {
        for(int i=0;i<items.size();i++){
            if(items.get(i).getName().equals(searchItem)){
                return items.indexOf(items.get(i));
            }
        }
        return -1;
    }

    public boolean onFile(String searchItem) {
        int position=findItem(searchItem);
        if(position>=0) {
            return true;
        }
        return false;
    }

    //*****************************************************************************//
}
