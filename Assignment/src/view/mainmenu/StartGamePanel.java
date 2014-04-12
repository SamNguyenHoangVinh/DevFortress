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
import view.ViewUtilities;

/**
 *
 * @author HungHandsome
 */
public class StartGamePanel extends MainMenuPanel {

    public static final int FIRST_BTN_Y = 155; //The y of the first menu button
//    public static final int FIRST_BTN_Y = 160; //The y of the first menu button
    //Menu button index
    public static final int NEW_GAME_BTN = 0;
    public static final int CONTINUE_BTN = 1;
    public static final int LOAD1_BTN = 2;
//    public static final int LOAD2_BTN = 3;
//    public static final int LOAD3_BTN = 4;
    public static final int BACK_BTN = 3;
    private SwingRepaintTimeline repaintTimeline;
//    private Timer animationStarter;
    private Thread animationStarter;

    public StartGamePanel() {
        img = ScreenCapturer.getScreenShot();
        menuBtns = new MainMenuButton[4];

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
                    case NEW_GAME_BTN:
                        newGameClicked();
                        break;
                    case CONTINUE_BTN:
                        loadGameClicked(SaveLoad.AUTOSAVE_PATH);
                        break;
                    case LOAD1_BTN:
                        loadGameClicked(SaveLoad.SAVE_PATH_1);
                        break;
//                    case LOAD2_BTN:
//                        loadGameClicked(SaveLoad.SAVE_PATH_2);
//                        break;
//                    case LOAD3_BTN:
//                        loadGameClicked(SaveLoad.SAVE_PATH_3);
//                        break;
                    case BACK_BTN:
                        backClicked();
                }
            }
        });
        
//        animationStarter.start();
    }

    private void initBtn() {
        for (int i=0, y=FIRST_BTN_Y; i < menuBtns.length; i++) {
            Image btnImg = null;

            switch (i) {
                case NEW_GAME_BTN:
                    btnImg = new ImageIcon(ViewUtilities.NEW_GAME_BTN).getImage();
                    break;
                case CONTINUE_BTN:
                    btnImg = new ImageIcon(ViewUtilities.CONTINUE_BTN).getImage();
                    break;
                case LOAD1_BTN:
                    btnImg = new ImageIcon(ViewUtilities.LOAD1_BTN).getImage();
                    break;
//                case LOAD2_BTN:
//                    btnImg = new ImageIcon(ViewUtilities.LOAD2_BTN).getImage();
//                    break;
//                case LOAD3_BTN:
//                    btnImg = new ImageIcon(ViewUtilities.LOAD3_BTN).getImage();
//                    break;
                case BACK_BTN:
                    btnImg = new ImageIcon(ViewUtilities.BACK_MENU_BTN).getImage();
            }

            menuBtns[i] = new MainMenuButton(btnImg, repaintTimeline);
            final int idx = i;
            y += MainMenuButton.BTN_GAP + MainMenuButton.BTN_HEIGHT;

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
        for(MainMenuButton b : menuBtns) {
            b.drawButton(g);
        }
    }

    private void newGameClicked() {
        System.out.println("START NEW GAME");
        Player.reinitInstance();
        startGame();
    }

    private void loadGameClicked(String filePath) {
        if (Player.loadPlayer(filePath)) {
            startGame();
        }
    }

    private void backClicked() {
        moveBtnsIn();
        
        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(400);
                } catch(Exception ex) {
                    
                }
                
                MainFrame mf = MainFrame.getInstance();                
                mf.switchBack();
                MainMenu mm = (MainMenu)mf.getCurrPanel();
                mm.moveBtnsOut();
                mm.setBtnDisabled(false);
                
            }            
        }.start();        
    }
    
    private void startGame() {
        MainFrame mainFrame = MainFrame.getInstance();
        ScreenCapturer.captureScreen(mainFrame);
        mainFrame.toStartGame();
    }

    public Thread getAnimationStarter() {
        return animationStarter;
    }
    
}
