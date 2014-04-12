/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.mainmenu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import view.MainFrame;
import view.SoundUtilities;

/**
 *
 * @author HungHandsome
 */
public abstract class MainMenuPanel extends JPanel {
    
    public static final int BTN_X = 80; //The x of the first menu button
    
    protected Image img;
    protected MainMenuButton[] menuBtns;
    protected int prevMousePos = -1; //Previous mouse position. 0 means nothing.
    protected boolean btnDisabled;

    public MainMenuPanel() {
        setLayout(null);
        setBackground(Color.BLACK);
        setPreferredSize(
                new Dimension(MainFrame.PANEL_WIDTH, MainFrame.PANEL_HEIGHT));
    }
    
    /**
     * Add mouse move listener
     */
    protected void initMouseMove() {
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if(btnDisabled) {
                    return;
                }
                
                int x = e.getX();
                int y = e.getY();
                int pos = findCurrMousePosition(x, y);
                
                if(prevMousePos==-1 && pos!=-1) {
                    SoundUtilities.Click1();
                    menuBtns[pos].moveToRight();
                }
                else if((prevMousePos!=-1 && pos==-1)
                        || (prevMousePos!=-1 && pos!=-1 && prevMousePos!=pos)) {
                    menuBtns[prevMousePos].moveToLeft();
//                    SoundUtilities.Click1();
                }
                
                prevMousePos = pos;
            }            
        });
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
    }   
    
    /**
     * Determine whether or not the mouse is in a button. If the mouse is <br/>
     * in a button, then determine which button the mouse is in.
     * @param x The current mouse x coordinate.
     * @param y The current mouse y coordinate.
     * @return The index of the current button, or -1 if the mouse is not in<br/>
     * a button.
     */
    protected int findCurrMousePosition(int x, int y) {
        for(int i=0; i<menuBtns.length; i++) {
            if(x>=menuBtns[i].getBtnX() 
                    && x<=menuBtns[i].getBtnX()+MainMenuButton.BTN_WIDTH
                    && y>=menuBtns[i].getBtnY() 
                    && y<=menuBtns[i].getBtnY()+MainMenuButton.BTN_HEIGHT) {
                return i;
            }
        }
        return -1;
    }
    
    public void moveBtnsOut() {
        for(MainMenuButton b : menuBtns) {
            b.moveOut();
            try {
                Thread.currentThread().sleep(100);
            } catch(Exception ex) {
                
            }
        }
    }
    
    public void moveBtnsIn() {
        for(MainMenuButton btn : menuBtns) {
            btn.moveIn();
        }
    }

    public boolean isBtnDisabled() {
        return btnDisabled;
    }

    public void setBtnDisabled(boolean btnDisabled) {
        this.btnDisabled = btnDisabled;
    }
    
}
