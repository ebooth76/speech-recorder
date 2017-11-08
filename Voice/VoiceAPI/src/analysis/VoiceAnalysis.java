package analysis;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import api.VoiceMetaData;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;
public class VoiceAnalysis{

	public VoiceMetaData analyze(File wavFile, String refText, VoiceMetaData vData) throws IOException{
		
		Configuration configuration = new Configuration();

		configuration.setAcousticModelPath("file:../../data/en-us/en-us");//TODO: fix file handles
		configuration.setDictionaryPath("file:../../data/en-us/cmudict-en-us.dict");
		configuration.setLanguageModelPath("file:../../data/en-us/en-us.lm.bin");

		StreamSpeechRecognizer recognizer = new StreamSpeechRecognizer(configuration);
		InputStream stream = new FileInputStream(wavFile);

		recognizer.startRecognition(stream);
		SpeechResult result;
		while ((result = recognizer.getResult()) != null) {
			System.out.format("Hypothesis: %s\n", result.getHypothesis());
		}
		recognizer.stopRecognition();
		return vData;

	}


}