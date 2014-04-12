/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.roomproperties;

import animation.AlphaToZeroPanel;
import animation.ScreenCapturer;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import model.player.Player;
import model.room.Room;
import model.room.RoomSet;
import org.pushingpixels.trident.Timeline;
import org.pushingpixels.trident.swing.SwingRepaintTimeline;
import turnbasedengine.TimeCounter;
import view.FontUtilities;
import view.MainFrame;
import view.ViewUtilities;

/**
 *
 * @author HungHandsome
 */
public class RoomSetView extends JPanel {

    public static final int BASE_X = 110;
    public static final int BASE_Y = 50;
    public static final int GAP = 15;
    public static final int IMG_WIDTH = 242;
    public static final int IMG_HEIGHT = 137;
    public static final int NUM_ROWS = 3;
    public static final int NUM_COLS = 3;
    public static final int SLIDE_LEN = 
                                MainFrame.getInstance().getWidth() - BASE_X;
    public static final int SLIDE_DURATION = 500;
    
    private RoomSet cur; //Current RoomSet
//    private RoomSet prev;
//    private RoomSet next;
    private JPanel[][] panels;
    private int curIdx; //Current RoomSet ID
    private boolean panelDisabled;
    
    
    private SwingRepaintTimeline repaintTimeline;
    private Timeline fromMetroTimer;
    private Timeline slideTimer;
    private AlphaToZeroPanel[] gridAnims;
//    private Image bg;
//    private Image roomImg;
    private int roomX;
    private int roomY;
    private float scale;
    private int slideX;
    

    public RoomSetView() {        
        cur = (RoomSet)(Player.getInstance().getRooms()
                .values().toArray()[curIdx]);
        panels = new JPanel[NUM_COLS][NUM_ROWS];
        gridAnims = new AlphaToZeroPanel[NUM_COLS];
        
        repaintTimeline = new SwingRepaintTimeline(this);
        repaintTimeline.setAutoRepaintMode(true);
        repaintTimeline.playLoop(Timeline.RepeatBehavior.LOOP);
        
        for(int i=0; i<NUM_COLS; i++) {
            gridAnims[i] = new AlphaToZeroPanel(repaintTimeline);
            gridAnims[i].setAlpha(0.0f);
        }
        
//        setBackground(Color.black);
        setLayout(null);
        setPreferredSize(new Dimension(MainFrame.PANEL_WIDTH, 
                                       MainFrame.PANEL_HEIGHT));
        
        initPanels();
        
        
        
        slideTimer = new Timeline(this);
        slideTimer.addPropertyToInterpolate("slideX", 0, -SLIDE_LEN);
        slideTimer.setDuration(SLIDE_DURATION);
        
        
        JButton addRoom = new JButton("Add room");
        addRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Player.getInstance().addRoom(new Room());
            }
        });
        addRoom.setBounds(20, 500, 100, 30);
        add(addRoom);
        
