/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.detailview;

import view.detailview.button.DtvButton;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import model.project.Project;
import org.pushingpixels.trident.swing.SwingRepaintTimeline;
import view.FontUtilities;
import view.MainFrame;
import view.ViewUtilities;
import view.menubar.ListMenuBtn;
import view.roomproperties.RoomView;

/**
 *
 * @author s3342128
 */
public class NewProjDetail extends ProjDetailPanel {

    public static final int AREA_HEIGHT = 19;
    public static final int ACCEPT_X = 530; //450
    
//    private static int currState;
//    
//    private Project proj;
//    private DevListView devList;
//    private JPanel acceptPanel;    
//    private boolean acceptHover;
    private DtvButton acceptBtn;

    public NewProjDetail(ListMenuBtn fromBtn,
                                               SwingRepaintTimeline repaintTL) {
        super(fromBtn, repaintTL);
        proj = (Project)(this.fromBtn.getModel());
        name = proj.getName();
        namePanel = new ImageIcon(ViewUtilities.NAME_PANEL_PATH).getImage();
        titles = new String[]{"Cost:", "Level:", "Type:", "Details:"};
        
        final RoomView r = (RoomView)(MainFrame.getInstance().getCurrPanel());
        acceptBtn = new DtvButton("Accept", ACCEPT_X) {
            @Override
            protected void onClick() {
                onAcceptBtnClick(r);
            }
        };       
        r.add(acceptBtn.getPanel());
    }

