package voice.api;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VoiceMetaData {
	
	private float overallErrorRate;
	private float insertionErrorRate;
	private float deletionErrorRate;
	private float replacementErrorRate;
	private String phonemicTranslationActual;
	private String phonemicTranslationDesired;
	private String date;
	private String time;

	
	/**
	 * Default constructor
	 */
	public VoiceMetaData() {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		this.date = df.format(new Date());
		df = new SimpleDateFormat("HH:mm:ss");
		this.time = df.format(new Date());
		
	}
	
	/**
	 * Constructor for voice meta data class
	 * @param overallErrorRate the overall error rate of the voice analyzed
	 * @param insertionErrorRate the error rate specifically for insertion error
	 * @param deletionErrorRate the error rate specifically for deletion error
	 * @param replacementErrorRate the error rate specifically for replacement error
	 * @param phonemicTranslationActual the phonemic translation that was gained from the voice data
	 * @param phonemicTranslationDesired the best phonemic translation for the text 
	 */
	public VoiceMetaData(float overallErrorRate, float insertionErrorRate, float deletionErrorRate, float replacementErrorRate,
			String phonemicTranslationActual, String phonemicTranslationDesired) {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		this.date = df.format(new Date());
		df = new SimpleDateFormat("HH:mm:ss");
		this.time = df.format(new Date());
		this.overallErrorRate = overallErrorRate;
		this.insertionErrorRate = insertionErrorRate;
		this.deletionErrorRate = deletionErrorRate;
		this.replacementErrorRate = replacementErrorRate;
		this.phonemicTranslationActual = phonemicTranslationActual;
		this.phonemicTranslationDesired = phonemicTranslationDesired;
		
	}
	
	/**
	 * Gets the overall error rate of the voice analyzed
	 * @return the overall error rate
	 */
	public float getOverallErrorRate() {
		return overallErrorRate;
	}

	/**
	 * Sets the overall error rate of the voice analyzed
	 * @param overallErrorRate the overall error rate
	 */
	public void setOverallErrorRate(float overallErrorRate) {
		this.overallErrorRate = overallErrorRate;
	}

	/**
	 * Gets the error rate specifically for insertion errors
	 * @return the insertion error rate
	 */
	public float getInsertionErrorRate() {
		return insertionErrorRate;
	}

	/**
	 * Sets the insertion error rate for the voice analyzed
	 * @param insertionErrorRate the insertion error rate
	 */
	public void setInsertionErrorRate(float insertionErrorRate) {
		this.insertionErrorRate = insertionErrorRate;
	}

	/**
	 * Gets the deletion error rate for the voice analyzed
	 * @return the deletion error rate
	 */
	public float getDeletionErrorRate() {
		return deletionErrorRate;
	}

	/**
	 * Sets the deletion error rate for the voice analyzed
	 * @param deletionErrorRate the delection error rate
	 */
	public void setDeletionErrorRate(float deletionErrorRate) {
		this.deletionErrorRate = deletionErrorRate;
	}

	/**
	 * Gets the replacement error rate
	 * @return the replacement error rate
	 */
	public float getReplacementErrorRate() {
		return replacementErrorRate;
	}

	/**
	 * Sets the replacement error rate for the voice analyzed
	 * @param replacementErrorRate the replacement error rate.
	 */
	public void setReplacementErrorRate(float replacementErrorRate) {
		this.replacementErrorRate = replacementErrorRate;
	}

	/**
	 * Gets the phonemic translation for the voice
	 * @return the actual phonemic translation
	 */
	public String getPhonemicTranslationActual() {
		return phonemicTranslationActual;
	}

	/**
	 * Sets the phonemic translation for the voice analyzed
	 * @param phonemicTranslationActual the actual phonemic translation
	 */
	public void setPhonemicTranslationActual(String phonemicTranslationActual) {
		this.phonemicTranslationActual = phonemicTranslationActual;
	}

	/**
	 * Gets the desired phonemic translation (text)
	 * @return the desired phonemic translation
	 */
	public String getPhonemicTranslationDesired() {
		return phonemicTranslationDesired;
	}

	/**
	 * Sets the desired phonemic translation based off the original text
	 * @param phonemicTranslationDesired the desired phonemic translation
	 */
	public void setPhonemicTranslationDesired(String phonemicTranslationDesired) {
		this.phonemicTranslationDesired = phonemicTranslationDesired;
	}
	
	/**
	 * Gets the date that this VoiceMetaData object was created.
	 * In the format "MM/dd/yyyy"
	 * @return the date this VoiceMetaData object was created.
	 */
	public String getDate() {
		return date;
	}

	/**
	 * Gets the time that this VoiceMetaData object was created.
	 * In the format "HH:mm:ss"
	 * @return the time this VoiceMetaData object was created.
	 */
	public String getTime() {
		return time;
	}
	
}
