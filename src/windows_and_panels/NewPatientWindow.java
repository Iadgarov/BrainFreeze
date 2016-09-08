package windows_and_panels;

import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import objects.ByFontSize_JLabel;

/**
 * 
 * @author David
 *
 *
 * pops up a new windows asking for patient info
 * on "OK" craetes patient. 
 * on close or cancel return null
 */
public class NewPatientWindow {

	// return list of patient information for the constructor.
	// return null upon cancel or close of window
	public static ArrayList<String> showInputdialog(boolean fresh) {
		
		ArrayList<String> returnMe = new ArrayList<>();	
			
		JTextField firstName = new JTextField();
		JTextField middleName = new JTextField();
		JTextField lastName = new JTextField();
		JTextField age = new JTextField();
		JTextField IDnum = new JTextField();
		JTextField condition = new JTextField();
		
		JComponent verticalStrut = (JComponent) Box.createVerticalStrut(30);
	
		
		final JComponent[] INPUTS = new JComponent[] {
				new ByFontSize_JLabel("First Name", ByFontSize_JLabel.Size.L),
				firstName,
				new ByFontSize_JLabel("middle Name", ByFontSize_JLabel.Size.L),
				middleName,
				new ByFontSize_JLabel("Last Name", ByFontSize_JLabel.Size.L),
				lastName,
				new ByFontSize_JLabel("Age", ByFontSize_JLabel.Size.L),
				age,
				new ByFontSize_JLabel("I.D. Number", ByFontSize_JLabel.Size.L),
				IDnum,
				new ByFontSize_JLabel("Cause of brain damage", ByFontSize_JLabel.Size.L),
				condition,
				verticalStrut,
				(JComponent) Box.createHorizontalStrut(200)
				
		};
		
		
		String title = fresh ? "New Patient Dialog" : "Edit Patient Dialog";
		      
		int chosenOption = JOptionPane.showConfirmDialog( null, INPUTS, title, 
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, MainWindow.myAppImage);
	
	
		if (firstName.getText().length() > 0)
			returnMe.add(objects.Patient.FIRST_NAME_IDENTIFIER + firstName.getText());
		
		if (middleName.getText().length() > 0)
			returnMe.add(objects.Patient.MIDDLE_NAME_IDENTIFIER + middleName.getText());
		
		if (lastName.getText().length() > 0)
			returnMe.add(objects.Patient.LAST_NAME_IDENTIFIER + lastName.getText());
		
		if (age.getText().length() > 0)
			returnMe.add(objects.Patient.AGE_IDENTIFIER + age.getText());
		
		if (IDnum.getText().length() > 0)
			returnMe.add(objects.Patient.ID_IDENTIFIER + IDnum.getText());
		
		if (condition.getText().length() > 0)
			returnMe.add(objects.Patient.CONDITON_IDENTIFIER + condition.getText());
		
		if (chosenOption == JOptionPane.CANCEL_OPTION ||
				chosenOption == JOptionPane.CLOSED_OPTION)
			return null;
		
		//System.out.println(chosenOption);
		return returnMe;
	
	}
}
