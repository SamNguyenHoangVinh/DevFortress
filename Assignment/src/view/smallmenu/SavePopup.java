/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.smallmenu;

import animation.ScreenCapturer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.JPanel;
import model.player.Player;
import model.saveload.SaveLoad;
import view.FontUtilities;
import view.MainFrame;
import view.ViewUtilities;
import view.popup.AbstractPopup;
import view.popup.PopupButton;
import view.popup.YesNoPopup;

/**
 *
 * @author Hung
 */
public class SavePopup extends JPanel  {

//    public static final int M1_X = 240;
    private static final int M1_Y = 240;
//    public static final int M2_X = ;
    private static final int M2_Y = 265;
    private static final int SLOT_X = 200;
    private static final int SLOT_Y = 290;
    private static final int SLOT_WIDTH = 50;
    private static final int SLOT_HEIGHT = 50;
    
    private final int YES_X = 540;
    private final int NO_X = YES_X + 120;
    private final int BUTTON_Y = 310;
    
    private static final String message1 = "Please choose 1 slot to save.";
    private static final String message2 = 
            "Note that old save will be overwriten by the new save in the same slot.";
    
    private JPanel[] slots = {new JPanel(), new JPanel(), new JPanel()};
    protected PopupButton ok;
    protected PopupButton cancel;
    private String selectedPath = null;
    
    public SavePopup() {
        ok = new PopupButton("Ok", YES_X, BUTTON_Y) {
            @Override
            protected void onButtonClicked() {
                onYesClicked();
            }
        };
        
        cancel = new PopupButton("Cancel", NO_X, BUTTON_Y) {
            @Override
            protected void onButtonClicked() {
                System.out.println("NO clicked");
                MainFrame.getInstance().switchBack();
            }
        };
        
        add(ok.getPanel());
        add(cancel.getPanel());
        
        for(int i=0; i<slots.length; i++) {
            final int idx = i;
            slots[i].setOpaque(false);
            slots[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
//                    SaveLoad.save(Player.getInstance(), 
//                                    "src/data/save" + (idx+1) + ".bin");
                    selectedPath = "src/data/save" + (idx+1) + ".bin";
                }                
            });
            add(slots[i]);
        }
        
        
        
        setLayout(null);
        setPreferredSize(
                new Dimension(MainFrame.PANEL_WIDTH, MainFrame.PANEL_HEIGHT));
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics g2 = FontUtilities.renderer(g);
        
        g2.drawImage(ScreenCapturer.getScreenShot(), 0, 0, null);
        g2.drawImage(ViewUtilities.DIALOG_IMG, 0, 0, 
                                               getWidth(), getHeight(), null);
        
        g2.setColor(Color.white);        
        g.setFont(FontUtilities.loadFont(FontUtilities.ROBOTO_THIN_PATH, 90));
        
        ok.drawButton(g2);
        cancel.drawButton(g2);
    }
    
    private boolean fileExisted(String fileName) {
        return new File(fileName).exists();
    }

    
    public void onYesClicked() {
        SaveLoad.save(Player.getInstance(), selectedPath);
        MainFrame.getInstance().switchBack();
    }
}
