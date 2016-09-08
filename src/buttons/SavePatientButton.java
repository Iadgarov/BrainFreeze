package buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.io.FilenameUtils;

import defualt.BrainFreezeMain;
import objects.Patient;
import utility.FilterChooser;

/**
 * Button for top toolbar, opens filechooser for save file location/name selection 
 * @author David
 *
 */
public class SavePatientButton extends JButton{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6153005405705490401L;
	
	public SavePatientButton(){
		super(UIManager.getIcon("FileView.floppyDriveIcon"));
		super.setText("Save Data To File");
		super.setToolTipText("Save patient information and data to file");
		super.setFocusPainted(false);
		super.setFocusable(false);

	}
	
	/**
	 * Open filechooser on click
	 * @param toolBar
	 */
	public void setListeners(JToolBar toolBar){
		
		this.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//JFileChooser fileOpener = new JFileChooser();
				FilterChooser fileOpener = new FilterChooser();
				FileNameExtensionFilter patientFileFilter = new FileNameExtensionFilter(
						"Patient file type (*.patient)", "patient");
				fileOpener.chooser.setDialogTitle("Save patient data to file");
				fileOpener.chooser.setFileFilter(patientFileFilter);	// only show .patient files in filechooser
				
				
                int whatDo = fileOpener.chooser.showSaveDialog(toolBar); // did the user choose to load anything?
                
                if (whatDo == JFileChooser.APPROVE_OPTION){
                	
                	Patient saveMe = BrainFreezeMain.patients.get(BrainFreezeMain.currentPatientIndex);
                	
                	File fileName = fileOpener.chooser.getSelectedFile();
                	// make shure filename ends with .patient
                	if (FilenameUtils.getExtension(fileName.getName()).equalsIgnoreCase("xml")) {
                	    // filename is OK as-is
                	} else {
                	    fileName = new File(fileName.getParentFile(), FilenameUtils.getBaseName(fileName.getName())
                	    		+ ".patient");
                	}
                	
                	try(FileWriter fw = new FileWriter(fileName)) {
                	    fw.write(saveMe.patientToFileFormat().toString());
                	} catch (IOException e1) {

                		JOptionPane.showMessageDialog(fileOpener.chooser,
                				"Illegal file name chosen.\n" ,
                			    "File Save Error",
                			    JOptionPane.ERROR_MESSAGE);
                		
                		
                		return;
					}
    				
    				
                }
				
			}
		});
	}

}
