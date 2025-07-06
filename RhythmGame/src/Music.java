import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Music {
	
	private Clip clip;
	private AudioInputStream sound;
	
	public void setFile(File file) {
		try {
			sound = AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			clip.open(sound);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}

	}
	
	public void setStream(InputStream rawStream) {
		BufferedInputStream bufferedStream = new BufferedInputStream(rawStream);
		
		try {
			sound = AudioSystem.getAudioInputStream(bufferedStream);
			clip = AudioSystem.getClip();
			clip.open(sound);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}

	}
	
	public void play() {
		clip.start();
	}
	
	public void stop() {
		try {
			sound.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		clip.close();
		clip.stop();
	}
}