    @Override
    protected void drawContent(Graphics g) {
//        g.setFont(new Font("", Font.BOLD, FONT_SIZE));
        g.setFont(FontUtilities.SEGOE_UI_BOLD_14);
        for(int i=0, y=TITLE_BASE_Y; i<titles.length; i++) {
            g.drawString(titles[i], TITLE_BASE_X, y);
            y += TITLE_HEIGHT;
        }
        
//        FontMetrics fm = g.getFontMetrics(new Font("", Font.BOLD, FONT_SIZE));
        FontMetrics fm = g.getFontMetrics(FontUtilities.SEGOE_UI_BOLD_14);

//        g2.translate(x, 0);

//        g.setFont(new Font("", Font.PLAIN, FONT_SIZE));
        g.setFont(FontUtilities.SEGOE_UI_14);
        g.drawString("$" + proj.getCapital(),
                TITLE_BASE_X + fm.stringWidth(titles[0]) + 10,
                TITLE_BASE_Y);
        g.drawString("" + proj.getLevel(),
                TITLE_BASE_X + fm.stringWidth(titles[1]) + 10,
                TITLE_BASE_Y + TITLE_HEIGHT);
        g.drawString(proj.getMainSkill().getName(),
                TITLE_BASE_X + fm.stringWidth(titles[1]) + 10,
                TITLE_BASE_Y + 2 * TITLE_HEIGHT);

//        g.setFont(new Font("", Font.PLAIN, SMALL_FONT_SIZE));
        g.setFont(FontUtilities.SEGOE_UI_14);

//        int x1 = TITLE_BASE_X + fm.stringWidth(titles[3]) + 10;
        int x1 = 100;
        int x2 = x1 + 160;
        
        for (int i = 0, strY = TITLE_BASE_Y + 5 * TITLE_HEIGHT - 25;
                i < proj.getAreas().size() && i < 10; i++) {
            boolean known = proj.getAreas().get(i).isKnown();
            
            g.drawString(proj.getAreas().get(i).getName(), x1, strY);
            if (known) {
                g.drawString(proj.getAreas().get(i).getFP() + "", x2, strY);
            } else {
                g.drawString("???" + "", x2, strY);
            }
            strY += AREA_HEIGHT;
        }

        if (proj.getAreas().size() > 10) {
            int x11 = x1 + 220;
            int x22 = x2 + 220;

            for (int i = 10, strY = TITLE_BASE_Y + 5 * TITLE_HEIGHT - 25;
                    i < proj.getAreas().size() && i < 20; i++) {
                boolean known = proj.getAreas().get(i).isKnown();
                
                g.drawString(proj.getAreas().get(i).getName(), x11, strY);                
                if (known) {
                    g.drawString(proj.getAreas().get(i).getFP() + "", x22, strY);
                } else {
                    g.drawString("???" + "", x22, strY);
                }
                strY += AREA_HEIGHT;
            }
            
            if(proj.getAreas().size() > 20){
                int x111 = x11 + 220;
                int x222 = x22 + 220;
                
                for (int i = 20, strY = TITLE_BASE_Y + 5 * TITLE_HEIGHT - 25;
                        i < proj.getAreas().size(); i++) {
                    boolean known = proj.getAreas().get(i).isKnown();

                    g.drawString(proj.getAreas().get(i).getName(), x111, strY);                
                    if (known) {
                        g.drawString(proj.getAreas().get(i).getFP() + "", x222, strY);
                    } else {
                        g.drawString("???" + "", x222, strY);
                    }
                    strY += AREA_HEIGHT;
                }
                
                g.setFont(FontUtilities.SEGOE_UI_BOLD_14);
                g.drawString("Areas", x111, TITLE_BASE_Y + 4 * TITLE_HEIGHT - 10);
                g.drawString("FPs", x222,
                        TITLE_BASE_Y + 4 * TITLE_HEIGHT - 10);
            }

//            g.setFont(new Font("", Font.BOLD, SMALL_FONT_SIZE));
            g.setFont(FontUtilities.SEGOE_UI_BOLD_14);
            g.drawString("Areas", x11, TITLE_BASE_Y + 4 * TITLE_HEIGHT - 10);
            g.drawString("FPs", x22,
                    TITLE_BASE_Y + 4 * TITLE_HEIGHT - 10);
        }

//        g.setFont(new Font("", Font.BOLD, SMALL_FONT_SIZE));
        g.setFont(FontUtilities.SEGOE_UI_BOLD_14);
        g.drawString("Areas", x1, TITLE_BASE_Y + 4 * TITLE_HEIGHT - 10);
        g.drawString("FPs", x2, TITLE_BASE_Y + 4 * TITLE_HEIGHT - 10);
        
//        drawAcceptButton(g);
        acceptBtn.drawButton(g, x);
    }
   
//    private void drawAcceptButton(Graphics g) {
////        if(RoomView.getCurrState() == RoomView.AVAI_PROJ_STATE) {
//            g.setFont(FontUtilities.SEGOE_UI_SEMIBOLD_17);
//            if(acceptHover) {
//                g.setColor(Color.WHITE);
//                g.fillRect(ACCEPT_X, BUTTON_Y, CANCEL_WIDTH, BUTTON_HEIGHT);
//                g.setColor(Color.BLACK);
//            }
//
//            acceptPanel.setBounds(ACCEPT_X+x, BUTTON_Y, 
//                                  CANCEL_WIDTH, BUTTON_HEIGHT);
//            g.drawRect(ACCEPT_X, BUTTON_Y, CANCEL_WIDTH, BUTTON_HEIGHT);
//            g.drawString("Accept", ACCEPT_X + 25, BUTTON_Y + 21);
////        }
//    }
    
    private void onAcceptBtnClick(RoomView room) {
        if (btnsDisabled) {
            return;
        }
        
        room.getMenu().setMenuDisabled(true);
        room.acceptProject(fromBtn);
    }
    
    @Override
    public void addBackBtns() {
        JPanel r = MainFrame.getInstance().getCurrPanel();
        r.add(acceptBtn.getPanel());
        r.add(cancelBtn.getPanel());
    }
    
//    @Override
//    public void removeBtns() {
//        
//    }
    
//    public void setProj(Project proj) {
//        this.proj = proj;
//    }
    
    public static int getCurrState() {
        return currState;
    }

    public static void setCurrState(int currState) {
        NewProjDetail.currState = currState;
    }
}
