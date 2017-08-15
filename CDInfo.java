/*
 * Deone'Ta Levy	
 * CD Store
 */

package cdstore;

//Define CDInfo class/Object

public class CDInfo {
    
	private float price;
	private int id;
    private String information, name;
    
    
    //This constructor will print a message if CD ID that is entered
    //isn't in the inventory.txt file
    public CDInfo(){
    	setInfo("CD entered is not in our inventory.");
    }
    

//Gets id, name, price, and information
    
    public int getID(){
    	return this.id;
    }
    public String getName(){
    	return this.name;
    }
    public float getPrice(){
    	return this.price;
    }
    public String getInfo(){
    	return this.information;
    }

//Sets id, name, price, and information
    public void setID(int id){
    	this.id = id;
    }
    public void setName(String name){
    	this.name = name;
    }
    public void setPrice(float price){
    	this.price = price;
    }
    public void setInfo(String information){
    	this.information = information;
    }
}
