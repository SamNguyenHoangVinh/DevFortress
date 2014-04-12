/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.smallmenu;

import animation.AlphaToZeroPanel;
import animation.ScreenCapturer;
import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.JPanel;
import model.player.Player;
import model.saveload.SaveLoad;
import view.MainFrame;
import view.Sound;
import view.ViewUtilities;
import view.mainmenu.OptionGamePanel;

/**
 *
 * @author Hung
 */
public class SmallMenu extends JPanel {

    public static final int X = 400;
    public static final int Y = 70;
    public static final int MENU_WIDTH = 300;
    public static final int MENU_HEIGHT = 165;
    public static final int BTN_X = X + 17;
    public static final int BTN_Y = Y + 10;
    public static final int BTN_GAP = 5;
    public static final int BTN_WIDTH = 266;
    public static final int BTN_HEIGHT = 45;
    
    private Image bg;
    private SmallMenuBtn[] btns;
    private AlphaToZeroPanel alpha;
    private JPanel temp;
    
    public SmallMenu() {
        bg = ScreenCapturer.captureScreen(MainFrame.getInstance());
        btns = new SmallMenuBtn[3];
        alpha = new AlphaToZeroPanel(null);
        final MainFrame mf = MainFrame.getInstance();
        
        alpha.setAlpha(0.5f);
        
        setLayout(null);
        setPreferredSize(
                new Dimension(MainFrame.PANEL_WIDTH, MainFrame.PANEL_HEIGHT));
        
        btns[0] = new SmallMenuBtn(ViewUtilities.SAVE_BTN) {
            @Override
            protected void onClick() {                
//                if(temp == null) {
//                    temp = mf.getTemp();
//                }
//                mf.switchPanel(new SavePopup());
                SaveLoad.save(Player.getInstance(), SaveLoad.SAVE_PATH_1);
                mf.switchBack();
            }
        };
        
        btns[1] = new SmallMenuBtn(ViewUtilities.RETURN_BTN) {
            @Override
            protected void onClick() {
//                if(temp != null) {
//                    mf.setTemp(temp);
//                }
                mf.switchBack();
            }
        };
        
        btns[2] = new SmallMenuBtn(ViewUtilities.QUIT_BTN) {
            @Override
            protected void onClick() {
                mf.fromIntroToMainMenu();
                Sound.stopBGMX();
                OptionGamePanel.DEFAULT=true;
            }
        };
        
        for(SmallMenuBtn b : btns) {
            add(b.getPanel());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)(g.create());
        g2.setComposite(alpha.makeComposite());
        g2.drawImage(bg, getWidth(), getHeight(), null);
        
        g.fillRect(X, Y, MENU_WIDTH, MENU_HEIGHT);
        
        for(int i=0, by=BTN_Y; i<btns.length; i++) {
            btns[i].drawBtn(g, BTN_X, by);
            by += BTN_HEIGHT + BTN_GAP;
        }
    }
    
    
}
