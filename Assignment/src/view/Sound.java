/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import sun.audio.AudioPlayer;
import sun.audio.ContinuousAudioDataStream;

/**
 *
 * @author USER
 */
public class Sound {
    
    public static AudioPlayer BGP = AudioPlayer.player;
    public static ContinuousAudioDataStream loop = null;             
    public static File inputFile = new File(SoundUtilities.BACK_GROUND);
    public static Sequence seq;
    public static Sequencer sequencer;
    public static Clip clip1;
    public static AudioInputStream audio1;
    public static Clip clip2;
    public static AudioInputStream audio2;
    public static Clip clip3;
    public static AudioInputStream audio3;
    public static Clip clip4;
    public static AudioInputStream audio4;
    public static Clip clip5;
    public static AudioInputStream audio5;
    public static boolean prepare = false;
    
    public static void prepareBGM(){
            prepare = true;
        prepareBGM1();
        prepareBGM2();
        prepareBGM3();
        prepareBGM4();
        prepareBGM5();
    }
    
    public static void prepareBGM1(){
        try {
            prepare = true;
            audio1 = AudioSystem.getAudioInputStream(new File(SoundUtilities.BGM1));
            clip1 = AudioSystem.getClip();
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void prepareBGM2(){
        try {
            prepare = true;
            audio2 = AudioSystem.getAudioInputStream(new File(SoundUtilities.BGM2));
            clip2 = AudioSystem.getClip();
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void prepareBGM3(){
        try {
            prepare = true;
            audio3 = AudioSystem.getAudioInputStream(new File(SoundUtilities.BGM3));
            clip3 = AudioSystem.getClip();
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void prepareBGM4(){
        try {
            prepare = true;
            audio4 = AudioSystem.getAudioInputStream(new File(SoundUtilities.BGM4));
            clip4 = AudioSystem.getClip();
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void prepareBGM5(){
        try {
            prepare = true;
            audio5 = AudioSystem.getAudioInputStream(new File(SoundUtilities.BGM5));
            clip5 = AudioSystem.getClip();
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void playBGM() {
        try {
            sequencer = MidiSystem.getSequencer();
            seq = MidiSystem.getSequence(inputFile);
            sequencer.setSequence(seq);
            //repeat times
            sequencer.setLoopCount(1000);
            sequencer.open();
            sequencer.start();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        BGP.start(loop);
    }
    
    public static void stopBGM(){
        sequencer.stop();
        BGP.stop(loop);
    }
    
    public static void playBGM1() {
        AudioPlayer BGP = AudioPlayer.player;
        ContinuousAudioDataStream loop = null;
        try {
            clip1.stop();
        clip2.stop();
        clip3.stop();
        clip4.stop();
        clip5.stop();
//            AudioInputStream audio = AudioSystem.getAudioInputStream(new File(SoundUtilities.BGM1));
//            clip = AudioSystem.getClip();
            clip1.open(audio1);
            //repeat times
            clip1.loop(1000);
            clip1.start();

        } catch (Exception e) {
            //e.printStackTrace();
        }
        BGP.start(loop);
    }
    
    public static void playBGM2() {
        AudioPlayer BGP = AudioPlayer.player;
        ContinuousAudioDataStream loop = null;
        try {clip1.stop();
        clip2.stop();
        clip3.stop();
        clip4.stop();
        clip5.stop();
//            AudioInputStream audio = AudioSystem.getAudioInputStream(new File(SoundUtilities.BGM2));
//            clip = AudioSystem.getClip();
            clip2.open(audio2);
            //repeat times
            clip2.loop(1000);
            clip2.start();

        } catch (Exception e) {
            //e.printStackTrace();
        }
        BGP.start(loop);
    }
    
    public static void playBGM3() {
        AudioPlayer BGP = AudioPlayer.player;
        ContinuousAudioDataStream loop = null;
        try {clip1.stop();
        clip2.stop();
        clip3.stop();
        clip4.stop();
        clip5.stop();
//            AudioInputStream audio = AudioSystem.getAudioInputStream(new File(SoundUtilities.BGM3));
//            clip = AudioSystem.getClip();
            clip3.open(audio3);
            //repeat times
            clip3.loop(1000);
            clip3.start();

        } catch (Exception e) {
            //e.printStackTrace();
        }
        BGP.start(loop);
    }
    
    public static void playBGM4() {
        AudioPlayer BGP = AudioPlayer.player;
        ContinuousAudioDataStream loop = null;
        try {clip1.stop();
        clip2.stop();
        clip3.stop();
        clip4.stop();
        clip5.stop();
//            AudioInputStream audio = AudioSystem.getAudioInputStream(new File(SoundUtilities.BGM4));
//            clip = AudioSystem.getClip();
            clip4.open(audio4);
            //repeat times
            clip4.loop(1000);
            clip4.start();

        } catch (Exception e) {
            //e.printStackTrace();
        }
        BGP.start(loop);
    }
    
    public static void playBGM5() {
        AudioPlayer BGP = AudioPlayer.player;
        ContinuousAudioDataStream loop = null;
        try {clip1.stop();
        clip2.stop();
        clip3.stop();
        clip4.stop();
        clip5.stop();
//            AudioInputStream audio = AudioSystem.getAudioInputStream(new File(SoundUtilities.BGM5));
//            clip = AudioSystem.getClip();
            clip5.open(audio5);
            //repeat times
            clip5.loop(1000);
            clip5.start();

        } catch (Exception e) {
            //e.printStackTrace();
        }
        BGP.start(loop);
    }
    
    public static void stopBGMX(int i){
            switch (i){
                case 1: clip1.stop();
                    break;
                case 2: clip2.stop();
                    break;
                case 3: clip3.stop();
                    break;
                case 4: clip4.stop();
                    break;
                case 5: clip5.stop();
            }
        
    }
    
    public static void stopBGMX(){
        if(prepare == true){
                clip1.stop();
                clip2.stop();
                clip3.stop();
                clip4.stop();
                clip5.stop();
        }
        }
        
    
}
