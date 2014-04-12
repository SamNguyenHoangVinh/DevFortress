/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.detailview;

import view.detailview.button.DtvButton;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JPanel;
import model.Developer;
import model.player.Player;
import model.area.Area;
import model.project.Project;
import org.pushingpixels.trident.swing.SwingRepaintTimeline;
import view.FontUtilities;
import view.MainFrame;
import view.listview.DevListView;
import view.menubar.ListMenuBar;
import view.menubar.ListMenuBtn;
import view.roomproperties.RoomView;

/**
 *
 * @author s3342128
 */
public class YourProjDetail extends ProjDetailPanel {
    
    public static final int AREA_HEIGHT = 25;
    public static final int ASSIGN_X = 340;
    public static final int CANCEL_PROJ_X = 500;
    public static final int CANCEL_PROJ_WIDTH = 170;
    
    public static final int ASSIGN_DEV_STATE = 1000;
    public static final int REMOVE_DEV_STATE = 2000;
    
    protected DevListView devList;
    protected AreaView areas[];
//    private JPanel ok;
//    private JPanel cancelProj;
//    private boolean okHover;
//    private boolean canProHover;
    private DtvButton cancelProj;
    private boolean acceptProjState;
    
    public YourProjDetail(final ListMenuBtn fromBtn,
                                               SwingRepaintTimeline repaintTL) {
        super(fromBtn, repaintTL);
        final RoomView r = (RoomView) (MainFrame.getInstance().getCurrPanel());
        titles = new String[]{"Name:", "Level:", "Type:", "Details:"};
//        ok = new JPanel();
//        cancelProj = new JPanel();
//        areas = new AreaView[proj.getAreas().size()];
        areas = new AreaView[30];
        acceptProjState = false;
        
//        System.out.println("Construct YourProjDetail");
        
        int i;
        for(i=0; i<proj.getAreas().size(); i++) {
//            System.out.println("area : " + proj.getAreas().get(i).getName() 
//                    + " - " + proj.getAreas().get(i).getFP());
            areas[i] = new AreaView(proj, proj.getAreas().get(i), this);
        }
        for(; i<30; i++) {
//            System.out.println("area : null - " + i);
            areas[i] = new AreaView(null, null, this);
        }
        
//        initOkBtn();
        initCancelProjBtn(r);
        
        cancelBtn.setText("Close");
        cancelBtn.getPanel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if(btnsDisabled) {
                    return;
                }
                
                if(r.getMenu().isMenuDisabled()) {
                    r.getMenu().setMenuDisabled(false);
                }
                
                if (acceptProjState) {
                    removeBtns();                    
                    acceptProjState = false;
                    
                    if(!proj.getDevelopers().isEmpty()) {
                        ListMenuBar menuBar = (ListMenuBar) (r.getMenu());

                        Player.getInstance().acceptProject(proj);
                        r.remove(fromBtn.getButtonPanel());

                        menuBar.removeItem(fromBtn);
                    }
                    
                    r.closeDetailPanel();
                }
            }
        });
    }
    
    private void initCancelProjBtn(final RoomView room) {
        cancelProj = new DtvButton("Cancel Project", CANCEL_PROJ_X) {
            @Override
            protected void onClick() {
                if(btnsDisabled || acceptProjState) {
                    return;
                }
                
//                System.out.println("Cancel this project");
                ListMenuBar menuBar = (ListMenuBar)(room.getMenu());
                
                Player.getInstance().cancelProject(proj.getID() + "");
                room.remove(fromBtn.getButtonPanel());
            
                menuBar.removeItem(fromBtn);
                menuBar.rearrangeItems();
                room.closeDetailPanel();
            }
        };
        room.add(cancelProj.getPanel());
    }
    
    @Override
    protected void drawContent(Graphics g) {
        int titleBaseX2 = TITLE_BASE_X - 40;
        int titleBaseY2 = TITLE_BASE_Y - 20;
        
        g.setFont(FontUtilities.SEGOE_UI_BOLD_12);
        g.drawString("Cost: $" + proj.getCapital(), 300, 50);
        g.drawString("Areas", titleBaseX2, titleBaseY2);
        g.drawString("FPs", titleBaseX2 + 150, titleBaseY2);
        
        g.setFont(FontUtilities.SEGOE_UI_12);
        for (int i = 0, strY = titleBaseY2 + SMALL_FONT_SIZE + 10;
                                    i < proj.getAreas().size() && i < 15; i++) {
            areas[i].drawArea(g, titleBaseX2, strY);
            strY += AREA_HEIGHT;
        }
        
        if (proj.getAreas().size() > 15) {
            int x1 = titleBaseX2 + 400;
            int x2 = x1 + 150;

            for (int i = 15, strY = titleBaseY2 + SMALL_FONT_SIZE + 10;
                                              i < proj.getAreas().size(); i++) {
                areas[i].drawArea(g, x1, strY);
                strY += AREA_HEIGHT;
            }

//            g2.setFont(new Font("", Font.BOLD, SMALL_FONT_SIZE));
            g.setFont(FontUtilities.SEGOE_UI_BOLD_12);
            g.drawString("Areas", x1, titleBaseY2);
            g.drawString("FPs", x2, titleBaseY2);
//            g.drawString("Progress", x2 + 70, titleBaseY2);
        }
        
//        g.setFont(new Font("", Font.BOLD, FONT_SIZE));
        g.setFont(FontUtilities.SEGOE_UI_BOLD_12);
//        drawOkBtn(g);
        
        if(!acceptProjState) {
            cancelProj.drawButton(g, x);
        }
        
        if(currState==ASSIGN_DEV_STATE || currState==REMOVE_DEV_STATE) {
            devList.drawList(g);
        }
    }
    
    /**
     * Draw the assign button using the current graphic context.
     * @param g The current graphic context
     */
