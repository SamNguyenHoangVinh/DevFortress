/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.menubar;

import java.awt.*;

/**
 * The button of the menu bar which appears when the game is being played
 * @author HungHandsome
 */
public class NormalMenuButton extends AbstractBtn {
            
    public NormalMenuButton(Image i) {
        img = i;
//        y = by;
        width = i.getWidth(null);
        height = i.getHeight(null);        
    }   

    @Override
    protected void drawProperties(Graphics g) {
        g.drawImage(img, x, y, null);
    }    

    @Override
    protected void drawSmallProperties(Graphics g) {
    }
}
