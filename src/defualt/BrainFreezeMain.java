package defualt;



import java.util.ArrayList;

import javax.swing.SwingUtilities;

import mainWindow.MainWindow;
import objects.Patient;

public class BrainFreezeMain {

	public static ArrayList<Patient> patients = new ArrayList<>();
	public static int currentPatientIndex = 0;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		 SwingUtilities.invokeLater(new Runnable() {
             public void run() {
                  final MainWindow visualize = new MainWindow();
                  visualize.start(null);
             }
             
		 });
		 
		 
		 
		 
	}

	

}
