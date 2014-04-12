/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package animation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import view.MainFrame;

/**
 *
 * @author HungHandsome
 */
public class MetroStarter extends JPanel {

    public MetroStarter() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
//                System.out.println("Mouse Enter");
                MainFrame.getInstance()
                         .switchPanel(AnimationUtils.getMetroAnim(e.getY()));
            }

            @Override
            public void mouseExited(MouseEvent e) {
//                System.out.println("Mouse Exit");
                MainFrame.getInstance().switchBack();
            }
        });
        
//        setBackground(Color.red);
        setPreferredSize(new Dimension(50, 50));
        setBounds(550, 0, 50, 50);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        g.setColor(Color.white);
        g.drawString("Drag it", 5, 30);
    }
}
