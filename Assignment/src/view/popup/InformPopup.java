/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.popup;

import java.awt.Graphics;
import view.FontUtilities;
import view.MainFrame;
import view.detailview.DetailsPanel;
import view.menubar.ListMenuBar;
import view.menubar.ListMenuBtn;
import view.roomproperties.RoomView;

/**
 *
 * @author benjamin
 */
public class InformPopup extends AbstractPopup{
    
//    private final int X_STRING = 240;
    private final int Y_STRING = 230;
    private final int YES_X = 540;
    private final int NO_X = YES_X + 120;
    private final int BUTTON_Y = 310;
    
    private PopupButton ok;
    private String msg; // The message in this dialog
    private int msgX;

    public InformPopup(String msg, int mx, final DetailsPanel panel) {
        this.msg = msg;
        msgX = mx;
        
        ok = new PopupButton("Ok", NO_X, BUTTON_Y) {
            @Override
            protected void onButtonClicked() {
                MainFrame.getInstance().switchBack();
                final RoomView r = (RoomView)(MainFrame.getInstance().getCurrPanel());
                ((ListMenuBar)(r.getSubmenu())).removeItem(panel.getFromBtn());
                ((ListMenuBar)(r.getSubmenu())).rearrangeItems();
                if (((ListMenuBar)(r.getSubmenu())).getType() == ListMenuBar.STORAGE) {
                    r.closeDetailPanel();
                } else {
                    r.openYourTableDetail(panel.getParentFromBtn());
                }
            }
        };
        
        add(ok.getPanel());
    }
    
    public InformPopup(String msg, int mx, final int index) {
        this.msg = msg;
        msgX = mx;
        
        ok = new PopupButton("Ok", NO_X, BUTTON_Y) {
            @Override
            protected void onButtonClicked() {
                MainFrame.getInstance().switchBack();
                final RoomView r = (RoomView)(MainFrame.getInstance().getCurrPanel());
                ((ListMenuBar)(r.getSubmenu())).removeItem((ListMenuBtn)((ListMenuBar)r.getSubmenu()).getButtons().get(index));
                ((ListMenuBar)(r.getSubmenu())).rearrangeItems();
            }
        };
        
        add(ok.getPanel());
    }
    
    public InformPopup(String msg, int mx) {
        this.msg = msg;
        msgX = mx;
        
        ok = new PopupButton("Ok", NO_X, BUTTON_Y) {
            @Override
            protected void onButtonClicked() {
                MainFrame.getInstance().switchBack();
                onClick();
            }
        };
        
        add(ok.getPanel());
    }
    
    public void onClick() {
    }
    
    @Override
    protected void drawContent(Graphics g) {
        g.setFont(FontUtilities.SEGOE_UI_LIGHT_24);
        g.drawString(msg, msgX, Y_STRING + 20);

        ok.drawButton(g);
    }
}
