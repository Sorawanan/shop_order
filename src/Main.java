import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static OrderBonus orderBonus = new OrderBonus();
    /**
     *    DON'T FORGET TO CHANGE filename WITH YOUR "stock.txt" PATH
     */
    private static final String filename = "C:\\Users\\User\\Code\\Project02\\testcase_init\\stocks.txt";
    public static Map<String, Item> stockData = new HashMap<String, Item>();
    public static void main(String[] args) {
        int CustomerChecker = 0;
        boolean NextProcess = false;
        System.out.println("\n=======================================================================================================");
        System.out.println("\t   ________   __       __    _________   ________  ________   __       __  _________  _________  ");
        System.out.println("\t /   ___   \\ |  |     |  |  /  _____  \\  |   __  \\ |   __  \\ |  |     |  ||  _______||  _______|  ");
        System.out.println("\t|   |   |__| |  |     |  | |  |     |  | |  |  |  ||  |  |  ||  |     |  ||  |\t    |  |");
        System.out.println("\t|   |_____   |  |_____|  | |  |     |  | |  |__|  ||  |__|  ||  |_____|  ||  |______ |  |______");
        System.out.println("\t \\_______  \\ |   _____   | |  |     |  | |  _____/ |  _____/ |   _____   ||   ______||   ______|");
        System.out.println("\t ___    |  | |  |     |  | |  |     |  | |  |\t   |  |      |  |     |  ||  |       |  |");
        System.out.println("\t |  |___|  | |  |     |  | |  |_____|  | |  |\t   |  |      |  |     |  ||  |______ |  |______   ");
        System.out.println("\t \\________/  |__|     |__|  \\_________/  |__|      |__|      |__|     |__||_________||_________|");
        System.out.println("");
        System.out.println("=======================================================================================================");
        System.out.println("\nAre you Customer or Online Customer");
        System.out.println("\tfor the Customer, press 1");
        System.out.println("\tfor the Online Customer, press 2");
        String CustomerName;
        double distance;
        while(!NextProcess){
            System.out.print("\nPress option: ");
            if(!scanner.hasNextInt()){
                scanner.nextLine();
                System.out.println("You can only press number 1 or 2");
            }
            else {
                CustomerChecker = scanner.nextInt();
                scanner.nextLine();
                switch (CustomerChecker) {
                    case 1:
                        //Instantiate Customer Object
                        System.out.print("Please enter your name: ");
                        CustomerName = scanner.nextLine();
                        Customer newCustomer = new Customer(CustomerName);
                        orderBonus.setCustomer(newCustomer);
                        NextProcess = true;
                        break;
                    case 2:
                        //Instantiate OnlineCustomer Object
                        System.out.print("Please enter your name: ");
                        CustomerName = scanner.nextLine();
                        System.out.print("Please enter your distance: ");
                        distance = scanner.nextDouble();
                        CustomerOnline newCustomerOnline = new CustomerOnline(CustomerName, distance);
                        orderBonus.setCustomer(newCustomerOnline);
                        NextProcess = true;
                        break;
                    default:
                        System.out.println("Please enter only number 1 or 2");
                        break;
                }
            }
        }
        System.out.println("-----------------------------------------------------");
        System.out.println(" Hi "+ orderBonus.getCustomer().getName() + "!");
        System.out.println("-----------------------------------------------------");
        initStock(filename);
        int count = 0;
        boolean quit = false;
        int choice = 0;
        printInstructions();
        while (!quit) {
            System.out.print("Enter your choice: ");
            if(!scanner.hasNextInt()){
                scanner.nextLine();
                System.out.println("You can only press number 0-7\n");
            }else {
                choice = scanner.nextInt();
                scanner.nextLine();
                count++;
                switch (choice) {
                    case 0:
                        printInstructions();
                        if(count % 5 == 0) printInstruction();
                        break;
                    case 1:
                        printItemInStock();
                        System.out.println();
                        printInstruction();
                        break;
                    case 2:
                        orderBonus.printItemList();
                        if(count % 5 == 0) printInstruction();
                        break;
                    case 3:
                        if(orderBonus.getItems().size()<10) {
                            addItem();
                            if(count % 5 == 0){
                                printInstruction();
                            }
                        }
                        else System.out.println("\nyou can only add less or equal than 10 items in each order");
                        break;
                    case 4:
                        modifyItem();
                        if(count % 5 == 0) printInstruction();
                        break;
                    case 5:
                        removeItem();
                        if(count % 5 == 0) printInstruction();
                        break;
                    case 6:
                        searchForItem();
                        //Check whether provided item name match each item in Stock.txt
                        //Output this item (.log())
                        //Otherwise, print "Do not find your item name provided"
                        if(count % 5 == 0) printInstruction();
                        break;
                    case 7:
                        printReceipt();
                        quit = true;
                        break;
                    default:
                        System.out.println("Please enter only number 0-7\n");
                        break;
                }
            }
        }
        //************************************************************************//
    }

    public static void printInstruction(){
        System.out.println("Press 0 for checking options");
        System.out.println("Press 7 for printing your order receipt and quit the application\n");
    }

    public static void printInstructions() {
        System.out.println("\nPress ");
        System.out.println("-------------------------------------------------------------------");
        System.out.println("\t === 0 - To print choice options. ");
        System.out.println("\t === 1 - To print the list of items in stock");
        System.out.println("\t === 2 - To print the list in your basket.");
        System.out.println("\t === 3 - To add an item to the basket.");
        System.out.println("\t === 4 - To modify an item in the basket.");
        System.out.println("\t === 5 - To remove an item from the basket.");
        System.out.println("\t === 6 - To search for an item in the stock.");
        System.out.println("\t === 7 - To print receipt and quit the application.");
        System.out.println("-------------------------------------------------------------------");
    }
    /* public static void printInstructions() {
         System.out.println("\nPress ");
         System.out.println("-------------------------------------------------------");
         System.out.println("\t 0 - To print choice options.");
         System.out.println("\t 1 - To print the list of items in stock");
         System.out.println("\t 2 - To print the list in your basket.");
         System.out.println("\t 3 - To add an item to the list.");
         System.out.println("\t 4 - To modify an item in the list.");
         System.out.println("\t 5 - To remove an item from the list.");
         System.out.println("\t 6 - To search for an item in the list.");
         System.out.println("\t 7 - To print receipt and quit the application.");
         System.out.println("-------------------------------------------------------");
     }*/
    public static void initStock(String filename) {

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
        //*****************************************************
    }

    public static void printItemInStock(){
        System.out.println("\nList of items in stock");
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println("Item\t\t\t\t\tPrice\t\t\t\t\tTaxable\t\t\t\t\tQuantity");
        System.out.println("---------------------------------------------------------------------------------------------");
        for(Item eachItem : stockData.values()){
            if(eachItem.getName().equals("ice cream")){
                System.out.println(eachItem.logBonus1());
            }
            else if(eachItem.getName().equals("ham")){
                System.out.println(eachItem.logBonus2());
            }
            else if(eachItem.getName().equals("chocolate")){
                System.out.println(eachItem.logBonus3());
            }
            else {
                System.out.println(eachItem.logBonus());
            }
        }
        System.out.println("---------------------------------------------------------------------------------------------");
    }

    public static void addItem() {
        System.out.print("\nPlease enter the grocery item: ");
        addItem(scanner.nextLine());
    }

    public static void addItem(String itemname){
        boolean isAdded = false;
        for(Item eachItem : stockData.values()){
            if(itemname.equals(eachItem.getName()) && eachItem.isAvailable()){
                orderBonus.getItemList().add(eachItem);
                eachItem.sold();
                System.out.println("Item: " + itemname +" is added into the basket\n" );
                isAdded = true;
            }
        }
        if(!isAdded)
            System.out.println("This item is not available in stock\n");
    }

    public static boolean isValidInStock(String itemname){
        boolean isValid = false;
        try {
            FileReader fileReader = new FileReader(new File(filename));
            BufferedReader reader = new BufferedReader(fileReader);
            String eachItem = "";
            while ((eachItem = reader.readLine()) != null) {
                Pattern validItem = Pattern.compile(("([\\w\\W]+)[,]+([\\d]+[\\d\\.]+)[,]+([true|false]+)[,]+([\\d]+)"));
                Matcher isValidItem = validItem.matcher(eachItem);
                if (isValidItem.matches() && itemname.equals(isValidItem.group(1))) {
                    isValid = true;
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return isValid;
    }

    public static Item initializeItem(String itemname){
        try {
            FileReader fileReader = new FileReader(new File(filename));
            BufferedReader reader = new BufferedReader(fileReader);
            String eachItem = "";
            String name = "";
            double price = 0;
            boolean taxable;
            int qty = 0;
            while ((eachItem = reader.readLine()) != null) {
                Pattern validItem = Pattern.compile(("([\\w\\W]+)[,]+([\\d]+[\\d\\.]+)[,]+([true|false]+)[,]+([\\d]+)"));
                Matcher isValidItem = validItem.matcher(eachItem);
                if (isValidItem.matches() && itemname.equals(isValidItem.group(1))) {
                    name = isValidItem.group(1);
                    price = Double.parseDouble(isValidItem.group(2));
                    taxable = Boolean.parseBoolean(isValidItem.group(3));
                    qty = Integer.parseInt(isValidItem.group(4));
                    Item item = new Item(name, price, taxable, qty);
                    return item;
                }

            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static void modifyItem() {
        System.out.println("\n----------------------------------");
        System.out.println("    MODIFY ITEM IN YOUR BASKET    ");
        System.out.println("----------------------------------");
        System.out.print("Current item name: ");
        String itemNo = scanner.nextLine();
        boolean haveItem = false;
        for(Item item : orderBonus.getItems()) {
            if (itemNo.equals(item.getName())){
                haveItem = true;
            }
        }
        if(haveItem) {
            System.out.print("Enter new item: ");
            String newItem = scanner.nextLine();
            if(isValidInStock(newItem)) {
                Item item = initializeItem(newItem);
                orderBonus.modifyItem(itemNo, item);
            }
        }
        else{
            System.out.println("There is no " + itemNo +"+ in your input");
        }
    }

    public static void removeItem() {
        Boolean haveItem = false;
        System.out.println("\n----------------------------------");
        System.out.println("    REMOVE ITEM IN YOUR BASKET    ");
        System.out.println("----------------------------------");
        System.out.print("Enter item name: ");
        String itemNo = scanner.nextLine();
        for(Item item : orderBonus.getItemList()){
            if (itemNo.equals(item.getName())) {
                haveItem = true;
            }
        }
        if(!haveItem){
            System.out.println("There is no "+ itemNo +" in your Grocery\n");
        }
        else if(orderBonus.removeItem(itemNo)){
            orderBonus.removeItem(itemNo);
            System.out.println(itemNo + " is successfully removed\n");
        }
    }

    public static void searchForItem() {
        System.out.println("-----------------------------------------------------");
        System.out.println("\t\t    SEARCH ITEM IN YOUR BASKET    ");
        System.out.println("-----------------------------------------------------");
        System.out.print("Item you want to search in the stock: ");
        String searchItem = scanner.nextLine();
        if(isValidInStock(searchItem)) {
            System.out.println(">>>> Found " + searchItem+" <<<<");
        } else
        {
            System.out.println(">>>> "+searchItem + " is not on stock. <<<<");
        }
    }

    public static void printReceipt() {
        System.out.println("\n\n===============================================================================================");
        System.out.println("\t\t ________   __________   ________  __________  __   _______   __________ ");
        System.out.println("\t\t|   __   |  |  ______|  / _______\\ |  ______| |  | |   __  \\ |___   ____|");
        System.out.println("\t\t|  |__| /   |  |_____  | /\t\t   |  |_____  |  | |  |__|  |    |  |");
        System.out.println("\t\t|  ___\t\\   |  ______| | |         |  ______| |  | |  _____/\t |  |");
        System.out.println("\t\t|  |  \\\t \\  |  |_____  | \\_______  |  |_____  |  | |  |\t\t\t |  |");
        System.out.println("\t\t|__|   \\__\\ |________|  \\________/ |________| |__| |__|          |__|   ");
        System.out.println("\nName: "+orderBonus.getCustomer().getName());
        System.out.println("===============================================================================================");
        orderBonus.printItemListForReceipt();
        //  System.out.println("Sub-total: "+orderBonus.calSubTotal());
        System.out.println("tax amount: "+orderBonus.calTax());
        System.out.println("Shipping fee: "+orderBonus.calShippingFee());
        System.out.println("Grand total: "+orderBonus.calGrandTotal());
        System.out.println("==============================================================================================");
        System.out.println("thank you");


    }
}
