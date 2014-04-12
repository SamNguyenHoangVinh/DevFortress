/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package animation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.pushingpixels.trident.Timeline;
import org.pushingpixels.trident.swing.SwingRepaintTimeline;
import view.MainFrame;
import view.SoundIntro;
import view.ViewUtilities;

/**
 *
 * @author benjamin
 */
public class Introduction extends JPanel implements ViewUtilities {

    private DarkToLightPanel overlayPanel;
    private Image layout;
    private Image cyberTech;
    private Image entertain;
    private Image proudly;
    private Image present;
    private Image dev;
    private Image seperate;
    private Image fortress;
    private Image background;
    private boolean start;
    private boolean fistSignal;
    private boolean secondSignal;
    private boolean thirdSignal;
    private boolean end;
    private int xCyber, yCyber;
    private int xEn, yEn;
    private int xPro, yPro;
    private int xPre, yPre;
    private int xDev, yDev;
    private int xSep, ySep;
    private int xFor, yFor;
    private Timeline timeline;
    private Timeline timeline2;
    private Timeline timeline3;

    public Introduction() {
        setBackground(Color.BLACK);
        
        layout = new ImageIcon(LAYOUT_IMG_PATH).getImage();
        cyberTech = new ImageIcon(CYBERTECH_IMG_PATH).getImage();
        entertain = new ImageIcon(ENTERTAIN_IMG_PATH).getImage();
        proudly = new ImageIcon(PROUDLY_IMG_PATH).getImage();
        present = new ImageIcon(PRESENT_IMG_PATH).getImage();
        dev = new ImageIcon(DEVELOP_IMG_PATH).getImage();
        seperate = new ImageIcon(SEPERATE_IMG_PATH).getImage();
        fortress = new ImageIcon(FORTRESS_IMG_PATH).getImage();
        background = new ImageIcon(BACKGROUND_IMG).getImage();

        SwingRepaintTimeline repaintTimeline = new SwingRepaintTimeline(this);
        repaintTimeline.setAutoRepaintMode(true);
        repaintTimeline.playLoop(Timeline.RepeatBehavior.LOOP);

        xCyber = 50; yCyber = 220;
        xEn = 200; yEn = 280;
        xPro = 50; yPro = 200;
        xPre = 400; yPre = 300;
        xDev = 130; yDev = 100;
        xSep = 370; ySep = 120;
        xFor = 410; yFor = 300;

        timeline = new Timeline(this);
        timeline.addPropertyToInterpolate("yCyber", 220, 100);
        timeline.addPropertyToInterpolate("yEn", 280, 400);
        timeline.setDuration(6000);

        timeline2 = new Timeline(this);
        timeline2.addPropertyToInterpolate("xPro", 50, 250);
        timeline2.addPropertyToInterpolate("xPre", 400, 250);
        timeline2.setDuration(6000);
        
        timeline3 = new Timeline(this);
        timeline3.addPropertyToInterpolate("yDev", 100, 300);
        timeline3.addPropertyToInterpolate("yFor", 300, 100);
        timeline3.setDuration(6000);

        overlayPanel = new DarkToLightPanel(repaintTimeline,
                Color.BLACK, 255, 0);
        overlayPanel.getTimeLine().setDuration(2200);
        
        setPreferredSize(
                new Dimension(MainFrame.PANEL_WIDTH, MainFrame.PANEL_HEIGHT));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (start) {
            Color backgr = overlayPanel.getBackgroundColor();

            g.drawImage(layout, 0, 0, getWidth(), getHeight(), null);
            g.drawImage(cyberTech, xCyber, yCyber, null);
            g.drawImage(entertain, xEn, yEn, null);
            overlayPanel.drawPanel(g, getWidth(), getHeight());

            if (backgr.getAlpha() == 0) {
                overlayPanel.reverseAnimation();
                fistSignal = true;
            } else if (fistSignal && backgr.getAlpha() == 255) {
                start = false;
                secondSignal = true;
            }
        } else if (secondSignal) {
            Color backgr = overlayPanel.getBackgroundColor();
            
            if (!thirdSignal && backgr.getAlpha() == 255) {
                timeline2.replay();
                overlayPanel.playAnimation();
            } else if(backgr.getAlpha() == 0) {
                overlayPanel.reverseAnimation();
                thirdSignal = true;
            } else if(thirdSignal && backgr.getAlpha() == 255) {
                secondSignal = false;
            }

            g.drawImage(layout, 0, 0, getWidth(), getHeight(), null);
            g.drawImage(proudly, xPro, yPro, null);
            g.drawImage(present, xPre, yPre, null);
            overlayPanel.drawPanel(g, getWidth(), getHeight());
        } else if(thirdSignal) {
            Color backgr = overlayPanel.getBackgroundColor();
            
            if(!end && backgr.getAlpha() == 255) {
                timeline3.replay();
                overlayPanel.playAnimation();
            } else if(backgr.getAlpha() == 0) {
                overlayPanel.reverseAnimation();
                end = true;
            } else if(end && backgr.getAlpha() == 255) {
                thirdSignal = false;
            }
            
            g.drawImage(layout, 0, 0, getWidth(), getHeight(), null);
            g.drawImage(dev, xDev, yDev, null);
            g.drawImage(seperate, xSep, ySep, null);
            g.drawImage(fortress, xFor, yFor, null);
            overlayPanel.drawPanel(g, getWidth(), getHeight());
        } else if(end) {
            Color backgr = overlayPanel.getBackgroundColor();
            
            if(backgr.getAlpha() == 255) {
                overlayPanel.playAnimation();
            }
            
            g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
            overlayPanel.drawPanel(g, getWidth(), getHeight());
            
            if(backgr.getAlpha() == 0) {
                MainFrame.getInstance().toMainMenu();
            }
        }
    }

    public void playAnimation() {
        start = true;
        overlayPanel.playAnimation();
        timeline.replay();
    }

    public DarkToLightPanel getOverlayPanel() {
        return overlayPanel;
    }

    public void setYCyber(int yCyber) {
        this.yCyber = yCyber;
    }

    public void setYEn(int yEn) {
        this.yEn = yEn;
    }

    public void setXPro(int xPro) {
        this.xPro = xPro;
    }

    public void setXPre(int xPre) {
        this.xPre = xPre;
    }
    
    public void setYDev(int yDev) {
        this.yDev = yDev;
    }
    
    public void setYFor(int yFor) {
        this.yFor = yFor;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        final Introduction intro = new Introduction();

        frame.add(intro);
        intro.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                intro.playAnimation();
                SoundIntro.playWAV();
            }
        });

        frame.setSize(960, 540);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
