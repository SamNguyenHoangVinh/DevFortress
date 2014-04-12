/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.roomproperties;

import animation.DarkToLightPanel;
import generator.RandDevGenerator;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import model.Developer;
import model.player.Player;
import model.room.Seat;
import org.pushingpixels.trident.swing.SwingRepaintTimeline;
import view.MouseState;
import view.ViewUtilities;
import view.menubar.ListMenuBar;
import view.menubar.ListMenuBtn;

/**
 * The view of a seat where developer sits to work, or just empty as there <br/>
 * is no one sitting on this seat. This class helps interacting between <br/>
 * player & developers.
 *
 * @author s3342128
 */
public class SeatView implements Observer {

    public static final int SEAT_WIDTH = 54;
    public static final int SEAT_HEIGHT = 54;
    private DarkToLightPanel avaiPanel; //This panel plays an animation so that 
    //the player knows which seats is available
    private Image img; //The image of the developer.
    private WorkingTableView table;
    private Seat model; //The SeatView's model
    private JPanel seatPanel; //The panel of this seat in the room
//    private JLabel imgLabel; //The label containing the gif image
//    private Timer workingAnimator;
    private boolean alerting; //True when the player is hiring developers
    private int x;
    private int y;
    private int index;
    private SwingRepaintTimeline repaintTimeline;

    /**
     * Construct a SeatView object with a RoomView object where this seat <br/>
     * is placed, & SeatView's model.
     *
     * @param r The room where this seat is placed
     * @param m The model of this seat.
     */
    public SeatView(final RoomView room, WorkingTableView t, Seat m, int index) {
//        final RoomView room = table.getRoom();
        table = t;
        model = m;
        this.index = index;
        seatPanel = new JPanel();
//        imgLabel = new JLabel();
        repaintTimeline = room.getRepaintTimeline();

//        imgLabel.setOpaque(false);

        if (index < 2) {
            model.setFaceDown(true);
        }
        model.addObserver(this);

        if (!model.isAvailable()) {
//            img = ViewUtilities.DEV_IMG;
            if (model.getCurrDev().isFemale()) {
                img = ViewUtilities.FEMALE;
            } else {
                img = ViewUtilities.MALE;
            }
//            imgLabel.setIcon(new ImageIcon(img));
        }

        if (t.hasCompAtPosition(index)) {
            model.getCurrDev().setComputer(t.getComputers().get(t.getIndexFromPosition(index)).getModel());
        }

        avaiPanel = new DarkToLightPanel(repaintTimeline, Color.GREEN, 20, 120) {
            @Override
            public void drawPanel(Graphics g,
                    int x, int y, int width, int height) {
                super.drawPanel(g, x, y, width, height);
                if (getBackgroundColor().getAlpha() == getFromAlpha() && alerting) {
                    playAnimation();
                } else if (getBackgroundColor().getAlpha() == getToAlpha()
                        && alerting) {
                    reverseAnimation();
                }
            }
        };
        avaiPanel.getTimeLine().setDuration(800);

        seatPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
//                System.out.println("CLICK TO REMOVE");
                onClick();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
//                System.out.println("MOUSE ENTERED");
            }

            @Override
            public void mouseExited(MouseEvent e) {
//                System.out.println("MOUSE EXITED");
            }
        });

//        seatPanel.add(imgLabel);
        seatPanel.setOpaque(false);
        seatPanel.setPreferredSize(new Dimension(SEAT_WIDTH, SEAT_HEIGHT));
        room.add(seatPanel);

//        workingAnimator = new Timer(100, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String weeklyEvent = model.getCurrDev().getWeeklyEvent();
//                if(weeklyEvent == null) {
//                    return;
//                }
//                if(weeklyEvent.equals(RandEventGenerator
//                            .EVENTS[RandEventGenerator.SOLUTION_NOT_SCALE]) ||
//                        weeklyEvent.equals(RandEventGenerator
//                            .EVENTS[RandEventGenerator.HACKED]) ||
//                        weeklyEvent.equals(RandEventGenerator
//                            .EVENTS[RandEventGenerator.BACKUP_FAILED])) {
//                    img = new ImageIcon("Images/Emoticons/7.gif").getImage();
//                }
//                
////                room.repaint(seatPanel.getBounds());
//            }
//        });

//        new Timer(100, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                room.repaint(seatPanel.getBounds());
//            }
//        }).start();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (!isAvailable()) {
            alerting = false;
        } else {
            if (table.getIndexFromPosition(index) != -1) {
                table.getComputers().get(table.getIndexFromPosition(index)).stopHardwareAnim();
                table.getMonitors().get(table.getIndexFromPosition(index)).stopHardwareAnim();
            }
        }

