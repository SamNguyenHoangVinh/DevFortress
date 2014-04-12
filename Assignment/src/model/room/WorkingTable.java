/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.room;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import model.player.Player;
import view.roomproperties.WorkingTableView;

/**
 *
 * @author s3342128
 */
public class WorkingTable extends Observable implements Serializable {

    private ArrayList<Hardware> computers; //The list of computers on 
    //this table
    private ArrayList<Hardware> monitors; //The list of monitors on 
    //this table
    private Seat[] seats;
    private Room room;
    private String name;
    private int x; //The x coordinate to draw the table
    private int y; //The y coordinate to draw the table

    public WorkingTable(Room r, String name) {
        this.name = name;
        room = r;
        computers = new ArrayList<Hardware>();
        monitors = new ArrayList<Hardware>();
        seats = new Seat[4];

        for (int i = 0; i < seats.length; i++) {
            seats[i] = new Seat(room);
        }
    }

    /**
     * Put more computer on the table. If the number of computers exceeds
     * <br/>the maximum capacity
     * <code>TABLE_PC_CAPACITY</code>, then putting <br/>failed & return false.
     *
     * @param computer The computer to be put.
     * @return True if the table still has space to put the computer. False<br/>
     * otherwise.
     */
    public boolean putComputer(Hardware computer, int position) {
        if (computers.size() < WorkingTableView.TABLE_PC_CAPACITY) {
            computer.setTable(this, position);
            System.out.println("add pos " + computer.getPosition());
            if (!seats[position].isAvailable()) {
                seats[position].getCurrDev().setComputer(computer);
            }
            Player.getInstance().getTableComputers().put(computer.getSerial(), computer);
            computer.setRoom(room);

//            if(computers.size() < 2) {
//                computer.setFaceUp(true);
//            }
            if (computer.getPosition() < 2) {
                computer.setFaceUp(true);
            } else {
                computer.setFaceUp(false);
            }

            computer.setCoordinates(
                    WorkingTableView.COMPUTER_POINTS[computer.getPosition()].x,
                    WorkingTableView.COMPUTER_POINTS[computer.getPosition()].y);
            computers.add(computer);

            setChanged();
            notifyObservers(computer);

//            addHardwareToRoom(computer);
            return true;
        }
        return false;
    }

    /**
     * Remove a specific computer from this table.
     *
     * @param computer The computer to be removed.
     */
    public void removeComputer(Hardware computer) {
        if (!seats[computer.getPosition()].isAvailable()) {
            seats[computer.getPosition()].getCurrDev().setComputer(null);
        }
        computer.setTable(null, -1);
        computers.remove(computer);
        System.out.println("remove pos " + computer.getPosition());
        Player.getInstance().getTableComputers().remove(computer.getSerial());
        setChanged();
        notifyObservers(computer.getPosition());
//        for(int i=0; i<computers.size(); i++) {
//            if(i < 2) {
//                computers.get(i).setFaceUp(true);
//            }
//            computers.get(i)
//                    .setCoordinates(WorkingTableView.COMPUTER_POINTS[i].x, 
//                                    WorkingTableView.COMPUTER_POINTS[i].y);
//        }

//        removeHardwareFromRoom(computer);
    }

    /**
     * Put more monitor on the table. If the number of monitors exceeds <br/>the
     * maximum capacity
     * <code>TABLE_MN_CAPACITY</code>, then putting <br/>failed & return false.
     *
     * @param monitor The monitor to be put.
     * @return True if the table still has space to put the monitor. False<br/>
     * otherwise.
     */
    public boolean putMonitor(Hardware monitor) {
        if (monitors.size() < WorkingTableView.TABLE_PC_CAPACITY) {
            monitor.setTable(this, -1);
            monitor.setRoom(room);

            monitor.setCoordinates(
                    WorkingTableView.MONITOR_POINTS[monitors.size()].x,
                    WorkingTableView.MONITOR_POINTS[monitors.size()].y);
            monitors.add(monitor);

//            addHardwareToRoom(monitor);
            return true;
        }
        return false;
    }

    /**
     * Remove a specific monitor from this table.
     *
     * @param monitor The monitor to be removed.
     */
    public void removeMonitor(Hardware monitor) {
        monitor.setTable(null, -1);
        monitors.remove(monitor);

        for (int i = 0; i < monitors.size(); i++) {
            monitors.get(i)
                    .setCoordinates(WorkingTableView.MONITOR_POINTS[i].x,
                    WorkingTableView.MONITOR_POINTS[i].y);
        }

//        removeHardwareFromRoom(monitor);
    }

    private void addHardwareToRoom(Hardware hv) {
//        room.add(hv.getPanel());
        room.updateGUI();
    }

    private void removeHardwareFromRoom(Hardware hv) {
//        room.remove(hv.getPanel());
        room.updateGUI();
    }

//    public void displayAvaiSeats() {
//        for(Seat seat : seats) {
//            seat.setAlerting(true);
//        }
//    }
//    
//    public void stopDisplayAvaiSeats() {
//        for(Seat seat : seats) {
//            seat.setAlerting(false);
//        }
//    }
    public ArrayList<Hardware> getComputers() {
        return computers;
    }

    public ArrayList<Hardware> getMonitors() {
        return monitors;
    }

    public Seat[] getSeats() {
        return seats;
    }

    public Room getRoom() {
        return room;
    }

    public String getName() {
        return name;
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
}
