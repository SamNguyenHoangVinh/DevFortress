/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.popup;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import view.FontUtilities;
import view.MainFrame;

/**
 * This class represents a button on a popup dialog. Although it is an abstract
 * <br/>class, there's no need to derive it. However, the onButtonClicked()<br/>
 * method must be implemented.
 * @author HungHandsome
 */
public abstract class PopupButton {
    
    private final int BUTTON_WIDTH = 100;
    private final int BUTTON_HEIGHT = 30;
//    private final int TEXT_MARGIN = 20;
    
    private JPanel panel;
    private String text;
    private boolean hover;
    private int bX;
    private int bY;
    private int transY;
    private int bWidth;
//    private int bHeight;
    
    public PopupButton(String text, int x, int y) {
        this.text = text;
        bX = x;
        bY = y;
        panel = new JPanel();
        
        panel.setOpaque(false);
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                onButtonClicked();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                hover = true;
                MainFrame.getInstance().getCurrPanel()
                                       .repaint(panel.getBounds());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hover = false;
                MainFrame.getInstance().getCurrPanel()
                                       .repaint(panel.getBounds());
            }            
        });
        
//        MainFrame.getInstance().getCurrPanel().add(panel);
    }
    
    protected abstract void onButtonClicked();
    
    public void drawButton(Graphics g) {   
        Graphics g2 = g.create();
        
        g2.translate(0, transY);
        g2.setFont(FontUtilities.SEGOE_UI_SEMIBOLD_17);
        
        int textX = (BUTTON_WIDTH - g2.getFontMetrics().stringWidth(text)) / 2;
        
        if (hover) {
            g2.setColor(Color.gray);
            g2.fillRect(bX, bY, BUTTON_WIDTH, BUTTON_HEIGHT);
        }
        
        g2.setColor(Color.white);
        panel.setBounds(bX, bY+transY, BUTTON_WIDTH, BUTTON_HEIGHT);
        g2.drawRect(bX, bY, BUTTON_WIDTH, BUTTON_HEIGHT);
        g2.drawString(text, bX + textX, bY + 21);
    }

    public JPanel getPanel() {
        return panel;
    }

    public int getbY() {
        return bY;
    }

    public void setbY(int bY) {
        this.bY = bY;
    }

    public int getTransY() {
        return transY;
    }

    public void setTransY(int transY) {
        this.transY = transY;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
}