/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.menubar;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import view.SoundUtilities;

/**
 *
 * @author s3342128
 */
public abstract class AbstractBtn {

    public static final int DEFAULT_HEIGHT = 18;
    
    protected Image img;
    protected JPanel buttonPanel;
    protected int width;
    protected int height;
    protected int x = 50;
    protected int y;
    protected int ySmall;
    protected boolean hover;
    protected boolean press;
    
    public AbstractBtn() {
        buttonPanel = new JPanel();
        
        buttonPanel.setOpaque(false);
//        buttonPanel.setPreferredSize(
//                new Dimension(GameMenuBar.MENU_BAR_WIDTH, height));
//        buttonPanel.setBounds(0, y-10, 
//                              AbstractGameMenu.MENU_BAR_WIDTH, height + 20);
        
        buttonPanel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
//                System.out.println("ENTER BUTTON");
                hover = true;    
SoundUtilities.Click2();                
//                buttonPanel.setOpaque(hover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
//                System.out.println("EXIT BUTTON");
                if(press) {
                    hover = true;  
                } else {
                    hover = false;
                }
//                buttonPanel.setOpaque(hover);
            }
        });
    }
    
    /**
     * Draw the menu button using the current graphic context
     * @param g The current graphic context
     */
    public void drawButton(Graphics g) {
        drawHover(g);
        
        drawProperties(g);
    }

    private void drawHover(Graphics g) {
        if(hover) {
            Color c1 = g.getColor();
            Color black = Color.BLACK;
            Color c2 = new Color(black.getRed(), black.getGreen(), 
                                                    black.getBlue(), 80);
            g.setColor(c2);
            g.fillRect(0, y-10, NormalMenuBar.MENU_BAR_WIDTH, height + 20);
        }
    }
    
    protected abstract void drawProperties(Graphics g);
    
    
    public void drawSmallButton(Graphics g){
        drawSmallHover(g);
        drawSmallProperties(g);
    }
    
    private void drawSmallHover(Graphics g){
        if(hover) {
            Color c1 = g.getColor();
            Color black = Color.BLACK;
            Color c2 = new Color(black.getRed(), black.getGreen(), 
                                                    black.getBlue(), 80);
            g.setColor(c2);
            g.fillRect(0, ySmall-10, NormalMenuBar.MENU_BAR_WIDTH, height + 12);
        }
    }
    
    protected abstract void drawSmallProperties(Graphics g);
    
    public JPanel getButtonPanel() {
        return buttonPanel;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getY() {
        return y;
    }

    public AbstractBtn setY(int y) {
        this.y = y;
        buttonPanel.setBounds(0, y-10, 
                             AbstractNormalMenu.MENU_BAR_WIDTH, height + 20);
        return this;
    }
    
    public int getYSmall(){
        return ySmall;
    }
    
    public AbstractBtn setYSmall(int ySmall){
        this.ySmall = ySmall;
        buttonPanel.setBounds(0, ySmall-10, 
                             AbstractNormalMenu.MENU_BAR_WIDTH, height + 15);
        return this;
    }

    public int getX() {
        return x;
    }

    public AbstractBtn setX(int x) {
        this.x = x;
//        buttonPanel.setBounds(x, y-10, 
//                             AbstractGameMenu.MENU_BAR_WIDTH, height + 20);
        return this;
    }
    
    public boolean isHover() {
        return hover;
    }

    public void setHover(boolean hover) {
        this.hover = hover;
    }
    
    public boolean isPressed() {
        return press;
    }
    
    public void setPressed(boolean press, Graphics g) {
        this.press = press;
        if(press) {
            hover = true;
        } else {
            hover = false;
        }
        drawHover(g);
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }
    
}
