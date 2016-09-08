package objects;

import java.io.IOException;
import java.util.Random;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import defualt.BrainFreezeMain;
import utility.Settings;
import utility.TestButtonListener;
import windows_and_panels.LeftPanel;

/**
 * Executes the test, collects the data as it is inputed, stops test once neccesary or otherwise propted to. 
 * @author David
 *
 */
public class Test {


	private static Noise noise;
	private static Random rand;

	// count the number of times each stimulant has been invoked, has to be at least 4 each
	static int timeStimulantCount[];
	static int levelStimulantCount[];

	// record data in here, will be set to new patient upon test completion
	static private int[][] levelDataCollected;
	static private int[][] timeDataCollected;
	private static int chosenResult; // which button was pressed as a result of this sound invocation

	Random randGenerator = new Random();	// for random choice of stimulant

	static int diff;
	static int location;



	public Test() throws Exception {
		timeStimulantCount = new int[9];
		levelStimulantCount = new int[9];
		int temp = Settings.custom_dataPointCount == -1 ? Settings.default_dataPointCount : Settings.custom_dataPointCount;
		for (int i = 0; i < 9; i++){
			timeStimulantCount[i] = temp;
			levelStimulantCount[i] = temp;
		}
		levelDataCollected = new int[9][9]; 
		timeDataCollected = new int[9][9];

		rand = new Random();
		noise = new Noise();

	}


	/**
	 * Choose random location, get sound stimulant for it. If this stimulant still has not been used enough times then sound it
	 * Not efficient.. Should really generate a random locations array ahead of time and go through it rather than an endless loop. 
	 * @param repeat = use the same sound as before or generate something new
	 * @throws LineUnavailableException
	 * @throws IOException
	 * @throws UnsupportedAudioFileException
	 */
	public void invokeStimulant(boolean repeat) throws LineUnavailableException, IOException, UnsupportedAudioFileException{

		if (repeat){
			System.out.println("repeating, location = " + location + " diff = " + diff);
			noise.generateToneByType(location, diff);
			return;
		}

		while (true){
			
			if (isDone())
				return;
			// location
			int chosen = rand.nextInt(9) + 1;
			location = chosen;
			// ITD
			if (rand.nextBoolean()){
				diff = Settings.ITD;
				if (timeStimulantCount[chosen - 1] > 0){
					System.out.println("ITD, location = " + location + " diff = " + diff);
					noise.generateToneByType(chosen, Settings.ITD);
					return;
				}

			}
			// ILD
			else{
				diff = Settings.ILD;
				if (levelStimulantCount[chosen - 1] > 0){
					System.out.println("LTD, location = " + location + " diff = " + diff);
					noise.generateToneByType(chosen, Settings.ILD);
					return;
				}
			}
		}

	}

	/**
	 * Checks to see if every stimulant has been used enough times
	 * @return true if enough data collected, false otherwise 
	 */
	public static boolean isDone(){
		for (int i = 0; i < 9; i++){
			if (timeStimulantCount[i] > 0 || levelStimulantCount[i] > 0)
				return false;
		}
		return true;
	}

	/**
	 * Take user input to stimulant and save it to array, later to be pushed into a patients Measurements object 
	 * @param c
	 */
	public static void setChosenResult(int c){
		chosenResult = c;
		TestButtonListener.setActive(false);


		if (diff == Settings.ILD){
			levelStimulantCount[location - 1]--;
			levelDataCollected[chosenResult - 1][location - 1]++;
		}
		else{
			timeStimulantCount[location - 1]--;
			timeDataCollected[chosenResult - 1][location - 1]++;
		}

		System.out.println("\t\t\tchosen button: " + c);


	}

	public static int getChosenResult(){
		return chosenResult;
	}

	/**
	 * stop test, save data to patient object 
	 */
	public static void endTest(){
		if (timeDataCollected == null)
			return;
		Measurements m = new Measurements();
		m.setITD_Results(timeDataCollected);
		m.setLTD_Results(levelDataCollected);
		m.dataIsValid();
		BrainFreezeMain.patients.get(BrainFreezeMain.currentPatientIndex).setData(m);
		LeftPanel.initComboBox(0); // init with ITD histogram 

	}

}
