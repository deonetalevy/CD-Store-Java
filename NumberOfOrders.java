/*
 * Deone'Ta Levy	
 * CD Store
 */

package cdstore;


//Import necessary classes
import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;


//Create NumberOfOrders class that contains how many different orders someone wants
public class NumberOfOrders {
	
	//define variables
	private ArrayList<NumberOfCDs> Order;
	private int numOfItems = 0;
    private float subtotal = 0, finalTotal = 0;
    private String currentTime, formattedTimeStamp;
    
    
    
    //Initializing the Order arraylist
    public NumberOfOrders(){
    	this.Order = new ArrayList<NumberOfCDs>();
    }

    //Set the current subtotal
    public void setSubtotal(float subtotal){
    	this.subtotal += subtotal;
    }
    
    //Set the final total
    public void setFinalTotal(float finalTotal){
    	this.finalTotal = finalTotal;
    }
    
    //Set the number of items in the order
    public void setNumItemsInOrder(int numOfItems){
    	this.numOfItems = numOfItems;
    }
    
    //Setting currentTime and formattedTimeStamp
    public void setDate(){
    	Date date = new Date();
    	DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    	this.currentTime = dateFormat.format(date);
    	dateFormat = new SimpleDateFormat("MMddyyHHmmss");
    	this.formattedTimeStamp = dateFormat.format(date);
    }
    
    
    public void addOrder(NumberOfCDs Order){
    	this.Order.add(Order);
    }
    
    //Get the current time
    public String getCurrentTime(){
    	return this.currentTime;
    }
    
    //Get the Formatted Time for order
    public String getFormattedTimeStamp(){
    	return this.formattedTimeStamp;
    }

    //Round function for totals and subtotals
    float roundTwoDecimals(float d){
    	DecimalFormat twoDForm = new DecimalFormat("#.##");
    	return Float.valueOf(twoDForm.format(d));
    	
    }
    //Get the current subtotal
    public float getSubtotal(){
    	return roundTwoDecimals(this.subtotal);
    	//return this.subtotal;
    }
    
    //Get the final total
    public float getFinalTotal(){
    	return roundTwoDecimals(this.finalTotal);
    	//return this.finalTotal;
    }
    
    //Get the number of items in the order
    public int getNumOfItems(){
    	return this.numOfItems;
    }

    //Get the number of CDs for the order
    public ArrayList<NumberOfCDs> getNumberOfCDs(){
    	return this.Order;
    }
    
}
