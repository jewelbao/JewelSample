package com.jewelbao.library.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

/**
 Created by Kevin on 2016/1/19. ${Descript}
 */
public class PlayVoiceUtil
{

	private Context context;
	SoundPool soundPool;
	//    int voiceID;
	float volume;

	int[] voiceIDs = new int[9];

	boolean playSound = true;

	public PlayVoiceUtil(Context context)
	{
		this.context = context;
	}

	public void setPlaySound(boolean play)
	{
		this.playSound = play;
	}

	public boolean getPlaySound()
	{
		return playSound;
	}

	public void setVoiceRes(int resID)
	{
		// 指定声音池的最大音频流数目为10，声音品质为5
		if(soundPool == null)
		{
			soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 5);
		}
		// 载入音频流，返回在池中的id
		voiceIDs[0] = soundPool.load(context, resID, 0);
		AudioManager manager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		float streamVolumeCurrent = manager.getStreamVolume(AudioManager.STREAM_MUSIC);
		float streamVolumeMax = manager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		volume = streamVolumeCurrent / streamVolumeMax;
	}

	public void setVoiceRes(int[] resID)
	{
		// 指定声音池的最大音频流数目为10，声音品质为5
		if(soundPool == null)
		{
			soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 5);
		}
		// 载入音频流，返回在池中的id
		for(int i = 0; i < resID.length; i++)
		{
			voiceIDs[i] = soundPool.load(context, resID[i], 0);
		}
		AudioManager manager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		float streamVolumeCurrent = manager.getStreamVolume(AudioManager.STREAM_MUSIC);
		float streamVolumeMax = manager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		volume = streamVolumeCurrent / streamVolumeMax;
	}

	public void playVoice()
	{
		if(playSound)
		{
			soundPool.play(voiceIDs[0], volume, volume, 0, 0, 1);
		}
	}

	public void playVoice(int voicePos)
	{
		if(playSound)
		{
			voicePos = voicePos - 1;
			soundPool.play(voiceIDs[voicePos], volume, volume, 0, 0, 1);
		}
	}
}
