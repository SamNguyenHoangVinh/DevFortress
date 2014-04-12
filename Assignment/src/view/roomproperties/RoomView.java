/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.roomproperties;

import animation.MetroStarter;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import model.player.Player;
import model.room.Hardware;
import model.room.Room;
import model.room.WorkingTable;
import org.pushingpixels.trident.Timeline;
import org.pushingpixels.trident.swing.SwingRepaintTimeline;
import view.FontUtilities;
import view.MainFrame;
import view.MouseState;
import view.ViewUtilities;
import view.detailview.*;
import view.menubar.*;

/**
 * This class is a view that represents a room where developers work.
 *
 * @author s3342128
 */
public class RoomView extends JPanel implements Observer {

    public static final int TABLE_BASE_X = 198;
    public static final int TABLE_BASE_Y = 12;
    public static final int TABLE_GAP = 15; //The distance between tables
    public static final int NORMAL_STATE = -1;
    public static final int AVAI_PROJ_STATE = 0;
    public static final int YOUR_PROJ_STATE = 1;
    public static final int HIRE_DEV_STATE = 2;
    public static final int YOUR_DEV_STATE = 3;
    public static final int NEW_COMP_STATE = 4;
    public static final int YOUR_COMP_STATE = 5;
    public static final int BUY_FOOD_STATE = 6;
    public static final int STORAGE_STATE = 7;
    public static final int START_TURN_STATE = 8;
    private static int currState = -1;
//    private Image floorImg;
    private MetroStarter metroStarter;
    private WorkingTableView[] tables;
    private AbsMenu menu;
    private AbsMenu submenu;
    private Room model;
    private SwingRepaintTimeline repaintTimeline;
    private DetailsPanel detailPanel;
    private Timer open;
//    private DetailsPanel subDetailPanel;

    public RoomView() {
//        model = Player.getInstance().getRoom(0, 0);
        model = Player.getInstance().getCurrRoom();
//        floorImg = new ImageIcon(ViewUtilities.FLOOR_IMG_PATH).getImage();
        tables = new WorkingTableView[4];

        repaintTimeline = new SwingRepaintTimeline(this);
        repaintTimeline.setAutoRepaintMode(true);
        repaintTimeline.playLoop(Timeline.RepeatBehavior.LOOP);

        menu = new NormalMenuBar(this);

        model.addObserver(this);

        for (int i = 0; i < 4; i++) {
            tables[i] = new WorkingTableView(this, model.getTableAtIndex(i));
        }

        setLayout(null);

        setPreferredSize(
                new Dimension(MainFrame.PANEL_WIDTH, MainFrame.PANEL_HEIGHT));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (MouseState.getCurrState() == MouseState.DRAG_NEW_DEV) {
                    MouseState.setCurrState(MouseState.NORMAL);
                    setCursor(MouseState.getNormalMouseCursor());
                    stopDisplayAvaiSeats();
                }
            }
        });

        metroStarter = new MetroStarter();
        add(metroStarter);
        
//        Action esc = new AbstractAction() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                System.out.println("ESC CLICKED");
//            }
//        };
//        getInputMap().put(
//                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "esc");
//        getActionMap().put("esc", esc);
        
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = FontUtilities.renderer(g);

        g2.drawImage(ViewUtilities.FLOOR_IMG, 0, 0,
                getWidth(), getHeight(), null);

        tables[0].drawTable(g2,
                TABLE_BASE_X, TABLE_BASE_Y);
        tables[1].drawTable(g2,
                TABLE_BASE_X + WorkingTableView.TABLE_WIDTH + TABLE_GAP,
                TABLE_BASE_Y);
        tables[2].drawTable(g2,
                TABLE_BASE_X,
                TABLE_BASE_Y + WorkingTableView.TABLE_HEIGHT + TABLE_GAP);
        tables[3].drawTable(g2,
                TABLE_BASE_X + WorkingTableView.TABLE_WIDTH + TABLE_GAP,
                TABLE_BASE_Y + WorkingTableView.TABLE_HEIGHT + TABLE_GAP);

        menu.drawMenuBar(g2);
        Graphics2D g3 = (Graphics2D) g2.create();

        if (MainFrame.getCurrState() == MainFrame.VIEW_DETAIL_STATE) {
            if (detailPanel instanceof RoomDetailPanel) {
//                System.out.println("1111111111111111111111111111111111111");
                detailPanel.drawRoomPanel(g2);
            } else if (menu.hasSubmenu()) {
//                System.out.println("2222222222222222222222222222222222222");
                detailPanel.drawSubPanel(g2);
            } else {
//                System.out.println("3333333333333333333333333333333333333");
                detailPanel.drawPanel(g2);
            }
        }

        if (menu.hasSubmenu()) {
            submenu.drawSubmenu(g3);
        }

