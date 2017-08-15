/*
 * Deone'Ta Levy	
 * CD Store
 */

package cdstore;


//Define NumberOfCDs Object
public class NumberOfCDs {
    
    private CDInfo CD;
    private int numberOfItems;
    private float total, discount;
    
    //Get and set the number of items
    public void setNumberOfItems(int numberOfItems){
    	this.numberOfItems = numberOfItems;
    }    
    public int getNumberOfItems(){
    	return this.numberOfItems;
    }    
    
    //Get and set CD Information
    public void setCDInfo(CDInfo CD){
    	this.CD = CD;
    }
    public CDInfo getCDInfo(){
    	return this.CD;
    }
    
    //Get and set the discount if applicable
    public void setDiscount(float discount){
    	this.discount = discount;
    }
    public float getDiscount(){
    	return this.discount;
    }
    
    //Get and set total
    public void setTotal(float total){
    	this.total = total;
    }
    public float getTotal(){
    	return this.total;
    }
    
}
