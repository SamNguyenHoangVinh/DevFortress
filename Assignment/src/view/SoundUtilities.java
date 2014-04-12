/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.File;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import sun.audio.*;

/**
 *
 * @author USER
 */
public class SoundUtilities {

    public static final String SOUND_PATH = "Sounds/";
    public static final String BEEP1 = SOUND_PATH + "BEEP1C.WAV";
    public static final String BEEP2 = SOUND_PATH + "BEEP2C.WAV";
    public static final String BEEP3 = SOUND_PATH + "BEEP3.WAV";
    public static final String BEEP4 = SOUND_PATH + "BEEP4.WAV";
    public static final String BACK_GROUND = SOUND_PATH + "BLUES.MID";
    public static final String CLICK1 = SOUND_PATH + "CLICK1C.WAV";
    public static final String CLICK2 = SOUND_PATH + "CLICK12A.WAV";
    public static final String CLICK3 = SOUND_PATH + "CLICK12B.WAV";
    public static final String CLICK4 = SOUND_PATH + "CLICK15A.WAV";
    public static final String CLICK5 = SOUND_PATH + "CLICK15B.WAV";
    public static final String CLICK6 = SOUND_PATH + "CLICK19A.WAV";
    public static final String CLICK7 = SOUND_PATH + "CLICK21A.WAV";
    public static final String COIN1 = SOUND_PATH + "coin1.wav";
    public static final String COIN2 = SOUND_PATH + "coin2.wav";
    public static final String GAME_OVER = SOUND_PATH + "Defeat.mid";
    public static final String PROJECT_DONE = SOUND_PATH + "Victory.mid";
    public static final String GASP = SOUND_PATH + "GASP.WAV";
    public static final String GROAN = SOUND_PATH + "GROAN.WAV";
    public static final String GRUNTBRE = SOUND_PATH + "GRUNTBRE.WAV";
    public static final String OLALA = SOUND_PATH + "OOH_LALA.WAV";
    public static final String SNORE1 = SOUND_PATH + "SNORE1.WAV";
    public static final String SNORE2 = SOUND_PATH + "SNORE2.WAV";
    public static final String TYPING1 = SOUND_PATH + "typing1.wav";
    public static final String TYPING2 = SOUND_PATH + "typing2.wav";
    public static final String TYPING3 = SOUND_PATH + "typing3.wav";
    public static final String WOW = SOUND_PATH + "WHAOO.WAV";
    public static final String SLIDE = SOUND_PATH + "slide.wav";
    public static final String INTRO = SOUND_PATH + "Intro.mid";
    public static final String INTRO2 = SOUND_PATH + "Intro2.wav";
    public static final String BGM1 = SOUND_PATH + "BGM1.wav";
    public static final String BGM2 = SOUND_PATH + "BGM2.wav";
    public static final String BGM3 = SOUND_PATH + "BGM3.wav";
    public static final String BGM4 = SOUND_PATH + "BGM4.wav";
    public static final String BGM5 = SOUND_PATH + "BGM5.wav";

    //Play .wav files
    public static void playWAV(String fileName, int loopCount) {
        AudioPlayer BGP = AudioPlayer.player;
        ContinuousAudioDataStream loop = null;
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(new File(fileName));
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            //repeat times
            clip.loop(loopCount);
            clip.start();

        } catch (Exception e) {
            //e.printStackTrace();
        }
        BGP.start(loop);

    }

    //Play.mid files
    public static void playMIDI(String fileName, int loopCount) {
        AudioPlayer BGP = AudioPlayer.player;
        ContinuousAudioDataStream loop = null;
        try {
            File inputFile = new File(fileName);
            Sequence seq;
            Sequencer sequencer;
            sequencer = MidiSystem.getSequencer();
            seq = MidiSystem.getSequence(inputFile);
            sequencer.setSequence(seq);
            //repeat times
            sequencer.setLoopCount(loopCount);
            sequencer.open();
            sequencer.start();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        BGP.start(loop);
    }
    
    
    //play background music
    public static void BackGroundMusic(){
        playMIDI(BACK_GROUND, 1000);
    }
    
    public static void GameOver(){
        playMIDI(GAME_OVER, 0);
    }
    
    public static void ProjectDone(){
        playMIDI(PROJECT_DONE, 0);
    }
    
    public static void Beep1(){
        playWAV(BEEP1, 0);
    }
    
    public static void Beep2(){
        playWAV(BEEP2, 0);
    }
    
    public static void Click1(){
        playWAV(CLICK1, 0);
    }
    
    public static void Click2(){
        playWAV(CLICK2, 0);
    }
    
    public static void Click3(){
        playWAV(CLICK3, 0);
    }
    
    public static void Click4(){
        playWAV(CLICK4, 0);
    }
    
    public static void Click5(){
        playWAV(CLICK5, 0);
    }
    
    public static void Click6(){
        playWAV(CLICK6, 0);
    }
    
    public static void Click7(){
        playWAV(CLICK7, 0);
    }
    
    public static void Coin1(){
        playWAV(COIN1, 0);
    }
    
    public static void Coin2(){
        playWAV(COIN2, 0);
    }
    
    public static void Typing1(){
        playWAV(TYPING1, 17);
    }
    
    public static void Typing2(){
        playWAV(TYPING2, 17);
    }
    
    public static void Typing3(){
        playWAV(TYPING3, 17);
    }
    
    public static void Back(){
        playWAV(BEEP1, 0);
    }
    
    public static void NewGame(){
        playWAV(BEEP3, 0);
    }
    
    public static void Slide(){
        playWAV(SLIDE, 0);
    }
}