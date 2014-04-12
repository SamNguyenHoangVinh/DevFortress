/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.room;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import model.player.Player;
import view.ViewUtilities;

/**
 *
 * @author s3342128
 */
public class Room extends Observable implements Serializable {
    
//    private static int autoincreRoomNo = 0;
    
    private WorkingTable[] tables;
    private int roomNo;
    private static final String TABLE_NAMES[] = {"Block Alpha", "Block Beta", 
        "Block Gamma", "Block Delta"};
    
    public Room() {
//        roomNo = autoincreRoomNo++;
        roomNo = Player.getInstance().getNextRoomId();
        tables = new WorkingTable[4];
//        ArrayList<String> serials = new ArrayList<String>();
        
        for(int i=0; i<4; i++) {
            tables[i] = new WorkingTable(this, TABLE_NAMES[i]);
            int path = 0;
            String name = "";
            int price = 0;
            float productivity = 0;
            
            switch(i) {
                case 0: path = ViewUtilities.IPAC_IDX; 
                    name = "iPac 3G";
                    price = 3000;
                    productivity = 1.5f;
                    break;
                case 1: path = ViewUtilities.PX64RX_IDX; 
                    name = "PX64-RX";
                    price = 1000;
                    productivity = 2.0f;
                    break;
                case 2: path = ViewUtilities.TERMINUS2_IDX; 
                    name = "Terminus II";
                    price = 20000;
                    productivity = 5.0f;
                    break;
                case 3: path = ViewUtilities.V5LX_IDX;
                    name = "V5-LX";
                    price = 8000;
                    productivity = 3.0f;
                    break;
            }
            
//            for(int j=0; j<4; j++) {
//                Hardware computer = new Hardware(path, name, price, productivity);
//                String serial = null;
//                do {
//                    serial = computer.generateSerialInit();
//                } while(serials.contains(serial));
//                serials.add(serial);
//                computer.setSerial(serial);
//                tables[i].putComputer(computer, j);
//            }
            
            for(int j=0; j<4; j++) {
                Hardware monitor = new Hardware(ViewUtilities.MONITOR_IDX, 
                                                             "S15", 2000, 2.0f);
                tables[i].putMonitor(monitor);
            }
        }
    }
    
    public void updateGUI() {
        setChanged();
        notifyObservers();
    }
    
//    public static void resetID() {
//        autoincreRoomNo = 0;
//    }

    public WorkingTable getTableAtIndex(int i) {
        return tables[i];
    }
    
    public WorkingTable[] getTables() {
        return tables;
    }

    public int getRoomNo() {
        return roomNo;
    }
        
}
