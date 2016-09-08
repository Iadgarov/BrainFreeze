package windows_and_panels;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import utility.ResultTypes;

/**
 * Right side of the main screen
 * Contains the data representations, settings screen, patient data, and other messages. 
 * Should REALY use a cardLayout but I discovered that later on.. Works just the same.
 * @author David
 *
 */
public class RightPanel {
	
	private static JLabel windowRight;
	
	public final static String SETTINGS = "SETTINGS";
	public final static String EDIT_PATIENT_DATA = "EditPatientData";
	

	protected static void addRightPanel(JSplitPane splitPane){

		
		windowRight = new JLabel();
		windowRight.setLayout(new BorderLayout());
		windowRight.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		setNoDataMessage();
		
		splitPane.setRightComponent(windowRight);
		
		
		windowRight.revalidate();
		windowRight.repaint();

		
	}
	
	

	/**
	 * Simulating a cardLayout basically
	 * @param type = which screen should be seen now. 
	 */
	public static void swapRepresentation(String type){
		
		windowRight.removeAll();
		
		if (type == ResultTypes.ITD.toString()){
			
			new BubbleChart( "Histogram: ITD results", windowRight, BubbleChart.ITD ); 
			
		}
		else if (type == ResultTypes.LTD.toString()){
		
			new BubbleChart( "Histogram: LTD results", windowRight, BubbleChart.LTD ); 
			
		}
		else if(type == ResultTypes.PARAM_DETAIL.toString()){
	
			ThetaDataPanel.getThetaDataPanel(windowRight);
			
		}
		else if (type == ResultTypes.BOTH_HIST.toString()){
			
			JSplitPane temp = new JSplitPane();
			temp.setResizeWeight(.5d);
			
			JLabel left = new JLabel();
			left.setLayout(new BorderLayout());
			new BubbleChart( "Histogram: ITD results", left, BubbleChart.ITD );
			temp.setLeftComponent(left);
			
			JLabel right = new JLabel();
			right.setLayout(new BorderLayout());
			new BubbleChart( "Histogram: LTD results", right, BubbleChart.LTD );
			temp.setRightComponent(right);
		
			
			windowRight.add(temp, BorderLayout.CENTER);
		}
        else if (type == ResultTypes.DIAGNOSIS.toString()){
            
            Diagnosis.getDiagnosisPanel(windowRight);
        }
		else if (type == ResultTypes.PARAM_DETAIL.toString()){
			setNoDataMessage();
		}
		else if (type == SETTINGS){
			SettingsPanel.getSettingsPanel(windowRight);
		}
		else if (type == EDIT_PATIENT_DATA){
			PatientDataPanel.getPatientDataPanel(windowRight);
		}
		
		else if (type == ""){
			setNoDataMessage();
		}
		else{
			setChooseRepresentationMessage();
		}
		
		
		windowRight.revalidate();
		windowRight.repaint();
	}

	
	private static void setNoDataMessage(){

		JPanel holder = new JPanel();
		holder.setLayout(new GridBagLayout());
		holder.add(new HelpPanel());
		windowRight.add(holder, BorderLayout.CENTER);
		
		
	}
	
	private static void setChooseRepresentationMessage(){
		
		
		JLabel message = new JLabel
				("<html><font size=\"4\"><br><b>Please choose a data represantation method from the dropdown menu on the left<br></b></font></html>");

		windowRight.add(message, null);
		
	}
	

    
}
