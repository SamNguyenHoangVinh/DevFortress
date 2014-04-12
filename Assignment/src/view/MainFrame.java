/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import animation.IntroducingAnimation;
import animation.Introduction;
import animation.ScreenCapturer;
import animation.SlowlyChangePageAnimation;
import generator.RandDevGenerator;
import generator.RandProjGenerator;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import model.Developer;
import model.player.Player;
import model.room.Hardware;
import model.skill.Skill;
import turnbasedengine.TimeCounter;
import view.detailview.DetailsPanel;
import view.detailview.SkillView;
import view.highscoreview.HighScorePanel;
import view.mainmenu.MainMenu;
import view.mainmenu.OptionGamePanel;
import view.popup.GameOverPopup;
import view.popup.InformPopup;
import view.popup.YesNoPopup;
import view.roomproperties.RoomSetView;
import view.roomproperties.RoomView;
import view.smallmenu.SmallMenu;

/**
 * The main dialog of the game.
 *
 * @author hung
 */
public class MainFrame extends JFrame {

//    public static final int FRAME_WIDTH = 966;
//    public static final int FRAME_HEIGHT = 568;
    public static final int PANEL_WIDTH = 960;
    public static final int PANEL_HEIGHT = 540;
    public static final int NORMAL_STATE = 0;
    public static final int VIEW_DETAIL_STATE = 1;
    private static MainFrame mainFrame;
    private static int currState;
    private JPanel currPanel; //The current main panel.
    private JPanel temp; //The temporary panel used in switchPanel() method.

    public MainFrame() {
        super("Dev Fortress - CyberTech");

        currPanel = new IntroducingAnimation();
//        currPanel = new RoomView();

        add(currPanel);

        setBackground(Color.WHITE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

        RandDevGenerator.prepare();
        RandProjGenerator.loadProjectNameList();

        Player.getInstance();

        // Save game process when exiting game
//        addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowClosing(WindowEvent e) {
//                SaveLoad.save(Player.getInstance(), SaveLoad.AUTOSAVE_PATH);
//            }
//        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if(!(e.getKeyCode()==KeyEvent.VK_ESCAPE && 
                        (currPanel instanceof RoomView 
                        || currPanel instanceof RoomSetView) &&
                        TimeCounter.getInstance().isStopped())) {
                    return;
                }
                
                System.out.println("ESC CLICKED");
                mainFrame.switchPanel(new SmallMenu());
            }
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        mainFrame = new MainFrame();
        mainFrame.fromIntroToMainMenu();
    }

