package rokon;

import java.util.HashSet;

import android.media.AudioManager;
import android.media.SoundPool;

/**
 * @author Richard Taylor
 * This is optimized for short sound files, which must be played quickly
 * such as sound effects in games. This is not suitable for music, and a 
 * relatively low file size limit exists.
 * 
 * The RokonAudio class uses SoundPool to manage its sounds.
 *
 */
public class RokonAudio {

	private static final int MAX_STREAMS = 4;
	public static RokonAudio singleton;
	
	private float _masterVolume;
	private SoundPool _soundPool;
	private HashSet<SoundFile> _soundFile;
	
	public RokonAudio() {
		RokonAudio.singleton = this;
		_soundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
		_soundFile = new HashSet<SoundFile>();
		_masterVolume = 1;
	}
	
	/**
	 * Frees up some memory, this should be called when you are finished.
	 */
	public void destroy() {
		try {
			for(SoundFile soundFile : RokonAudio.singleton.soundHash())
				soundFile.unload();
			_soundPool.release();
		} catch (Exception e) { }
		_soundPool = null;
	}
	
	/**
	 * @return a HashSet of all current SoundFile's 
	 */
	public HashSet<SoundFile> soundHash() {
		return _soundFile;
	}
	
	/**
	 * @return the SoundPool object currently being used
	 */
	public SoundPool getSoundPool() {
		return _soundPool;
	}
	
	/**
	 * Loads a file from the /assets/ folder in your APK, ready to be played
	 * @param filename
	 * @return 
	 */
	public SoundFile createSoundFile(String filename) {
		try {
			int id = _soundPool.load(Rokon.getRokon().getActivity().getAssets().openFd(filename), 0);
			SoundFile soundFile = new SoundFile(id);
			Debug.print("SoundFile loaded as id=" + id);
			return soundFile;
		} catch (Exception e) {
			Debug.print("CANNOT FIND " + filename + " IN ASSETS");
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * @param soundFile SoundFile to be removed from the memory
	 */
	public void removeSoundFile(SoundFile soundFile) {
		_soundPool.unload(soundFile.getId());
		_soundFile.remove(soundFile);
	}
	
	/**
	 * Removes all SoundFile's from the memory
	 */
	public void removeAllSoundFiles() {
		_soundFile = new HashSet<SoundFile>();
		_soundPool.release();
	}
	
	/**
	 * @param masterVolume the volume at which all future AudioStream's will play
	 */
	public void setMasterVolume(float masterVolume) {
		_masterVolume = masterVolume;
	}
	
	/**
	 * @return the current volume at which AudioStream's will play at
	 */
	public float getMasterVolume() {
		return _masterVolume;
	}
	

}
