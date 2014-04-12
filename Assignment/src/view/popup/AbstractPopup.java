/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.popup;

import animation.ScreenCapturer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import view.FontUtilities;
import view.MainFrame;
import view.ViewUtilities;

/**
 *
 * @author s3342128
 */
public abstract class AbstractPopup extends JPanel {

    public AbstractPopup() {
        ScreenCapturer.captureScreen(MainFrame.getInstance());
        setLayout(null);
        setPreferredSize(
                new Dimension(MainFrame.PANEL_WIDTH, MainFrame.PANEL_HEIGHT));
    }
    
    @Override
    public void paintComponent(Graphics g) {
        Graphics g2 = FontUtilities.renderer(g);
        
        g2.drawImage(ScreenCapturer.getScreenShot(), 0, 0, null);
        g2.drawImage(ViewUtilities.DIALOG_IMG, 0, 0, 
                                               getWidth(), getHeight(), null);
        
        g2.setColor(Color.white);        
        g.setFont(FontUtilities.loadFont(FontUtilities.ROBOTO_THIN_PATH, 90));
        
        drawContent(g);
    }
    
    protected abstract void drawContent(Graphics g);
}