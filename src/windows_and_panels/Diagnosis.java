package windows_and_panels;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import defualt.BrainFreezeMain;
import objects.Measurements;
import objects.Patient;

/**
 * Panel that shows final diagnosis. Not in an way accurate but written as it is defined in the research. 
 * @author David
 *
 */
public class Diagnosis {
	
	public static void getDiagnosisPanel(JLabel windowRight){
		
		JPanel holder = new JPanel();
		holder.setLayout(new GridBagLayout());
		JPanel diagnosisPanel = new JPanel();
		//diagnosisPanel.setLayout(new MigLayout());
		diagnosisPanel.setLayout(new GridLayout(7,1));
		
		String timePerformace = getPerformace(Measurements.TIME);
		String levelPerformace = getPerformace(Measurements.LEVEL);
		
		JLabel timeLabel = new JLabel("TIME:        " + timePerformace);
		timeLabel.setFont(new Font("Serif", Font.PLAIN, 20));
		diagnosisPanel.add( timeLabel);
		
		diagnosisPanel.add(new JLabel());	diagnosisPanel.add(new JLabel());
		
		JLabel levelLabel = new JLabel("LEVEL:      " + levelPerformace);
		levelLabel.setFont(new Font("Serif", Font.PLAIN, 20));
		diagnosisPanel.add(levelLabel);
		
		diagnosisPanel.add(new JLabel());	diagnosisPanel.add(new JLabel());
		
		JLabel recomndation = new JLabel("It is recomended the professional make their own conclusion based on the given histograms.");
		recomndation.setFont(new Font("Serif", Font.PLAIN, 20));
		diagnosisPanel.add(recomndation);
		
		
//		GridBagConstraints c = new GridBagConstraints();
//		c.weightx = 1; c.weighty = 1;
//		c.fill = GridBagConstraints.BOTH; 
//		c.anchor = GridBagConstraints.NORTH;
//		panel.add(diagnosisPanel, c);
		
		holder.add(diagnosisPanel);
		windowRight.add(holder, BorderLayout.CENTER);
	}
	
	private static String getPerformace(int type){
		
		Patient p = BrainFreezeMain.patients.get(BrainFreezeMain.currentPatientIndex);
		
		if (p.getData().isCenterOriented(type))
			return "Center Oriented";
		if (p.getData().isSideOriented(type))
			return "Side Oriented";
		if (p.getData().isNormal(type))
			return "Normal Performace";
		
		return "Results Are Inconclusive."; // message for debugging. 
		
	}

}
