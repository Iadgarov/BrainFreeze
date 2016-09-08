package objects;

import java.io.IOException;
import java.io.InputStream;

/**
 * Master class for audio stimulants, either a resource or an external file. 
 * @author David
 *
 */
public abstract class AudioSource {
	public abstract InputStream getStream() throws IOException;
}



