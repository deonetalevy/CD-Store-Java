/*
 * Deone'Ta Levy	
 * CD Store
 */
package cdstore;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

//CD Store Window creation
public class CDStoreWindow{
	
	private static CDInfo CD;
	private static int orderNumber;
	private static JFrame CDStoreWindow;
	private static JPanel panel;
	private static SpringLayout layout;
	private static StoreEventHandling events;
    
	private static JTextField numOfItems, CDIDnumber, amountOfOrders, CDInformation, orderTotal;
	private static JLabel lNumOfItems, lCDIDnumber, lAmountOfOrders, lCDInformation, lOrderTotal;
	private static JButton processItem, confirmItem, viewOrder, finishOrder, newOrder, exit;
	
	//Initialize the JFrame
	public static void main(String[] args) {
		new CDStoreWindow().StartThread();
		orderNumber = 1;
        InitWindow();
        
	}
   
	//Create the window
	private static void InitWindow(){
    	CDStoreWindow = new JFrame("CD Store");
    	panel  = new JPanel();
    	panel.setSize(685, 200);
        CDStoreWindow.setSize(685, 200);
             
        InitButtons();
        InitLabels();
        InitText();
        InitPlacement();
      
        CDStoreWindow.add(panel);
        CDStoreWindow.setVisible(true);
    }
    
	//Add JLabels to panel and set background color
	private static void InitLabels(){
    	lNumOfItems = new JLabel("Enter the number of items for this order:");
    	lCDIDnumber = new JLabel("Enter CD ID for item #"+String.valueOf(orderNumber)+":");
    	lAmountOfOrders = new JLabel("Enter quantity for item #"+String.valueOf(orderNumber)+":");
    	lCDInformation = new JLabel("Item #"+String.valueOf(orderNumber)+" info:");
    	lOrderTotal = new JLabel("Order subtotal for "+String.valueOf(orderNumber-1)+" item(s):");
    	
        panel.add(lNumOfItems);
        panel.add(lCDIDnumber);
        panel.add(lAmountOfOrders);
        panel.add(lCDInformation);
        panel.add(lOrderTotal);
        panel.setOpaque(true);
        panel.setBackground(Color.green);
    }
    
