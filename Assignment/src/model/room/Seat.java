/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.room;

import java.io.Serializable;
import java.util.Observable;
import model.Developer;

/**
 * The model of the seat where developer sits to work.
 * @author s3342128
 */
public class Seat extends Observable implements Serializable {
    
    private Room room; //The room this seat is placed
    private Developer currDev; //The current developer on this seat
    private boolean faceDown; //If true, the seat face is turn up.
                            //Used for computers only.
    
    public Seat(Room r) {
        room = r;
//        currDev = new Developer("Hung", null);
    }
    
    public boolean isAvailable() {
        return currDev == null;
    }

    public Developer getCurrDev() {
        return currDev;
    }

    public void setCurrDev(Developer dev) {
        this.currDev = dev;
        if(this.currDev == null) {
            setChanged();
            notifyObservers();
        }
    }

    public Room getRoom() {
        return room;
    }    

    public boolean isFaceDown() {
        return faceDown;
    }

    public void setFaceDown(boolean faceDown) {
        this.faceDown = faceDown;
    }
    
}
