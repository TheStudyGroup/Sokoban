package com.c0destudy.sokoban.sound;

import com.c0destudy.sokoban.misc.Resource;
import com.c0destudy.sokoban.ui.frame.MainFrame;

import java.awt.*;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class SoundManager
{
    private static Clip backgroundClip;

	public static void playPlayerMoveSound() {
        playSound(Resource.PATH_SOUND_PLAYER_MOVE, false);
    }

    public static void playBackgroundMusic() {
	    if (backgroundClip != null) {
            stopBackgroundMusic();
        }
        backgroundClip = playSound(Resource.PATH_SOUND_BACKGROUND, true, -8.0f);
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

    // volume
    //   0 = 100%
    // -10 = 50%?
    private static Clip playSound(final String fileName,
                                  final boolean isContinuing,
                                  final float volume) {
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
            e.printStackTrace();
            return null;
        }
    }
}