package com.c0destudy.sound;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class SoundManager
{
    private static final String SOUND_BACKGROUND = "src/resources/test.wav";
    private static final String SOUND_PLAYER_MOVE = "src/resources/move.wav";

	public static void playPlayerMoveSound() {
        playSound(SOUND_PLAYER_MOVE, false);
    }

    public static void playBackgroundMusic() {
        playSound(SOUND_BACKGROUND, true, -8.0f);
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
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(fileName));
            Clip clip = AudioSystem.getClip();
            clip.open(ais);

            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(volume);

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