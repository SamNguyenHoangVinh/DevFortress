/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.popup;

import animation.ScreenCapturer;
import java.awt.*;
import model.saveload.SaveLoad;
import view.FontUtilities;
import view.MainFrame;
import view.Sound;
import view.SoundUtilities;

/**
 *
 * @author HungHandsome
 */
public class GameOverPopup extends AbstractPopup {
    
    private String reason;
    private String reasons[];
    private PopupButton ok;

    public GameOverPopup(String reason) {
        
        Sound.stopBGMX();
        SoundUtilities.GameOver();
        
        this.reason = reason;
        reasons = reason.split("\n");
        ok = new PopupButton("Ok", 750, 320) {
            @Override
            protected void onButtonClicked() {                
//                MainFrame.getInstance().fromIntroToMainMenu();
                MainFrame.getInstance().showHighScorePanel();
            }
        };
        
        add(ok.getPanel());
//        ScreenCapturer.captureScreen(MainFrame.getInstance());
    }
   
    @Override
    protected void drawContent(Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        
        g.drawString("Game Over", 
                (MainFrame.PANEL_WIDTH - fm.stringWidth("Game Over")) / 2, 280);
                
        g.setFont(FontUtilities.SEGOE_UI_17);
        fm = g.getFontMetrics();
        
        for(int i=0, ry=307; i<reasons.length; i++) {
            int rx = (MainFrame.PANEL_WIDTH - fm.stringWidth(reasons[i])) / 2;
            g.drawString(reasons[i], rx, ry);
            ry += 19;
        }
        
        ok.drawButton(g);
    }
}