//    @Override
//    public void paint(Graphics g) {
//        super.paint(g);
//        g.drawImage(img, 0, 0, FRAME_WIDTH, FRAME_HEIGHT, null);
//    }
    public void fromIntroToMainMenu() {
        setCurrPanel(new Introduction());
        ((Introduction) currPanel).playAnimation();
        SoundIntro.playWAV();
    }

    public void toMainMenu() {
        MainMenu menu = new MainMenu();
        setCurrPanel(menu);
//        SoundIntro.stopWAV();
//        SoundIntro.playBGM();
    }

    public void toStartGame() {
        final RoomView room = new RoomView();
        setCurrPanel(room);
        ScreenCapturer.captureHiddenPanel(room);

        SlowlyChangePageAnimation animation = new SlowlyChangePageAnimation() {
            @Override
            protected void actAfterAnimcation() {
//                System.out.println("FINISH SLOWLY CHANGE PAGE ANIMATION");
                setCurrPanel(room);

                SoundIntro.stopWAV();
                //System.out.println("Default: "+OptionGamePanel.DEFAULT);
                if(OptionGamePanel.DEFAULT == true){
                    Sound.prepareBGM();
                    Sound.playBGM3();
                }
                //SoundUtilities.BackGroundMusic();
            }
        };

        setCurrPanel(animation);
//        animation.playAnimation();
    }

    public void toGameOver(String reason) {
//        ((RoomView)currPanel).getRepaintTimeline().setAutoRepaintMode(false);
        GameOverPopup popup = new GameOverPopup(reason);
        setCurrPanel(popup);
        temp = null;
    }

    /**
     * Display a pop up dialog to confirm to the player when they are
     * buying<br/> new hardware.
     *
     * @param msg The message about the info of the transaction.
     * @param question The question to confirm.
     * @param comp The new hardware object.
     * @param quantity The quantity of the hardware.
     */
    public void toBuyHardwarePopup(String msg, String question,
            final Hardware comp, final int quantity) {
//        System.out.println("TO Buy Hardware Popup");
        YesNoPopup popup = new YesNoPopup(msg, question,
                YesNoPopup.BUY_HARDWARE_MSG_X) {
            @Override
            public void onYesClicked() {
                SoundUtilities.Coin1();
                Player.getInstance().buyComputer(comp, quantity);
                switchBack();
            }
        };
        switchPanel(popup);
    }

    /**
     * Display a pop up dialog to confirm to the player when they decide to<br/>
     * train a developer.
     *
     * @param msg The message containing the info of the training.
     * @param question The question to confirm.
     * @param d The developer who will attend the training.
     * @param s The skill that developer will train.
     */
    public void toTrainingPopup(String msg, String question, final SkillView sv,
            final Developer d, final Skill s) {
        System.out.println("TO Training Popup");
        YesNoPopup popup = new YesNoPopup(msg, question, 
                YesNoPopup.BUY_HARDWARE_MSG_X) {
            @Override
            public void onYesClicked() {
                sv.startTraining();
                d.setTrainingSkl(d.getSkills().get(s.getName()));
                switchBack();
            }
        };
        switchPanel(popup);
    }

    /**
     * Display a pop up dialog to confirm to the player when they are
     * selling<br/> hardware.
     *
     * @param msg The message about the info of the transaction.
     * @param question The question to confirm.
     * @param comp The new hardware object.
     */
    public void toSellHardwarePopup(String msg, String question,
            final Hardware comp, final DetailsPanel panel) {
        YesNoPopup popup = new YesNoPopup(msg, question,
                YesNoPopup.BUY_HARDWARE_MSG_X) {
            @Override
            public void onYesClicked() {
                switchBack();

                if (Player.getInstance().sellComputer(comp)) {
                    SoundUtilities.Coin1();
                    InformPopup ok = new InformPopup("Product "
                            + comp.getName() + " ID "
                            + comp.getSerial()
                            + " sold!", YesNoPopup.BUY_HARDWARE_MSG_X, panel);
                    switchPanel(ok);
                } else {
                    InformPopup ok = new InformPopup("Internal error, Execution failed!",
                            YesNoPopup.BUY_HARDWARE_MSG_X);
                    switchPanel(ok);
                }
            }
        };
        switchPanel(popup);
    }

    public void showHighScorePanel() {
        HighScorePanel hsp = new HighScorePanel();
        setCurrPanel(hsp);
        hsp.playAnimation();
    }

    /**
     * Temporarily switch the current panel with another
     * <code>panel2</code>. Be sure that they are switched back at a certain
     * point of time.
     *
     * @param panel2 The panel to be switched with the current panel.
     */
    public void switchPanel(JPanel panel2) {
        temp = currPanel;
        setCurrPanel(panel2);
    }

    public void switchBack() {
        setCurrPanel(temp);
//        temp = null;
    }

    public void setCurrPanel(JPanel currPanel) {
        remove(this.currPanel);
//        nextPanel = curPanel;
        this.currPanel = currPanel;
        add(this.currPanel);
//        invalidate();
        validate();
        this.currPanel.updateUI();
//        currPanel.requestFocus();
//        updateUI();
    }

    public void resetTemp() {
        temp = null;
    }

    public JPanel getCurrPanel() {
        return currPanel;
    }

    public JPanel getTemp() {
        return temp;
    }

    public static int getCurrState() {
        return currState;
    }

    public static void setCurrState(int currState) {
//        System.out.println("MainFrame set current state : "  + currState);
        MainFrame.currState = currState;
    }

    public static MainFrame getInstance() {
        return mainFrame;
    }

    public void setTemp(JPanel temp) {
        this.temp = temp;
    }
}