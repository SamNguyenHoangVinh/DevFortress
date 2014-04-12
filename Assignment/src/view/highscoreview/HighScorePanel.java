/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.highscoreview;

import animation.ScreenCapturer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;
import model.player.Player;
import model.player.PlayerInfo;
import model.player.PlayerRank;
import model.saveload.SaveLoad;
import org.pushingpixels.trident.Timeline;
import org.pushingpixels.trident.swing.SwingRepaintTimeline;
import view.FontUtilities;
import view.MainFrame;
import view.ViewUtilities;
import view.popup.PopupButton;

/**
 *
 * @author HungHandsome
 */
public class HighScorePanel extends JPanel {

    // 110 - 138    
    public static final int BASE_Y = 154; // 138
    public static final int RANK_X = 110;
    public static final int NAME_X = 402; // 326
    public static final int DURATION_X = 694; // 542
//    public static final int CASH_X = 110;
    public static final int GAP_Y = 33;
    
    private SwingRepaintTimeline repaintTimeline;
    private Image lowerImg;
    private Timeline timeline;
    private PopupButton ok;
    private int panelY;
    private boolean onTop;
    
    public HighScorePanel() {
        panelY = MainFrame.PANEL_HEIGHT;
        lowerImg = ScreenCapturer.getScreenShot();
        
        timeline = new Timeline(this);
        timeline.addPropertyToInterpolate("panelY", MainFrame.PANEL_HEIGHT, 0);
//        timeline.setDuration(500);
        
        repaintTimeline = new SwingRepaintTimeline(this);
        repaintTimeline.setAutoRepaintMode(false);
        repaintTimeline.playLoop(Timeline.RepeatBehavior.LOOP);
        
        onTop = PlayerRank.getInstance().checkOnTop(
                                            Player.getInstance().getWeekNum());
        
        ok = new PopupButton("Ok", 836, 490) {
            @Override
            protected void onButtonClicked() {
                MainFrame.getInstance().fromIntroToMainMenu();
            }
        };

        add(ok.getPanel());
        
        setLayout(null);
        setPreferredSize(
                new Dimension(MainFrame.PANEL_WIDTH, MainFrame.PANEL_HEIGHT));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = FontUtilities.renderer(g);
        
//        g2.setFont(FontUtilities.ROBOTO_THIN_12);
        g2.setFont(FontUtilities.loadFont(FontUtilities.ROBOTO_THIN_PATH, 17));
        g2.setColor(Color.white);
        
        if(panelY > 0) {
            g2.drawImage(lowerImg, 0, 0, getWidth(), getHeight(), null);
        }
        
        g2.drawImage(ViewUtilities.HIGHSCORE_PANEL, 0, panelY, 
                getWidth(), getHeight(), null);
        
        int i = 1;
        int lineY = BASE_Y;
        for(PlayerInfo p : PlayerRank.getInstance().getPlayers()) {
            g.drawString("" + i, RANK_X, lineY+panelY);
            g.drawString(p.getName(), NAME_X, lineY+panelY);
            g.drawString(p.getWeekNum() + " W", DURATION_X, lineY+panelY);
            
            lineY += GAP_Y;
            i++;
        }
        
        ok.drawButton(g);
    }
    
    public void playAnimation() {
        timeline.replay();
        
        if(onTop) {
            Timer t = new Timer(650, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    MainFrame.getInstance().switchPanel(new InputNamePopup());
                }
            });
            t.setRepeats(false);
            t.start();
        }
    }

    public int getPanelY() {
        return panelY;
    }

    public void setPanelY(int panelY) {
        this.panelY = panelY;
        ok.setTransY(panelY);
        repaintTimeline.forceRepaintOnNextPulse();
    }
    
}
