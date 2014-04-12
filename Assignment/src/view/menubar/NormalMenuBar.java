/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.menubar;

import generator.CompGenerator;
import generator.RandDevGenerator;
import generator.RandProjGenerator;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.ImageIcon;
import turnbasedengine.TimeCounter;
import view.SoundUtilities;
import view.ViewUtilities;
import view.roomproperties.RoomView;

/**
 * The menu bar when the game is being played.
 * @author HungHandsome
 */
public class NormalMenuBar extends AbstractNormalMenu implements ViewUtilities {
        
    public static final int NEW_PROJ_BUTTON = 0;
    public static final int YOUR_PROJ_BUTTON = 1;
    public static final int HIRE_DEV_BUTTON = 2;
    public static final int YOUR_DEV_BUTTON = 3;
    public static final int NEW_COMP_BUTTON = 4;
    public static final int YOUR_COMP_BUTTON = 5;
    
//    public static final int BASIC_INFO_Y = 15;
    public static final int MENU_BTN_BASE_Y = 235;
    public static final int BTN_GROUP_GAP = 100;
    
    private Image projLabel;
    private Image devLabel;
    private Image hardwareLabel;
    
    private Collection preData;
    
    public NormalMenuBar(RoomView r) {
        super(r, MENU_TITLE_IMG_PATH, NORMAL_MENU);
        hardwareLabel = new ImageIcon(HARDWARE_IMG_PATH).getImage();
        projLabel = new ImageIcon(PROJ_IMG_PATH).getImage();
        devLabel = new ImageIcon(DEVELOPER_IMG_PATH).getImage();
        buttons = new ArrayList<AbstractBtn>(6);
        NormalMenuButton btns[] = new NormalMenuButton[6];      
                        
        initBackButton(PLAY_BTN_IMG);
        
        btns[NEW_PROJ_BUTTON] = new NormalMenuButton(
                                   new ImageIcon(NEW_PROJ_IMG_PATH).getImage());
        btns[YOUR_PROJ_BUTTON] = new NormalMenuButton(
                                   new ImageIcon(YOUR_PROJ_IMG_PATH).getImage());
        btns[HIRE_DEV_BUTTON] = new NormalMenuButton(
                                   new ImageIcon(HIRE_DEV_IMG_PATH).getImage());
        btns[YOUR_DEV_BUTTON] = new NormalMenuButton(
                                   new ImageIcon(YOUR_DEV_IMG_PATH).getImage());
        btns[NEW_COMP_BUTTON] = new NormalMenuButton(
                                   new ImageIcon(NEW_COMP_IMG_PATH).getImage());
        btns[YOUR_COMP_BUTTON] = new NormalMenuButton(
                                   new ImageIcon(YOUR_COMP_IMG_PATH).getImage());
        
        for(int i=0; i<btns.length; i++) {   
            if (i % 2 == 0) {
                btns[i].setY(MENU_BTN_BASE_Y + (i / 2) * BTN_GROUP_GAP + 36);
            } else {
                btns[i].setY(btns[i-1].getY() + btns[i].getHeight() + 18);
            }
            room.add(btns[i].getButtonPanel());
        }
        
        for(NormalMenuButton b : btns) {
            buttons.add(b);
        }
        
        addButtonListener();
    }       

    @Override
    protected void drawMenuProperties(Graphics g) {
        Graphics g2 = g.create();
        
//        g2.setColor(Color.white);
//        g2.translate(x, BASIC_INFO_Y);
//        AbsMenu.drawCalendar(g2);
//        g2.translate(x, -10);
//        AbsMenu.drawCash(g2);
        
        for(int i=0; i<buttons.size(); i++) {
            buttons.get(i).drawButton(g);
        }
        
        int gap = BTN_GROUP_GAP;
        
        g.drawImage(projLabel, BACK_BUTTON_X, MENU_BTN_BASE_Y, null);
        g.drawImage(devLabel, BACK_BUTTON_X, 
                                 MENU_BTN_BASE_Y + gap, null);
        g.drawImage(hardwareLabel, BACK_BUTTON_X, 
                                 MENU_BTN_BASE_Y + (gap+=BTN_GROUP_GAP), null);
    }

    @Override
    protected void backBtnClicked() {
        if(menuDisabled) {
            return;
        }
        timeLine.replay();
        SoundUtilities.Slide();
        afterAnimateReactor.start();
        clickedButton = BACK_BUTTON;    
        TimeCounter.getInstance().setState(TimeCounter.READY);
    }

    @Override
    protected void actAfterAnimation() {
        if(x == -MENU_BAR_WIDTH) {
            switch(clickedButton) {
                case BACK_BUTTON: room.startTurn(); break;
                case NEW_PROJ_BUTTON: room.toNewProj(preData); break;
                case YOUR_PROJ_BUTTON: room.toYourProj(); break;
                case HIRE_DEV_BUTTON: room.toHireDev(preData); break;
                case YOUR_DEV_BUTTON: room.toYourDev(); break;
                case NEW_COMP_BUTTON: room.toNewComp(preData); break;
                case YOUR_COMP_BUTTON: room.toYourComp();
//                case BUY_FOOD_BUTTON: room.backToNormalMenu(); break;
//                case STORAGE_BUTTON: room.backToNormalMenu();
    //            default: timeLine.replayReverse();
            }  
            afterAnimateReactor.stop();
        }
    }
    
    private void addButtonListener() { 
        buttons.get(NEW_PROJ_BUTTON).getButtonPanel()
                                .addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if(menuDisabled) {
                    return;
                }
                
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        preData = RandProjGenerator.generateProjectList();                        
                    }
                };
                Thread thread = new Thread(runnable);
                thread.start();
            }
        });
        
//        buttons[YOUR_PROJ_BUTTON].getButtonPanel()
//                                .addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                
//            }
//        });
//        
        buttons.get(HIRE_DEV_BUTTON).getButtonPanel()
                                .addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (menuDisabled) {
                    return;
                }
                
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        preData = RandDevGenerator.generateRandDevList();                        
                    }
                };
                Thread thread = new Thread(runnable);
                thread.start();
            }
        });
        
//        buttons.get(YOUR_DEV_BUTTON).getButtonPanel()
//                                .addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseReleased(MouseEvent e) {
//                
//            }
//        });
        
        buttons.get(NEW_COMP_BUTTON).getButtonPanel()
                                .addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if(menuDisabled){
                    return;
                }
                
                Runnable runnable = new Runnable() {

                    @Override
                    public void run() {
                        preData = CompGenerator.generateCompList();
                    }
                    
                };
                Thread thread = new Thread(runnable);
                thread.start();
            }
        });
//        
//        buttons[YOUR_COMP_BUTTON].getButtonPanel()
//                                .addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                
//            }
//        });
//        
//        buttons[BUY_FOOD_BUTTON].getButtonPanel()
//                                .addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                
//            }
//        });
//        
//        buttons[STORAGE_BUTTON].getButtonPanel()
//                                .addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                
//            }
//        });
        
        for(int i=0; i<buttons.size(); i++) {
            final int idx = i;
            buttons.get(i).getButtonPanel().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    if (menuDisabled) {
                        return;
                    }
                
//                    System.out.println("CLICK BUTTON");
                    clickedButton = idx;
                    timeLine.replay();
        SoundUtilities.Slide();
                    afterAnimateReactor.start();
                    
                    for(int j=0; j<buttons.size(); j++) {
                        room.remove(buttons.get(j).getButtonPanel());
                    }
                    room.remove(backBtnPanel);
                }
            });
        }
    }

    @Override
    protected void submenuClosed() {
    }

    @Override
    protected void afterSubmenuClosed() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
