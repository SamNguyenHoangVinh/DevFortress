/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.detailview;

import java.awt.Graphics;
import javax.swing.ImageIcon;
import model.project.Project;
import org.pushingpixels.trident.swing.SwingRepaintTimeline;
import view.ViewUtilities;
import view.menubar.ListMenuBtn;

/**
 *
 * @author s3342128
 */
public abstract class ProjDetailPanel extends DetailsPanel {
    
    public static final int NORMAL_STATE = 0;    
    
    protected static int currState;
        
    protected Project proj;
    
    public ProjDetailPanel(ListMenuBtn fromBtn, 
                                               SwingRepaintTimeline repaintTL) {
        super(fromBtn, repaintTL);
        proj = (Project)(fromBtn.getModel());
        currState = NORMAL_STATE;  
        name = proj.getName();
        namePanel = new ImageIcon(ViewUtilities.NAME_PANEL_PATH).getImage();
    }
    
    @Override
    protected abstract void drawContent(Graphics g);

    @Override
    public abstract void addBackBtns();
    
    @Override
    public void setFromBtn(ListMenuBtn fromBtn) {
        super.setFromBtn(fromBtn);
        proj = (Project)(this.fromBtn.getModel());
        name = proj.getName();
    }
    
    public Project getProj() {
        return proj;
    }

    public static int getCurrState() {
        return currState;
    }

    public static void setCurrState(int currState) {
        ProjDetailPanel.currState = currState;
    }
    
}
