/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.detailview;

import view.detailview.button.DtvButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import model.player.Player;
import org.pushingpixels.trident.Timeline;
import org.pushingpixels.trident.swing.SwingRepaintTimeline;
import view.FontUtilities;
import view.MainFrame;
import view.SoundUtilities;
import view.ViewUtilities;
import view.menubar.AbstractNormalMenu;
import view.menubar.ListMenuBar;
import view.menubar.ListMenuBtn;
import view.roomproperties.*;

/**
 *
 * @author s3342128
 */
public abstract class DetailsPanel {

    public static final int FONT_SIZE_LARGE = 22;
    public static final int FONT_SIZE = 17;
    public static final int SMALL_FONT_SIZE = 12;
    public static final int TITLE_BASE_X = 70;
    public static final int TITLE_BASE_Y = 120; //90
    public static final int TITLE_HEIGHT = 35;
    public static final int CANCEL_X = 650;
    public static final int BUTTON_Y = 500;
    public static final int CANCEL_WIDTH = 100;
    public static final int BUTTON_HEIGHT = 30;
    protected SwingRepaintTimeline repaintTimeline;
    protected Timeline timeLine;
//    protected RoomView room;
    protected ListMenuBtn fromBtn;
    protected ListMenuBtn parentFromBtn;
    protected ListMenuBtn ancestorFromBtn;
    protected ArrayList<JPanel> temp;
    protected Image bg; //Background image
    protected Image namePanel;
    protected String name;
    protected String[] titles;
    protected Timer afterAnimateReactor;
    protected Timer afterSubAnimateReactor;
    protected DtvButton cancelBtn;
    protected int width;
    protected int height;
    protected int x;
    protected int currentIndex;
    protected int anotherIndex;
    protected float alpha;
    protected boolean btnsDisabled;
    protected DetailsPanel next;

    public DetailsPanel(ListMenuBtn fromBtn, ListMenuBtn parentFromBtn, ListMenuBtn ancestorFromBtn, SwingRepaintTimeline repaintTL) {
        this.parentFromBtn = parentFromBtn;
        this.ancestorFromBtn = ancestorFromBtn;
        init(fromBtn, repaintTL);
    }

    public DetailsPanel(ListMenuBtn fromBtn, ListMenuBtn parentFromBtn, SwingRepaintTimeline repaintTL) {
        this.parentFromBtn = parentFromBtn;
        init(fromBtn, repaintTL);
    }

    public DetailsPanel(ListMenuBtn fromBtn, SwingRepaintTimeline repaintTL) {
        init(fromBtn, repaintTL);
    }

    private void init(final ListMenuBtn fromBtn, SwingRepaintTimeline repaintTL) {
        final RoomView r = GetCurrRoomCommand.getRoom();
        
        temp = new ArrayList<JPanel>();
        bg = new ImageIcon(ViewUtilities.DETAIL_PANEL_IMG_PATH).getImage();
        this.fromBtn = fromBtn;
        repaintTimeline = repaintTL;
        width = r.getWidth() - AbstractNormalMenu.MENU_BAR_WIDTH;
        height = r.getHeight();
//        x = width + AbstractGameMenu.MENU_BAR_WIDTH;
        x = 1000;

        timeLine = new Timeline(this);
        timeLine.addPropertyToInterpolate("x", 1000,
                AbstractNormalMenu.MENU_BAR_WIDTH);
        timeLine.setDuration(400);

        cancelBtn = new DtvButton("Cancel", CANCEL_X) {
            @Override
            protected void onClick() {
                if (btnsDisabled) {
                    return;
                }
                
                if(r.getSubmenu() != null && ancestorFromBtn == null){
                    ((ListMenuBar)(r.getSubmenu())).submenuClosed();
                }

                if (getInstance() instanceof TableDetailPanel
                        && ((TableDetailPanel) getInstance()).getComputer() != null) {
                    r.openYourStorageDetail((ListMenuBtn)((AbstractNormalMenu)r.getMenu()).getButtons().get(4));
                } else if (ancestorFromBtn != null) {
                    System.out.println("yay");
                    r.openSubDetailView(new YourCompDetail(parentFromBtn, ancestorFromBtn, repaintTimeline));
                } else if (parentFromBtn != null) {
                    System.out.println("here");
                    r.openYourTableDetail(parentFromBtn);
                } else {
                    reverseAnimation();
                }
            }
        };
//        cancelBtn.getPanel().addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseReleased(MouseEvent e) {
//                if(r.getSubmenu() != null && ancestorFromBtn == null){
//                    ((ListMenuBar)(r.getSubmenu())).submenuClosed();
//                }
//            }
//        });
        r.add(cancelBtn.getPanel());

        afterAnimateReactor = new Timer(450, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (x == 1000) {
                    MainFrame.setCurrState(MainFrame.NORMAL_STATE);
                    addBackPanels();
                    temp.clear();
                    afterAnimateReactor.stop();
                }
            }
        });

        afterSubAnimateReactor = new Timer(450, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (x == 1000) {
                    MainFrame.setCurrState(MainFrame.NORMAL_STATE);
                    afterSubAnimateReactor.stop();
                    r.progressSequence(next);
                    r.finalizeSequence();
                }
            }
        });
    }

    public void drawPanel(Graphics g) {
        predraw(g);
        drawContent(g);
        postdraw(g);
    }

    public void drawSubPanel(Graphics g) {
        predrawAl(g);
        drawContent(g);
        postdraw(g);
    }

    public void drawRoomPanel(Graphics g) {
        predrawAl(g);
        drawContent(g);
        postdrawAl(g);
    }

    protected void predrawAl(Graphics g) {
        g.translate(x, 0);
        g.drawImage(bg, 0, 0, width, height, null);
    }

    protected void predraw(Graphics g) {
        g.translate(x, 0);
        drawHeader(g);
    }

    protected abstract void drawContent(Graphics g);

    protected void postdraw(Graphics g) {
//        drawCancelBtn(g);
        cancelBtn.drawButton(g, x);
        actAfterAnimation();
    }

    protected void postdrawAl(Graphics g) {
        actAfterAnimation();
    }

    /**
     * Draw the header of the detail panel using the current graphic context.
     *
     * @param g The current graphic context
     */
    protected void drawHeader(Graphics g) {
//        Graphics2D g2 = FontUtilities.renderer(g);
//        System.out.println("DRAW DETAIL PANEL");
        g.drawImage(bg, 0, 0, width, height, null);
        g.drawImage(namePanel, 0, 20, null);

//        g2.setFont(new Font("", Font.BOLD, 25));
        g.setFont(FontUtilities.SEGOE_UI_SEMIBOLD_22);
        g.drawString(name, 30, 50);
        g.drawString("Your Cash: $" + Player.getInstance().getMoney(),
                CANCEL_X - 100, 50);
    }

