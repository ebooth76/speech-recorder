package voice.analysis;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;
import edu.cmu.sphinx.linguist.g2p.G2PConverter;
import voice.api.VoiceMetaData;
import edu.cmu.sphinx.result.Result;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class VoiceAnalysis{

	private float totalInsertions = 0;
	private float totalDeletions = 0;
	private float totalReplacements = 0;
	private float totalPhonemeRef;
	private String ref[];

	public VoiceMetaData analyze(File wavFile, String refText, VoiceMetaData vData) throws IOException {


		ref = getRefPhonemes(refText);

		StringBuilder tempS = new StringBuilder();
		for (String aRef : ref) tempS.append(aRef);
		String phonemeRef = tempS.toString();


		
		vData.setOriginalText(refText);

		Configuration configuration = new Configuration();

		configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us"); //TODO: fix file handles
		configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
		configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");


		StreamSpeechRecognizer recognizer = new StreamSpeechRecognizer(configuration);
		InputStream stream = new FileInputStream(wavFile);

		recognizer.startRecognition(stream);
		SpeechResult result = recognizer.getResult();


		if (result != null) {
			Result temp = result.getResult();
//			System.out.format("Phoneme Hypothesis: %s\n", temp.getBestPronunciationResult());	// prints words and phonemes
			System.out.println( "\nHypothesis Phonemes: " + getPhonemeHypothesis(temp.getBestPronunciationResult(), ref));	// prints just phonemes
			vData.setPhonemicTranslationDesired(phonemeRef);

			System.out.println("DESIRED PHONEMIC ALIGNMENT: " + vData.getPhonemicTranslationDesired());

			vData.setPhonemicTranslationActual(getPhonemeHypothesis(temp.getBestPronunciationResult(), ref)); // set phonemic translation for meta data
			System.out.println("Word Hypothesis: " + result.getHypothesis()); // prints what was said
			System.out.println("Desired: " + vData.getOriginalText() + "\n");	// prints what should have been said
		}
		else {
			System.out.println("Recognizer did not hear anything.");
		}


		vData.setDeletionErrorRate(totalDeletions / totalPhonemeRef); // # of phonemes left out for a word (D) / # of words
		vData.setInsertionErrorRate(totalInsertions / totalPhonemeRef); // # of extra phonemes in a word added (I) / # of words
		vData.setReplacementErrorRate(totalReplacements / totalPhonemeRef); // # of phonemes in a word said wrong (S) / # of words
		vData.setOverallErrorRate((totalDeletions + totalInsertions + totalReplacements) / totalPhonemeRef);
		

		recognizer.stopRecognition();
		return vData;
	}

	private String[] getRefPhonemes(String refText) {
		String[] words;
		words = refText.split(" ");


		G2PConverter refTextConversion = new G2PConverter("en-us/model.fst.ser");


		String refPhonemes[] = new String[words.length];
		String wordPhonemes;

		for(int i = 0; i< words.length; i++) {
			wordPhonemes = refTextConversion.phoneticize(words[i], 1).toString();
			String[] tempArray = wordPhonemes.split("	");
			refPhonemes[i] = "[" + tempArray[1].replace(" ", ",");
		}

		return refPhonemes;
	}


	/**
	 * given a string from getBestPronunciation() remove
	 * the words from the output, leaving only the phonemes.
	 * @param s getBestPronunciation()
	 * @return formatted phonemes
	 */
	private String getPhonemeHypothesis(String s, String ref[]) {
		int i = 0;
		StringBuilder ret = new StringBuilder();
		String ary[] = s.split(" ");
		for (String temp: ary) {
			String ary2[] = temp.split("\\[");
			ret.append("[").append(ary2[1]);
			phonemeAlignment(ary2[1], ref[i]);
			i++;
		}
		return ret.toString();
	}

	/**
	 * Phoneme Alignment for one word.
	 * @param hyp phoneme(s) of one word for what was said
	 * @param ref phoneme(s) of one word for what should have been said
	 */
	private void phonemeAlignment(String hyp, String ref) {
		float totalPhonemeHyp;
		// assuming ref is a word's phoneme string structured: [X,XX,X,XX,...,X]
		ref = ref.substring(1, ref.length() - 1);

		// assuming hyp is a word's phoneme string structured: X,XX,X,XX,...,X]
		hyp = hyp.substring(0, hyp.length() - 1);

		String refArr[] = ref.split(",");
		String hypArr[] = hyp.split(",");

		totalPhonemeHyp = hypArr.length;
		totalPhonemeRef = refArr.length;

		if (totalPhonemeHyp > totalPhonemeRef) {
			totalInsertions = totalPhonemeHyp - totalPhonemeRef;
		} else if (totalPhonemeHyp < totalPhonemeRef) {
			totalDeletions = totalPhonemeRef - totalPhonemeHyp;
		} else {
			for (int i = 0; i < totalPhonemeHyp; i++) {
				if (!hypArr[i].equals(refArr[i])) {
					totalReplacements++;
				}
			}
		}
	}

	/**
	 * The scoring for right now is only in here to show that we can get a scored. 
	 * Once we can get a better evaluation of the word/phrase then we can change the scoring
	 * based on that information. 
	 * 
	 * The the score is based on the error rates of different ways to mess up the word/phrase, 
	 * and then depending on that rate I have added a factor to generate a score.
	 * 
	 * @param vData Voice Meta Data
	 */
	public void getVoiceScore(VoiceMetaData vData) {
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

		vData.setScore(score);
		System.out.println("Score: " + vData.getScore());
	}
}
