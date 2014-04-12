/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.listview;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import view.menubar.AbstractNormalMenu;
import view.roomproperties.RoomView;

/**
 *
 * @author HungHandsome
 */
public abstract class ListComponent {

    private RoomView room;
    private Image img;
    private Image imgHover;
    private Image imgPressed;
    private JPanel panel;
    private int cx; //The x of this component.
    private int cy; //The y of this component.
    private int cw; //The width of this component.
    private int ch; //The height of this component.
    private int transX;
    private boolean hover;
    private boolean press;

    public ListComponent(int cx, int cy, int transX, 
//    public ListComponent(int cx, int cy,
                                           Image i, Image iHover, RoomView rv) {
        this.cx = cx;
        this.cy = cy;
        this.transX = transX;
        room = rv;
        img = i;
        imgHover = iHover;
        cw = img.getWidth(null);
        ch = img.getHeight(null);
        panel = new JPanel();
        
        panel.setOpaque(false);
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                onClick();
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
//                System.out.println("Mouse enter component");
                hover = true;    
//                room.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
//                System.out.println("Mouse exit component");
                hover = false;
//                room.repaint();
            }
        });
//        panel.setBounds(cx + transX, cy, cw, ch);
        room.add(panel);
    }
    
    public ListComponent(int cx, int cy, int transX, Image iHover, RoomView rv) {
        this.cx = cx;
        this.cy = cy;
        this.transX = transX;
        room = rv;
        imgHover = iHover;
        cw = imgHover.getWidth(null);
        ch = imgHover.getHeight(null);       
        panel = new JPanel();
        
        panel.setOpaque(false);
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                onClick();
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
//                System.out.println("Mouse enter component");
            }

            @Override
            public void mouseExited(MouseEvent e) {
//                System.out.println("Mouse exit component");
            }
        });
//        panel.setBounds(cx + transX, cy, cw, ch);
        room.add(panel);
        
//        System.out.println("Content x:" + cx + " - y:" + cy);
    }
    
    public ListComponent(int cx, int cy, int transX, 
             Image i, Image iHover, Image iPressed, RoomView rv, boolean nav) {
        this.cx = cx;
        this.cy = cy;
        this.transX = transX;
        room = rv;
        img = i;
        imgHover = iHover;
        imgPressed = iPressed;
        cw = imgHover.getWidth(null);
        ch = imgHover.getHeight(null);
        panel = new JPanel();
        
        panel.setOpaque(false);
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                onClick();
                press = true;
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                press = false;
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                hover = true;    
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hover = false;
            }
        });
        room.add(panel);
    }
    
    public void drawLeft(Graphics g) {
        g.drawImage(img, cx, cy, null);
        if(hover) {
            g.drawImage(imgHover, cx, cy, null);
        }
        if(press) {
            g.drawImage(imgPressed, cx, cy, null);
        }
        panel.setBounds(cx + transX, cy, cw, ch);
    }
    
    public void drawRight(Graphics g) {
        if(hover) {
            g.drawImage(imgHover, cx - 2, cy, null);
        }
        if(press) {
            g.drawImage(imgPressed, cx - 2, cy, null);
        }
        panel.setBounds(cx + transX, cy, cw, ch);
    }

    public void drawComponent(Graphics g) {
        if(hover) {
            g.drawImage(imgHover, cx, cy, null);
        }
        else {
            g.drawImage(img, cx, cy, null);
        }        
        panel.setBounds(cx + transX, cy, cw, ch);
    }
    
    public void drawComponent(Graphics g, int cx, int cy) {
        if(hover) {
            g.drawImage(imgHover, cx+2, cy, null);
        }
        else {
            g.drawImage(img, cx, cy, null);
        }        
        panel.setBounds(cx, cy, cw, ch);
    }
    
    protected abstract void onClick();

    public JPanel getPanel() {
        return panel;
    }
    
    public int getCx() {
        return cx;
    }

    public int getCy() {
        return cy;
    }

    public int getCw() {
        return cw;
    }

    public int getCh() {
        return ch;
    }

    public boolean isHover() {
        return hover;
    }

    public void setHover(boolean hover) {
        this.hover = hover;
    }
    
}
