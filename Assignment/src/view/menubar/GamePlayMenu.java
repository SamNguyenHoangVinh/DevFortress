/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.menubar;

import animation.AlphaToZeroPanel;
import generator.RandDevGenerator;
import generator.RandProjGenerator;
import java.awt.Color;
import java.awt.Composite;
import turnbasedengine.TimeCounter;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Timer;
import model.player.Player;
import model.area.Area;
import model.effect.MoneyModification;
import model.event.EventCollector;
import model.project.Project;
import org.pushingpixels.trident.Timeline.TimelineState;
import view.FontUtilities;
import view.ViewUtilities;
import view.roomproperties.RoomView;

/**
 *
 * @author s3342128
 */
public class GamePlayMenu extends AbsMenu {
        
    public static final int PROGRESS_LEN = 170;
    public static final int DAY_PROGRESS = PROGRESS_LEN / 7;
    public static final int WEEK_PROGRESS_Y = 105;
    public static final int AREA_Y = 350;
    
    private ArrayList<Area> areas;
    private ArrayList<String> events;
//    private Timeline moneyEffect;
//    private float moneyAlpha;
    
    private AlphaToZeroPanel alphaPanel;
    
//    private Image eventBg;
//    private boolean threadRunning;

    public GamePlayMenu(RoomView r) {
        super(r);
        menuBarImg = ViewUtilities.GAMEPLAYMENU_IMG;
        areas = new ArrayList<Area>();
        events = new ArrayList<String>();
        room = r;
        alphaPanel = new AlphaToZeroPanel(repaintTimeline);
//        eventBg = new ImageIcon(ViewUtilities.MESSAGE_IMG_PATH).getImage();
//        moneyEffect = new Timeline(this);
        
        timeLine.addPropertyToInterpolate("x", 0, -menuBarImg.getWidth(null));
        alphaPanel.getTimeline().setDuration(1500);
//        moneyEffect.addPropertyToInterpolate("moneyAlpha", 1.0f, 0.0f);
        
        EventCollector.getInstance().clear();
        
        afterAnimateReactor = new Timer(470, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actAfterAnimation();
            }
        });
        afterAnimateReactor.setRepeats(false);
        
        try {
            RandDevGenerator.resetDevList();
        } catch(Exception ex) {
            
        }
        
        try {
            RandProjGenerator.resetProjList();
        } catch(Exception ex) {
            
        }
        
        generate4RandArea();
    }
    
    private void generate4RandArea() {
        int count = 0;
        
        for(Project p : Player.getInstance().getProjects().values()) {
            for(Area a : p.getAreas().getList()) {
                if(!a.getDevs().isEmpty()) {
                    areas.add(a);
                    count++;
                    
                    if(count > 3) {
                        break;
                    }
                }
            }
            
            if (count > 3) {
                break;
            }
        }
    }
    
    @Override
    public void drawMenuBar(Graphics g) {
        Graphics g2 = g.create();
        Player player = Player.getInstance();
        
        g2.translate(x, 0);

        g2.drawImage(menuBarImg, 0, 0, 
                            menuBarImg.getWidth(null), room.getHeight(), null);
        
        drawCalendar(g2);
        
        g2.fillRect(BASE_X, WEEK_PROGRESS_Y, 
                    BASE_X + player.getDay()*DAY_PROGRESS, 5);
        
        drawCash(g2);
        
        g2.setFont(FontUtilities.ROBOTO_THIN_12);
        
        for(int i=0, ay=AREA_Y; i<areas.size(); i++) {
            Area a = areas.get(i);
            float percent = (float)(a.getDone()) / a.getFP();
            
            g2.drawString(a.getName(), BASE_X, ay);
            g2.drawRect(BASE_X, ay+5, PROGRESS_LEN, 15);
            g2.fillRect(BASE_X, ay+5, (int)(percent*100*1.7), 15);
            
            ay += 37;
        }
        
        
//        int weekEventY = room.getHeight()-eventBg.getHeight(null)-BASE_X;
//        g2.drawString("Weekly Events", BASE_X, weekEventY - 7);
////        g2.drawImage(eventBg, 0, room.getHeight()-eventBg.getHeight(null), null);
//        g2.drawImage(eventBg, BASE_X, weekEventY, null);
//        
//        g2.setColor(Color.white);
//        for(int i=0; i<events.size(); i++) {
//            weekEventY += 20;
//            g2.drawString("- " + events.get(i), BASE_X+3, weekEventY);
//        }
        ArrayList<Integer> moneyChange = MoneyModification.getMoneyChange();
        Graphics2D g3 = (Graphics2D)(g2.create());
        g3.setFont(FontUtilities.SEGOE_UI_BOLD_24);
        for(int i=0, my=CASH_Y+30; i<moneyChange.size(); i++) {
            int m = moneyChange.get(i);
            String s = "";
            
            if(m > 0) {
                g3.setColor(Color.green);
                s += "+";
            } else {
                g3.setColor(Color.red);
                s += "-";
            }
            
            s += m;
            
            if(i == 0) {
                Composite cps1 = g3.getComposite();
                
                if(alphaPanel.getTimeline().getState() != 
                                    TimelineState.PLAYING_FORWARD) {
                    playMoneyEffect();
                }
                g3.setComposite(alphaPanel.makeComposite());
                g3.drawString(s, BASE_X, my);
                g3.setComposite(cps1);
            } else {
                g3.drawString(s, BASE_X, my);
            }
            
            my += 20;
        }
        
        if(TimeCounter.getInstance().isReady() && x==0) {
//            System.out.println("GAMEPLAY START TURN");
            TimeCounter.getInstance().startTurn(room);
        }
    }

    @Override
    protected void actAfterAnimation() {
        room.backToNormalMenu();
    }
    
    private void playMoneyEffect() {
        alphaPanel.playAnimation();
        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1520);
                } catch(Exception ex) {
                    
                }
                MoneyModification.getMoneyChange().remove(0);
            }            
        }.start();
    }
    
