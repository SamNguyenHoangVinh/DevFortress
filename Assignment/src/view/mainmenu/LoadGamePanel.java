/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.mainmenu;

import animation.ScreenCapturer;
import java.awt.Graphics;
import view.MainFrame;

/**
 *
 * @author HungHandsome
 */
public class LoadGamePanel extends MainMenuPanel {

    public LoadGamePanel() {
        img = ScreenCapturer.captureScreen(MainFrame.getInstance());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
       
    private void cancelClicked() {
        MainFrame.getInstance().switchBack();
    }
}
