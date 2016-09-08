package buttons;



import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import defualt.BrainFreezeMain;
import windows_and_panels.HelpPanel;

/**
 * Button for the top tool bar, opens up simple pop-up window (non exclusive) to show opening text 
 * @author David
 *
 */
public class HelpButton extends JButton {
	


	/**
	 * 
	 */
	private static final long serialVersionUID = 4668800733638939272L;

	public HelpButton (){
		super(new ImageIcon(BrainFreezeMain.
				class.getResource("/toolbarButtonGraphics/general/Help16.gif")));
		
		super.setText("Help");
		super.setToolTipText("Help");
		super.setFocusPainted(false);
		super.setFocusable(false);
		
	}
	
	/**
	 * Gives the button its listener = open pop up window with info when clicked
	 */
	public void setListeners(){
		
		this.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// chosen to add new patient
				
				JPanel temp = new HelpPanel();
				JDialog d = new JDialog();
				
				d.setTitle("Help");
				d.setIconImage(new ImageIcon(BrainFreezeMain.class.getResource("/toolbarButtonGraphics/general/Help16.gif")).getImage());
				
				//d.setLayout(new BorderLayout());
				//d.setModalityType(null);
			
				d.setLayout(new GridBagLayout());
				d.add(temp, null);
				//d.setContentPane(temp);
				d.setVisible(true);
				d.pack();
				d.setSize(new Dimension(d.getSize().width+50,d.getSize().height+50));
					
				}
			});
	}
	
	
}
