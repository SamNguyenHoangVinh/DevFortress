/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
import sun.audio.AudioPlayer;
import sun.audio.ContinuousAudioDataStream;

/**
 *
 * @author USER
 */
public class SoundIntro {
    
    public static AudioPlayer BGP = AudioPlayer.player;
    public static ContinuousAudioDataStream loop = null;             
    public static File inputFile = new File(SoundUtilities.INTRO);
    public static String File = SoundUtilities.INTRO2;
    public static Sequence seq;
    public static Sequencer sequencer;
    public static Clip clip;
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
    
    public static void playWAV() {
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(new File(File));
            clip = AudioSystem.getClip();
            clip.open(audio);
            //repeat times
            clip.loop(1000);
            clip.start();

        } catch (Exception e) {
            //e.printStackTrace();
        }
        BGP.start(loop);

    }
    
    public static void stopBGM(){
        sequencer.stop();
        BGP.stop(loop);
    }
    
    public static void stopWAV(){
        clip.stop();
    }
}
