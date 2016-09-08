package windows_and_panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import defualt.BrainFreezeMain;
import objects.ByFontSize_JLabel;
import objects.Patient;

/**
 * Shows patient information, allows for its change. 
 * Also gives a text are to contain notes about the patient. 
 * @author David
 *
 */
public class PatientDataPanel extends JPanel{	// extends but not really used.. go figure. 
	
	
	private static final long serialVersionUID = -7835660161061186436L;
	
	private static JPanel patientDataPane;
	private static JScrollPane scrollPane; // bottom
	private static JLabel patientInfoPageTitle = new ByFontSize_JLabel("Patient Information:", ByFontSize_JLabel.Size.XLT);
	private static JTextArea notes;
	private static JButton	editInformation;
	
	private static JLabel name;
//	private static JLabel age;
//	private static JLabel ID;
//	private static JLabel condition;
	
	/**
	 * Create the panel and add set it as the active view of the right panel
	 * @param windowRight = panel will be shown here
	 */
	public static void getPatientDataPanel(JLabel windowRight) {

		patientDataPane = new JPanel();
		patientDataPane.setLayout(new BoxLayout(patientDataPane, BoxLayout.Y_AXIS));	// main grid, simply one line under the other 
		
		JPanel temp = new JPanel();
		temp.setMaximumSize(new Dimension(temp.getMaximumSize().width, temp.getMinimumSize().height));
		temp.add(patientInfoPageTitle);
		patientDataPane.add(temp);
		
		//patientDataPane.add(new JSeparator(SwingConstants.HORIZONTAL));
		//patientDataPane.add(Box.createHorizontalStrut(5));
		
		JPanel top = new JPanel(); 
		//top.setLayout(new BoxLayout(top, BoxLayout.LINE_AXIS));
		top.setLayout(new GridLayout(1, 2));
		top.setPreferredSize(top.getMinimumSize());

		
		editInformation = new JButton("Edit Patient Information");
		editInformation.setFont(new Font("Serif", Font.PLAIN, 20));
		editInformation.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Patient p = BrainFreezeMain.patients.get(BrainFreezeMain.currentPatientIndex);
				ArrayList<String> constructorInput = null;
				constructorInput = NewPatientWindow.showInputdialog(false);
				
				// if cancel or close chosen on new patient window:
				if (constructorInput == null)
					return; 
				
				p.updateInfo(constructorInput.toArray( new String[constructorInput.size()]), true);
				initInfoLabels();
				LeftPanel.refreshPatientLabel();	// refres becauase patient data has changed. 
				
			}
		});
	
		name = new ByFontSize_JLabel(ByFontSize_JLabel.Size.XL);				

		
		name.setHorizontalAlignment(JLabel.CENTER);
	
		top.add(name);
		
		temp = new JPanel();
		temp.setLayout(new BoxLayout(temp, BoxLayout.LINE_AXIS));
		temp.add(Box.createHorizontalGlue());
		temp.add(editInformation);
		temp.add(Box.createHorizontalGlue());
		top.add(temp);

		top.setBorder(BorderFactory.createEtchedBorder());

		
		patientDataPane.add(top);
		
		
		notes = new JTextArea();
		notes.setLineWrap (true);
		notes.setWrapStyleWord (true);

		notes.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				BrainFreezeMain.patients.get(BrainFreezeMain.currentPatientIndex).setNotes(((JTextArea)e.getSource()).getText());
				notes.setText("");
				notes.setText(new String(BrainFreezeMain.patients.get(BrainFreezeMain.currentPatientIndex).getNotes()));
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		scrollPane = new JScrollPane(notes);

		
		JLabel noteTitle = new ByFontSize_JLabel("Enter Patient Notes Below:", ByFontSize_JLabel.Size.T);
		
		JPanel bottom = new JPanel();
		bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
		bottom.add(noteTitle);
		bottom.add(scrollPane);
		patientDataPane.add(bottom);
		
		
		initInfoLabels();

		windowRight.add(patientDataPane, BorderLayout.CENTER);	// update right window to show this panel 
	}
	

	
	private static void initInfoLabels(){
		Patient p = BrainFreezeMain.patients.get(BrainFreezeMain.currentPatientIndex);

		notes.setText(null);
		notes.setText(p.getNotes());
		
		name.setText("<html>" + p.toString() + "</html>");
	}
	

}