//    protected void drawOkBtn(Graphics g) {
//        Graphics g2 = (Graphics)(g.create());
//        if(okHover) {
//            g2.setColor(Color.WHITE);
//            g2.fillRect(ASSIGN_X, BUTTON_Y, CANCEL_WIDTH, BUTTON_HEIGHT);
//            g2.setColor(Color.BLACK);
//        }
//        
//        ok.setBounds(ASSIGN_X+x, BUTTON_Y, CANCEL_WIDTH, BUTTON_HEIGHT);
//        g2.drawRect(ASSIGN_X, BUTTON_Y, CANCEL_WIDTH, BUTTON_HEIGHT);
//        g2.setFont(FontUtilities.SEGOE_UI_SEMIBOLD_17);
//        g2.drawString("Assign", ASSIGN_X + 25, BUTTON_Y + 21);
//    }
    
    /**
     * Draw the cancel project button using the current graphic context.
     * @param g The current graphic context
     */
//    protected void drawCancelProjBtn(Graphics g) {
//        Graphics g2 = (Graphics)(g.create());
//        if(canProHover) {
//            g2.setColor(Color.WHITE);
//            g2.fillRect(CANCEL_PROJ_X, BUTTON_Y, 
//                       CANCEL_PROJ_WIDTH, BUTTON_HEIGHT);
//            g2.setColor(Color.BLACK);
//        }
//        
//        cancelProj.setBounds(CANCEL_PROJ_X+x, BUTTON_Y, 
//                             CANCEL_PROJ_WIDTH, BUTTON_HEIGHT);
//        g2.drawRect(CANCEL_PROJ_X, BUTTON_Y, CANCEL_PROJ_WIDTH, BUTTON_HEIGHT);
//        g2.setFont(FontUtilities.SEGOE_UI_SEMIBOLD_17);
//        g2.drawString("Cancel Project", CANCEL_PROJ_X + 25, BUTTON_Y + 21);
//    }
    
    public void assignDeveloper(final Area a) {
        final Player player = Player.getInstance();
        final RoomView r = (RoomView) (MainFrame.getInstance().getCurrPanel());
        setCurrState(ASSIGN_DEV_STATE);
        disableAreaViewBtns(true);
        
        devList = new DevListView(r, this, a, player.getAvailableDev()) {
            @Override
            public void okClicked() {
                if(!selectedItems.isEmpty()) {
                    Project proj = a.getProject();
                    
                    player.assignDeveloper(selectedItems, a);
                    
                    selectedItems.clear();
                }
                
                removeComponents();
                currDetailPanel.backToNormalState();
                
                if(acceptProjState) {
                    r.getMenu().setMenuDisabled(true);
                }
            }            
        };
//        r.repaint();
    }
    
    public void removeDeveloper(Area area) {
        final RoomView r = (RoomView) (MainFrame.getInstance().getCurrPanel());
        
        setCurrState(REMOVE_DEV_STATE);
        disableAreaViewBtns(true);
        devList = new DevListView(r, this, area, 
                            new ArrayList<Developer>(area.getDevs().values())) {
            @Override
            public void okClicked() {
//                System.out.println("AREA: " + area.getName());
                if(!selectedItems.isEmpty()) {
                    for(Developer d : selectedItems) {
//                        d.getCurrProj().removeDeveloper(d.getName());
                        d.getCurrArea().removeDev(d);
                    }
                    selectedItems.clear();
                }
                
                removeComponents();
                currDetailPanel.backToNormalState();
                
                if(acceptProjState) {
                    r.getMenu().setMenuDisabled(true);
                }
            }            
        };
//        r.repaint();
    }
    
    protected void disableAreaViewBtns(boolean disabled) {
        for(AreaView av : areas) {
            av.setBtnsDisabled(disabled);
        }
    }
    
    public void backToNormalState() {
//        btnsDisabled = false;
        currState = NORMAL_STATE;
        disableAreaViewBtns(false);
//        room.getMenu().setMenuDisabled(false);
//        MainFrame.getInstance().getCurrPanel().repaint();
        devList = null;
    }
       
    @Override
    public void addBackBtns() {
        JPanel room = MainFrame.getInstance().getCurrPanel();
        for(AreaView av : areas) {
            av.addBackBtns();
        }
        
//        room.add(ok);
        room.add(cancelProj.getPanel());
        room.add(cancelBtn.getPanel());
    }
    
//    @Override
    public void removeBtns() {
        JPanel room = MainFrame.getInstance().getCurrPanel();
        for(AreaView av : areas) {
            av.removeBtns();
        }
        
        room.remove(cancelProj.getPanel());
        room.remove(cancelBtn.getPanel());
    }
    
    @Override
    public void setFromBtn(ListMenuBtn fromBtn) {
        super.setFromBtn(fromBtn);
        proj = (Project)(this.fromBtn.getModel());
        name = proj.getName();
        
        int i;
        for(i=0; i<proj.getAreas().size(); i++) {
            areas[i].setProj(proj);
            areas[i].setModel(proj.getAreas().get(i));
        }
        for(; i<30; i++) {
            areas[i].setProj(null);
            areas[i].setModel(null);
        }
    }

    public boolean isAcceptProjState() {
        return acceptProjState;
    }

    public void setAcceptProjState(boolean acceptProjState) {
        this.acceptProjState = acceptProjState;
    }
    
}
