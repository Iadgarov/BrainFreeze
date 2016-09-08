package utility;

import java.awt.Font;

import javax.swing.JLabel;

/**
 * a JLabel with built in font sizes. A duplicate class. 
 * Results of months of downtime between work
 * It works. Can be cleaned up someday if you really want to. 
 * 
 * ALSO allows for wrapping the text in the label in <html> </html> tags. 
 * @author David
 *
 */
public class BetterLabel extends JLabel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3303848819375755823L;
	
	public static final int MAIN_TITLE = 0;
	public static final int TITLE = 1;
	public static final int SUB_TITLE = 2;
	
	public BetterLabel(String s, int type, boolean html){
		
		super("<html>" + s + "</html>");
		if (!html)
			this.setText(s);	// remove html formatting
		switch (type){
		
		case MAIN_TITLE: super.setFont(new Font(this.getFont().getName(), Font.BOLD, 25)); break;
		
		case TITLE: super.setFont(new Font(this.getFont().getName(), Font.BOLD, 20)); break;
		
		case SUB_TITLE: super.setFont(new Font(this.getFont().getName(), Font.PLAIN, 14)); break;
			
		
		}
		
	}
	
	public BetterLabel(String s){
		
		super("<html>" + s + "</html>");
	
	}
	
	

}
