package objects;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineEvent.Type;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import utility.Settings;
import utility.TestButtonListener;

/**
 * Responsible for generating sound stimulants from either resources or external files 
 * @author David
 *
 */
public class Noise {

	// hold all sounds, a total of 17, in this array
	// first one is zero stimulant, then [1,9]\{5} ITD, then [1,9]\{5} LTD
	// 1-4 is left side, 6-9 is right side, 5 is zero stimulant 
	private static AudioSource  soundReaders[] = new AudioSource [17];


	public Noise() throws Exception{


		// use my sounds
		if (Settings.useDefaultSounds_ITD){
			soundReaders[0] = new ResourceAudioSource("/ClickITD5.wav");
			for (int i = 0, j = 1; i < 8; i++, j++){
				j = j == 5 ? j + 1 : j;
				soundReaders[i+1] = new ResourceAudioSource("/ClickITD" + Integer.toString(j) + ".wav");
				System.out.println("Putting: " + "/ClickITD" + Integer.toString(j) + ".wav" + " Into: " + (i+1));
			}

		}
		// use their sounds
		else{
			System.out.println(Settings.ITD_files + "\\ITD_5.wav");

			soundReaders[0] = new FileAudioSource(new File(Settings.ITD_files + "\\ITD_5.wav"));
			System.out.println("2133343");
			for (int i = 0, j = 1; i < 8; i++, j++){
				j = j == 5 ? j + 1 : j;
				soundReaders[i+1] = new FileAudioSource(new File(Settings.ITD_files + "\\ITD_" + Integer.toString(j) + ".wav"));
			}
		}

		if (Settings.useDefaultSounds_ILD){
			soundReaders[0] = new ResourceAudioSource("/ClickILD5.wav");
			for (int i = 0, j = 1; i < 8; i++, j++){
				j = j == 5 ? j + 1 : j;
				soundReaders[i+9] = new ResourceAudioSource("/ClickILD" + Integer.toString(j) + ".wav");	
				System.out.println("Putting: " + "/ClickILD" + Integer.toString(j) + ".wav" + " Into: " + (i+9));
			}
		}
		else{
			soundReaders[0] = new FileAudioSource(new File(Settings.ILD_files + "\\ILD_5.wav"));
			for (int i = 0, j = 1; i < 8; i++, j++){
				j = j == 5 ? j + 1 : j;
				soundReaders[i+9] = new FileAudioSource(new File(Settings.ILD_files + "\\ILD_" + Integer.toString(j) + ".wav"));
			}
		}

	}


	/**
	 * Generates sound for the relevant location around the head {1-9]. 
	 * Chooses between ILD and ITD randomly. 
	 * @param location = where the sound is coming from. Locations 1-9
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 * @throws LineUnavailableException
	 */
	public void generateTone(int location) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
		
		// Randomly choose ITD or ILD for said location
		System.out.println(location);
		int type = new Random().nextBoolean() ? Settings.ITD : Settings.ILD;
		generateToneByType(location, type);
	

	}
	
	/**
	 * Generates sound relevant to the gives location [1-9] in either ILD or ITD depending on iput
	 * @param location = where the sound is coming from. Locations 1-9
	 * @param type = ILD or ITD. ITD = 0, ILD = 1
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 * @throws LineUnavailableException
	 */
	public void generateToneByType(int location, int type)throws UnsupportedAudioFileException, IOException, LineUnavailableException{
		
		int chosenSound = type == Settings.ITD ? location : location + 8;
		if (location == 5)
			chosenSound = 0;
		else if ((chosenSound >= 5 && chosenSound <= 9 && type == Settings.ITD) || chosenSound >= 13){
			chosenSound--;
		}
		
		System.out.println("playing: " + chosenSound + " with type (ITD = 0): " + type);
		AudioInputStream chosen = null; 
		chosen = AudioSystem.getAudioInputStream(soundReaders[chosenSound].getStream());
		System.out.println("Palying: " + soundReaders[chosenSound].toString());

		Clip clip = (Clip) AudioSystem.getClip();
		clip.addLineListener(new LineListener() {
			
			@Override
			public void update(LineEvent event) {
				
				if (event.getType() == Type.STOP && TestButtonListener.demo){
					TestButtonListener.demoLive = false;
				}
				
			}
		});
		clip.open(chosen);
		clip.start();
	
	}


}
