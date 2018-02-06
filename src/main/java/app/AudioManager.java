package app;

import voice.api.Voice;
import voice.api.VoiceMetaData;

import javax.sound.sampled.AudioInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Decoder;


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
	 * Method to accept audio and string 
	 * @param audio: audio file
	 * @param phrase: phrase to be spoken
	 * @param user: user to record the record for.
	 * @return string containing success or failure.  once fully implemented, will return metadata.
	 */
	public String analyze(String audio, String phrase, String user) {
		AudioManager.prompt = phrase;
		AudioManager.user = user;
		if(saveAudio(audio) == 0){
			addToDB(sendToSphinx(audioFile));
			return "success";
		}
		return "failure";
	}
	
	private String addToDB(VoiceMetaData vmd) {
		try (Connection connection = getConnection()) {
	        Statement stmt = connection.createStatement();
	        String preppedStatement = "insert into metadata Values(" +vmd.getOverallErrorRate() + ", " +
	        		vmd.getInsertionErrorRate() + ", " + vmd.getDeletionErrorRate() + ", " + vmd.getReplacementErrorRate() + 
	        		", " + vmd.getPhonemicTranslationActual() + ", " + vmd.getPhonemicTranslationDesired() + ", " + vmd.getDate() + 
	        		vmd.getTime() + audioFile.getPath() + ")";
	        stmt.executeUpdate(preppedStatement);
			connection.close();
	        return "db";
	      } catch (Exception e) {
	        return "error";
	      }
	}
	private static Connection getConnection() throws SQLException {
	    String dbUrl = System.getenv("JDBC_DATABASE_URL");
	    return DriverManager.getConnection(dbUrl);
	}
	
	private void parseMetaData(VoiceMetaData vmd) {
		
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
		//generate file name
		String name = user + System.currentTimeMillis() + ".wav";
		//save file name to Google Drive
		GoogleDrive drive = new GoogleDrive();
		String saveFile = name;
		try {
			FileOutputStream fileOut = new FileOutputStream(saveFile);
			fileOut.write(buffer);
			fileOut.close();
			audioFile = new File(saveFile);
			drive.saveFile(audioFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return 1;
		}catch (IOException e) {
			e.printStackTrace();
			return 2;
		}		
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
