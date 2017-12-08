package voice.analysis;


import java.io.File;
import java.io.IOException;

import voice.analysis.VoiceAnalysis;
import voice.api.VoiceMetaData;

public class Test {
	public static void main(String[] args) throws IOException {
		File file = new File("E:\\eclipse-workspace\\CS471-F17-Micronophones-master\\10001-90210-01803.wav");
		VoiceAnalysis va = new VoiceAnalysis();
		System.out.println("Starting Test...");
		VoiceMetaData vData = new VoiceMetaData();
		
		vData = va.analyze(file, "one zero zero zero one", vData);
		va.getVoiceScore(vData);
	}
}
