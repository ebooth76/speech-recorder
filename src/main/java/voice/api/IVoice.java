package voice.api;
import java.io.File;

public interface IVoice {
	
	/***
	 * This is the front facing interface for the voice recognition packages.
	 * A .wav file of the subjects voice recording needs to be passed in,
	 * as well as what the subject was intending to say.
	 * @param wavFile The voice data of the subject (file handle).
	 * @param originalInput A string of text that represents the "game" the subject attempted to say.
	 * @return VoiceMetaData or null if failure
	 */
	public VoiceMetaData analyze(File wavFile, String originalInput);
	
	//This is the entry point for the Voice API that will be open to researchers and will 
	//also be utilized by the server side for ASR and Voice analytics.
}
