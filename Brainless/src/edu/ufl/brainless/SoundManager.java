package edu.ufl.brainless;

import java.io.IOException;
import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.util.Log;

public class SoundManager {
	private static SoundManager _instance;
	private static SoundPool soundPool;
	private static HashMap<String, Integer> soundPoolMap;
	private static AudioManager audioManager;
	private static Context context;
	private static MediaPlayer mediaPlayer;
	private static HashMap<String, Uri> mediaPlayerMap;

	private SoundManager()	{ }

	static synchronized public SoundManager getInstance() {
		if (_instance == null)
			_instance = new SoundManager();
		return _instance;
	}

	public static void initSounds(Context theContext) {
		context = theContext;
		soundPool = new SoundPool(8, AudioManager.STREAM_MUSIC, 0);
		soundPoolMap = new HashMap<String, Integer>();
		audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mediaPlayerMap = new HashMap<String, Uri>();
	}

	public static void addSound(String index, int soundID) {
		soundPoolMap.put(index, soundPool.load(context, soundID, 1));
	}

	/**
	 * Adds short audio files to the soundPool map for use.
	 */
	public static void loadSounds() {
		addSound("pistol", R.raw.pistol);
		addSound("submachine", R.raw.submachine);
		addSound("pistol_reload", R.raw.rifle_reload);
		addSound("player_injured", R.raw.player_injured);
	}

	/**
	 * 
	 * @param index - the key of the desired sound in the soundPoolMap 
	 * @param speed - used to control playback speed (0.5 = half, 2.0 = double)
	 * @param isLooping - set to true if sound loops, otherwise use false for single sounds
	 */
	public static void playSound(String index, float speed, boolean isLooping)	{
		float streamVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		streamVolume = streamVolume / audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		if (isLooping)
			soundPool.play(soundPoolMap.get(index), streamVolume, streamVolume, 1, -1, speed);
		else
			soundPool.play(soundPoolMap.get(index), streamVolume, streamVolume, 1, 0, speed);
		//Log.d("SoundManager", "Play sound " + index);
	}

	public static void stopSound(int index)
	{
		soundPool.stop(soundPoolMap.get(index));
	}

	/**
	 * Creates a Uri object used to identify files for MediaPlayer
	 */
	public static void addMedia(String index, String path) {
		Uri media = Uri.parse("android.resource://" + context.getPackageName() + "/raw/" + path);
		mediaPlayerMap.put(index, media);
	}

	/**
	 * Adds longer audio files to mediaPlayer map for future use
	 */
	public static void loadMedia() {
		addMedia("theme", "test_theme");
	}	

	/**
	 * playMedia(int index) sets a new data source for the mediaPlayer with Uri from mediaPlayerMap,
	 * prepares the mediaPlayer once set, and then starts it.
	 * 
	 * @param index - the key of the desired audio file in the mediaPlayerMap
	 */
	public static void playMedia(String index) {
		try {
			mediaPlayer.setDataSource(context, mediaPlayerMap.get(index));
			mediaPlayer.prepare();
			mediaPlayer.start();
		}
		catch(IOException ex) {
			Log.d("SoundManager", "Media file not found");
		}
		catch(IllegalArgumentException ex) {
			Log.d("SoundManager", "Unable to play media file");
		}
	}

	/**
	 * Pauses playback of the mediaPlayer.
	 */
	public static void pauseMedia() {
		mediaPlayer.pause();
	}

	/**
	 * Resumes playing from where the mediaPlayer was paused.
	 */
	public static void resumeMedia() {
		mediaPlayer.start();
	}

	/**
	 * Resets the mediaPlayer so that a new file can be loaded into the player.
	 */
	public static void resetMedia() {
		mediaPlayer.reset();
	}

	public static void cleanup() {
		soundPool.release();
		soundPool = null;
		soundPoolMap.clear();
		mediaPlayer.release();
		mediaPlayer = null;
		mediaPlayerMap.clear();
		audioManager.unloadSoundEffects();
		_instance = null;
	}
}