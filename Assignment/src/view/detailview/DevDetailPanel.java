/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.detailview;

import view.detailview.button.DtvButton;
import java.awt.*;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import model.Developer;
import model.area.Area;
import model.player.Player;
import org.pushingpixels.trident.swing.SwingRepaintTimeline;
import view.FontUtilities;
import view.MainFrame;
import view.ViewUtilities;
import view.menubar.ListMenuBar;
import view.menubar.ListMenuBtn;
import view.roomproperties.RoomView;

/**
 *
 * @author s3342128
 */
public class DevDetailPanel extends DetailsPanel {
    
    public static final int SKILL_HEIGHT = 25;
    public static final int STOP_WORK_X = 387;
    public static final int STOP_WORK_WIDTH = 150;
    public static final int FIRE_X = 530;
    public static final int FIRE_WIDTH = 100;
    
    private Developer dev;
    private DtvButton stopWorkBtn;
    private DtvButton fireBtn;
    private SkillView sklViews[];
    
    public DevDetailPanel(ListMenuBtn fromBtn, SwingRepaintTimeline repaintTL) {
        super(fromBtn, repaintTL);
        final RoomView r = (RoomView)(MainFrame.getInstance().getCurrPanel());
        dev = (Developer)(this.fromBtn.getModel());
        name = dev.getName();
        namePanel = new ImageIcon(ViewUtilities.NAME_PANEL_PATH).getImage();
        titles = new String[]{ "Name:", "Salary:", "Skills:" };
        sklViews = new SkillView[9];
        
        int strY = TITLE_BASE_Y+2*TITLE_HEIGHT;
        for(int i=0; i<dev.getSkills().size(); i++) {
            sklViews[i] = new SkillView(dev, 
                                        dev.getSkills().getList().get(i), strY);            
            strY += SKILL_HEIGHT;
        }
        
        if(dev.getSkills().size() < 9) {
            for(int i=dev.getSkills().size(); i<9; i++) {
                sklViews[i] = new SkillView(null, null, strY);            
                strY += SKILL_HEIGHT;
            }
        }
        
        initStopWorkBtn(r);
        initFireBtn(r);
    }
    
    private void initStopWorkBtn(final RoomView r) {
        stopWorkBtn = new DtvButton("Stop working", STOP_WORK_X) {
            @Override
            protected void onClick() {
                if(btnsDisabled || dev.isAvailable()) {
                    return;
                }
                
                try {
//                    dev.getCurrProj().removeDeveloper(dev.getName());
                    Area a = dev.getCurrArea();
                    a.removeDev(dev);
                } catch(Exception ex) {
                    for(SkillView sv : sklViews) {
                        sv.stopTraining();
                    }
                }
//                r.repaint();
            }
        };
        r.add(stopWorkBtn.getPanel());
    }
    
    private void initFireBtn(final RoomView r) {
        fireBtn = new DtvButton("Fire", FIRE_X) {
            @Override
            protected void onClick() {
                if(btnsDisabled) {
                    return;
                }
                
                System.out.println("FIRE");                
                Player.getInstance().removeDeveloper(dev);
                ((ListMenuBar)(r.getMenu())).removeItem(fromBtn);
                ((ListMenuBar)(r.getMenu())).rearrangeItems();
                
                r.closeDetailPanel();                
//                r.repaint();
            }
        };
        r.add(fireBtn.getPanel());
    }

    @Override
    protected void drawContent(Graphics g) {        
        g.setFont(FontUtilities.SEGOE_UI_BOLD_14);
        for(int i=0, y=TITLE_BASE_Y; i<titles.length; i++) {
            g.drawString(titles[i], TITLE_BASE_X, y);
            y += TITLE_HEIGHT;
        }
        
        FontMetrics fm = g.getFontMetrics(FontUtilities.SEGOE_UI_BOLD_14);
        
        g.setFont(FontUtilities.SEGOE_UI_14);
        g.drawString(dev.getName(), 
                          TITLE_BASE_X + fm.stringWidth(titles[0]) + 10, 
                          TITLE_BASE_Y);
        g.drawString("$" + dev.getSalary(), 
                          TITLE_BASE_X + fm.stringWidth(titles[1]) + 10, 
                          TITLE_BASE_Y + TITLE_HEIGHT);
        
//        for(int i=0, strY=TITLE_BASE_Y+2*TITLE_HEIGHT; 
//                                                i<dev.getSkills().size(); i++) {
//            g.drawString(dev.getSkills().getList().get(i).getName(), 
//                          TITLE_BASE_X + fm.stringWidth(titles[2]) + 10, strY);
//            System.out.println(TITLE_BASE_X + 270);
//            g.drawString("Level " + dev.getSkills().getList().get(i).getLevel(), 
//                          TITLE_BASE_X + 270, strY);
//            strY += SKILL_HEIGHT;
//        }
        
        for(SkillView sv : sklViews) {
            sv.drawView(g);
        }
        
        if(dev.isEmployed()) {
            fireBtn.drawButton(g, x);
            
            if(dev.isAvailable()) {
                for(SkillView sv : sklViews) {
                    sv.drawTrainButton(g, x);
                }
            }
            else if(dev.isTraining()) {
                for(SkillView sv : sklViews) {
                    if(sv.getModel() == dev.getTrainingSkl()) {
                        sv.drawTrainButton(g, x);
                    }
                }
            }            
        }
        
        if(!dev.isAvailable()) {
            stopWorkBtn.drawButton(g, x);
        }
    }
    
    @Override
    public void addBackBtns() {
        JPanel r = MainFrame.getInstance().getCurrPanel();
        r.add(fireBtn.getPanel());
        r.add(stopWorkBtn.getPanel());
        r.add(cancelBtn.getPanel());
        
        for(SkillView sv : sklViews) {
            r.add(sv.getTrainBtn().getPanel());
        }
    }
    
    public Developer getDev() {
        return dev;
    }

    public void setDev(Developer dev) {
        this.dev = dev;
    }
    
    @Override
    public void setFromBtn(ListMenuBtn fromBtn) {
        super.setFromBtn(fromBtn);
        dev = (Developer)(this.fromBtn.getModel());
        name = dev.getName();
        
        for(int i=0; i<dev.getSkills().size(); i++) {
            sklViews[i].setDev(dev); 
            sklViews[i].setModel(dev.getSkills().getList().get(i)); 
            if(sklViews[i].getModel() == dev.getTrainingSkl()) {
                sklViews[i].getTrainBtn().setText(SkillView.STOP_TRAIN_TEXT);
            }
            else {
                sklViews[i].getTrainBtn().setText(SkillView.TRAIN_TEXT);
            }
        }
        
        if(dev.getSkills().size() < 9) {
            for(int i=dev.getSkills().size(); i<9; i++) {
                sklViews[i].setDev(null); 
                sklViews[i].setModel(null); 
            }
        }
        
//        MainFrame.getInstance().getCurrPanel().repaint();
    }
   
}
