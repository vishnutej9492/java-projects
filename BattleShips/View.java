import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class View {

	// CONSTRUCTOR FOR THE CLASS
	BattleShip m;

	public View(BattleShip m) {
		this.m = m;
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 */
	// view
	public void createAndShowGUI() {
		// Create and set up the window.
		m.frame = new JFrame("BATTLE SHIP GAME");
		m.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		int maxGap = 20;
		int ButtonWidth = 20;
		int ButtonHeight = 1;

		JPanel gui = new JPanel(new GridLayout(2, 2, 20, 40));
		gui.setBorder(new EmptyBorder(5, 5, 5, 5));
		// Set up components preferred size
		JButton b = new JButton();
		Dimension buttonSize = b.getPreferredSize();

		// BUTTONS FOR THE 4 LAYOUTS
		m.buttonArrayP1_left = new JButton[m.GRID_SIZE * m.GRID_SIZE];
		m.buttonArrayP1_right = new JButton[m.GRID_SIZE * m.GRID_SIZE];
		m.buttonArrayP2_left = new JButton[m.GRID_SIZE * m.GRID_SIZE];
		m.buttonArrayP2_right = new JButton[m.GRID_SIZE * m.GRID_SIZE];

		// HELPER TO CHECK IF CODE IS EXECUTED
		System.out.println("Initializaing");

		int numButtons = m.GRID_SIZE * m.GRID_SIZE;
		m.buttonArrayP1_left = new JButton[numButtons];
		m.buttonArrayP2_left = new JButton[numButtons];
		m.buttonArrayP1_right = new JButton[numButtons];
		m.buttonArrayP2_right = new JButton[numButtons];

		Container paneL1 = m.createContentPane(m.buttonArrayP1_left, "P1L");
		Container paneL2 = m.createContentPane(m.buttonArrayP2_left, "P2L");
		Container paneR1 = m.createContentPane(m.buttonArrayP1_right, "P1R");
		Container paneR2 = m.createContentPane(m.buttonArrayP2_right, "P2R");

		b.setPreferredSize(new Dimension(ButtonWidth, ButtonHeight));

		// ADDING THE LABELS FOR VIEW OF THE USER
		gui.add(new JLabel("Player 1 Ships"));
		gui.add(paneL1);
		gui.add(new JLabel("Player 1 Arena"));
		gui.add(paneR1);
		gui.add(new JLabel("Player 2 Ships"));
		gui.add(paneL2);
		gui.add(new JLabel("Player 2 Arena"));
		gui.add(paneR2);
		m.frame.setContentPane(gui);

		System.out.println("Done creating");
		markShips();
		// Display the window, setting the size
		m.frame.setSize(1100, 650);
		m.frame.setLocationRelativeTo(null);
		m.frame.setVisible(true);
	}

	/**
	 * Marking 4 ships for each of the player for a new game !
	 */
	// view
	public void markShips() {
		/**
		 * MARKING ALL THE SHIPS RANDOMLY FOR THE GAME TO BEGIN
		 * 
		 * EACH SHIP IS NUMBERED
		 * 
		 * THERE ARE 4 SHIPS ON EACH PLAYERS BOARD OF LENGTH 3
		 * 
		 * EACH SHIP IS GIVER A RESPECTIVE NUMBER 1 2 3 4
		 */
		m.shipP1[0][1] = true;
		// System.out.println(getId(0, 1));
		m.fireShot(0, 1, m.buttonArrayP1_left[m.getId(0, 1)]);
		m.buttonArrayP1_left[m.getId(0, 1)].setText("1");
		m.shipP1[0][2] = true;
		m.fireShot(0, 2, m.buttonArrayP1_left[m.getId(0, 2)]);
		m.buttonArrayP1_left[m.getId(0, 2)].setText("1");
		m.shipP1[0][3] = true;
		m.fireShot(0, 3, m.buttonArrayP1_left[m.getId(0, 3)]);
		m.buttonArrayP1_left[m.getId(0, 3)].setText("1");

		m.shipP1[1][5] = true;
		m.fireShot(1, 5, m.buttonArrayP1_left[m.getId(1, 5)]);
		m.buttonArrayP1_left[m.getId(1, 5)].setText("2");
		m.shipP1[2][5] = true;
		m.fireShot(2, 5, m.buttonArrayP1_left[m.getId(2, 5)]);
		m.buttonArrayP1_left[m.getId(2, 5)].setText("2");
		m.shipP1[3][5] = true;
		m.fireShot(3, 5, m.buttonArrayP1_left[m.getId(3, 5)]);
		m.buttonArrayP1_left[m.getId(3, 5)].setText("2");

		m.shipP1[2][0] = true;
		m.fireShot(2, 0, m.buttonArrayP1_left[m.getId(2, 0)]);
		m.buttonArrayP1_left[m.getId(2, 0)].setText("3");
		m.shipP1[2][1] = true;
		m.fireShot(2, 1, m.buttonArrayP1_left[m.getId(2, 1)]);
		m.buttonArrayP1_left[m.getId(2, 1)].setText("3");
		m.shipP1[2][2] = true;
		m.fireShot(2, 2, m.buttonArrayP1_left[m.getId(2, 2)]);
		m.buttonArrayP1_left[m.getId(2, 2)].setText("3");

		m.shipP1[3][3] = true;
		m.fireShot(3, 3, m.buttonArrayP1_left[m.getId(3, 3)]);
		m.buttonArrayP1_left[m.getId(3, 3)].setText("4");
		m.shipP1[4][3] = true;
		m.fireShot(4, 3, m.buttonArrayP1_left[m.getId(4, 3)]);
		m.buttonArrayP1_left[m.getId(4, 3)].setText("4");
		m.shipP1[5][3] = true;
		m.fireShot(5, 3, m.buttonArrayP1_left[m.getId(5, 3)]);
		m.buttonArrayP1_left[m.getId(5, 3)].setText("4");

		m.shipP2[1][0] = true;
		m.fireShot(1, 0, m.buttonArrayP2_left[m.getId(1, 0)]);
		m.buttonArrayP2_left[m.getId(1, 0)].setText("1");
		m.shipP2[2][0] = true;
		m.fireShot(2, 0, m.buttonArrayP2_left[m.getId(2, 0)]);
		m.buttonArrayP2_left[m.getId(2, 0)].setText("1");
		m.shipP2[3][0] = true;
		m.fireShot(3, 0, m.buttonArrayP2_left[m.getId(3, 0)]);
		m.buttonArrayP2_left[m.getId(3, 0)].setText("1");

		m.shipP2[5][1] = true;
		m.fireShot(5, 1, m.buttonArrayP2_left[m.getId(5, 1)]);
		m.buttonArrayP2_left[m.getId(5, 1)].setText("2");
		m.shipP2[5][2] = true;
		m.fireShot(5, 2, m.buttonArrayP2_left[m.getId(5, 2)]);
		m.buttonArrayP2_left[m.getId(5, 2)].setText("2");
		m.shipP2[5][3] = true;
		m.fireShot(5, 3, m.buttonArrayP2_left[m.getId(5, 3)]);
		m.buttonArrayP2_left[m.getId(5, 3)].setText("2");

		m.shipP2[1][2] = true;
		m.fireShot(1, 2, m.buttonArrayP2_left[m.getId(1, 2)]);
		m.buttonArrayP2_left[m.getId(1, 2)].setText("3");
		m.shipP2[1][3] = true;
		m.fireShot(1, 3, m.buttonArrayP2_left[m.getId(1, 3)]);
		m.buttonArrayP2_left[m.getId(1, 3)].setText("3");
		m.shipP2[1][4] = true;
		m.fireShot(1, 4, m.buttonArrayP2_left[m.getId(1, 4)]);
		m.buttonArrayP2_left[m.getId(1, 4)].setText("3");

		m.shipP2[3][5] = true;
		m.fireShot(3, 5, m.buttonArrayP2_left[m.getId(3, 5)]);
		m.buttonArrayP2_left[m.getId(3, 5)].setText("4");
		m.shipP2[4][5] = true;
		m.fireShot(4, 5, m.buttonArrayP2_left[m.getId(4, 5)]);
		m.buttonArrayP2_left[m.getId(4, 5)].setText("4");
		m.shipP2[5][5] = true;
		m.fireShot(5, 5, m.buttonArrayP2_left[m.getId(5, 5)]);
		m.buttonArrayP2_left[m.getId(5, 5)].setText("4");

	}

}
