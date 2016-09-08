package windows_and_panels;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import buttons.ProbablityRangeWarnButton;
import defualt.BrainFreezeMain;
import net.miginfocom.swing.MigLayout;
import objects.ByFontSize_JLabel;
import objects.Measurements;
import objects.Patient;
import utility.BetterLabel;

/**
 * Shows data that means little. As defined in the research. 
 * @author David
 *
 */
public class ThetaDataPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2400555721434557831L;

	private static JPanel thetaPanel;
	
	public static void getThetaDataPanel(JLabel windowRight){
		
		DecimalFormat f = new DecimalFormat("#.##");
		
		Patient p = BrainFreezeMain.patients.get(BrainFreezeMain.currentPatientIndex);
		
		String s1 = f.format(p.getData().
				getThetaArray()[Measurements.LEFT + Measurements.TIME].getSum());
		String s2 = f.format(p.getData().
				getThetaArray()[Measurements.CENTER + Measurements.TIME].getSum());;
		String s3 = f.format(p.getData().
				getThetaArray()[Measurements.RIGHT + Measurements.TIME].getSum());
		
		String s4 = f.format(p.getData().
				getThetaArray()[Measurements.LEFT + Measurements.LEVEL].getSum());
		String s5 = f.format(p.getData().
				getThetaArray()[Measurements.CENTER + Measurements.LEVEL].getSum());
		String s6 = f.format(p.getData().
				getThetaArray()[Measurements.RIGHT + Measurements.LEVEL].getSum());
		
		
	
		
		thetaPanel = new JPanel();
		thetaPanel.setLayout(new MigLayout("", "[]30[]"));
		
		// Labels
		BetterLabel title = new BetterLabel("Laterazation Index:", BetterLabel.MAIN_TITLE, false);
		JLabel timeType = new BetterLabel("Interaural Time Delay:", BetterLabel.TITLE, false);
		JLabel levelType = new BetterLabel("Interaural Level Difference:", BetterLabel.TITLE, false);
		JLabel lineInfo = new BetterLabel("Resulting regression lines:", BetterLabel.TITLE, false);
		
		JSeparator j = new JSeparator(SwingConstants.VERTICAL);	// separator

		//Buttons
		JButton timeInfoButton = new JButton();		infoButtonSetUp(timeInfoButton, "TIME");
		JButton levelInfoButton = new JButton();	infoButtonSetUp(levelInfoButton, "LEVEL");
		JButton lineInfoButton = new JButton();		infoButtonSetUp(lineInfoButton, "LINE");
	
		thetaPanel.add(title, "wrap 15px");
		
		thetaPanel.add(new JSeparator(SwingConstants.HORIZONTAL), "growx, spanx, wrap 15px");
		
		
		thetaPanel.add(timeType, "split 2");
		thetaPanel.add(timeInfoButton);
		thetaPanel.add(j, "center, growy, spany 7");
		thetaPanel.add(levelType, "split");
		thetaPanel.add(levelInfoButton, "wrap 20px");
		
		thetaPanel.add(new JSeparator(SwingConstants.HORIZONTAL), "growx, spanx, wrap 30px");
		
		thetaPanel.add(new ByFontSize_JLabel("Left = " + s1, ByFontSize_JLabel.Size.L), "center");	
		thetaPanel.add(new ByFontSize_JLabel("Left = " + s4, ByFontSize_JLabel.Size.L), "center, wrap 5px");
		
		thetaPanel.add(new ByFontSize_JLabel("Center = " + s2, ByFontSize_JLabel.Size.L), "center");
		thetaPanel.add(new ByFontSize_JLabel("Center = " + s5, ByFontSize_JLabel.Size.L), "center, wrap 5px");
		
		thetaPanel.add(new ByFontSize_JLabel("Right = " + s3, ByFontSize_JLabel.Size.L), "center");
		thetaPanel.add(new ByFontSize_JLabel("Right = " + s6, ByFontSize_JLabel.Size.L), "center, wrap 5px");
		
		thetaPanel.add(new JSeparator(SwingConstants.HORIZONTAL), "growx, spanx, wrap 15px");
		
		if (check(s1) || check(s2) || check(s3)){
			thetaPanel.add(probRangeWarn1, "center");
		}
		else{
			thetaPanel.add(new JLabel());
		}
		if (check(s4) || check(s5) || check(s6)){
			thetaPanel.add(probRangeWarn2, "center, wrap 15px");
		}
		else{
			thetaPanel.add(new JLabel(), "center, wrap 15px");
		}
		thetaPanel.add(new JSeparator(SwingConstants.HORIZONTAL), "growx, spanx, wrap 15px");	
		
		/////////////////////

		thetaPanel.add(lineInfo,"split 2");
		thetaPanel.add(lineInfoButton, "wrap 10px");
		
		thetaPanel.add(new JSeparator(SwingConstants.HORIZONTAL), "growx, spanx, wrap 15px");
		
		
		
		String eq1 = "POS(ITD) = " + f.format(p.getData().getITD_Line().getSlope()) + "*ITD + " 
				+ f.format(p.getData().getITD_Line().getIntercept());
		String eq2 = "POS(LTD) = " + f.format(p.getData().getLTD_Line().getSlope()) 
				+ "*LTD + " + f.format(p.getData().getLTD_Line().getIntercept());
		
		
		thetaPanel.add(new ByFontSize_JLabel(eq1, ByFontSize_JLabel.Size.L), "center");
		thetaPanel.add(new JSeparator(SwingConstants.VERTICAL), "center, growy, spany");
		thetaPanel.add(new ByFontSize_JLabel(eq2, ByFontSize_JLabel.Size.L), "center, wrap 5px");
		
		// slope out of range?
		if (!p.getData().isSlopeInRange(Measurements.TIME)){
			
			JLabel slopeNote = new ByFontSize_JLabel("<html><font color = 'red'>NOTE:</font><br>"
					+ "Slope is out of recommended range!<br>"
					+ "Expected range:<br>"
		    		+ Measurements.SLOPE_LIMITS[0] 
		    		+ " &lt A &lt "
		    		+ Measurements.SLOPE_LIMITS[1]
		    		+ "</html>", ByFontSize_JLabel.Size.M);
			
			thetaPanel.add(slopeNote);	
		}
		else
			thetaPanel.add(new JLabel());
		if (!p.getData().isSlopeInRange(Measurements.LEVEL)){
			
			JLabel slopeNote = new ByFontSize_JLabel("<html><font color = 'red'>NOTE:</font><br>"
					+ "Slope is out of recommended range!<br>"
					+ "Expected range:<br>"
		    		+ Measurements.SLOPE_LIMITS[0] 
		    		+ " &lt A &lt "
		    		+ Measurements.SLOPE_LIMITS[1]
		    		+ "</html>", ByFontSize_JLabel.Size.M);
			
			thetaPanel.add(slopeNote, "wrap 10px");	
		}
		else
			thetaPanel.add(new JLabel(), "wrap 10px");
		
		// Intercept out of range?
		if (!p.getData().isInterceptInRange(Measurements.TIME)){
			
			JLabel slopeNote = new ByFontSize_JLabel("<html><font color = 'red'>NOTE:</font><br>"
					+ "Slope is out of recommended range!<br>"
					+ "Expected range:<br>"
		    		+ "B &lt "
		    		+ Measurements.INTERCEPT_LIMIT
		    		+ "</html>", ByFontSize_JLabel.Size.M);
			
			thetaPanel.add(slopeNote);	
		}
		if (!p.getData().isInterceptInRange(Measurements.LEVEL)){
			
			JLabel slopeNote = new ByFontSize_JLabel("<html><font color = 'red'>NOTE:</font><br>"
					+ "Slope is out of recommended range!<br>"
					+ "Expected range:<br>"
		    		+ "B &lt "
		    		+ Measurements.INTERCEPT_LIMIT
		    		+ "</html>", ByFontSize_JLabel.Size.M);
			
			thetaPanel.add(slopeNote, "wrap 10px");	
		}
		else
			thetaPanel.add(new JLabel(), "wrap 10px");
		
		thetaPanel.add(new JSeparator(SwingConstants.HORIZONTAL), "growx, spanx, wrap 15px");

		
		/////////
		
		
		
		windowRight.add(thetaPanel, BorderLayout.CENTER);
	}
	
	private static void infoButtonSetUp(JButton button, String type){
		
		ImageIcon icon = new ImageIcon(BrainFreezeMain.
				class.getResource("/toolbarButtonGraphics/general/Information16.gif"));
		button.setIcon(icon);
		
		button.setBorder(BorderFactory.createEmptyBorder());
		button.setContentAreaFilled(false);
		
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			
				if (type == "TIME")
					JOptionPane.showMessageDialog(thetaPanel,
						    "<html><pre>"
						    + "The probabilty of the patient choosing the 3 leftmost/center/rightmost<br>"
						    + "positions when presented with time delayed dichotic stimulation."
						    + "</pre></html>", "Help Message", JOptionPane.INFORMATION_MESSAGE);
				else if (type == "LEVEL")
					JOptionPane.showMessageDialog(thetaPanel,
						    "<html><pre>"
				    		+ "The probabilty of the patient choosing the 3 leftmost/center/rightmost<br>"
						    + "positions when presented with unequal sound level dichotic stimulation."
						    + "</pre></html>", "Help Message", JOptionPane.INFORMATION_MESSAGE);
				else if (type == "LINE")
					JOptionPane.showMessageDialog(thetaPanel,
						    "<html><pre>"
				    		+ "The regression line based on data collected from subject.<br>"
				    		+ "Line is of the form y = Ax + B, where the expected ranges are:<br>"
				    		+ Measurements.SLOPE_LIMITS[0] 
				    		+ " &lt A &lt "
				    		+ Measurements.SLOPE_LIMITS[1]
				    		+ " &  B &lt "
				    		+ Measurements.INTERCEPT_LIMIT
				    		+ " If A &asymp 0  &  b &asymp 0.5 then subject may have<br>"
				    		+ "randomly chosen answers."
						    + "</pre></html>", "Help Message", JOptionPane.INFORMATION_MESSAGE);
					
			}
				
		});
		
	}
	
	private static boolean check(String p){
		if (Math.abs(0.333 - Double.parseDouble(p)) > 0.1){
			return true;
		}
		return false;
	}
	
	// yes I know, horrible practice. deal with it
	private static JLabel probRangeWarn1 = new ByFontSize_JLabel("<html><font color = 'red'>NOTE:</font><br>"
			+ "Above values are out of recommended range!<br>"
			+ "Expected range for every value:<br>"
    		+ Double.toString(0.23) // 0.33 - 0.1
    		+ " &lt Value &lt "
    		+ Double.toString(0.43) // 0.33 + 0.1
    		+ "</html>", ByFontSize_JLabel.Size.M);
	
	private static JLabel probRangeWarn2 = new ByFontSize_JLabel("<html><font color = 'red'>NOTE:</font><br>"
			+ "Above values are out of recommended range!<br>"
			+ "Expected range for every value:<br>"
    		+ Double.toString(0.23) 
    		+ " &lt Value &lt "
    		+ Double.toString(0.43) 
    		+ "</html>", ByFontSize_JLabel.Size.M);
	
	

}
