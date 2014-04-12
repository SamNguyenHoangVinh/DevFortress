/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.menubar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import javax.swing.Timer;
import model.player.Player;
import org.pushingpixels.trident.Timeline;
import org.pushingpixels.trident.swing.SwingRepaintTimeline;
import view.FontUtilities;
import view.MainFrame;
import view.SoundUtilities;
import view.roomproperties.RoomView;

/**
 *
 * @author s3342128
 */
public abstract class AbsMenu extends Observable {
    
    public static final int MENU_BAR_WIDTH = 183;
    public static final int BASE_X = 5;
    public static final int YEAR_Y = 90;
    public static final int CASH_Y = 145;
    
    protected RoomView room;
    protected Image menuBarImg;
    protected SwingRepaintTimeline repaintTimeline;
    protected Timeline timeLine;
    protected Timer afterAnimateReactor;
    protected Timer afterReverseReactor;
    protected Timer afterSubmenuClosed;
    
    protected int x;
    protected float alpha;
    protected boolean menuDisabled;
    protected boolean submenu;

    public AbsMenu(RoomView r) {
        room = r;
        repaintTimeline = room.getRepaintTimeline();
        timeLine = new Timeline(this); 
        
//        timeLine.setDuration(200);
        
        afterReverseReactor = new Timer(470, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaintTimeline.setRepaintRectangle(MainFrame.getInstance()
                                                .getCurrPanel().getBounds());
                sequence();
            }
        });
        afterReverseReactor.setRepeats(false);
    }
    
    public static void drawCalendar(Graphics g) {
        Player player = Player.getInstance();
        
        // Draw YEAR
        g.setFont(FontUtilities.ROBOTO_THIN_110);
        String year = (player.getYear()>9) ? ""+player.getYear() 
                                           : "0"+player.getYear();
        g.drawString(year, BASE_X, YEAR_Y);
        
        // Draw MONTH
        g.setFont(FontUtilities.ROBOTO_THIN_48);
        String month = (player.getMonth()>9) ? ""+player.getMonth() 
                                             : "0"+player.getMonth();
        g.drawString(month, 126, 46);
        
        // Draw SEPARATOR
        g.drawLine(130, 55, 175, 55);
        
        // Draw WEEK
        g.setFont(FontUtilities.ROBOTO_THIN_30);
        int week = (player.getWeek()==5) ? 1 : player.getWeek();
        g.drawString("W" + week, 132, 90);             
    }
    
    public static void drawCash(Graphics g) {
        int cash = Player.getInstance().getMoney();
        
        if(cash <= 500) {
            g.setColor(Color.red);
            g.drawString("$" + cash, BASE_X+4, CASH_Y);
            g.setColor(Color.black);
        }
        else {
            g.drawString("$" + cash, BASE_X+4, CASH_Y);
        }
    }
    
    public void sequence() {
    }
    
    public void playAnimation() {
        timeLine.replay();     
    }
    
    public void reverseAnimation() {
        repaintTimeline.setRepaintRectangle(
                new Rectangle(0, 0, AbsMenu.MENU_BAR_WIDTH * 2, room.getHeight()));
        timeLine.replayReverse();
        SoundUtilities.Slide();
        afterReverseReactor.start();
    }
    
    public void reverseSubAnimation() {
        repaintTimeline.setRepaintRectangle(MainFrame.getInstance()
                                                .getCurrPanel().getBounds());
        timeLine.replayReverse();
    }
    
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
//        repaintTimeline.forceRepaintOnNextPulse();
    }
    
    public float getAlpha() {
        return alpha;
    }
    
    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }
    
    public boolean isMenuDisabled() {
        return menuDisabled;
    }

    public void setMenuDisabled(boolean menuDisabled) {
        this.menuDisabled = menuDisabled;
    }
    
    public boolean hasSubmenu() {
        return submenu;
    }
    
    public void setSubmenu(boolean submenu) {
        this.submenu = submenu;
    }

    public Timer getAfterAnimateReactor() {
        return afterAnimateReactor;
    }

    public void setAfterAnimateReactor(Timer afterAnimateReactor) {
        this.afterAnimateReactor = afterAnimateReactor;
    }
    
    /**
     * Draw the menu bar using a copy of the current graphic context
     * @param g The current graphic context
     */
    public abstract void drawMenuBar(Graphics g);
    
    /**
     * Draw the sub menu bar using a copy of the current graphic context
     * @param g The current graphic context
     */
    public abstract void drawSubmenu(Graphics g);
    
    /**
     * Handle the event happening after the menu finishes playing animation.
     */
    protected abstract void actAfterAnimation();
    
    /**
     * Handle the event when sub menu is closed
     */
    protected abstract void submenuClosed();
    
    /**
     * Handle the event happening after sub menu closed
     */
    protected abstract void afterSubmenuClosed();
}
