/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.mainmenu;

import animation.ScreenCapturer;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import org.pushingpixels.trident.Timeline;
import org.pushingpixels.trident.swing.SwingRepaintTimeline;
import view.MainFrame;
import view.Sound;
import view.SoundUtilities;
import view.ViewUtilities;
import view.logo.Logo;
import view.roomproperties.RoomView;

/**
 * This class represents the main menu of the game.
 * @author Hung
 */
public class MainMenu extends MainMenuPanel {
        
    public static final int FIRST_BTN_Y = 215; //The y of the first menu button
    
    //Menu button index
    public static final int START_GAME_BTN = 0;
    public static final int OPTIONS_BTN = 1;
//    public static final int EXTRA_BTN = 2;
    public static final int ABOUT_BTN = 2;
    public static final int EXIT_BTN = 3;

    private SwingRepaintTimeline repaintTimeline;
//    private Timer animationStarter;
    private Thread animationStarter;
    private Logo logo;    

    public MainMenu() {
        img = new ImageIcon(ViewUtilities.BACKGROUND_IMG).getImage();
        
        repaintTimeline = new SwingRepaintTimeline(this);
        repaintTimeline.setAutoRepaintMode(true);
        repaintTimeline.playLoop(Timeline.RepeatBehavior.LOOP);
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if(btnDisabled) {
                    return;
                }
                
                int x = e.getX();
                int y = e.getY();
                int pos = findCurrMousePosition(x, y);

                switch(pos) {
                    case START_GAME_BTN: startGameBtnClicked(); break;
                    case OPTIONS_BTN: optionsBtnClicked(); break;
//                    case EXTRA_BTN: extraBtnClicked(); break;
                    case ABOUT_BTN: aboutBtnClicked(); break;
                    case EXIT_BTN: exitBtnClicked();
                }                  
            }
        });
        
        displayLogo();
        initBtns();             
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
                
        logo.drawLogo(g);
        
        for(MainMenuButton b : menuBtns) {
            b.drawButton(g);
        }        
    }
    
//    /**
//     * Add mouse move listener
//     */
//    private void initMouseMove() {
//        addMouseMotionListener(new MouseAdapter() {
//            @Override
//            public void mouseMoved(MouseEvent e) {
//                if(btnDisabled) {
//                    return;
//                }
//                
//                int x = e.getX();
//                int y = e.getY();
//                int pos = findCurrMousePosition(x, y);
//                
//                if(prevMousePos==-1 && pos!=-1) {
//                    SoundUtilities.Click1();
//                    menuBtns[pos].moveToRight();
//                }
//                else if((prevMousePos!=-1 && pos==-1)
//                        || (prevMousePos!=-1 && pos!=-1 && prevMousePos!=pos)) {
//                    menuBtns[prevMousePos].moveToLeft();
////                    SoundUtilities.Click1();
//                }
//                
//                prevMousePos = pos;
//            }            
//        });
//    }
    
    /**
     * Display the logo of the game by running an animation.
     */
    private void displayLogo() {
        logo = new Logo(repaintTimeline) {
            @Override
            protected void actAfterAnimation() {
                animationStarter.start();
            }            
        };
        logo.playAnimation();
    }
    
    /**
     * Displays the menu buttons by running an animation.
     */
    private void initBtns() {
        menuBtns = new MainMenuButton[4];
//        final Timer[] animators = new Timer[5];
        
        for(int i=0; i<4; i++) {
            Image btnImg = null;
            
            switch(i) {
                case START_GAME_BTN: 
                    btnImg = new ImageIcon(ViewUtilities.START_GAME_BTN).getImage(); 
                    break;
                case OPTIONS_BTN: 
                    btnImg = new ImageIcon(ViewUtilities.OPTIONS_BTN).getImage(); 
                    break;
//                case EXTRA_BTN: 
//                    btnImg = new ImageIcon(ViewUtilities.EXTRA_BTN).getImage(); 
//                    break;
                case ABOUT_BTN: 
                    btnImg = new ImageIcon(ViewUtilities.ABOUT_BTN).getImage(); 
                    break;
                case EXIT_BTN: 
                    btnImg = new ImageIcon(ViewUtilities.EXIT_BTN).getImage();
            }
            
            menuBtns[i] = new MainMenuButton(btnImg, repaintTimeline);
            final int idx = i;
            final int y = FIRST_BTN_Y + 
                    i*(MainMenuButton.BTN_GAP + MainMenuButton.BTN_HEIGHT);
            
            menuBtns[i].setCoordinatesNotRepaint(-MainMenuButton.BTN_WIDTH, y);
            
//            animators[i] = new Timer(10, new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    if(menuBtns[idx].getBtnX() >= FIRST_BTN_X) {
////                        menuBtns[idx].setCoordinates(FIRST_BTN_X, y);
//                        menuBtns[idx].setCoordinatesNotRepaint(FIRST_BTN_X, y);
////                        menuBtns[idx].addTimeLineProperty();
//                        animators[idx].stop();
////                        animators[idx] = null;
////                        repaint();
//                        return;
//                    }
//                    
//                    int nx = menuBtns[idx].getBtnX() + 40;
//                    
//                    menuBtns[idx].setCoordinatesNotRepaint(nx, y);
//                    
//                    if(idx<EXIT_BTN && nx==-MainMenuButton.getBtnWidth()+80) {
//                        animators[idx + 1].start();
//                    }
//                }
//            });         
        }
        
//        animationStarter = new Timer(1, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (!animators[START_GAME_BTN].isRunning()
//                        && menuBtns[START_GAME_BTN].getBtnX() < FIRST_BTN_X) {
//                    animators[START_GAME_BTN].start();
//                }
//
//                if (!animators[EXIT_BTN].isRunning()
//                        && menuBtns[EXIT_BTN].getBtnX() == FIRST_BTN_X) {
//                    animationStarter.stop();
////                    repaint();
//                    animationStarter = null;
//                    initMouseMove();
////                    return;
//                }
//                
////                repaint();
//            }
//        });
        
        animationStarter = new Thread() {
            @Override
            public void run() {
//                for(MainMenuButton b : menuBtns) {
//                    b.moveOut();
//                    try {
//                        Thread.sleep(100);
//                    } catch(Exception ex) {
//                        
//                    }
//                }
                moveBtnsOut();
                initMouseMove();
            }            
        };        
    }
    
