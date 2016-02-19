package mainWindow;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import defualt.BrainFreezeMain;
import objects.Measurements;
import utility.ResultTypes;
import windows.TestWindow;

public class LeftPanel {
	
	private static JLabel patientLabelPointer;	// pointer to text representation of patient on the left
	private static GroupLayout dataPanelLayoutPointer; // pointer to data choosing panel under patient name to the left
	
	private static GroupLayout.Group currentHorizontalDataGroup; // the current horizontal group in the layout for lower half of the left pane
	private static GroupLayout.Group currentVerticalDataGroup; // the current vertical group in the layout for the lower half of the left pane
	
	// not always visible components
	private static JComboBox<String> resultChoiceList; // drop down list
	private static JLabel noDataText; // a label to indicate that no data is available for this patient
	private static JButton collectDataButton;
	
	
	protected static void addLeftPanel(JSplitPane splitPane){
		
		JPanel windowLeft = new JPanel();
		GroupLayout windowLeftLayout = new GroupLayout(windowLeft);
		windowLeft.setLayout(windowLeftLayout); // one column, start with no rows. 
		windowLeftLayout.setAutoCreateContainerGaps(true);
		windowLeftLayout.setAutoCreateGaps(true);
		
		
		JLabel patientLabel = new JLabel
				("<html><font size=\"4\"><br><b>Create or Load New Patient File<br></b></font></html>");
		patientLabelPointer = patientLabel;// global pointer to use for dynamic updating of label upon every relevant action
		patientLabel.setVerticalAlignment(JLabel.TOP);
		
		JLabel chooseResultText = new JLabel("Choose Desired Reult to View:");
	
		JPanel patientDataPanel= new JPanel();
		GroupLayout dataPanelLayout = new GroupLayout(patientDataPanel);
		patientDataPanel.setLayout(dataPanelLayout);
		dataPanelLayout.setHonorsVisibility(true);
		dataPanelLayoutPointer = dataPanelLayout;// global pointer to use for dynamic updating of label upon every relevant action
		
		
		Component strut50 = Box.createVerticalStrut(50);

		resultChoiceList = new JComboBox<String>(ResultTypes.resultNames());
		resultChoiceList.setSelectedIndex(-1);
		resultChoiceList.setPreferredSize(new Dimension(4000, 25));
		resultChoiceList.setMaximumSize(resultChoiceList.getPreferredSize());
		resultChoiceList.setVisible(false); // invisible till we know there is valid data to work with
		
		noDataText = new JLabel("<html>No information to show. Please run test to collect data.<html>");
		noDataText.setVisible(false);
		
		collectDataButton = new JButton("Begin Test");
		collectDataButton.setVisible(false);
		collectDataButton.setHorizontalAlignment(JButton.CENTER);
		
		///////////
		
		windowLeftLayout.setHorizontalGroup(
				windowLeftLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
				.addComponent(patientLabel)
				.addComponent(strut50)
				.addComponent(patientDataPanel));
		
		windowLeftLayout.setVerticalGroup(
				windowLeftLayout.createSequentialGroup()
				.addComponent(patientLabel)
				.addComponent(strut50)
				.addComponent(patientDataPanel));
		
		//////////////
		
		currentHorizontalDataGroup = 
				dataPanelLayoutPointer.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(chooseResultText)
				.addComponent(strut50)
				.addComponent(resultChoiceList)
				.addComponent(noDataText)
				.addComponent(strut50)
				.addComponent(collectDataButton, GroupLayout.Alignment.CENTER);
		
		currentVerticalDataGroup = dataPanelLayoutPointer
				.createSequentialGroup()
				.addComponent(chooseResultText)
				.addComponent(strut50)
				.addComponent(resultChoiceList)
				.addComponent(noDataText)
				.addComponent(strut50)
				.addComponent(collectDataButton);
		
		dataPanelLayoutPointer.setHorizontalGroup(currentHorizontalDataGroup);
		dataPanelLayoutPointer.setVerticalGroup(currentVerticalDataGroup);
		
		//////////////

		
		splitPane.setLeftComponent(windowLeft);
		
	}
	
	protected static void refreshPatientLabel(){
		// using html for effects in text
		
		String title = "<font size=\"5\"><b>Patient Information:</b></font><br><br>";
		patientLabelPointer.setText("<html>" + title + 
				BrainFreezeMain.patients.get(BrainFreezeMain.currentPatientIndex).toString()
				+ "</html>");
		
	}
	
	protected static void refreshPatientData(){
		
		Measurements data = BrainFreezeMain.patients.get(BrainFreezeMain.currentPatientIndex).getData();
		
		if (data.isValid()){
			
			resultChoiceList.setVisible(true); // we have valid data, show options for representation 
			noDataText.setVisible(false);
			collectDataButton.setVisible(false);
			
			// Nothing chosen by definition, put up message
			RightPanel.swapRepresentation("ChooseSomethingMessage");
			
			resultChoiceList.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					@SuppressWarnings("unchecked")
					JComboBox<String> cb = (JComboBox<String>)e.getSource();
				    String chosenRepresentation = (String)cb.getSelectedItem();
				    RightPanel.swapRepresentation(chosenRepresentation);
				}
			});
			
		}
		else{
			
			resultChoiceList.setVisible(false); // we have valid data, show options for representation 
			noDataText.setVisible(true);
			collectDataButton.setVisible(true);
			
			RightPanel.swapRepresentation(""); // remove any graphs that may be there, this is a new patient with no valid data
	
			collectDataButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {

					TestWindow.launchTestWindow();
					
				}
			});
		}
		
		
	}

}
