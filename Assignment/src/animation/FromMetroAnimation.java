/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package animation;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.JPanel;
import org.pushingpixels.trident.Timeline;
import org.pushingpixels.trident.swing.SwingRepaintTimeline;
import view.MainFrame;

/**
 *
 * @author HungHandsome
 */
public class FromMetroAnimation extends JPanel {
    
    private SwingRepaintTimeline repaintTimeline;
    private Timeline timeline;
    private Image bg;
    private Image roomImg;
    private int roomX;
    private int roomY;
    private float scale;

    public FromMetroAnimation(Image bg, Image roomImg, 
                                int rx, int ry, float scale) {
        this.bg = bg;
        this.roomImg = roomImg;
        roomX = rx;
        roomY = ry;
        this.scale = scale;
        
        timeline = new Timeline(this);
        timeline.addPropertyToInterpolate("scale", scale, 1.0f);
        timeline.addPropertyToInterpolate("roomX", roomX, 0);
        timeline.addPropertyToInterpolate("roomY", roomY, 0);
        timeline.setDuration(300);
        
        repaintTimeline = new SwingRepaintTimeline(this);
        repaintTimeline.setAutoRepaintMode(true);
        repaintTimeline.playLoop(Timeline.RepeatBehavior.LOOP);
        
        setLayout(null);
        setPreferredSize(
                new Dimension(MainFrame.PANEL_WIDTH, MainFrame.PANEL_HEIGHT));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)(g.create());
        
        g.drawImage(bg, 0, 0, getWidth(), getHeight(), null);
        
        g2.translate(roomX, roomY);
        g2.scale(scale, scale);
        g2.drawImage(roomImg, 0, 0, null);
    }
    
    public void playAnimation() {
//        new Thread() {
//            @Override
//            public void run() {
//                try {
//                    sleep(350);
//                } catch(Exception ex) {
//                    
//                }
//                
//            }
//        }.start();
        timeline.replay();
    }

    public int getRoomX() {
        return roomX;
    }

    public void setRoomX(int roomX) {
        this.roomX = roomX;
    }

    public int getRoomY() {
        return roomY;
    }

    public void setRoomY(int roomY) {
        this.roomY = roomY;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
    
}
