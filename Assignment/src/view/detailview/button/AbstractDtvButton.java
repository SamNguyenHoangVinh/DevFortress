/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.detailview.button;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import view.MainFrame;
import view.roomproperties.RoomView;

/**
 *
 * @author Hung
 */
public abstract class AbstractDtvButton {
   
    protected JPanel panel;
    protected boolean hover;
    protected int x;
    protected int y;
//    private int transX;
    protected int width;
    protected int height;

    public AbstractDtvButton() {
        panel = new JPanel();
        
        panel.setOpaque(false);
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                onClick();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                onHover(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                onHover(false);
            }
            
        });
    }
    
    protected void onGeneralDraw(Graphics g, int transX) {        
        RoomView r = (RoomView)(MainFrame.getInstance().getCurrPanel());
        if(hover) {
            g.setColor(Color.WHITE);
            g.fillRect(x, y, width, height);
            g.setColor(Color.BLACK);
        }
        else if(r.getDetailPanel().isBtnsDisabled()) {
            g.setColor(Color.GRAY);
        }
        
        panel.setBounds(x+transX, y, width, height);
        g.drawRect(x, y, width, height);
    }
    
    private void onHover(boolean hover) {
        RoomView r = (RoomView)(MainFrame.getInstance().getCurrPanel());
        if (r.getDetailPanel().isBtnsDisabled()) {
            return;
        }
        this.hover = hover;
//        r.repaint(panel.getBounds());
    }
    
    /**
     * Indicates the event which happens when the button is clicked.
     */
    protected abstract void onClick();
}
