package objects;

import java.awt.Font;

import javax.swing.JLabel;


/**
 * A JLabel like any other with the option of settings the font upon construction. 
 * Fonts taken from a preset collection defined in this class. 
 * Used to easily change size of various texts in the program. 
 * @author David
 *
 */
public class ByFontSize_JLabel extends JLabel {

	private static final long serialVersionUID = 9056409678186831195L;

	public static enum Size {S, M , L, XL, T, LT, XLT};	// use enums because apparently consts are for loser.. 

	public ByFontSize_JLabel(ByFontSize_JLabel.Size s) {
		this("", s);

	}

	public ByFontSize_JLabel(String text, ByFontSize_JLabel.Size s){
		super(text);
		switch(s){

		case S: this.setFont(new Font("Serif", Font.PLAIN, 12)); break;
		case M: this.setFont(new Font("Serif", Font.PLAIN, 14)); break;
		case L: this.setFont(new Font("Serif", Font.PLAIN, 16)); break;
		case XL: this.setFont(new Font("Serif", Font.PLAIN, 18)); break;
		case T: this.setFont(new Font("Serif", Font.PLAIN, 24)); break;
		case LT: this.setFont(new Font("Serif", Font.PLAIN, 28)); break;
		case XLT: this.setFont(new Font("Serif", Font.PLAIN, 32)); break;

		}
	}

}
