/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.menubar;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import view.SoundUtilities;
import view.ViewUtilities;
import view.roomproperties.RoomView;

/**
 *
 * @author s3342128
 */
public abstract class AbstractNormalMenu extends AbsMenu {
    
    public static final int BACK_BUTTON = -1;
    public static final int BACK_BUTTON_X = 15;
    public static final int BACK_BUTTON_Y = 170; // 20
    public static final int BASIC_INFO_Y = 15;
    
    public static final int NORMAL_MENU = 1;
    public static final int LIST_MENU = 2;
        
    protected JPanel backBtnPanel;
    protected Image backImg;
    protected Image menuTitleImg;
    protected ArrayList<AbstractBtn> buttons;
    
    protected int clickedButton = -2;
    protected int menuTitleX = 10;
    
    protected int menuType;
    
    public AbstractNormalMenu(RoomView r, String menuTitleImgPath, final int menuType) {
        super(r);
        this.menuType = menuType;
        menuBarImg = ViewUtilities.MENU_BAR_IMG;
        menuTitleImg = new ImageIcon(menuTitleImgPath).getImage();
        
        backBtnPanel = new JPanel();
        backBtnPanel.setOpaque(false);
        
        timeLine.addPropertyToInterpolate("x", 0, -MENU_BAR_WIDTH);        
        
        backBtnPanel.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//                System.out.println("CLICK BUTTON");
////                System.exit(0);
////                timeLine.replay();
//                if(menuType == LIST_MENU) {
//                    backImg = ViewUtilities.BACK_PRESS_BTN_IMG;
//                } else if(menuType == NORMAL_MENU) {
//                    backImg = ViewUtilities.PLAY_PRESS_BTN_IMG;
//                }
//            }
            @Override
            public void mouseReleased(MouseEvent e) {
SoundUtilities.Back();                
                System.out.println("CLICK BUTTON");
//                System.exit(0);
//                timeLine.replay();
                if(menuType == LIST_MENU) {
                    backImg = ViewUtilities.BACK_PRESS_BTN_IMG;
                } else if(menuType == NORMAL_MENU) {
                    backImg = ViewUtilities.PLAY_PRESS_BTN_IMG;
                }
                backBtnClicked();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                if(menuType == LIST_MENU) {
                    backImg = ViewUtilities.BACK_HOVER_BTN_IMG;
                } else if(menuType == NORMAL_MENU) {
                    backImg = ViewUtilities.PLAY_HOVER_BTN_IMG;
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if(menuType == LIST_MENU) {
                    backImg = ViewUtilities.BACK_BTN_IMG;
                } else if(menuType == NORMAL_MENU) {
                    backImg = ViewUtilities.PLAY_BTN_IMG;
                }
            }
        });       
        
        room.add(backBtnPanel);
        
//        timeLine = new Timeline(this);  
//        timeLine.addPropertyToInterpolate("x", 0, -MENU_BAR_WIDTH);
//        timeLine.setDuration(500);
        
        afterAnimateReactor = new Timer(450, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actAfterAnimation();
            }
        });
        
        afterSubmenuClosed = new Timer(450, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                afterSubmenuClosed();
            }
        });
        afterSubmenuClosed.setRepeats(false);
    }
    
    protected void initBackButton(Image bsckBtnImg) {
        backImg = bsckBtnImg;
        backBtnPanel.setPreferredSize(
                new Dimension(backImg.getWidth(null), backImg.getHeight(null)));
        backBtnPanel.setBounds(BACK_BUTTON_X, BACK_BUTTON_Y, 
                               backImg.getWidth(null), backImg.getHeight(null));
    }
    
    @Override
    public void drawMenuBar(Graphics g) {
        Graphics g2 = g.create();
        Graphics g3 = g.create();
        
        g2.translate(x, 0);
        
        g2.drawImage(menuBarImg, 0, 0, MENU_BAR_WIDTH, room.getHeight(), null);
        g2.drawImage(backImg, BACK_BUTTON_X, BACK_BUTTON_Y, null);
        
        if(RoomView.getCurrState() == RoomView.NORMAL_STATE){
            menuTitleX = BACK_BUTTON_X;
        }
        g2.drawImage(menuTitleImg, 
                BACK_BUTTON_X+menuTitleX+backImg.getWidth(null), 
                BACK_BUTTON_Y + 7, null);
        
        backBtnPanel.setBounds(BACK_BUTTON_X + x, BACK_BUTTON_Y, 
                               backImg.getWidth(null), backImg.getHeight(null));
        
        if(!buttons.isEmpty() &&
                (!timeLine.isDone() || (timeLine.isDone() 
                    && buttons.get(0).getButtonPanel().getBounds().x!=x))) {
            for(AbstractBtn button : buttons) {
                Rectangle r = button.getButtonPanel().getBounds();
                r.x = x;
                button.getButtonPanel().setBounds(r);
            }
        }
        
        drawMenuProperties(g2);
        
        g2.setColor(Color.white);
        g2.translate(0, BASIC_INFO_Y);
        AbsMenu.drawCalendar(g2);
        g2.translate(0, -10);
        AbsMenu.drawCash(g2);
        
//        System.out.println("X: " + x);
//        drawMenuProperties(g2);
    }
    
    @Override
    public void drawSubmenu(Graphics g) {
        Graphics g2 = g.create();
        
//        x = MENU_BAR_WIDTH;
        
        g2.translate(MENU_BAR_WIDTH, 0);
        
        int rule = AlphaComposite.SRC_OVER;
        Composite composite = AlphaComposite.getInstance(rule, alpha);
        ((Graphics2D)g2).setComposite(composite);
        
        g2.drawImage(ViewUtilities.SUB_MENU_IMG, 0, 0, MENU_BAR_WIDTH, room.getHeight(), null);
        
        if(!buttons.isEmpty() &&
                (!timeLine.isDone() || (timeLine.isDone() 
                    && buttons.get(0).getButtonPanel().getBounds().x != MENU_BAR_WIDTH))) {
            for(AbstractBtn button : buttons) {
                Rectangle r = button.getButtonPanel().getBounds();
                r.x = MENU_BAR_WIDTH;
                button.getButtonPanel().setBounds(r);
            }
        }    
        
        drawMenuProperties(g2);
    }

    public ArrayList<AbstractBtn> getButtons() {
        return buttons;
    }
    
    /**
     * Draw the properties of the menu bar using the current graphic context
     * @param g The current graphic context
     */  
    protected abstract void drawMenuProperties(Graphics g);
    
    /**
     * Handle the event when the Back button is click
     */
    protected abstract void backBtnClicked();
    
    protected class ButtonMouseListener extends MouseAdapter {
        @Override
        public void mouseEntered(MouseEvent e) {
            if(menuDisabled) {
                return;
            }
//            room.repaint();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if(menuDisabled) {
                return;
            }
//            room.repaint();
        }         
    }
}
