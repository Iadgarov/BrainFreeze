package utility;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import buttons.TestChoiceButton;
import objects.Noise;
import objects.Test;
import windows_and_panels.TestWindow;

/**
 * This happens once user clicks one of the unput buttons during the test
 * @author David
 *
 */
public class TestButtonListener extends ToggleListner {

	public static boolean demo = false;
	public static boolean demoLive = false;

	private static Noise noise;

	public TestButtonListener() {
		if (noise == null){
			try{

				noise = new Noise();
			}
			catch(Exception ex){
				System.out.println("died in testButtonListener");
				JOptionPane.showMessageDialog(null,
						TestWindow.soudError,
						"Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * Make buttons (in)active and (dis)allow for input once more
	 * @param active
	 * @param d
	 */
	public static void turnOn(boolean active, boolean d){
		setActive(active);
		demo = d;
	}

	/**
	 * Either collect user input or invoke corresponding sound when in training mode. 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// if training and buttons work (no sound playing currently)
		if (demo && active ){
			if (demoLive)
				return;

			try{
				demoLive = true;
				if (TestWindow.demoType()){
					System.out.println(((TestChoiceButton)e.getSource()).getID() + "         <<<<<<<<<");
					noise.generateToneByType(((TestChoiceButton)e.getSource()).getID(), Settings.ITD);
				}
				else{
					noise.generateToneByType(((TestChoiceButton)e.getSource()).getID(), Settings.ILD);
				}


			}
			catch(Exception ex){
				System.out.println("died in testButtonListener");
				JOptionPane.showMessageDialog(null,
						TestWindow.soudError,
						"Error",
						JOptionPane.ERROR_MESSAGE);
			}
			return;
		}
		// active but not in training mode. 
		else if (active){
			Test.setChosenResult(((TestChoiceButton)e.getSource()).getID());

			// have we got enough data?
			if (Test.isDone()){
				// prompt user that test is done
				//Test.endTest();
				//int temp = 
				
				JOptionPane.showOptionDialog(null, "<html><pre>All Data Has Been Collected.<br>Click OK To Proceed.</pre></html>",
						"Examanation Over", JOptionPane.PLAIN_MESSAGE, JOptionPane.INFORMATION_MESSAGE, null, null, null);

				System.out.println("!!!!!!!!");
				//if (temp == JOptionPane.OK_OPTION)
				TestWindow.killTest();
			}

			if (utility.Settings.clickBtweenSounds){
				TestWindow.getInvokeButton().setText("Next Sound");

			}
			else{
				TestWindow.getInvokeButton().setText("Next Sound");
				TestWindow.getInvokeButton().setVisible(false);
				//TestWindow.getInvokeButton().setEnabled(false);
				//TestWindow.getBar().setVisible(true); // not visible, animation not wanted for the sake of no visual stimulants 
				TestWindow.getBar().start();


				//				TestWindow.getInvokeButton().setEnabled(false);
				//				TestWindow.getInvokeButton().setVisible(false);
				//				
				//				TestWindow.
			}



		}
		setActive(false);
	}


}
