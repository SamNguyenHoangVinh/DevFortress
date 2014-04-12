/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.detailview;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import model.area.Area;
import model.project.Project;
import view.FontUtilities;
import view.MainFrame;
import view.menubar.AbsMenu;
import view.roomproperties.RoomView;

/**
 *
 * @author s3342128
 */
public class AreaView {
    
    public static final int FP_X = 149;
    public static final int ASSIGN_WIDTH = 60;
    public static final int ASSIGN_HEIGHT = 21;
    public static final int ASSIGN_X = FP_X + 40;
    public static final int REMOVE_WIDTH = 70;
    public static final int REMOVE_HEIGHT = 21;
    public static final int REMOVE_X = ASSIGN_X + ASSIGN_WIDTH + 10;
    
//    private RoomView room;
    private YourProjDetail detailPanel;
    private Project proj;
    private Area model;
    private JPanel assign;
    private JPanel remove;
//    private int x;
//    private int y;
    private boolean btnsDisabled;
//    private boolean assigning;
    private boolean assignHover;
    private boolean removeHover;

    public AreaView(Project proj, Area model, YourProjDetail d) {
        this.proj = proj;
        this.model = model;
//        room = r;
        detailPanel = d;
        assign = new JPanel();
        remove = new JPanel();
        
        initAssignBtn();
        initRemoveBtn();
    }
    
    private void initAssignBtn() {
        assign.setOpaque(false);
        assign.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                assignClicked();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                System.out.println(btnsDisabled + " - " + (model==null));
                if(isAssignDisabled()) {
                    return;
                }
                assignHover = true;
//                MainFrame.getInstance().getCurrPanel().repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(isAssignDisabled()) {
                    return;
                }
                assignHover = false;
//                MainFrame.getInstance().getCurrPanel().repaint();
            }            
        });
        MainFrame.getInstance().getCurrPanel().add(assign);
    }
    
    private void assignClicked() {
//        System.out.println("AREAVIEW Assign: " + model.getName());
        if (isAssignDisabled()) {
            return;
        }

        RoomView r = (RoomView)(MainFrame.getInstance().getCurrPanel());
        r.getMenu().setMenuDisabled(true);
        assignHover = false;
        detailPanel.assignDeveloper(model);
    }
    
    private void initRemoveBtn() {        
        remove.setOpaque(false);
        remove.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                removeClicked();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if(isRemoveDisabled()) {
                    return;
                }
                removeHover = true;
//                MainFrame.getInstance().getCurrPanel().repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(isRemoveDisabled()) {
                    return;
                }
                removeHover = false;
//                MainFrame.getInstance().getCurrPanel().repaint();
            }            
        });
        MainFrame.getInstance().getCurrPanel().add(remove);
    }
    
    private void removeClicked() {
//        System.out.println("AREAVIEW Remove: " + model.getName());
        if (isRemoveDisabled()) {
            return;
        }

        RoomView r = (RoomView)(MainFrame.getInstance().getCurrPanel());
        r.getMenu().setMenuDisabled(true);
        removeHover = false;
        detailPanel.removeDeveloper(model);
    }
    
    public void drawArea(Graphics g, int x, int y) {
        if(model == null) {
            return;
        }
        
        g.drawString(model.getName(), x, y);
        
        if(!model.isFinished()) {
            if (model.isKnown()) {
                g.drawString(model.getDone() + "/" + model.getFP(), x + FP_X, y);
            } else {
                g.drawString(model.getDone() + "/???", x + FP_X, y);
            }
        }
        else {
            g.drawString("Done", x + FP_X, y);
        }
        
        if(!model.isFinished()) {
            drawAssignBtn(g, x + ASSIGN_X, y - 15);
        }
        if(!model.getDevs().isEmpty()) {
            drawRemoveBtn(g, x + REMOVE_X, y - 15);
        }
    }
    
    /**
     * Draw the assign button using the current graphic context.
     * @param g The current graphic context
     */
    private void drawAssignBtn(Graphics g, int aX, int btnY) {
        Graphics g2 = (Graphics)(g.create());
        if(assignHover) {
            g2.setColor(Color.WHITE);
            g2.fillRect(aX, btnY, ASSIGN_WIDTH, ASSIGN_HEIGHT);
            g2.setColor(Color.BLACK);
        }
        
        assign.setBounds(aX+AbsMenu.MENU_BAR_WIDTH, btnY, 
                                                ASSIGN_WIDTH, ASSIGN_HEIGHT);
        g2.drawRect(aX, btnY, ASSIGN_WIDTH, ASSIGN_HEIGHT);
        g2.setFont(FontUtilities.SEGOE_UI_BOLD_14);
        g2.drawString("Assign", aX + 10, btnY + 16);
    }
    
    /**
     * Draw the assign button using the current graphic context.
     * @param g The current graphic context
     */
    private void drawRemoveBtn(Graphics g, int aX, int btnY) {
        Graphics g2 = (Graphics)(g.create());
        if(removeHover) {
            g2.setColor(Color.WHITE);
            g2.fillRect(aX, btnY, REMOVE_WIDTH, REMOVE_HEIGHT);
            g2.setColor(Color.BLACK);
        }
        
        remove.setBounds(aX+AbsMenu.MENU_BAR_WIDTH, btnY, 
                                                REMOVE_WIDTH, REMOVE_HEIGHT);
        g2.drawRect(aX, btnY, REMOVE_WIDTH, REMOVE_HEIGHT);
        g2.setFont(FontUtilities.SEGOE_UI_BOLD_14);
        g2.drawString("Remove", aX + 10, btnY + 16);
    }

    public void addBackBtns() {
        JPanel r = MainFrame.getInstance().getCurrPanel();
        r.add(assign);
        r.add(remove);
    }
    
    public void removeBtns() {
        JPanel r = MainFrame.getInstance().getCurrPanel();
        r.remove(assign);
        r.remove(remove);
    }
    
    public boolean isBtnsDisabled() {
        return btnsDisabled;
    }

    public void setBtnsDisabled(boolean btnsDisabled) {
        this.btnsDisabled = btnsDisabled;
    }

//    public boolean isAssigning() {
//        return assigning;
//    }
//
//    public void setAssigning(boolean assigning) {
//        this.assigning = assigning;
//        room.repaint();
//    }
    
    private boolean isRemoveDisabled() {
        return (btnsDisabled || model==null || model.getDevs().isEmpty());
    }
    
    private boolean isAssignDisabled() {
        return (btnsDisabled || model==null || model.isFinished());
    }

    public JPanel getAssign() {
        return assign;
    }

    public Area getModel() {
        return model;
    }

    public void setModel(Area model) {
        this.model = model;
    }

    public Project getProj() {
        return proj;
    }

    public void setProj(Project proj) {
        this.proj = proj;
    }
    
}
