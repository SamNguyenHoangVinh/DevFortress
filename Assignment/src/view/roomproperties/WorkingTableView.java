/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.roomproperties;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import model.room.Hardware;
import model.room.WorkingTable;
import view.ViewUtilities;

/**
 * The view of a working table in a room.
 * @author hung
 */
public class WorkingTableView implements Observer {
    
    public static final int TABLE_WIDTH = 370;
    public static final int TABLE_HEIGHT = 250;   
    public static final int TABLE_PC_CAPACITY = 4;
    public static final int TABLE_MN_CAPACITY = 4;
    
    public static final int NONE = -1;
    
    public static final Point[] COMPUTER_POINTS = {
        new Point(13, 66), new Point(184, 66), 
        new Point(13, 129), new Point(184, 129)
    };
    public static final Point[] MONITOR_POINTS = {
        new Point(142, 13), new Point(315, 13), 
        new Point(142, 133), new Point(315, 133)
    };
    public static final Point[] DISH_POINTS = {
        new Point(15, 41), new Point(186, 41), 
        new Point(15, 180), new Point(186, 180)
    };
    public static final Point[] SEAT_POINTS = {
        new Point(62, 11), new Point(232, 11), 
        new Point(62, 184), new Point(232, 184)
    };
    
    private RoomView room; //The room containing this table
    private ArrayList<HardwareView> computers; //The list of computers on 
                                                //this table
    private ArrayList<HardwareView> monitors; //The list of monitors on 
                                                //this table
    private SeatView[] seats;
    private WorkingTable model;
//    private int numComputers; //The current number of computers
//    private int numMonitors; //The current number of monitors
    
//    private int x; //The x coordinate to draw the table
//    private int y; //The y coordinate to draw the table    
    private boolean clicked;
    
    public WorkingTableView(RoomView r, WorkingTable m) {
        room = r;        
        model = m;
        model.addObserver(this);
        computers = new ArrayList<HardwareView>();
        monitors = new ArrayList<HardwareView>();
        seats = new SeatView[4];
        
        for(Hardware com : model.getComputers()) {
            HardwareView comp = new HardwareView(com, this);
            computers.add(comp);
            addHardwareToRoom(comp);
        }
        
        for(Hardware mon : model.getMonitors()) {
            HardwareView monitor = new HardwareView(mon, this);
            monitors.add(monitor);
            addHardwareToRoom(monitor);
        }
        
        for(int i=0; i<4; i++) {
            seats[i] = new SeatView(r, this, model.getSeats()[i], i);
            seats[i].setCoordinates(SEAT_POINTS[i].x, SEAT_POINTS[i].y);
//            room.add(seats[i].getSeatPanel());
        }
        
        room.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                clicked = true;
                
            }            
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof Integer) {
            int position = (Integer) arg;
            HardwareView computer = getFromPosition(position);
            if(computer != null) {
                computers.remove(computer);
            }
        } else {
            Hardware comp = (Hardware) arg;
            HardwareView computer = new HardwareView(comp, this);
            computers.add(computer);
        }