//    /**
//     * Draw the cancel button using the current graphic context.
//     * @param g The current graphic context
//     */
//    protected void drawCancelBtn(Graphics g) {
////        g.setFont(new Font("", Font.BOLD, FONT_SIZE));
//        g.setFont(FontUtilities.SEGOE_UI_SEMIBOLD_17);
//        if(cancelHover) {
//            g.setColor(Color.WHITE);
//            g.fillRect(CANCEL_X, BUTTON_Y, CANCEL_WIDTH, BUTTON_HEIGHT);
//            g.setColor(Color.BLACK);
//        }
//        
//        cancelBtn.setBounds(CANCEL_X+x, BUTTON_Y, CANCEL_WIDTH, BUTTON_HEIGHT);
//        g.drawRect(CANCEL_X, BUTTON_Y, CANCEL_WIDTH, BUTTON_HEIGHT);
//        g.drawString("Close", CANCEL_X + 25, BUTTON_Y + 21);
//    }
    protected void actAfterAnimation() {
//        System.out.println("ACT AFTER ANIMATION : " + x);
//        if(x > room.getWidth() && 
//                timeLine.getState()==TimelineState.PLAYING_REVERSE) {
//            MainFrame.setCurrState(MainFrame.NORMAL_STATE);
//        }
    }

    public void playAnimation() {
        final RoomView r = GetCurrRoomCommand.getRoom();;
        timeLine.replay();

        for (WorkingTableView table : r.getTables()) {
            for (HardwareView comp : table.getComputers()) {
                temp.add(comp.getPanel());
                r.remove(comp.getPanel());
            }
            for (HardwareView monitor : table.getMonitors()) {
                temp.add(monitor.getPanel());
                r.remove(monitor.getPanel());
            }
            for (SeatView seat : table.getSeats()) {
                temp.add(seat.getSeatPanel());
                r.remove(seat.getSeatPanel());
            }
        }

        temp.add(r.getMetroStarter());
        r.remove(r.getMetroStarter());
    }

    public void reverseAnimation() {
//        System.out.println("TEMPORARY PANEL SIZE: " + temp.size());
        timeLine.replayReverse();
        SoundUtilities.Slide();
        afterAnimateReactor.start();


//        addBackPanels();
//        temp.clear();

//        temp = null;
    }

    public void playSubAnimation() {
        timeLine.replay();
    }

    public void reverseSubAnimation(DetailsPanel next) {
        this.next = next;
        timeLine.replayReverse();
        SoundUtilities.Slide();
        afterSubAnimateReactor.start();

        addBackPanels();
        temp.clear();
    }

    public abstract void addBackBtns();

//    public abstract void removeBtns();
    public void addBackPanels() {
        final RoomView r = (RoomView) (MainFrame.getInstance().getCurrPanel());
        for (int i = 0; i < temp.size(); i++) {
            r.add(temp.get(i));
        }
    }

//    public RoomView getCurrentRoom() {
//        try {
//            return (RoomView) (MainFrame.getInstance().getCurrPanel());
//        } catch(Exception ex) {
//            return GetCurrRoomCommand.getRoom();
//        }
//    }
    
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

    public ListMenuBtn getFromBtn() {
        return fromBtn;
    }

    public ListMenuBtn getParentFromBtn() {
        return parentFromBtn;
    }

    public void setFromBtn(ListMenuBtn fromBtn) {
//        System.out.println("Setting new button");
        this.fromBtn = fromBtn;
    }

    public boolean isBtnsDisabled() {
        return btnsDisabled;
    }

    public void setBtnsDisabled(boolean btnsDisabled) {
        this.btnsDisabled = btnsDisabled;
    }

    public void switchEnableBtns() {
        btnsDisabled = !btnsDisabled;
    }

    public Timeline getTimeLine() {
        return timeLine;
    }

    public DetailsPanel getInstance() {
        return this;
    }

    public DtvButton getCancelBtn() {
        return cancelBtn;
    }
}
