package windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class TestWindow {

	private static ArrayList<JButton> buttons;
	
	public static void launchTestWindow (){
		
		JFrame testWindow = new JFrame();
		
		testWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
		//testWindow.setUndecorated(true);

		setButtons();
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 1.0;
		
		for (int i = 0; i < buttons.size(); i++) {
		    buttonPanel.add(buttons.get(i), c);
		}

		buttonPanel.setBackground(Color.DARK_GRAY);
		testWindow.add(buttonPanel);

		testWindow.pack();
		testWindow.setVisible(true);
		
		
	}
	
	public static void setButtons(){
		
		buttons = new ArrayList<>();
		Dimension d = new Dimension(150, 150);
		
		for (int i = 0; i < 9; i++){
			
			JButton button = new JButton(String.valueOf(i+1));
			
			button.setFont(new Font("Arial", Font.PLAIN, 40));
			button.setBackground(Color.GRAY);
			button.setBorder(new LineBorder(Color.WHITE, 6));
			button.setSize(d);
			button.setMinimumSize(d);
			button.setPreferredSize(d);
			
			
			buttons.add(button);
			
		}
		
	}
}