//        table.getRoom().repaint(seatPanel.getBounds());
    }

    /**
     * Draw this seat view using the current graphic context
     *
     * @param g The current graphic context
     * @param x The x coordinate to draw the view
     * @param y The y coordinate to draw the view
     * @param width The width of the view
     * @param height The height of the view
     */
    public void drawSeatView(Graphics g, int x, int y, int width, int height) {
        int svx = table.getX() + x;
        int svy = table.getY() + y;

        generalDrawing(g, svx, svy, width, height);
    }

    /**
     * Draw this seat view using the current graphic context
     *
     * @param g The current graphic context
     */
    public void drawSeatView(Graphics g) {     // There is a big bug
        int svx = table.getX() + x;
        int svy = table.getY() + y;

        generalDrawing(g, svx, svy, SEAT_WIDTH, SEAT_HEIGHT);
    }

    private void generalDrawing(Graphics g, int x, int y,
            int width, int height) {
        seatPanel.setBounds(x, y, width, height);

        if (isAvailable() && alerting) {
            avaiPanel.drawPanel(g, x, y, width, height);
        } else if (!isAvailable()) {
            if (model.isFaceDown()) {
//                Graphics2D g2d = (Graphics2D)(g.create());  
                int y2 = y + height / 3 - 13;
//                g2d.rotate(Math.toRadians(180), x+width/2, y2+height/2);
////                g2d.rotate(Math.toRadians(180));
                g.drawImage(img, x, y2, width, height, null);
            } else {
                int y2 = y - height / 3;
                g.drawImage(img, x, y2, width, height, null);
            }

        }

        if (model.getCurrDev() != null && model.getCurrDev().isEating()) {
            int dx = table.getX() + WorkingTableView.DISH_POINTS[index].x;
            int dy = table.getY() + WorkingTableView.DISH_POINTS[index].y;

            Image dish = ViewUtilities.DISH;
            g.drawImage(dish, dx, dy,
                    dish.getWidth(null), dish.getHeight(null), null);
        }

        if (model == null) {
            System.out.println("The SeatView's model is null");
        }
    }

    /**
     * Handle the event happening when there is a mouse click on the seat panel.
     */
    private void onClick() {
        if (model.isAvailable() && isAlerting()) {
            final RoomView room = table.getRoom();
            ListMenuBtn btn = (ListMenuBtn) (MouseState.getCurrDraggedObj());
            ListMenuBar menuBar = (ListMenuBar) (room.getMenu());
            Developer newDev = (Developer) (btn.getModel());
//            img = ViewUtilities.DEV_IMG;
            alerting = false;

            model.setCurrDev(newDev);
            Player.getInstance().hireDeveloper(newDev);
            newDev.setEmployed(true);
            if (table.getIndexFromPosition(index) != -1) {
                newDev.setComputer(table.getComputers().
                        get(table.getIndexFromPosition(index)).getModel());
            }

            if (model.getCurrDev().isFemale()) {
                img = ViewUtilities.FEMALE;
            } else {
                img = ViewUtilities.MALE;
            }

//            imgLabel.setIcon(new ImageIcon(img));

            room.stopDisplayAvaiSeats();
            room.setCursor(MouseState.getNormalMouseCursor());
            room.remove(btn.getButtonPanel());

            MouseState.setCurrState(MouseState.NORMAL);
            MouseState.setCurrDraggedObj(null);

            menuBar.removeItem(btn);
            RandDevGenerator.getThisWeekDevList().remove(newDev);

//            room.repaint(seatPanel.getBounds());

            System.out.println("Hire new developer successfull");
        }
    }

    public void devStartWorking() {
        if (isAvailable() || model.getCurrDev().isAvailable()) {
            return;
        }

        if (model.getCurrDev().isTraining()) {
            if (model.getCurrDev().isFemale()) {
                img = ViewUtilities.F_TRAINING;
            } else {
                img = ViewUtilities.M_TRAINING;
            }
        } else if (model.getCurrDev().isWorking()) {
            if (table.hasCompAtPosition(index)) {
                if (model.getCurrDev().isFemale()) {
                    img = ViewUtilities.F_WORKING;
                } else {
                    img = ViewUtilities.M_WORKING;
                }
            } else {
                if (model.getCurrDev().isFemale()) {
                    img = ViewUtilities.FEMALE;
                } else {
                    img = ViewUtilities.MALE;
                }
            }
        }

        System.out.println("START WORKING");
//        workingAnimator.start();
        if (table.hasCompAtPosition(index)) {
            table.getComputers().get(table.getIndexFromPosition(index)).startHardwareAnim();
            table.getMonitors().get(index).startHardwareAnim();
        }
    }

    public void devStopWorking() {
//        workingAnimator.stop();
        if (table.hasCompAtPosition(index)) {
            table.getComputers().get(table.getIndexFromPosition(index)).stopHardwareAnim();
            table.getMonitors().get(index).stopHardwareAnim();
        }

//        img = ViewUtilities.DEV_IMG;
        try {
            Developer dev = model.getCurrDev();
            if (dev.isFemale()) {
                img = ViewUtilities.FEMALE;
            } else {
                img = ViewUtilities.MALE;
            }

            if (dev.isTraining()) {
                dev.train(dev.getTrainingSkl().getName());
                dev.setTrainingSkl(null);
            }
        } catch (Exception ex) {
//            System.out.println("Developer stop working exception");
        }

//        table.getRoom().repaint();
    }

    public boolean isAvailable() {
//        return img == null;
        return model.isAvailable();
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public RoomView getRoom() {
        return table.getRoom();
    }

//    public void setRoom(RoomView room) {
//        this.room = room;
//    }
    public boolean isAlerting() {
        return alerting;
    }

    /**
     * If true, this seat view is alerting that the seat is available. <br/>
     * Otherwise, it's not available. If the seat is already not available,<br/>
     * the
     * <code>alerting</code> will be false & can't be set to true until<br/> the
     * seat is available.
     *
     * @param alerting
     */
    public void setAlerting(boolean alerting) {
        if (!isAvailable()) {
            return;
        }

        this.alerting = alerting;

        if (alerting) {
            avaiPanel.playAnimation();
        } else {
            avaiPanel.getTimeLine().cancel();
        }
    }

    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;

//        seatPanel.setBounds(table.getX() + x, table.getY() + y, 
//                            SEAT_WIDTH, SEAT_HEIGHT);
    }

    public JPanel getSeatPanel() {
        return seatPanel;
    }

    public DarkToLightPanel getAvaiPanel() {
        return avaiPanel;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Seat getModel() {
        return model;
    }

    public int getIndex() {
        return index;
    }
}
