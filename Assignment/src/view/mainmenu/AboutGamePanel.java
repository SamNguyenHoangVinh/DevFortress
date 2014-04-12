/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.mainmenu;

import animation.ScreenCapturer;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import model.player.Player;
import model.saveload.SaveLoad;
import org.pushingpixels.trident.Timeline;
import org.pushingpixels.trident.swing.SwingRepaintTimeline;
import view.MainFrame;
import view.Sound;
import view.SoundIntro;
import view.ViewUtilities;

/**
 *
 * @author HungHandsome
 */
public class AboutGamePanel extends MainMenuPanel {

    public static final int FIRST_BTN_Y = 155; //The y of the first menu button
    //Menu button index
    public static final int BMG5 = 0;
    public static final int BACK_BTN = 1;
    public static boolean DEFAULT = true;
    private SwingRepaintTimeline repaintTimeline;
//    private Timer animationStarter;
    private Thread animationStarter;

    public AboutGamePanel() {
        img = ScreenCapturer.getScreenShot();
        menuBtns = new MainMenuButton[2];

        repaintTimeline = new SwingRepaintTimeline(this);
        repaintTimeline.setAutoRepaintMode(true);
        repaintTimeline.playLoop(Timeline.RepeatBehavior.LOOP);
        
        initBtn();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (btnDisabled) {
                    return;
                }

                int x = e.getX();
                int y = e.getY();
                int pos = findCurrMousePosition(x, y);

                switch (pos) {
                    case BACK_BTN:
                        backClicked();
                }
            }
        });

//        animationStarter.start();
    }

    private void initBtn() {
        for (int i = 0; i < menuBtns.length; i++) {
            Image btnImg = null;

            switch (i) {
                case BMG5:
                    btnImg = new ImageIcon(ViewUtilities.ABOUT_DES).getImage();
                    break;
                case BACK_BTN:
                    btnImg = new ImageIcon(ViewUtilities.BACK_MENU_BTN).getImage();
            }

            menuBtns[i] = new MainMenuButton(btnImg, repaintTimeline);
            final int idx = i;
            final int y = FIRST_BTN_Y
                    + i * (280 + MainMenuButton.BTN_HEIGHT);

            menuBtns[i].setCoordinatesNotRepaint(-MainMenuButton.BTN_WIDTH, y);
        }

        animationStarter = new Thread() {
            @Override
            public void run() {
//                for(MainMenuButton b : menuBtns) {
//                    b.moveOut();
//                    try {
//                        Thread.sleep(100);
//                    } catch (Exception ex) {
//                    }
//                }
                moveBtnsOut();
                initMouseMove();
            }
        };
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (MainMenuButton b : menuBtns) {
            b.drawButton(g);
        }
    }

    private void backClicked() {
        moveBtnsIn();

        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(400);
                } catch (Exception ex) {
                }

                MainFrame mf = MainFrame.getInstance();
                mf.switchBack();
                MainMenu mm = (MainMenu) mf.getCurrPanel();
                mm.moveBtnsOut();
                mm.setBtnDisabled(false);

            }
        }.start();
    }

    public Thread getAnimationStarter() {
        return animationStarter;
    }
}
