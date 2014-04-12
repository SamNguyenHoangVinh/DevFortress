/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.detailview;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import model.area.Area;
import model.event.EventCollector;
import model.player.Player;
import model.project.Project;
import org.pushingpixels.trident.swing.SwingRepaintTimeline;
import turnbasedengine.TimeCounter;
import view.FontUtilities;
import view.MainFrame;
import view.ViewUtilities;
import view.listview.ListComponent;
import view.menubar.AbstractNormalMenu;
import view.roomproperties.GetCurrRoomCommand;
import view.roomproperties.RoomView;

/**
 *
 * @author HungHandsome
 */
public class TurnSummaryPanel extends DetailsPanel {
    
    public static final int AREA_HEIGHT = 19;
    public static final int BACK_X = 0;
    public static final int NAV_Y = 250;
    public static final int NEXT_X = 729;
    
    private ListComponent backBtn;
    private ListComponent nextBtn;
    private ArrayList<Project> projs;
    private int currIdx;

    public TurnSummaryPanel(SwingRepaintTimeline repaintTL) {
        super(null, repaintTL);
        bg = new ImageIcon(ViewUtilities.REPORT_BG_IMG_PATH).getImage();
        projs = new ArrayList<Project>(
                                Player.getInstance().getProjects().values());
        try {
            name = projs.get(currIdx).getName();
        } catch(Exception ex) {
            name = "";
        }        
        
        final RoomView r = GetCurrRoomCommand.getRoom();
        cancelBtn.setText("Ok");
        cancelBtn.getPanel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
//                reverseAnimation();
                TimeCounter.getInstance().setState(TimeCounter.STOP);
                r.getMenu().playAnimation();
                r.getMenu().getAfterAnimateReactor().start();
            }
        });
        
        initBack(r);
        initNext(r);
    }   
    
    private void initBack(final RoomView room) {
        backBtn = new ListComponent(BACK_X, NAV_Y,  AbstractNormalMenu.MENU_BAR_WIDTH,
//        back = new ListComponent(BACK_X, NAV_Y,
            new ImageIcon(ViewUtilities.BACK_SMALL_IMG_PATH).getImage(), 
            new ImageIcon(ViewUtilities.BACK_SMALL_HOVER_IMG_PATH).getImage(), room) {

            @Override
            protected void onClick() {                
                setCurrIdx(currIdx-1);
            }                
        };
    }
    
    private void initNext(final RoomView room) {
        nextBtn = new ListComponent(NEXT_X, NAV_Y, AbstractNormalMenu.MENU_BAR_WIDTH,
//        next = new ListComponent(NEXT_X, NAV_Y,
            new ImageIcon(ViewUtilities.NEXT_IMG_PATH).getImage(), 
            new ImageIcon(ViewUtilities.NEXT_HOVER_IMG_PATH).getImage(), room) {

            @Override
            protected void onClick() {                
                setCurrIdx(currIdx+1);
            }                
        };
    }
    
    @Override
    protected void drawContent(Graphics g) {
        if(currIdx >= 0) {
            drawProjSummary(g);
        } else {
            drawEventSummary(g);
        }
        
//        if(Player.getInstance().getProjects().size() > 1) {
            backBtn.drawComponent(g);
            nextBtn.drawComponent(g);
//        }
    }

    @Override
    protected void drawHeader(Graphics g) {
//        System.out.println("DRAW TURN SUMMARY PANEL");
        g.drawImage(bg, 0, 0, width, height, null);
        
        if(projs.isEmpty()) {            
            return;
        }
        
        g.drawImage(namePanel, 0, 20, null);        
          
//        g2.setFont(new Font("", Font.BOLD, 25));
        g.setFont(FontUtilities.SEGOE_UI_SEMIBOLD_22);
        g.drawString(name, 30, 50);
    }
    
