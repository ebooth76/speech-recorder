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

import com.sun.org.apache.xml.internal.security.utils.SignerOutputStream;
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
		SpeechResult result = recognizer.getResult(); 
		if(result != null) { 
			System.out.format("Hypothesis: %s\n", result.getHypothesis());
		}
		else {
			System.out.println("Recognizer did not hear anything.");
		}
//		}
//		while ((result = recognizer.getResult()) != null) {
//			System.out.format("Hypothesis: %s\n", result.getHypothesis());
//		}
		
		nistAlign.align(refText, result.getHypothesis());
		float wordCount = nistAlign.getTotalWords();
		vData.setDeletionErrorRate(nistAlign.getTotalDeletions() / wordCount); // # of words left out (D) / # of words
		vData.setInsertionErrorRate(nistAlign.getTotalInsertions() / wordCount); // # of extra words added (I) / # of words
		
		//I believe this is the correct method call for replacements but not sure 
		vData.setReplacementErrorRate(nistAlign.getTotalSubstitutions() / wordCount); // # of words said wrong (S) / # of words
		
		//not sure if we want total word error rate for overall error rate
		vData.setOverallErrorRate(nistAlign.getTotalWordErrorRate()); // (S + D + I) / # of words
		
		recognizer.stopRecognition();
		return vData;

	}

	
	/**
	 * The scoring for right now is only in here to show that we can get a scored. 
	 * Once we can get a better evaluation of the word/phrase then we can change the scoring
	 * based on that information. 
	 * 
	 * The the score is based on the error rates of different ways to mess up the word/phrase, 
	 * and then depending on that rate I have added a factor to generate a score.
	 * 
	 * @param vData
	 * @return score
	 */
	public int getVoiceScore(VoiceMetaData vData) {
		
		int score = 100; // 100% percent
		
		float dErrRate = vData.getDeletionErrorRate();
		float iErrRate = vData.getInsertionErrorRate();
		float rErrRate = vData.getReplacementErrorRate();
		float oErrRate = vData.getOverallErrorRate();
		
		double dErrFactor = 0.30;	// Deletion Error Factor
		double iErrFactor = 0.30;	// Insertion Error Factor
		double rErrFactor = 0.30;	// Replacement Error Factor
		double oErrFactor = 0.10;	// Overall Error Factor
		
		System.out.println("Deletion Error Rate: " + dErrRate);
		System.out.println("Insertion Error Rate: " + iErrRate);
		System.out.println("Replacement Error Rate: " + rErrRate);
		System.out.println("Overall Error Rate: " + oErrRate + "\n");
		
		double dScore = dErrRate * dErrFactor;
		double iScore = iErrRate * iErrFactor;
		double rScore = rErrRate * rErrFactor;
		double oScore = oErrRate * oErrFactor;
		
		double reduction = (dScore + iScore + rScore + oScore) * 100.00;
		
		score -= reduction;
		
		System.out.println("Reduction Value: " + reduction);
		
		System.out.println("Score: " + score);
		
		return score;
	}
}
