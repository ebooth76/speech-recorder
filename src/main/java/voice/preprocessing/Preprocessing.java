package voice.preprocessing;

import java.io.File;
import java.io.IOException;

import voice.analysis.VoiceAnalysis;
import voice.api.VoiceMetaData;

/**
 * Class to be used for any voice pre-processing.
 * @author Tyler
 *
 */
public class Preprocessing {
	private File wavFile;
	private String originalStatement;
	/***
	 * Default constructor
	 * @param wavFile
	 * @param originalStatement
	 */
	public Preprocessing(File wavFile, String originalStatement) {
		this.wavFile=wavFile;
		this.originalStatement = originalStatement;
	}
	
	public VoiceMetaData runVoiceAnalysis() throws IOException {
		VoiceMetaData vData = new VoiceMetaData();
		runPreprocessing();    
		//Add any metadata to vData here...
		VoiceAnalysis va = new VoiceAnalysis();
		return va.analyze(wavFile, originalStatement,vData);
	}
	
	/***
	 * Unimplemented, no current need for pre-processing.
	 * Here is where any whitespace, background noise, etc, will try to be removed 
	 */
	private void runPreprocessing() {
		
		return;
	}
	
	
	
	
}
