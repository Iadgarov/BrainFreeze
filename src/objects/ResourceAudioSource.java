package objects;

import java.io.BufferedInputStream;
import java.io.InputStream;

import defualt.BrainFreezeMain;

/**
 * Specific type of audio source type. Using the resources in the jar file itself. 
 * @author David
 *
 */
public class ResourceAudioSource extends AudioSource {

	private final String resourceName;

	public ResourceAudioSource(String resourceName) {
		this.resourceName = resourceName;
	}

	@Override
	public InputStream getStream() {
		return new BufferedInputStream(BrainFreezeMain.class.getResourceAsStream(resourceName));
	}
	
	@Override
	public String toString() {
		return resourceName;
	}
}