//        g2.setColor(Color.red);
//        g2.fillRect(550, 0, 50, 50);
    }

    @Override
    public void update(Observable o, Object arg) {
//        repaint();
    }

    /**
     * Play an animation to show which seats in this room is still available.
     */
    public void displaysAvaiSeats() {
        for (WorkingTableView tab : tables) {
            tab.displayAvaiSeats();
        }
    }

    /**
     * Stop the animation which shows the available seats.
     */
    public void stopDisplayAvaiSeats() {
        for (WorkingTableView tab : tables) {
            tab.stopDisplayAvaiSeats();
        }
    }

    public void backToNormalMenu() {
        removeAll();

        menu = new NormalMenuBar(this);
        menu.setX(-AbstractNormalMenu.MENU_BAR_WIDTH);
        menu.reverseAnimation();

        for (WorkingTableView table : tables) {
            for (HardwareView comp : table.getComputers()) {
                add(comp.getPanel());
            }
            for (HardwareView monitor : table.getMonitors()) {
                add(monitor.getPanel());
            }
            for (SeatView seat : table.getSeats()) {
                add(seat.getSeatPanel());
            }
        }
        add(metroStarter);

        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(450);
                } catch (Exception ex) {
                }

                setCurrState(NORMAL_STATE);
            }
        }.start();
    }

    /**
     * Start the next turn
     */
    public void startTurn() {
        RoomView.setCurrState(RoomView.START_TURN_STATE);
        menu = new GamePlayMenu(this);
        menu.reverseAnimation();

        for (WorkingTableView tab : tables) {
            for (SeatView sv : tab.getSeats()) {
                sv.devStartWorking();
            }
        }
    }

    /**
     * Change to the menu of taking new project
     */
    public void toNewProj(Collection projs) {
        RoomView.setCurrState(RoomView.AVAI_PROJ_STATE);
        menu = new ListMenuBar(this, NormalMenuBar.NEW_PROJ_TITLE_IMG_PATH, projs);
        menu.reverseAnimation();
    }

    /**
     * Change to the menu of the list of player's current projects
     */
    public void toYourProj() {
        RoomView.setCurrState(RoomView.YOUR_PROJ_STATE);
        menu = new ListMenuBar(this, NormalMenuBar.YOUR_PROJ_TITLE_IMG_PATH,
                Player.getInstance().getProjects().values());
        menu.reverseAnimation();
    }

    /**
     * Change to the menu of hiring new developer
     */
    public void toHireDev(Collection devs) {
        RoomView.setCurrState(RoomView.HIRE_DEV_STATE);
        menu = new ListMenuBar(this, NormalMenuBar.HIRE_DEV_TITLE_IMG_PATH, devs);
        menu.reverseAnimation();
    }

    /**
     * Change to the menu of the list of player's current developers
     */
    public void toYourDev() {
        RoomView.setCurrState(RoomView.YOUR_DEV_STATE);
        menu = new ListMenuBar(this, NormalMenuBar.YOUR_DEV_TITLE_IMG_PATH,
                Player.getInstance().getDevelopers().values());
        menu.reverseAnimation();
    }

    /**
     * Menu displays the list of available computers
     *
     * @param comps List of available computers
     */
    public void toNewComp(Collection comps) {
        RoomView.setCurrState(RoomView.NEW_COMP_STATE);
        menu = new ListMenuBar(this, NormalMenuBar.NEW_COMP_TITLE_IMG_PATH, comps);
        menu.reverseAnimation();
    }

    /**
     * Menu displays the list of player's computers
     */
    public void toYourComp() {
        RoomView.setCurrState(RoomView.YOUR_COMP_STATE);
        menu = new ListMenuBar(this, NormalMenuBar.YOUR_COMP_TITLE_IMG_PATH,
                Arrays.asList(model.getTables()));
        menu.reverseSubAnimation();
        openRoomDetail();
    }

    public void openRoomDetail() {
        if (detailPanel == null || MainFrame.getCurrState() == MainFrame.NORMAL_STATE) {
            openDetailView(new RoomDetailPanel(model.getTables(),
                    repaintTimeline));
        } else {
            openSubDetailView(new RoomDetailPanel(model.getTables(),
                    repaintTimeline));
        }
    }

    public void openRoomDetail(Hardware chosenComputer) {
        openSubDetailView(new RoomDetailPanel(model.getTables(),
                chosenComputer,
                repaintTimeline));
    }

    public void openDevDetail(ListMenuBtn fromBtn) {
        openDetailView(new DevDetailPanel(fromBtn, repaintTimeline));
    }

    public void openNewProjDetail(ListMenuBtn fromBtn) {
        openDetailView(new NewProjDetail(fromBtn, repaintTimeline));
    }

    public void openYourProjDetail(ListMenuBtn fromBtn) {
        openDetailView(new YourProjDetail(fromBtn, repaintTimeline));
    }

    public void openNewCompDetail(ListMenuBtn fromBtn) {
        openDetailView(new NewCompDetail(fromBtn, repaintTimeline));
    }

    public void openYourCompDetail(ListMenuBtn fromBtn) {
        if ((detailPanel instanceof TableDetailPanel || detailPanel instanceof RoomDetailPanel)
                && MainFrame.getCurrState() == MainFrame.VIEW_DETAIL_STATE) {
            openSubDetailView(new YourCompDetail(fromBtn, repaintTimeline));
        } else {
            openDetailView(new YourCompDetail(fromBtn, repaintTimeline));
        }
    }

    public void openYourCompDetail(ListMenuBtn fromBtn, ListMenuBtn parentFromBtn) {
        if ((detailPanel instanceof TableDetailPanel || detailPanel instanceof RoomDetailPanel)
                && MainFrame.getCurrState() == MainFrame.VIEW_DETAIL_STATE) {
            openSubDetailView(new YourCompDetail(fromBtn, parentFromBtn, repaintTimeline));
        } else {
            openDetailView(new YourCompDetail(fromBtn, parentFromBtn, repaintTimeline));
        }
    }

    public void openYourTableDetail(final ListMenuBtn fromBtn) {
        if (submenu != null) {
            ((ListMenuBar) submenu).submenuClosed();
            menu.setSubmenu(false);
//            ((ListMenuBar)submenu).setItems(
//                            ((WorkingTable)fromBtn.getModel()).getComputers());
            submenu = null;
        }
        submenu = new ListMenuBar(this, NormalMenuBar.YOUR_COMP_TITLE_IMG_PATH,
                ((WorkingTable) fromBtn.getModel()).getComputers(), ListMenuBar.SUBMENU) {
            @Override
            public void sequence() {
                if ((detailPanel instanceof YourCompDetail || detailPanel instanceof RoomDetailPanel)
                        && MainFrame.getCurrState() == MainFrame.VIEW_DETAIL_STATE) {
                    openSubDetailView(new TableDetailPanel(fromBtn, repaintTimeline));
                } else {
                    openDetailView(new TableDetailPanel(fromBtn, repaintTimeline));
                }
            }
        };
        menu.setSubmenu(true);
        submenu.reverseAnimation();
    }

    public void openYourTableDetail(final ListMenuBtn fromBtn, final Hardware chosenComputer) {
        if (submenu != null) {
            ((ListMenuBar) submenu).submenuClosed();
            menu.setSubmenu(false);
            submenu = null;
        }
        if (chosenComputer == null) {
            submenu = new ListMenuBar(this, NormalMenuBar.YOUR_COMP_TITLE_IMG_PATH,
                    ((WorkingTable) fromBtn.getModel()).getComputers(), ListMenuBar.SUBMENU) {
                @Override
                public void sequence() {
                    openSubDetailView(new TableDetailPanel(fromBtn, chosenComputer, repaintTimeline));
                }
            };
        } else {
            submenu = new ListMenuBar(this, NormalMenuBar.YOUR_COMP_TITLE_IMG_PATH,
                    new ArrayList<Hardware>(), ListMenuBar.SUBMENU) {
                @Override
                public void sequence() {
                    openSubDetailView(new TableDetailPanel(fromBtn, chosenComputer, repaintTimeline));
                }
            };
        }
        menu.setSubmenu(true);
        submenu.reverseAnimation();
    }

    public void openYourStorageDetail(ListMenuBtn fromBtn) {
        if (detailPanel instanceof RoomDetailPanel) {
            ((RoomDetailPanel) detailPanel).removeButtons();
            detailPanel.reverseAnimation();
        }
        if (submenu != null) {
            ((ListMenuBar) submenu).submenuClosed();
            menu.setSubmenu(false);
            submenu = null;
        }
        submenu = new ListMenuBar(this, NormalMenuBar.YOUR_COMP_IMG_PATH,
                Player.getInstance().getComputers().values(), ListMenuBar.STORAGE);
        menu.setSubmenu(true);
        submenu.reverseSubAnimation();
    }

    private void openDetailView(DetailsPanel dp) {
        if (detailPanel == null
                || !detailPanel.getClass().equals(dp.getClass())
                || detailPanel instanceof RoomDetailPanel) {
            if (detailPanel != null) {
                detailPanel.addBackPanels();
            }
            detailPanel = null;
            detailPanel = dp;
            if (detailPanel instanceof TableDetailPanel || submenu != null
                    && ((ListMenuBar) submenu).getType() == ListMenuBar.STORAGE) {
                detailPanel.getCancelBtn().getPanel().addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        openRoomDetail();
                    }
                });
            }
            if (detailPanel instanceof TableDetailPanel) {
                submenu.addObserver((TableDetailPanel) detailPanel);
                ((TableDetailPanel) detailPanel).addPanels();
            } else if (detailPanel instanceof RoomDetailPanel) {
                menu.addObserver((RoomDetailPanel) detailPanel);
                ((RoomDetailPanel) detailPanel).addTablePanels();
            }
        } else {
            detailPanel.setFromBtn(dp.getFromBtn());
            detailPanel.addBackBtns();
            if (detailPanel instanceof TableDetailPanel) {
                submenu.addObserver((TableDetailPanel) detailPanel);
            }
        }
        if (MainFrame.getCurrState() != MainFrame.VIEW_DETAIL_STATE) {
            MainFrame.setCurrState(MainFrame.VIEW_DETAIL_STATE);
            detailPanel.playAnimation();
        }
    }

    public void openSubDetailView(DetailsPanel dp) {
        initializeSequence(dp);
    }

    private void initializeSequence(DetailsPanel dp) {
        if (detailPanel != null) {
            detailPanel.addBackPanels();
            if (detailPanel instanceof YourCompDetail) {
                ((YourCompDetail) detailPanel).removeButtons();
                detailPanel.reverseSubAnimation(dp);
            } else if (detailPanel instanceof TableDetailPanel) {
                ((TableDetailPanel) detailPanel).removeButtons();
                detailPanel.reverseSubAnimation(dp);
            } else if (detailPanel instanceof RoomDetailPanel) {
                ((RoomDetailPanel) detailPanel).removeButtons();
                detailPanel.reverseSubAnimation(dp);
            }
        }
    }

    public void progressSequence(DetailsPanel dp) {
        detailPanel = null;
        detailPanel = dp;
        if (detailPanel instanceof TableDetailPanel || submenu != null
                && ((ListMenuBar) submenu).getType() == ListMenuBar.STORAGE) {
            detailPanel.getCancelBtn().getPanel().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    openRoomDetail();
                }
            });
        }
        if (detailPanel instanceof TableDetailPanel) {
            submenu.addObserver((TableDetailPanel) detailPanel);
            ((TableDetailPanel) detailPanel).addPanels();
        } else if (detailPanel instanceof RoomDetailPanel) {
            menu.addObserver((RoomDetailPanel) detailPanel);
            ((RoomDetailPanel) detailPanel).addTablePanels();
        }
    }

    public void finalizeSequence() {
        if (MainFrame.getCurrState() != MainFrame.VIEW_DETAIL_STATE) {
            MainFrame.setCurrState(MainFrame.VIEW_DETAIL_STATE);
            detailPanel.playAnimation();
        }
    }

    public void openTurnSummaryDetail() {
        if (detailPanel != null) {
            detailPanel.addBackPanels();
        }
        detailPanel = null;
        detailPanel = new TurnSummaryPanel(repaintTimeline);

        MainFrame.setCurrState(MainFrame.VIEW_DETAIL_STATE);
        detailPanel.playAnimation();
    }

    private void prepareDetailPanel() {
        if (MainFrame.getCurrState() != MainFrame.VIEW_DETAIL_STATE) {
//            System.out.println("Case 1");
            MainFrame.setCurrState(MainFrame.VIEW_DETAIL_STATE);
            detailPanel.playAnimation();
        }
//        else {
////            System.out.println("case 2");
////            detailPanel.setX(AbstractGameMenu.MENU_BAR_WIDTH);
//            repaint();
//        }
    }

    public void closeDetailPanel() {
//        System.out.println("Close detail view");
        detailPanel.reverseAnimation();
    }

    public void acceptProject(final ListMenuBtn fromBtn) {
        closeDetailPanel();

        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(900);
                } catch (Exception ex) {
                }

                openYourProjDetail(fromBtn);
                ((YourProjDetail) detailPanel).setAcceptProjState(true);
            }
        }.start();
    }

    public DetailsPanel getDetailPanel() {
        return detailPanel;
    }

    public void setDetailPanel(DetailsPanel detailPanel) {
        this.detailPanel = detailPanel;
    }

    public Room getModel() {
        return model;
    }

    public void setModel(Room model) {
        this.model = model;
    }

    public SwingRepaintTimeline getRepaintTimeline() {
        return repaintTimeline;
    }

    public AbsMenu getMenu() {
        return menu;
    }

    public void setMenu(AbstractNormalMenu menu) {
        this.menu = menu;
    }

    public AbsMenu getSubmenu() {
        return submenu;
    }

    public void setSubmenu(AbstractNormalMenu submenu) {
        this.submenu = submenu;
    }

    public WorkingTableView[] getTables() {
        return tables;
    }

    public static int getCurrState() {
        return currState;
    }

    public static void setCurrState(int currState) {
        RoomView.currState = currState;
    }

    public MetroStarter getMetroStarter() {
        return metroStarter;
    }
}
