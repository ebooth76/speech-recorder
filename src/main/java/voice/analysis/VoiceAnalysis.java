package voice.analysis;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;
import edu.cmu.sphinx.util.NISTAlign;
import voice.api.VoiceMetaData;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
public class VoiceAnalysis{

	public VoiceMetaData analyze(File wavFile, String refText, VoiceMetaData vData) throws IOException{
		
		NISTAlign nistAlign = new NISTAlign(true, true);
		
		
		Configuration configuration = new Configuration();

		configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");//TODO: fix file handles
		configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
		configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");

		StreamSpeechRecognizer recognizer = new StreamSpeechRecognizer(configuration);
		InputStream stream = new FileInputStream(wavFile);

		recognizer.startRecognition(stream);
		SpeechResult result; //need to define this value 
		while ((result = recognizer.getResult()) != null) {
			System.out.format("Hypothesis: %s\n", result.getHypothesis());
		}
		
		nistAlign.align(refText, result.getHypothesis());
		vData.setDeletionErrorRate(nistAlign.getTotalDeletions());
		vData.setInsertionErrorRate(nistAlign.getTotalInsertions());
		vData.setReplacementErrorRate(nistAlign.getTotalSubstitutions()); //I believe this is the correct method call for replacements but not sure 
		vData.setOverallErrorRate(nistAlign.getTotalWordErrorRate()); //not sure if we want total word error rate for overall error rate
		
		recognizer.stopRecognition();
		return vData;

	}


}
