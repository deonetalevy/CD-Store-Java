/*
 * Deone'Ta Levy	
 * CD Store
 */

package cdstore;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;


//StoreEventHandling class handles events
public class StoreEventHandling{
	
	private final String FILE = "inventory.txt",FILE_OUT = "transaction.txt", DISPLAY_TAX_RATE = "6%"; 
	private final float TAX = 0.06f;
	
	private ArrayList<CDInfo> CDList;
	
	private NumberOfOrders numOfOrders; 
	
	private boolean readFile = false;

	//Initializes numOfOrders. Reads file.
	public StoreEventHandling(){
		this.numOfOrders = new NumberOfOrders();
		
		if(!readFile){
			readInventoryFile();
			readFile = true;
		}
	}
	
	//Processes order by setting values determined by user input
	public void processOrder(CDInfo CD,int totalNum, int numOfTotalItems){
		NumberOfCDs Order = new NumberOfCDs();
		Order.setCDInfo(CD);
		Order.setNumberOfItems(totalNum);
		Order.setDiscount(Float.valueOf(getDiscountAmount(totalNum)));
		Order.setTotal(discountCalculation(CD,totalNum));
		
		this.numOfOrders.addOrder(Order);
		this.numOfOrders.setSubtotal(discountCalculation(CD,totalNum));
		this.numOfOrders.setFinalTotal(this.numOfOrders.getSubtotal() + (this.numOfOrders.getSubtotal() * TAX));
		this.numOfOrders.setNumItemsInOrder(numOfTotalItems);
		
		
	}
	
	//Looks for CD in inventory.txt file that has been read 
	public CDInfo findCD(int discIDNumber){
		for(CDInfo cd:CDList){
			if(cd.getID() == discIDNumber){
				return cd;
			}
		}
		return new CDInfo();
	}
	
	//SetCDInfo function that sets info for CD
    public void setCDInfo(CDInfo CD, int amount){
    	CD.setInfo(String.valueOf(CD.getID()) +" "+CD.getName()+" "+String.valueOf(CD.getPrice() + 
				" " + String.valueOf(amount) + " " + String.valueOf(getDiscountAmount(amount))+ "% "+
    			String.valueOf(discountCalculation(CD,amount))));
    }
    
    //Gets subtotal that will be shown to user
    public float getSubtotal(CDInfo CD, int amount){
    	return numOfOrders.getSubtotal() + discountCalculation(CD,amount);
    }
    
    //Gets string containing textual representation of all processed orders
    public String getProcessedOrdersText(){
    	String listOrder = "";
    	int i = 1;
    	
    	//For loop gets CDInfo for each entry from CDInfo class
    	for(NumberOfCDs Order: numOfOrders.getNumberOfCDs()){
    		listOrder = listOrder + String.valueOf(i)+". "+Order.getCDInfo().getInfo()+"\n";
    		i++;
    	}
    	return listOrder;
    }
	
    //Writes order to transaction.txt file
    public void writeInvoice(){
		
    	BufferedWriter bufferedWriter = null;
    	FileWriter fileWriter = null;
		
		try {
			fileWriter = new FileWriter(FILE_OUT,true);
		} catch (IOException e) {
			System.out.println("The specified file could not be opened. An error has occurred.");
			e.printStackTrace();
		}
		bufferedWriter = new BufferedWriter(fileWriter);
		
		for(NumberOfCDs Order : this.numOfOrders.getNumberOfCDs()){
			try {
				bufferedWriter.write(this.numOfOrders.getFormattedTimeStamp()+", "+Order.getCDInfo().getID()+", "
						+Order.getCDInfo().getName()+", "+Order.getCDInfo().getPrice()+", "
						+Order.getNumberOfItems()+", "+Order.getDiscount()+", "+Order.getTotal()+", "
						+this.numOfOrders.getCurrentTime());
				bufferedWriter.newLine();
			} catch (IOException e) {
				System.out.println("Error: Could not write to file.");
				e.printStackTrace();
			}
		}
		try {
			bufferedWriter.close();
			fileWriter.close();
		} catch (IOException a) {
			a.printStackTrace();
		}
		
	}    
    
    //Creates string containing complete order info that user will see
    public String displayInvoice(){
    	this.numOfOrders.setDate();
    	String displayText = "Date: ";
    	
    	displayText = displayText + this.numOfOrders.getCurrentTime() + "\r\n" +
    				"Number of line items: " + this.numOfOrders.getNumOfItems() + "\r\n"
    	 			+ "Item# / ID / Title / Price / Qty / Disc % / Subtotal:\r\n" + getProcessedOrdersText() + "\r\n"
    				+ "Order subtotal: " + this.numOfOrders.getSubtotal() + "\r\n" + "Tax rate:    " + DISPLAY_TAX_RATE + "\r\n"
    				+ "Tax amount:    $" + (this.numOfOrders.getSubtotal() * TAX) + "\r\n"
    				+ "Order total:    " + this.numOfOrders.getFinalTotal() + "\r\n"  
    				+ "Thank you for shopping at the CD store! \r\n";
    	
    	return displayText;
    }
	
    //Function used to determine discount amount
    private int getDiscountAmount(int amount){
    	if(amount < 5){
			return 0; //No discount if less than 5 CDs are bought
		}
    	else if(amount < 10){
			return 10;//10% discount if more than 5 CDs are bought
		}
    	else if(amount < 15){
			return 15;//15% discount if more than 10 CDs are bought
		}
    	else if(amount >= 15){
			return 20;//20% discount if 15 or more CDs are bought
		}
    	else return 0;
    }
	
    //Calculate discount if the order qualifies
    private float discountCalculation(CDInfo CD, int amount){
		float total = CD.getPrice() * (float) amount;
		
		if(amount < 5){
			return total;
		}else if(amount < 10){
			return total - (total * 0.1f);
		}else if(amount < 15){
			return total - (total * 0.15f);
		}else if(amount >= 15){
			return total - (total * 0.2f);
		}else return 0f;
	}
	
    //Read in the inventory.txt file and store it
    private void readInventoryFile(){
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		CDList = new ArrayList<CDInfo>();
		try {
			//read in inventory.txt
			fileReader = new FileReader(FILE);
			
		} catch (FileNotFoundException e) {
			//Notifies user that file couldn't be found
			JOptionPane textBox = new JOptionPane("File could not be open/found.",JOptionPane.ERROR_MESSAGE);
			textBox.setVisible(true);
			e.printStackTrace();
		}	
		
		try {
			bufferedReader = new BufferedReader(fileReader);
			
			String lineOfText;
			while((lineOfText = bufferedReader.readLine()) != null){
				String[] currentCD = new String[3];
				CDInfo CD = new CDInfo();
				
				currentCD = lineOfText.split(",", 3); //Comma separates strings in file
				CD.setID(Integer.valueOf(currentCD[0]));
				CD.setName(currentCD[1]);
				CD.setPrice(Float.valueOf(currentCD[2]));
				
				CDList.add(CD); //CD added to the CDList arraylist
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
