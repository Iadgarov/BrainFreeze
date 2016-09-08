package buttons;

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
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import defualt.BrainFreezeMain;
import objects.Patient;
import utility.FilterChooser;
import windows_and_panels.LeftPanel;

/**
 * Button for the top toolbar, open up filechooser and loads a .patient file
 * @author David
 *
 */
public class LoadPatientButton extends JButton{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8423108672545065561L;
	
	public LoadPatientButton(){
		super(UIManager.getIcon("FileView.directoryIcon"));
		super.setText("Load Patient");
		super.setToolTipText("Load Existing Patient File");
		super.setFocusPainted(false);
		super.setFocusable(false);

	}
	
	/**
	 * opens a filechooser on click
	 * @param toolBar = the bar on which this button goes
	 */
	public void setListeners(JToolBar toolBar){
		

		this.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//JFileChooser fileOpener = new JFileChooser();
				FilterChooser fileOpener = new FilterChooser();
				FileNameExtensionFilter patientFileFilter = new FileNameExtensionFilter(
						"Patient file type (*.patient)", "patient");
				fileOpener.chooser.setDialogTitle("Open existing patient file");
				fileOpener.chooser.setFileFilter(patientFileFilter);		// filter to only take .patient files
				
				
                int whatDo = fileOpener.chooser.showOpenDialog(toolBar); // did the user choose to load anything?
                
                if (whatDo == JFileChooser.APPROVE_OPTION){
                	
                	// new patient stuff:
                	ArrayList<String> constructorInput = new ArrayList<>(); // input for new patient creation
                	
                	// open existing patient file
                	File patientFile = fileOpener.chooser.getSelectedFile();
                	List<String> fileLines = null;
                	try{
                		fileLines = Files.readAllLines(patientFile.toPath(), StandardCharsets.UTF_8);
                		
                	}
                	catch(IOException ex){
                		
                		JOptionPane.showMessageDialog(fileOpener.chooser,
                				"Illegal file chosen.\n" 
                		+ "Please make sure the file exists and is a \"*.patient\" type file.",
                			    "File Load Error",
                			    JOptionPane.ERROR_MESSAGE);
                		
                		
                		return;
                		//ex.printStackTrace();
                	}
                	
                	// covert file to input list
                	for (String str : fileLines){
                		constructorInput.addAll(Arrays.asList(str.split(",")));
                	}
                	
                	Patient temp = new Patient(constructorInput.toArray( new String[constructorInput.size()]));
                	
    				
    				boolean b = temp.setData(new ArrayList<String>(constructorInput.subList(Patient.INFO_AMOUNT, Patient.INFO_AMOUNT + 2)));
    				
    				if (!b){
    					// bad file!
                		JOptionPane.showMessageDialog(fileOpener.chooser,
                				"Bad Input File.\n" 
                		+ "Not a valid patient file.",
                			    "File Load Error",
                			    JOptionPane.ERROR_MESSAGE);
                		
                		
                		return;
    				}
    				
    				BrainFreezeMain.patients.add(temp);
    				BrainFreezeMain.currentPatientIndex = BrainFreezeMain.patients.size()-1;
    				
    				LeftPanel.refreshPatientLabel(); // change to patient list, update label
    				LeftPanel.refreshPatientData();  // change to patient, update data
    				
    				
                }
				
			}
		});
	}

}
