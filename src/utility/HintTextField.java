package utility;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JFormattedTextField;
import javax.swing.text.NumberFormatter;

/**
 * Text field with text written in it when no user input is present, a 'hint'.
 * @author David
 *
 */
public class HintTextField extends JFormattedTextField implements FocusListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2528460883480690491L;
	
	private final String hint;
	private boolean showingHint;

	public HintTextField(final String hint, NumberFormatter fo) {
		super(fo);
		this.setText(hint);
		this.hint = hint;
		this.showingHint = true;
		super.addFocusListener(this);
	}


	@Override
	public void focusGained(FocusEvent e) {
		if(this.getText().isEmpty()) {
			super.setText("");
			showingHint = false;
		}
	}
	@Override
	public void focusLost(FocusEvent e) {
		if(this.getText().isEmpty()) {
			super.setText(hint);
			showingHint = true;
		}
	}

	@Override
	public String getText() {
		return showingHint ? "" : super.getText();
	}

	

}