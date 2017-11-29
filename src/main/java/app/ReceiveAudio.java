package src.main.java.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

import Voice.VoiceAPI.src.api.*;

/**
 * @author Aaron Wamsley
 *
 */
public class ReceiveAudio{
	
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
	 * returns: 	0=success
	 * 				1=failed to open output stream
	 */
	private FileReturn saveAudio() {
		//TODO create path to save to
		String path = "where to save";
		//generate file name
		String name = user + System.currentTimeMillis() + ".wav";
		//save file name to path
		String saveFile = path + "\\" + name;
		try {
			FileOutputStream fileOut = new FileOutputStream(saveFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return new FileReturn(1, null);
		}
		//TODO insert entry in DB
		
		//return success and the created file.
		return new FileReturn(0, saveFile);
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
	
	/**
	 * helper class to handle return from file creation.
	 *
	 */
	private class FileReturn {
		private int returnCode;
		private String fileName;
		
		public FileReturn(int i, String s) {
			returnCode = i;
			fileName = s;
		}
		public int getReturnCode() {
			return returnCode;
		}
		public String getFileName() {
			return fileName;
		}
	}
}