//    public static void drawCalendar(Graphics g) {
//        Player player = Player.getInstance();
//        
//        // Draw YEAR
//        g.setFont(FontUtilities.ROBOTO_THIN_110);
//        String year = (player.getYear()>9) ? ""+player.getYear() 
//                                           : "0"+player.getYear();
//        g.drawString(year, BASE_X, YEAR_Y);
//        
//        // Draw MONTH
//        g.setFont(FontUtilities.ROBOTO_THIN_48);
//        String month = (player.getMonth()>9) ? ""+player.getMonth() 
//                                             : "0"+player.getMonth();
//        g.drawString(month, 126, 46);
//        
//        // Draw SEPARATOR
//        g.drawLine(130, 55, 175, 55);
//        
//        // Draw WEEK
//        g.setFont(FontUtilities.ROBOTO_THIN_30);
//        int week = (player.getWeek()==5) ? 1 : player.getWeek();
//        g.drawString("W" + week, 132, 90);             
//    }
//    
//    public static void drawCash(Graphics g) {
//        int cash = Player.getInstance().getMoney();
//        
//        if(cash <= 500) {
//            g.setColor(Color.red);
//            g.drawString("Cash: " + cash, BASE_X, CASH_Y);
//            g.setColor(Color.black);
//        }
//        else {
//            g.drawString("Cash: " + cash, BASE_X, CASH_Y);
//        }
//    }
    
    /**
     * Add event message
     * @param event The new event message
     */
    public void addEvent(String event) {
        events.add(event);
    }
    
    public void clearEvent() {
        events.clear();
    }

    public ArrayList<String> getEvents() {
        return events;
    }

    @Override
    public void drawSubmenu(Graphics g) {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void submenuClosed() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void afterSubmenuClosed() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

//    public float getMoneyAlpha() {
//        return moneyAlpha;
//    }
//
//    public void setMoneyAlpha(float moneyAlpha) {
//        this.moneyAlpha = moneyAlpha;
//    }
    
}
