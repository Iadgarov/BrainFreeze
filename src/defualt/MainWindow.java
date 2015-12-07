package defualt;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import objects.Measurements;
import objects.Patient;
import windows.NewPatientWindow;

import javax.swing.JSplitPane;

import javax.swing.JFileChooser;
import javax.swing.Box;
import javax.swing.JButton;

import javax.swing.JToolBar;

import javax.swing.JComboBox;

import javax.swing.GroupLayout;


import javax.swing.JLabel;


public class MainWindow extends JFrame {

	private JPanel contentPane;		// main content pain
	private JLabel patientLabelPointer;	// pointer to text represantion of patient on the left
	private GroupLayout dataPanelLayoutPointer; // pointer to data choosing panel under patient name to the left

	/**
	 * Launch the application.
	 */
	public void start(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 800);
		
		
		// add content pain to main window
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	
		addTopToolBar(contentPane);
		
		// split area below toolbar into two
		JSplitPane splitPane = new JSplitPane();
		contentPane.add(splitPane, BorderLayout.CENTER);
		
		// fill in the left area
		addLeftPanel(splitPane);

		
		JPanel windowRight = new JPanel();
		
		
		
		
	
	}
	
	private void addLeftPanel(JSplitPane splitPane){
		
		JPanel windowLeft = new JPanel();
		GroupLayout windowLeftLayout = new GroupLayout(windowLeft);
		windowLeft.setLayout(windowLeftLayout); // one column, start with no rows. 
		windowLeftLayout.setAutoCreateContainerGaps(true);
		windowLeftLayout.setAutoCreateGaps(true);
		
		
		JLabel patientLabel = new JLabel
				("<html><font size=\"4\"><br><b>Create or Load New Patient File<br></b></font></html>");
		patientLabelPointer = patientLabel;// global pointer to use for dynamic updating of label upon every relevant action
		patientLabel.setVerticalAlignment(JLabel.TOP);
		
		
	
		JPanel patientDataPanel= new JPanel();
		GroupLayout dataPanelLayout = new GroupLayout(patientDataPanel);
		patientDataPanel.setLayout(dataPanelLayout);
		dataPanelLayoutPointer = dataPanelLayout;// global pointer to use for dynamic updating of label upon every relevant action
		
		Component strut = Box.createVerticalStrut(50);
	
		windowLeftLayout.setHorizontalGroup(
				windowLeftLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
				.addComponent(patientLabel)
				.addComponent(strut)
				.addComponent(patientDataPanel));
		
		windowLeftLayout.setVerticalGroup(
				windowLeftLayout.createSequentialGroup()
				.addComponent(patientLabel)
				.addComponent(strut)
				.addComponent(patientDataPanel));
		

		
		splitPane.setLeftComponent(windowLeft);
		
	}
	
	private void refreshPatientLabel(){
		// using html for effects in text
		
		String title = "<font size=\"5\"><b>Patient Information:</b></font><br><br>";
		patientLabelPointer.setText("<html>" + title + 
				BrainFreezeMain.patients.get(BrainFreezeMain.currentPatientIndex).toString()
				+ "</html>");
		
	}
	
	private void refreshPatientData(){
		
		Measurements data = BrainFreezeMain.patients.get(BrainFreezeMain.currentPatientIndex).getData();
		
		JLabel chooseResultText = new JLabel("Choose Desired Reult to View:");
		
		Component strut = Box.createVerticalStrut(10);
		
		if (data.isValid()){

			JComboBox<String> resultChoiceList = new JComboBox<String>(BrainFreezeMain.RESULT_TYPES);
			
			resultChoiceList.setPreferredSize(new Dimension(4000, 25));
			resultChoiceList.setMaximumSize(resultChoiceList.getPreferredSize());
			
			dataPanelLayoutPointer.setHorizontalGroup(dataPanelLayoutPointer
					.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(chooseResultText)
					.addComponent(strut)
					.addComponent(resultChoiceList));

			dataPanelLayoutPointer.setVerticalGroup(dataPanelLayoutPointer
					.createSequentialGroup()
					.addComponent(chooseResultText)
					.addComponent(strut)
					.addComponent(resultChoiceList));
			
		}
		else{
			
			JLabel noDataText = new JLabel("No Data to Show");
			
			dataPanelLayoutPointer.setHorizontalGroup(dataPanelLayoutPointer
					.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(chooseResultText)
					.addComponent(strut)
					.addComponent(noDataText));

			dataPanelLayoutPointer.setVerticalGroup(dataPanelLayoutPointer
					.createSequentialGroup()
					.addComponent(chooseResultText)
					.addComponent(strut)
					.addComponent(noDataText));
		}
		
		
	}
	
	/**
	 * add tool bar to top of content pane
	 * @param contentPane
	 */
	private void addTopToolBar(JPanel contentPane){
		
		// set top toolbar
		JToolBar toolBar = new JToolBar();
		contentPane.add(toolBar, BorderLayout.NORTH);
		toolBar.setFloatable(false);
		
		addNewPatientToolBarButton(toolBar); // new patient button on toolbar at top
		addLoadPatientToolBarButton(toolBar); // load patient button on toolbar at top
	}
	
	/**
	 * adds new patient button to toolbar at screen top
	 * upon click data may be entered to create a new patient object
	 * @param toolBar
	 */
	private void addNewPatientToolBarButton(JToolBar toolBar){
		// create buttons to go inside toolbar
				JButton newPatient = new JButton();
				toolBar.add(newPatient);
				newPatient.setText("New Patient");
				newPatient.setToolTipText("Create New Patient File");
				
				
				newPatient.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// chosen to add new patient
						
						ArrayList<String> constructorInput = null;
						constructorInput = NewPatientWindow.showInputdialog();
						
						// if cancel or close chosen on new patient window:
						if (constructorInput == null)
							return; 
						
						//else:
						BrainFreezeMain.patients.add(new Patient(
								(constructorInput.toArray( new String[constructorInput.size()]))));
						BrainFreezeMain.currentPatientIndex = BrainFreezeMain.patients.size()-1;
						
						
						refreshPatientLabel(); // change to patient list, update label
						refreshPatientData();  // change to patient, update data
						
					}
				});
	}
	
	
	private void addLoadPatientToolBarButton(JToolBar toolBar){
		// create buttons to go inside toolbar
				JButton newPatient = new JButton();
				toolBar.add(newPatient);
				newPatient.setText("Load Patient");
				newPatient.setToolTipText("Load Existing Patient File");
				
				
				newPatient.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						JFileChooser fileOpener = new JFileChooser();
		                int whatDo = fileOpener.showOpenDialog(toolBar); // did the user choose to load anything?
		                if (whatDo == JFileChooser.APPROVE_OPTION){
		                	File patientFile = fileOpener.getSelectedFile();
		                }
						
					}
				});
	}

}
