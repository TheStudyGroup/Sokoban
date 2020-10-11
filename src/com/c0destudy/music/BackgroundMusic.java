package com.c0destudy;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class BackgroundMusic {
	public static void Play(String fileName)
    {
		
        try
        {
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(fileName));
            Clip clip = AudioSystem.getClip();
            clip.stop();
            clip.open(ais);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            
        }
        catch (Exception ex)
        {
        	System.out.println(ex.getMessage());
        }
        
    }
}