/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.room;

import java.io.Serializable;
import java.util.LinkedHashMap;
import model.player.Player;

/**
 *
 * @author HungHandsome
 */
public class RoomSet implements Serializable {
    private static final long serialVersionUID = 8205070534567417638L;
    
    public static final int MAX_SIZE = 9;
    
//    private static int autoincreRoomSetId = 0;
    
    private LinkedHashMap<Integer, Room> rooms;
    private int id;
    
    public RoomSet() {
//        id = autoincreRoomSetId++;
        id = Player.getInstance().getNextRoomSetId();
        rooms = new LinkedHashMap<Integer, Room>();
    }
    
    /**
     * Add new room to this set. If the size exceeds the MAX_SIZE, or the <br/>
     * new room number is already included, the adding will be failed.
     * @param r The new room
     * @return True if the adding is successful. False otherwise.
     */
    public boolean addRoom(Room r) {
        if(rooms.size()>=MAX_SIZE || rooms.containsKey(r.getRoomNo())) {
            return false;
        }        
        rooms.put(r.getRoomNo(), r);
        return true;
    }

//    public static void resetID() {
//        autoincreRoomSetId = 0;
//    }
    
    public int size() {
        return rooms.size();
    }
    
    public Room getRoom(int idx) {
        return (Room)(rooms.values().toArray()[idx]);
    }
    
//    public Room[] getRoomArray() {
//        return (Room[])(rooms.values().toArray());
//    }
    
//    public static int getAutoincreRoomSetId() {
//        return autoincreRoomSetId;
//    }

    public LinkedHashMap<Integer, Room> getRooms() {
        return rooms;
    }

    public int getId() {
        return id;
    }
    
}