//    @Override
//    protected void drawCancelBtn(Graphics g) {
//        g.setFont(FontUtilities.SEGOE_UI_SEMIBOLD_17);
//        if(cancelHover) {
//            g.setColor(Color.WHITE);
//            g.fillRect(CANCEL_X, BUTTON_Y, CANCEL_WIDTH, BUTTON_HEIGHT);
//            g.setColor(Color.BLACK);
//        }
//        
//        cancelBtn.setBounds(CANCEL_X+x, BUTTON_Y, CANCEL_WIDTH, BUTTON_HEIGHT);
//        g.drawRect(CANCEL_X, BUTTON_Y, CANCEL_WIDTH, BUTTON_HEIGHT);
//        g.drawString("Ok", CANCEL_X + 40, BUTTON_Y + 21);
//    }
    
    @Override
    public void addBackBtns() {
        JPanel r = MainFrame.getInstance().getCurrPanel();
        r.add(cancelBtn.getPanel());
    }

    private void drawProjSummary(Graphics g) {
        if(projs.isEmpty()) {
            g.drawString("All of your projects are done", 200, 200);            
            return;
        }
        
        Project currProj = projs.get(currIdx);
        
        g.setFont(FontUtilities.SEGOE_UI_BOLD_12);
        g.drawString("Areas", TITLE_BASE_X, TITLE_BASE_Y);
        g.drawString("FPs", TITLE_BASE_X + 150, TITLE_BASE_Y);
        g.drawString("Progress", TITLE_BASE_X + 220, TITLE_BASE_Y);
        
//        g2.setFont(new Font("", Font.PLAIN, SMALL_FONT_SIZE));
        g.setFont(FontUtilities.SEGOE_UI_12);
        for (int i = 0, strY = TITLE_BASE_Y + SMALL_FONT_SIZE + 10;
                                    i < currProj.getAreas().size() && i < 15; i++) {
            Area a = currProj.getAreas().get(i);
            g.drawString(a.getName(), TITLE_BASE_X, strY);
            
            if (a.isKnown()) {
                g.drawString(a.getFP() + "", TITLE_BASE_X + 150, strY);
            } else {
                g.drawString("???", TITLE_BASE_X + 150, strY);
            }
                        
            if(a.isFinished()) {
                g.drawString("Done", TITLE_BASE_X + 220, strY);
            } else {
                g.drawString(a.getDone() + "", TITLE_BASE_X + 220, strY);
            }
            
            strY += AREA_HEIGHT;
        }
        
        if (currProj.getAreas().size() > 15) {
            int x1 = TITLE_BASE_X + 380;
            int x2 = TITLE_BASE_X + 530;

            for (int i = 15, strY = TITLE_BASE_Y + SMALL_FONT_SIZE + 10;
                                          i < currProj.getAreas().size(); i++) {                
                Area a = currProj.getAreas().get(i);
                g.drawString(a.getName(), x1, strY);       
                
                if (a.isKnown()) {
                    g.drawString(a.getFP() + "", x2, strY);
                } else {
                    g.drawString("???", x2, strY);
                }
                
                if(a.isFinished()) {
                    g.drawString("Done", x2 + 70, strY);
                } else {
                    g.drawString(a.getDone() + "", x2 + 70, strY);
                }
                
                strY += AREA_HEIGHT;
            }

//            g2.setFont(new Font("", Font.BOLD, SMALL_FONT_SIZE));
            g.setFont(FontUtilities.SEGOE_UI_BOLD_12);
            g.drawString("Areas", x1, TITLE_BASE_Y);
            g.drawString("FPs", x2, TITLE_BASE_Y);
            g.drawString("Progress", x2 + 70, TITLE_BASE_Y);
        }
    }
    
    private void drawEventSummary(Graphics g) {
         g.setFont(FontUtilities.SEGOE_UI_14);
        
        Iterator iterator = EventCollector.getInstance().getSet().iterator();
        int strY = TITLE_BASE_Y + SMALL_FONT_SIZE + 12;
        
        while(iterator.hasNext()) {
            try {
                String event = (String)(iterator.next());
                int num = EventCollector.getInstance().getEvents().get(event);

                g.drawString(event, TITLE_BASE_X, strY);
                g.drawString("" + num, TITLE_BASE_X + 220, strY);

                strY += AREA_HEIGHT;
            } catch(Exception ex) {
                
            }
        }        
    }
    
    public void setCurrIdx(int currIdx) {
        if (currIdx >= projs.size() - 1) {
            System.out.println("Higher 1: " + currIdx);
            this.currIdx = projs.size() - 1;
            System.out.println("Higher 2: " + this.currIdx);
        }
        else if(currIdx <= -1) {
            System.out.println("Smaller 1: " + currIdx);
            this.currIdx = -1;
            System.out.println("Smaller 2: " + this.currIdx);
        }
        else {
            System.out.println("Equal 1: " + currIdx);
            this.currIdx = currIdx;
            System.out.println("Equal 2: " + this.currIdx);
        }
        
        if(this.currIdx >= 0) {
            name = projs.get(this.currIdx).getName();
        } else {
            name = "Event Report";
        }
//        MainFrame.getInstance().getCurrPanel().repaint();
    }
    
}
