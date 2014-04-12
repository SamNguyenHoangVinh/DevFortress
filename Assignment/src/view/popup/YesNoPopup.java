/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.popup;

import java.awt.Graphics;
import view.FontUtilities;
import view.MainFrame;

/**
 *
 * @author s3342128
 */
public abstract class YesNoPopup extends AbstractPopup {
    
    public static final int BUY_HARDWARE_MSG_X = 240;
    
//    private final int X_STRING = 240;
    private final int Y_STRING = 230;
    private final int YES_X = 540;
    private final int NO_X = YES_X + 120;
    private final int BUTTON_Y = 310;
    
    protected PopupButton yes;
    protected PopupButton no;
    protected String msg; // The message in this dialog
    protected String question; // The question in this dialog
    protected int msgX;

    public YesNoPopup(String msg, String question, int mx) {
        this.msg = msg;
        this.question = question;
        msgX = mx;
        
        yes = new PopupButton("Yes", YES_X, BUTTON_Y) {
            @Override
            protected void onButtonClicked() {
                onYesClicked();
            }
        };
        
        no = new PopupButton("No", NO_X, BUTTON_Y) {
            @Override
            protected void onButtonClicked() {
                System.out.println("NO clicked");
                MainFrame.getInstance().switchBack();
            }
        };
        
        add(yes.getPanel());
        add(no.getPanel());
    }
    
    @Override
    protected void drawContent(Graphics g) {
        g.setFont(FontUtilities.SEGOE_UI_LIGHT);
        g.drawString(msg, msgX, Y_STRING);
        g.setFont(FontUtilities.SEGOE_UI_LIGHT_24);
        g.drawString(question, msgX, Y_STRING + 40);

        yes.drawButton(g);
        no.drawButton(g);
    }
    
    public abstract void onYesClicked();
}