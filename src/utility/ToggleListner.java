package utility;

import java.awt.event.ActionListener;

/**
 * A listener that can be turned off and on
 * @author David
 *
 */
public abstract class ToggleListner implements ActionListener{
	
	protected static boolean active = false; // not in use by defualt

	public static void setActive(boolean toggle){
		System.out.println("set active called with: " + toggle);
		active = toggle;
	}	
	
	public static boolean getActive(){
		return active;
	}

}