	//Create text fields and key listeners
	private static void InitText(){
    	numOfItems = new JTextField(15);
    	CDIDnumber = new JTextField(15);
    	amountOfOrders = new JTextField(15);
    	CDInformation = new JTextField(50);
    	orderTotal = new JTextField(15);
    	
    	CDIDnumber.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent arg0) {
			}
			@Override
			public void keyReleased(KeyEvent arg0) {	
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				if((arg0.getKeyCode() == KeyEvent.VK_ENTER) && !CDIDnumber.getText().isEmpty()){
					
					CD = events.findCD(Integer.valueOf(CDIDnumber.getText()));
					
					if(!amountOfOrders.getText().isEmpty())
						events.setCDInfo(CD, Integer.valueOf(amountOfOrders.getText()));
					else events.setCDInfo(CD, 1);
					AutoComplete(CD);
				}
				
			}});
    	
    	CDIDnumber.addFocusListener(new FocusListener(){
			@Override
			public void focusGained(FocusEvent e) {	
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(!CDIDnumber.getText().isEmpty()){
					CD = events.findCD(Integer.valueOf(CDIDnumber.getText()));
					
					if(!amountOfOrders.getText().isEmpty())
						events.setCDInfo(CD, Integer.valueOf(amountOfOrders.getText()));
					else events.setCDInfo(CD, 1);
					
					AutoComplete(CD);
				}}});
    	
    	amountOfOrders.addFocusListener(new FocusListener(){
			@Override
			public void focusGained(FocusEvent arg0) {
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				if(!amountOfOrders.getText().isEmpty() && !CDIDnumber.getText().isEmpty()){
					CD = events.findCD(Integer.valueOf(CDIDnumber.getText()));
					events.setCDInfo(CD, Integer.valueOf(amountOfOrders.getText()));				
					AutoComplete(CD);
				}
				
			}});
    	
    	amountOfOrders.addKeyListener(new KeyListener(){
			@Override
			public void keyPressed(KeyEvent arg0) {			
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				if(Character.isDigit(arg0.getKeyChar())){
					CD = events.findCD(Integer.valueOf(CDIDnumber.getText()));
					events.setCDInfo(CD, Integer.valueOf(amountOfOrders.getText()));
					AutoComplete(CD);
				}
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
			}});
    	
    	//Can't edit the following anymore
    	CDInformation.setEnabled(false);
    	orderTotal.setEditable(false);
    	
        panel.add(numOfItems);
        panel.add(CDIDnumber);
        panel.add(amountOfOrders);
        panel.add(CDInformation);
        panel.add(orderTotal);
    }
    
	//Creates buttons and adds listeners
	private static void InitButtons(){
    	processItem = new JButton("Process Item #"+String.valueOf(orderNumber)+"");
    	confirmItem = new JButton("Confirm Item #"+String.valueOf(orderNumber)+"");
    	viewOrder = new JButton("View Order");
    	finishOrder = new JButton("Finish Order");
    	newOrder = new JButton("New Order");
    	exit = new JButton("Exit");
    	
    	//Can't process item yet
    	processItem.setEnabled(false);
    	
    	processItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				events.processOrder(CD, Integer.valueOf(amountOfOrders.getText()), Integer.valueOf(numOfItems.getText()));
				if(orderNumber < Integer.valueOf(numOfItems.getText())){
					numOfItems.setEditable(false);
					numOfItems.setEditable(false);
					confirmItem.setEnabled(true);
					processItem.setEnabled(false);
					
					CDIDnumber.setText("");
					amountOfOrders.setText("");
					orderNumber++;
					RedrawLabels();
				}else{
					confirmItem.setEnabled(false);
					processItem.setEnabled(false);
				}
			}});
    	confirmItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(panel,"Item #"+String.valueOf(orderNumber)+" accepted");
				numOfItems.setEditable(false);
				processItem.setEnabled(true);
				confirmItem.setEnabled(false);
			}});
    	viewOrder.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(panel, events.getProcessedOrdersText());
			}});
    	finishOrder.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(panel, events.displayInvoice());
				events.writeInvoice();
				NewOrderClick();
			}});
    	newOrder.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				NewOrderClick();
			}});
    	exit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				CDStoreWindow.dispose();
			}});
    	
    	panel.add(processItem);
        panel.add(confirmItem);
        panel.add(viewOrder);
        panel.add(finishOrder);
        panel.add(newOrder);
        panel.add(exit);
    }
	
	//Create SpringLayout
	private static void InitPlacement(){
    	layout = new SpringLayout();
    	layout.putConstraint(SpringLayout.NORTH,lNumOfItems, 5, SpringLayout.NORTH,panel);
    	layout.putConstraint(SpringLayout.NORTH,numOfItems, 5, SpringLayout.NORTH,panel);
    	layout.putConstraint(SpringLayout.WEST, lNumOfItems, 5, SpringLayout.WEST, panel);
    	layout.putConstraint(SpringLayout.WEST, numOfItems, 5, SpringLayout.EAST, lNumOfItems); 	
    	layout.putConstraint(SpringLayout.NORTH,lCDIDnumber, 7, SpringLayout.SOUTH,lNumOfItems);
    	layout.putConstraint(SpringLayout.WEST, lCDIDnumber, 5, SpringLayout.WEST, panel);
    	layout.putConstraint(SpringLayout.NORTH, CDIDnumber, 5, SpringLayout.SOUTH, numOfItems);
    	layout.putConstraint(SpringLayout.WEST, CDIDnumber, 5, SpringLayout.EAST, lCDIDnumber);    	
    	layout.putConstraint(SpringLayout.NORTH,lAmountOfOrders, 10, SpringLayout.SOUTH,lCDIDnumber);
    	layout.putConstraint(SpringLayout.WEST, lAmountOfOrders, 5, SpringLayout.WEST, panel);
    	layout.putConstraint(SpringLayout.NORTH, amountOfOrders, 5, SpringLayout.SOUTH, CDIDnumber);
    	layout.putConstraint(SpringLayout.WEST, amountOfOrders, 5, SpringLayout.EAST, lAmountOfOrders);
    	layout.putConstraint(SpringLayout.NORTH,lCDInformation, 10, SpringLayout.SOUTH,lAmountOfOrders);
    	layout.putConstraint(SpringLayout.WEST, lCDInformation, 5, SpringLayout.WEST, panel);
    	layout.putConstraint(SpringLayout.NORTH, CDInformation, 5, SpringLayout.SOUTH, amountOfOrders);
    	layout.putConstraint(SpringLayout.WEST, CDInformation, 5, SpringLayout.EAST, lCDInformation);    	
    	layout.putConstraint(SpringLayout.NORTH,lOrderTotal, 10, SpringLayout.SOUTH,lCDInformation);
    	layout.putConstraint(SpringLayout.WEST, lOrderTotal, 5, SpringLayout.WEST, panel);
    	layout.putConstraint(SpringLayout.NORTH, orderTotal, 5, SpringLayout.SOUTH, CDInformation);
    	layout.putConstraint(SpringLayout.WEST, orderTotal, 5, SpringLayout.EAST, lOrderTotal);    	
    	layout.putConstraint(SpringLayout.NORTH, processItem, 10, SpringLayout.SOUTH, lOrderTotal);
    	layout.putConstraint(SpringLayout.NORTH, confirmItem, 10, SpringLayout.SOUTH, lOrderTotal);
    	layout.putConstraint(SpringLayout.NORTH, viewOrder, 10, SpringLayout.SOUTH, lOrderTotal);
    	layout.putConstraint(SpringLayout.NORTH, finishOrder, 10, SpringLayout.SOUTH, lOrderTotal);
    	layout.putConstraint(SpringLayout.NORTH, newOrder, 10, SpringLayout.SOUTH, lOrderTotal);
    	layout.putConstraint(SpringLayout.NORTH, exit, 10, SpringLayout.SOUTH, lOrderTotal);    	
    	layout.putConstraint(SpringLayout.WEST, processItem, 10, SpringLayout.WEST, panel);
    	layout.putConstraint(SpringLayout.WEST, confirmItem, 10, SpringLayout.EAST, processItem);
    	layout.putConstraint(SpringLayout.WEST, viewOrder, 10, SpringLayout.EAST, confirmItem);
    	layout.putConstraint(SpringLayout.WEST, finishOrder, 10, SpringLayout.EAST, viewOrder);
    	layout.putConstraint(SpringLayout.WEST, newOrder, 10, SpringLayout.EAST, finishOrder);
    	layout.putConstraint(SpringLayout.WEST, exit, 10, SpringLayout.EAST, newOrder);
    	
    	panel.setLayout(layout);
    }
	
	//Sets CD Info text
	private static void AutoComplete(CDInfo CD){
    	CDInformation.setText(CD.getInfo());
    	if(!amountOfOrders.getText().isEmpty())
    		orderTotal.setText(String.valueOf(events.getSubtotal(CD, Integer.valueOf(amountOfOrders.getText()))));
    	else{
    		amountOfOrders.setText("1");
    		orderTotal.setText(String.valueOf(events.getSubtotal(CD,1)));
    	}
    }
    
	//Changes item labels if Order has changed
	private static void RedrawLabels(){
    	processItem.setText("Process Item #"+String.valueOf(orderNumber)+"");
    	confirmItem.setText("Confirm Item #"+String.valueOf(orderNumber)+"");
    	lCDIDnumber.setText("Enter CD ID for item #"+String.valueOf(orderNumber)+":");
    	lAmountOfOrders.setText("Enter quantity for item #"+String.valueOf(orderNumber)+":");
    	lCDInformation.setText("Item #"+String.valueOf(orderNumber)+" info:");
    	lOrderTotal.setText("Order subtotal for "+String.valueOf(orderNumber-1)+" item(s):");
    }
    
	//Restarts window when New Order button is clicked
	private static void NewOrderClick(){
		new CDStoreWindow().StartThread();
    	orderNumber = 1;
		
    	CDIDnumber.setText("");
		amountOfOrders.setText("");
		CDInformation.setText("");
    	orderTotal.setText("");
		numOfItems.setText("");
		
		RedrawLabels();
		
		numOfItems.setEditable(true);
		confirmItem.setEnabled(true);
		processItem.setEnabled(false);
    }

	

	class OrderThread extends Thread{
		@Override
		public void run() {
			events = new StoreEventHandling();
		}
	}
	
	private void StartThread(){
		new OrderThread().start();
	}
}


