package windows_and_panels;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import objects.ByFontSize_JLabel;

/**
 * Panel with welcome message and rudimentary instructions. Shows at program start and whenever the help button is pressed. 
 * @author David
 *
 */
public class HelpPanel extends JPanel{
	
	private static final long serialVersionUID = 8954349723568707005L;

	public HelpPanel() {
		
		String tabarrow = "    \u2756    ";
		
		ByFontSize_JLabel title = new ByFontSize_JLabel("BrainFreeze  - Welcome To The Laterization Test", ByFontSize_JLabel.Size.XLT);
		
		ByFontSize_JLabel headphones = new ByFontSize_JLabel(tabarrow + "Please connect a pair of headphones before proceeding.", ByFontSize_JLabel.Size.XL);
		
		ByFontSize_JLabel customSound = new ByFontSize_JLabel(tabarrow + "To use custom sound files see the setting screen, avilable via the top toolbar.", ByFontSize_JLabel.Size.XL);
		ByFontSize_JLabel customSound2 = new ByFontSize_JLabel("           See \"Audio Stimulants for Examination\".", ByFontSize_JLabel.Size.XL);
		
		
		ByFontSize_JLabel patient = new ByFontSize_JLabel(tabarrow + "To begin please create a new or load an existing Patient file.", ByFontSize_JLabel.Size.XL);
		
		ByFontSize_JLabel patientInfo = new ByFontSize_JLabel(tabarrow + "The current Patient information can be accessed to the left.", ByFontSize_JLabel.Size.XL);
		
		ByFontSize_JLabel settings = new ByFontSize_JLabel(tabarrow + "To edit delay between stimulants, sound files, data point count, etc. see the settings screen.", ByFontSize_JLabel.Size.XL);
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		//this.add(Box.createVerticalGlue());
		
		this.add(title);
		this.add(Box.createVerticalStrut(100));
		
		this.add(headphones);
		this.add(Box.createVerticalStrut(10));
		
		this.add(customSound);
		this.add(customSound2);
		this.add(Box.createVerticalStrut(10));
		
		this.add(patient);
		this.add(Box.createVerticalStrut(10));
		
		this.add(patientInfo);
		this.add(Box.createVerticalStrut(10));
		
		this.add(settings);
		this.add(Box.createVerticalStrut(10));
		
		this.add(Box.createVerticalStrut(100));
		
		this.setOpaque(false);
		
		//this.setBorder(BorderFactory.createEtchedBorder());
		
		
	}

}
