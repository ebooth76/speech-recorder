package src.Voice.VoiceAPI.src.analysis;

import java.io.File;
import java.io.IOException;

import api.VoiceMetaData;

public class Test {
	public static void main(String[]args) throws IOException {
		File file = new File("C:\\Users\\Tyler\\workspace\\SeniorProject\\CS471-F17-Micronophones\\Voice\\VoiceAPI\\data\\10001-90210-01803.wav");
		VoiceAnalysis va = new VoiceAnalysis();
		va.analyze(file, "foo", new VoiceMetaData());
	}
}
