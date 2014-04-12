/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package animation;

import java.awt.*;
import java.awt.event.*;
import javax.sound.midi.Soundbank;
import javax.swing.*;
import org.pushingpixels.trident.Timeline;
import org.pushingpixels.trident.swing.SwingRepaintTimeline;
import view.MainFrame;
import view.ViewUtilities;

/**
 *
 * @author Hung
 */
public class IntroducingAnimation extends JPanel {
    
    private DarkToLightPanel overlayPanel;
    private Image img;
    private boolean clicked;

    public IntroducingAnimation() {
        setLayout(null);
        setBackground(Color.BLACK);
        
        img = new ImageIcon(ViewUtilities.BACKGROUND_IMG).getImage();
        
        SwingRepaintTimeline repaintTimeline = new SwingRepaintTimeline(this);
        repaintTimeline.setAutoRepaintMode(true);
        repaintTimeline.playLoop(Timeline.RepeatBehavior.LOOP);

        overlayPanel = new DarkToLightPanel(repaintTimeline, 
                                                          Color.BLACK, 255, 0);       
        
//        addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseReleased(MouseEvent e) {
//                if(!clicked) {
//                    clicked = true;
//                    overlayPanel.playAnimation();
//                }               
//            }
//        });
        
        setPreferredSize(
                new Dimension(MainFrame.PANEL_WIDTH, MainFrame.PANEL_HEIGHT));
        
//        overlayPanel.playAnimation();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(clicked) {
//            Graphics2D g2d = (Graphics2D) g.create();
////            g2d.setColor(Color.black);
////            g2d.fillRect(0, 0, getWidth(), getHeight());            
//            
            Color backgr = overlayPanel.getBackgroundColor();
//            if (!Color.black.equals(backgr)) {
//                g2d.setColor(backgr);
//                g2d.drawImage(img, 0, 0, 
////                         MainFrame.PANEL_WIDTH, MainFrame.PANEL_HEIGHT, null);
//                        getWidth(), getHeight(), null);
//                g2d.fillRect(0, 0, getWidth(), getHeight());
//            }                      
//            
//            g2d.dispose();
            
            g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
            overlayPanel.drawPanel(g, getWidth(), getHeight());
            
//            if(backgr.getAlpha() == 0) {
//                MainFrame.getInstance().toMainMenu();
//            }
        }
    }
    
    public void playAnimation() {
//        System.out.println("Start animation");
        clicked = true;
        overlayPanel.playAnimation();
    }

    public DarkToLightPanel getOverlayPanel() {
        return overlayPanel;
    }
    
}