//        room.repaint();
    }
    
    private HardwareView getFromPosition(int position) {
        for(int i = 0; i < computers.size(); i++) {
            if(computers.get(i).getModel().getPosition() == position) {
                return computers.get(i);
            }
        }
        return null;
    }
    
    /**
     * Draw this table using the current graphic context.
     * @param g The current graphic context
     * @param tx //The x coordinate to draw the table
     * @param ty //The y coordinate to draw the table
     */
    public void drawTable(Graphics g, int tx, int ty) {
        model.setX(tx);
        model.setY(ty);
        
        g.drawImage(ViewUtilities.TABLE_IMG, model.getX(), model.getY(), 
                                TABLE_WIDTH, TABLE_HEIGHT, null);
//        panel.setBounds(x, y, TABLE_WIDTH, TABLE_HEIGHT);
        drawHardwares(g);
        drawSeatViews(g);  //  There is a big bug in the seat view
    }
    
    public void drawTable(Graphics g, int tx, int ty, boolean rotated) {
        model.setX(tx);
        model.setY(ty);
        
        if(rotated) {
            Graphics2D g2d = (Graphics2D)(g.create());        
            g2d.rotate(Math.toRadians(45), model.getX() + TABLE_WIDTH/2, 
                                           model.getY() + TABLE_HEIGHT/2);
            g2d.drawImage(ViewUtilities.TABLE_IMG, model.getX(), model.getY(), 
                                        TABLE_WIDTH, TABLE_HEIGHT, null);
        }
        else {
            g.drawImage(ViewUtilities.TABLE_IMG, model.getX(), model.getY(), 
                                        TABLE_WIDTH, TABLE_HEIGHT, null);
        }       
        
//        panel.setBounds(x, y, TABLE_WIDTH, TABLE_HEIGHT);
        drawHardwares(g);        
    }
    
    /**
     * Draw the hardwares on this table using the current graphic <br/>
     * context.
     * @param g The current graphic context. 
     */
    private void drawHardwares(Graphics g) {
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < computers.size(); j++) {
                if(computers.get(j).getModel().getPosition() == i) {
                    computers.get(j).drawHardware(g);
                    if(computers.get(j).getModel().isEquipped()) {
                        monitors.get(i).drawHardware(g);
                    }
                }
            }
        }
//        for(HardwareView computer : computers) {
//            computer.drawHardware(g);
//        }
//        for(HardwareView monitor : monitors) {
//            monitor.drawHardware(g);
//        }
    }
    
    private void drawSeatViews(Graphics g) {
        for(int i=0; i<seats.length; i++) {
            seats[i].drawSeatView(g);
        }
    }
    
    /**
     * Put more hardware on the table. If the number of hardwares exceeds
     * <br/>the maximum capacity <code>TABLE_HW_CAPACITY</code>, then putting
     * <br/>fail & return false.
     * @param hv The hardware to be put.
     * @return True if the table still has space to put the hardware. False<br/>
     * otherwise.
     */
//    public boolean putHardware(HardwareView hv) {
//        if(computers.size() < TABLE_HW_CAPACITY) {
//            hv.setTable(this);
//            hv.setRoom(room);
//            
//            if(computers.size() < 2) {
//                hv.setFaceUp(true);
//            }
//            
//            if(hv.isMonitor()) {
//                hv.setCoordinates(MONITOR_POINTS[numMonitors].x, 
//                              MONITOR_POINTS[numMonitors].y);
//                numMonitors++;
//            }
//            else {
//                hv.setCoordinates(COMPUTER_POINTS[numComputers].x, 
//                              COMPUTER_POINTS[numComputers].y);
//                numComputers++;
//            }
//            
//            room.add(hv.getPanel());
//            computers.add(hv);            
//            return true;
//        }
//        return false;
//    }
    
    /**
     * Remove a specific hardware from this table.
     * @param hv The hardware to be removed.
     */