//        JButton prevRoom = new JButton("Prev");
//        prevRoom.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                prevRoomSet();
//            }
//        });
//        prevRoom.setBounds(130, 500, 100, 30);
//        add(prevRoom);
//        
//        JButton nextRoom = new JButton("Next");
//        nextRoom.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                nextRoomSet();
//            }
//        });
//        nextRoom.setBounds(240, 500, 100, 30);
//        add(nextRoom);
    }

    private void initPanels() {
        for(int i=0, idx=0, ix=BASE_X, size=cur.size(); i<NUM_COLS; i++) {
            for(int j=0, iy=BASE_Y; j<NUM_ROWS; j++, idx++) {
                final int size2 = size;
                panels[i][j] = new JPanel();
                
                panels[i][j].setOpaque(false);
                panels[i][j].setBounds(ix, iy, IMG_WIDTH, IMG_HEIGHT);
                
                add(panels[i][j]);
                
                final int rx = ix;
                final int ry = iy;
                final int roomId = idx;
                panels[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        int mx = e.getX();
                        int my = e.getY();
                                                
                        if(panelDisabled || 
                                mx<0 || mx>IMG_WIDTH || my<0 || my>IMG_HEIGHT) {
                            return;                            
                        }
                        
                        if(!(Player.getInstance().setCurrRoom(curIdx, roomId))) {
                            return;
                        }
                        
                        System.out.println("Panel click");
                        
//                        Image roomImg = ScreenCapturer.getScreenShot();
//                        Image bg = ScreenCapturer.captureScreen(
//                                                    MainFrame.getInstance());
                        float scale = ((float)IMG_WIDTH) 
                                        / MainFrame.PANEL_WIDTH;
//                        FromMetroAnimation a = 
//                                new FromMetroAnimation(bg, roomImg, rx, ry, scale);
//                        
//                        MainFrame.getInstance().switchPanel(a);
//                        a.playAnimation();
                        panelDisabled = true;
                        
                        playFromMetroAnim(rx, ry, scale);
                    }                    
                });
                
                iy += IMG_HEIGHT + GAP;
            }
            
            ix += IMG_WIDTH + GAP;
        }
    }
    
    private void playFromMetroAnim(int rx, int ry, float scl) {
        roomX = rx;
        roomY = ry;
        scale = scl;
        
        fromMetroTimer = new Timeline(this);
        fromMetroTimer.addPropertyToInterpolate("scale", scale, 1.0f);
        fromMetroTimer.addPropertyToInterpolate("roomX", roomX, 0);
        fromMetroTimer.addPropertyToInterpolate("roomY", roomY, 0);
        fromMetroTimer.setDuration(300);
        
//        repaintTimeline = new SwingRepaintTimeline(this);
//        repaintTimeline.setAutoRepaintMode(false);
//        repaintTimeline.playLoop(Timeline.RepeatBehavior.LOOP);
        
        fromMetroTimer.replay();
        
        new Thread() {
            @Override
            public void run() {
                RoomView selectedRoom = new RoomView();
                
                if(TimeCounter.getInstance().isRunning()) {
                    selectedRoom.startTurn();
                }
                
                try {
                    sleep(350);
                } catch(Exception ex) {                    
                }
                
//                MainFrame.getInstance().switchPanel(selectedRoom);
                MainFrame.getInstance().setCurrPanel(selectedRoom);
                
                if(TimeCounter.getInstance().isJustFinished()) {
                    GetCurrRoomCommand.setRoom(selectedRoom);
                    selectedRoom.startTurn();
                    selectedRoom.openTurnSummaryDetail(); 
                }
            }
        }.start();
    }
    
    public void playGridAnim() {
        new Thread() {
            @Override
            public void run() {
                panelDisabled = true;
                for(AlphaToZeroPanel a : gridAnims) {
                    a.reverseAnimation();
                    try {
                        sleep(200);
                    } catch (Exception ex) {
                    }
                }
//                repaintTimeline.setAutoRepaintMode(false);
                panelDisabled = false;
            }
        }.start();        
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)(g.create());
//        Image img = ScreenCapturer.getScreenShot();
//        int imgW = img.getWidth(null) / 4;
//        int imgH = img.getHeight(null) / 4;
        
//        System.out.println("W: " + imgW + " H: " + imgH);
        
        g.fillRect(0, 0, MainFrame.getInstance().getWidth(), 
                         MainFrame.getInstance().getHeight());
        
//        g.setColor(Color.white);
//        g.setFont(FontUtilities.SEGOE_UI_BOLD_17);
//        g.drawString(cur.getId() + " / " 
//                + Player.getInstance().getRooms().size(), 400, 520);
        
        g2.setColor(Color.white);
        g2.translate(slideX, 0);
        
        Composite cps1 = g2.getComposite();
        
