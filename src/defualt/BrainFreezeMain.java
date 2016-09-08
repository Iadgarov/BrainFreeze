package defualt;



import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import objects.Patient;
import utility.Settings;
import windows_and_panels.MainWindow;

public class BrainFreezeMain {

	public static ArrayList<Patient> patients = new ArrayList<>();
	public static int currentPatientIndex = 0;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		// load the setting saved to the settings file from any previous run
		// if no file exists settings are left as defualt. 
		Settings.load();


		// give time delay so that we may admire the spalshscreen for a bit longer 
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		// set look and feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// start
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				final MainWindow visualize = new MainWindow();
				visualize.start(null);
			}

		});


	}



}
