package mainWindow;



import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import utility.ResultTypes;

public class RightPanel {
	
	private static JPanel windowRight;
	

	protected static void addRightPanel(JSplitPane splitPane){
		
		
		windowRight = new JPanel();
		windowRight.setLayout(new GridBagLayout());
	
		setNoDataMessage();
		
		splitPane.setRightComponent(windowRight);
		windowRight.revalidate();
		
	}
	
	protected static void swapRepresentation(String type){
		
		windowRight.removeAll();
		
		if (type == ResultTypes.ITD.toString()){
			
			new BubbleChart( "Histogram: ITD results", windowRight, BubbleChart.ITD ); 
			
		}
		else if (type == ResultTypes.LTD.toString()){
		
			new BubbleChart( "Histogram: ITD results", windowRight, BubbleChart.LTD ); 
			
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
		
		JLabel noDataMessage = new JLabel
				("<html><font size=\"4\"><br><b>No Data to Show. Load Someting.<br></b></font></html>");

		windowRight.add(noDataMessage, null);
	}
	
	private static void setChooseRepresentationMessage(){
		
		JLabel message = new JLabel
				("<html><font size=\"4\"><br><b>Please Choose Data Represantation Method<br></b></font></html>");

		windowRight.add(message, null);
	}
	
	
    
}
