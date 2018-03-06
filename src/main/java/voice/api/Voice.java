package voice.api;
import java.io.File;
import java.io.IOException;

import voice.preprocessing.Preprocessing;

public class Voice implements IVoice{

	@Override
	public VoiceMetaData analyze(File wavFile, String originalInput) {
		Preprocessing preProc = new Preprocessing(wavFile, originalInput);
		try {
			return preProc.runVoiceAnalysis();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
