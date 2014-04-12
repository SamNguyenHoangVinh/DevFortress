/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import animation.IntroducingAnimation;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * 
 * @author Hung
 */
public class WorldView extends JPanel {
    
    private JPanel currPanel;
    
    public WorldView() {
        setLayout(new BorderLayout());
        
//        currPanel = new IntroducingAnimation(this);
        
        add(currPanel);
        setPreferredSize(
                new Dimension(MainFrame.PANEL_WIDTH, MainFrame.PANEL_HEIGHT));
    }
    
    public void toMainMenu() {
//        MainMenu menu = new MainMenu();
//        setCurrPanel(menu);
    }

    public JPanel getCurrPanel() {
        return currPanel;
    }

    public void setCurrPanel(JPanel currPanel) {
        remove(this.currPanel);
//        nextPanel = curPanel;
        this.currPanel = currPanel;
        add(this.currPanel);
//        invalidate();
        validate();
        this.currPanel.updateUI();
//        currPanel.requestFocus();
//        updateUI();
    }
    
}
