
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * BattleShip.java	
 *
 */
public class BattleShip extends JFrame
{
	private JMenuItem newGame, quit, about, restart;

	private JMenuBar bar;
	private JMenu fileMenu, optionMenu, challenge;
	private JCheckBoxMenuItem easy, normal, impossible;
	private static JCheckBoxMenuItem sound;

	private JPanel statusBar;
	private JLabel status;

	private Container c;
	
	private int difficulty = 1;				//easy is 0, normal is 1, impossible is 2!

	private JPanel 	buttonPanel = new JPanel();

	public BattleShip()
	{
		super("BattleShip");
		c = getContentPane();

		ItemHandler itemHandler = new ItemHandler();

		setJMenuBar(bar = new JMenuBar());

		(fileMenu = new JMenu("File")).setMnemonic('F');		// This is the File Menu

		

		newGame = new JMenuItem("New Game...",'G');
		newGame.addActionListener(itemHandler);
		fileMenu.add(newGame);

		restart = new JMenuItem("Restart Application",'R');
		restart.addActionListener(itemHandler);
		restart.setEnabled(false);
		fileMenu.add(restart);

		fileMenu.addSeparator();
		about = new JMenuItem("About...",'A');
		about.addActionListener(itemHandler);
		fileMenu.add(about);

		quit = new JMenuItem("Exit",'x');
		quit.addActionListener(itemHandler);
		fileMenu.add(quit);

		(optionMenu = new JMenu("Options")).setMnemonic('O');	 // This is the Console Menu

		challenge = new JMenu("Game Difficulty");

		(easy = new JCheckBoxMenuItem("Easy")).addActionListener(itemHandler);
		(normal = new JCheckBoxMenuItem("Normal")).setSelected(true);
		normal.addActionListener(itemHandler);
		(impossible = new JCheckBoxMenuItem("Impossible")).addActionListener(itemHandler);

		challenge.add(easy);
		challenge.add(normal);
		challenge.add(impossible);
		optionMenu.add(challenge);
		
		bar.add(fileMenu);
		bar.add(optionMenu);
		
		setPreferredSize(new Dimension(600,400));
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		pack();
		
		
	}

	private class ItemHandler implements ActionListener
	{
		public void actionPerformed( ActionEvent e )
		{
			if (e.getSource()==newGame)
			{
				String level = null;
				switch (difficulty)
				{
					case 0:	level = "Easy";			break;
					case 1:	level = "Normal";		break;
					case 2:	level = "Impossible";	break;
				}
				restart.setEnabled(true);
			}
			if (e.getSource()==restart)
			{
			}
			if (e.getSource()==about) {
				JOptionPane.showMessageDialog(c,
					"BattleShip Game\n\n" +
					"Program Design:        \n" +
					"Board Design:          \n" +
					"Game Logic:            \n\n" +
					"Created: ",
					"About BattleShip", JOptionPane.PLAIN_MESSAGE);
			}
			if (e.getSource()==quit)	{  System.exit(0);	}
			if (e.getSource()==easy)
			{
				if (normal.isSelected() || impossible.isSelected())
				{
					normal.setSelected(false);
					impossible.setSelected(false);
					difficulty = 0;
				}
				else easy.setSelected(true);
			}
			if (e.getSource()==normal)
			{
				if (easy.isSelected() || impossible.isSelected())
				{
					easy.setSelected(false);
					impossible.setSelected(false);
					difficulty = 1;
				}
				else normal.setSelected(true);
			}
			if (e.getSource()==impossible)
			{
				if (easy.isSelected() || normal.isSelected())
				{
					normal.setSelected(false);
					easy.setSelected(false);
					difficulty = 2;
				}
				else impossible.setSelected(true);
			}
		}
	}

	public static void main(String [] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new BattleShip().setVisible(true);
			}
		});
	}
}
