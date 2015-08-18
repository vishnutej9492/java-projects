import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * 
 * @author Vishnutej MP
 * 
 * 
 * Model class which contains data and logic for Chain Reaction Game
 * 
 */
public class ChainReactionModel extends JFrame {	
	/**
	 * 
	 */
	private static final long serialVersionUID = -55808178925652630L;

	//To represent boxes
	static Box[][] box = new Box [ 10 ] [ 10 ];
	
	//GridBag Constraints
	static GridBagConstraints[][] gbc_box = new GridBagConstraints [ 10 ][ 10 ];
	
	//To maintain queue of boxes while running BFS
	static LinkedList<Box> boxQueue = new LinkedList<Box> ();
	
	//Player 1 Name
	static String playerOne;
	
	//Player 2 Name
	static String playerTwo;
	
	//To represent player name
	static JLabel playerTurn = new JLabel("New label");
	
	//panel where playername is displayed
	static JPanel panelPlayer = new JPanel();

	static String application;
	
	static InetAddress serverIPAddress;
	
	static int serverPortNumber;
	
	static int winner = 0;
	/**
	 * This function implements BFS to traverse all adjancent boxes and 
	 * sees if reaction has happened
	 */
	public static void runOverBoxes () {
		
		while ( !boxQueue.isEmpty()) {
			Box currentBox = boxQueue.pop();
			
			if ( currentBox.currentValue > currentBox.maxValue ) {
				currentBox.currentValue = 0;			
									
				currentBox.setText( "" + currentBox.currentValue + "" );
				Box.changeBoxColor( 0, currentBox );
				
				if ( currentBox.xPosition - 2 >= 0 ) {
					addBoxToQueue ( currentBox.yPosition - 1, currentBox.xPosition - 2, currentBox.owner );	
				}
				
				if ( currentBox.xPosition <= 9 ) {
					addBoxToQueue ( currentBox.yPosition - 1, currentBox.xPosition, currentBox.owner );	
				}

				if ( currentBox.yPosition - 2 >= 0 ) {
					addBoxToQueue ( currentBox.yPosition - 2, currentBox.xPosition - 1, currentBox.owner );	
				}
				
				if ( currentBox.yPosition <= 9 ) {
					addBoxToQueue ( currentBox.yPosition, currentBox.xPosition - 1, currentBox.owner );	
				}	
				
				currentBox.owner = 0;
			}			
		}
		winner = checkWhoWon ();				
	}
	/**
	 * This adds currentBox to queue for further processing on it
	 * @param x
	 * @param y
	 * @param owner
	 */
	public static void addBoxToQueue ( int x, int y, int owner ) {
		box [ x ] [ y ].currentValue++;
		box [ x ] [ y ].owner = owner;
		
		box [ x ] [ y ].setText( "" + box [ x ] [ y ].currentValue + "" );
		Box.changeBoxColor( owner, box [ x ] [ y ] );
		
		if ( box [ x ] [ y ].currentValue > box [ x ] [ y ].maxValue ) {
			boxQueue.add( box [ x ] [ y ] );	
		}
	}
	/**
	 * To check if any player has won the game
	 * it returns 0 if none won and returns 1/2 which indicates player number
	 * @return
	 */
	public static int checkWhoWon () {
		int owner = 0;
		for ( int x = 0; x < 10; x++ ) {
			for ( int y = 0; y < 10; y++ ) {
				if ( box [ x ] [ y ].owner != 0 ) {
					owner = box [ x ] [ y ].owner;
					break;
				}
			}
		}
		
		for ( int x = 0; x < 10; x++ ) {
			for ( int y = 0; y < 10; y++ ) {
				if ( box [ x ] [ y ].owner != owner &&  box [ x ] [ y ].owner != 0 ) {
					return 0;
				}
			}
		}
		return owner;		
	}
	/**
	 * This is to notify players whose turn it is
	 * @param owner
	 */
	public static void changeTurn ( int owner ) {
		if ( owner == 2 ) {			
			playerTurn.setText("This is " + playerOne + "'s Turn!");
			playerTurn.setForeground(Color.blue);
			panelPlayer.add(playerTurn);
		}
		
		else {
			playerTurn.setText("This is " + playerTwo + "'s Turn!");
			playerTurn.setForeground(Color.red);
			panelPlayer.add(playerTurn);
		}
	}
	
	/**
	 * Box class to represent box structure
	 * 
	 * @author Vinayak Pawar
	 * @author Vishnutej M
	 * 
	 */
	public static class Box extends JButton implements ActionListener {

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
		public void actionPerformed(ActionEvent e) {
			if ( ( ChainReactionModel.application.equals("Server") && ( ( ChainReactionModel.Box.counter % 2 ) + 1 ) == 1 ) ||
					( ChainReactionModel.application.equals("Client") && ( ( ChainReactionModel.Box.counter % 2 ) + 1 ) == 2 ) ) {
				
				Main.boxX = this.yPosition - 1;
				Main.boxY = this.xPosition - 1;
				
				Main.boxClicked ( this.yPosition - 1, this.xPosition - 1 );
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
}

