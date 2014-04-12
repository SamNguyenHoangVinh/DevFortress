/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.mainmenu;

import java.awt.*;
import org.pushingpixels.trident.Timeline;
import org.pushingpixels.trident.swing.SwingRepaintTimeline;
import view.SoundUtilities;

/**
 * The view of the main menu button
 * @author s3342128
 */
public class MainMenuButton {
    
    public static final int BTN_GAP = 15; //The vertical gap between buttons
    public static final int BTN_WIDTH = 265;
    public static final int BTN_HEIGHT = 45;
    public static final int MOVE_DISTANCE = 30;
    
    
    private Image img; 
    private Timeline timeLine;
    private Timeline timeLine2;
//    private SwingRepaintTimeline repaintTimeline;
    private int btnX;
    private int btnY;
//    private static int btnWidth;
//    private static int btnHeight;
    
    public MainMenuButton(Image i, SwingRepaintTimeline repaintTimeline) {
//        setBackground(Color.BLACK);
        img = i;       
//        btnWidth = img.getWidth(null);
//        btnHeight = img.getHeight(null);
        
        timeLine = new Timeline(this);
        timeLine.addPropertyToInterpolate("btnX",
                MainMenu.BTN_X, MainMenu.BTN_X + MOVE_DISTANCE);
        timeLine.setDuration(200);
        
        
        timeLine2 = new Timeline(this);
        timeLine2.addPropertyToInterpolate("btnX", -BTN_WIDTH, MainMenu.BTN_X);
        timeLine2.setDuration(300);
//        this.repaintTimeline = repaintTimeline;
    }

    /**
     * Draw this button using the current graphic context
     * @param g The current graphic context
     */
    public void drawButton(Graphics g) {
        g.drawImage(img, btnX, btnY, null);
    }
   
    /**
     * Play animation which this button moves <code>MOVE_DISTANCE</code> to<br/>
     * the right
     */
    public void moveToRight() {
        timeLine.replay();
    }
    
    /**
     * Play animation which this button moves <code>MOVE_DISTANCE</code> to<br/>
     * the left
     */
    public void moveToLeft() {
        timeLine.replayReverse();
        SoundUtilities.Slide();
    }
    
    /**
     * Play animation which this button move out
     */
    public void moveOut() {
        timeLine2.replay();
    }
    
    /**
     * Play animation which this button move in
     */
    public void moveIn() {
        timeLine2.replayReverse();
    }
    
    public void setCoordinates(int x, int y) {
        btnX = x;
        btnY = y;     
    }
    
    public void setCoordinatesNotRepaint(int x, int y) {
        btnX = x;
        btnY = y;
//        System.out.println("Y: " + y);
    }
      
    public int getBtnX() {
        return btnX;
    }

    public void setBtnX(int x) {
        btnX = x;
//        repaintTimeline.forceRepaintOnNextPulse();
    }

    public int getBtnY() {
        return btnY;
    }

    public void setBtnY(int y) {
        btnY = y;
    }

//    public static int getBtnWidth() {
//        return btnWidth;
//    }
//
//    public static int getBtnHeight() {
//        return btnHeight;
//    }

    public Timeline getTimeLine() {
        return timeLine;
    }
    
}
