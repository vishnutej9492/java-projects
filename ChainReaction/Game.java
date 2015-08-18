import java.awt.*;

import javax.swing.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.LinkedList;

import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

/**
 * This Game is called - "Chain Reaction"
 * popular among Android Users.
 * 
 * Game starts with 10x10 boxes marked with 0
 * Two players play it turn by turn
 * 
 * On each turn, player can click on any box
 * Initially, when game starts, all boxes have value 0
 * 
 * All 4 corner boxes can have max value 1
 * All side boxes can have max value 2
 * All other boxes ( inside ) can have max value 3
 * ( in general, box can have max value one less than number of edges it shares 
 * with adjacent boxes )
 * 
 * If box value exceeds, box splits and adds 1 to adjacent boxes
 * with which it is sharing it's side and make them of their own type
 * 
 * Game continues until both players have their items in grid
 * If there exists only items of similar type, that player wins the game!
 * 
 * @author Vinayak Pawar
 * @author Vishnutej M
 * 
 *
 */
public class Game extends JFrame {

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
	
	private static final long serialVersionUID = -7803629994015778818L;

	/**
	 * Main Method.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//Dialogue box to ask player 1 name
					playerOne = JOptionPane.showInputDialog(null, "( Blue ) Enter Player 1 Name : ", "Player 1 Name", 3);
					
					//Dialogue box to ask player 2 name
					playerTwo = JOptionPane.showInputDialog(null, "( Red ) Enter Player 2 Name : ", "Player 2 Name", 3);
					
					//set players' names
					if ( playerOne == null ) {
						playerOne = "Player 1";
					}
					
					if ( playerTwo == null ) {
						playerTwo = "Player 2";
					}
					
					
					Game frame = new Game();
					
					JPanel panel = new JPanel();
					
					frame.getContentPane().add(panel, BorderLayout.WEST);
					
					GridBagLayout gbl_panel = new GridBagLayout();
					gbl_panel.columnWidths = new int[]{22, 0, 0, 0};
					gbl_panel.rowHeights = new int[]{30, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
					gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
					gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
					panel.setLayout(gbl_panel);									
				
					//This creates 10x10 boxes
					for ( int row = 0; row < 10; row++ ) {
						for ( int column = 0; column < 10; column++ ) {				
							box [ row ] [ column ] = new Box ("0");			
							gbc_box [ row ] [ column ] = new GridBagConstraints ();
							
						
							gbc_box [ row ] [ column ].insets = new Insets ( 2, 2, 2, 2 );
							
							gbc_box [ row ] [ column ].gridx = column + 1;
							gbc_box [ row ] [ column ].gridy = row + 1;
							panel.add(box [ row ] [ column ], gbc_box [ row ] [ column ]);
							
							box [ row ] [ column ].setValues(gbc_box [ row ] [ column ].gridx, gbc_box [ row ] [ column ].gridy );	
							
							box [ row ] [ column ].addActionListener(box [ row ] [ column ]);
						}			
					}
					
					frame.setVisible(true);
					frame.setResizable(false);
					
					
					frame.getContentPane().add(panelPlayer, BorderLayout.SOUTH);
					
					
					playerTurn.setVerticalAlignment(SwingConstants.BOTTOM);
					
					playerTurn.setText("This is " + playerOne + "'s Turn!");
					playerTurn.setForeground(Color.blue);
					
					panelPlayer.add(playerTurn);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame. Constructor for frame
	 */
	public Game() {		
		setTitle("Chain Reaction");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 394);
	}
	
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
		int winner = Game.checkWhoWon ();
		if ( winner != 0 ) {
			if ( winner == 1 ) {
				JOptionPane.showMessageDialog(null, playerOne + " has won the Game! Exiting now!");	
			}
			else {
				JOptionPane.showMessageDialog(null, playerTwo + " has won the Game! Exiting now!");
			}
			
			System.exit(0);
		}
		
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
}