//    public void removeHardware(HardwareView hv) {
////        if (hv.isMonitor()) {
////            numMonitors--;
////        } else {
////            numComputers--;
////        }
////        
////        hv.setTable(null);
////        room.remove(hv.getPanel());
////        computers.remove(hv);
//        
//        if(hv.getModel().isEquipped()) {
//            int index = 0;
//            for(int i = 0; i < computers.size(); i++) {
//                if(computers.get(i).equals(hv)){
//                    index = i;
//                }
//            }
//            computers.get(index).setTable(null);
//            room.remove(computers.get(index).getPanel());
//            computers.remove(computers.get(index));
//        }
//        hv.setTable(null);
//        room.remove(hv.getPanel());
//        computers.remove(hv);
//    }
    
    
    
    
//    /**
//     * Put more computer on the table. If the number of computers exceeds
//     * <br/>the maximum capacity <code>TABLE_PC_CAPACITY</code>, then putting
//     * <br/>failed & return false.
//     * @param computer The computer to be put.
//     * @return True if the table still has space to put the computer. False<br/>
//     * otherwise.
//     */
//    public boolean putComputer(HardwareView computer) {
//        if(computers.size() < TABLE_PC_CAPACITY) {
//            computer.setTable(this);
//            computer.setRoom(room);
//            
//            if(computers.size() < 2) {
//                computer.setFaceUp(true);
//            }
//                        
//            computer.setCoordinates(COMPUTER_POINTS[computers.size()].x, 
//                                    COMPUTER_POINTS[computers.size()].y);
//            computers.add(computer); 
//            
//            addHardwareToRoom(computer);
//            return true;
//        }
//        return false;
//    }
//    
//    /**
//     * Remove a specific computer from this table.
//     * @param computer The computer to be removed.
//     */
//    public void removeComputer(HardwareView computer) {          
//        computer.setTable(null);        
//        computers.remove(computer);
//        
//        for(int i=0; i<computers.size(); i++) {
//            if(i < 2) {
//                computers.get(i).setFaceUp(true);
//            }
//            computers.get(i)
//                    .setCoordinates(COMPUTER_POINTS[i].x, COMPUTER_POINTS[i].y);
//        }
//        
//        removeHardwareFromRoom(computer);
//    }
//    
//    /**
//     * Put more monitor on the table. If the number of monitors exceeds
//     * <br/>the maximum capacity <code>TABLE_MN_CAPACITY</code>, then putting
//     * <br/>failed & return false.
//     * @param monitor The monitor to be put.
//     * @return True if the table still has space to put the monitor. False<br/>
//     * otherwise.
//     */
//    public boolean putMonitor(HardwareView monitor) {
//        if(monitors.size() < TABLE_PC_CAPACITY) {
//            monitor.setTable(this);
//            monitor.setRoom(room);            
//            
//            monitor.setCoordinates(MONITOR_POINTS[monitors.size()].x, 
//                                    MONITOR_POINTS[monitors.size()].y);   
//            monitors.add(monitor); 
//            
//            addHardwareToRoom(monitor);
//            return true;
//        }
//        return false;
//    }
//    
//    /**
//     * Remove a specific monitor from this table.
//     * @param monitor The monitor to be removed.
//     */
//    public void removeMonitor(HardwareView monitor) {          
//        monitor.setTable(null);        
//        monitors.remove(monitor);
//        
//        for(int i=0; i<monitors.size(); i++) {
//            monitors.get(i)
//                    .setCoordinates(MONITOR_POINTS[i].x, MONITOR_POINTS[i].y);
//        }
//        
//        removeHardwareFromRoom(monitor);
//    }
    
    private void addHardwareToRoom(HardwareView hv) {
        room.add(hv.getPanel());
//        room.repaint();
    }
    
    private void removeHardwareFromRoom(HardwareView hv) {
        room.remove(hv.getPanel());
//        room.repaint();
    }
    
    public void displayAvaiSeats() {
        for(SeatView seat : seats) {
//            if(seat.isAvailable()) {
                seat.setAlerting(true);
//            }
        }
    }
    
    public void stopDisplayAvaiSeats() {
        for(SeatView seat : seats) {
            seat.setAlerting(false);
        }
    }

    public boolean hasDeveloper() {
        for(SeatView seat : seats) {
            if(!seat.isAvailable()) {
                return true;
            }
        }        
        return false;
    }
    
    public boolean hasCompAtPosition(int position) {
        for(HardwareView comp : computers) {
            if(comp.getModel().getPosition() == position) {
                return true;
            }
        }
        return false;
    }
    
    public int getIndexFromPosition(int position) {
        for(int i = 0; i < computers.size(); i++) {
            if(computers.get(i).getModel().getPosition() == position) {
                return i;
            }
        }
        return NONE;
    }
    
    public RoomView getRoom() {
        return room;
    }
    
    public ArrayList<HardwareView> getComputers() {
        return computers;
    }

    public ArrayList<HardwareView> getMonitors() {
        return monitors;
    }

    public SeatView[] getSeats() {
        return seats;
    }
    
    public int getX() {
        return model.getX();
    }

    public int getY() {
        return model.getY();
    }

    public void setX(int x) {
        model.setX(x);
    }

    public void setY(int y) {
        model.setY(y);
    }        
}
