package buttons;

/**
 * The buttons corresponding to locations in the test (the semicircle around the head)
 * A cicular button with an ID (number to represent the location).
 * @author David
 *
 */
public class TestChoiceButton extends RoundButton{

	private static final long serialVersionUID = 60021903248889428L;
	
	private int id = -1;
	
	public TestChoiceButton(String label) {
		super(label);
		id = Integer.parseInt(label);
	}

	public int getID(){
		return id;
	}
	

}
