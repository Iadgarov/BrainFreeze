package buttons;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.UIManager;

import defualt.BrainFreezeMain;
import objects.Patient;
import windows_and_panels.LeftPanel;
import windows_and_panels.NewPatientWindow;

/**
 * Top toolbar button, open new atient creation dialog 
 * @author David
 *
 */
public class NewPatientButton extends JButton {
	


	/**
	 * 
	 */
	private static final long serialVersionUID = 4668800733638939272L;

	public NewPatientButton (){
		super(UIManager.getIcon("FileView.fileIcon"));
		super.setText("New Patient");
		super.setToolTipText("Create new patient file");
		super.setFocusPainted(false);
		super.setFocusable(false);
		
	}
	
	/**
	 * Opens new patient creation dialog 
	 */
	public void setListeners(){
		
		this.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// chosen to add new patient
				
				ArrayList<String> constructorInput = null;
				constructorInput = NewPatientWindow.showInputdialog(true);
				
				// if cancel or close chosen on new patient window:
				if (constructorInput == null)
					return; 
				
				//else:
				BrainFreezeMain.patients.add(new Patient(
						(constructorInput.toArray( new String[constructorInput.size()]))));
				BrainFreezeMain.currentPatientIndex = BrainFreezeMain.patients.size()-1;
				
				
				LeftPanel.refreshPatientLabel(); // change to patient list, update label
				LeftPanel.refreshPatientData();  // change to patient, update data
					
				}
			});
	}
	
	
}
