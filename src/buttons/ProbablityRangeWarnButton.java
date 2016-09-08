package buttons;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.UIManager;

public class ProbablityRangeWarnButton extends JButton{
	
	public ProbablityRangeWarnButton(){
		//super(UIManager.getIcon("OptionPane.warningIcon"));
		super.setText("Value out of range");
		super.setToolTipText("Out of range!");
		
		//super.setFocusPainted(false);
		//super.setFocusable(false);
		//super.setBorder(BorderFactory.createEmptyBorder());
		//super.setContentAreaFilled(false);

	}

}
