/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package generator;

import java.awt.Component;
import org.pushingpixels.trident.Timeline;
import org.pushingpixels.trident.swing.SwingRepaintTimeline;

/**
 *
 * @author HungHandsome
 */
public class TimelineGenerator {
    
    public static void generateRepaintTimeline(Component mainTimelineComp) {
        SwingRepaintTimeline repaintTimeline = 
                                    new SwingRepaintTimeline(mainTimelineComp);
        repaintTimeline.setAutoRepaintMode(true);
        repaintTimeline.playLoop(Timeline.RepeatBehavior.LOOP);
    }
}
