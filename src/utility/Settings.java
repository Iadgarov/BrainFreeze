package utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

/**
 * Holds dry data. Consts and settings loaded from previous user inputs. 
 * @author David
 *
 */
public class Settings {
	
	public final static String version = "v0.9.1 [beta]";

	public final static int ITD = 0;
	public final static int ILD = 1;

	public static boolean clickBtweenSounds = false;
	public final static int defaultDelayBetweenSounds = 30;
	public static int customDelayBetweenSounds = -1;

	public static final int default_dataPointCount = 4;
	public static int custom_dataPointCount = -1; // if -1 use default


	public static boolean useDefaultSounds_ITD = true;
	public static boolean useDefaultSounds_ILD = true;
	public final static String defaultSoundDirectory = "BrainFreeze/defaultSounds/";
	
	// custom sound file location:
	public static String ITD_files = "";
	public static String ILD_files = "";

	public static double[] thetaMean_ITD = {0.37, 0.35, 0.37};
	public static double[] thetaMean_ILD = {0.31, 0.32, 0.31};
	public static double[] thetaSD_ITD = {0.04, 0.05, 0.04};
	public static double[] thetaSD_ILD = {0.02, 0.03, 0.02};

	public final static double[] default_thetaMean_ITD = {0.37, 0.35, 0.37};
	public final static double[] default_thetaMean_ILD = {0.31, 0.32, 0.31};
	public final static double[] default_thetaSD_ITD = {0.04, 0.05, 0.04};
	public final static double[] default_thetaSD_ILD = {0.02, 0.03, 0.02};

	public static void thetaToDefault(){

		for (int i = 0; i < 3; i++){
			thetaMean_ITD[i] = default_thetaMean_ITD[i];
			thetaMean_ILD[i] = default_thetaMean_ILD[i];
			thetaSD_ITD[i] = default_thetaSD_ITD[i];
			thetaSD_ILD[i] = default_thetaSD_ILD[i];
		}
		Settings.save();
	}

	private static boolean failToSave = false;
	
	/**
	 * Saves settings to file
	 */
	public static void save(){
		if (failToSave)
			return;
		String data = "";

		data += ((Boolean)clickBtweenSounds).toString() + "\n";
		data += ((Integer)customDelayBetweenSounds).toString() + "\n";
		data += ((Integer)custom_dataPointCount).toString() + "\n";
		data += ((Boolean)useDefaultSounds_ITD).toString() + "\n";
		data += ((Boolean)useDefaultSounds_ILD).toString() + "\n";
		data += ITD_files + "\n";
		data += ILD_files + "\n";
		for (int i = 0; i < 3; i++){
			data += ((Double)thetaMean_ITD[i]).toString() + "\n";
			data += ((Double)thetaMean_ILD[i]).toString() + "\n";
			data += ((Double)thetaSD_ITD[i]).toString() + "\n";
			data += ((Double)thetaSD_ILD[i]).toString() + "\n";
		}

		File f = new File("BrainFreeze.settings");
		try(FileWriter fw = new FileWriter(f)) {
			fw.write(data);
		} catch (IOException e1) {

			JOptionPane.showMessageDialog(null,
					"Cannot save program settings. They will be reset to default next time." ,
					"File Save Error",
					JOptionPane.ERROR_MESSAGE);
			
			failToSave = true; // give up on saving this time around, something is wrong
			return;
		}

	}

	/**
	 * Load settings from settings file if one exists. Otherwise uses defualt values. 
	 */
	public static void load(){

		File f = new File("BrainFreeze.settings");
		FileReader fileReader;
		BufferedReader br;
		try {
			fileReader = new FileReader(f);

			br = new BufferedReader(fileReader);

			clickBtweenSounds = Boolean.parseBoolean(br.readLine());
			customDelayBetweenSounds  = Integer.parseInt(br.readLine());
			custom_dataPointCount = Integer.parseInt(br.readLine());
			useDefaultSounds_ITD = Boolean.parseBoolean(br.readLine());
			useDefaultSounds_ILD = Boolean.parseBoolean(br.readLine());
			ITD_files = br.readLine();
			ILD_files = br.readLine();
			for (int i = 0; i < 3; i++){
				thetaMean_ITD[i] = Double.parseDouble(br.readLine());
				thetaMean_ILD[i] = Double.parseDouble(br.readLine());
				thetaSD_ITD[i] = Double.parseDouble(br.readLine());
				thetaSD_ILD[i] = Double.parseDouble(br.readLine());
			}
			br.close();

		} catch (Exception e) {
			
		}
	}
	


}
