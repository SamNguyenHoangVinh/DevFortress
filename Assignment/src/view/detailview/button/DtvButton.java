/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.detailview.button;

import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;
import view.FontUtilities;

/**
 * Detail View Button. Although this is an abstract class, there's no need <br/>
 * to derive it. However, the <code>onClick()</code> method must be override, 
 * <br/>either in a subclass or anonymous class.
 * @author hung
 */
public abstract class DtvButton extends AbstractDtvButton {
    
    public static final int MIN_WIDTH = 100;
    public static final int MIN_TEXT_WIDTH = 80;
    public static final int HEIGHT = 30;
    public static final int Y = 500; //Default y
    public static final int TEXT_Y = 521; //Default text y
    public static final Font FONT = FontUtilities.SEGOE_UI_SEMIBOLD_17;
    
    private String text;
    private int textY;

    public DtvButton(String text, int x) {
        this.text = text;
        this.x = x;
        y = Y;
        textY = TEXT_Y;
        height = HEIGHT;
    }

    public DtvButton(String text, int x, int y, int textY, int h) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.textY = textY;
        this.height = h;
    }
    
    /**
     * Draw the detail button using the current graphic context and default<br/>
     * width.
     * @param g The current graphic context
     */
    public void drawButton(Graphics g, int transX) {
        g.setFont(FONT);
        
        int textWidth = g.getFontMetrics().stringWidth(text);
        width = (textWidth > MIN_TEXT_WIDTH) ? (textWidth+20) : MIN_WIDTH;
        int textX = (width - textWidth) / 2 + x;
        
        onGeneralDraw(g, transX);
        g.drawString(text, textX, textY);
    }

    public JPanel getPanel() {
        return panel;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isHover() {
        return hover;
    }

    public void setHover(boolean hover) {
        this.hover = hover;
    }
    
}