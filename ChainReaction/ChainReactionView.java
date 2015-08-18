import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * 
 * @author Vishnutej MP
 * 
 * This is a view class which contains design details for frame and panel
 *
 */
public class ChainReactionView {

	public ChainReactionView () {
		JFrame frame = new JFrame ();
		
		frame.setTitle("Chain Reaction");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 500, 394);
		
		JPanel panel = new JPanel();
		
		frame.getContentPane().add(panel, BorderLayout.WEST);
		
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{22, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{30, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);									

		//call button creation functions
	
		for ( int row = 0; row < 10; row++ ) {
			for ( int column = 0; column < 10; column++ ) {				
				ChainReactionModel.box [ row ] [ column ] = new ChainReactionModel.Box ("0");			
				ChainReactionModel.gbc_box [ row ] [ column ] = new GridBagConstraints ();
				
			
				ChainReactionModel.gbc_box [ row ] [ column ].insets = new Insets ( 2, 2, 2, 2 );
				
				ChainReactionModel.gbc_box [ row ] [ column ].gridx = column + 1;
				ChainReactionModel.gbc_box [ row ] [ column ].gridy = row + 1;
				panel.add(ChainReactionModel.box [ row ] [ column ], ChainReactionModel.gbc_box [ row ] [ column ]);
				
				ChainReactionModel.box [ row ] [ column ].setValues(ChainReactionModel.gbc_box [ row ] [ column ].gridx, ChainReactionModel.gbc_box [ row ] [ column ].gridy );	
				
				ChainReactionModel.box [ row ] [ column ].addActionListener(ChainReactionModel.box [ row ] [ column ]);
			}			
		}
		
		frame.setVisible(true);
		frame.setResizable(false);
		
		
		frame.getContentPane().add(ChainReactionModel.panelPlayer, BorderLayout.SOUTH);
		
		
		ChainReactionModel.playerTurn.setVerticalAlignment(SwingConstants.BOTTOM);
		
		ChainReactionModel.playerTurn.setText("This is " + ChainReactionModel.playerOne + "'s Turn!");
		ChainReactionModel.playerTurn.setForeground(Color.blue);
		
		ChainReactionModel.panelPlayer.add(ChainReactionModel.playerTurn);
	}
}
