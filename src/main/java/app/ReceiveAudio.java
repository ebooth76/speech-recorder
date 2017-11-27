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
	private static AudioInputStream audioIn;
	private String prompt, user;
	
	
	/**
	 * @param in - audio input stream
	 * @param p - prompt the user is attempting to match.
	 */
	public ReceiveAudio(AudioInputStream in, String p, String u) {
		//TODO get audio recording details from front end.
		/*******Audio device settings***********/
		float rate = 44100.0f;
		int sampleSize = 16;
		int channels = 2;
		boolean isSigned = true;
		boolean bigEndian = true;
		/***************************************/
		
		audioIn = in;
		prompt = p;
		user = u;
		AudioFormat af = new AudioFormat(rate, sampleSize, channels, isSigned, bigEndian);
		
	}
	
	/**
	 * Save audio to disk and insert database reference to it.
	 */
	private void saveAudio() {
		//TODO create path to save to
		
		//TODO generate file name
		
		//TODO save file name to path
		
		//TODO insert entry in DB
		
	}
	
	/**
	 * Sphinx requires audio be in the following format:
	 * RIFF, Little Endian, WAVE audio, Microsoft PCM, 16 bit, mono 1600Hz
	 */
	private void sendToSphinx() {
		//TODO convert audio input to Sphinx required format (if necessary)
		
		//TODO transmit to Sphinx
		
		//TODO process Sphinx return
		//TODO change method return value based on Sphinx return type.
		
	}
}
