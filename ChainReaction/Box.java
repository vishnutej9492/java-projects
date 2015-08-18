import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * Box class to represent box structure
 * 
 * @author Vinayak Pawar
 * @author Vishnutej M
 * 
 */
public class Box extends JButton implements ActionListener {

	//state represents color of text
	static String[] state = { "black", "blue", "red" };
	
	static int counter = 0;
	private static final long serialVersionUID = 2548716465232757132L;
	
	int id;
	
	//0 for none, 1 for blue, 2 for red
	int owner;

	//4 corners -1, sides - 2, others - 3
	int maxValue;
	
	//current value of blue or red in blue or red
	int currentValue;
	
	//xPosition number
	int xPosition;
	
	//yPosition number
	int yPosition;
	
	/**
	 * Box constructor
	 * @param text
	 */
	public Box ( String text ) {
		super ( text );
	}
	
	/**
	 * This sets values for Box
	 * @param x
	 * @param y
	 */
	public void setValues ( int x, int y ) {
	//	System.out.println(this.getText());
		this.id = Integer.parseInt( this.getText() );
		this.owner = 0;
		this.xPosition = x;
		this.yPosition = y;
		
		//Initially sets max value 3 for all boxes
		this.maxValue = 3;
		
		//sets maxvalue 2 for side boxes
		if ( this.xPosition == 1 || this.yPosition == 1 || 
				this.xPosition == 10 || this.yPosition == 10 ) {
			this.maxValue = 2;
		}
		
		//sets maxvalue 1 for corner boxes
		if ( ( this.xPosition == 1 && this.yPosition == 1 ) ||
				( this.xPosition == 1 && this.yPosition == 10 ) ||
				( this.xPosition == 10 && this.yPosition == 1 ) ||
				( this.xPosition == 10 && this.yPosition == 10 ) ) {
			this.maxValue = 1;
		}
		//resets current value to 0
		this.currentValue = 0;				
	}

	/**
	 * Box click Action
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if ( this.owner == 0 || this.owner == ( counter % 2 ) + 1 ) {
			this.owner = ( counter % 2 ) + 1;
			this.setText( "" + ++this.currentValue + "" );
			this.setFont(this.getFont().deriveFont(Font.BOLD));
			
			Box.changeBoxColor(this.owner, this );
			
			if ( this.currentValue > this.maxValue ) {
				Game.boxQueue.add( this );
				Game.runOverBoxes ();	
			}
			
			Game.changeTurn ( this.owner );
			counter++;						
		}		
						
	}	
	/**
	 * This changes color of box depending on play
	 * 
	 * @param owner
	 * @param temp
	 */
	public static void changeBoxColor ( int owner, Box temp ) {
		switch ( owner ) {
		case 0 : temp.setForeground( Color.black );
				 break;
				 
		case 1 : temp.setForeground( Color.blue );
				 break;
				 
		case 2 : temp.setForeground( Color.red );
				 break;
		}
	}
}
