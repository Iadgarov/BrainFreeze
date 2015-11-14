package windows;

import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

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
	public static ArrayList<String> showInputdialog() {
		
	ArrayList<String> returnMe = new ArrayList<>();	
		
	JTextField firstName = new JTextField();
	JTextField middleName = new JTextField();
	JTextField lastName = new JTextField();
	JTextField age = new JTextField();
	JTextField IDnum = new JTextField();
	JTextField condition = new JTextField();
	
	final JComponent[] inputs = new JComponent[] {
			new JLabel("First Name"),
			firstName,
			new JLabel("middle Name"),
			middleName,
			new JLabel("Last Name"),
			lastName,
			new JLabel("age"),
			age,
			new JLabel("I.D. Number"),
			IDnum,
			new JLabel("Cause of brain damage"),
			condition
	};
	JOptionPane.showMessageDialog(null, inputs, "My custom dialog", JOptionPane.PLAIN_MESSAGE);
	/*
	System.out.println("You entered " +
			firstName.getText() + ", " +
			middleName.getText() + ", " +
			lastName.getText() + ", " +
			age.getText() + ", " +
			IDnum.getText() + ", " +
			condition.getText());
	*/

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
	
	return returnMe;
	
	}
}
