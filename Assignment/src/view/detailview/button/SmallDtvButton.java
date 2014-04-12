/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.detailview.button;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import view.ViewUtilities;

/**
 *
 * @author Hung
 */
public abstract class SmallDtvButton extends AbstractDtvButton {
    
    private static final Image PLUS = 
                        new ImageIcon(ViewUtilities.PLUS_IMG_PATH).getImage();
    private static final Image MINUS = 
                        new ImageIcon(ViewUtilities.MINUS_IMG_PATH).getImage();
    
    private Image img;    

    public SmallDtvButton(Image symbol, int x, int y, int w, int h) {
        this.img = symbol;
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }
    
    /**
     * Draw the detail button using the current graphic context and default<br/>
     * width.
     * @param g The current graphic context
     */
    public void drawButton(Graphics g, int transX) {
        onGeneralDraw(g, transX);
        g.drawImage(PLUS, x + 5, y + 5, null);
    }
    
//    private void drawButton(Graphics g) {
//        if (hover) {
//            g.setColor(Color.WHITE);
//            g.fillRect(ACCEPT_X - 50, BUTTON_Y, BUTTON_HEIGHT + 1, BUTTON_HEIGHT);
//            g.setColor(Color.BLACK);
//        }
//        panel.setBounds(ACCEPT_X - 50 + transX, BUTTON_Y,
//                BUTTON_HEIGHT + 1, BUTTON_HEIGHT);
//        g.drawRect(ACCEPT_X - 50, BUTTON_Y, BUTTON_HEIGHT + 1, BUTTON_HEIGHT);
//        g.drawImage(PLUS, ACCEPT_X - 50 + 5, BUTTON_Y + 5, null);
//    }
}
