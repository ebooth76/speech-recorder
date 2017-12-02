package src.main.java.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Base64.Decoder;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

import Voice.VoiceAPI.src.api.*;

/**
 * @author Aaron Wamsley
 *
 */
public class AudioManager{
	
	//globals
	private byte[] buffer;
	private int port;
	private static File audioFile;
	private static AudioInputStream audioIn;
	private static String prompt, user;
	
	/**
	 * @param in - audio input stream
	 * @param p - prompt the user is attempting to match.
	 */
//	public ReceiveAudio(AudioInputStream in, String p, String u) {
//		//TODO get audio recording details from front end.
//		/*******Audio device settings***********/
//		float rate = 44100.0f;
//		int sampleSize = 16;
//		int channels = 2;
//		boolean isSigned = true;
//		boolean bigEndian = true;
//		/***************************************/
//		
//		audioIn = in;
//		prompt = p;
//		user = u;
//		AudioFormat af = new AudioFormat(rate, sampleSize, channels, isSigned, bigEndian);
//		
//	}
	
	public VoiceMetaData analyze(String audio) {
		if(saveAudio(audio) == 0)
			return sendToSphinx(audioFile);
		return null;
	}
	
	/**
	 * Save audio to disk and insert database reference to it.
	 * returns: 	0=success
	 * 				1=failed to open output stream
	 * 				2=failed to write to file
	 */
	private int saveAudio(String audio) {
		Decoder decoder = Base64.getDecoder();
		buffer = decoder.decode(audio.split(",")[1]);
		//TODO create path to save to
		String path = "where to save";
		//generate file name
		String name = user + System.currentTimeMillis() + ".wav";
		//save file name to path
		String saveFile = path + "\\" + name;
		try {
			FileOutputStream fileOut = new FileOutputStream(saveFile);
			fileOut.write(buffer);
			fileOut.close();
			audioFile = new File(saveFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return 1;
		}catch (IOException e) {
			e.printStackTrace();
			return 2;
		}
		//TODO insert entry in DB
		
		return 0;
	}
	
	/**
	 * Sphinx requires audio file be in the following format:
	 * RIFF, Little Endian, WAVE audio, Microsoft PCM, 16 bit, mono 1600Hz
	 */
	private VoiceMetaData sendToSphinx(File f) {
		Voice voice = new Voice();
		
		//send data to sphinx and return metadata.
		return voice.analyze(f, prompt);		
	}	
}
