package src.main.java.app;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

/**
 * @author Aaron Wamsley
 *
 */
public class ReceiveAudio {
	
	//globals
	private byte[] buffer;
	private int port;
	private static AudioInputStream input;
	private String prompt;
	
	
	/**
	 * @param in - audio input stream
	 * @param p - prompt the user is attempting to match.
	 */
	public ReceiveAudio(AudioInputStream in, String p) {
		/*******Audio device settings***********/
		private float rate = 44100.0f;
		private int sampleSize = 16;
		private int channels = 2;
		private boolean isSigned = true;
		private boolean bigEndian = true;
		/***************************************/
		
		input = in;
		prompt = p;
		AudioFormat af = new AudioFormat(rate, sampleSize, channels, isSigned, bigEndian);
		
	}
}
