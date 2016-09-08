package objects;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Specifc audio stimulant type. Audio taken from external file. 
 * @author David
 *
 */
public class FileAudioSource extends AudioSource {

	private final File audioFile;

	public FileAudioSource(File audioFile) {
		this.audioFile = audioFile;
	}

	@Override
	public InputStream getStream() throws FileNotFoundException {
		return new BufferedInputStream(new FileInputStream(audioFile));
	}
}