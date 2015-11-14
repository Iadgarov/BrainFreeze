package defualt;



import java.util.ArrayList;

import javax.swing.SwingUtilities;

import objects.Patient;

public class BrainFreezeMain {

	public static ArrayList<Patient> patients = new ArrayList<>();
	public static int currentPatientIndex = 0;
	public static final String[] RESULT_TYPES = {"graph A", "graph B", "crap", "shit"};

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
