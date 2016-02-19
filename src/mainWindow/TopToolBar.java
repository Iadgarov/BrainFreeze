package mainWindow;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileNameExtensionFilter;

import defualt.BrainFreezeMain;
import objects.Patient;
import windows.NewPatientWindow;

public class TopToolBar {

	/**
	 * add tool bar to top of content pane
	 * @param contentPane
	 */
	protected static void addTopToolBar(JPanel contentPane){
		
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
	protected static void addNewPatientToolBarButton(JToolBar toolBar){
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
				
				
				LeftPanel.refreshPatientLabel(); // change to patient list, update label
				LeftPanel.refreshPatientData();  // change to patient, update data
					
				}
			});
	}
	
	
	protected static void addLoadPatientToolBarButton(JToolBar toolBar){
		// create buttons to go inside toolbar
		JButton newPatient = new JButton();
		toolBar.add(newPatient);
		newPatient.setText("Load Patient");
		newPatient.setToolTipText("Load Existing Patient File");
		
		
		newPatient.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser fileOpener = new JFileChooser();
				FileNameExtensionFilter patientFileFilter = new FileNameExtensionFilter(
						"Patient file type (*.patient)", "patient");
				fileOpener.setDialogTitle("Open existing patient file");
				fileOpener.setFileFilter(patientFileFilter);
				
                int whatDo = fileOpener.showOpenDialog(toolBar); // did the user choose to load anything?
                if (whatDo == JFileChooser.APPROVE_OPTION){
                	
                	ArrayList<String> constructorInput = new ArrayList<>(); // input for new patient creation
                	
                	// open existing patient file
                	File patientFile = fileOpener.getSelectedFile();
                	List<String> fileLines = null;
                	try{
                		fileLines = Files.readAllLines(patientFile.toPath(), StandardCharsets.UTF_8);
                		
                	}
                	catch(IOException ex){
                		ex.printStackTrace();
                	}
                	
                	// covert file to input list
                	for (String str : fileLines){
                		constructorInput.addAll(Arrays.asList(str.split("\\,")));
                	}
                	
                	
                	BrainFreezeMain.patients.add(new Patient(
    						(constructorInput.toArray( new String[constructorInput.size()]))));
    				BrainFreezeMain.currentPatientIndex = BrainFreezeMain.patients.size()-1;
    				BrainFreezeMain.patients.get(BrainFreezeMain.currentPatientIndex).setData(new ArrayList<String>(
    						constructorInput.subList(Patient.INFO_AMOUNT, constructorInput.size())));
    				
    				
    				LeftPanel.refreshPatientLabel(); // change to patient list, update label
    				LeftPanel.refreshPatientData();  // change to patient, update data
    				
    				
                }
				
			}
		});
	}

}
