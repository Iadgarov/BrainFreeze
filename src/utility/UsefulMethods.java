package utility;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.text.ParseException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import javax.swing.text.NumberFormatter;

/**
 * Collection of methods that do mundane but useful things
 * @author David
 *
 */
public class UsefulMethods {

	public static double[][] int_To_Double_2DArray( int [][] intArray){

		double returnMe [][] = new double[intArray.length][intArray[0].length];

		for (int i = 0; i < intArray.length; i++) {
			for (int j = 0; j < intArray[0].length; j++) {
				returnMe[i][j] = intArray[i][j];
			}
		}
		return returnMe;
	}

	public static double[][] int_To_Double_2DArray_Ratio( int [][] intArray, int ratio){

		double returnMe [][] = new double[intArray.length][intArray[0].length];

		for (int i = 0; i < intArray.length; i++) {
			for (int j = 0; j < intArray[0].length; j++) {
				returnMe[i][j] = (double)intArray[i][j]/ratio;
			}
		}
		return returnMe;
	}

	public static double[] int_To_Double_Array( int []intArray){

		double returnMe [] = new double[intArray.length];

		for (int i = 0; i < intArray.length; i++) {
			returnMe[i] = (double)intArray[i];

		}
		return returnMe;
	}

	public static double[][] transposeMatrix(double [][] m){
		double[][] temp = new double[m[0].length][m.length];
		for (int i = 0; i < m.length; i++)
			for (int j = 0; j < m[0].length; j++)
				temp[j][i] = m[i][j];
		return temp;
	}

	public static double[][] flipMatrixVertically(double [][] m){
		double[][] temp = new double[m[0].length][m.length];
		for (int i = 0; i < m.length; i++)
			for (int j = 0; j < m[0].length; j++)
				temp[i][j] = m[m.length-i-1][j];
		return temp;
	}



	private static final KeyStroke escapeStroke = 
			KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0); 
	public static final String dispatchWindowClosingActionMapKey = 
			"com.spodding.tackline.dispatch:WINDOW_CLOSING"; 

	public static void installEscapeCloseOperation(final JFrame dialog) { 

		Action dispatchClosing = new AbstractAction() { 

			private static final long serialVersionUID = -6878666591757611265L;

			public void actionPerformed(ActionEvent event) { 
				dialog.dispatchEvent(new WindowEvent( 
						dialog, WindowEvent.WINDOW_CLOSING 
						)); 
			} 
		}; 
		JRootPane root = dialog.getRootPane(); 
		root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put( 
				escapeStroke, dispatchWindowClosingActionMapKey 
				); 
		root.getActionMap().put( dispatchWindowClosingActionMapKey, dispatchClosing 
				); 
	}

	public static NumberFormatter getNumberFormatter(){
		
		NumberFormatter numberFormatter =
				new NumberFormatter() {

			private static final long serialVersionUID = -4099441919715537828L;
			public String valueToString(Object o)
					throws ParseException {
				Number number = (Number)o;
				if (number != null) {
					double d = number.doubleValue() * 100.0;
					number = new Double(d);
				}
				return super.valueToString(number);
			}
			
			public Object stringToValue(String s)
					throws ParseException {
				Number number = (Number)super.stringToValue(s);
				if (number != null) {
					double d = number.doubleValue() / 100.0;
					number = new Double(d);
				}
				return number;
			}
		};
		return numberFormatter;
	}


	public static boolean checkForFiles(String directory){

		for (int i = 0, j = 1; i < 8; i++, j++){
			j = j == 5 ? j + 1 : j;
			File temp1 = new File(directory + "\\ITD_" + Integer.toString(j) + ".wav");
			File temp2 = new File(directory + "\\ILD_" + Integer.toString(j) + ".wav");
			if (!temp1.exists() || !temp2.exists()){
				return false;
			}
		}

		return true;

	}


}
