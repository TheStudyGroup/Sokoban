package com.c0destudy.sokoban.resource;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

public class Sound
{
    private static Clip backgroundClip;
    private static boolean isBackgroundEnabled = true;

	public static void playPlayerMoveSound() {
        playSound(Resource.PATH_SOUND_PLAYER_MOVE, false);
    }

    public static boolean getBackgroundEnabled() {
	    return isBackgroundEnabled;
    }

    public static void setBackgroundEnabled(final boolean enabled) {
	    isBackgroundEnabled = enabled;
	    if (!isBackgroundEnabled) {
            stopBackgroundMusic();
        }
    }

    public static void playBackgroundMusic() {
	    if (backgroundClip != null) {
            stopBackgroundMusic();
        }
	    if (isBackgroundEnabled) {
            backgroundClip = playSound(Resource.PATH_SOUND_BACKGROUND, true, -8.0f);
        }
    }

    public static void stopBackgroundMusic() {
	    if (backgroundClip != null) {
            backgroundClip.stop();
            backgroundClip = null;
        }
    }

    private static Clip playSound(final String fileName, final boolean isContinuing) {
        return playSound(fileName, isContinuing, 0);
    }

    private static Clip playSound(final String fileName, final boolean isContinuing, final float volume) {
        try {
            final AudioInputStream ais     = AudioSystem.getAudioInputStream(new File(fileName));
            final Clip             clip    = AudioSystem.getClip();
            clip.open(ais);
            final FloatControl     control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            control.setValue(volume);
            clip.start();
            if (isContinuing) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            return clip;
        } catch (Exception e) {
            return null;
        }
    }
}