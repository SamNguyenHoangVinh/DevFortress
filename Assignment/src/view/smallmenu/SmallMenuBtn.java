/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.smallmenu;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

/**
 *
 * @author Hung
 */
public abstract class SmallMenuBtn {
    
    private Image img;
    private JPanel panel;

    public SmallMenuBtn(Image img) {
        this.img = img;
        panel = new JPanel();
        
        panel.setOpaque(false);
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                onClick();
            }            
        });
    }
    
    public void drawBtn(Graphics g, int x, int y) {
        g.drawImage(img, x, y, null);
        panel.setBounds(x, y, SmallMenu.BTN_WIDTH, SmallMenu.BTN_HEIGHT);
    }
    
    protected abstract void onClick();

    public JPanel getPanel() {
        return panel;
    }
    
}
