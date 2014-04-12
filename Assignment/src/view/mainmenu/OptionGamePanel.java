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
public class OptionGamePanel extends MainMenuPanel {

    public static final int FIRST_BTN_Y = 155; //The y of the first menu button
    //Menu button index
    public static final int BMG1 = 0;
    public static final int BMG2 = 1;
    public static final int BMG3 = 2;
    public static final int BMG4 = 3;
    public static final int BMG5 = 4;
    public static final int BACK_BTN = 5;
    public static boolean DEFAULT = true;
    private SwingRepaintTimeline repaintTimeline;
//    private Timer animationStarter;
    private Thread animationStarter;

    public OptionGamePanel() {
        img = ScreenCapturer.getScreenShot();
        menuBtns = new MainMenuButton[6];

        repaintTimeline = new SwingRepaintTimeline(this);
        repaintTimeline.setAutoRepaintMode(true);
        repaintTimeline.playLoop(Timeline.RepeatBehavior.LOOP);

        //Sound.prepareBGM();
        System.out.println("Prepare: "+ Sound.prepare);
        //Sound.stopBGMX();
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
                    case BMG1:
                        DEFAULT = false;
                        Sound.stopBGMX();
                        Sound.prepareBGM();
                        BGMClicked(BMG1);
                        break;
                    case BMG2:
                        DEFAULT = false;
                        Sound.stopBGMX();
                        Sound.prepareBGM();
                        BGMClicked(BMG2);
                        break;
                    case BMG3:
                        DEFAULT = false;
                        Sound.stopBGMX();
                        Sound.prepareBGM();
                        BGMClicked(BMG3);
                        break;
                    case BMG4:
                        DEFAULT = false;
                        Sound.stopBGMX();
                        Sound.prepareBGM();
                        BGMClicked(BMG4);
                        break;
                    case BMG5:
                        DEFAULT = false;
                        Sound.stopBGMX();
                        Sound.prepareBGM();
                        BGMClicked(BMG5);
                        break;
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
                case BMG1:
                    btnImg = new ImageIcon(ViewUtilities.BGM1).getImage();
                    break;
                case BMG2:
                    btnImg = new ImageIcon(ViewUtilities.BGM2).getImage();
                    break;
                case BMG3:
                    btnImg = new ImageIcon(ViewUtilities.BGM3).getImage();
                    break;
                case BMG4:
                    btnImg = new ImageIcon(ViewUtilities.BGM4).getImage();
                    break;
                case BMG5:
                    btnImg = new ImageIcon(ViewUtilities.BGM5).getImage();
                    break;
                case BACK_BTN:
                    btnImg = new ImageIcon(ViewUtilities.BACK_MENU_BTN).getImage();
            }

            menuBtns[i] = new MainMenuButton(btnImg, repaintTimeline);
            final int idx = i;
            final int y = FIRST_BTN_Y
                    + i * (MainMenuButton.BTN_GAP + MainMenuButton.BTN_HEIGHT);

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

    private void BGMClicked(int i) {
        //System.out.println("BGM1");
        SoundIntro.stopWAV();
        switch (i) {
            case 0:
                Sound.stopBGMX(1);
                Sound.playBGM1();
                break;
            case 1:
                Sound.stopBGMX(2);
                Sound.playBGM2();
                break;
            case 2:
                Sound.stopBGMX(3);
                Sound.playBGM3();
                break;
            case 3:
                Sound.stopBGMX(4);
                Sound.playBGM4();
                break;
            case 4:
                Sound.stopBGMX(5);
                Sound.playBGM5();
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