//        for(int i=0, idx=0, ix=BASE_X, size=cur.size(); i<NUM_COLS; i++) {            
//            g2.setComposite(gridAnims[i].makeComposite());
//            for(int j=0, iy=BASE_Y; j<NUM_ROWS; j++) {
//                if(idx < size) {
//                    g2.drawImage(img, ix, iy, IMG_WIDTH, IMG_HEIGHT, null);                    
//                    idx++;
//                } else {
//                    g2.fillRect(ix, iy, IMG_WIDTH, IMG_HEIGHT);
//                }
//                
//                iy += IMG_HEIGHT + GAP;
//            }
//            
//            ix += IMG_WIDTH + GAP;
//        }
//        if(prev != null) {
//            drawRoomSet(g2, img, prev.size(), BASE_X-SLIDE_LEN, BASE_Y);
//        }
        
        drawRoomSet(g2, cur.size(), BASE_X, BASE_Y);
        
//        if(next != null) {
//            drawRoomSet(g2, img, next.size(), BASE_X+SLIDE_LEN, BASE_Y);
//        }
        
        g2.setComposite(cps1);
        g2.dispose();
        
        if(panelDisabled) {
            Graphics2D g3 = (Graphics2D)(g.create());
            g3.translate(roomX, roomY);
            g3.scale(scale, scale);
            g3.drawImage(ViewUtilities.OUR_ROOM, 0, 0, null);
        }
    }

    private void drawRoomSet(Graphics2D g, int size, int baseX, int baseY) {
        for(int i=0, idx=0; i<NUM_COLS; i++) {            
            g.setComposite(gridAnims[i].makeComposite());
            for(int j=0, iy=baseY; j<NUM_ROWS; j++) {
                if(idx < size) {
                    g.drawImage(ViewUtilities.OUR_ROOM, 
                            baseX, iy, IMG_WIDTH, IMG_HEIGHT, null);                    
                    idx++;
                } else {
//                    g.fillRect(baseX, iy, IMG_WIDTH, IMG_HEIGHT);
                    g.drawImage(ViewUtilities.EMPTY_ROOM2, 
                            baseX, iy, IMG_WIDTH, IMG_HEIGHT, null);
                }
                
                iy += IMG_HEIGHT + GAP;
            }
            
            baseX += IMG_WIDTH + GAP;
        }
    }
    
//    private void nextRoomSet() {
//        Player p = Player.getInstance();
//
//        if (curIdx>=(p.getRooms().size() - 1) || panelDisabled) {
//            curIdx = p.getRooms().size() - 1;
//            return;
//        }
//
//        panelDisabled = true;
//        curIdx++;
//        next = (RoomSet) (p.getRooms().values().toArray()[curIdx]);
//        slideTimer.replay();
//        
//        new Thread() {
//            @Override
//            public void run() {
//                try {
//                    sleep(SLIDE_DURATION+80);
//                } catch(Exception ex) {
//                    
//                }
//                cur = next;
//                next = null;
//                slideX = 0;
//                panelDisabled = false;
//            }
//        }.start();
//    }
    
//    private void prevRoomSet() {
//        Player p = Player.getInstance();
//
//        if (curIdx<=0 || panelDisabled) {
//            curIdx = 0;
//            return;
//        }
//
//        panelDisabled = true;
//        curIdx--;
//        prev = (RoomSet) (p.getRooms().values().toArray()[curIdx]);
//        slideTimer.replayReverse();
//        
//        new Thread() {
//            @Override
//            public void run() {
//                try {
//                    sleep(SLIDE_DURATION+80);
//                } catch(Exception ex) {
//                    
//                }
//                cur = prev;
//                prev = null;
//                slideX = 0;
//                panelDisabled = false;
//            }            
//        }.start();
//    }
//    
    public int getRoomX() {
        return roomX;
    }

    public void setRoomX(int roomX) {
        this.roomX = roomX;
//        repaintTimeline.forceRepaintOnNextPulse();
    }

    public int getRoomY() {
        return roomY;
    }

    public void setRoomY(int roomY) {
        this.roomY = roomY;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public int getSlideX() {
        return slideX;
    }

    public void setSlideX(int slideX) {
        this.slideX = slideX;
    }
    
}
