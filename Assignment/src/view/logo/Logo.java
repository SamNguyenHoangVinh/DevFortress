/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.logo;

import java.awt.*;
import javax.swing.*;
import org.pushingpixels.trident.Timeline;
import org.pushingpixels.trident.swing.SwingRepaintTimeline;
import view.ViewUtilities;

/**
 * This class represents a view of the logo of the game. The view will appear
 * <br/>by playing an animation.
 * @author s3342128
 */
public class Logo {   
       
    private Timeline separatorTL; //The time line that adjust the separator 
                                  //in the logo
    private Timeline leftWidthTL; //The time line that adjust the left side's 
                             //width in the logo
    private Timeline rightWidthTL; //The time line that adjust the right side's 
                              //width in the logo
    private Timeline leftXTL; //The time line that adjust the left side's 
                             //x in the logo
    private Timeline rightXTL; //The time line that adjust the right side's 
                              //y in the logo
    private SwingRepaintTimeline repaintTimeline;
    private Image separator;
    private Image leftSide;
    private Image rightSide;
    private int separatorX;
    private int separatorY;
    private int separatorHeight;
    private int leftX;
//    private int leftY;
    private int leftWidth;
    private int rightX;
//    private int rightY;
    private int rightWidth;
    private int wordY = 65;
        
    public Logo(SwingRepaintTimeline repaintTimeline) {    
        leftX = 569;
        rightX = 572;
        separator = new ImageIcon(ViewUtilities.SEPERATOR_LOGO).getImage();
        leftSide = new ImageIcon(ViewUtilities.DEV_LOGO).getImage();
        rightSide = new ImageIcon(ViewUtilities.FORTRESS_LOGO).getImage();
        
        separatorTL = new Timeline(this);  
        separatorTL.addPropertyToInterpolate("separatorHeight", 
                                                0, separator.getHeight(null));
        separatorTL.setDuration(500);
        
        leftWidthTL = new Timeline(this);  
        leftWidthTL.addPropertyToInterpolate("leftWidth", 
                                                0, leftSide.getWidth(null));
        leftWidthTL.addPropertyToInterpolate("leftX", 
                                              569, 569-leftSide.getWidth(null));
        leftWidthTL.setDuration(500);
        
        rightWidthTL = new Timeline(this);  
        rightWidthTL.addPropertyToInterpolate("rightWidth", 
                                                0, rightSide.getWidth(null));
        rightWidthTL.setDuration(500);
        
        leftXTL = new Timeline(this);  
        leftXTL.addPropertyToInterpolate("leftX", 
                                              569-leftSide.getWidth(null), 454);
        leftXTL.setDuration(100);
        
        rightXTL = new Timeline(this);  
        rightXTL.addPropertyToInterpolate("rightX", 572, 582);
        rightXTL.setDuration(100);
        
        this.repaintTimeline = repaintTimeline;
    }

    /**
     * Draw the logo using the current graphic context
     * @param g The current graphic context
     */
    public void drawLogo(Graphics g) {
        Graphics g2 = g.create();
        
        g2.translate(10, 0);
        
        g2.drawImage(separator, 570, 27, 571, 27+separatorHeight, 
                               0, 0, 1, separatorHeight, null);
        g2.drawImage(leftSide, 
                leftX, wordY, leftX+leftWidth, wordY+leftSide.getHeight(null), 
                0, 0, leftWidth, leftSide.getHeight(null), null);
        g2.drawImage(rightSide, 
                rightX, wordY, rightX+rightWidth, wordY+rightSide.getHeight(null), 
                rightSide.getWidth(null)-rightWidth, 0, 
                rightSide.getWidth(null), rightSide.getHeight(null), null);
    }
    
    public void playAnimation() {
        separatorTL.play();
    }
    
    protected void actAfterAnimation() {
        
    }
    
    public int getSeparatorHeight() {
        return separatorHeight;
    }

    public void setSeparatorHeight(int separatorHeight) {
        this.separatorHeight = separatorHeight;
//        repaintTimeline.forceRepaintOnNextPulse();
        
        if(this.separatorHeight == separator.getHeight(null)) {
            leftWidthTL.play();
            rightWidthTL.play();
        }
    }

    public int getLeftX() {
        return leftX;
    }

    public void setLeftX(int leftX) {
        this.leftX = leftX;
//        repaintTimeline.forceRepaintOnNextPulse();
    }

    public int getLeftWidth() {
        return leftWidth;
    }

    public void setLeftWidth(int leftWidth) {
        this.leftWidth = leftWidth;
//        repaintTimeline.forceRepaintOnNextPulse();
        
        if(this.leftWidth == leftSide.getWidth(null)) {
            leftXTL.play();
        }
    }

    public int getRightX() {
        return rightX;
    }

    public void setRightX(int rightX) {
        this.rightX = rightX;
//        repaintTimeline.forceRepaintOnNextPulse();
        
        if(this.rightX == 582) {
            actAfterAnimation();
        }
    }

    public int getRightWidth() {
        return rightWidth;
    }

    public void setRightWidth(int rightWidth) {
        this.rightWidth = rightWidth;
//        repaintTimeline.forceRepaintOnNextPulse();
        
        if(this.rightWidth == rightSide.getWidth(null)) {
            rightXTL.play();
        }
    }

    public int getWordY() {
        return wordY;
    }

    public void setWordY(int wordY) {
        this.wordY = wordY;
    }
    
}
