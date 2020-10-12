package com.c0destudy.sound;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class MoveSound {
	public static void Play(String fileName)
    {
		
        try
        {
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(fileName));
            Clip clip = AudioSystem.getClip();
            clip.stop();
            clip.open(ais);
            clip.start();
            
            
        }
        catch (Exception ex)
        {
        	System.out.println(ex.getMessage());
        }
        
    }
}