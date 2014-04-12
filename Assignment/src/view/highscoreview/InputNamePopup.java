/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.highscoreview;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import model.player.Player;
import model.player.PlayerRank;
import model.saveload.SaveLoad;
import view.FontUtilities;
import view.MainFrame;
import view.popup.AbstractPopup;
import view.popup.PopupButton;

/**
 *
 * @author HungHandsome
 */
public class InputNamePopup extends AbstractPopup {

    public static final int MAX_CHAR = 20;
//    public static final int TITLE_X = 260;
    public static final int TITLE_Y = 240;
//    public static final int FIRST_LINE_X = 300;
    public static final int FIRST_LINE_Y = 275;
    public static final int SECOND_LINE_Y = 299;
    public static final int TEXT_FIELD_WIDTH = 200;
    public static final int TEXT_FIELD_HEIGHT = 25;
    
    public static final String TITLE = "Congratulation";
    public static final String FIRST_LINE =
            "You've made a new record in the Top 10 Players";
    public static final String SECOND_LINE = "Please enter your name";
    
    private CustomTextField nameTf;
    private PopupButton ok;

    public InputNamePopup() {
        nameTf = new CustomTextField();

        ok = new PopupButton("Ok", 750, 320) {
            @Override
            protected void onButtonClicked() {
                Player p = Player.getInstance();
                String name = nameTf.getText().isEmpty() ? 
                                                   "Default" : nameTf.getText();                
                p.setName(name);
                PlayerRank rank = PlayerRank.getInstance();
                rank.addPlayer(p);
                MainFrame.getInstance().switchBack();
                SaveLoad.saveRank(rank);
                SaveLoad.deleteSave(SaveLoad.AUTOSAVE_PATH);
            }
        };

        add(ok.getPanel());

        setLayout(null);

        nameTf.setPreferredSize(new Dimension(TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT));
        nameTf.setBounds((MainFrame.PANEL_WIDTH - TEXT_FIELD_WIDTH) / 2, 315,
                TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);

        nameTf.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                return ((JTextField) input).getText().length() == MAX_CHAR;
            }
        });

        add(nameTf);
    }

//    @Override
//    protected void paintComponent(Graphics g) {
//        g.drawImage(ViewUtilities.HIGHSCORE_PANEL, 0, 0, 
//                getWidth(), getHeight(), null);
//        g.drawImage(ViewUtilities.DIALOG_IMG, 0, 0, 
//                getWidth(), getHeight(), null);
//        
//        g.setFont(FontUtilities.);
//    }   
    @Override
    protected void drawContent(Graphics g) {
        FontMetrics fm = g.getFontMetrics();

        g.setFont(FontUtilities.loadFont(FontUtilities.ROBOTO_THIN_PATH, 60));
        g.drawString(TITLE,
                (MainFrame.PANEL_WIDTH - fm.stringWidth(TITLE)) / 2 + 90, TITLE_Y);

        g.setFont(FontUtilities.SEGOE_UI_17);
        fm = g.getFontMetrics();

        g.drawString(FIRST_LINE,
                (MainFrame.PANEL_WIDTH - fm.stringWidth(FIRST_LINE)) / 2,
                FIRST_LINE_Y);
        g.drawString(SECOND_LINE,
                (MainFrame.PANEL_WIDTH - fm.stringWidth(SECOND_LINE)) / 2,
                SECOND_LINE_Y);

        ok.drawButton(g);
    }

    private class CustomTextField extends JTextField {
        @Override
        protected Document createDefaultModel() {
            return new RestrictLenDoc();
        }
    }

    static class RestrictLenDoc extends PlainDocument {
        @Override
        public void insertString(int offs, String str, AttributeSet a) {
            try {
                if ((str.length()+getLength()) <= MAX_CHAR) {
                    super.insertString(offs, str, a);
                }
            } catch(Exception ex) {
                
            }
        }
    }
}
