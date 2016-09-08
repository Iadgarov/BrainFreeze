package buttons;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.UIManager;

import windows_and_panels.RightPanel;

/**
 * Top toolbar button, opens settings screen on click
 * @author David
 *
 */
public class SettingsButton extends JButton {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 5629009165510869479L;

	public SettingsButton (){
		super(UIManager.getIcon("FileChooser.detailsViewIcon"));
		super.setText("Settings");
		super.setToolTipText("View and Edit Program Settings");
		super.setFocusPainted(false);
		super.setFocusable(false);
		
	}
	
	/**
	 * Set the right panel to show the settings page
	 */
	public void setListeners(){
		
		this.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				RightPanel.swapRepresentation(RightPanel.SETTINGS);
					
				}
			});
	}
	
	
}