//    /**
//     * Determine whether or not the mouse is in a button. If the mouse is <br/>
//     * in a button, then determine which button the mouse is in.
//     * @param x The current mouse x coordinate.
//     * @param y The current mouse y coordinate.
//     * @return The index of the current button, or -1 if the mouse is not in<br/>
//     * a button.
//     */
//    private int findCurrMousePosition(int x, int y) {
//        if(x>=menuBtns[START_GAME_BTN].getBtnX() 
//           && x<=menuBtns[START_GAME_BTN].getBtnX()+MainMenuButton.BTN_WIDTH
//           && y>=menuBtns[START_GAME_BTN].getBtnY() 
//           && y<=menuBtns[START_GAME_BTN].getBtnY()+MainMenuButton.BTN_HEIGHT) {
////SoundUtilities.Click1();
//            return START_GAME_BTN;
//        }
//        else if(x>=menuBtns[OPTIONS_BTN].getBtnX() 
//           && x<=menuBtns[OPTIONS_BTN].getBtnX()+MainMenuButton.BTN_WIDTH
//           && y>=menuBtns[OPTIONS_BTN].getBtnY() 
//           && y<=menuBtns[OPTIONS_BTN].getBtnY()+MainMenuButton.BTN_HEIGHT) {
////SoundUtilities.Click1();
//            return OPTIONS_BTN;
//        }
//        else if(x>=menuBtns[EXTRA_BTN].getBtnX() 
//           && x<=menuBtns[EXTRA_BTN].getBtnX()+MainMenuButton.BTN_WIDTH
//           && y>=menuBtns[EXTRA_BTN].getBtnY() 
//           && y<=menuBtns[EXTRA_BTN].getBtnY()+MainMenuButton.BTN_HEIGHT) {
////SoundUtilities.Click1();
//            return EXTRA_BTN;
//        }
//        else if(x>=menuBtns[ABOUT_BTN].getBtnX() 
//           && x<=menuBtns[ABOUT_BTN].getBtnX()+MainMenuButton.BTN_WIDTH
//           && y>=menuBtns[ABOUT_BTN].getBtnY() 
//           && y<=menuBtns[ABOUT_BTN].getBtnY()+MainMenuButton.BTN_HEIGHT) {
////SoundUtilities.Click1();
//            return ABOUT_BTN;
//        }
//        else if(x>=menuBtns[EXIT_BTN].getBtnX() 
//           && x<=menuBtns[EXIT_BTN].getBtnX()+MainMenuButton.BTN_WIDTH
//           && y>=menuBtns[EXIT_BTN].getBtnY() 
//           && y<=menuBtns[EXIT_BTN].getBtnY()+MainMenuButton.BTN_HEIGHT) {
////SoundUtilities.Click1();
//            return EXIT_BTN;
//        }
//        return -1;
//    }
    
    /**
     * The event when the Start Game button is clicked
     */
    private void startGameBtnClicked() {
//        System.out.println("START NEW GAME");
//        MainFrame mainFrame = MainFrame.getInstance();
//        ScreenCapturer.captureScreen(mainFrame);
//        mainFrame.toStartGame();
        
        
        
        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(400);
                } catch(Exception ex) {
                    
                }
                ScreenCapturer.captureScreen(MainFrame.getInstance());
                StartGamePanel panel = new StartGamePanel();
                MainFrame.getInstance().switchPanel(panel);
                panel.getAnimationStarter().start();
            }
        }.start();
        
        btnDisabled = true;
        moveBtnsIn();
    }
    
    private void loadGameBtnClicked() {
        MainFrame.getInstance().switchPanel(new LoadGamePanel());
    }
    
    private void optionsBtnClicked() {
        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(400);
                } catch(Exception ex) {
                    
                }
                ScreenCapturer.captureScreen(MainFrame.getInstance());
                OptionGamePanel panel = new OptionGamePanel();
                MainFrame.getInstance().switchPanel(panel);
                panel.getAnimationStarter().start();
            }
        }.start();
        
        btnDisabled = true;
        moveBtnsIn();
    }
    
    private void extraBtnClicked() {
        
    }
    
    private void aboutBtnClicked() {
        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(400);
                } catch(Exception ex) {
                    
                }
                ScreenCapturer.captureScreen(MainFrame.getInstance());
                AboutGamePanel panel = new AboutGamePanel();
                MainFrame.getInstance().switchPanel(panel);
                panel.getAnimationStarter().start();
            }
        }.start();
        
        btnDisabled = true;
        moveBtnsIn();
    }
    
    private void exitBtnClicked() {
        System.exit(0);
    }

    public boolean isSwitchPanel() {
        return btnDisabled;
    }

    public void setSwitchPanel(boolean switchPanel) {
        this.btnDisabled = switchPanel;
    }
    
}