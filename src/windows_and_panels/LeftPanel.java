package windows_and_panels;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import defualt.BrainFreezeMain;
import objects.ByFontSize_JLabel;
import objects.Measurements;
import utility.ResultTypes;

/**
 * The left hand side of the main program screen. Contains patient info, choise for result depiction, option to view and edit patent info, start or retake the exam
 * @author David
 *
 */
public class LeftPanel {

	private static JLabel patientLabelPointer;	// pointer to text representation of patient on the left
	private static GroupLayout dataPanelLayoutPointer; // pointer to data choosing panel under patient name to the left

	private static GroupLayout.Group currentHorizontalDataGroup; // the current horizontal group in the layout for lower half of the left pane
	private static GroupLayout.Group currentVerticalDataGroup; // the current vertical group in the layout for the lower half of the left pane

	// not always visible components, need a pointer to them
	private static JComboBox<String> resultChoiceList; // drop down list
	private static JLabel noDataText; // a label to indicate that no data is available for this patient
	private static JButton collectDataButton;
	private static JButton editPatientInfoButton;


	/**
	 * Creates the panel and adds it to the main window (splitpane) on te left
	 * @param splitPane = main windows split into two sides, left and right. 
	 */
	protected static void addLeftPanel(JSplitPane splitPane){

		JPanel windowLeft = new JPanel();
		//windowLeft.setBorder(BorderFactory.createLineBorder(Color.black));
		GroupLayout windowLeftLayout = new GroupLayout(windowLeft);
		windowLeft.setLayout(windowLeftLayout); // one column, start with no rows. 
		windowLeftLayout.setAutoCreateContainerGaps(true);
		windowLeftLayout.setAutoCreateGaps(true);


		JLabel patientLabel = new ByFontSize_JLabel
				("<html><br><b>Create or Load a New Patient File<br></b></html>", ByFontSize_JLabel.Size.L);
		patientLabelPointer = patientLabel;// global pointer to use for dynamic updating of label upon every relevant action
		patientLabel.setVerticalAlignment(JLabel.TOP);

		JLabel chooseResultText = new ByFontSize_JLabel("Choose Desired Reult to View:", ByFontSize_JLabel.Size.L);

		JPanel patientDataPanel= new JPanel();
		GroupLayout dataPanelLayout = new GroupLayout(patientDataPanel);	// foolishly using grouplayot when starting out. way more complicated than it needs to be 
		patientDataPanel.setLayout(dataPanelLayout);
		dataPanelLayout.setHonorsVisibility(true);
		dataPanelLayoutPointer = dataPanelLayout;// global pointer to use for dynamic updating of label upon every relevant action


		Component strut50 = Box.createVerticalStrut(50);

		resultChoiceList = new JComboBox<String>(ResultTypes.resultNames());
		resultChoiceList.setSelectedIndex(-1);
		resultChoiceList.setPreferredSize(new Dimension(4000, 25));
		resultChoiceList.setMaximumSize(resultChoiceList.getPreferredSize());
		resultChoiceList.setVisible(false); // invisible till we know there is valid data to work with

		noDataText = new ByFontSize_JLabel("<html>No information to show. Please run test to collect data.<html>", ByFontSize_JLabel.Size.L);
		noDataText.setVisible(false);

		collectDataButton = new JButton("Begin Test");
		collectDataButton.setVisible(false);
		collectDataButton.setHorizontalAlignment(JButton.CENTER);
		collectDataButton.setToolTipText("Click here to begin sound lateralization test");		
		collectDataButton.setFont(new Font("Serif", Font.PLAIN, 18));
		
		editPatientInfoButton = new JButton("View Patient Information");
		editPatientInfoButton.setVisible(false);
		editPatientInfoButton.setHorizontalAlignment(JButton.CENTER);
		editPatientInfoButton.setToolTipText("Click to edit patient info and add notes");
		editPatientInfoButton.setFont(new Font("Serif", Font.PLAIN, 18));

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
				.addComponent(collectDataButton, GroupLayout.Alignment.CENTER)
				.addComponent(strut50)
				.addComponent(editPatientInfoButton, GroupLayout.Alignment.CENTER);

		currentVerticalDataGroup = dataPanelLayoutPointer
				.createSequentialGroup()
				.addComponent(chooseResultText)
				.addComponent(strut50)
				.addComponent(resultChoiceList)
				.addComponent(noDataText)
				.addComponent(strut50)
				.addComponent(collectDataButton)
				.addComponent(strut50)
				.addComponent(editPatientInfoButton);

		dataPanelLayoutPointer.setHorizontalGroup(currentHorizontalDataGroup);
		dataPanelLayoutPointer.setVerticalGroup(currentVerticalDataGroup);

		//////////////


		splitPane.setLeftComponent(windowLeft);

	}

	public static void refreshPatientLabel(){
		// using html for effects in text

		String title = "<b>Patient Information:</b><br><br>";
		patientLabelPointer.setText("<html>" + title + 
				BrainFreezeMain.patients.get(BrainFreezeMain.currentPatientIndex).toString()
				+ "</html>");
		//patientLabelPointer.setFont(new Font("Serif", Font.PLAIN, 14));
		
		//RightPanel.swapRepresentation("~~");	// sets right panel to default form


	}

	public static void refreshPatientData(){

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


			collectDataButton.setVisible(true);
			collectDataButton.setText("Retake Exam");

			RightPanel.swapRepresentation("~~"); // remove any graphs that may be there, this is a new patient with no valid data
			initComboBox(0);


			collectDataButton.removeActionListener(startExamListener);
			collectDataButton.addActionListener(retakeListener);

		}
		else{
			


			resultChoiceList.setVisible(false); // we have no valid data
			noDataText.setVisible(true);
			collectDataButton.setVisible(true);
			collectDataButton.setText("Begin Examination");

			RightPanel.swapRepresentation("~~"); // remove any graphs that may be there, this is a new patient with no valid data

			collectDataButton.removeActionListener(retakeListener);
			collectDataButton.addActionListener(startExamListener);
		}
		
		editPatientInfoButton.addActionListener(editPatientInfoListener);
		editPatientInfoButton.setVisible(true);


	}

	/**
	 * Listener for test retake button (starting test when valid data already exists. 
	 */
	static ActionListener retakeListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if (TestWindow.testRunning)
				return;
			
			int temp = JOptionPane.showConfirmDialog(null,
					"Retaking the test will ovveride existing data. Are you sure you wish to continue?",
					"Confirm Selection",
					JOptionPane.YES_NO_OPTION);

			if (temp == JOptionPane.NO_OPTION){
				return;
			}

			BrainFreezeMain.patients.get(BrainFreezeMain.currentPatientIndex).eraseData();
			try{
				TestWindow tw = new TestWindow();
				tw.launchTestWindow();	
			}
			catch(Exception ex){};

		}
	};

	/**
	 * Listener for firing up the test for the first time
	 */
	static ActionListener startExamListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if (TestWindow.testRunning)
				return;
			
			try{
				TestWindow tw = new TestWindow();
				tw.launchTestWindow();	
			}
			catch(Exception ex){};
		}
	};
	
	/**
	 * Listener for view and edit patient info button
	 */
	static ActionListener editPatientInfoListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			RightPanel.swapRepresentation(RightPanel.EDIT_PATIENT_DATA);
			
		}
	};
	
	
	///
	
	public static void initComboBox(int i){
		resultChoiceList.setSelectedIndex(i);
		String chosenRepresentation = (String)resultChoiceList.getSelectedItem();
		System.out.println(chosenRepresentation);
		RightPanel.swapRepresentation(chosenRepresentation);
	}

}
