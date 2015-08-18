import java.awt.EventQueue;
import java.awt.Font;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;


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
 * @author Vishnutej M.
 * 
 * Main Class contains main function
 *
 */
public class Main {
	
	static int boxX = -1;
	static int boxY = -1;
	
	static Socket socket = null;
	static ServerSocket serverSocket = null;
	
	static DataOutputStream out;
	static DataInputStream in;
	
	static InputStream input;
	
	/**
	 * this is a box click function
	 * @param x
	 * @param y
	 */
	
	public static void boxClicked ( int x, int y ) {
			
		if ( ChainReactionModel.box [ x ] [ y ].owner == 0 || 
				ChainReactionModel.box [ x ] [ y ].owner == ( ChainReactionModel.Box.counter % 2 ) + 1 ) {
			ChainReactionModel.box [ x ] [ y ].owner = ( ChainReactionModel.Box.counter % 2 ) + 1;
			ChainReactionModel.box [ x ] [ y ].setText( "" + ++ChainReactionModel.box [ x ] [ y ].currentValue + "" );
			ChainReactionModel.box [ x ] [ y ].setFont(ChainReactionModel.box [ x ] [ y ].getFont().deriveFont(Font.BOLD));
			
			ChainReactionModel.Box.changeBoxColor(ChainReactionModel.box [ x ] [ y ].owner, ChainReactionModel.box [ x ] [ y ] );
			
			if ( ChainReactionModel.box [ x ] [ y ].currentValue > ChainReactionModel.box [ x ] [ y ].maxValue ) {
				ChainReactionModel.boxQueue.add( ChainReactionModel.box [ x ] [ y ] );
				ChainReactionModel.runOverBoxes ();	
			}
			
			ChainReactionModel.changeTurn ( ChainReactionModel.box [ x ] [ y ].owner );
			ChainReactionModel.Box.counter++;
			
			try {
				
				//After processing box click operation, box details are sent to client/server
				
				out.writeUTF( Integer.toString(x) );
				out.writeUTF( Integer.toString(y) );
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	/**
	 * when box details are received from client/server this function is processed
	 * @param x
	 * @param y
	 */
	public static void changeBox ( int x, int y ) {
		ChainReactionModel.box [ x ] [ y ].owner = ( ChainReactionModel.Box.counter % 2 ) + 1;
		ChainReactionModel.box [ x ] [ y ].setText( "" + ++ChainReactionModel.box [ x ] [ y ].currentValue + "" );
		ChainReactionModel.box [ x ] [ y ].setFont(ChainReactionModel.box [ x ] [ y ].getFont().deriveFont(Font.BOLD));
		
		ChainReactionModel.Box.changeBoxColor(ChainReactionModel.box [ x ] [ y ].owner, ChainReactionModel.box [ x ] [ y ] );
		
		if ( ChainReactionModel.box [ x ] [ y ].currentValue > ChainReactionModel.box [ x ] [ y ].maxValue ) {
			ChainReactionModel.boxQueue.add( ChainReactionModel.box [ x ] [ y ] );
			ChainReactionModel.runOverBoxes ();	
		}
		
		ChainReactionModel.changeTurn ( ChainReactionModel.box [ x ] [ y ].owner );
		ChainReactionModel.Box.counter++;
		
		if ( ChainReactionModel.winner != 0 ) {
			if ( ChainReactionModel.winner == 1 ) {
				JOptionPane.showMessageDialog(null, ChainReactionModel.playerOne + " has won the Game! Exiting now!");	
			}
			else {
				JOptionPane.showMessageDialog(null, ChainReactionModel.playerTwo + " has won the Game! Exiting now!");
			}
			
			System.exit(0);
		}
	}
	/**
	 * main function
	 * @param args
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					try {
						Object[] possibilities = { "Server", "Client" };
						String appType = (String)JOptionPane.showInputDialog(
						                    null,
						                    "Is this a Server or a Client ? \n( If you press cancel, This app will act as Server)",
						                    "",
						                    JOptionPane.PLAIN_MESSAGE,
						                    null,
						                    possibilities,
						                    "Server");
						
						if ( appType.equals("Client") ) {							
							ChainReactionModel.application = "Client";
							
							String serverIP = (String)JOptionPane.showInputDialog(
				                    null,
				                    "Enter Server IP Address : ",
				                    "Server IP Address",
				                    JOptionPane.PLAIN_MESSAGE,
				                    null,
				                    null,
				                    "127.0.0.1");
							
							try {
								ChainReactionModel.serverIPAddress = InetAddress.getByName(serverIP);
							} 
							
							catch (Exception e) {
								ChainReactionModel.serverIPAddress = InetAddress.getByName("127.0.0.1");
							}
							
							String portNumber = (String)JOptionPane.showInputDialog(
				                    null,
				                    "Enter port number for Server : ",
				                    "Server Port Number",
				                    JOptionPane.PLAIN_MESSAGE,
				                    null,
				                    null,
				                    "3000");
							
							try {
								ChainReactionModel.serverPortNumber = Integer.parseInt(portNumber);
							} 
							
							catch (Exception e) {
								ChainReactionModel.serverPortNumber = 3000;
							}
						}
						
						else if (appType.equals("Server") ) {							
							ChainReactionModel.application = "Server";
							String portNumber = (String)JOptionPane.showInputDialog(
				                    null,
				                    "Enter port number for Server : ",
				                    "Server Port Number",
				                    JOptionPane.PLAIN_MESSAGE,
				                    null,
				                    null,
				                    "3000");
							
							try {
								ChainReactionModel.serverPortNumber = Integer.parseInt(portNumber);
							} catch (Exception e) {
								ChainReactionModel.serverPortNumber = 3000;
							}
						}
						
						else {
							System.out.println("null");
							ChainReactionModel.serverPortNumber = 3000;
						}
					} 
					
					catch (java.lang.NullPointerException e) {						
						ChainReactionModel.application = "Server";
						ChainReactionModel.serverPortNumber = 3000;
					}
					
					//communication ----
					
					if ( ChainReactionModel.application.equals("Server") ) {
						serverSocket = new ServerSocket ( ChainReactionModel.serverPortNumber);

						//Dialogue box to ask player 1 name
						ChainReactionModel.playerOne = 
								JOptionPane.showInputDialog(null, "( Blue ) Enter Player 1 Name : ", "Player 1 Name", 3);
						
						if ( ChainReactionModel.playerOne == null ) {
							ChainReactionModel.playerOne = "Player 1";
						}
						//JOptionPane.showMessageDialog(null, "Waiting for client ...");
						socket = serverSocket.accept();
						
						//Inform client player 1 name
						out = new DataOutputStream (socket.getOutputStream());						
						out.writeUTF(ChainReactionModel.playerOne);
						
						//take player 2 name from client
						input = socket.getInputStream();
						in = new DataInputStream ( input );

						ChainReactionModel.playerTwo = in.readUTF();
						
						ChainReactionView newView = new ChainReactionView ();
						
						Thread boxThread = new Thread ( new Runnable () {

							@Override
							public void run() {
								while ( true ) {								
									try {
										
										Main.changeBox(Integer.parseInt( in.readUTF() ), Integer.parseInt( in.readUTF() ));
										
										if ( ChainReactionModel.winner != 0 ) {
											if ( ChainReactionModel.winner == 1 ) {
												JOptionPane.showMessageDialog(null, ChainReactionModel.playerOne + " has won the Game! Exiting now!");	
											}
											else {
												JOptionPane.showMessageDialog(null, ChainReactionModel.playerTwo + " has won the Game! Exiting now!");
											}
											
											System.exit(0);
										}
									} 
									catch (IOException e) {
										if ( ChainReactionModel.winner != 0 ) {
											if ( ChainReactionModel.winner == 1 ) {
												JOptionPane.showMessageDialog(null, ChainReactionModel.playerOne + " has won the Game! Exiting now!");	
											}
											else {
												JOptionPane.showMessageDialog(null, ChainReactionModel.playerTwo + " has won the Game! Exiting now!");
											}
											
											System.exit(0);
										}
										JOptionPane.showMessageDialog(null, "Client aborted!");
										System.exit(0);
									}																		
								}								
							}
							
						});
						
						boxThread.start ();
					}
					
					else if ( ChainReactionModel.application.equals("Client") ) {
						socket = new Socket (ChainReactionModel.serverIPAddress, ChainReactionModel.serverPortNumber );
						
						//Dialogue box to ask player 2 name
						ChainReactionModel.playerTwo = 
								JOptionPane.showInputDialog(null, "( Red ) Enter Player 2 Name : ", "Player 2 Name", 3);						
						
						if ( ChainReactionModel.playerTwo == null ) {
							ChainReactionModel.playerTwo = "Player 2";
						}
						
						input = socket.getInputStream();
						in = new DataInputStream ( input );
						
						ChainReactionModel.playerOne = in.readUTF();
						
						out = new DataOutputStream (socket.getOutputStream());						
						out.writeUTF(ChainReactionModel.playerTwo);
						
						ChainReactionView newView = new ChainReactionView ();
						
						Thread boxThread = new Thread ( new Runnable () {

							@Override
							public void run() {
								while ( true ) {
									try {										
										Main.changeBox(Integer.parseInt( in.readUTF() ), Integer.parseInt( in.readUTF() ));
										
										if ( ChainReactionModel.winner != 0 ) {
											if ( ChainReactionModel.winner == 1 ) {
												JOptionPane.showMessageDialog(null, ChainReactionModel.playerOne + " has won the Game! Exiting now!");	
											}
											else {
												JOptionPane.showMessageDialog(null, ChainReactionModel.playerTwo + " has won the Game! Exiting now!");
											}
											
											System.exit(0);
										}
									} 
									catch (IOException e) {
										if ( ChainReactionModel.winner != 0 ) {
											if ( ChainReactionModel.winner == 1 ) {
												JOptionPane.showMessageDialog(null, ChainReactionModel.playerOne + " has won the Game! Exiting now!");	
											}
											else {
												JOptionPane.showMessageDialog(null, ChainReactionModel.playerTwo + " has won the Game! Exiting now!");
											}
											
											System.exit(0);
										}
										JOptionPane.showMessageDialog(null, "Server aborted!");
										System.exit(0);
									}
								}								
							}
							
						});
						
						boxThread.start();
					}
				} 
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
