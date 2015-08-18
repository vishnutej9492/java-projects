import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

public class Controller implements ActionListener {

	// Constructor for the class
	BattleShip m;

	public Controller(BattleShip m) {
		this.m = m;
	}

	/**
	 * This method handles events from the Menu and the board.
	 * 
	 */
	// action listener
	public void actionPerformed(ActionEvent e) {
		String classname = m.getClassName(e.getSource());
		// Handle the event from the user clicking on a command button
		if (classname.equals("JButton")) {
			JButton button = (JButton) (e.getSource());
			// Row and columns for the game !
			int bnum = Integer.parseInt(button.getActionCommand());
			int row = bnum / m.GRID_SIZE;
			int col = bnum % m.GRID_SIZE;

			/**
			 * HANDLING THE USER CLICK FOR THE GAME !
			 */

			// IF PLAYER 1 IS PLAYING
			if (m.player1) {
				if (m.shipP2[row][col] == true) {
					// CLICKS ON THE BUTTONG AND TURN TO RED IF THE SHIP
					// AVAILABLE ON THE OPPONENTS SAME POSITION
					m.fireShot(row, col, button, Color.RED);
					// INCREASE THE SCORE BY 1
					m.p1count++;
					// TOTAL 3 X 4 = 12 POINTS
					if (m.p1count == 12) {
						// IF USER EARNS 12 POINTS HE WINS
						JOptionPane.showMessageDialog(m.frame, "Player1 Wins");
						System.exit(0);
					}
				} else {
					// IF NO SHIP ON THAT POSITION -
					button.setText("X");
				}
				// ALLOWING CHANCE TO PLAYER 2
				button.setEnabled(false);
				m.player1 = false;
				// DISABLING THE BOARD FOR PLAYER 1
				for (int i = 0; i < m.GRID_SIZE * m.GRID_SIZE; i++) {
					m.buttonArrayP1_right[i].setEnabled(false);
				}
				// ENABLING THE BOARD FOR PLAYER 2
				for (int i = 0; i < m.GRID_SIZE * m.GRID_SIZE; i++) {
					if (!m.buttonArrayP2_right[i].getText().equals("X")
							&& m.buttonArrayP2_right[i].getBackground() != Color.RED) {
						m.buttonArrayP2_right[i].setEnabled(true);
					}
				}
			}
			/**
			 * PERFORMING THE EXACT OPPOSITE OF PLAYER 1 TURNS
			 */
			else {
				// IF HE FINDS THE SAME POSITION TURN BUTTON TO RED
				if (m.shipP1[row][col] == true) {
					m.fireShot(row, col, button, Color.RED);
					// INCREASE POINT BY 1
					m.p2count++;
					// IF 12 POINTS USER 2 WINS
					if (m.p1count == 12) {
						JOptionPane.showMessageDialog(m.frame, "Player2 Wins");
						System.exit(1);
					}
				} else {
					button.setText("X");
				}
				// SET TO DISABLE
				button.setEnabled(false);
				// ENABLE PLAYER 1 TURN
				m.player1 = true;
				// SET THE PLAYER 2 BOARD DISABLE
				for (int i = 0; i < m.GRID_SIZE * m.GRID_SIZE; i++) {
					m.buttonArrayP2_right[i].setEnabled(false);
				}
				// SET THE PLAYER 1 BOARD ENABLE
				for (int i = 0; i < m.GRID_SIZE * m.GRID_SIZE; i++) {
					if (!m.buttonArrayP1_right[i].getText().equals("X")
							&& m.buttonArrayP1_right[i].getBackground() != Color.RED) {
						m.buttonArrayP1_right[i].setEnabled(true);
					}
				}
			}

		}
	}

}
